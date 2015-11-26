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


public class Amenity extends POI {
    
    private String type = "Amenity";
    private String amenityType;
    
    public Amenity() {

    }

    public Amenity(long id, float longitude, float latitude, String type) {
        
        this.type = type;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        
    }

    public String getAmenitySubType() {
        
        return this.amenityType;
        
    }

    public void setAmenitySubType(String amenityType) {
        
        this.amenityType = amenityType;
        
    }
}

