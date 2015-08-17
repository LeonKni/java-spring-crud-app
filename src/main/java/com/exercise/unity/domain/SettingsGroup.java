package com.exercise.unity.domain;

import java.io.Serializable;

/**
 * SettingsGroup.java.
 *
 * @author Leon K
 */
public class SettingsGroup implements Serializable {
    private static final long serialVersionUID = 1512418983261759562L;

    private int id;
    private String name;
    private SettingsSubGroup subGroupList = new SettingsSubGroup();

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

    public SettingsSubGroup getSubGroupList() {
        return subGroupList;
    }

    public void setSubGroupList(SettingsSubGroup subGroupList) {
        this.subGroupList = subGroupList;
    }
}
