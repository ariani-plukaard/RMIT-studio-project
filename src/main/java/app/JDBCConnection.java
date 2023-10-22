package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs2016() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE year='2016'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("code");
                String name  = results.getString("name");

                // Create a LGA Object
                LGA lga = new LGA(code, name, 2016);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    // TODO: Add your required methods here

    // Method to get personas from db
    public ArrayList<Persona> getPersonas() {
        // Create the ArrayList of Persona objects to return
        ArrayList<Persona> personas = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Persona";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name           = results.getString("name");
                String imageFilePath  = results.getString("imageFilePath");
                String attributes     = results.getString("attributes");
                String background     = results.getString("background");
                String needs          = results.getString("needs");
                String goals          = results.getString("goals");
                String skillsExp      = results.getString("skillsExp");

                // Create a Persona Object
                Persona persona = new Persona(name, imageFilePath, attributes, background, needs, goals, skillsExp);

                // Add the persona object to the array
                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the personas
        return personas;
    }

    // Method to get team members from db
    public ArrayList<TeamMember> getTeamMembers() {
        // Create the ArrayList of TeamMember objects to return
        ArrayList<TeamMember> teamMembers = new ArrayList<TeamMember>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM TeamMember";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String studentNo           = results.getString("studentNo");
                String name  = results.getString("name");
                String email     = results.getString("email");

                // Create a TeamMember Object
                TeamMember teamMember = new TeamMember(studentNo, name, email);

                // Add the TeamMember object to the array
                teamMembers.add(teamMember);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the team members
        return teamMembers;
    }

    // Method to get raw 2021 LGA data from db (Level 2)
    public ArrayList<OverviewData> getRawLGAData2021(String population, String topic, String sort) {
        // Create the ArrayList of OverviewData objects to return
        ArrayList<OverviewData> overviewDataPoints = new ArrayList<OverviewData>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get category column name by topic
            String categoryCol = "";
            if (topic == "Population") {
                categoryCol = "age_category";
            } else if (topic == "LTHC") {
                categoryCol = "Condition";
            } else if (topic == "SchoolCompletion") {
                categoryCol = "SchoolYear";
            } else if (topic == "NonSchoolCompletion") {
                categoryCol = "NonSchoolBracket";
            }

            // Get the sorting attribute by topic
            String sortByAttr = "";
            if (topic == "Population") {
                sortByAttr = "AND age_category = '_65_yrs_ov'"; // Population results will be sorted by the 65+ count
            } else if (topic == "LTHC") {
                sortByAttr = ""; // Health results will be sorted by the count for all health conditions
            } else if (topic == "SchoolCompletion") {
                sortByAttr = "AND SchoolYear = 'y12_equiv'"; // School results will be sorted by the year 12 count
            } else if (topic == "NonSchoolCompletion") {
                sortByAttr = "AND (NonSchoolBracket = 'bd' OR NonSchoolBracket = 'pd_gd_gc')"; // Non school results will be sorted by total count from bachelor and post grad
            }

            // The Query
            String query = "SELECT l.name, topic." + categoryCol + ", SUM(topic.count) AS raw_values, topic2.totalToSort FROM (" + topic + " topic "
                    + "JOIN LGA l ON topic.lga_code=l.code "
                    + "AND topic.lga_year = l.year) "
                    + "JOIN (SELECT topic_i.lga_code, SUM(topic_i.count) AS totalToSort FROM " + topic + " topic_i "
                    +     "WHERE topic_i.indigenous_status = '" + population + "' AND topic_i.lga_year = '2021' " + sortByAttr + " "
                    +     "GROUP BY topic_i.lga_code) AS topic2 "
                    + "ON l.code = topic2.lga_code "
                    + "WHERE topic.indigenous_status = '" + population + "' AND l.year = '2021' "
                    + "GROUP BY l.name, topic." + categoryCol + " "
                    + "ORDER BY topic2.totalToSort " + sort + ";";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String location    = results.getString("name");
                String category    = results.getString(categoryCol);
                int count          = results.getInt("raw_values");

                // Create a OverviewData Object
                OverviewData dataPoint = new OverviewData(location, category, count);

                // Add the OverviewData object to the array
                overviewDataPoints.add(dataPoint);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the data overview values
        return overviewDataPoints;
    }
}
