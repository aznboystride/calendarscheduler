package models;

import java.sql.Time;
import java.util.Date;

public class Appointment {

    private Date date;

    private Time time;

    private String place;

    private String eventName;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Appointment(Date date, Time time, String place, String eventName)
    {
        this.date = date;
        this.time = time;
        this.place = place;
        this.eventName = eventName;
    }


}
