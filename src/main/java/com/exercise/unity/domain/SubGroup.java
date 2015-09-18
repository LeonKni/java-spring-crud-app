package com.exercise.unity.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Leon K
 */
public class SubGroup implements Serializable {
    @NotNull
    private String name;
    @NotNull
    private String groupName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
