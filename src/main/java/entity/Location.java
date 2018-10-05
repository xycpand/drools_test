package entity;

import java.io.Serializable;

/**
 * Created by lcc on 2018/10/3.
 */
public enum Location implements Serializable {

    CHECK_IN("check-in"), SORTING("sorting"), STAGING("staging"), LOADING("loading");

    private String location;

    private Location(String location) {
        this.setLocation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}