/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.controller;

import com.sg.herosightings.model.Hero;
import com.sg.herosightings.model.Location;
import com.sg.herosightings.model.Organization;
import com.sg.herosightings.model.Sighting;
import com.sg.herosightings.model.Superpower;
import com.sg.herosightings.service.HeroService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Ben Norman
 */
@Controller
public class HeroController {

    @Inject
    HeroService service;

    private Sighting createTestSighting() {
        Location location = new Location();
        location.setName("Test");
        location.setDescription("test desc");
        location.setAddress("test address");
        location.setLatitude(new BigDecimal("-10.00001"));
        location.setLongitude(new BigDecimal("-10.00001"));
        location = service.createLocation(location);
        
        Superpower power = new Superpower();
        power.setName("TestPowerA");
        power.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        power = service.createSuperpower(power);
        
        Organization org = new Organization();
        org.setName("TestOrganization");
        org.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        org.setAddress("this is test address for a organization");
        org.setPhoneNumber("123-456-7890");
        org.setEmail("testorganizationematil@gmail.com");
        org = service.createOrganization(org);

        Hero hero = new Hero();
        hero.setName("testMan");
        hero.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        hero.setOrganizations(new ArrayList<>());
        hero.getOrganizations().add(org);
        hero.setPowers(new ArrayList<>());
        hero.getPowers().add(power);
        hero = service.createHero(hero);
        List<Hero> heros = new ArrayList<>();
        heros.add(hero);

        Sighting sighting = new Sighting();
        sighting.setDateAndTime(LocalDateTime.now());
        sighting.setLocation(location);
        sighting.setHeros(heros);

        return sighting;

    }

    @GetMapping("/")
    public String landingPage(Model model) {
        List<Sighting> recentSightings = service.getMostRecentSightings(10);
        if (recentSightings.isEmpty()) {
            recentSightings.add(service.createSighting(createTestSighting()));
        }
        model.addAttribute("recentSightings", recentSightings);
        return "index";
    }

    //=======================================================
    // HERO METHODS
    //=======================================================
    @GetMapping("/heros")
    public String displayHeros(Model model) {
        List<Hero> heros = service.getAllHeros();
        model.addAttribute("heros", heros);
        return "heros";
    }

    @GetMapping("/hero{id}")
    public String showHero(@PathVariable("id") int id, Model model) {
        Hero h = service.getHeroById(id);
        model.addAttribute("hero", h);
        return "hero";
    }

    @GetMapping("/editHero{id}")
    public String editHero(@PathVariable("id") int id, Model model) {
        Hero h = service.getHeroById(id);
        model.addAttribute("hero", h);
        return "editHero";
    }

    //=======================================================
    // SUPERPOWER METHODS
    //=======================================================
    @GetMapping("/superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> powers = service.getAllSuperpowers();
        model.addAttribute("superpowers", powers);
        return "superpowers";
    }
    
    @GetMapping("/superpower{id}")
    public String showSuperpower(@PathVariable("id") int id, Model model) {
        Superpower s = service.getSuperpowerById(id);
        model.addAttribute("superpower", s);
        return "superpower";
    }

    //=======================================================
    // ORGANIZATION METHODS
    //=======================================================
    @GetMapping("/organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }
    
    @GetMapping("/organization{id}")
    public String showOrganization(@PathVariable("id") int id, Model model) {
        Organization o = service.getOrganizationById(id);
        model.addAttribute("organization", o);
        return "organization";
    }

    //=======================================================
    // SIGHTING METHODS
    //=======================================================
    @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = service.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "sightings";
    }
    
    @GetMapping("/sighting{id}")
    public String showSighting(@PathVariable("id") int id, Model model) {
        Sighting s = service.getSightingById(id);
        model.addAttribute("sighting", s);
        return "sighting";
    }
    
    //=======================================================
    // LOCATION METHODS
    //=======================================================
    @GetMapping("/locations")
    public String displayLocations(Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }
    
    @GetMapping("/location{id}")
    public String showLocation(@PathVariable("id") int id, Model model) {
        Location s = service.getLocationById(id);
        model.addAttribute("location", s);
        return "location";
    }

    //=======================================================
    // HELPER METHODS
    //=======================================================
}
