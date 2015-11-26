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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class POI {
    
    protected long id;
    protected float longitude;
    protected float latitude;
    protected String name;
    protected String wheelchair;
    
    public POI() {

    }
    
    // Constructor
    public POI(float longitude, float latitude, String name) {
        
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        
    }
    
    @JsonProperty("Longitude")
    public void tLongitude(float longitude) {

        this.longitude = longitude;

    }
    @JsonIgnore
    public float getLongitude() {

        return this.longitude;

    }
    
    
    @JsonProperty("Latitude")
    public void setLatitude(float latitude) {

        this.latitude = latitude;

    }
    @JsonIgnore
    public float getLatitude() {

        return this.latitude;

    }
    
    
    @JsonProperty("Name")
    public void setName(String name) {

        this.name = name;

    }
    @JsonIgnore
    public String getName() {

        return this.name;

    }
    
    
    @JsonProperty("ID")
    public void setID(long id) {

        this.id = id;

    }
    public long getID() {

        return this.id;

    }
    
    
    @JsonProperty("Wheelchair")
    public void setWheelchair(String wheelchair) {

        this.wheelchair = wheelchair;

    }
    @JsonIgnore
    public String getWheelchair() {

        return this.wheelchair;

    }
    
    @JsonCreator
    public static POI Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        POI module;
        module = mapper.readValue(jsonString, POI.class);
        return module;
    }
    
}

