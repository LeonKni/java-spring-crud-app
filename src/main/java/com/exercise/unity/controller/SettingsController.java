package com.exercise.unity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SettingsController.java.
 *
 * @author Leon K
 */
@RestController
@RequestMapping("/settings")
public class SettingsController {
    /**
     * POST
     *
     * @param groupName Name of group to create.
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> createGroup(@PathVariable("groupName") String groupName) {
        //TODO:  set difficulty
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getSettings() throws Exception {
//        SettingModel model = new SettingModel();
//        model.setKey("minEnemyCount");
//        model.setValue("5");
//        model.setGroupName("difficulty");
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("retObj",model);
//        return new ResponseEntity<Object>(model,HttpStatus.OK);
        return "test";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> updateSetting(@PathVariable("id") String id) {
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteSetting(@PathVariable("id") String id) {
        return null;
    }
}
