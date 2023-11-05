package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.PrintWriter;
import java.io.StringWriter;
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

    // Method to get raw 2021 data from db (Level 2)
    public ArrayList<OverviewData> getRawData2021(String granularity, String population, String topic, String sort, String categoryToSort) {
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
            if (topic.equals("Population")) {
                categoryCol = "age_category";
            } else if (topic.equals("LTHC")) {
                categoryCol = "Condition";
            } else if (topic.equals("SchoolCompletion")) {
                categoryCol = "SchoolYear";
            } else if (topic.equals("NonSchoolCompletion")) {
                categoryCol = "NonSchoolBracket";
            }

            // Get the sorting attribute by topic
            String sortByAttr = "";
            if (topic.equals("Population")) {
                // Filter to sorting population results
                if (categoryToSort.equals("All Ages")) {
                    sortByAttr = "";
                } else {
                    sortByAttr = "AND age_category = '" + categoryToSort + "'";
                }
            } else if (topic.equals("LTHC")) {
                // Filter to sort health results
                if (categoryToSort.equals("all health issues")) {
                    sortByAttr = "";
                } else {
                    sortByAttr = "AND Condition = '" + categoryToSort + "'";
                }
            } else if (topic.equals("SchoolCompletion")) {
                // Filter to sort school results
                if (categoryToSort.equals("Attended school - any year of completion")) {
                    sortByAttr = "AND SchoolYear <> 'Did not go to school'";
                } else {
                    sortByAttr = "AND SchoolYear = '" + categoryToSort + "'";
                }
            } else if (topic.equals("NonSchoolCompletion")) {
                // Filter to sort non-school results
                if (categoryToSort.equals("All Non-School Levels")) {
                    sortByAttr = "";
                } else {
                    sortByAttr = "AND NonSchoolBracket = '" + categoryToSort + "'";
                }
            }

            String query = "";
            
            if (granularity.equals("State or Territory")) {
                // The Query - State
                query = "SELECT l.state_abbr, topic." + categoryCol + ", SUM(topic.count) AS raw_values, topic2.totalToSort FROM (" + topic + " topic "
                    + "JOIN LGA l ON topic.lga_code=l.code "
                    + "AND topic.lga_year = l.year) "
                    + "JOIN (SELECT li.state_abbr, SUM(topic_i.count) AS totalToSort FROM " + topic + " topic_i "
                    +     "JOIN LGA li ON topic_i.lga_code=li.code AND topic_i.lga_year = li.year "
                    +     "WHERE topic_i.indigenous_status = '" + population + "' AND topic_i.lga_year = '2021' " + sortByAttr + " "
                    +     "GROUP BY li.state_abbr) AS topic2 "
                    + "ON l.state_abbr = topic2.state_abbr "
                    + "WHERE topic.indigenous_status = '" + population + "' AND l.year = '2021' "
                    + "GROUP BY l.state_abbr, topic." + categoryCol + " "
                    + "ORDER BY topic2.totalToSort " + sort + ";";
            } else {
            // The Query - LGA
                query = "SELECT l.name, topic." + categoryCol + ", SUM(topic.count) AS raw_values, topic2.totalToSort FROM (" + topic + " topic "
                    + "JOIN LGA l ON topic.lga_code=l.code "
                    + "AND topic.lga_year = l.year) "
                    + "JOIN (SELECT topic_i.lga_code, SUM(topic_i.count) AS totalToSort FROM " + topic + " topic_i "
                    +     "WHERE topic_i.indigenous_status = '" + population + "' AND topic_i.lga_year = '2021' " + sortByAttr + " "
                    +     "GROUP BY topic_i.lga_code) AS topic2 "
                    + "ON l.code = topic2.lga_code "
                    + "WHERE topic.indigenous_status = '" + population + "' AND l.year = '2021' "
                    + "GROUP BY l.name, topic." + categoryCol + " "
                    + "ORDER BY topic2.totalToSort " + sort + ";";
            }

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            String locationColumn = "";
            if (granularity.equals("State or Territory")) {
                locationColumn = "state_abbr";
            } else {
                locationColumn = "name";
            }
            while (results.next()) {
                // Lookup the columns we need
                String location    = results.getString(locationColumn);
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

    // Method to get prop 2021 data from db (Level 2)
    public ArrayList<OverviewData> getPropData2021(String granularity, String population, String topic, String sort, String categoryToSort) {
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
            if (topic.equals("Population")) {
                categoryCol = "age_category";
            } else if (topic.equals("LTHC")) {
                categoryCol = "Condition";
            } else if (topic.equals("SchoolCompletion")) {
                categoryCol = "SchoolYear";
            } else if (topic.equals("NonSchoolCompletion")) {
                categoryCol = "NonSchoolBracket";
            }

            // Get the sorting attribute by topic
            String sortByAttr = "";
            if (topic.equals("Population")) {
                // Filter to sorting population results
                if (categoryToSort.equals("All Ages")) {
                    sortByAttr = "";
                } else {
                    sortByAttr = "AND age_category = '" + categoryToSort + "'";
                }
            } else if (topic.equals("LTHC")) {
                // Filter to sort health results
                if (categoryToSort.equals("all health issues")) {
                    sortByAttr = "";
                } else {
                    sortByAttr = "AND Condition = '" + categoryToSort + "'";
                }
            } else if (topic.equals("SchoolCompletion")) {
                // Filter to sort school results
                if (categoryToSort.equals("Attended school - any year of completion")) {
                    sortByAttr = "AND SchoolYear <> 'Did not go to school'";
                } else {
                    sortByAttr = "AND SchoolYear = '" + categoryToSort + "'";
                }
            } else if (topic.equals("NonSchoolCompletion")) {
                // Filter to sort non-school results
                if (categoryToSort.equals("All Non-School Levels")) {
                    sortByAttr = "";
                } else {
                    sortByAttr = "AND NonSchoolBracket = '" + categoryToSort + "'";
                }
            }

            String query = "";

            if (granularity.equals("State or Territory")) {
                // The Query - State
                query = "SELECT a.state_abbr AS state_abbr, a." + categoryCol + ", (a.total / b.totalPop) * 100 AS prop "
                    // Raw values
                    + "FROM ( "
                    + "SELECT lga.state_abbr, " + categoryCol + ", "
                    + "CAST(COALESCE(SUM(CASE WHEN indigenous_status = '" + population + "' THEN count ELSE 0 END), 0) AS FLOAT) AS total "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.state_abbr, " + categoryCol + " ) "
                    + "AS a "
                    + "JOIN ( "
                    // Population values (to get proportional)
                    + "SELECT lga.state_abbr, CAST(SUM(CASE WHEN indigenous_status = '" + population + "' THEN count ELSE 0 END) AS FLOAT) AS totalPop "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.state_abbr ) "
                    + "AS b "
                    + "ON a.state_abbr = b.state_abbr "
                    + "Join ("
                    // Raw values for the sorting attribute only (to sort it by that attribute)
                    + "SELECT a.state_abbr AS state_abbr, (a.total / b.totalPop) * 100 AS totalToSort "
                    + "FROM ( "
                    + "SELECT lga.state_abbr, "
                    + "CAST(COALESCE(SUM(CASE WHEN indigenous_status = '" + population + "' " + sortByAttr + " THEN count ELSE 0 END), 0) AS FLOAT) AS total "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.state_abbr) "
                    + "AS a "
                    + "JOIN ( "
                    // Population values to get the proportional value to sort by
                    + "SELECT lga.state_abbr, CAST(SUM(CASE WHEN indigenous_status = '" + population + "' THEN count ELSE 0 END) AS FLOAT) AS totalPop "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.state_abbr ) "
                    + "AS b "
                    + "ON a.state_abbr = b.state_abbr) AS topic2 ON a.state_abbr = topic2.state_abbr "
                    + "ORDER BY topic2.totalToSort " + sort + ";";
            } else {
            // The Query - LGA
            query = "SELECT a.name AS name, a." + categoryCol + ", (a.total / b.totalPop) * 100 AS prop "
                // Raw values
                    + "FROM ( "
                    + "SELECT lga.name, " + categoryCol + ", "
                    + "CAST(COALESCE(SUM(CASE WHEN indigenous_status = '" + population + "' THEN count ELSE 0 END), 0) AS FLOAT) AS total "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.name, " + categoryCol + " ) "
                    + "AS a "
                    + "JOIN ( "
                    // Population values (to get proportional)
                    + "SELECT lga.name, CAST(SUM(CASE WHEN indigenous_status = '" + population + "' THEN count ELSE 0 END) AS FLOAT) AS totalPop "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.name ) "
                    + "AS b "
                    + "ON a.name = b.name "
                    + "Join ("
                    // Raw values for the sorting attribute only (to sort it by that attribute)
                    + "SELECT a.name AS name, (a.total / b.totalPop) * 100 AS totalToSort "
                    + "FROM ( "
                    + "SELECT lga.name, "
                    + "CAST(COALESCE(SUM(CASE WHEN indigenous_status = '" + population + "' " + sortByAttr + " THEN count ELSE 0 END), 0) AS FLOAT) AS total "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.name) "
                    + "AS a "
                    + "JOIN ( "
                    // Population values to get the proportional value to sort by
                    + "SELECT lga.name, CAST(SUM(CASE WHEN indigenous_status = '" + population + "' THEN count ELSE 0 END) AS FLOAT) AS totalPop "
                    + "FROM lga "
                    + "JOIN " + topic + " ON lga_code = code AND lga_year = year "
                    + "WHERE year = 2021 "
                    + "GROUP BY lga.name ) "
                    + "AS b "
                    + "ON a.name = b.name) AS topic2 ON a.name = topic2.name "
                    + "ORDER BY topic2.totalToSort " + sort + ";";
            }
            System.out.println(query);
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            String locationColumn = "";
            if (granularity.equals("State or Territory")) {
                locationColumn = "state_abbr";
            } else {
                locationColumn = "name";
            }
            while (results.next()) {
                // Lookup the columns we need
                String location    = results.getString(locationColumn);
                String category    = results.getString(categoryCol);
                float propCount    = results.getInt("prop");

                // Create a OverviewData Object
                OverviewData dataPoint = new OverviewData(location, category, propCount);

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
    
    public ArrayList<LTHC> getLTHCgraph() {
        // Create the ArrayList of TeamMember objects to return
        ArrayList<LTHC> LTHC = new ArrayList<LTHC>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select Condition, sum(case when indigenous_status = 'indig' then count else 0 end) as indig_count, sum(case when indigenous_status = 'non_indig' then count else 0 end) as non_indig_count from lthc group by condition; ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String condition           = results.getString("Condition");
                int indig_count  = results.getInt("indig_count");
                int non_indig_count     = results.getInt("non_indig_count");

                // Create a LTHC Object
                LTHC LongTerm = new LTHC(condition, indig_count, non_indig_count);

                // Add the LTHC object to the array
                LTHC.add(LongTerm);
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

        return LTHC;
    }

    public ArrayList<NonSchoolCompletion> getNSEgraph() {
        // Create the ArrayList of TeamMember objects to return
        ArrayList<NonSchoolCompletion> NSE = new ArrayList<NonSchoolCompletion>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select NonSchoolBracket, sum(case when indigenous_status = 'indig' then count else 0 end) as indig_count, sum(case when indigenous_status = 'non_indig' then count else 0 end) as non_indig_count from NonSchoolCompletion where lga_year = 2021 group by NonSchoolBracket;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String education           = results.getString("NonSchoolBracket");
                int indig_count  = results.getInt("indig_count");
                int non_indig_count     = results.getInt("non_indig_count");

                // Create a LTHC Object
                NonSchoolCompletion NonSchoolCompletion = new NonSchoolCompletion(education, indig_count, non_indig_count);

                // Add the LTHC object to the array
                NSE.add(NonSchoolCompletion);
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

        return NSE;
    }

    public ArrayList<SchoolCompletion> getSEgraph() {
        // Create the ArrayList of TeamMember objects to return
        ArrayList<SchoolCompletion> SE = new ArrayList<SchoolCompletion>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select SchoolYear, sum(case when indigenous_status = 'indig' then count else 0 end) as indig_count, sum(case when indigenous_status = 'non_indig' then count else 0 end) as non_indig_count from SchoolCompletion where lga_year = 2021 group by SchoolYear;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String SchoolYear           = results.getString("SchoolYear");
                int indig_count  = results.getInt("indig_count");
                int non_indig_count     = results.getInt("non_indig_count");

                SchoolCompletion SchoolCompletion = new SchoolCompletion(SchoolYear, indig_count, non_indig_count);

                SE.add(SchoolCompletion);
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

        return SE;
    }

    public ArrayList<totalPop> getTotalPop() {
    // Create the ArrayList of TeamMember objects to return
    ArrayList<totalPop> totalPops = new ArrayList<totalPop>();

    // Setup the variable for the JDBC connection
    Connection connection = null;

    try {
        // Connect to JDBC data base
        connection = DriverManager.getConnection(DATABASE);

        // Prepare a new SQL Query & Set a timeout
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        // The Query
        String query = "Select indigenous_status, sum(CASE WHEN lga_year = 2016 THEN count else 0 end) as \"2016_pop\", sum(CASE WHEN lga_year = 2021 THEN count else 0 end) as \"2021_pop\" from population group by indigenous_status;";
        
        // Get Result
        ResultSet results = statement.executeQuery(query);

        // Process all of the results
        while (results.next()) {
            // Lookup the columns we need
            String stat           = results.getString("indigenous_status");
            int total2016  = results.getInt("2016_pop");
            int total2021     = results.getInt("2021_pop");

            totalPop totalPop = new totalPop(stat, total2016, total2021);

            totalPops.add(totalPop);
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

    return totalPops;
}

    public ArrayList<totalPop> getTotalStatePop() {
        // Create the ArrayList of TeamMember objects to return
        ArrayList<totalPop> totalPops = new ArrayList<totalPop>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select State_abbr as state, sum(count) as pop2021 from lga join population on code=lga_code where year=2021 and lga_year=2021 group by State_abbr;;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String state           = results.getString("state");
                int total2021     = results.getInt("pop2021");

                totalPop totalPop = new totalPop(state, total2021);

                totalPops.add(totalPop);
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

        return totalPops;
    }

    public int getTotalLga(String year) {
        // Setup the variable for the JDBC connection
        Connection connection = null;
        int total = 0;
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT count(*) as total from LGA where year="+year+";";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                total     = results.getInt("total");
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

        return total;
    }

    // Method to get the categories for each topic
    public ArrayList<String> getCategories(String topic) {
        // Create the ArrayList of Strings to return
        ArrayList<String> categories = new ArrayList<String>();

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
            if (topic.equals("Population")) {
                categoryCol = "age_category";
            } else if (topic.equals("LTHC")) {
                categoryCol = "Condition";
            } else if (topic.equals("SchoolCompletion")) {
                categoryCol = "SchoolYear";
            } else if (topic.equals("NonSchoolCompletion")) {
                categoryCol = "NonSchoolBracket";
            }

            // The Query 
            String query = "SELECT DISTINCT " + categoryCol + " FROM " + topic + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String category    = results.getString(categoryCol);

                // Add the category to the array
                categories.add(category);
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

        // Finally we return all of the categories
        return categories;
    }
    
    // Method to get the LGA names
    public ArrayList<String> getLGANames(String year) {
        // Create the ArrayList of Strings to return
        ArrayList<String> LGANames = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query 
            String query = "SELECT DISTINCT name FROM LGA WHERE year = '" + year + "' ORDER BY name;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");

                // Add the name to the array
                LGANames.add(name);
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

        // Finally we return all of the LGA Names
        return LGANames;
    }

    // Method to get population, school completion OR non-school completion gap (Level 3)
    public ArrayList<Deepdive> getGap(String gender, String indigStatus, String sort, String topic, String categories, String minRange, String maxRange) {
        // Create the ArrayList of Deepdive objects to return
        ArrayList<Deepdive> deepdiveDataPoints = new ArrayList<Deepdive>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        // Get category filter string by topic
            String categoryFilter = "";
            if (topic.equals("Population")) {
                categoryFilter = "AND age_min >= " + minRange + " and age_max <= " + maxRange;
            }  else if (topic.equals("SchoolCompletion")) {
                categoryFilter = "AND yearMax >= " + minRange + " and yearMax <= " + maxRange;
            } else if (topic.equals("NonSchoolCompletion")) {
                categoryFilter = "AND topic.NonSchoolBracket IN (" + categories + ")";
            }

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // The Query
            String query = ""
            + "SELECT lga.name, "
            + "Sum(Case when topic.indigenous_status IN (" + indigStatus + ") and topic.lga_year = 2016 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) as 'Number of people (2016)', "
            + "Sum(Case when topic.indigenous_status IN (" + indigStatus + ") and topic.lga_year = 2021 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) as 'Number of people (2021)', "

            + "(sum(Case when topic.indigenous_status='non_indig' and topic.lga_year = 2016 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) - "
            + "sum(Case when topic.indigenous_status='indig' and topic.lga_year = 2016 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end)) as Gap2016 , "

            + "(sum(Case when topic.indigenous_status='non_indig' and topic.lga_year = 2021 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) - "
            + "sum(Case when topic.indigenous_status='indig' and topic.lga_year = 2021 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end)) as Gap2021, "

            + "(sum(Case when topic.indigenous_status='non_indig' and topic.lga_year = 2021 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) - "
            + "sum(Case when topic.indigenous_status='indig' and topic.lga_year = 2021 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end)) - "
            + "(sum(Case when topic.indigenous_status='non_indig' and topic.lga_year = 2016 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) - "
            + "sum(Case when topic.indigenous_status='indig' and topic.lga_year = 2016 and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end)) As improv "

            + "From lga join " + topic + " as topic on code = topic.lga_code and year=topic.lga_year group by lga.name order by improv " + sort + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String lgaName       = results.getString("name");
                int numPeople2016    = results.getInt("Number of people (2016)");
                int numPeople2021    = results.getInt("Number of people (2021)");
                int gap2016          = results.getInt("Gap2016");
                int gap2021          = results.getInt("Gap2021");
                int improv           = results.getInt("improv");

                // Create a Deepdive Object
                Deepdive dataPoint = new Deepdive(lgaName, numPeople2016, numPeople2021, gap2016, gap2021, improv);

                // Add the Deepdive object to the array
                deepdiveDataPoints.add(dataPoint);
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
        return deepdiveDataPoints;
    }

    // Method to get long term health condition gap 2021 (Level 3)
    public ArrayList<Deepdive> getHealthGap2021(String gender, String indigStatus, String sort, String categories) {
        // Create the ArrayList of Deepdive objects to return
        ArrayList<Deepdive> deepdiveDataPoints = new ArrayList<Deepdive>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // The Query
            String query = ""
            + "SELECT lga.name, "
            + "Sum(Case when topic.indigenous_status IN (" + indigStatus + ") and topic.lga_year = 2021 and topic.sex in (" + gender + ") AND topic.Condition IN (" + categories + ") then count else 0 end) as 'Number of people (2021)', "

            + "(sum(Case when topic.indigenous_status='indig' and topic.lga_year = 2021 and topic.sex in (" + gender + ") AND topic.Condition IN (" + categories + ") then count else 0 end) - "
            + "sum(Case when topic.indigenous_status='non_indig' and topic.lga_year = 2021 and topic.sex in (" + gender + ") AND topic.Condition IN (" + categories + ") then count else 0 end)) as Gap2021 "

            + "From lga join LTHC as topic on code = topic.lga_code and year=topic.lga_year group by lga.name order by Gap2021 " + sort + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String lgaName       = results.getString("name");
                int numPeople2021    = results.getInt("Number of people (2021)");
                int gap2021          = results.getInt("Gap2021");

                // Create a Deepdive Object
                Deepdive dataPoint = new Deepdive(lgaName, numPeople2021, gap2021);

                // Add the Deepdive object to the array
                deepdiveDataPoints.add(dataPoint);
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
        return deepdiveDataPoints;
    }
    public ArrayList<Deepdive> getSimilarLga(String gender, String indigStatus, String topic, String categories, String minRange, String maxRange, String censusYear, String lga, String numberOfLGA) {
        // Create the ArrayList of Deepdive objects to return
        ArrayList<Deepdive> deepdiveDataPoints = new ArrayList<Deepdive>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        // Get category filter string by topic
            String categoryFilter = "";
            if (topic.equals("Population")) {
                categoryFilter = "AND age_min >= " + minRange + " and age_max <= " + maxRange;
            }  else if (topic.equals("SchoolCompletion")) {
                categoryFilter = "AND yearMax >= " + minRange + " and yearMax <= " + maxRange;
            } else if (topic.equals("NonSchoolCompletion")) {
                categoryFilter = "AND topic.NonSchoolBracket IN (" + categories + ")";
            } else if (topic.equals("LTHC")) {
                categoryFilter = "AND topic.Condition IN (" + categories + ")";
            }

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            int numberLGA = Integer.parseInt(numberOfLGA) + 1;
            // The Query
            String query = ""
            + "SELECT lga.name, "
            + "Sum(Case when topic.indigenous_status IN (" + indigStatus + ") and topic.lga_year = '" + censusYear + "' and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) as 'Number of people', "
            + "abs(Sum(Case when topic.indigenous_status IN (" + indigStatus + ") and topic.lga_year = '" + censusYear + "' and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) "
            + "- (SELECT Sum(Case when topic.indigenous_status IN (" + indigStatus + ") and topic.lga_year = '" + censusYear + "' and topic.sex in (" + gender + ") " + categoryFilter + " then count else 0 end) "
            + "From lga join "+ topic +" as topic on code = topic.lga_code and year=topic.lga_year where lga.name='" + lga + "' group by lga.name)) as abs "
            + "From lga join " + topic + " as topic on code = topic.lga_code and year=topic.lga_year group by lga.name order by abs asc limit " + numberLGA + ";";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String lgaName       = results.getString("name");
                int numPeople        = results.getInt("Number of people");

                // Create a Deepdive Object
                Deepdive dataPoint = new Deepdive(lgaName, numPeople);

                // Add the Deepdive object to the array
                deepdiveDataPoints.add(dataPoint);
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
        return deepdiveDataPoints;
    }

}
