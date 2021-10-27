package com.cookandroid.graduation_project;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class ReportData {
    public String time;
    public String email;
    public boolean state;
    public String address;
    public Double longitude;
    public Double latitude;


    public ReportData() { }
    public ReportData(String time, String email, boolean state, String address,Double longitude, Double latitude) {
        this.email = email;
        this.time = time;
        this.state=state;
        this.address=address;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public void setTime(String time) { this.time = time; }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setState(boolean state) { this.state = state; }
    public void setAddress(String address) { this.address = address; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }


    public String getTime() { return time; }
    public String getEmail() {
        return email;
    }
    public boolean isState() { return state; }
    public String getAddress() { return address; }
    public Double getLongitude() { return longitude; }
    public Double getLatitude() { return latitude; }



    @Override
    public String toString() {
        return "Report{" +
                "time='" + time + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '}';
    }
}