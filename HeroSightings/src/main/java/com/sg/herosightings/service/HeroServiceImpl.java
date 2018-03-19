/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.service;

import com.sg.herosightings.dao.HeroDao;
import com.sg.herosightings.model.Hero;
import com.sg.herosightings.model.Location;
import com.sg.herosightings.model.Organization;
import com.sg.herosightings.model.Sighting;
import com.sg.herosightings.model.Superpower;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Ben Norman
 */
public class HeroServiceImpl implements HeroService{
    HeroDao dao;

    public void setDao(HeroDao dao) {
        this.dao = dao;
    }
    
    @Override
    public Hero createHero(Hero hero) {
        return dao.createHero(hero);
    }

    @Override
    public Hero deleteHero(int heroId) {
        return dao.deleteHero(heroId);
    }

    @Override
    public Hero updateHero(int heroId, Hero updatedHero) {
        return dao.updateHero(heroId, updatedHero);
    }

    @Override
    public Hero getHeroById(int heroId) {
        return dao.getHeroById(heroId);
    }

    @Override
    public List<Hero> getAllHeros() {
        return dao.getAllHeros();
    }

    @Override
    public Sighting createSighting(Sighting sighting) {
        return dao.createSighting(sighting);
    }

    @Override
    public Sighting deleteSighting(int sightingId) {
        return dao.deleteSighting(sightingId);
    }

    @Override
    public Sighting updateSighting(int sightingId, Sighting updatedSighting) {
        return dao.updateSighting(sightingId, updatedSighting);
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        return dao.getSightingById(sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return dao.getAllSightings();
    }

    @Override
    public Location createLocation(Location location) {
        return dao.createLocation(location);
    }

    @Override
    public Location deleteLocation(int locationId) {
        return dao.deleteLocation(locationId);
    }

    @Override
    public Location updateLocation(int locationId, Location updatedLocation) {
        return dao.updateLocation(locationId, updatedLocation);
    }

    @Override
    public Location getLocationById(int locationId) {
        return dao.getLocationById(locationId);
    }

    @Override
    public List<Location> getAllLocations() {
        return dao.getAllLocations();
    }

    @Override
    public Superpower createSuperpower(Superpower superpower) {
        return dao.createSuperpower(superpower);
    }

    @Override
    public Superpower deleteSuperpower(int superpowerId) {
        return dao.deleteSuperpower(superpowerId);
    }

    @Override
    public Superpower updateSuperpower(int superpowerId, Superpower updatedSuperpower) {
        return dao.updateSuperpower(superpowerId, updatedSuperpower);
    }

    @Override
    public Superpower getSuperpowerById(int superpowerId) {
        return dao.getSuperpowerById(superpowerId);
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        return dao.getAllSuperpowers();
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return dao.createOrganization(organization);
    }

    @Override
    public Organization deleteOrganization(int organizationId) {
        return dao.deleteOrganization(organizationId);
    }

    @Override
    public Organization updateOrganization(int organizationId, Organization updatedOrganization) {
        return dao.updateOrganization(organizationId, updatedOrganization);
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        return dao.getOrganizationById(organizationId);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return dao.getAllOrganizations();
    }

    @Override
    public List<Superpower> getHeroSuperpowers(int heroId) {
        return dao.getHeroSuperpowers(heroId);
    }

    @Override
    public List<Organization> getHeroOrganizations(int heroId) {
        return dao.getHeroOrganizations(heroId);
    }

    @Override
    public List<Hero> getSightingHeros(int sightingId) {
        return dao.getSightingHeros(sightingId);
    }

    @Override
    public List<Sighting> getMostRecentSightings(int count) {
        return dao.getMostRecentSightings(count);
    }
    
}
