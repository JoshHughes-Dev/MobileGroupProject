package com.mobilegroupproject.studentorganiser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;

/**
 * Extends WeekViewEvent to add....
 * -> geoSigned Boolean
 *
 * (Also class is Parcelable)
 */
public class ExtendedWeekViewEvent extends WeekViewEvent implements Parcelable {

    private Boolean geoSigned;
    private double locationLatitude;
    private double locationLongitude;
    private String personalCommentary; //TODO


    public ExtendedWeekViewEvent() {
        super();
    }

    public ExtendedWeekViewEvent(long id, String name, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute) {
        super(id, name, startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute);
    }

    public ExtendedWeekViewEvent(long id, String name, String location, Calendar startTime, Calendar endTime) {
        super(id, name, location, startTime, endTime);
    }

    public ExtendedWeekViewEvent(long id, String name, Calendar startTime, Calendar endTime) {
        super(id, name, startTime, endTime);
    }

    //extended to include geosigned and location coordinates
    public ExtendedWeekViewEvent(long id, String name, String location, Calendar startTime, Calendar endTime, Boolean geoSigned, double lat, double lng){
        super(id, name, location, startTime, endTime);
        this.geoSigned = geoSigned;
        this.locationLatitude = lat;
        this.locationLongitude = lng;
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
