package com.crud.app.service;

import com.crud.app.dao.impl.SettingsDAOImpl;
import com.crud.app.domain.Group;
import com.crud.app.domain.SubGroup;
import com.crud.app.model.SettingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * SettingsService.java.
 *
 * @author Leon K
 */
@Service
public class SettingsService {
    @Autowired
    private SettingsDAOImpl settingsDAO;

    public void createSettings(List<SettingModel> settingModelList) throws SQLException, ClassNotFoundException {
        this.settingsDAO.insert(settingModelList);
    }

    public void createGroups(List<Group> groupList) throws SQLException, ClassNotFoundException {
        this.settingsDAO.insertGroup(groupList);
    }

    public void createSubGroups(List<SubGroup> subGroupList) throws SQLException, ClassNotFoundException {
        this.settingsDAO.insertSubGroup(subGroupList);
    }

    public List<SettingModel> getSettings() throws SQLException, ClassNotFoundException {
        return this.settingsDAO.findAll();
    }

    public List<SettingModel> getSettingsByGroup(String groupName) throws SQLException, ClassNotFoundException {
        return this.settingsDAO.findBy(groupName);
    }

    public List<Group> getGroups() throws SQLException, ClassNotFoundException {
        return this.settingsDAO.findAllGroups();
    }

    public List<SubGroup> getSubGroups() throws SQLException, ClassNotFoundException {
        return this.settingsDAO.findAllSubGroups();
    }

    @Transactional
    public void deleteGroup(List<String> groupNameList) throws SQLException, ClassNotFoundException {
        //delete sub-groups belonging to group being deleted
        this.settingsDAO.deleteSubGroupByGroupName(groupNameList);
        this.settingsDAO.deleteGroup(groupNameList);
    }

    @Transactional
    public void deleteSetting(List<String> idList) throws SQLException, ClassNotFoundException {
        this.settingsDAO.delete(idList);
    }

    @Transactional
    public void deleteSubGroup(List<String> idList) throws SQLException, ClassNotFoundException {
        this.settingsDAO.deleteSubGroup(idList);
    }
}
