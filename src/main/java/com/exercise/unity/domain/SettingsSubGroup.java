package com.exercise.unity.domain;

import java.io.Serializable;

/**
 * SettingsSubGroup.java.
 *
 * @author Leon K
 */
public class SettingsSubGroup implements Serializable {
    private static final long serialVersionUID = -8660224256895247392L;

    private int id;
    private String name;

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
}
