package com.crud.app.service;

import com.crud.app.domain.Group;
import com.crud.app.domain.SubGroup;
import com.crud.app.model.SettingModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SettingsServiceTest.java.
 *
 * @author Leon K
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class SettingsServiceTest {
    @Autowired
    private SettingsService settingsService;
    private List<SettingModel> testModelList = new ArrayList<SettingModel>();

    @Before
    public void setUp() {
        SettingModel testModel = new SettingModel();
        for (int i = 0; i < 15; i++) {

        }
        testModel.setSettingKey("testKey");
        testModel.setSettingValue("testValue");
        this.testModelList.add(testModel);
    }

    @After
    public void tearDown() {

    }

    /**
     * Test create setting
     */
    @Test
    public void testCreateSettings() throws SQLException, ClassNotFoundException {
        this.settingsService.createSettings(this.testModelList);
    }

    /**
     * Test create group
     */
    @Test
    public void testCreateGroups() {

    }

    /**
     * Test create sub-group
     */
    @Test
    public void testCreateSubGroups() {

    }

    /**
     * Test get settings
     */
    @Test
    public void getSettings() throws SQLException, ClassNotFoundException {
        List<SettingModel> settingModelList = this.settingsService.getSettings();
        Assert.notNull(settingModelList);
    }

    /**
     * Test get settings by group name
     */
    @Test
    public void getSettingsByGroup() throws SQLException, ClassNotFoundException {
        List<SettingModel> settingModelList = this.settingsService.getSettingsByGroup("test");
        Assert.notNull(settingModelList);
    }

    /**
     * Test get all groups
     */
    @Test
    public void getGroups() throws SQLException, ClassNotFoundException {
        List<Group> groupList = this.settingsService.getGroups();
        Assert.notNull(groupList);
    }

    /**
     * Test get all sub-groups
     */
    @Test
    public void getSubGroups() throws SQLException, ClassNotFoundException {
        List<SubGroup> subGroupList = this.settingsService.getSubGroups();
        Assert.notNull(subGroupList);
    }

    /**
     * Test delete group
     */
    @Test
    public void testDeleteGroup() throws SQLException, ClassNotFoundException {
        List<String> groupNameList = new ArrayList<String>();
        groupNameList.add("test");
        this.settingsService.deleteGroup(groupNameList);
    }

    /**
     * Test delete setting
     */
    @Test
    public void testDeleteSetting() throws SQLException, ClassNotFoundException {
        List<String> settingKey = new ArrayList<String>();
        settingKey.add("test");
        this.settingsService.deleteGroup(settingKey);
    }

    /**
     * Test delete sub-group
     */
    @Test
    public void testDeleteSubGroup() throws SQLException, ClassNotFoundException {
        List<String> subGroupNameList = new ArrayList<String>();
        subGroupNameList.add("test");
        this.settingsService.deleteSubGroup(subGroupNameList);
    }
}
