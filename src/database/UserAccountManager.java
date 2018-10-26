package database;

import models.UserAccount;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserAccountManager
{
    private static final UserAccountManager INSTANCE = new UserAccountManager();

    public static UserAccountManager getInstance()
    {
        return INSTANCE;
    }

    private Connection connection;

    private UserAccountManager()
    {
        try
        {
            connection = ConnectionManager.GetConnection();
        }
        catch(Exception e)
        {
            System.out.println("UserAccountManager cannot establish connection");
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public void insert(UserAccount user)
    {
        PreparedStatement preparedStatement = null;
        String query = "insert into UserAccount VALUES (?,?,?,?,?,?,?)";
        if(connection != null)
        {
            try
            {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getFirstName());
                preparedStatement.setString(4, user.getLastName());
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setString(6, String.valueOf(user.getGender()));
                preparedStatement.setDate(7, user.getDateOfBirth());
                preparedStatement.executeUpdate();
                System.out.println("Successfully inserted UserAccount: ");
                System.out.println(user);
            }
            catch(SQLException e)
            {
                System.out.println("Fail to insert user");
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public UserAccount getUser(String username) throws SQLException {
        String user, pass, fname, lname, email;
        char gender;
        Date dob;
        PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM " +
                "userAccount WHERE " +
                "userAccount.username = '%s'", username)
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()) {
            System.out.println("USER NOT FOUND");
        } else {
            user = resultSet.getString("username");
            pass = resultSet.getString("password");
            fname = resultSet.getString("firstname");
            lname = resultSet.getString("lastname");
            email = resultSet.getString("email");
            gender = resultSet.getString("gender").charAt(0);
            dob = resultSet.getDate("dob");
            return new UserAccount(user, fname, lname, email, gender, dob, pass);
        }
        return null;
    }

    public boolean checkUserExists(String username, String password) throws java.sql.SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(String.format("SELECT * FROM " +
                "userAccount WHERE " +
                "userAccount.username = '%s' AND userAccount.password = '%s'", username, password)
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public void updateUser(UserAccount userAccount) throws java.sql.SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE userAccount " +
                        "SET firstname = ?, lastname = ?, gender = ?, email = ?, dob = ?, " +
                        "password = ? " +
                        "WHERE userAccount.username = ?"
        );
        preparedStatement.setString(1, userAccount.getFirstName());
        preparedStatement.setString(2, userAccount.getLastName());
        preparedStatement.setString(3, Character.toString(userAccount.getGender()));
        preparedStatement.setString(4, userAccount.getEmail());
        preparedStatement.setDate(5, userAccount.getDateOfBirth());
        preparedStatement.setString(6, userAccount.getPassword());
        preparedStatement.setString(7, userAccount.getUserName());
        preparedStatement.executeUpdate();
    }
}
