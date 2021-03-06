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
import com.sg.herosightings.model.TableObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ben Norman
 */
public class HeroDaoDBImpl implements HeroDao {

    // hero specifc queries
    private static final String CREATE_HERO = "INSERT INTO hero (`name`, description) VALUES (?,?)";
    private static final String DELETE_HERO = "DELETE FROM hero WHERE heroId = ?";
    private static final String UPDATE_HERO = "UPDATE hero SET `name`=?, description=? WHERE heroId = ?";
    private static final String GET_HERO_BY_ID = "SELECT * FROM hero WHERE heroId = ?";
    private static final String GET_HEROS = "SELECT * FROM hero";

    // sighting specifc queries
    private static final String CREATE_SIGHTING = "INSERT INTO sighting (locationId, dateAndTime) VALUES(?,?)";
    private static final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
    private static final String UPDATE_SIGHTING = "UPDATE sighting SET locationId=?, dateAndTime=? WHERE sightingId = ?";
    private static final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
    private static final String GET_SIGHTINGS = "SELECT * FROM sighting";

    // location specifc queries
    private static final String CREATE_LOCATION = "INSERT INTO location (`name`, description, address, latitude, longitude) VALUES(?,?,?,?,?)";
    private static final String DELETE_LOCATION = "DELETE FROM location WHERE locationId = ?";
    private static final String UPDATE_LOCATION = "UPDATE location SET `name`=?, description=?, address=?, latitude=?, longitude=? WHERE locationId = ?";
    private static final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE locationId = ?";
    private static final String GET_LOCATIONS = "SELECT * FROM location";

    // superpower specifc queries
    private static final String CREATE_SUPERPOWER = "INSERT INTO superpower (`name`, description) VALUES(?,?)";
    private static final String DELETE_SUPERPOWER = "DELETE FROM superpower WHERE superpowerId = ?";
    private static final String UPDATE_SUPERPOWER = "UPDATE superpower SET `name`=?, description=? WHERE superpowerId = ?";
    private static final String GET_SUPERPOWER_BY_ID = "SELECT * FROM superpower WHERE superpowerId = ?";
    private static final String GET_SUPERPOWERS = "SELECT * FROM superpower";

    // organization specifc queries
    private static final String CREATE_ORGANIZATION = "INSERT INTO organization (`name`, description, address, phoneNumber, email) VALUES(?,?,?,?,?)";
    private static final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE organizationId = ?";
    private static final String UPDATE_ORGANIZATION = "UPDATE organization SET `name`=?, description=?, address=?, phoneNumber=?, email=? WHERE organizationId = ?";
    private static final String GET_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE organizationId = ?";
    private static final String GET_ORGANIZATIONS = "SELECT * FROM organization";

    // misc
    private static final String GET_LAST_INSERT_ID = "select LAST_INSERT_ID()";
    private static final String GET_SUPEPOWERS_OF_HERO = "SELECT s.* FROM herosuperpower AS hs JOIN superpower AS s ON s.superpowerId = hs.superpowerId WHERE hs.heroId=?";
    private static final String GET_ORGANIZAIONS_OF_HERO = "SELECT o.* FROM heroorganization AS ho JOIN organization AS o ON ho.organizationId = o.organizationId WHERE heroId=?";
    private static final String GET_HEROS_FROM_SIGHTING = "SELECT h.* FROM herosighting AS hs JOIN hero AS h ON h.heroId = hs.heroId WHERE sightingId = ?";
    private static final String GET_SIGHTINGS_WITH_HERO = "SELECT s.* FROM herosighting AS hs JOIN sighting AS s ON s.sightingId = hs.sightingId WHERE heroId = ?";
    private static final String GET_MOST_RECENT_SIGHTINGS = "SELECT s.* FROM sighting AS s ORDER BY dateAndTime DESC LIMIT ?";
    
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    //================================================================
    // Hero methods
    //================================================================
    @Override
    @Transactional
    public Hero createHero(Hero hero) {
        // create hero
        template.update(CREATE_HERO, hero.getName(), hero.getDescription());
        // set the herosID
        int heroId = template.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        hero.setId(heroId);
        // reset bridge entries
        replacePairsInBridge(heroId, hero.getPowers(), "herosuperpower", "heroId", "superpowerId");
        replacePairsInBridge(heroId, hero.getOrganizations(), "heroorganization", "heroId", "organizationId");
        return hero;
    }
    
