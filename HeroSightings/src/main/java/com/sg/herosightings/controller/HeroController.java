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
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        List<Superpower> powers = new ArrayList<>();
        Superpower power = new Superpower();
        power.setName("TestPowerA");
        power.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        power = service.createSuperpower(power);
        powers.add(power);
        Superpower power2 = new Superpower();
        power2.setName("TestPowerB");
        power2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        power2 = service.createSuperpower(power2);
        powers.add(power2);

        List<Organization> orgs = new ArrayList<>();
        Organization org = new Organization();
        org.setName("TestOrganizationA");
        org.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        org.setAddress("this is test address for a organization");
        org.setPhoneNumber("123-456-7890");
        org.setEmail("testorganizationematil@gmail.com");
        org = service.createOrganization(org);
        orgs.add(org);
        Organization org2 = new Organization();
        org2.setName("TestOrganizationB");
        org2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        org2.setAddress("this is test address for a organization");
        org2.setPhoneNumber("123-456-7890");
        org2.setEmail("testorganizationematil@gmail.com");
        org2 = service.createOrganization(org2);
        orgs.add(org2);

        Hero hero = new Hero();
        hero.setName("testMan");
        hero.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        hero.setOrganizations(orgs);
        hero.setPowers(powers);
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
        if (recentSightings.size() < 10) {
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
        // add powers hero doesnt already have
        List<Superpower> powers = service
                .getAllSuperpowers()
                .stream()
                .filter(pwr -> h.getPowers().contains(pwr) == false)
                .collect(Collectors.toList());
        model.addAttribute("powers", powers);
        // add organizations hero is not already part of
        List<Organization> orgs = service
                .getAllOrganizations()
                .stream()
                .filter(org -> h.getOrganizations().contains(org) == false)
                .collect(Collectors.toList());
        model.addAttribute("organizations", orgs);

        return "editHero";
    }

    @GetMapping("/createHero")
    public String createHero(Model model) {
        model.addAttribute("powers", service.getAllSuperpowers());
        model.addAttribute("organizations", service.getAllOrganizations());
        return "createHero";
    }

    @GetMapping("/deleteHero{id}")
    public String deleteHero(@PathVariable("id") int id) {
        service.deleteHero(id);
        return "redirect:/heros";
    }

    @PostMapping("/saveChangesHero")
    public String saveHero(Hero hero,
            // defaults to emmpty collection
            @RequestParam(value = "powersArray", defaultValue = "") ArrayList<Integer> powersArray,
            @RequestParam(value = "organizationArray", defaultValue = "") ArrayList<Integer> organizationArray) {
        // extract powers
        List<Superpower> powers = new ArrayList<>();
        for (Integer i : powersArray) {
            powers.add(service.getSuperpowerById(i));
        }
        hero.setPowers(powers);

        // extract orgs
        List<Organization> orgs = new ArrayList<>();
        for (Integer i : organizationArray) {
            orgs.add(service.getOrganizationById(i));
        }
        hero.setOrganizations(orgs);

        hero = service.updateHero(hero.getId(), hero);

        return "redirect:/hero" + hero.getId();
    }

    @PostMapping("/saveNewHero")
    public String saveNewHero(Hero hero,
            @RequestParam(value = "powersArray", defaultValue = "") ArrayList<Integer> powersArray,
            @RequestParam(value = "organizationArray", defaultValue = "") ArrayList<Integer> organizationArray) {
        // extract powers
        List<Superpower> powers = new ArrayList<>();
        for (Integer i : powersArray) {
            powers.add(service.getSuperpowerById(i));
        }
        hero.setPowers(powers);

        // extract orgs
        List<Organization> orgs = new ArrayList<>();
        for (Integer i : organizationArray) {
            orgs.add(service.getOrganizationById(i));
        }
        hero.setOrganizations(orgs);
        
        // create hero
        hero = service.createHero(hero);
        
        return "redirect:/hero" + hero.getId();
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
    
    @GetMapping("/createSuperpower")
    public String createSuperpower(){
        return "createSuperpower";
    }
    
    @GetMapping("/deleteSuperpower{id}")
    public String deleteSuperpower(@PathVariable("id") int id){
        service.deleteSuperpower(id);
        return "redirect:/superpowers";
    }
    
    @GetMapping("/editSuperpower{id}")
    public String editSuperpower(@PathVariable("id") int id, Model model){
        Superpower s = service.getSuperpowerById(id);
        model.addAttribute("superpower", s);
        return "editSuperpower";
    }
    
    @PostMapping("/saveChangesSuperpower")
    public String saveSuperpower(Superpower power){
        power = service.updateSuperpower(power.getId(), power);
        return "redirect:/superpower"+power.getId();
    }
    
    @PostMapping("/saveNewSuperpower")
    public String saveNewSuperpower(Superpower power){
        power = service.createSuperpower(power);
        return "redirect:/superpower"+power.getId();
    }

    //=======================================================
    // ORGANIZATION METHODS
    //=======================================================
     @GetMapping("/organizations")
    public String displayOrganizations(Model model) {
        List<Organization> powers = service.getAllOrganizations();
        model.addAttribute("organizations", powers);
        return "organizations";
    }

    @GetMapping("/organization{id}")
    public String showOrganization(@PathVariable("id") int id, Model model) {
        Organization s = service.getOrganizationById(id);
        model.addAttribute("organization", s);
        return "organization";
    }
    
    @GetMapping("/createOrganization")
    public String createOrganization(){
        return "createOrganization";
    }
    
    @GetMapping("/deleteOrganization{id}")
    public String deleteOrganization(@PathVariable("id") int id){
        service.deleteOrganization(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("/editOrganization{id}")
    public String editOrganization(@PathVariable("id") int id, Model model){
        Organization s = service.getOrganizationById(id);
        model.addAttribute("organization", s);
        return "editOrganization";
    }
    
    @PostMapping("/saveChangesOrganization")
    public String saveOrganization(Organization power){
        power = service.updateOrganization(power.getId(), power);
        return "redirect:/organization"+power.getId();
    }
    
    @PostMapping("/saveNewOrganization")
    public String saveNewOrganization(Organization power){
        power = service.createOrganization(power);
        return "redirect:/organization"+power.getId();
    }

    //=======================================================
    // SIGHTING METHODS
    //=======================================================
     @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> powers = service.getAllSightings();
        model.addAttribute("sightings", powers);
        return "sightings";
    }

    @GetMapping("/sighting{id}")
    public String showSighting(@PathVariable("id") int id, Model model) {
        Sighting s = service.getSightingById(id);
        model.addAttribute("sighting", s);
        return "sighting";
    }
    
    @GetMapping("/createSighting")
    public String createSighting(){
        return "createSighting";
    }
    
    @GetMapping("/deleteSighting{id}")
    public String deleteSighting(@PathVariable("id") int id){
        service.deleteSighting(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("/editSighting{id}")
    public String editSighting(@PathVariable("id") int id, Model model){
        Sighting s = service.getSightingById(id);
        model.addAttribute("sighting", s);
        return "editSighting";
    }
    
    @PostMapping("/saveChangesSighting")
    public String saveSighting(Sighting power){
        power = service.updateSighting(power.getId(), power);
        return "redirect:/sighting"+power.getId();
    }
    
    @PostMapping("/saveNewSighting")
    public String saveNewSighting(Sighting power){
        power = service.createSighting(power);
        return "redirect:/sighting"+power.getId();
    }

    //=======================================================
    // LOCATION METHODS
    //=======================================================
     @GetMapping("/locations")
    public String displayLocations(Model model) {
        List<Location> powers = service.getAllLocations();
        model.addAttribute("locations", powers);
        return "locations";
    }

    @GetMapping("/location{id}")
    public String showLocation(@PathVariable("id") int id, Model model) {
        Location s = service.getLocationById(id);
        model.addAttribute("location", s);
        return "location";
    }
    
    @GetMapping("/createLocation")
    public String createLocation(){
        return "createLocation";
    }
    
    @GetMapping("/deleteLocation{id}")
    public String deleteLocation(@PathVariable("id") int id){
        service.deleteLocation(id);
        return "redirect:/locations";
    }
    
    @GetMapping("/editLocation{id}")
    public String editLocation(@PathVariable("id") int id, Model model){
        Location s = service.getLocationById(id);
        model.addAttribute("location", s);
        return "editLocation";
    }
    
    @PostMapping("/saveChangesLocation")
    public String saveLocation(Location power){
        power = service.updateLocation(power.getId(), power);
        return "redirect:/location"+power.getId();
    }
    
    @PostMapping("/saveNewLocation")
    public String saveNewLocation(Location location){
        location = service.createLocation(location);
        return "redirect:/location"+location.getId();
    }
}
