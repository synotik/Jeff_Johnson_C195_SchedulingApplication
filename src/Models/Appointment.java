/*
 * 
 * Created by Jeff Johnson - November 2018
 * 
 */
package Models;

import Views.LoginController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {
    
    public static String currentUser = LoginController.currentUser;
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    
    private final SimpleIntegerProperty appointmentId;
    private final SimpleIntegerProperty appointmentCustomerId;
    private final SimpleStringProperty appointmentStart;
    private final SimpleStringProperty appointmentEnd;
    private final SimpleStringProperty appointmentTitle;
    private final SimpleStringProperty appointmentDescription;
    private final SimpleStringProperty appointmentLocation;
    private final SimpleStringProperty appointmentContact;
    
    private static final Date STARTDATE = Date.valueOf(LocalDate.now());
    private static final Date ENDDATEMONTH = Date.valueOf(LocalDate.now().plusMonths(1));
    private static final Date ENDDATEWEEK = Date.valueOf(LocalDate.now().plusWeeks(1));
    
    private static final LocalDateTime CURRENTLDT = LocalDateTime.now();
    private static ZoneId zoneId = ZoneId.systemDefault();
    private static ZonedDateTime zonedDT = CURRENTLDT.atZone(zoneId);
    private static LocalDateTime ldt1 = zonedDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    private static LocalDateTime ldt2 = ldt1.plusMinutes(15);
           
    private static ObservableList<Appointment> appointmentsForWeek = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsForMonth = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsIn15 = FXCollections.observableArrayList();
    
    public static final String APPOINTMENTS_BY_MONTH_QUERY = "SELECT * FROM appointment" +
            " WHERE customerId = ? AND start >= ? AND start <= ?";
    public static final String APPOINTMENTS_BY_WEEK_QUERY = "SELECT * FROM appointment" +
            " WHERE customerID = ? AND start >= ? AND start <= ?";
    public static final String APPOINTMENTS_IN_15_MINUTES_QUERY = "SELECT * FROM appointment" +
            " WHERE (start BETWEEN ? AND ?) AND contact = ?";
    public static final String INSERT_NEW_APPOINTMENT = "INSERT INTO appointment SET customerID = ?," +
            " title = ?, description = ?, contact = ?, location = ?, start = ?, end = ?, url = ''," +
            " createDate = NOW(), createdBy = ?, lastUpdate = NOW(), lastUpdateBy = ?";
    public static final String REMOVE_APPOINTMENT_QUERY = "DELETE FROM appointment WHERE appointmentId = ?";
    public static final String DUPLICATE_APPOINTMENT_QUERY = "SELECT * FROM appointment WHERE (start BETWEEN ?" +
            " AND ?) OR (end BETWEEN ? AND ?)";
            
    private static PreparedStatement monthlyAppointments;
    private static PreparedStatement weeklyAppointments;
    private static PreparedStatement newAppointment;
    private static PreparedStatement removeAppointment;
    private static PreparedStatement duplicateAppointment;
        
    public Appointment() {
        
        appointmentId = new SimpleIntegerProperty();
        appointmentCustomerId = new SimpleIntegerProperty();
        appointmentStart = new SimpleStringProperty();
        appointmentEnd = new SimpleStringProperty();
        appointmentTitle = new SimpleStringProperty();
        appointmentDescription = new SimpleStringProperty();
        appointmentLocation = new SimpleStringProperty();
        appointmentContact = new SimpleStringProperty();
        
    }

    public int getAppointmentId() {
        
        return appointmentId.get();
        
    }

    public int getAppointmentCustomerId() {
        
        return appointmentCustomerId.get();
        
    }

    public String getAppointmentStart() {
        
        return appointmentStart.get();
        
    }
    
    public static String getAppointmentStartTime(String string) {
        
        String start = string;
        String start1 = start.substring(11, 16);
        
        return start1;    
        
    }

    public String getAppointmentEnd() {
        
        return appointmentEnd.get();
        
    }
    
    public static String getAppointmentEndTime(String string) {
        
        String end = string;
        String end1 = end.substring(11, 16);

        return end1;
        
    }

    public String getAppointmentTitle() {
        
        return appointmentTitle.get();
        
    }

    public String getAppointmentDescription() {
        
        return appointmentDescription.get();
        
    }

    public String getAppointmentLocation() {
        
        return appointmentLocation.get();
        
    }

    public String getAppointmentContact() {
        
        return appointmentContact.get();
        
    }
    
    public void setAppointmentId(int id) {
        
        this.appointmentId.set(id);
        
    }
    
    public void setAppointmentCustomerId(int id) {
        
        this.appointmentCustomerId.set(id);
        
    }
  
    public void setAppointmentStart(String start) {
        
        appointmentStart.set(start);
        
    }
    
    public void setAppointmentEnd(String end) {
        
        this.appointmentEnd.set(end);
        
    }
    
    public void setAppointmentTitle(String title) {
        
        appointmentTitle.set(title);
        
    }
    
    public void setAppointmentDescription(String description) {
        
        this.appointmentDescription.set(description);
        
    }
    
    public void setAppointmentLocation(String location) {
        
        this.appointmentLocation.set(location);
        
    }
    
    public void setAppointmentContact(String contact) {
        
        this.appointmentContact.set(contact);
        
    }
    
    public static ObservableList<Appointment> getAppointmentsByMonth(int id) {
        
        appointmentsForMonth.clear();
        try {
            monthlyAppointments = DBConnection.getConnection().prepareStatement(APPOINTMENTS_BY_MONTH_QUERY);
            monthlyAppointments.setInt(1, id);
            monthlyAppointments.setDate(2, STARTDATE);
            monthlyAppointments.setDate(3, ENDDATEMONTH);
            
            ResultSet results = monthlyAppointments.executeQuery();
            
            while(results.next()) {
                Appointment appointment = new Appointment();
                
                appointment.setAppointmentId(results.getInt("appointmentId"));
                appointment.setAppointmentCustomerId(results.getInt("customerId"));
                appointment.setAppointmentStart(results.getString("start"));
                appointment.setAppointmentEnd(results.getString("end"));
                appointment.setAppointmentTitle(results.getString("title"));
                appointment.setAppointmentDescription(results.getString("description"));
                appointment.setAppointmentLocation(results.getString("location"));
                appointment.setAppointmentContact(results.getString("contact"));
                
                appointmentsForMonth.add(appointment);
                    
            }
            return appointmentsForMonth;            
            
        } catch (SQLException e) {
            System.out.println("Querying for Appointments by Month failed: " + e.getMessage());
            return null;
        }
        
    }
    
    public static ObservableList<Appointment> getAppointmentsByWeek(int id) {
        
        appointmentsForWeek.clear();
        try {
            weeklyAppointments = DBConnection.getConnection().prepareStatement(APPOINTMENTS_BY_WEEK_QUERY);
            weeklyAppointments.setInt(1, id);
            weeklyAppointments.setDate(2, STARTDATE);
            weeklyAppointments.setDate(3, ENDDATEWEEK);
            
            ResultSet results = weeklyAppointments.executeQuery();
            
            while(results.next()) {
                Appointment appointment = new Appointment();
                
                appointment.setAppointmentId(results.getInt("appointmentId"));
                appointment.setAppointmentCustomerId(results.getInt("customerId"));
                appointment.setAppointmentStart(results.getString("start"));
                appointment.setAppointmentEnd(results.getString("end"));
                appointment.setAppointmentTitle(results.getString("title"));
                appointment.setAppointmentDescription(results.getString("description"));
                appointment.setAppointmentLocation(results.getString("location"));
                appointment.setAppointmentContact(results.getString("contact"));
                
                appointmentsForWeek.add(appointment);

            }
            return appointmentsForWeek;
            
        } catch (SQLException e) {
            System.out.println("Query for Appointments by Week failed: " + e.getMessage());
            return null;
        }
        
    }
    
    public static Appointment getAppointmentsIn15Minutes() {
   
        appointmentsIn15.clear();
        try {
            Statement statement = DBConnection.getConnection().createStatement();

            String query = "SELECT * FROM appointment WHERE start BETWEEN '" + ldt1 + "' AND '" + ldt2 + "'";
            
            ResultSet results = statement.executeQuery(query);
                        
            if(results.next()) {
                              
                Appointment appointment = new Appointment();
                
                appointment.setAppointmentId(results.getInt("appointmentId"));
                appointment.setAppointmentCustomerId(results.getInt("customerId"));
                appointment.setAppointmentStart(results.getString("start"));
                appointment.setAppointmentEnd(results.getString("end"));
                appointment.setAppointmentTitle(results.getString("title"));
                appointment.setAppointmentDescription(results.getString("description"));
                appointment.setAppointmentLocation(results.getString("location"));
                appointment.setAppointmentContact(results.getString("contact"));
                
                appointmentsIn15.add(appointment);
                
                return appointment;
            }
            
        } catch (SQLException e) {
            System.out.println("Query for Appointments in 15 minutes failed:" + e.getMessage());
        }
        return null;
        
    } 
    
    public static boolean newAppointment(String name, String title, String description, String contact, 
            String location, Timestamp start, Timestamp end) {
        
        try {
            int id = Customer.customerIdFromCustomerName(name);
            
            newAppointment = DBConnection.getConnection().prepareStatement(INSERT_NEW_APPOINTMENT);
            newAppointment.setInt(1, id);
            newAppointment.setString(2, title); //Appointment Type
            newAppointment.setString(3, description);
            newAppointment.setString(4, contact);
            newAppointment.setString(5, location);
            newAppointment.setTimestamp(6, start);
            newAppointment.setTimestamp(7, end);
            newAppointment.setString(8, currentUser);
            newAppointment.setString(9, currentUser);
                        
            int results = newAppointment.executeUpdate();
            
            if(results == 1) {
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println("Adding new appointment failed: " + e.getMessage());
        }
        return false;   
        
    }
    
    public static boolean removeAppointment(int id) {
        
       try {
           removeAppointment = DBConnection.getConnection().prepareStatement(REMOVE_APPOINTMENT_QUERY);
           removeAppointment.setInt(1, id);
           
           int updateOne = removeAppointment.executeUpdate();
           
           if(updateOne >= 1) {
               return true;
           } else {
               return false;
           }
           
       } catch (SQLException e) {
           System.out.println("Delete appointment query failed: " +e.getMessage());
           return false;
       }
       
    }
    
    public static int checkForDuplicateAppointment(String start, String end) {
        
        try {
            duplicateAppointment = DBConnection.getConnection().prepareStatement(DUPLICATE_APPOINTMENT_QUERY);
            duplicateAppointment.setString(1, start);
            duplicateAppointment.setString(2, end);
            duplicateAppointment.setString(3, start);
            duplicateAppointment.setString(4, end);
            
            ResultSet results = duplicateAppointment.executeQuery();
            
            if(results.next()) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Error running duplicate appointment query: " + e.getMessage());
            return -1;
        }
        
    }
    
    public static void clearAppointmentLists() {
        
        appointmentsForWeek.clear();
        appointmentsForMonth.clear();
        appointmentsIn15.clear();
        
    } 
}