    @Override
    @Transactional
    public Hero deleteHero(int heroId) {
        Hero h = getHeroById(heroId);
        deletePairsFromBridge(heroId, "herosuperpower", "heroId");
        deletePairsFromBridge(heroId, "heroorganization", "heroId");
        deletePairsFromBridge(heroId, "herosighting", "heroId");
        template.update(DELETE_HERO, heroId);
        return h;
    }

    @Override
    @Transactional
    public Hero updateHero(int heroId, Hero updatedHero) {
        template.update(UPDATE_HERO, updatedHero.getName(), updatedHero.getDescription(), heroId);
        replacePairsInBridge(heroId, updatedHero.getPowers(), "herosuperpower", "heroId", "superpowerId");
        replacePairsInBridge(heroId, updatedHero.getOrganizations(), "heroorganization", "heroId", "organizationId");
        updatedHero.setId(heroId);
        return updatedHero;
    }

    @Override
    public Hero getHeroById(int heroId) {
        try {
            Hero h = template.queryForObject(GET_HERO_BY_ID, new HeroMapper(), heroId);
            setHeroPowersAndOrgs(h);
            return h;
        } catch (EmptyResultDataAccessException e) {
            // if theres was no result we want to return null;
        }
        return null;
    }

    @Override
    public List<Hero> getAllHeros() {
        List<Hero> heros = template.query(GET_HEROS, new HeroMapper());
        setHeroPowersAndOrgs(heros);
        return heros;
    }

    @Override
    public List<Superpower> getHeroSuperpowers(int heroId) {
        return template.query(GET_SUPEPOWERS_OF_HERO, new SuperpowerMapper(), heroId);
    }

    @Override
    public List<Organization> getHeroOrganizations(int heroId) {
        return template.query(GET_ORGANIZAIONS_OF_HERO, new OrganizationMapper(), heroId);
    }

    private void setHeroPowersAndOrgs(List<Hero> heros) {
        for (Hero h : heros) {
            setHeroPowersAndOrgs(h);
        }
    }

    private void setHeroPowersAndOrgs(Hero h) {
        h.setPowers(getHeroSuperpowers(h.getId()));
        h.setOrganizations(getHeroOrganizations(h.getId()));
    }

    private final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException {
            Hero h = new Hero();
            h.setId(rs.getInt("heroId"));
            h.setName(rs.getString("name"));
            h.setDescription(rs.getString("description"));
            return h;
        }
    }

    //================================================================
    // Sighting methods
    //================================================================
    @Override
    @Transactional
    public Sighting createSighting(Sighting sighting) {
        template.update(CREATE_SIGHTING, sighting.getLocation().getId(), java.sql.Timestamp.valueOf(sighting.getDateAndTime()));
        int sightingId = template.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        sighting.setId(sightingId);
        replacePairsInBridge(sightingId, sighting.getHeros(), "herosighting", "sightingId", "heroId");
        return sighting;
    }

    @Override
    @Transactional
    public Sighting deleteSighting(int sightingId) {
        Sighting s = getSightingById(sightingId);
        deletePairsFromBridge(sightingId, "herosighting", "sightingId");
        template.update(DELETE_SIGHTING, sightingId);
        return s;
    }

    @Override
    public Sighting updateSighting(int sightingId, Sighting updatedSighting) {
        template.update(UPDATE_SIGHTING, updatedSighting.getLocation().getId(), java.sql.Timestamp.valueOf(updatedSighting.getDateAndTime()), sightingId);
        replacePairsInBridge(sightingId, updatedSighting.getHeros(), "herosighting", "sightingId", "heroId");
        updatedSighting.setId(sightingId);
        return updatedSighting;
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        try {
            Sighting s = template.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), sightingId);
            s.setHeros(getSightingHeros(s.getId()));
            return s;
        } catch (EmptyResultDataAccessException e) {
            // if theres was no result we want to return null;
        }
        return null;
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = template.query(GET_SIGHTINGS, new SightingMapper());
        setSightingHeros(sightings);
        return sightings;
    }

    @Override
    public List<Hero> getSightingHeros(int sightingId) {
        List<Hero> heros = template.query(GET_HEROS_FROM_SIGHTING, new HeroMapper(), sightingId);
        setHeroPowersAndOrgs(heros);
        return heros;
    }
    
