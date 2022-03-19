package com.zak.domain.model;


import com.zak.domain.enums.EUserRole;

import java.util.Objects;

public class UserRole {

    private Integer id;

    private EUserRole name;

    private String label;

    public UserRole() {

    }

    public UserRole(EUserRole name) {
        this.name = name;
    }
    public UserRole(EUserRole name, String label) {
        this.name = name;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EUserRole getName() {
        return name;
    }

    public void setName(EUserRole name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) &&
                name == userRole.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
