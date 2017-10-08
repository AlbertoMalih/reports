package com.example.reports.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Олег on 08.10.2017.
 */

public class User implements Parcelable{
    private Long sizeHours;
    private Long sizePublications;
    private Long sizeRepeatVisits;
    private Long sizeStudyingBible;
    private Long sizeVideos;

    public User() {
    }

    public User(Long sizeHours, Long sizePublications, Long sizeRepeatVisits, Long sizeStudyingBible, Long sizeVideos) {
        this.sizeHours = sizeHours;
        this.sizePublications = sizePublications;
        this.sizeRepeatVisits = sizeRepeatVisits;
        this.sizeStudyingBible = sizeStudyingBible;
        this.sizeVideos = sizeVideos;
    }

    protected User(Parcel in) {
    }

    public void addHours(Long value){
        sizeHours +=value;
    }

    public void addVideos(Long value){
        sizeVideos +=value;
    }

    public void addRepeatVisits(Long value){
        sizeRepeatVisits +=value;
    }

    public void addStudyingsBible(Long value){
        sizeStudyingBible +=value;
    }

    public void addPublications(Long value){
        sizePublications +=value;
    }

    public Long getSizeHours() {
        return sizeHours;
    }

    public void setSizeHours(Long sizeHours) {
        this.sizeHours = sizeHours;
    }

    public Long getSizePublications() {
        return sizePublications;
    }

    public void setSizePublications(Long sizePublications) {
        this.sizePublications = sizePublications;
    }

    public Long getSizeRepeatVisits() {
        return sizeRepeatVisits;
    }

    public void setSizeRepeatVisits(Long sizeRepeatVisits) {
        this.sizeRepeatVisits = sizeRepeatVisits;
    }

    public Long getSizeStudyingBible() {
        return sizeStudyingBible;
    }

    public void setSizeStudyingBible(Long sizeStudyingBible) {
        this.sizeStudyingBible = sizeStudyingBible;
    }

    public Long getSizeVideos() {
        return sizeVideos;
    }

    public void setSizeVideos(Long sizeVideos) {
        this.sizeVideos = sizeVideos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(sizeHours);
        dest.writeLong(sizePublications);
        dest.writeLong(sizeRepeatVisits);
        dest.writeLong(sizeStudyingBible);
        dest.writeLong(sizeVideos);
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in.readLong(), in.readLong(), in.readLong(), in.readLong(), in.readLong());
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
