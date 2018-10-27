package controllers;

import database.UserAccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserAccount;

public class MainMenuViewController {

    private UserAccount userAccount;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label userLabel;

    @FXML
    private Label passLabel;

    public void createAccount(ActionEvent event) throws java.io.IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AccountCreationView.fxml")));
        window.setScene(scene);
        window.setTitle("Calendar App - Create Account");
    }

    public void login(ActionEvent event) throws java.io.IOException, java.sql.SQLException {

        if(username.getText().length() == 0) {
            userLabel.setText("Please Enter Username");
            return;
        }
        if(password.getText().length() == 0) {
            passLabel.setText("Please Enter Password");
            return;
        }

        if(checkUserExists(username.getText(), password.getText())) {
            userAccount = retrieveUserAccount();
            System.out.println("RECEIVED ACCOUNT: ");
            System.out.println(userAccount);
        } else {
            userLabel.setText("Invalid username or password");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CalendarView.fxml"));
        Parent parent = fxmlLoader.load();
        CalendarViewController calendarViewController = fxmlLoader.getController();
        calendarViewController.setUserAccount(userAccount);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.setTitle("Calendar App - Calendar View");
    }

    private UserAccount retrieveUserAccount() throws java.sql.SQLException {
        return UserAccountManager.getInstance().getUser(username.getText());
    }

    private boolean checkUserExists(String username, String password) throws java.sql.SQLException {
        return UserAccountManager.getInstance().checkUserExists(username, password);
    }
}
