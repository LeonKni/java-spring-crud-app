package com.crud.app.dao.impl;

import com.crud.app.dao.SettingsDAO;
import com.crud.app.domain.Group;
import com.crud.app.domain.SubGroup;
import com.crud.app.model.SettingModel;
import com.crud.app.util.DatabaseConnectionUtil;
import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Leon K
 */
@Repository
public class SettingsDAOImpl implements SettingsDAO {
    private Connection connection;

    //SQL//
    private final String FIND_ALL_SETTINGS_STATEMENT = "" +
            "SELECT s.setting_key, s.setting_value, sg.group_name, ssg.sub_group_name\n" +
            "FROM setting s\n" +
            "LEFT OUTER JOIN setting_group sg on s.setting_key = sg.setting_key\n" +
            "LEFT OUTER JOIN setting_sub_group ssg on sg.setting_key = ssg.setting_key;";
    private final String FIND_SETTINGS_BY_GROUP = "" +
            "SELECT s.setting_key, s.setting_value, sg.group_name, ssg.sub_group_name\n" +
            "FROM setting s\n" +
            "JOIN setting_group sg on s.setting_key = sg.setting_key\n" +
            "JOIN setting_sub_group ssg on sg.setting_key = ssg.setting_key " +
            "WHERE sg.group_name = ?;";
    private final String FIND_ALL_GROUPS_STATEMENT = "SELECT group_name FROM setting_group; \n";
    private final String FIND_ALL_SUB_GROUPS_STATEMENT = "SELECT sub_group_name, group_name FROM setting_sub_group; \n";
    private final String FIND_SUB_GROUPS_STATEMENT = "SELECT sub_group_name, group_name FROM setting_sub_group WHERE group_name = ?; \n";
    private final String INSERT_SETTING_STATEMENT = "" +
            "INSERT INTO setting(setting_key, setting_value) " +
            "VALUES(?, ?) ON DUPLICATE KEY UPDATE setting_value = ?;";
    private final String UPDATE_GROUP_STATEMENT = "" +
            "UPDATE setting_group " +
            "SET setting_key = ? " +
            "WHERE group_name = ?;";
    private final String UPDATE_SUB_GROUP_STATEMENT = "" +
            "UPDATE setting_sub_group " +
            "SET setting_key = ? " +
            "WHERE sub_group_name = ?;";
    private final String INSERT_GROUP_STATEMENT = "INSERT INTO setting_group(group_name) VALUES(?);";
    private final String INSERT_SUB_GROUP_STATEMENT = "INSERT INTO setting_sub_group(sub_group_name,group_name) " +
            "VALUES(?,?);";
    private final String UPDATE_SETTING_STATEMENT = "UPDATE setting SET setting_key, setting_value" +
            "WHERE setting_key = ?";


    /**
     * @return List of Settings.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<SettingModel> findAll() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SettingModel> settingList = new ArrayList<SettingModel>();
        SettingModel setting;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            preparedStatement = this.connection.prepareStatement(this.FIND_ALL_SETTINGS_STATEMENT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setting = new SettingModel();
                setting.setSettingKey(resultSet.getString("setting_key"));
                setting.setSettingValue(resultSet.getString("setting_value"));
                setting.setGroupName(resultSet.getString("group_name"));
                setting.setSubGroupName(resultSet.getString("sub_group_name"));
                settingList.add(setting);
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
        return settingList;
    }

    /**
     * @return List of Groups.
     */
    public List<Group> findAllGroups() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Group> groups = new ArrayList<Group>();
        Group group;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            preparedStatement = this.connection.prepareStatement(this.FIND_ALL_GROUPS_STATEMENT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                group = new Group();
                group.setName(resultSet.getString("group_name"));
                group.setSubGroups(findSubGroups(group.getName()));
                groups.add(group);
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
        return groups;
    }

