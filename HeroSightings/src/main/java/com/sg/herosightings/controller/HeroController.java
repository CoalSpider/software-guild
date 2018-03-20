/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.controller;

import com.sg.herosightings.model.Hero;
import com.sg.herosightings.model.Location;
import com.sg.herosightings.model.Sighting;
import com.sg.herosightings.service.HeroService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Ben Norman
 */
@Controller
public class HeroController {

    @Inject
    HeroService service;

    @GetMapping("/")
    public String landingPage(Model model) {
        List<Sighting> recentSightings = service.getMostRecentSightings(10);
        recentSightings.clear();
        if (recentSightings.isEmpty()) {
            Location location = new Location();
            location.setName("Test");
            location.setDescription("test desc");
            location.setAddress("test address");
            location.setLatitude(new BigDecimal("-10.00001"));
            location.setLongitude(new BigDecimal("-10.00001"));
            location = service.createLocation(location);

            Hero hero = new Hero();
            hero.setName("testMan");
            hero.setDescription("tests application");
            hero.setOrganizations(new ArrayList<>());
            hero.setPowers(new ArrayList<>());
            hero = service.createHero(hero);
            List<Hero> heros = new ArrayList<>();
            heros.add(hero);

            Sighting sighting = new Sighting();
            sighting.setDateAndTime(LocalDateTime.now());
            sighting.setLocation(location);
            sighting.setHeros(heros);

            recentSightings.add(service.createSighting(sighting));
        }
        model.addAttribute("recentSightings", recentSightings);
        return "index";
    }
}
