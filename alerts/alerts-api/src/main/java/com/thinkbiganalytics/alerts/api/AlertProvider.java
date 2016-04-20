/**
 * 
 */
package com.thinkbiganalytics.alerts.api;

import java.io.Serializable;
import java.util.Iterator;

import org.joda.time.DateTime;

/**
 * This provider is the primary facade over one or more alert sources.  It allows
 * for synchronously retrieving and responding to alerts, as well as registering listeners/
 * responders to react to new events enter the system.
 * @author Sean Felten
 */
public interface AlertProvider {
    
    // TODO: add criteria-based alert search
    
    // TODO: add criteria-based listener/responder alert filtering
    
    /**
     * Resolves and reconstitutes one of the acceptable serializable ID formats (such as its toString() form) into 
     * a valid alert ID.
     * @param value one of the serializable formats
     * @return an alert ID
     */
    Alert.ID resolve(Serializable value);
    
    /**
     * Registers a listener that will be called whenever the state of an alert
     * changes, such as when one is created or responded to by responder that 
     * responded to an alert and caused its state to change.
     * @param listener the listener being added
     */
    void addListener(AlertListener listener);
    
    /**
     * Registers a responder that will be invoked whenever an respondable alert has transitioned new
     * state other than CLEARED. 
     * @param responder
     */
    void addResponder(AlertResponder responder);
    
    /**
     * Retrieves a specific alert
     * @param id the alert's ID
     * @return the alert, or null if no alert exists with that ID
     */
    Alert getAlert(Alert.ID id);
    
    /**
     * Gets a stream of all alerts that may have been created after the given time.  
     * @param since the time from which newer alerts should be returned
     * @return an iterator on all alerts created after the specified time
     */
    Iterator<? extends Alert> getAlerts(DateTime since);
    
    /**
     * Gets a stream of all alerts that may have been crreated since the alert specified by the
     * given ID was created.  Specifying null retrieve all known alerts.
     * @param since the ID of an alert
     * @return an iterator on all alerts created after the given alert ID
     */
    Iterator<? extends Alert> getAlerts(Alert.ID since);

    /**
     * Allows a synchronous update of a particular alert using the supplied responder.  This method will block
     * until the call to the responder has returned.
     * @param id the ID of the alert to respond to
     * @param responder the responder used as a call-back to respond to the alert
     */
    void respondTo(Alert.ID id, AlertResponder responder);
}