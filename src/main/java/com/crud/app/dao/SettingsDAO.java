package com.crud.app.dao;

import com.crud.app.domain.Group;
import com.crud.app.domain.SubGroup;
import com.crud.app.model.SettingModel;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * @author Leon K
 */
public interface SettingsDAO {
    /**
     * @return Collection of Settings
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Collection<SettingModel> findAll() throws SQLException, ClassNotFoundException;

    /**
     * @param key .
     * @return SettingModel
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Collection<SettingModel> findBy(String key) throws SQLException, ClassNotFoundException;

    /**
     * @param settingModelList .
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void insert(List<SettingModel> settingModelList) throws SQLException, ClassNotFoundException;

    /**
     * @param groupList .
     * @throws SQLException
     * @throws ClassCastException
     */
    void insertGroup(List<Group> groupList) throws SQLException, ClassCastException, ClassNotFoundException;

    void insertSubGroup(List<SubGroup> subGroupList) throws SQLException, ClassCastException, ClassNotFoundException;

    /**
     * @param settingKeyList .
     */
    void delete(List<String> settingKeyList) throws SQLException, ClassNotFoundException;

    /**
     * @param groupNameList .
     */
    void deleteGroup(List<String> groupNameList) throws SQLException, ClassNotFoundException;

    void deleteSubGroup(List<String> nameList) throws SQLException, ClassNotFoundException;

    void deleteSubGroupByGroupName(List<String> groupNameList) throws SQLException, ClassNotFoundException;
}
