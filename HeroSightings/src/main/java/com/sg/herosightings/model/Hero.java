/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.herosightings.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ben Norman
 */
public class Hero implements TableObject{
    private int id;
    @NotNull
    @Size(min=1,max=60, message="name should be between 1 - 60 characters")
    private String name;
    @NotNull
    @Size(min=1,max=500, message="description should be between 1 - 500 characters")
    private String description;
    @Valid
    private List<Superpower> powers = new ArrayList<>();
    @Valid
    private List<Organization> organizations = new ArrayList<>();

    public Hero() {
        // default
    }

    @Override
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

    public List<Superpower> getPowers() {
        return powers;
    }

    public void setPowers(List<Superpower> powers) {
        this.powers = powers;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.powers);
        hash = 41 * hash + Objects.hashCode(this.organizations);
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
        final Hero other = (Hero) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.powers, other.powers)) {
            return false;
        }
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        return true;
    }

}
