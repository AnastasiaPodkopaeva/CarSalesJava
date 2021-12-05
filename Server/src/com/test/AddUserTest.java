package com.test;
import com.SQLsupport.DBConnection;
import com.SQLsupport.strategies.updatable.AddUser;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AddUserTest {
    private AddUser addUser;
    private Connection connection;
    private String data;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Initial setup...");
        System.out.println("AddUserTests executes only once");
    }

    @Before
    public void setUp() throws SQLException {
        data = "test 1234 0 Test Case 1000 Minsk 80297094567";
        addUser = new AddUser();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/db7", "root", "1234");
    }

    @Test
    public void whenCreateNewUserThenReturnTrue(){
        addUser.getData(data);
        boolean result = addUser.executeUpdate(connection);
        assertTrue(result);
    }

    @Test
    public void whenCreateNewUserWithIncorrectConnection(){
        addUser.getData(data);
        boolean result = false;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db8", "root", "1234");
            result = addUser.executeUpdate(connection);
            assertTrue(result);
        } catch (SQLException e) {
            assertFalse(result);
        }
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Tests finished");
    }
}
