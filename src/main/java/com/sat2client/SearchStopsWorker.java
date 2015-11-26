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

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class SearchStopsWorker extends SwingWorker<Integer, Integer> {
    
    private DefaultListModel listModel;
    private javax.swing.JButton searchButton;
    private String serverAddress;
    private boolean useName;
    private boolean useLongitude;
    private boolean useLatitude;
    private String nameString;
    private String longitudeString;
    private String latitudeString;
    private boolean longitudeAbove;
    private boolean latitudeAbove;
    private List<PublicTransportStop> stops = new ArrayList<>();
    
    public SearchStopsWorker(DefaultListModel lm, javax.swing.JButton sb,
                             boolean usename, String name, boolean uselong,
                             String longitude, boolean longabove,
                             boolean uselat, String latitude, boolean latabove, String serverAddress) {
        
        this.listModel = lm;
        this.searchButton = sb;
        this.serverAddress = serverAddress;

        this.useName = usename;
        this.useLongitude = uselong;
        this.useLatitude = uselat;
        this.nameString = name;
        this.longitudeString = longitude;
        this.latitudeString = latitude;
        this.longitudeAbove = longabove;
        this.latitudeAbove = latabove;

    }

    @Override
    protected Integer doInBackground() {
        
        // disable search button during search
        searchButton.setEnabled(false);
        listModel.clear();

        NodejsAccess na = new NodejsAccess(serverAddress);
        double longitude = 0;
        double latitude = 0;

        if(!(longitudeString.isEmpty())) {
            longitude = Double.parseDouble(longitudeString);
        }

        if(!(latitudeString.isEmpty())) {
            latitude = Double.parseDouble(latitudeString);
        }
        
        long id = -1; // if ID is not used, set to negative!
        
        if(useName) {
            if(StringUtils.isNumeric(nameString)) {
                useName = false;
                id = Integer.parseInt(nameString);
            }
        }
        
        stops = na.searchStopsFiltered(id, useName, nameString,
                                       useLongitude, longitudeAbove, longitude,
                                       useLatitude, latitudeAbove, latitude);

        return 0;
        
    }

    @Override
    protected void done() {
        
        // update the GUI
        
        if (!stops.isEmpty()) {

            for (PublicTransportStop stop : stops) {
                listModel.addElement(stop);
            }

        } else {

            JOptionPane.showMessageDialog(null, "No stops were found using these criteria");

        }
        
        searchButton.setEnabled(true);
        
    }
    
}

