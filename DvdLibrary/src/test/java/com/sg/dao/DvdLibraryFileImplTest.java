/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.Dvd;
import com.sg.model.Mpaar;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben Norman
 */
public class DvdLibraryFileImplTest {

    public DvdLibraryFileImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    DvdLibraryFileImpl dao = new DvdLibraryFileImpl();

    @Before
    public void setUp() {
        try {
            Random random = new Random(1993);
            Dvd nd = new Dvd("A");
            nd.setReleaseDate(LocalDate.now());
            nd.setRating(Mpaar.G);
            nd.setDirectorsName("B");
            nd.setStudio("C");
            nd.setNote("D");
            dao.addDvd(nd.getTitle(), nd);
            for (int i = 0; i < 100; i++) {
                Dvd dvd = new Dvd("Title" + i);
                dvd.setDirectorsName("Director" + i);
                dvd.setReleaseDate(LocalDate.now().minusYears(random.nextInt(100)).minusMonths(random.nextInt(12)).minusWeeks(random.nextInt(52)));
                if(dvd.getReleaseDate()==null){
                    fail("Release date is null");
                }
                dvd.setRating(Mpaar.values()[random.nextInt(Mpaar.values().length-1) + 1]);
                dvd.setStudio("Studio" + i);
                int len = random.nextInt(25)+1;
                for (int j = 0; j < len; j++) {
                    dvd.setNote(dvd.getNote() + i);
                }
                dao.addDvd(dvd.getTitle(), dvd);
            }
        } catch (DvdLibraryException ex) {
            fail();
        }
    }

    @After
    public void tearDown() {
        try {
            for (String name : dao.listTitles()) {
                dao.removeDvd(name);
            }
        } catch (DvdLibraryException ex) {
            fail();
        }
    }

