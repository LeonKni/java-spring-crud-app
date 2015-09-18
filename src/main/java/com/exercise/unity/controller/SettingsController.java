package com.exercise.unity.controller;

import com.exercise.unity.domain.Group;
import com.exercise.unity.domain.SubGroup;
import com.exercise.unity.model.SettingModel;
import com.exercise.unity.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

/**
 * SettingsController.java.
 *
 * @author Leon K
 */
@RestController
@RequestMapping("settings")
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

    /**
     * PUT
     *
     * @param settingModelList to create
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> createSetting(@RequestBody List<SettingModel> settingModelList)
            throws SQLException, ClassNotFoundException {
        this.settingsService.createSettings(settingModelList);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    /**
     * PUT
     *
     * @param groupList to create
     * @return ResponseEntity
     */
    @RequestMapping(value = "/groups", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> createGroup(@RequestBody List<Group> groupList)
            throws SQLException, ClassNotFoundException {
        this.settingsService.createGroups(groupList);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    /**
     * PUT
     *
     * @param subGroupList to create
     * @return ResponseEntity
     */
    @RequestMapping(value = "/subGroups", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> createSubGroup(@RequestBody List<SubGroup> subGroupList)
            throws SQLException, ClassNotFoundException {
        this.settingsService.createSubGroups(subGroupList);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    /**
     * GET
     *
     * @return ResponseEntity - List of Settings
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SettingModel>> getSettings() throws Exception {
        List<SettingModel> settingModelList = this.settingsService.getSettings();
        return new ResponseEntity<List<SettingModel>>(settingModelList, HttpStatus.OK);
    }

    /**
     * GET
     *
     * @param groupName to search by.
     * @return ResponseEntity - Setting
     * @throws Exception
     */
    @RequestMapping(value = "/{groupName}", method = RequestMethod.GET)
    public ResponseEntity<List<SettingModel>> getSettings(@PathVariable("groupName") String groupName) throws Exception {
        List<SettingModel> settingModelList = this.settingsService.getSettingsByGroup(groupName);
        return new ResponseEntity<List<SettingModel>>(settingModelList, HttpStatus.OK);
    }

    /**
     * GET
     *
     * @return ResponseEntity - Group
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> getGroups() throws SQLException, ClassNotFoundException {
        List<Group> groups = this.settingsService.getGroups();
        return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
    }

    /**
     * GET
     *
     * @return ResponseEntity - SubGroup
     */
    @RequestMapping(value = "/subGroups", method = RequestMethod.GET)
    public ResponseEntity<List<SubGroup>> getSubGroups() throws SQLException, ClassNotFoundException {
        List<SubGroup> subGroups = this.settingsService.getSubGroups();
        return new ResponseEntity<List<SubGroup>>(subGroups, HttpStatus.OK);
    }

    /**
     * DELETE
     *
     * @param idList to delete.
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteSetting(@RequestBody List<String> idList) throws SQLException, ClassNotFoundException {
        this.settingsService.deleteSetting(idList);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    /**
     * DELETE
     *
     * @param idList to delete.
     * @return ResponseEntity
     */
    @RequestMapping(value = "/groups", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteGroup(@RequestBody List<String> idList) throws SQLException, ClassNotFoundException {
        this.settingsService.deleteGroup(idList);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    /**
     * DELETE
     *
     * @param idList to delete.
     * @return ResponseEntity
     */
    @RequestMapping(value = "/subGroups", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteSubGroup(@RequestBody List<String> idList) throws SQLException, ClassNotFoundException {
        this.settingsService.deleteSubGroup(idList);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
