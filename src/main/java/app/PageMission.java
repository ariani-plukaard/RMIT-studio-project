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
        String html = "<html lang='en'>";

        // Add some Head information
        html = html + "<head>" + """
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>About Us</title>
        """;

        // Add some CSS (external file) and the Bootstrap CSS
        html = html + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN' crossorigin='anonymous'>";
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
                <div class='buttons'>
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
        """;

        // Using JDBC to lookup the Personas
        JDBCConnection jdbc = new JDBCConnection();

        // Add the HTML for the personas carousel
        html = html + """
            <h2>Personas of our Customers</h2>
            <div id="personasCarousel" class="carousel slide my-4" data-bs-theme="dark">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#personasCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#personasCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#personasCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
                    <button type="button" data-bs-target="#personasCarousel" data-bs-slide-to="3" aria-label="Slide 4"></button>
                </div>
                <div class="carousel-inner">
        """;

        // Get the personas and add them into the HTML for carousel
        ArrayList<Persona> personas = jdbc.getPersonas();
        boolean active = true;
        for (Persona persona : personas) {
            if (active) {
                html = html + "<div class='carousel-item active'>";
                active = false;
            } else {
                html = html + "<div class='carousel-item'>";
            }
            html = html + "<img src='" + persona.getImageFilePath() + "' class='d-block mx-auto w-50 py-4' alt='" + persona.getName() + "'>"
                        + "<div class='d-block pb-5 text-center'>"
                        +     "<h5>" + persona.getName() + "</h5>"
                        +     "<p><b>Attributes: </b>" + persona.getAttributes() + "</p>"
                        +     "<p><b>Background: </b>" + persona.getBackground() + "</p>"
                        +     "<p><b>Needs: </b>" + persona.getNeeds() + "</p>"
                        +     "<p><b>Goals: </b>" + persona.getGoals() + "</p>"
                        +     "<p><b>Skills & Experience: </b>" + persona.getSkillsExp() + "</p>"
                        + "</div>"
                    + "</div>";
        }
        
        //Add the rest of the carousel HTML
        html = html + """
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#personasCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#personasCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        """;

        // Add the HTML for the team members
        ArrayList<TeamMember> teamMembers = jdbc.getTeamMembers();
        html = html + "<h2>Design Team</h2>";
        for (TeamMember teamMember : teamMembers) {
            html = html + "<p>" + teamMember.getName() + " - " 
                        + teamMember.getStudentNo() + ", "
                        + "<i>" + teamMember.getEmail() + "</i></p>";
        }

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

        // Add Bootstrap JavaScript bundle and finish the HTML webpage
        html = html + "<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js' integrity='sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL' crossorigin='anonymous'></script>";
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