    /**
     * Test of addDvd method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testAddDvd() throws Exception {
        // title0 was added in setup
        // check that its there
        assertNotNull(dao.getDvd("Title0"));
    }

    /**
     * Test of removeDvd method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testRemoveDvd() throws Exception {
        // confirm we removed something
        assertNotNull(dao.removeDvd("Title0"));
        // confirm its not there
        assertNull(dao.getDvd("Title0"));
    }

    /**
     * Test of editDvd method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testEditDvd() throws Exception {
        // edit note
        Dvd dvd = dao.getDvd("Title0");
        dvd.setNote("A NEW NOTE");
        dao.editDvd("Title0", dvd);
        // confirm edit
        assertEquals(dao.getDvd("Title0").getNote(), "A NEW NOTE");
    }

    /**
     * Test of listDvds method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testListDvds() throws Exception {
        // setup adds 101 dvds
        assertTrue(dao.listDvds().size() == 101);
    }

    /**
     * Test of getDvd method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetDvd() throws Exception {
        // setup adds Title0
        assertNotNull(dao.getDvd("Title0"));
    }

    /**
     * Test of listTitles method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testListTitles() throws Exception {
        // setup adds 101 dvds
        assertTrue(dao.listTitles().size() == 101);
    }

    /**
     * Test of getDvdReleasedInLast method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetDvdReleasedInLast() throws Exception {
        // clear
        for (String name : dao.listTitles()) {
            dao.removeDvd(name);
        }
        
        Dvd newDvd = getDvd("A",LocalDate.now());
        Dvd oldDvd = getDvd("B",LocalDate.of(2018-9, Month.MARCH, 1));
        Dvd olderDvd = getDvd("C",LocalDate.of(2018-49, Month.MARCH, 1));
        Dvd oldestDvd = getDvd("D",LocalDate.of(2018-99, Month.MARCH, 1));
        dao.addDvd("A", newDvd);
        dao.addDvd("B", oldDvd);
        dao.addDvd("C", olderDvd);
        dao.addDvd("D", oldestDvd);
        assertEquals(dao.getDvdReleasedInLast(1).size(),1);
        assertEquals(dao.getDvdReleasedInLast(10).size(),2);
        assertEquals(dao.getDvdReleasedInLast(50).size(),3);
        assertEquals(dao.getDvdReleasedInLast(100).size(),4);
    }

    /**
     * Test of getDvdWithRating method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetDvdWithRating() throws Exception {
        // setup should generate at least one of each rating
        assertTrue(dao.getDvdWithRating(Mpaar.G).size() > 0);
        assertTrue(dao.getDvdWithRating(Mpaar.PG).size() > 0);
        assertTrue(dao.getDvdWithRating(Mpaar.PG13).size() > 0);
        assertTrue(dao.getDvdWithRating(Mpaar.NC17).size() > 0);
        assertTrue(dao.getDvdWithRating(Mpaar.NRUR).size() > 0);
    }

    /**
     * Test of getDvdsWithDirector method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetDvdsWithDirector() throws Exception {
        // dvd with Director0 generated in startup
        assertTrue(dao.getDvdsWithDirector("Director0").size() == 1);
    }

    /**
     * Test of getDvdWithStudio method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetDvdWithStudio() throws Exception {
        // dvd with Studio0 generated in startup
        assertTrue(dao.getDvdWithStudio("Studio0").size() == 1);
    }

    /**
     * Test of getAverageAge method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetAverageAge() throws Exception {
        for (String name : dao.listTitles()) {
            dao.removeDvd(name);
        }
        
        Dvd newDvd = getDvd("A",LocalDate.now());
        Dvd oldDvd = getDvd("B",LocalDate.of(2018-9, Month.MARCH, 1));
        Dvd olderDvd = getDvd("C",LocalDate.of(2018-49, Month.MARCH, 1));
        Dvd oldestDvd = getDvd("D",LocalDate.of(2018-99, Month.MARCH, 1));
        // 0 + 9 + 49 + 99
        dao.addDvd("A", newDvd);
        dao.addDvd("B", oldDvd);
        dao.addDvd("C", olderDvd);
        dao.addDvd("D", oldestDvd);
        assertEquals(dao.getAverageAge(),(0+9+49+99)/4);
    }

    /**
     * Test of getNewestDvds method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetNewestDvds() throws Exception {
        // dvd generated with todays date in setup
       assertTrue(dao.getNewestDvds().getReleaseDate().isEqual(LocalDate.now()));
    }

    /**
     * Test of getOldestDvds method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetOldestDvds() throws Exception {
        for (String name : dao.listTitles()) {
            dao.removeDvd(name);
        }
        
        Dvd newDvd = getDvd("A",LocalDate.now());
        Dvd oldDvd = getDvd("B",LocalDate.of(2018-9, Month.MARCH, 1));
        Dvd olderDvd = getDvd("C",LocalDate.of(2018-49, Month.MARCH, 1));
        Dvd oldestDvd = getDvd("D",LocalDate.of(2018-99, Month.MARCH, 1));
        dao.addDvd("A", newDvd);
        dao.addDvd("B", oldDvd);
        dao.addDvd("C", olderDvd);
        dao.addDvd("D", oldestDvd);
        assertEquals(dao.getOldestDvds(),oldestDvd);
    }

    /**
     * Test of getAverageNoteLength method, of class DvdLibraryFileImpl.
     */
    @Test
    public void testGetAverageNoteLength() throws Exception {
        for (String name : dao.listTitles()) {
            dao.removeDvd(name);
        }
        
        dao.addDvd("A", getDvd("A","1"));
        dao.addDvd("B", getDvd("B","Tu"));
        dao.addDvd("C", getDvd("C","Tre"));
        dao.addDvd("D", getDvd("D","Four"));
        assertEquals(dao.getAverageNoteLength(),(1+2+3+4)/4);
    }

    private Dvd getDvd(String name, String note){
        Dvd d = new Dvd(name);
        d.setDirectorsName("null");
        d.setStudio("null");
        d.setRating(Mpaar.G);
        d.setReleaseDate(LocalDate.now());
        d.setNote(note);
        return d;
    }
    
    private Dvd getDvd(String name, LocalDate date){
        Dvd d = new Dvd(name);
        d.setDirectorsName("null");
        d.setStudio("null");
        d.setRating(Mpaar.G);
        d.setReleaseDate(date);
        d.setNote("null");
        return d;
    }
}
