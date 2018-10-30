
package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.UserAccount;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CalendarViewController implements Initializable {

    private UserAccount userAccount;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private ComboBox yearDropDown;

    @FXML
    private ComboBox monthDropDown;

    @FXML
    private Label year;

    @FXML
    private Label month;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        year.setText(Integer.toString(LocalDate.now().getYear()));
        month.setText(new SimpleDateFormat("MMMM").format(new Date(System.currentTimeMillis())));
        initializeYearDropDown();
        initializeMonthDropDown();
        yearDropDown.setOnAction(event -> {
            year.setText(yearDropDown.getValue().toString());
            try {
                setCalendarRange(month.getText(), Integer.parseInt(year.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        monthDropDown.setOnAction(event -> {
            month.setText(monthDropDown.getValue().toString());
            try {
                setCalendarRange(month.getText(), Integer.parseInt(year.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        try {
            setCalendarRange(month.getText(), Integer.parseInt(year.getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Calendar View Initialized!");
    }

    private void setCalendarRange(String month, int year) throws ParseException {
        int lastDayOfMonth = getLastDayOfMonth(month, year);
        int firstDayOfWeek = getFirstDayOfWeek(month, year);
        int day = 1;
        clearCalendarRange();
        for(Node node: calendarGrid.getChildren()) {
            if(node instanceof Label) {
                if(GridPane.getRowIndex(node) == null || GridPane.getColumnIndex(node) == null) {
                    continue;
                }
                if(1 == GridPane.getRowIndex(node) && GridPane.getColumnIndex(node) == firstDayOfWeek - 1) {
                    ((Label) node).setText(String.valueOf(day++));
                } else if(1 <= GridPane.getRowIndex(node) && 1 < day && day <= lastDayOfMonth) {
                    ((Label) node).setText(String.valueOf(day++));
                }
            }
        }
    }
    
    private int getMonthFromString(String month) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(new SimpleDateFormat("MMMM").parse(month).getTime());
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    private int getFirstDayOfWeek(String month, int year) throws ParseException {
        int monthNum = getMonthFromString(month);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(Integer.toString(year) + "-" + Integer.toString(monthNum) + "-01").getTime());
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private int getLastDayOfMonth(String month, int year) throws ParseException {
        int monthNum = getMonthFromString(month);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(new SimpleDateFormat("yyyy-M-dd").parse(Integer.toString(year) + "-" + Integer.toString(monthNum) + "-01").getTime());
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void initializeYearDropDown() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        yearDropDown.setPromptText(Integer.toString(calendar.get(Calendar.YEAR)));
        for(int i = 2019; i >= 1920; i--) {
            yearDropDown.getItems().add(i);
        }
    }

    @FXML
    public void goToModifyAccountScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ModifyAccountView.fxml"));
        Parent modifyAccountView = fxmlLoader.load();
        ModifyAccountViewController modifyAccountViewController = fxmlLoader.getController();
        modifyAccountViewController.setField(userAccount);
        Stage stage = (Stage) ((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();
        stage.setTitle("Calendar App - Modify Account Information");
        stage.setScene(new Scene(modifyAccountView));
    }

    private void initializeMonthDropDown() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        monthDropDown.setPromptText(dateFormat.format(new Date(System.currentTimeMillis())));
        
        for(int i = 1; i <= 12; i++) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M");
                monthDropDown.getItems().add(new SimpleDateFormat("MMMM").format(simpleDateFormat.parse(String.valueOf(i))));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void clearCalendarRange() {
        for(Node node: calendarGrid.getChildren()) {
            if(node instanceof Label) {
                if(((Label) node).getText().length() == 3)
                    continue;
                ((Label) node).setText("");
            }
        }
    }

    public void setUserAccount(UserAccount userAccount) {
        System.out.println("HERE");
        this.userAccount = userAccount;
    }
}
