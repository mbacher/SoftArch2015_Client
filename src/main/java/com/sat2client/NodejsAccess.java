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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class NodejsAccess {
    
    private RestTemplate restTemplate;
    private String serverAddress;
    
    
    public NodejsAccess(String serverAddress) {
        
        this.serverAddress = serverAddress;
        try {
            this.restTemplate = new RestTemplate();
        } catch(Exception exc){
            exc.printStackTrace();
        }
        
    }

    public List<Route> getConnectingRoutesByName(String start_name, String stop_name) {
        
        // from mySQL query get the route(s) connecting two given Stops
        
        List<Route> result = new ArrayList<>();
        
        String uri = serverAddress + "/route?fromName=" + start_name + "&toName=" + stop_name;

        try {
            result = RestGetRoutes(uri);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        return result;
        
    }


    public List<Route> getConnectingRoutesByID(long start_id, long stop_id) {
        
        // from mySQL query get the route(s) connecting two given Stops
        
        List<Route> result = new ArrayList<>();
        
        String uri = serverAddress + "route?fromID=" + Long.toString(start_id) + "&toID=" + Long.toString(stop_id);
        
        try {
            result = RestGetRoutes(uri);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        return result;
        
    }


    public PublicTransportStop getPublicTransportStopData(long ID) {
        
        // given an ID, type of POI (Stop, Amenity, etc...) returns a POI object containing the POIs data
        
        String uri = serverAddress + "stop?id=" + ID;
        List<PublicTransportStop> result;
        PublicTransportStop final_result = new PublicTransportStop();
        
        try {
            String jsonData = restTemplate.getForObject(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(jsonData, new TypeReference<List<PublicTransportStop>>(){});

            /* ugly hack for when json is empty list */
            if(result.isEmpty()) {
                final_result = new PublicTransportStop();
            } else {
                final_result = result.get(0);
            }
            /* ugly hack for when json is empty list */
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        return final_result;
        
    }

    public List<POI> getPOIsFromNames(String poi_name, String type) {
        
        String uri = serverAddress + "stop?name=" + poi_name;
        List<POI> result_list = new ArrayList<>();
        
        try {
            String jsonData = restTemplate.getForObject(uri, String.class);
            
            ObjectMapper mapper = new ObjectMapper();
            result_list = mapper.readValue(jsonData, new TypeReference<List<POI>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result_list;
        
    }

    public long insertRoute(Route new_route) throws Exception {
        
        /*
        ObjectMapper mapper = new ObjectMapper();

        JsonNode route = mapper.valueToTree(new_route);

        System.out.print(route.toString());
        */
        
        String uri = serverAddress + "/route";
        long new_id;
        
        PostRouteMessage message = new PostRouteMessage();
        message.setName(new_route.getRouteName());
        
        long[] stop_list = new long[new_route.getNumberOfStops()];
        int i = 0;
        
        for(POI stop : new_route.getRouteStops()) {
            stop_list[i] = stop.getID();
            i++;
        }
        
        message.setStops(stop_list);
        
        // Set the Content-Type header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application","json"));
        HttpEntity<PostRouteMessage> requestEntity = new HttpEntity<>(message, requestHeaders);
        
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        
        try {
            String post_response = restTemplate.postForObject(uri, requestEntity, String.class);
            // Extract ID from Json response
            new_id = Long.parseLong(post_response.replaceAll("[^0-9?!\\.]", ""));
        } catch(Exception exc) {
            exc.printStackTrace();
            new_id = -1;
        }
        
        return new_id;
        
    }

    public List<PublicTransportStop> searchStopsFiltered(long id,
                                                         boolean usename,
                                                         String name,
                                                         boolean uselong,
                                                         boolean longabove,
                                                         double longitude,
                                                         boolean uselat,
                                                         boolean latabove,
                                                         double latitude
                                                         ) {
        
        List<PublicTransportStop> result = new ArrayList<>();
        
        if(id > 0) {
            // use just id
            String uri = serverAddress + "/stop?id=" + id;
            try {
                result = RestGetStops(uri);
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        } else if(usename && !uselong && !uselat) {
            // use just name
            String uri = serverAddress + "/stop?name=" + name;
            try {
                result = RestGetStops(uri);

            } catch(Exception exc) {
                exc.printStackTrace();
            }
        } else if(usename==true && uselong==true && uselat==false) {
            // use name & longitude
            String uri = serverAddress + "/stop?name=" + name + "&longitude=" + longitude + "&belowlongitude="
                    + !longabove;
            try {
                result = RestGetStops(uri);
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        } else if(usename==true && uselong==false && uselat==true) {
            // use name and latitude
            String uri = serverAddress + "/stop?name=" + name + "&latitude=" + longitude + "&belowlatitude="
                    + !latabove;
            try {
                result = RestGetStops(uri);
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        } else if(usename==true && uselong==true && uselat==true) {
            // use name, lat & long
            String uri = serverAddress + "/stop?name=" + name + "&longitude=" + longitude + "&latitude="
                    + latitude + "&belowlongitude=" + !longabove + "&belowlatitude=" + !latabove;
            try {
                result = RestGetStops(uri);
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        } else if(usename==false && uselong==true && uselat==false) {
            // use long
            String uri = serverAddress + "/stop?longitude=" + longitude + "&belowlongitude=" + !longabove;
            try {
                result = RestGetStops(uri);
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        } else if(usename==false && uselong==false && uselat==true) {
            // use lat
            String uri = serverAddress + "/stop?latitude=" + latitude + "&belowlatitude=" + !latabove;
            try {
                result = RestGetStops(uri);
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        } else if(usename==false && uselong==true && uselat==true) {
            // use lat & long
            String uri = serverAddress + "/stop?longitude=" + longitude + "&latitude=" + latitude
                    + "&belowlongitude" + !longabove + "&belowlatitude=" + !latabove;

            try {
                result = RestGetStops(uri);
            } catch(Exception exc) {
                exc.printStackTrace();
            }
        }
        
        return result;
        
    }

    private List<PublicTransportStop> RestGetStops(String uri) throws Exception {
        
        List<PublicTransportStop> result;
        String jsonData = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        result = mapper.readValue(jsonData, new TypeReference<List<PublicTransportStop>>(){});
        
        return result;
        
    }

    private List<Route> RestGetRoutes(String uri) throws Exception {

        List<Route> result = new ArrayList<Route>();

        ObjectMapper mapper = new ObjectMapper();
        String jsonData = restTemplate.getForObject(uri, String.class);
        JsonNode rootArray = mapper.readTree(jsonData);

        for(JsonNode root : rootArray) {
            result.add(mapper.readValue(root.toString(), new TypeReference<Route>(){}));
        }

        return result;

    }
}

