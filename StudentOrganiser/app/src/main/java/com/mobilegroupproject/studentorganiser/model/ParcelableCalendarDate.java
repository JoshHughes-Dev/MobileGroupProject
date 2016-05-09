package com.mobilegroupproject.studentorganiser.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Calender date class not parcelable. created this as a substitute.
 * need to parcel this data so that the current viewed dat on calendar widget remains consistent
 * between orientation and day number changes
 */
public class ParcelableCalendarDate implements Parcelable {

    public int Year;
    public int Month;
    public int Date;
    public int Hour;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Year);
        dest.writeInt(this.Month);
        dest.writeInt(this.Date);
        dest.writeInt(this.Hour);
    }

    public ParcelableCalendarDate() {
    }

    protected ParcelableCalendarDate(Parcel in) {
        this.Year = in.readInt();
        this.Month = in.readInt();
        this.Date = in.readInt();
        this.Hour = in.readInt();
    }

    public static final Creator<ParcelableCalendarDate> CREATOR = new Creator<ParcelableCalendarDate>() {
        @Override
        public ParcelableCalendarDate createFromParcel(Parcel source) {
            return new ParcelableCalendarDate(source);
        }

        @Override
        public ParcelableCalendarDate[] newArray(int size) {
            return new ParcelableCalendarDate[size];
        }
    };
}
