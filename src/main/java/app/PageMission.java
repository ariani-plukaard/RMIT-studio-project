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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>About Us</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <div class='logo'>
                    <img src='logo.png' class='top-image' alt='RMIT logo' height='18'>
                </div>
                <div>
                <a href='/'>HOME</a>
                <a href='page2A.html'>DATA OVERVIEW</a>
                <a href='page3A.html'>DATA DEEP DIVE</a>
                <a href='mission.html'>ABOUT US</a>
                </div>
            </div>
        """;
        //<a href='page2B.html'>Sub Task 2.B</a>
        //<a href='page3B.html'>Sub Task 3.B</a>

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>About Us - Mission Statement</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <h2>The Social Challenge</h2>
            <p>In the lead-up to the Voice to Parliament Referendum, voters are keen to learn about the Gap between Indigenous and Non-Indigenous Australians. 
            This website aims to address this issue by presenting unbiased data from the last two Australian Censuses (2016 and 2021), 
            focusing on The Gap in key socioeconomic areas including health, education and income. Depending on what you're looking for, 
            this website can give you an overall snapshot of the issue, a high level summary of data from the key topics or a deep dive into more detailed 
            and specific data.</p>
            <h2>How to Use this Site</h2>
            <ul>
                <li>On landing page you can see an overview of the statistics covered on the site</li>
                <li>From the landing page, navigate to the data overview page to view the data for 
                individual Local Government Areas or summarised by State/Territory for each socioeconomic topic</li>
                <li>On landing page you can see an overview of the statistics covered on the site</li>
                <li>For more detailed analysis, view the detailed data deep dive page for further sorting and filtering</li>
            </ul>
            <h2>Personas of our Customers</h2>    
            <p>Insert Personas Here</p>
            <h2>Design Team</h2>
            <p>Insert Team Here</p>
            
        """;

        // This example uses JDBC to lookup the LGAs
        //JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs
        //ArrayList<LGA> lgas = jdbc.getLGAs2016();

        // Add HTML for the LGA list
        //html = html + "<h1>All 2016 LGAs in the Voice to Parliament database (using JDBC Connection)</h1>" + "<ul>";

        // Finally we can print out all of the LGAs
        //for (LGA lga : lgas) {
        //    html = html + "<li>" + lga.getCode()
        //                + " - " + lga.getName() + "</li>";
        //}

        // Finish the List HTML
        //html = html + "</ul>";


        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                <div>
                    <a href='https://www.rmit.edu.au/contact'>Contact</a>
                    <a href='mission.html'>About Us</a>
                    <a href='https://www.rmit.edu.au/utilities/terms'>Terms & Conditions</a>
                </div>
                <div>
                    <img src='twitter.png' alt='Twitter logo' height='18'>
                    <img src='fb.png' alt='Facebook logo' height='18'>
                    <img src='linkedin.png' alt='LinkedIn logo' height='18'>
                    <img src='insta.png' alt='Instagram logo' height='18'>
                </div> 
                <div>
                    <a href='https://www.abs.gov.au/census/find-census-data'>Data Sources</a>
                    <a href='/'>FAQ</a>
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