//    @Override
//    public List<Sighting> getSightingWithHero(int heroId){
//        List<Sighting> sightings = template.query(GET_SIGHTINGS_WITH_HERO, new SightingMapper(),heroId);
//        setSightingHeros(sightings);
//        return sightings;
//    }

    @Override
    public List<Sighting> getMostRecentSightings(int count) {
        List<Sighting> sightings = template.query(GET_MOST_RECENT_SIGHTINGS, new SightingMapper(), count);
        setSightingHeros(sightings);
        return sightings;
    }

    private void setSightingHeros(List<Sighting> sightings) {
        for (Sighting s : sightings) {
            s.setHeros(getSightingHeros(s.getId()));
        }
    }

    private final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting s = new Sighting();
            s.setId(rs.getInt("sightingId"));
            s.setLocation(getLocationById(rs.getInt("locationId")));
            s.setDateAndTime(rs.getTimestamp("dateAndTime").toLocalDateTime());
            return s;
        }

    }

    //================================================================
    // Location methods
    //================================================================
    @Override
    @Transactional
    public Location createLocation(Location location) {
        template.update(CREATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        int locationId = template.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        location.setId(locationId);
        return location;
    }

    @Override
    @Transactional
    public Location deleteLocation(int locationId) {
        Location l = getLocationById(locationId);
        List<Sighting> sightings = template.query("SELECT * FROM sighting WHERE locationId = "+locationId, new SightingMapper());
        for(Sighting s : sightings){
            deleteSighting(s.getId());
        }
        template.update(DELETE_LOCATION, locationId);
        return l;
    }

    @Override
    public Location updateLocation(int locationId, Location updatedLocation) {
        template.update(UPDATE_LOCATION,
                updatedLocation.getName(),
                updatedLocation.getDescription(),
                updatedLocation.getAddress(),
                updatedLocation.getLatitude(),
                updatedLocation.getLongitude(),
                locationId);
        updatedLocation.setId(locationId);
        return updatedLocation;
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            return template.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), locationId);
        } catch (EmptyResultDataAccessException e) {
            // if theres was no result we want to return null;
        }
        return null;
    }

    @Override
    public List<Location> getAllLocations() {
        return template.query(GET_LOCATIONS, new LocationMapper());
    }

    private final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location l = new Location();
            l.setId(rs.getInt("locationId"));
            l.setName(rs.getString("name"));
            l.setDescription(rs.getString("description"));
            l.setAddress(rs.getString("address"));
            l.setLatitude(rs.getBigDecimal("latitude"));
            l.setLongitude(rs.getBigDecimal("longitude"));
            return l;
        }

    }

    //================================================================
    // Superpower methods
    //================================================================
    @Override
    @Transactional
    public Superpower createSuperpower(Superpower superpower) {
        template.update(CREATE_SUPERPOWER, superpower.getName(), superpower.getDescription());
        int superpowerId = template.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        superpower.setId(superpowerId);
        return superpower;
    }

    @Override
    @Transactional
    public Superpower deleteSuperpower(int superpowerId) {
        Superpower s = getSuperpowerById(superpowerId);
        deletePairsFromBridge(superpowerId, "herosuperpower", "superpowerId");
        template.update(DELETE_SUPERPOWER, superpowerId);
        return s;
    }

    @Override
    public Superpower updateSuperpower(int superpowerId, Superpower updatedSuperpower) {
        template.update(UPDATE_SUPERPOWER, updatedSuperpower.getName(), updatedSuperpower.getDescription(), superpowerId);
        updatedSuperpower.setId(superpowerId);
        return updatedSuperpower;
    }

    @Override
    public Superpower getSuperpowerById(int superpowerId) {
        try {
            return template.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), superpowerId);
        } catch (EmptyResultDataAccessException e) {
            // if theres was no result we want to return null;
        }
        return null;
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        return template.query(GET_SUPERPOWERS, new SuperpowerMapper());
    }

    private final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int i) throws SQLException {
            Superpower sp = new Superpower();
            sp.setId(rs.getInt("superpowerId"));
            sp.setName(rs.getString("name"));
            sp.setDescription(rs.getString("description"));
            return sp;
        }

    }

    //================================================================
    // Organization methods
    //================================================================
    @Override
    @Transactional
    public Organization createOrganization(Organization organization) {
        template.update(CREATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhoneNumber(),
                organization.getEmail());
        int organizationId = template.queryForObject(GET_LAST_INSERT_ID, Integer.class);
        organization.setId(organizationId);
        return organization;
    }

    @Override
    @Transactional
    public Organization deleteOrganization(int organizationId) {
        Organization org = getOrganizationById(organizationId);
        deletePairsFromBridge(organizationId, "heroorganization", "organizationId");
        template.update(DELETE_ORGANIZATION, organizationId);
        return org;
    }

    @Override
    public Organization updateOrganization(int organizationId, Organization updatedOrganization) {
        template.update(UPDATE_ORGANIZATION,
                updatedOrganization.getName(),
                updatedOrganization.getDescription(),
                updatedOrganization.getAddress(),
                updatedOrganization.getPhoneNumber(),
                updatedOrganization.getEmail(),
                organizationId);
        updatedOrganization.setId(organizationId);
        return updatedOrganization;
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            return template.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), organizationId);
        } catch (EmptyResultDataAccessException e) {
            // if theres was no result we want to return null;
        }
        return null;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return template.query(GET_ORGANIZATIONS, new OrganizationMapper());
    }

    private final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("organizationId"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setEmail(rs.getString("email"));
            org.setPhoneNumber(rs.getString("phoneNumber"));
            return org;
        }

    }

    //================================================================
    // Helper methods
    //================================================================
    @Transactional
    private <T extends TableObject> void replacePairsInBridge(int id, List<T> objects, String tableName, String columnA, String columnB) {
        if (objects.isEmpty()) {
            return;
        }
        // delete old entries if any
        deletePairsFromBridge(id, tableName, columnA);
        // add all new entries
        addPairsToBridge(id, objects, tableName, columnA, columnB);
    }

    /**
     * THIS METHOD ASSUMES INPUTS ARE SANITIZED
     * 
     * This method is a helper for bridge tables that only consist of two
     * foreign keys such as HeroSuperpower, HeroOrganization, and HeroSighting.
     * This method is for inserting pairs into the bridge table that do not
     * already exist. If you wanted to replace all pairs with new pairs use
     * {@link #replacePairsInBridge(int, java.util.List, java.lang.String, java.lang.String, java.lang.String)}
     *
     * @param <T> a Table object
     * @param id the id you want to find in the table, this will be in columnA
     * @param objects the objects to pair with the id
     * @param tableName the name of the bridge table
     * @param columnA the name of the first column
     * @param columnB the name of the second column
     */
    private <T extends TableObject> void addPairsToBridge(int id, List<T> objects, String tableName, String columnA, String columnB) {
        if (objects.isEmpty()) {
            return;
        }
        // sanitize strings
        
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO ");
        insertQuery.append(tableName);
        insertQuery.append(" (");
        insertQuery.append(columnA);
        insertQuery.append(",");
        insertQuery.append(columnB);
        insertQuery.append(")");
        insertQuery.append(generateValuesSQL(id,objects));

        // add all new entries
        template.update(insertQuery.toString());
    }

    /**
     * Generates a VALUES(a,b),(c,d)... etc... SQL statement
     *
     * @param id the first id to insert
     * @param objects a list of table objects to pair with the id
     * @return the sql string
     */
    private <T extends TableObject> String generateValuesSQL(int id, List<T> objects) {
        if(objects.isEmpty()){
            throw new IllegalArgumentException("objects cannot be empty");
        }
        StringBuilder values = new StringBuilder();
        values.append("VALUES");
        for (TableObject t : objects) {
            values.append("(");
            values.append(id);
            values.append(",");
            values.append(t.getId());
            values.append(")");
            values.append(",");
        }
        // strip last comma if needed
        if (values.charAt(values.length() - 1) == ',') {
            values.deleteCharAt(values.length() - 1);
        }
        return values.toString();
    }

    /**
     * THIS METHOD ASSUMES INPUTS ARE SANITIZED
     * 
     * Removes all parings in a simple bridge table that have the associated id
     *
     * @param id the id to remove
     * @param tableName the name of the table to delete from
     * @param columnName the column name where the id would be located
     */
    private void deletePairsFromBridge(int id, String tableName, String columnName) {
        StringBuilder deleteQuery = new StringBuilder();
        deleteQuery.append("DELETE FROM ");
        deleteQuery.append(tableName);
        deleteQuery.append(" WHERE ");
        deleteQuery.append(columnName);
        deleteQuery.append(" = ");
        deleteQuery.append(id);

        template.update(deleteQuery.toString());
    }
}
