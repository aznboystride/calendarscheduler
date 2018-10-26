package controllers;

import database.UserAccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import models.UserAccount;
import models.alerts.AlertBox;
import java.io.IOException;
import java.sql.SQLException;

public class ChangePasswordViewController {

    private UserAccount userAccount;

    @FXML
    private PasswordField oldPass;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField confirmPass;

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @FXML
    public void update(ActionEvent event) throws IOException, SQLException {
        if(oldPass.getText().length() == 0 || newPass.getText().length() == 0 || confirmPass.getText().length() == 0) {
            new AlertBox("You left some field empty");
        } else if(!UserAccountManager.getInstance().checkUserExists(userAccount.getUserName(), oldPass.getText())) {
            new AlertBox("Wrong password");
        } else if(!newPass.getText().equals(confirmPass.getText())) {
            new AlertBox("Password does not match");
        } else {
            userAccount.setPassword(newPass.getText());
            try {
                UserAccountManager.getInstance().updateUser(userAccount);
                System.out.println("Successfully modified: ");
                System.out.println(userAccount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CalendarView.fxml"));
            Parent parent = loader.load();
            CalendarViewController controller = loader.getController();
            controller.setUserAccount(userAccount);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Calendar App - Calendar View");
            window.setScene(new Scene(parent));
        }
    }
}
