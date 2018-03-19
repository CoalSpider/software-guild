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
import java.time.Instant;
import java.time.LocalDateTime;
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
    private static final String GET_SUPEPOWERS_OF_HERO = "SELECT s.* FROM herosuperpower AS hs JOIN superpower AS s ON s.superpowerId = hs.superpowerId WHERE hs.heroId=?";
    private static final String GET_ORGANIZAIONS_OF_HERO = "SELECT o.* FROM heroorganization AS ho JOIN organization AS o ON ho.organizationId = o.organizationId WHERE heroId=?";
    private static final String GET_HEROS_FROM_SIGHTING = "SELECT h.heroId, h.name, h.description FROM herosighting AS hs JOIN hero AS h ON h.heroId = hs.heroId WHERE sightingId = ?";

    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    // WHY DONT WE DO THIS? ASK KYLE DURING CODE REVIEW
    // REASONS I KNOW OF: SLOWER
    private <T> List<T> getAll(String tableName, RowMapper<T> rowMapper) {
        return template.query("SELECT * FROM " + tableName, rowMapper);
    }

    private <T> List<T> getOne(String tableName, String idName, int id, RowMapper<T> rowMapper) {
        return template.query("SELECT * FROM " + tableName + " WHERE " + idName + " = " + id, rowMapper);
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
        int heroId = template.queryForObject("select LAST_INSERT_ID()", Integer.class);
        hero.setId(heroId);
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
            return template.queryForObject(GET_HERO_BY_ID, new HeroMapper(), heroId);
        } catch (EmptyResultDataAccessException e) {
            // if theres was no result we want to return null;
        }
        return null;
    }

    @Override
    public List<Hero> getAllHeros() {
        return template.query(GET_HEROS, new HeroMapper());
//       return getAll("hero",new HeroMapper());
    }

    // TODO: make public part of interface
    private List<Superpower> getHeroSuperpowers(int heroId) {
        return template.query(GET_SUPEPOWERS_OF_HERO, new SuperpowerMapper(), heroId);
    }

    // TODO: make public part of interface
    private List<Organization> getHeroOrganizations(int heroId) {
        return template.query(GET_ORGANIZAIONS_OF_HERO, new OrganizationMapper(), heroId);
    }

    private final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException {
            Hero h = new Hero();
            h.setId(rs.getInt("heroId"));
            h.setName(rs.getString("name"));
            h.setDescription(rs.getString("description"));
            h.setPowers(getHeroSuperpowers(h.getId()));
            h.setOrganizations(getHeroOrganizations(h.getId()));
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
        int sightingId = template.queryForObject("select LAST_INSERT_ID()", Integer.class);
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
            return template.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), sightingId);
        } catch (EmptyResultDataAccessException e) {
            // if theres was no result we want to return null;
        }
        return null;
    }

    @Override
    public List<Sighting> getAllSightings() {
        return template.query(GET_SIGHTINGS, new SightingMapper());
    }

    private List<Hero> getSightingHeros(int sightingId) {
        return template.query(GET_HEROS_FROM_SIGHTING, new HeroMapper(), sightingId);
    }

    private final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting s = new Sighting();
            s.setId(rs.getInt("sightingId"));
            s.setLocation(getLocationById(rs.getInt("locationId")));
            s.setHeros(getSightingHeros(s.getId()));
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
        int locationId = template.queryForObject("select LAST_INSERT_ID()", Integer.class);
        location.setId(locationId);
        return location;
    }

    @Override
    @Transactional
    public Location deleteLocation(int locationId) {
        Location l = getLocationById(locationId);
        template.update("DELETE FROM sighting WHERE locationId=" + locationId);
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
        int superpowerId = template.queryForObject("select LAST_INSERT_ID()", Integer.class);
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
        int organizationId = template.queryForObject("select LAST_INSERT_ID()", Integer.class);
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
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO ");
        insertQuery.append(tableName);
        insertQuery.append(" (");
        insertQuery.append(columnA);
        insertQuery.append(",");
        insertQuery.append(columnB);
        insertQuery.append(") VALUES");
        for (TableObject t : objects) {
            insertQuery.append("(");
            insertQuery.append(id);
            insertQuery.append(",");
            insertQuery.append(t.getId());
            insertQuery.append(")");
            insertQuery.append(",");
        }
        // strip last comma if needed
        if (insertQuery.charAt(insertQuery.length() - 1) == ',') {
            insertQuery.deleteCharAt(insertQuery.length() - 1);
        }
        // add all new entries
        template.update(insertQuery.toString());
    }

    /**
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
