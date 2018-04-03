/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.dao;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Ben Norman
 */
public class HeroDaoDBImplTest {

    private HeroDao dao;

    public HeroDaoDBImplTest() {
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

        dao = ctx.getBean(HeroDao.class);

        if (dao == null) {
            throw new RuntimeException("null dao");
        }

        // clear the database
        // delete heros
        for (Hero h : dao.getAllHeros()) {
            dao.deleteHero(h.getId());
        }
        // delete powers
        for (Superpower p : dao.getAllSuperpowers()) {
            dao.deleteSuperpower(p.getId());
        }
        // delete organizations
        for (Organization o : dao.getAllOrganizations()) {
            dao.deleteOrganization(o.getId());
        }
        // delete Sightings
        for (Sighting s : dao.getAllSightings()) {
            dao.deleteSighting(s.getId());
        }
        // delete locations
        for (Location l : dao.getAllLocations()) {
            dao.deleteLocation(l.getId());
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
        powers.add(dao.createSuperpower(getSuperpower1()));
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
        powers.add(dao.createSuperpower(getSuperpower2()));
        h.setPowers(powers);
        // add org
        List<Organization> orgs = new ArrayList<>();
        orgs.add(dao.createOrganization(getOrganization1()));
        h.setOrganizations(orgs);

        return h;
    }

    @Test
    public void testCreateHero() {
        Hero h = getHero1();
        // add the first
        Hero r1 = dao.createHero(h);
        // confirm we only added one
        assertEquals(dao.getAllHeros().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(dao.getHeroById(r1.getId()), r1);

        Hero h2 = getHero2();
        // add the second
        Hero r2 = dao.createHero(h2);
        // confirm theres now two
        assertEquals(dao.getAllHeros().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(dao.getHeroById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteHero() {
        // create out heros
        Hero h = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // check we have two things
        assertEquals(dao.getAllHeros().size(), 2);
        // delete the first hero
        dao.deleteHero(h.getId());
        // confirm we removed something
        assertEquals(dao.getAllHeros().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(dao.getHeroById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(dao.getHeroById(h2.getId()), h2);
        // delete the second hero
        dao.deleteHero(h2.getId());
        // confirm theres nothing left
        assert (dao.getAllHeros().isEmpty());
        // double check that its not there
        assertEquals(dao.getHeroById(h2.getId()), null);

    }

    @Test
    public void testUpdateHero() {
        Hero h1 = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // check we add two things
        assertEquals(dao.getAllHeros().size(), 2);
        // update h1
        h1.setName("Lone-Star");
        Hero updated = dao.updateHero(h1.getId(), h1);
        // confirm that we returned the updated hero
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(dao.getHeroById(h1.getId()), h1);
        // confirm we didnt update any other heros
        Hero r2 = dao.getHeroById(h2.getId());
        // confirm second hero didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetHeroById() {
        Hero h1 = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // check we add two things
        assertEquals(dao.getAllHeros().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(dao.getHeroById(h1.getId()), h1);
        assertEquals(dao.getHeroById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllHeros() {
        Hero h1 = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // confirm that there are two things
        assertEquals(dao.getAllHeros().size(), 2);
    }

    //================================================================
    // Sighting methods
    //================================================================
    private Sighting getSighting1() {
        Sighting s = new Sighting();
        s.setDateAndTime(LocalDateTime.of(2018, 3, 18, 6, 5));
        Location l = dao.createLocation(getLocation1());
        s.setLocation(l);
        List<Hero> heros = new ArrayList<>();
        Hero h = dao.createHero(getHero1());
        heros.add(h);
        s.setHeros(heros);
        return s;
    }

    private Sighting getSighting2() {
        Sighting s = new Sighting();
        s.setDateAndTime(LocalDateTime.of(2017, 2, 17, 0, 30));
        Location l = dao.createLocation(getLocation2());
        s.setLocation(l);
        List<Hero> heros = new ArrayList<>();
        Hero h = dao.createHero(getHero2());
        heros.add(h);
        s.setHeros(heros);
        return s;
    }

    @Test
    public void testCreateSighting() {
        Sighting h = getSighting1();
        // add the first
        Sighting r1 = dao.createSighting(h);
        // confirm we only added one
        assertEquals(dao.getAllSightings().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(dao.getSightingById(r1.getId()), r1);

        Sighting h2 = getSighting2();
        // add the second
        Sighting r2 = dao.createSighting(h2);
        // confirm theres now two
        assertEquals(dao.getAllSightings().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(dao.getSightingById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteSighting() {
        // create out sightings
        Sighting h = dao.createSighting(getSighting1());
        Sighting h2 = dao.createSighting(getSighting2());
        // check we have two things
        assertEquals(dao.getAllSightings().size(), 2);
        // delete the first sighting
        dao.deleteSighting(h.getId());
        // confirm we removed something
        assertEquals(dao.getAllSightings().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(dao.getSightingById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(dao.getSightingById(h2.getId()), h2);
        // delete the second sighting
        dao.deleteSighting(h2.getId());
        // confirm theres nothing left
        assert (dao.getAllSightings().isEmpty());
        // double check that its not there
        assertEquals(dao.getSightingById(h2.getId()), null);

    }

    @Test
    public void testUpdateSighting() {
        Sighting h1 = dao.createSighting(getSighting1());
        Sighting h2 = dao.createSighting(getSighting2());
        // check we add two things
        assertEquals(dao.getAllSightings().size(), 2);
        // update h1
        List<Hero> heros = h1.getHeros();
        heros.add(dao.createHero(getHero2()));
        h1.setHeros(heros);
        Sighting updated = dao.updateSighting(h1.getId(), h1);
        // confirm that we returned the updated sighting
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(dao.getSightingById(h1.getId()), h1);
        // confirm we didnt update any other sightings
        Sighting r2 = dao.getSightingById(h2.getId());
        // confirm second sighting didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetSightingById() {
        Sighting h1 = dao.createSighting(getSighting1());
        Sighting h2 = dao.createSighting(getSighting2());
        // check we add two things
        assertEquals(dao.getAllSightings().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(dao.getSightingById(h1.getId()), h1);
        assertEquals(dao.getSightingById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllSightings() {
        Sighting h1 = dao.createSighting(getSighting1());
        Sighting h2 = dao.createSighting(getSighting2());
        // confirm that there are two things
        assertEquals(dao.getAllSightings().size(), 2);
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
        Location r1 = dao.createLocation(h);
        // confirm we only added one
        assertEquals(dao.getAllLocations().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(dao.getLocationById(r1.getId()), r1);

        Location h2 = getLocation2();
        // add the second
        Location r2 = dao.createLocation(h2);
        // confirm theres now two
        assertEquals(dao.getAllLocations().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(dao.getLocationById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteLocation() {
        // create out locations
        Location h = dao.createLocation(getLocation1());
        Location h2 = dao.createLocation(getLocation2());
        // check we have two things
        assertEquals(dao.getAllLocations().size(), 2);
        // delete the first location
        dao.deleteLocation(h.getId());
        // confirm we removed something
        assertEquals(dao.getAllLocations().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(dao.getLocationById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(dao.getLocationById(h2.getId()), h2);
        // delete the second location
        dao.deleteLocation(h2.getId());
        // confirm theres nothing left
        assert (dao.getAllLocations().isEmpty());
        // double check that its not there
        assertEquals(dao.getLocationById(h2.getId()), null);

    }

    @Test
    public void testUpdateLocation() {
        Location h1 = dao.createLocation(getLocation1());
        Location h2 = dao.createLocation(getLocation2());
        // check we add two things
        assertEquals(dao.getAllLocations().size(), 2);
        // update h1
        h1.setName("new location");
        Location updated = dao.updateLocation(h1.getId(), h1);
        // confirm that we returned the updated location
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(dao.getLocationById(h1.getId()), h1);
        // confirm we didnt update any other locations
        Location r2 = dao.getLocationById(h2.getId());
        // confirm second location didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetLocationById() {
        Location h1 = dao.createLocation(getLocation1());
        Location h2 = dao.createLocation(getLocation2());
        // check we add two things
        assertEquals(dao.getAllLocations().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(dao.getLocationById(h1.getId()), h1);
        assertEquals(dao.getLocationById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllLocations() {
        Location h1 = dao.createLocation(getLocation1());
        Location h2 = dao.createLocation(getLocation2());
        // confirm that there are two things
        assertEquals(dao.getAllLocations().size(), 2);
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
        Superpower r1 = dao.createSuperpower(h);
        // confirm we only added one
        assertEquals(dao.getAllSuperpowers().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(dao.getSuperpowerById(r1.getId()), r1);

        Superpower h2 = getSuperpower2();
        // add the second
        Superpower r2 = dao.createSuperpower(h2);
        // confirm theres now two
        assertEquals(dao.getAllSuperpowers().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(dao.getSuperpowerById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteSuperpower() {
        // create out superpowers
        Superpower h = dao.createSuperpower(getSuperpower1());
        Superpower h2 = dao.createSuperpower(getSuperpower2());
        // check we have two things
        assertEquals(dao.getAllSuperpowers().size(), 2);
        // delete the first superpower
        dao.deleteSuperpower(h.getId());
        // confirm we removed something
        assertEquals(dao.getAllSuperpowers().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(dao.getSuperpowerById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(dao.getSuperpowerById(h2.getId()), h2);
        // delete the second superpower
        dao.deleteSuperpower(h2.getId());
        // confirm theres nothing left
        assert (dao.getAllSuperpowers().isEmpty());
        // double check that its not there
        assertEquals(dao.getSuperpowerById(h2.getId()), null);

    }

    @Test
    public void testUpdateSuperpower() {
        Superpower h1 = dao.createSuperpower(getSuperpower1());
        Superpower h2 = dao.createSuperpower(getSuperpower2());
        // check we add two things
        assertEquals(dao.getAllSuperpowers().size(), 2);
        // update h1
        h1.setName("Gunslinger");
        Superpower updated = dao.updateSuperpower(h1.getId(), h1);
        // confirm that we returned the updated superpower
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(dao.getSuperpowerById(h1.getId()), h1);
        // confirm we didnt update any other superpowers
        Superpower r2 = dao.getSuperpowerById(h2.getId());
        // confirm second superpower didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetSuperpowerById() {
        Superpower h1 = dao.createSuperpower(getSuperpower1());
        Superpower h2 = dao.createSuperpower(getSuperpower2());
        // check we add two things
        assertEquals(dao.getAllSuperpowers().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(dao.getSuperpowerById(h1.getId()), h1);
        assertEquals(dao.getSuperpowerById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllSuperpowers() {
        Superpower h1 = dao.createSuperpower(getSuperpower1());
        Superpower h2 = dao.createSuperpower(getSuperpower2());
        // confirm that there are two things
        assertEquals(dao.getAllSuperpowers().size(), 2);
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
        Organization r1 = dao.createOrganization(h);
        // confirm we only added one
        assertEquals(dao.getAllOrganizations().size(), 1);
        // confirm return value of created
        assertEquals(r1, h);
        // confirm we put in what we think we put in
        assertEquals(dao.getOrganizationById(r1.getId()), r1);

        Organization h2 = getOrganization2();
        // add the second
        Organization r2 = dao.createOrganization(h2);
        // confirm theres now two
        assertEquals(dao.getAllOrganizations().size(), 2);
        // confirm we put in what we think we put in
        assertEquals(dao.getOrganizationById(h2.getId()), h2);
        // check for any aliasing
        assertNotEquals(r1, r2);
    }

    @Test
    public void testDeleteOrganization() {
        // create out organizations
        Organization h = dao.createOrganization(getOrganization1());
        Organization h2 = dao.createOrganization(getOrganization2());
        // check we have two things
        assertEquals(dao.getAllOrganizations().size(), 2);
        // delete the first organization
        dao.deleteOrganization(h.getId());
        // confirm we removed something
        assertEquals(dao.getAllOrganizations().size(), 1);
        // confrim we removed what we think we removed
        assertEquals(dao.getOrganizationById(h.getId()), null);
        // confirm we still have what we think we have
        assertEquals(dao.getOrganizationById(h2.getId()), h2);
        // delete the second organization
        dao.deleteOrganization(h2.getId());
        // confirm theres nothing left
        assert (dao.getAllOrganizations().isEmpty());
        // double check that its not there
        assertEquals(dao.getOrganizationById(h2.getId()), null);

    }

    @Test
    public void testUpdateOrganization() {
        Organization h1 = dao.createOrganization(getOrganization1());
        Organization h2 = dao.createOrganization(getOrganization2());
        // check we add two things
        assertEquals(dao.getAllOrganizations().size(), 2);
        // update h1
        h1.setName("HeroicOrg");
        Organization updated = dao.updateOrganization(h1.getId(), h1);
        // confirm that we returned the updated organization
        assertEquals(h1, updated);
        // confirm that we updated the databse
        assertEquals(dao.getOrganizationById(h1.getId()), h1);
        // confirm we didnt update any other organizations
        Organization r2 = dao.getOrganizationById(h2.getId());
        // confirm second organization didnt change
        assertEquals(r2, h2);

    }

    @Test
    public void testGetOrganizationById() {
        Organization h1 = dao.createOrganization(getOrganization1());
        Organization h2 = dao.createOrganization(getOrganization2());
        // check we add two things
        assertEquals(dao.getAllOrganizations().size(), 2);
        // confirm that we got what we were suppose to get
        assertEquals(dao.getOrganizationById(h1.getId()), h1);
        assertEquals(dao.getOrganizationById(h2.getId()), h2);
        // confrim we dont have the same thing
        assertNotEquals(h1, h2);
    }

    @Test
    public void testGetAllOrganizations() {
        Organization h1 = dao.createOrganization(getOrganization1());
        Organization h2 = dao.createOrganization(getOrganization2());
        // confirm that there are two things
        assertEquals(dao.getAllOrganizations().size(), 2);
    }

    //================================================================
    // Misc methods
    //================================================================
    @Test
    public void testGetHeroSuperpowers() {
        Hero h1 = dao.createHero(getHero1());
        List<Superpower> powers = dao.getHeroSuperpowers(h1.getId());
        assertEquals(powers.size(),1);
        assertEquals(h1.getPowers(),powers);
    }

    @Test
    public void testGetHeroOrganizations() {
        Hero h1 = dao.createHero(getHero1());
        List<Organization> organizations = dao.getHeroOrganizations(h1.getId());
        assertEquals(organizations.size(),0);
        assertEquals(h1.getOrganizations(),organizations);
    }

    @Test
    public void testGetSightingHeros() {
        Sighting s1 = dao.createSighting(getSighting1());
        List<Hero> heros = dao.getSightingHeros(s1.getId());
        assertEquals(heros.size(),1);
        assertEquals(s1.getHeros(),heros);
    }
}
