/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author Ben Norman
 */
public class Location{
    private int id;
    @NotNull
    @Size(min=1,max=50, message="name should be between 1 - 50 characters")
    private String name;
    @NotNull
    @Size(min=1,max=500, message="description should be between 1 - 500 characters")
    private String description;
    @NotNull
    @Size(min=1,max=100, message="address should be between 1 - 100 characters")
    private String address;
    @NotNull
    @Range(min=-90,max=90, message="latitude must be between -90 and 90 degrees")
    private BigDecimal latitude;
    @NotNull
    @Range(min=-180,max=180, message="longitude must be between -180 and 180 degrees")
    private BigDecimal longitude;

    public Location() {
        // default
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + Objects.hashCode(this.address);
        hash = 23 * hash + Objects.hashCode(this.latitude);
        hash = 23 * hash + Objects.hashCode(this.longitude);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

}
