/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.UserAccountManager;
import javafx.event.ActionEvent;
import models.UserAccount;
import models.alerts.AlertBox;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ModifyAccountViewController implements Initializable {

    private UserAccount userAccount;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField genderField;

    @FXML
    private DatePicker dobField;

    @FXML
    private Button makeChangeBtn;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Button calendarViewBtn;

    @FXML
    private Label labelField;

    @FXML
    private TextField emailField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelField.setText("");
        makeChangeBtn.setOnAction(event -> {
            if(!checkEmptyField()) {
                new AlertBox("You left some field empty");
            } else if(!genderField.getText().equals("M") && !genderField.getText().equals("F")) {
                new AlertBox("Pick M / F for Gender");
            } else {
                userAccount.setFirstName(firstnameField.getText());
                userAccount.setLastName(lastnameField.getText());
                userAccount.setUserName(usernameField.getText());
                userAccount.setEmail(emailField.getText());
                userAccount.setDateOfBirth(Date.valueOf(dobField.getValue()));
                userAccount.setGender(genderField.getText().charAt(0));
                labelField.setText("Changes Made Successfully!");
                try {
                    UserAccountManager.getInstance().updateUser(userAccount);
                    System.out.println("Successfully modified: ");
                    System.out.println(userAccount);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        mainMenuBtn.setOnAction(event -> {

        });
        calendarViewBtn.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CalendarView.fxml"));
            Parent calendarView = null;
            try {
                calendarView = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CalendarViewController calendarViewController = fxmlLoader.getController();
            calendarViewController.setUserAccount(userAccount);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Calendar App - Calendar View");
            stage.setScene(new Scene(calendarView));
        });
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
        if(dobField.getValue() == null)
            return false;
        if(genderField.getText().length() == 0)
            return false;
        return true;
    }

    public void setField(UserAccount userAccount) {
        this.userAccount = userAccount;
        firstnameField.setPromptText("first name");
        firstnameField.setText(userAccount.getFirstName());
        lastnameField.setPromptText("last name");
        lastnameField.setText(userAccount.getLastName());
        usernameField.setPromptText("username");
        usernameField.setText(userAccount.getUserName());
        emailField.setPromptText("email");
        emailField.setText(userAccount.getEmail());
        genderField.setPromptText("M/F");
        genderField.setText(String.valueOf(userAccount.getGender()));
        dobField.setPromptText("mm/dd/yyyy");
        dobField.setValue(userAccount.getDateOfBirth().toLocalDate());
    }

    @FXML
    public void changePasswordBtnPressed(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChangePasswordView.fxml"));
        Parent parent = fxmlLoader.load();
        ChangePasswordViewController changePasswordViewController = fxmlLoader.getController();
        changePasswordViewController.setUserAccount(userAccount);
        window.setTitle("Calender App - Change Password");
        window.setScene(new Scene(parent));
    }
}
