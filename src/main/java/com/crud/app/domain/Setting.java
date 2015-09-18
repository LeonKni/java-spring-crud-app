package com.crud.app.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Setting.java.
 *
 * @author Leon K
 */
public class Setting implements Serializable {
    private static final long serialVersionUID = -5537825463775042905L;
    @NotEmpty
    @Pattern(regexp = "a-zA-Z0-9")
    private String settingKey;
    private String settingValue;

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }
}