    /**
     * @param groupName to search.
     * @return List of sub-groups.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<SubGroup> findSubGroups(String groupName) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SubGroup> subGroups = new ArrayList<SubGroup>();
        SubGroup subGroup;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            preparedStatement = this.connection.prepareStatement(this.FIND_SUB_GROUPS_STATEMENT);
            preparedStatement.setString(1, groupName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subGroup = new SubGroup();
                subGroup.setName(resultSet.getString("sub_group_name"));
                subGroup.setGroupName(resultSet.getString("group_name"));
                subGroups.add(subGroup);
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
        return subGroups;
    }

    /**
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<SubGroup> findAllSubGroups() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SubGroup> subGroups = new ArrayList<SubGroup>();
        SubGroup subGroup;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            preparedStatement = this.connection.prepareStatement(this.FIND_ALL_SUB_GROUPS_STATEMENT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subGroup = new SubGroup();
                subGroup.setName(resultSet.getString("sub_group_name"));
                subGroup.setGroupName(resultSet.getString("group_name"));
                subGroups.add(subGroup);
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
        return subGroups;
    }

    /**
     * @param groupName .
     * @return Setting
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<SettingModel> findBy(String groupName) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SettingModel> settingList = new ArrayList<SettingModel>();
        SettingModel settingModel;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            preparedStatement = this.connection.prepareStatement(this.FIND_SETTINGS_BY_GROUP);
            preparedStatement.setString(1, groupName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                settingModel = new SettingModel();
                settingModel.setSettingKey(resultSet.getString("setting_key"));
                settingModel.setSettingValue(resultSet.getString("setting_value"));
                settingModel.setGroupName(resultSet.getString("group_name"));
                settingModel.setSubGroupName(resultSet.getString("sub_group_name"));
                settingList.add(settingModel);
            }
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
        return settingList;
    }

    /**
     * @param settingModelList .
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public void insert(List<SettingModel> settingModelList) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            connection.setAutoCommit(false);

            //Insert or Update Settings
            preparedStatement = this.connection.prepareStatement(this.INSERT_SETTING_STATEMENT);
            for (SettingModel settingModel : settingModelList) {
                preparedStatement.setString(1, settingModel.getSettingKey());
                preparedStatement.setString(2, settingModel.getSettingValue());
                preparedStatement.setString(3, settingModel.getSettingValue());
                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
                connection.commit();
            }
            //Update Groups
            preparedStatement = this.connection.prepareStatement(this.UPDATE_GROUP_STATEMENT);
            for (SettingModel settingModel : settingModelList) {
                preparedStatement.setString(1, settingModel.getSettingKey());
                preparedStatement.setString(2, settingModel.getGroupName());
                preparedStatement.executeUpdate();
                connection.commit();
            }
            //Update Sub-Groups
            preparedStatement = this.connection.prepareStatement(this.UPDATE_SUB_GROUP_STATEMENT);
            for (SettingModel settingModel : settingModelList) {
                preparedStatement.setString(1, settingModel.getSettingKey());
                preparedStatement.setString(2, settingModel.getSubGroupName());
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } finally {
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
    }

    /**
     * @param groupList .
     * @throws SQLException
     * @throws ClassCastException
     */
    @Override
    public void insertGroup(List<Group> groupList) throws SQLException, ClassCastException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            connection.setAutoCommit(false);
            //Insert or Update Groups
            preparedStatement = this.connection.prepareStatement(this.INSERT_GROUP_STATEMENT);
            for (Group group : groupList) {
                preparedStatement.setString(1, group.getName());
                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } finally {
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
    }

