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
import java.sql.Timestamp;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Customer {

    public static String currentUser = LoginController.currentUser;
    
    private final SimpleIntegerProperty customerID;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty customerAddress;
    private final SimpleStringProperty customerCity;
    private final SimpleIntegerProperty customerZip;
    private final SimpleStringProperty customerPhone;
    
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<String> allCustomerNames = FXCollections.observableArrayList();
    
    private static final Timestamp DATE = new Timestamp(new java.util.Date().getTime());
    
    private static final String CUSTOMER_QUERY = "SELECT * FROM customer WHERE customerId = ?";
    private static final String ALL_CUSTOMER_QUERY = "SELECT customer.customerId, customer.customerName,"
            + " address.address, address.phone, address.postalCode, city.city FROM customer"
            + " INNER JOIN address ON customer.addressID = address.addressId"
            + " INNER JOIN city ON address.cityID = city.cityId ORDER BY customer.customerId";
    private static final String INSERT_NEW_CUSTOMER1 = "INSERT INTO address SET address = ?, phone = ?,"
            + " postalCode = ?, cityId = ?, createDate = ?, createdBy = ?, lastUpdateBy = ?";
    private static final String INSERT_NEW_CUSTOMER2 = "INSERT INTO customer SET customerName = ?, addressId = ?,"
            + " active = 1, createDate = ?, createdBy = ?, lastUpdateBy = ?";
    private static final String UPDATE_CUSTOMER1 = "UPDATE address SET address = ?, cityId = ?, postalCode = ?," 
            + " phone = ? WHERE addressId = ?";
    private static final String UPDATE_CUSTOMER2 = "UPDATE customer SET customerName = ?, addressId = ?"
            + " WHERE customerId = ?";
    private static final String REMOVE_CUSTOMER1 = "DELETE FROM address WHERE addressId = ?";
    private static final String REMOVE_CUSTOMER2 = "DELETE FROM customer WHERE customerId = ?";
    public static final String GET_CUSTOMERID_QUERY = "SELECT customerid FROM customer WHERE customerName = ?";
    
    private static PreparedStatement customerQuery;
    private static PreparedStatement allCustomerQuery;
    private static PreparedStatement newCustomer1;
    private static PreparedStatement newCustomer2;
    private static PreparedStatement updateCustomer1;
    private static PreparedStatement updateCustomer2;
    private static PreparedStatement removeCustomer1;
    private static PreparedStatement removeCustomer2;
    private static PreparedStatement getCustomerId;
    
    public Customer() {
        
        customerID = new SimpleIntegerProperty();
        customerName = new SimpleStringProperty();
        customerAddress = new SimpleStringProperty();
        customerCity = new SimpleStringProperty();
        customerZip = new SimpleIntegerProperty();
        customerPhone = new SimpleStringProperty();
        
    }

    public int getCustomerID() {
        
        return customerID.get();
        
    }

    public void setCustomerId(int customerId) {
        
        this.customerID.set(customerId);
        
    }

    public String getCustomerName() {
        
        return customerName.get();
        
    }

    public void setCustomerName(String customerName) {
        
        this.customerName.set(customerName);
        
    }

    public String getCustomerAddress() {
        
        return customerAddress.get();
        
    }

    public void setCustomerAddress(String customerAddress) {
        
        this.customerAddress.set(customerAddress);
        
    }

    public String getCustomerCity() {
        
        return customerCity.get();
        
    }

    public void setCustomerCity(String customerCity) {
        
        this.customerCity.set(customerCity);
        
    }

    public int getCustomerZip() {
        
        return customerZip.get();
        
    }

    public void setCustomerZip(int customerZip) {
        
        this.customerZip.set(customerZip);
        
    }

    public String getCustomerPhone() {
        
        return customerPhone.get();
        
    }

    public void setCustomerPhone(String customerPhone) {
        
        this.customerPhone.set(customerPhone);
        
    }
    
    public static Customer getCustomer(int id) {
        
        try {
            customerQuery = DBConnection.getConnection().prepareStatement(CUSTOMER_QUERY);
            customerQuery.setInt(1, id);
                        
            ResultSet results = customerQuery.executeQuery();
            
            if(results.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(results.getString("customerName"));
                return customer;
            } else {
                System.out.println("No customer found!");
            }
        } catch (SQLException e) {
            System.out.println("Get customer query failed: " +e.getMessage());
        }
        return null;
        
    }
    
    public static ObservableList<Customer> getAllCustomers() {
        
        allCustomers.clear();
        allCustomerNames.clear();
        try {
            allCustomerQuery = DBConnection.getConnection().prepareStatement(ALL_CUSTOMER_QUERY);
            
            ResultSet results = allCustomerQuery.executeQuery();
            
            while(results.next()) {
                Customer customer = new Customer();
                    customer.setCustomerId(results.getInt("customerId"));
                    customer.setCustomerName(results.getString("customerName"));
                    customer.setCustomerAddress(results.getString("address"));
                    customer.setCustomerCity(results.getString("city"));
                    customer.setCustomerZip(results.getInt("postalCode"));
                    customer.setCustomerPhone(results.getString("phone"));
                                                     
                allCustomers.add(customer);
                allCustomerNames.add(customer.getCustomerName());
            }
            return allCustomers;
        } catch (SQLException e) {
            System.out.println("Get all customer query failed: " + e.getMessage());
            return null;
        }
        
    }
    
    public static boolean newCustomer(String name, String address, int city, int zip, String phone) {
        
        try {
            newCustomer1 = DBConnection.getConnection().prepareStatement(INSERT_NEW_CUSTOMER1);
            newCustomer2 = DBConnection.getConnection().prepareStatement(INSERT_NEW_CUSTOMER2);
            
            newCustomer1 = DBConnection.getConnection().prepareStatement(INSERT_NEW_CUSTOMER1, Statement.RETURN_GENERATED_KEYS);
            newCustomer1.setString(1, address);
            newCustomer1.setString(2, phone);
            newCustomer1.setInt(3, zip);
            newCustomer1.setInt(4, city);
            newCustomer1.setTimestamp(5, DATE);
            newCustomer1.setString(6, currentUser);
            newCustomer1.setString(7, currentUser);
            
            int updateOne = newCustomer1.executeUpdate();
            
            if (updateOne == 1) {
                ResultSet results = newCustomer1.getGeneratedKeys();
                results.next();
                newCustomer2.setString(1, name);
                newCustomer2.setInt(2, results.getInt(1));
                newCustomer2.setTimestamp(3, DATE);
                newCustomer2.setString(4, currentUser);
                newCustomer2.setString(5, currentUser);
                int updateTwo = newCustomer2.executeUpdate();
                
                if(updateTwo == 1) {
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Inserting new customer failed: " + e.getMessage());
        }
        return false;
        
    }
    
    public static boolean updateCustomer(int id, String name, String address, int city, int zip, String phone) {
        
        try {
            updateCustomer1 = DBConnection.getConnection().prepareStatement(UPDATE_CUSTOMER1);
            updateCustomer2 = DBConnection.getConnection().prepareStatement(UPDATE_CUSTOMER2);
            
            updateCustomer1.setString(1, address);
            updateCustomer1.setInt(2, city);
            updateCustomer1.setInt(3, zip);
            updateCustomer1.setString(4, phone);
            updateCustomer1.setInt(5, id);
            
            int updateOne = updateCustomer1.executeUpdate();
            
            if(updateOne == 1) {
                updateCustomer2.setString(1, name);
                updateCustomer2.setInt(2, id);
                updateCustomer2.setInt(3, id);
                
                int updateTwo = updateCustomer2.executeUpdate();
                
                if(updateTwo == 2) {
                    return true;
                }
            }
           
        } catch (SQLException e) {
            System.out.println("Updating customer failed: " + e.getMessage());
        }
        return false;
        
    }
    
    public static boolean removeCustomer(int id) {
        
        try {
            removeCustomer1 = DBConnection.getConnection().prepareStatement(REMOVE_CUSTOMER1);
            removeCustomer2 = DBConnection.getConnection().prepareStatement(REMOVE_CUSTOMER2);
            
            removeCustomer1.setInt(1, id);
            
            int updateOne = removeCustomer1.executeUpdate();
            
            if(updateOne == 1) {
                removeCustomer2.setInt(1, id);
                
                int updateTwo = removeCustomer2.executeUpdate();
                
                if(updateTwo == 1) {
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Removing customer failed: " + e.getMessage());
        }
        return false;
        
    }
    
    public static int customerIdFromCustomerName(String name) throws SQLException {
        
        try {
            getCustomerId = DBConnection.getConnection().prepareStatement(GET_CUSTOMERID_QUERY);
            getCustomerId.setString(1, name);
            ResultSet results = getCustomerId.executeQuery();
            if (results.next()) {
                int foundCustomerId = results.getInt("customerid");
                return foundCustomerId;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("Find customer ID query failed: " + e.getMessage());
            return -1;
        }
        
    }
}
