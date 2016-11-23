package com.app.theweatherapp.api;

import java.io.Serializable;

/**
 * Created by Beyeta Sanu on 11/19/16.
 */

public class ApiResourceLocationResult implements Serializable {
    private static final Long serialVersionUID = 1L;
    private ApiResourceLocation location;

    public ApiResourceLocation getLocation() {
        return location;
    }

    public void setLocation(ApiResourceLocation location) {
        this.location = location;
    }
}