    /**
     * @param subGroupList .
     * @throws SQLException
     * @throws ClassCastException
     */
    @Override
    public void insertSubGroup(List<SubGroup> subGroupList) throws SQLException, ClassCastException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        try {
            this.connection = DatabaseConnectionUtil.connect();
            connection.setAutoCommit(false);
            //Insert or Update Sub-Groups
            preparedStatement = this.connection.prepareStatement(this.INSERT_SUB_GROUP_STATEMENT);
            for (SubGroup subGroup : subGroupList) {
                preparedStatement.setString(1, subGroup.getName());
                preparedStatement.setString(2, subGroup.getGroupName());
                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } finally {
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
    }

    /**
     * @param settingKeyList to delete.
     */
    @Override
    public void delete(List<String> settingKeyList) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        final StringBuilder DELETE_SETTING_STATEMENT = new StringBuilder("DELETE FROM setting WHERE setting_key IN ");
        try {
            this.connection = DatabaseConnectionUtil.connect();
            connection.setAutoCommit(false);
            DELETE_SETTING_STATEMENT.append("(");
            //build query params
            for (int i = 0; i < settingKeyList.size(); i++) {
                DELETE_SETTING_STATEMENT.append("?");
                if (i < settingKeyList.size() - 1) {
                    DELETE_SETTING_STATEMENT.append(",");
                }
            }
            DELETE_SETTING_STATEMENT.append(")");
            preparedStatement = this.connection.prepareStatement(DELETE_SETTING_STATEMENT.toString());
            //set query param values
            for (int j = 0; j < settingKeyList.size(); j++) {
                preparedStatement.setString(j, settingKeyList.get(j - 1));
            }
            preparedStatement.addBatch();
            preparedStatement.executeQuery();
            connection.commit();
        } finally {
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
    }

    /**
     * @param groupNameList to delete.
     */
    @Override
    public void deleteGroup(List<String> groupNameList) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        final StringBuilder DELETE_GROUP_STATEMENT = new StringBuilder("DELETE FROM setting_group WHERE group_name IN ");
        try {
            this.connection = DatabaseConnectionUtil.connect();
            connection.setAutoCommit(false);
            DELETE_GROUP_STATEMENT.append("(");
            //build query params
            for (int i = 0; i < groupNameList.size(); i++) {
                DELETE_GROUP_STATEMENT.append("?");
                if (i < groupNameList.size() - 1) {
                    DELETE_GROUP_STATEMENT.append(",");
                }
            }
            DELETE_GROUP_STATEMENT.append(")");
            preparedStatement = this.connection.prepareStatement(DELETE_GROUP_STATEMENT.toString());
            //set query param values
            for (int k = 1; k <= groupNameList.size(); k++) {
                preparedStatement.setString(k, groupNameList.get(k - 1));
            }
            preparedStatement.addBatch();
            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
    }

    /**
     * @param nameList
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public void deleteSubGroup(List<String> nameList) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        final StringBuilder DELETE_GROUP_STATEMENT = new StringBuilder("DELETE FROM setting_sub_group WHERE sub_group_name IN ");
        try {
            this.connection = DatabaseConnectionUtil.connect();
            connection.setAutoCommit(false);
            DELETE_GROUP_STATEMENT.append("(");
            //build query params
            for (int i = 0; i < nameList.size(); i++) {
                DELETE_GROUP_STATEMENT.append("?");
                if (i < nameList.size() - 1) {
                    DELETE_GROUP_STATEMENT.append(",");
                }
            }
            DELETE_GROUP_STATEMENT.append(")");
            preparedStatement = this.connection.prepareStatement(DELETE_GROUP_STATEMENT.toString());
            //set query param values
            for (int k = 1; k <= nameList.size(); k++) {
                preparedStatement.setString(k, nameList.get(k - 1));
            }
            preparedStatement.addBatch();
            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
    }

    /**
     * @param groupNameList to delete.
     */
    @Override
    public void deleteSubGroupByGroupName(List<String> groupNameList) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        final StringBuilder DELETE_GROUP_STATEMENT = new StringBuilder("DELETE FROM setting_sub_group WHERE group_name IN ");
        try {
            this.connection = DatabaseConnectionUtil.connect();
            connection.setAutoCommit(false);
            DELETE_GROUP_STATEMENT.append("(");
            //build query params
            for (int i = 0; i < groupNameList.size(); i++) {
                DELETE_GROUP_STATEMENT.append("?");
                if (i < groupNameList.size() - 1) {
                    DELETE_GROUP_STATEMENT.append(",");
                }
            }
            DELETE_GROUP_STATEMENT.append(")");
            preparedStatement = this.connection.prepareStatement(DELETE_GROUP_STATEMENT.toString());
            //set query param values
            for (int k = 1; k <= groupNameList.size(); k++) {
                preparedStatement.setString(k, groupNameList.get(k - 1));
            }
            preparedStatement.addBatch();
            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            DbUtils.closeQuietly(this.connection);
            DbUtils.closeQuietly(preparedStatement);
        }
    }
}
