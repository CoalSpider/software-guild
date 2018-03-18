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

    HeroDao dao;

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
        for (Sighting s : dao.getAllSighting()) {
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

    private Hero getHero1(){
        Hero h = new Hero();
        // add name and desc
        h.setName("loneStar");
        h.setDescription("A gunslinging hero, a freelancer not part of any known organization");
        // add powers
        List<Superpower> powers = new ArrayList<>();
        Superpower p = new Superpower();
        p.setName("Sharpshooter");
        p.setDescription("Never misses a shot");
        powers.add(p);
        h.setPowers(powers);
        // create powers as they dont exist
        for(Superpower pwr : powers){
            dao.createSuperpower(pwr);
        }
        // add org
        h.setOrganizations(new ArrayList<>());
        
        return h;
    }
    
    private Hero getHero2(){
        Hero h = new Hero();
        // add name and desc
        h.setName("fumbledor");
        h.setDescription("A bumbling fool of a hero. Legend says hes never cast the same spell twice");
        // add powers
        List<Superpower> powers = new ArrayList<>();
        Superpower p = new Superpower();
        p.setName("Wild Magic");
        p.setDescription("Could do literally anything.");
        powers.add(p);
        h.setPowers(powers);
        // create powers as they dont exist
        for(Superpower pwr : powers){
            dao.createSuperpower(pwr);
        }
        // add org
        h.setOrganizations(new ArrayList<>());
        
        return h;
    }
    
    @Test
    public void testCreateHero() {
        Hero h = getHero1();
        Hero h2 = getHero2();
        // add the first
        Hero r1 = dao.createHero(h);
        // confirm we only added one
        assertEquals(dao.getAllHeros().size(),1);
        // add the second
        Hero r2 = dao.createHero(h2);
        // confirm theres now two
        assertEquals(dao.getAllHeros().size(),2);
        // confirm the return value of create hero is as expected
        assertEquals(r1, h);
        assertEquals(r2,h2);
        // check for any aliasing
        assertNotEquals(r1,r2);
        // confirm we put in what we think we put in
        assertEquals(dao.getHeroById(h.getId()), h);
        assertEquals(dao.getHeroById(h2.getId()), h2);
    }

    @Test
    public void testDeleteHero() {
        // create out heros
        Hero h = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // check we have two things
        assertEquals(dao.getAllHeros().size(),2);
        // delete the first hero
        dao.deleteHero(h.getId());
        // confirm we removed something
        assertEquals(dao.getAllHeros().size(),1);
        // confrim we removed what we think we removed
        assertEquals(dao.getHeroById(h.getId()),null);
        // confirm we still have what we think we have
        assertEquals(dao.getHeroById(h2.getId()),h2);
        // delete the second hero
        dao.deleteHero(h2.getId());
        // confirm theres nothing left
        assert(dao.getAllHeros().isEmpty());
        // double check that its not there
        assertEquals(dao.getHeroById(h2.getId()),null);
        
    }

    @Test
    public void testUpdateHero() {
        Hero h1 = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // check we add two things
        assertEquals(dao.getAllHeros().size(),2);
        // update h1
        h1.setName("Lone-Star");
        Hero updated = dao.updateHero(h1.getId(), h1);
        // confirm that we returned the updated hero
        assertEquals(h1,updated);
        // confirm that we updated the databse
        assertEquals(dao.getHeroById(h1.getId()),h1);
        // confirm we didnt update any other heros
        Hero r2 = dao.getHeroById(h2.getId());
        // confirm second hero didnt change
        assertEquals(r2,h2);
        
    }

    @Test
    public void testGetHeroById() {
        Hero h1 = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // check we add two things
        assertEquals(dao.getAllHeros().size(),2);
        // confirm that we got what we were suppose to get
        assertEquals(dao.getHeroById(h1.getId()),h1);
        assertEquals(dao.getHeroById(h2.getId()),h2);
        // confrim we dont have the same thing
        assertNotEquals(h1,h2);
    }

    @Test
    public void testGetAllHeros() {
        Hero h1 = dao.createHero(getHero1());
        Hero h2 = dao.createHero(getHero2());
        // confirm we got two things
        assertEquals(dao.getAllHeros().size(),2);
    }

    /**
     * Test of createSighting method, of class HeroDaoDBImpl.
     */
    @Test
    public void testCreateSighting() {
    }

    /**
     * Test of deleteSighting method, of class HeroDaoDBImpl.
     */
    @Test
    public void testDeleteSighting() {
    }

    /**
     * Test of updateSighting method, of class HeroDaoDBImpl.
     */
    @Test
    public void testUpdateSighting() {
    }

    /**
     * Test of getSightingById method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGetSightingById() {
    }

    /**
     * Test of getAllSighting method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGetAllSighting() {
    }

    /**
     * Test of createLocation method, of class HeroDaoDBImpl.
     */
    @Test
    public void testCreateLocation() {
    }

    /**
     * Test of deleteLocation method, of class HeroDaoDBImpl.
     */
    @Test
    public void testDeleteLocation() {
    }

    /**
     * Test of updateLocation method, of class HeroDaoDBImpl.
     */
    @Test
    public void testUpdateLocation() {
    }

    /**
     * Test of getLocationById method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGetLocationById() {
    }

    /**
     * Test of getAllLocations method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGetAllLocations() {
    }

    /**
     * Test of createSuperpower method, of class HeroDaoDBImpl.
     */
    @Test
    public void testCreateSuperpower() {
    }

    /**
     * Test of deleteSuperpower method, of class HeroDaoDBImpl.
     */
    @Test
    public void testDeleteSuperpower() {
    }

    /**
     * Test of updateSuperpower method, of class HeroDaoDBImpl.
     */
    @Test
    public void testUpdateSuperpower() {
    }

    /**
     * Test of geSuperpowerById method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGeSuperpowerById() {
    }

    /**
     * Test of getAllSuperpowers method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGetAllSuperpowers() {
    }

    /**
     * Test of createOrganization method, of class HeroDaoDBImpl.
     */
    @Test
    public void testCreateOrganization() {
    }

    /**
     * Test of deleteOrganization method, of class HeroDaoDBImpl.
     */
    @Test
    public void testDeleteOrganization() {
    }

    /**
     * Test of updateOrganization method, of class HeroDaoDBImpl.
     */
    @Test
    public void testUpdateOrganization() {
    }

    /**
     * Test of getOrganizationById method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGetOrganizationById() {
    }

    /**
     * Test of getAllOrganizations method, of class HeroDaoDBImpl.
     */
    @Test
    public void testGetAllOrganizations() {
    }

}
