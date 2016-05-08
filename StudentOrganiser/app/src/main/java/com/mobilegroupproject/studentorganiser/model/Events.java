package com.mobilegroupproject.studentorganiser.model;

/**
 * Created by alex on 08/05/2016.
 */
public class Events {
    private String title;
    private String date_time;
    private String building;
    private String hangout_link;
    private String event_creator;
    private String colour_id;
    private String description;
    private String uid;

    public Events(String title, String date_time, String building, String hangout_link, String event_creator,
                    String colour_id, String description, String uid) {
        this.title = title;
        this.date_time = date_time;
        this.building = building;
        this.hangout_link = hangout_link;
        this.event_creator = event_creator;
        this.colour_id = colour_id;
        this.description = description;
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getBuilding() {
        return building;
    }

    public String getHangout_link() {
        return hangout_link;
    }

    public String getEvent_creator() {
        return event_creator;
    }

    public String getColour_id() {
        return colour_id;
    }

    public String getDescription() {
        return description;
    }

    public String getUid() {
        return uid;
    }
}