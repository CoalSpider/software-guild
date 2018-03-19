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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Ben Norman
 */
public class HeroServiceImplTest {
    HeroService service;
    
    public HeroServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
     @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        service = ctx.getBean(HeroService.class);

        if (service == null) {
            throw new RuntimeException("null service");
        }

        // clear the database
        // delete heros
        for (Hero h : service.getAllHeros()) {
            service.deleteHero(h.getId());
        }
        // delete powers
        for (Superpower p : service.getAllSuperpowers()) {
            service.deleteSuperpower(p.getId());
        }
        // delete organizations
        for (Organization o : service.getAllOrganizations()) {
            service.deleteOrganization(o.getId());
        }
        // delete Sightings
        for (Sighting s : service.getAllSightings()) {
            service.deleteSighting(s.getId());
        }
        // delete locations
        for (Location l : service.getAllLocations()) {
            service.deleteLocation(l.getId());
        }
    }

    @After
    public void tearDown() {
    }

    //================================================================
    // Hero Tests
    //================================================================
    private Hero getHero1() {
        Hero h = new Hero();
        // add name and desc
        h.setName("loneStar");
        h.setDescription("A gunslinging hero, a freelancer not part of any known organization");
        // add powers
        List<Superpower> powers = new ArrayList<>();
        powers.add(service.createSuperpower(getSuperpower1()));
        h.setPowers(powers);
        // add org
        h.setOrganizations(new ArrayList<>());

        return h;
    }

    private Hero getHero2() {
        Hero h = new Hero();
        // add name and desc
        h.setName("fumbledor");
        h.setDescription("A bumbling fool of a hero. Legend says hes never cast the same spell twice");
        // add powers
        List<Superpower> powers = new ArrayList<>();
        powers.add(service.createSuperpower(getSuperpower2()));
        h.setPowers(powers);
        // add org
        List<Organization> orgs = new ArrayList<>();
        orgs.add(service.createOrganization(getOrganization1()));
        h.setOrganizations(orgs);

        return h;
    }

    @Test
    public void testCreateHero() {
        Hero h = getHero1();
        // add the first
        Hero r1 = service.createHero(h);
        // confirm we only added one
        assertEquals(service.getAllHeros().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(service.getHeroById(r1.getId()), r1);

        Hero h2 = getHero2();
        // add the second
        Hero r2 = service.createHero(h2);
        // confirm theres now two
        assertEquals(service.getAllHeros().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(service.getHeroById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteHero() {
        // create out heros
        Hero h = service.createHero(getHero1());
        Hero h2 = service.createHero(getHero2());
        // check we have two things
        assertEquals(service.getAllHeros().size(), 2);
        // delete the first hero
        service.deleteHero(h.getId());
        // confirm we removed something
        assertEquals(service.getAllHeros().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(service.getHeroById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(service.getHeroById(h2.getId()), h2);
        // delete the second hero
        service.deleteHero(h2.getId());
        // confirm theres nothing left
        assert (service.getAllHeros().isEmpty());
        // double check that its not there
        assertEquals(service.getHeroById(h2.getId()), null);

    }

    @Test
    public void testUpdateHero() {
        Hero h1 = service.createHero(getHero1());
        Hero h2 = service.createHero(getHero2());
        // check we add two things
        assertEquals(service.getAllHeros().size(), 2);
        // update h1
        h1.setName("Lone-Star");
        Hero updated = service.updateHero(h1.getId(), h1);
        // confirm that we returned the updated hero
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(service.getHeroById(h1.getId()), h1);
        // confirm we didnt update any other heros
        Hero r2 = service.getHeroById(h2.getId());
        // confirm second hero didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetHeroById() {
        Hero h1 = service.createHero(getHero1());
        Hero h2 = service.createHero(getHero2());
        // check we add two things
        assertEquals(service.getAllHeros().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(service.getHeroById(h1.getId()), h1);
        assertEquals(service.getHeroById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllHeros() {
        Hero h1 = service.createHero(getHero1());
        Hero h2 = service.createHero(getHero2());
        // confirm that there are two things
        assertEquals(service.getAllHeros().size(), 2);
    }

    //================================================================
    // Sighting methods
    //================================================================
    private Sighting getSighting1() {
        Sighting s = new Sighting();
        s.setDateAndTime(LocalDateTime.of(2018, 3, 18, 6, 5));
        Location l = service.createLocation(getLocation1());
        s.setLocation(l);
        List<Hero> heros = new ArrayList<>();
        Hero h = service.createHero(getHero1());
        heros.add(h);
        s.setHeros(heros);
        return s;
    }

    private Sighting getSighting2() {
        Sighting s = new Sighting();
        s.setDateAndTime(LocalDateTime.of(2017, 2, 17, 0, 30));
        Location l = service.createLocation(getLocation2());
        s.setLocation(l);
        List<Hero> heros = new ArrayList<>();
        Hero h = service.createHero(getHero2());
        heros.add(h);
        s.setHeros(heros);
        return s;
    }

    @Test
    public void testCreateSighting() {
        Sighting h = getSighting1();
        // add the first
        Sighting r1 = service.createSighting(h);
        // confirm we only added one
        assertEquals(service.getAllSightings().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(service.getSightingById(r1.getId()), r1);

        Sighting h2 = getSighting2();
        // add the second
        Sighting r2 = service.createSighting(h2);
        // confirm theres now two
        assertEquals(service.getAllSightings().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(service.getSightingById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteSighting() {
        // create out sightings
        Sighting h = service.createSighting(getSighting1());
        Sighting h2 = service.createSighting(getSighting2());
        // check we have two things
        assertEquals(service.getAllSightings().size(), 2);
        // delete the first sighting
        service.deleteSighting(h.getId());
        // confirm we removed something
        assertEquals(service.getAllSightings().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(service.getSightingById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(service.getSightingById(h2.getId()), h2);
        // delete the second sighting
        service.deleteSighting(h2.getId());
        // confirm theres nothing left
        assert (service.getAllSightings().isEmpty());
        // double check that its not there
        assertEquals(service.getSightingById(h2.getId()), null);

    }

    @Test
    public void testUpdateSighting() {
        Sighting h1 = service.createSighting(getSighting1());
        Sighting h2 = service.createSighting(getSighting2());
        // check we add two things
        assertEquals(service.getAllSightings().size(), 2);
        // update h1
        List<Hero> heros = h1.getHeros();
        heros.add(service.createHero(getHero2()));
        h1.setHeros(heros);
        Sighting updated = service.updateSighting(h1.getId(), h1);
        // confirm that we returned the updated sighting
        assertEquals(h1, updated);
        // confirm that we updated the databse
        Sighting g1 = service.getSightingById(h1.getId());
        assertEquals(g1.getId(), h1.getId());
        assertEquals(g1.getDateAndTime(), h1.getDateAndTime());
        assertEquals(g1.getLocation(), h1.getLocation());
        assertEquals(g1.getHeros(), h1.getHeros());
        assertEquals(service.getSightingById(h1.getId()), h1);
        // confirm we didnt update any other sightings
        Sighting r2 = service.getSightingById(h2.getId());
        // confirm second sighting didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetSightingById() {
        Sighting h1 = service.createSighting(getSighting1());
        Sighting h2 = service.createSighting(getSighting2());
        // check we add two things
        assertEquals(service.getAllSightings().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(service.getSightingById(h1.getId()), h1);
        assertEquals(service.getSightingById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllSightings() {
        Sighting h1 = service.createSighting(getSighting1());
        Sighting h2 = service.createSighting(getSighting2());
        // confirm that there are two things
        assertEquals(service.getAllSightings().size(), 2);
    }

    //================================================================
    // Location methods
    //================================================================
    private Location getLocation1() {
        Location l = new Location();
        l.setName("test");
        l.setDescription("a test description");
        l.setAddress("anacrtica address");
        // anartica 76.2506 N, 100.1140 W
        l.setLatitude(new BigDecimal("76.25060"));
        l.setLongitude(new BigDecimal("100.11400"));
        return l;
    }

    private Location getLocation2() {
        Location l = new Location();
        l.setName("another location");
        l.setDescription("the second test description");
        l.setAddress("some address");
        l.setLatitude(new BigDecimal("76.25060"));
        l.setLongitude(new BigDecimal("50.11400"));
        return l;
    }

    @Test
    public void testCreateLocation() {
        Location h = getLocation1();
        // add the first
        Location r1 = service.createLocation(h);
        // confirm we only added one
        assertEquals(service.getAllLocations().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(service.getLocationById(r1.getId()), r1);

        Location h2 = getLocation2();
        // add the second
        Location r2 = service.createLocation(h2);
        // confirm theres now two
        assertEquals(service.getAllLocations().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(service.getLocationById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteLocation() {
        // create out locations
        Location h = service.createLocation(getLocation1());
        Location h2 = service.createLocation(getLocation2());
        // check we have two things
        assertEquals(service.getAllLocations().size(), 2);
        // delete the first location
        service.deleteLocation(h.getId());
        // confirm we removed something
        assertEquals(service.getAllLocations().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(service.getLocationById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(service.getLocationById(h2.getId()), h2);
        // delete the second location
        service.deleteLocation(h2.getId());
        // confirm theres nothing left
        assert (service.getAllLocations().isEmpty());
        // double check that its not there
        assertEquals(service.getLocationById(h2.getId()), null);

    }

    @Test
    public void testUpdateLocation() {
        Location h1 = service.createLocation(getLocation1());
        Location h2 = service.createLocation(getLocation2());
        // check we add two things
        assertEquals(service.getAllLocations().size(), 2);
        // update h1
        h1.setName("new location");
        Location updated = service.updateLocation(h1.getId(), h1);
        // confirm that we returned the updated location
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(service.getLocationById(h1.getId()), h1);
        // confirm we didnt update any other locations
        Location r2 = service.getLocationById(h2.getId());
        // confirm second location didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetLocationById() {
        Location h1 = service.createLocation(getLocation1());
        Location h2 = service.createLocation(getLocation2());
        // check we add two things
        assertEquals(service.getAllLocations().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(service.getLocationById(h1.getId()), h1);
        assertEquals(service.getLocationById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllLocations() {
        Location h1 = service.createLocation(getLocation1());
        Location h2 = service.createLocation(getLocation2());
        // confirm that there are two things
        assertEquals(service.getAllLocations().size(), 2);
    }

    //================================================================
    // superpower methods
    //================================================================
    private Superpower getSuperpower1() {
        Superpower p = new Superpower();
        p.setName("Sharpshooter");
        p.setDescription("Never misses a shot");
        return p;
    }

    private Superpower getSuperpower2() {
        Superpower p = new Superpower();
        p.setName("Wild Magic");
        p.setDescription("Could do literally anything.");
        return p;
    }

    @Test
    public void testCreateSuperpower() {
        Superpower h = getSuperpower1();
        // add the first
        Superpower r1 = service.createSuperpower(h);
        // confirm we only added one
        assertEquals(service.getAllSuperpowers().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(service.getSuperpowerById(r1.getId()), r1);

        Superpower h2 = getSuperpower2();
        // add the second
        Superpower r2 = service.createSuperpower(h2);
        // confirm theres now two
        assertEquals(service.getAllSuperpowers().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(service.getSuperpowerById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteSuperpower() {
        // create out superpowers
        Superpower h = service.createSuperpower(getSuperpower1());
        Superpower h2 = service.createSuperpower(getSuperpower2());
        // check we have two things
        assertEquals(service.getAllSuperpowers().size(), 2);
        // delete the first superpower
        service.deleteSuperpower(h.getId());
        // confirm we removed something
        assertEquals(service.getAllSuperpowers().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(service.getSuperpowerById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(service.getSuperpowerById(h2.getId()), h2);
        // delete the second superpower
        service.deleteSuperpower(h2.getId());
        // confirm theres nothing left
        assert (service.getAllSuperpowers().isEmpty());
        // double check that its not there
        assertEquals(service.getSuperpowerById(h2.getId()), null);

    }

    @Test
    public void testUpdateSuperpower() {
        Superpower h1 = service.createSuperpower(getSuperpower1());
        Superpower h2 = service.createSuperpower(getSuperpower2());
        // check we add two things
        assertEquals(service.getAllSuperpowers().size(), 2);
        // update h1
        h1.setName("Gunslinger");
        Superpower updated = service.updateSuperpower(h1.getId(), h1);
        // confirm that we returned the updated superpower
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(service.getSuperpowerById(h1.getId()), h1);
        // confirm we didnt update any other superpowers
        Superpower r2 = service.getSuperpowerById(h2.getId());
        // confirm second superpower didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetSuperpowerById() {
        Superpower h1 = service.createSuperpower(getSuperpower1());
        Superpower h2 = service.createSuperpower(getSuperpower2());
        // check we add two things
        assertEquals(service.getAllSuperpowers().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(service.getSuperpowerById(h1.getId()), h1);
        assertEquals(service.getSuperpowerById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllSuperpowers() {
        Superpower h1 = service.createSuperpower(getSuperpower1());
        Superpower h2 = service.createSuperpower(getSuperpower2());
        // confirm that there are two things
        assertEquals(service.getAllSuperpowers().size(), 2);
    }

    //================================================================
    // Organization methods
    //================================================================
    private Organization getOrganization1() {
        Organization o = new Organization();
        o.setName("HeroOrg");
        o.setDescription("Organization for heros");
        o.setEmail("heroorg@gmail.com");
        o.setPhoneNumber("123-456-7890");
        o.setAddress("heroorg address");
        return o;
    }

    private Organization getOrganization2() {
        Organization o = new Organization();
        o.setName("VillanOrg");
        o.setDescription("Organization for villans");
        o.setEmail("villanorg@gmail.com");
        o.setPhoneNumber("987-654-3210");
        o.setAddress("villanorg address");
        return o;
    }

    @Test
    public void testCreateOrganization() {
        Organization h = getOrganization1();
        // add the first
        Organization r1 = service.createOrganization(h);
        // confirm we only added one
        assertEquals(service.getAllOrganizations().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(service.getOrganizationById(r1.getId()), r1);

        Organization h2 = getOrganization2();
        // add the second
        Organization r2 = service.createOrganization(h2);
        // confirm theres now two
        assertEquals(service.getAllOrganizations().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(service.getOrganizationById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteOrganization() {
        // create out organizations
        Organization h = service.createOrganization(getOrganization1());
        Organization h2 = service.createOrganization(getOrganization2());
        // check we have two things
        assertEquals(service.getAllOrganizations().size(), 2);
        // delete the first organization
        service.deleteOrganization(h.getId());
        // confirm we removed something
        assertEquals(service.getAllOrganizations().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(service.getOrganizationById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(service.getOrganizationById(h2.getId()), h2);
        // delete the second organization
        service.deleteOrganization(h2.getId());
        // confirm theres nothing left
        assert (service.getAllOrganizations().isEmpty());
        // double check that its not there
        assertEquals(service.getOrganizationById(h2.getId()), null);

    }

    @Test
    public void testUpdateOrganization() {
        Organization h1 = service.createOrganization(getOrganization1());
        Organization h2 = service.createOrganization(getOrganization2());
        // check we add two things
        assertEquals(service.getAllOrganizations().size(), 2);
        // update h1
        h1.setName("HeroicOrg");
        Organization updated = service.updateOrganization(h1.getId(), h1);
        // confirm that we returned the updated organization
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(service.getOrganizationById(h1.getId()), h1);
        // confirm we didnt update any other organizations
        Organization r2 = service.getOrganizationById(h2.getId());
        // confirm second organization didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetOrganizationById() {
        Organization h1 = service.createOrganization(getOrganization1());
        Organization h2 = service.createOrganization(getOrganization2());
        // check we add two things
        assertEquals(service.getAllOrganizations().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(service.getOrganizationById(h1.getId()), h1);
        assertEquals(service.getOrganizationById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllOrganizations() {
        Organization h1 = service.createOrganization(getOrganization1());
        Organization h2 = service.createOrganization(getOrganization2());
        // confirm that there are two things
        assertEquals(service.getAllOrganizations().size(), 2);
    }

    //================================================================
    // Misc methods
    //================================================================
    @Test
    public void testGetHeroSuperpowers() {
        Hero h1 = service.createHero(getHero1());
        List<Superpower> powers = service.getHeroSuperpowers(h1.getId());
        assertEquals(powers.size(),1);
        assertEquals(h1.getPowers(),powers);
    }

    @Test
    public void testGetHeroOrganizations() {
        Hero h1 = service.createHero(getHero1());
        List<Organization> organizations = service.getHeroOrganizations(h1.getId());
        assertEquals(organizations.size(),0);
        assertEquals(h1.getOrganizations(),organizations);
    }

    @Test
    public void testGetSightingHeros() {
        Sighting s1 = service.createSighting(getSighting1());
        List<Hero> heros = service.getSightingHeros(s1.getId());
        assertEquals(heros.size(),1);
        assertEquals(s1.getHeros(),heros);
    }
}
