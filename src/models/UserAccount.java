package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class UserAccount
{
    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private char gender;

    private Date dateOfBirth;

    private int age;

    private String password;

    private ArrayList<Appointment> appointments;

    public UserAccount(String userName, String firstName, String lastName, String email,
                       char gender, Date dateOfBirth, String password)
    {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.age = getDiffYears(dateOfBirth, new Date(System.currentTimeMillis()));
        this.password = password;
        appointments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return    userName + "\n"
                + firstName + "\n"
                + lastName + "\n"
                + email + "\n"
                + gender + "\n"
                + dateOfBirth + "\n"
                + age + "\n"
                + password + "\n";
    }

    private int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        return a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE)) ? diff - 1 : diff;
    }

    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        age = getDiffYears(dateOfBirth, new Date(System.currentTimeMillis()));
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void createAppointment(Date date, Time time, String place, String eventName)
    {
        appointments.add(new Appointment(date, time, place, eventName));
    }

}
