package helper;

import java.io.File;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * You can run this file using the "Run" or "Debug" options
 * from within VSCode. This won't conflict with the web server.
 * <p>
 * This program opens a CSV file from the Closing-the-Gap data set
 * and uses JDBC to load up data into the database.
 * <p>
 * To use this program you will need to change:
 * 1. The input file location
 * 2. The output file location
 * <p>
 * This assumes that the CSV files are the the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there will be a lot
 * of SQL insert statments!
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au

 */
public class VTPProcessCSV {

   // MODIFY these to load/store to/from the correct locations
   private static final String DATABASE = "jdbc:sqlite:database/vtp.db";
   private static final String LGAS_2016_CSV_FILE = "database/lgas_2016.csv";
   private static final String LGAS_2021_CSV_FILE = "database/lgas_2021.csv";
   private static final String AGE_2016_CSV_FILE = "database/lga_indigenous_status_by_age_by_sex_census_2016.csv";
   private static final String AGE_2021_CSV_FILE = "database/lga_indigenous_status_by_age_by_sex_census_2021.csv";
   private static final String HEALTH_2021_CSV_FILE = "database/lga_long_term_health_conditions_by_indigenous_status_by_sex_2021.csv";
   private static final String SCHOOL_2016_CSV_FILE = "database/lga_highest_year_of_school_completed_by_indigenous_status_by_sex_2016.csv";
   private static final String SCHOOL_2021_CSV_FILE = "database/lga_highest_year_of_school_completed_by_indigenous_status_by_sex_2021.csv";
   private static final String NONSCHOOL_2016_CSV_FILE = "database/lga_non_school_education_by_indigenous_status_by_sex_census_2016.csv";
   private static final String NONSCHOOL_2021_CSV_FILE = "database/lga_non_school_education_by_indigenous_status_by_sex_census_2021.csv";

   public static void main (String[] args) {
      populateLGATable(LGAS_2016_CSV_FILE, 2016);
      populateLGATable(LGAS_2021_CSV_FILE, 2021);
      populatePopulationTable(AGE_2016_CSV_FILE, 2016);
      populatePopulationTable(AGE_2021_CSV_FILE, 2021);
      populateSchoolTable(SCHOOL_2016_CSV_FILE, 2016);
      populateSchoolTable(SCHOOL_2021_CSV_FILE, 2021);
      populateNonSchoolTable(NONSCHOOL_2016_CSV_FILE, 2016);
      populateNonSchoolTable(NONSCHOOL_2021_CSV_FILE, 2021);
      //To Do: Add Health
   }

   private static void populateLGATable(String filename, int censusYear) {      
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing

      //The *first digit* of the LGA code provides the State or Territory that the LGA is located within:
      String state[] = {
         "NSW", //1
         "VIC", //2
         "QLD", //3
         "SA", //4
         "WA", //5
         "TAS", //6
         "NT", //7
         "ACT", //8
         "Other" //9
      };

      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         // Open the CSV File to process, one line at a time
         Scanner lineScanner = new Scanner(new File(filename));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // Save the lga_code
            String lgaCode = rowScanner.next();

            // Save the lga_name
            String lgaName = rowScanner.next();
            rowScanner.close();

            // Determine lga_state
            char lgaStateNum = lgaCode.charAt(0);
            int lgaStateIndex = Character.getNumericValue(lgaStateNum) - 1;
            String lgaState = state[lgaStateIndex];

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();

            // Create Insert Statement
            String query = "INSERT into LGA VALUES ("
                           + lgaCode + ","
                           + censusYear + ","
                           + lgaName + "',"
                           + lgaState
                           + ")";

            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }      

   }

   private static void populatePopulationTable(String filename, int censusYear) {
      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category[] = {
         "_0_4",
         "_5_9",
         "_10_14",
         "_15_19",
         "_20_24",
         "_25_29",
         "_30_34",
         "_35_39",
         "_40_44",
         "_45_49",
         "_50_54",
         "_55_59",
         "_60_64",
         "_65_yrs_ov"
      };
      String status[] = {
         "indig",
         "non_indig",
         "indig_ns"
      };
      String sex[] = {
         "f",
         "m"
      };
      int age_min[] = {
         0,
         5,
         10,
         15,
         20,
         25,
         30,
         35,
         40,
         45,
         50,
         55,
         60,
         65
      };
      int age_max[] = {
         4,
         9,
         14,
         19,
         24,
         29,
         34,
         39,
         44,
         49,
         54,
         59,
         64,
         200
      };

      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         // Open the CSV File to process, one line at a time
         Scanner lineScanner = new Scanner(new File(filename));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         //int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() && indexCategory < category.length) {
               String count = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into Population VALUES ("
                              + lgaCode + ","
                              + censusYear + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category[indexCategory] + "',"
                              + count + ","
                              + age_min[indexCategory] + ","
                              + age_max[indexCategory]
                              + ")";

               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               //row++;
            }
            rowScanner.close();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }      

   }

   private static void populateSchoolTable(String filename, int censusYear) {
      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category[] = {
         "didnt_go",
         "y8_below",
         "y9_equiv",
         "y10_equiv",
         "y11_equiv",
         "y12_equiv"
      };
      String status[] = {
         "indig",
         "non_indig",
         "indig_ns"
      };
      String sex[] = {
         "f",
         "m"
      };

      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         // Open the CSV File to process, one line at a time
         Scanner lineScanner = new Scanner(new File(filename));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         //int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() && indexCategory < category.length) {
               String count = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into SchoolCompletion VALUES ("
                              + lgaCode + ","
                              + censusYear + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category[indexCategory] + "',"
                              + count
                              + ")";

               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               //row++;
            }
            rowScanner.close();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }      

   }

   private static void populateNonSchoolTable(String filename, int censusYear) {
      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category[] = {
         "pd_gd_gc",
         "bd",
         "adip_dip",
         "ct_iii_iv",
         "ct_i_ii"
      };
      String status[] = {
         "indig",
         "non_indig",
         "indig_ns"
      };
      String sex[] = {
         "f",
         "m"
      };

      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         // Open the CSV File to process, one line at a time
         Scanner lineScanner = new Scanner(new File(filename));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         //int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() && indexCategory < category.length) {
               String count = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into NonSchoolCompletion VALUES ("
                              + lgaCode + ","
                              + censusYear + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category[indexCategory] + "',"
                              + count
                              + ")";

               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               //row++;
            }
            rowScanner.close();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }      

   }
   
}
