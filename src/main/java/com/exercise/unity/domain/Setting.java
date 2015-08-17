package com.exercise.unity.domain;

import java.io.Serializable;

/**
 * Setting.java.
 *
 * @author Leon K
 */
public class Setting implements Serializable {
    private static final long serialVersionUID = -5537825463775042905L;

    private int settingsGroupId;
    private int settingsSubGroupId;

    public int getSettingsGroupId() {
        return settingsGroupId;
    }

    public void setSettingsGroupId(int settingsGroupId) {
        this.settingsGroupId = settingsGroupId;
    }

}
