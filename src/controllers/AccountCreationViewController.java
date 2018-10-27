package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.UserAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import database.UserAccountManager;
import java.io.IOException;
import java.sql.Date;
import models.alerts.AlertBox;

public class AccountCreationViewController {

    private UserAccount userAccount;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private DatePicker dob;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    public void createUserBtn(ActionEvent Event) throws IOException {
        if(!checkEmptyField()) {
            new AlertBox("You left some field empty");
        } else if(!passwordField.getText().equals(confirmPasswordField.getText())) {
            new AlertBox("Password Doesn't Match");
        } else if(!genderField.getText().toUpperCase().equals("M") && !genderField.getText().toUpperCase().equals("F")) {
            new AlertBox("Pick M / F for Gender");
        } else {
            userAccount = new UserAccount(usernameField.getText(), firstnameField.getText(),
                lastnameField.getText(), emailField.getText(), genderField.getText().charAt(0),
                Date.valueOf(dob.getValue()), passwordField.getText());
            UserAccountManager userAccountManager = UserAccountManager.getInstance();
            userAccountManager.insert(userAccount);

            Stage stage = (Stage) ((Node)Event.getSource()).getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/views/MainMenuView.fxml")));
            stage.setTitle("Calendar App - Main Menu");
            stage.setScene(scene);
        }
    }

    private boolean checkEmptyField() {
        if(firstnameField.getText().length() == 0)
            return false;
        if(lastnameField.getText().length() == 0)
            return false;
        if(usernameField.getText().length() == 0)
            return false;
        if(emailField.getText().length() == 0)
            return false;
        if(dob.getValue() == null)
            return false;
        if(genderField.getText().length() == 0)
            return false;
        if(passwordField.getText().length() == 0)
            return false;
        if(confirmPasswordField.getText().length() == 0)
            return false;
        return true;
    }
}
