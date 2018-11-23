/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Models;

import javafx.beans.property.SimpleStringProperty;

public class Report {
    
    private SimpleStringProperty appointmentDescription;
    private SimpleStringProperty appointmentMonth;
    private SimpleStringProperty appointmentTotal;
    private SimpleStringProperty appointmentContact;
    private SimpleStringProperty appointmentCustomerName;
    private SimpleStringProperty appointmentStart;
    private SimpleStringProperty appointmentEnd;
    private SimpleStringProperty customerCount;
    private SimpleStringProperty cityCount;
    
       
    public Report() {
        
        appointmentDescription = new SimpleStringProperty();
        appointmentMonth = new SimpleStringProperty();
        appointmentTotal = new SimpleStringProperty();
        appointmentContact = new SimpleStringProperty();
        appointmentCustomerName = new SimpleStringProperty();
        appointmentStart = new SimpleStringProperty();
        appointmentEnd = new SimpleStringProperty();
        customerCount = new SimpleStringProperty();
        cityCount = new SimpleStringProperty();
        
    }

    public String getAppointmentDescription() {
        
        return appointmentDescription.get();
        
    }

    public void setAppointmentDescription(String appointmentDescription) {
        
        this.appointmentDescription.set(appointmentDescription);
        
    }

    public String getAppointmentMonth() {
        
        return appointmentMonth.get();
        
    }

    public void setAppointmentMonth(String appointmentMonth) {
        
        this.appointmentMonth.set(appointmentMonth);
        
    }

    public String getAppointmentTotal() {
        
        return appointmentTotal.get();
        
    }

    public void setAppointmentTotal(String appointmentTotal) {
        
        this.appointmentTotal.set(appointmentTotal);
        
    }

    public String getAppointmentContact() {
        
        return appointmentContact.get();
        
    }

    public void setAppointmentContact(String appointmentContact) {
        
        this.appointmentContact.set(appointmentContact);
        
    }

    public String getAppointmentCustomerName() {
        
        return appointmentCustomerName.get();
        
    }

    public void setAppointmentCustomerName(String appointmentCustomerName) {
        
        this.appointmentCustomerName.set(appointmentCustomerName);
        
    }

    public String getAppointmentStart() {
        
        return appointmentStart.get();
        
    }

    public void setAppointmentStart(String appointmentStart) {
        
        this.appointmentStart.set(appointmentStart);
        
    }

    public String getAppointmentEnd() {
        
        return appointmentEnd.get();
        
    }

    public void setAppointmentEnd(String appointmentEnd) {
        
        this.appointmentEnd.set(appointmentEnd);
        
    }

    public String getAppointmentCount() {
        
        return customerCount.get();
        
    }

    public void setAppointmentCount(String appointmentCount) {
        
        this.customerCount.set(appointmentCount);
        
    }

    public String getAppointmentCity() {
        
        return cityCount.get();
        
    }

    public void setAppointmentCity(String appointmentCity) {
        
        this.cityCount.set(appointmentCity);
        
    } 
}
