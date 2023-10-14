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
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

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
                    <a href = '/'><img src='logo.png' class='top-image' alt='RMIT logo' height='70'></a>
                </div>
                <div class ='buttons'>
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
            <div class='wrapper'>
                <div class='wrapperText'>
                    <h2>Exploring the gap</h2>
                    <p>Between Indigineous & Non-Indigineous Australians</p>
                    <a href='page2A.html'>DATA OVERVIEW</a>
                </div>
                <div class='wrapperImg'>
                    <img src='vop.png' alt='vop' height='1200' width='672'>
                </div>
                
            </div>
        """;

        // Add HTML for the page content
        html = html + """
            <div class='content'>
                <div class='contentwrap'>
                    <div>
                        <h2>Our goal</h2>
                        <p>Our goal is to provide users with unbiased data to help educate and them about 
                        The Gap between Indigenous and Non-Indigenous Australians in the lead up to the 
                        Voice to Parliament Referendum</p>
                        <a href='mission.html'>Mission Statement</a>
                    </div>
                    <div id='qna'>
                        <h2>What we cover</h2>
                        <p>Our main focus are on the topics of <b>health, education, and income</b>, as these topics give us 
                        a greater understanding on the disparities between Indigenous and Non-Indigenous Australians</p>
                        <a href='page2A.html'>See Data Overview</a>
                    </div>
                </div>
                <div>

                </div>
            </div>
            """;

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
        //TO DO: create FAQ

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
