package com.mobilegroupproject.studentorganiser.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;
import com.mobilegroupproject.studentorganiser.data.Event;

import java.util.Calendar;

/**
 * Extends WeekViewEvent to add....
 * -> geoSigned Boolean
 *
 * (Also class is Parcelable)
 */
public class ExtendedWeekViewEvent extends WeekViewEvent implements Parcelable {

    private String strId;
    private Boolean geoSigned;
    private double locationLatitude;
    private double locationLongitude;
    private String personalCommentary; //TODO


    public ExtendedWeekViewEvent() {
        super();
    }

    private ExtendedWeekViewEvent(long id, String name, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute) {
        super(id, name, startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute);
    }

    private ExtendedWeekViewEvent(long id, String name, String location, Calendar startTime, Calendar endTime) {
        super(id, name, location, startTime, endTime);
    }

    private ExtendedWeekViewEvent(long id, String name, Calendar startTime, Calendar endTime) {
        super(id, name, startTime, endTime);
    }

    //extended to include geosigned and location coordinates
    public ExtendedWeekViewEvent(long id, String name, String location, Calendar startTime, Calendar endTime, Boolean geoSigned, double lat, double lng){
        super(id, name, location, startTime, endTime);
        this.geoSigned = geoSigned;
        this.locationLatitude = lat;
        this.locationLongitude = lng;
    }

    public ExtendedWeekViewEvent(Event providerEvent, long intId){

        try {
            this.setStrId(providerEvent.id);
            this.setId(intId);
            this.setName(providerEvent.title);
            this.setStartTime(createCalendarTime(providerEvent.startTime, providerEvent.date));
            this.setEndTime(createCalendarTime(providerEvent.endTime, providerEvent.date));
            this.setLocation(providerEvent.location);
            this.geoSigned = Boolean.parseBoolean(providerEvent.isSigned);
            this.locationLatitude = 52.764939;
            this.locationLongitude = -1.227303;
            this.setColor(Color.parseColor(providerEvent.hexColor));

        } catch (Exception e){
            Log.d("ExtendedWeekViewEvent", providerEvent.id + " :: " + e.toString());
        }
    }


    @Override
    public Calendar getStartTime() {
        return super.getStartTime();
    }

    @Override
    public void setStartTime(Calendar startTime) {
        super.setStartTime(startTime);
    }

    @Override
    public Calendar getEndTime() {
        return super.getEndTime();
    }

    @Override
    public void setEndTime(Calendar endTime) {
        super.setEndTime(endTime);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Override
    public void setLocation(String location) {
        super.setLocation(location);
    }

    @Override
    public int getColor() {
        return super.getColor();
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    public void setStrId(String sid){
        this.strId = sid;
    }

    public String getStrId(){
        return this.strId;
    }

    public Boolean getGeoSigned(){
        return this.geoSigned;
    }

    public void setGeoSigned(Boolean geo){
        this.geoSigned = geo;
    }

    public double getLat(){
        return this.locationLatitude;
    }

    public void setLat(double lat){
        this.locationLatitude = lat;
    }

    public double getLng(){
        return this.locationLongitude;
    }

    public void setLng(double lng){
        this.locationLongitude = lng;
    }

    public void setPersonalCommentary(String pc){
        this.personalCommentary = pc;
    }

    public String getPersonalCommentary(){
        return this.personalCommentary;
    }


    private Calendar createCalendarTime(String time, String date){
        Calendar calendar = Calendar.getInstance();

        String[] timeStrArray = time.split(":");
        String[] dateStrArray = date.split(":");

        calendar.set(
                Integer.parseInt(dateStrArray[2]),
                Integer.parseInt(dateStrArray[1])-1, //month index in calender format is 0-11
                Integer.parseInt(dateStrArray[0]),
                Integer.parseInt(timeStrArray[0]),
                Integer.parseInt(timeStrArray[1])
        );

        return calendar;
    }

    //PARCELABLE CODE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getId());
        dest.writeSerializable(this.getStartTime());
        dest.writeSerializable(this.getEndTime());
        dest.writeString(this.getName());
        dest.writeString(this.getLocation());
        dest.writeInt(this.getColor());

        dest.writeByte((byte) (this.getGeoSigned() ? 1 : 0));
        dest.writeDouble(this.getLat());
        dest.writeDouble(this.getLng());
    }

    protected ExtendedWeekViewEvent(Parcel in) {
        this.setId(in.readLong());
        this.setStartTime((Calendar) in.readSerializable());
        this.setEndTime((Calendar) in.readSerializable());
        this.setName(in.readString());
        this.setLocation(in.readString());
        this.setColor(in.readInt());

        this.setGeoSigned(in.readByte() != 0);
        this.setLat(in.readDouble());
        this.setLng(in.readDouble());
    }

    public static final Parcelable.Creator<ExtendedWeekViewEvent> CREATOR = new Parcelable.Creator<ExtendedWeekViewEvent>() {
        @Override
        public ExtendedWeekViewEvent createFromParcel(Parcel source) {
            return new ExtendedWeekViewEvent(source);
        }

        @Override
        public ExtendedWeekViewEvent[] newArray(int size) {
            return new ExtendedWeekViewEvent[size];
        }
    };
}
