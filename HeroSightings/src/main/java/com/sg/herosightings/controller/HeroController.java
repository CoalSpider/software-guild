/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.controller;

import com.sg.herosightings.dao.HeroDao;
import com.sg.herosightings.model.Hero;
import com.sg.herosightings.model.Location;
import com.sg.herosightings.model.Organization;
import com.sg.herosightings.model.Sighting;
import com.sg.herosightings.model.Superpower;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private HeroDao dao;

    private Sighting createTestSighting() {
        Location location = new Location();
        location.setName("Test");
        location.setDescription("test desc");
        location.setAddress("test address");
        location.setLatitude(new BigDecimal("-10.00001"));
        location.setLongitude(new BigDecimal("-10.00001"));
        location = dao.createLocation(location);

        List<Superpower> powers = new ArrayList<>();
        Superpower power = new Superpower();
        power.setName("TestPowerA");
        power.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        power = dao.createSuperpower(power);
        powers.add(power);
        Superpower power2 = new Superpower();
        power2.setName("TestPowerB");
        power2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        power2 = dao.createSuperpower(power2);
        powers.add(power2);

        List<Organization> orgs = new ArrayList<>();
        Organization org = new Organization();
        org.setName("TestOrganizationA");
        org.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        org.setAddress("this is test address for a organization");
        org.setPhoneNumber("123-456-7890");
        org.setEmail("testorganizationematil@gmail.com");
        org = dao.createOrganization(org);
        orgs.add(org);
        Organization org2 = new Organization();
        org2.setName("TestOrganizationB");
        org2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        org2.setAddress("this is test address for a organization");
        org2.setPhoneNumber("123-456-7890");
        org2.setEmail("testorganizationematil@gmail.com");
        org2 = dao.createOrganization(org2);
        orgs.add(org2);

        Hero hero = new Hero();
        hero.setName("testMan");
        hero.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
        hero.setOrganizations(orgs);
        hero.setPowers(powers);
        hero = dao.createHero(hero);
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
        List<Sighting> recentSightings = dao.getMostRecentSightings(10);
        if (recentSightings.isEmpty()) {
            recentSightings.add(dao.createSighting(createTestSighting()));
        }
        model.addAttribute("recentSightings", recentSightings);
        return "index";
    }

    //=======================================================
    // HERO METHODS
    //=======================================================
    @GetMapping("/heros")
    public String displayHeros(Model model) {
        List<Hero> heros = dao.getAllHeros();
        model.addAttribute("heros", heros);
        return "heros";
    }

    @GetMapping("/hero{id}")
    public String showHero(@PathVariable("id") Integer id, Model model) {
        Hero h = dao.getHeroById(id);
        model.addAttribute("hero", h);
        return "hero";
    }

    @GetMapping("/editHero{id}")
    public String editHero(@PathVariable("id") Integer id, Model model) {
        Hero h = dao.getHeroById(id);
        setHeroModel(h, model);
        return "editHero";
    }

    /**
     * returns a model with the following attributes
     * <li>hero - hero object</li>
     * <li>powers - list of powers not contained in hero powers</li>
     * <li>organizations - list of organizations not contained in hero
     * organization</li>
     *
     * @param hero the hero to use
     * @param model the model that was passed in
     *
     */
    private Model setHeroModel(@Valid Hero hero, Model model) {
        model.addAttribute("hero", hero);
        model.addAttribute("powers", filterList(dao.getAllSuperpowers(), hero.getPowers()));
        model.addAttribute("organizations", filterList(dao.getAllOrganizations(), hero.getOrganizations()));
        return model;
    }

    /**
     * @return a list with every element from list a that is not in list b
     */
    private <T> List<T> filterList(List<T> a, List<T> b) {
        return a.stream().filter(element -> b.contains(element) == false).collect(Collectors.toList());
    }

    @GetMapping("/createHero")
    public String createHero(Model model) {
        setHeroModel(new Hero(), model);
        return "createHero";
    }

    @GetMapping("/deleteHero{id}")
    public String deleteHero(@PathVariable("id") Integer id) {
        dao.deleteHero(id);
        return "redirect:/heros";
    }
    
    private Hero setHeroArrays(Hero hero, List<Integer> powersArray, List<Integer> organizationArray){
        // extract powers
        List<Superpower> powers = new ArrayList<>();
        powersArray.forEach(i -> powers.add(dao.getSuperpowerById(i)));
        hero.setPowers(powers);

        // extract orgs
        List<Organization> orgs = new ArrayList<>();
        organizationArray.forEach(i -> orgs.add(dao.getOrganizationById(i)));
        hero.setOrganizations(orgs);
        return hero;
    }

    @PostMapping("/saveChangesHero")
    public String saveHero(@Valid Hero hero, BindingResult bindingResult,
            @RequestParam(value = "powersArray", defaultValue = "") ArrayList<Integer> powersArray,
            @RequestParam(value = "organizationArray", defaultValue = "") ArrayList<Integer> organizationArray,
            Model model) {
        
        setHeroArrays(hero,powersArray,organizationArray);
        // if there was errors in the form set the model and goto edit hero page
        if (bindingResult.hasErrors()) {
            setHeroModel(hero, model);
            return "editHero";
        }
        //update hero
        hero = dao.updateHero(hero.getId(), hero);

        return "redirect:/hero" + hero.getId();
    }

    @PostMapping("/saveNewHero")
    public String saveNewHero(@Valid Hero hero, BindingResult bindingResult,
            @RequestParam(value = "powersArray", defaultValue = "") ArrayList<Integer> powersArray,
            @RequestParam(value = "organizationArray", defaultValue = "") ArrayList<Integer> organizationArray,
            Model model) {
        
        setHeroArrays(hero,powersArray,organizationArray);

        // if there was errors in the form set the model and goto create hero page
        if (bindingResult.hasErrors()) {
            setHeroModel(hero, model);
            model.addAttribute("hero", hero);
            return "createHero";
        }
        // create hero
        hero = dao.createHero(hero);

        return "redirect:/hero" + hero.getId();
    }

    //=======================================================
    // SUPERPOWER METHODS
    //=======================================================
    @GetMapping("/superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> powers = dao.getAllSuperpowers();
        model.addAttribute("superpowers", powers);
        return "superpowers";
    }

    @GetMapping("/superpower{id}")
    public String showSuperpower(@PathVariable("id") Integer id, Model model) {
        Superpower s = dao.getSuperpowerById(id);
        model.addAttribute("superpower", s);
        return "superpower";
    }

    @GetMapping("/createSuperpower")
    public String createSuperpower(Model model) {
        model.addAttribute("superpower", new Superpower());
        return "createSuperpower";
    }

    @GetMapping("/deleteSuperpower{id}")
    public String deleteSuperpower(@PathVariable("id") Integer id) {
        dao.deleteSuperpower(id);
        return "redirect:/superpowers";
    }

    @GetMapping("/editSuperpower{id}")
    public String editSuperpower(@PathVariable("id") Integer id, Model model) {
        Superpower s = dao.getSuperpowerById(id);
        model.addAttribute("superpower", s);
        return "editSuperpower";
    }

    @PostMapping("/saveChangesSuperpower")
    public String saveSuperpower(@Valid Superpower power, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("superpower", power);
            return "editSuperpower";
        }
        power = dao.updateSuperpower(power.getId(), power);
        return "redirect:/superpower" + power.getId();
    }

    @PostMapping("/saveNewSuperpower")
    public String saveNewSuperpower(@Valid Superpower power, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("superpower", power);
            return "createSuperpower";
        }
        power = dao.createSuperpower(power);
        return "redirect:/superpower" + power.getId();
    }

    //=======================================================
    // ORGANIZATION METHODS
    //=======================================================
    @GetMapping("/organizations")
    public String displayOrganizations(Model model) {
        List<Organization> powers = dao.getAllOrganizations();
        model.addAttribute("organizations", powers);
        return "organizations";
    }

    @GetMapping("/organization{id}")
    public String showOrganization(@PathVariable("id") Integer id, Model model) {
        Organization s = dao.getOrganizationById(id);
        model.addAttribute("organization", s);
        return "organization";
    }

    @GetMapping("/createOrganization")
    public String createOrganization(Model model) {
        model.addAttribute("organization", new Organization());
        return "createOrganization";
    }

    @GetMapping("/deleteOrganization{id}")
    public String deleteOrganization(@PathVariable("id") Integer id) {
        dao.deleteOrganization(id);
        return "redirect:/organizations";
    }

    @GetMapping("/editOrganization{id}")
    public String editOrganization(@PathVariable("id") Integer id, Model model) {
        Organization s = dao.getOrganizationById(id);
        model.addAttribute("organization", s);
        return "editOrganization";
    }

    @PostMapping("/saveChangesOrganization")
    public String saveOrganization(@Valid Organization organization, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("organization", organization);
            return "editOrganization";
        }
        organization = dao.updateOrganization(organization.getId(), organization);
        return "redirect:/organization" + organization.getId();
    }

    @PostMapping("/saveNewOrganization")
    public String saveNewOrganization(@Valid Organization organization, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("organization", organization);
            return "createOrganization";
        }
        organization = dao.createOrganization(organization);
        return "redirect:/organization" + organization.getId();
    }

    //=======================================================
    // SIGHTING METHODS
    //=======================================================
    @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> powers = dao.getAllSightings();
        model.addAttribute("sightings", powers);
        return "sightings";
    }

    @GetMapping("/sighting{id}")
    public String showSighting(@PathVariable("id") Integer id, Model model) {
        Sighting s = dao.getSightingById(id);
        model.addAttribute("sighting", s);
        return "sighting";
    }

    @GetMapping("/createSighting")
    public String createSighting(Model model) {
        model.addAttribute("locations", dao.getAllLocations());
        model.addAttribute("heros", dao.getAllHeros());
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("time", LocalTime.now());
        return "createSighting";
    }

    @GetMapping("/deleteSighting{id}")
    public String deleteSighting(@PathVariable("id") Integer id) {
        dao.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("/editSighting{id}")
    public String editSighting(@PathVariable("id") Integer id, Model model) {
        Sighting s = dao.getSightingById(id);
        model.addAttribute("sighting", s);
        // get all heros not already part of sighting
        List<Hero> heros = dao
                .getAllHeros()
                .stream()
                .filter(hero -> s.getHeros().contains(hero) == false)
                .collect(Collectors.toList());
        model.addAttribute("sightingHeros", s.getHeros());
        model.addAttribute("heros", heros);
        model.addAttribute("date", s.getDateAndTime().toLocalDate());
        model.addAttribute("time", s.getDateAndTime().toLocalTime());
        return "editSighting";
    }

    @PostMapping("/saveChangesSighting")
    public String saveSighting(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "locationId") Integer locationId,
            @RequestParam(value = "herosArray", defaultValue = "") ArrayList<Integer> herosArray,
            @RequestParam(value = "date") @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam(value = "time") @DateTimeFormat(iso = ISO.TIME) LocalTime time) {
        // create heros
        List<Hero> heros = new ArrayList<>();
        herosArray.forEach(i -> heros.add(dao.getHeroById(i)));
        // create sighting
        Sighting s = new Sighting();
        s.setLocation(dao.getLocationById(locationId));
        s.setHeros(heros);
        s.setDateAndTime(LocalDateTime.of(date, time));
        // update
        s = dao.updateSighting(id, s);
        return "redirect:/sighting" + s.getId();
    }

    @PostMapping("/saveNewSighting")
    public String saveNewSighting(
            @RequestParam(value = "locationId") Integer locationId,
            @RequestParam(value = "herosArray", defaultValue = "") ArrayList<Integer> herosArray,
            @RequestParam(value = "date") @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestParam(value = "time") @DateTimeFormat(iso = ISO.TIME) LocalTime time) {
        // create heros
        List<Hero> heros = new ArrayList<>();
        herosArray.forEach(i -> heros.add(dao.getHeroById(i)));
        // create sighting
        Sighting s = new Sighting();
        s.setLocation(dao.getLocationById(locationId));
        s.setHeros(heros);
        s.setDateAndTime(LocalDateTime.of(date, time));
        // create sighting
        s = dao.createSighting(s);
        return "redirect:/sighting" + s.getId();
    }

    //=======================================================
    // LOCATION METHODS
    //=======================================================
    @GetMapping("/locations")
    public String displayLocations(Model model) {
        List<Location> powers = dao.getAllLocations();
        model.addAttribute("locations", powers);
        return "locations";
    }

    @GetMapping("/location{id}")
    public String showLocation(@PathVariable("id") Integer id, Model model) {
        Location s = dao.getLocationById(id);
        model.addAttribute("location", s);
        return "location";
    }

    @GetMapping("/createLocation")
    public String createLocation(Model model) {
        model.addAttribute("location", new Location());
        return "createLocation";
    }

    @GetMapping("/deleteLocation{id}")
    public String deleteLocation(@PathVariable("id") Integer id) {
        dao.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("/editLocation{id}")
    public String editLocation(@PathVariable("id") Integer id, Model model) {
        Location s = dao.getLocationById(id);
        model.addAttribute("location", s);
        return "editLocation";
    }

    @PostMapping("/saveChangesLocation")
    public String saveLocation(@Valid Location location, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("location", location);
            return "editLocation";
        }
        location = dao.updateLocation(location.getId(), location);
        return "redirect:/location" + location.getId();
    }

    @PostMapping("/saveNewLocation")
    public String saveNewLocation(@Valid Location location, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("location", location);
            return "createLocation";
        }
        location = dao.createLocation(location);
        return "redirect:/location" + location.getId();
    }
}
