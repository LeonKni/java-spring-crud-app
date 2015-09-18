package com.crud.app.dao;

import com.crud.app.dao.impl.SettingsDAOImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author Leon K
 */
public class SettingsDAOImplTest {
    private SettingsDAOImpl settingsDAO = new SettingsDAOImpl();

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    /**
     * Test FindAll
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testFindAll() throws SQLException, ClassNotFoundException {
//        Collection<Setting> settings = this.settingsDAO.findAll();
//        Assert.assertTrue(settings.size() > 0);
    }

    /**
     * Test FindBy
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testFindByKey() throws SQLException, ClassNotFoundException {
//        Setting setting = this.settingsDAO.findBy("maxEnemyCount");
//        Assert.assertTrue(setting != null);
    }

    /**
     * Test Insert
     */
    @Test
    public void testInsert() {

    }

    /**
     * Test Update
     */
    @Test
    public void testUpdate() {

    }

    /**
     * Test Delete
     */
    @Test
    public void testDelete() {

    }
}
