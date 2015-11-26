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

// A route has an ID, a name and 2 to n stops (POI objects, having a name, type, longitude and latitude)


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Route {
    
    private long id;
    private String name;
    private List<POI> stops;
    
    // Default Constructor
    public Route() {

    }
    
    // Constructor
    public Route(long id, String name, List<POI> stops) {
        
        this.id = id;
        this.name = name;
        this.stops = stops;
        
    }
    
    @JsonProperty("ID")
    public void setRouteID(long id) {

        this.id = id;

    }
    @JsonIgnore
    public long getRouteID() {

        return this.id;
    }
    
    @JsonProperty("Name")
    public void setRouteName(String name) {

        this.name = name;

    }
    public String getRouteName() {

        return this.name;

    }
    
    @JsonProperty("Stops")
    public void setRouteStops(List<POI> stops) {

        this.stops = stops;

    }
    public List<POI> getRouteStops() {

        return this.stops;

    }
    
    @JsonCreator
    public static Route Create(String jsonString) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Route module;
        module = mapper.readValue(jsonString, Route.class);
        return module;

    }
    
    
    
    @JsonIgnore
    public int getNumberOfStops() {

        return this.stops.size();

    }
    
    
    public List<POI> getIntermediateStops(long id_1, long id_2) {
        
        List<POI> intermediate_stops;

        Optional<POI> result_1 = this.stops
                .stream()
                .filter(a -> a.getID() == id_1)
                .findFirst();

        Optional<POI> result_2 = this.stops
                .stream()
                .filter(a -> a.getID() == id_2)
                .findFirst();

        intermediate_stops = this.stops.subList(this.stops.indexOf(result_1.get()), this.stops.indexOf(result_2.get()) + 1);

        return intermediate_stops;
        
    }

    public String toString() {
        
        // Warning: StringEscapeUtils.unescapeHTML4() must be used to properly display characters!
        return StringEscapeUtils.unescapeHtml4(this.name);
        
    }
}

