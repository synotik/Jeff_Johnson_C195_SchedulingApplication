/*
 *
 * Created by Jeff Johnson - November 2018
 *
 */
package Models;

import java.io.*;
import java.time.ZonedDateTime;

public class LogUtility {
    
    private static final String FILENAME = "logfile.txt";
    
    public static void log (String username, boolean success) {
        
        try (FileWriter fw = new FileWriter(FILENAME, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)) {
            pw.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure"));
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
        
    }
}
