package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/data-deep-dive.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Data Deep Dive</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='page2.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <div class='logo'>
                    <a href = '/'><img src='logo.png' class='top-image' alt='RMIT logo' height='70'></a>
                </div>
                <div class ='buttons'>
                    <a href='/'>HOME</a>
                    <a href='data-overview.html'>DATA OVERVIEW</a>
                    <a href='data-deep-dive.html'>DATA DEEP DIVE</a>
                    <a href='mission.html'>ABOUT US</a>
                </div>
            </div>
        """;
        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Change in The Gap from 2016 to 2021 (by LGA)</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content - Filters
        html = html + "<h2>FILTERS</h2>";

        html = html + "<form action='/data-deep-dive.html' method='post'>";
        
        html = html + "   <div class = 'filter-box'>";

        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Topic</h3>";
        html = html + "      <input type='radio' id='topic1' name='topic' value='Population'>";
        html = html + "      <label for='topic1'>Age (Population)</label><br>";
        html = html + "      <input type='radio' id='topic2' name='topic' value='LTHC'>";
        html = html + "      <label for='topic2'>Long Term Health Conditions (LTHC)</label><br>";
        html = html + "      <input type='radio' id='topic3' name='topic' value='SchoolCompletion'>";
        html = html + "      <label for='topic3'>School Completion</label><br>";
        html = html + "      <input type='radio' id='topic4' name='topic' value='NonSchoolCompletion'>";
        html = html + "      <label for='topic4'>Non-School Completion</label><br>";
        html = html + "   </div>";
        
        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Population</h3>";
        html = html + "      <input type='checkbox' id='population1' name='population1' value='Indig'>";
        html = html + "      <label for='population1'>Indigenous</label><br>";
        html = html + "      <input type='checkbox' id='population2' name='population2' value='Non_Indig'>";
        html = html + "      <label for='population2'>Non-Indigenous</label><br>";
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Gender</h3>";
        html = html + "      <input type='checkbox' id='gender1' name='gender1' value='m'>";
        html = html + "      <label for='gender1'>Male</label><br>";
        html = html + "      <input type='checkbox' id='gender2' name='gender2' value='f'>";
        html = html + "      <label for='gender2'>Female</label><br>";
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Sort</h3>";
        html = html + "      <input type='radio' id='sort1' name='sort' value='Improved'>";
        html = html + "      <label for='sort1'>Most improved</label><br>";
        html = html + "      <input type='radio' id='sort2' name='sort' value='Declined'>";
        html = html + "      <label for='sort2'>Worst decline</label><br>";
        html = html + "   </div>";

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<String> healthConditions = jdbc.getCategories("LTHC");
        
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='health_drop'><h3>Health Issue</h3></label>";
        html = html + "      <select id='health_drop' name='health_drop' multiple>";
        for (String condition: healthConditions) {
            html = html + "         <option>" + condition + "</option>";;
        }
        html = html + "      </select>";
        html = html + "   </div>";

        ArrayList<String> nonSchoolCategories = jdbc.getCategories("NonSchoolCompletion");
        
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='nonSchool_drop'><h3>School Category</h3></label>";
        html = html + "      <select id='nonSchool_drop' name='nonSchool_drop' multiple>";
        for (String schoolLevel: nonSchoolCategories) {
            html = html + "         <option>" + schoolLevel + "</option>";;
        }
        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "   </div>";

        html = html + "   <button type='submit' class='pink-button'>APPLY FILTERS</button>";

        html = html + "</form>";

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                <div>
                    <ul>
                        <li><a href='https://www.rmit.edu.au/contact'>Contact</a></li>
                        <li><a href='mission.html'>About Us</a></li>
                        <li><a href='https://www.rmit.edu.au/utilities/terms'>Terms & Conditions</a></li>
                    </ul>
                </div>
                <div>
                    <a href ='https://twitter.com/rmit'><img src='twitter.png' alt='Twitter logo' height='18'></a>
                    <a href ='https://www.facebook.com/RMITuniversity'><img src='fb.png' alt='Facebook logo' height='18'></a>
                    <a href ='https://au.linkedin.com/school/rmit-university'><img src='linkedin.png' alt='LinkedIn logo' height='18'></a>
                    <a href ='https://www.instagram.com/rmituniversity/?hl=en'><img src='insta.png' alt='Instagram logo' height='18'></a>
                </div> 
                <div>
                    <ul>
                        <li><a href='https://www.abs.gov.au/census/find-census-data'>Data Sources</a></li>
                        <li><a href='/'>FAQ</a></li>
                    </ul>
                </div>     
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
