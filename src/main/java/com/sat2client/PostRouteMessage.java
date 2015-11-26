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

public class PostRouteMessage {
    
    private String name;
    private long[] stops;
    
    public void setName(String name) {
        
        this.name = name;
        
    }
    
    public String getName() {
        
        return this.name;
        
    }
    
    public void setStops(long[] stops) {
        
        this.stops = stops;
        
    }
    
    public long[] getStops() {
        
        return this.stops;
        
    }
}

