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


public class CheckConnectionsWorker extends SwingWorker<List<Route>, Integer> {

    private DefaultListModel listModel;
    private javax.swing.JButton searchButton;
    private String serverAddress;
    private String startName;
    private String stopName;
    private List<Route> routes = new ArrayList<>();
    
    public CheckConnectionsWorker(DefaultListModel lm, javax.swing.JButton sb, String startName, String stopName, String sa) {
        
        this.startName = startName;
        this.stopName = stopName;
        this.listModel = lm;
        this.searchButton = sb;
        this.serverAddress = sa;
        
    }
    
    
    @Override
    protected List<Route> doInBackground() {
        
        // disable search button during search
        searchButton.setEnabled(false);
        listModel.clear();

        try {

            System.out.println("Searching connetions between " + startName + " and " + stopName);

            NodejsAccess na = new NodejsAccess(serverAddress);

            if((StringUtils.isNumeric(startName)) &&(StringUtils.isNumeric(stopName))) {
                System.out.println("Numeric check");
                routes = na.getConnectingRoutesByID(Integer.parseInt(startName), Integer.parseInt(stopName));
            } else {
                System.out.println("String check");
                routes = na.getConnectingRoutesByName(startName, stopName);
            }

            System.out.println("Done");

        } catch (Exception exc) {
            exc.printStackTrace();System.out.println("EXCEPTION");
        }
        
        System.out.println("Lines connecting " + startName + " to " + stopName + " : " + routes.size());

        return routes;
        
    }

    @Override
    protected void done() {
        
        // update the GUI
        if (!routes.isEmpty()) {
            for (Route route : routes) {
                listModel.addElement(route);
            }

            // To test intermediate Stops, display intermediate stops of first found route:
            /*
            Route test_route = routes.get(0);
            List<POI> intermediates = test_route.getIntermediateStops(startName, stopName);

            for (POI i : intermediates) {
                System.out.println("Intermediate Stop: ID= " + i.getID() + " Name: " + i.getName());
            }
            */

        } else {
            JOptionPane.showMessageDialog(null, "No route was found connecting these stops");
        }
        
        searchButton.setEnabled(true);
        
    }
}

