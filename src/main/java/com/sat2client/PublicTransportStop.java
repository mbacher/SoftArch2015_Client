/*
 * Software Architecture Task 2
 *
 * Group 21
 *
 * Christian Tinauer
 * Marco Stranner
 * Mario Bacher
 * Matthias Tomaschek
 *
 */
package com.sat2client;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PublicTransportStop extends POI {
    
    private String type = "Stop";
    
    public PublicTransportStop() {

    }

    public PublicTransportStop(long id, String name, float longitude, float latitude, String type) {
        
        this.type = type;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        
    }

    @JsonIgnore
    public String getType() {
        
        return type;
        
    }

    public void setType(String type) {
        
        this.type = type;
        
    }

    public String toString() {
        
        return "#" + this.id + ": " + this.name + ", Typ: " + this.type;
        
    }
}

