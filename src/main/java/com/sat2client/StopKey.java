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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class StopKey {
    
    long stop_id;
    int order_key;

    @JsonProperty("Stop_ID")
    public void setStopID(long route_id) {

        this.stop_id = route_id;

    }
    public long getStopID() {

        return this.stop_id;

    }

    @JsonProperty("order_key")
    public void setOrderKey(int order_key) {

        this.order_key = order_key;

    }
    public int getOrderKey() {

        return this.order_key;

    }

    @JsonCreator
    public static StopKey Create(String jsonString) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        StopKey module;
        module = mapper.readValue(jsonString, StopKey.class);
        return module;

    }
    
}

