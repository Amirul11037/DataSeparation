package com.example.amiru.dataseperation.Constant;

import android.location.Location;

/**
 * Created by amiru on 22/03/2017.
 */

public class PlaceDetailsConstant {

    public String place_id;
    public String place_name;
    public String place_address;
    public String place_type;
    public String place_vicinity;
    public String place_references;
    public Location place_location;
    public int place_direction;
    public double place_distance;
    public int place_herenow;
    public int restaurant_details_photo;

    public double logtitude;
    public double latitude;

    public double current_logtitude;
    public double current_latitude;

    public String urlTextSearch;

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public void setPlace_address(String place_address){
        this.place_address = place_address;
    }

    public String getPlace_address() {
        return place_address;
    }
}
