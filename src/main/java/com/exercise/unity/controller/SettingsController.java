package com.exercise.unity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.exercise.unity.domain.Setting;
import com.exercise.unity.model.SettingModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ResponseEntity<Object> getSettings() throws Exception {
        SettingModel model = new SettingModel();
        model.setKey("minEnemyCount");
        model.setValue("5");
        model.setGroupName("difficulty");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("retObj",model);
        return new ResponseEntity<Object>(model,HttpStatus.OK);
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
