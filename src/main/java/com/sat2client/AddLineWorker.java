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

public class AddLineWorker extends SwingWorker<Route, Void> {
    
    private DefaultListModel listModel;
    private javax.swing.JButton createButton;
    private String routeName;
    private String serverAddress;
    private long newLineId = -1;
    
    public AddLineWorker(DefaultListModel lm, javax.swing.JButton cb,
                         String rn, String sa) {
        
        this.listModel = lm;
        this.createButton = cb;
        this.routeName = rn;
        this.serverAddress = sa;
        
    }


    @Override
    protected Route doInBackground() {
        
        createButton.setEnabled(false);

        try {
            Route newLine = new Route();
            List<POI> newLineStops = new ArrayList<>();
            
            newLine.setRouteID(newLineId);   //ID doesn't matter, is autocreated in insert statement

            for(int i = 0; i < listModel.size(); i++) {
                newLineStops.add((PublicTransportStop)listModel.get(i));
            }
                
            newLine.setRouteStops(newLineStops);
            newLine.setRouteName(routeName);
                
            NodejsAccess na = new NodejsAccess(serverAddress);
            newLineId = na.insertRoute(newLine);

        } catch(Exception exc) {

            exc.printStackTrace();

        }

        return null;
    }

    @Override
    protected void done() {
        
        System.out.println("AddLineWorker finished");
        createButton.setEnabled(true);
        JOptionPane.showMessageDialog(null, "New Line added: " + newLineId + ": " + routeName);
        
    }
}

