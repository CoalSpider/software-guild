/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.service;

import com.sg.herosightings.model.Hero;
import com.sg.herosightings.model.Location;
import com.sg.herosightings.model.Organization;
import com.sg.herosightings.model.Sighting;
import com.sg.herosightings.model.Superpower;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface HeroService {
    //================================================================
    // Hero methods
    //================================================================
    Hero createHero(Hero hero);

    Hero deleteHero(int heroId);

    Hero updateHero(int heroId, Hero updatedHero);

    Hero getHeroById(int heroId);

    List<Hero> getAllHeros();

    //================================================================
    // Sighting methods
    //================================================================
    Sighting createSighting(Sighting sighting);

    Sighting deleteSighting(int sightingId);

    Sighting updateSighting(int sightingId, Sighting updatedSighting);

    Sighting getSightingById(int sightingId);

    List<Sighting> getAllSightings();

    //================================================================
    // Location methods
    //================================================================
    Location createLocation(Location location);

    Location deleteLocation(int locationId);

    Location updateLocation(int locationId, Location updatedLocation);

    Location getLocationById(int locationId);

    List<Location> getAllLocations();

    //================================================================
    // Superpower methods
    //================================================================
    Superpower createSuperpower(Superpower superpower);

    Superpower deleteSuperpower(int superpowerId);

    Superpower updateSuperpower(int superpowerId, Superpower updatedSuperpower);

    Superpower getSuperpowerById(int superpowerId);

    List<Superpower> getAllSuperpowers();

    //================================================================
    // Organization methods
    //================================================================
    Organization createOrganization(Organization organization);

    Organization deleteOrganization(int organizationId);

    Organization updateOrganization(int organizationId, Organization updatedOrganization);

    Organization getOrganizationById(int organizationId);

    List<Organization> getAllOrganizations();
    
    
    //================================================================
    // Misc methods
    //================================================================
    List<Superpower> getHeroSuperpowers(int heroId);

    List<Organization> getHeroOrganizations(int heroId);
    
    List<Hero> getSightingHeros(int sightingId);
    
    List<Sighting> getMostRecentSightings(int count);
}
