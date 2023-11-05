package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;


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
                <a href='data-overview.html'>DATA OVERVIEW</a>
                <a href='data-deep-dive.html'>DATA DEEP DIVE</a>
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
        html = html + "<div class='content mission-info'>";

        // Add HTML for the page content
        html = html + """
            <h2>The Social Challenge</h2>
            <p>In the wake of the Voice to Parliament Referendum, Australians are keen to learn about the Gap between Indigenous and Non-Indigenous Australians. 
            This website aims to address this issue by presenting unbiased data from the last two Australian Censuses (2016 and 2021), 
            focusing on The Gap in key socioeconomic areas. Depending on what you're looking for, 
            this website can give you an overall snapshot of the issue, a high level summary of data from the key topics or a deep dive into more detailed 
            and specific data.</p>
            <p>The <b>key topics</b> covered by this website include:</p>
            <ul class='about-text'>
                <li>Age (Population)</li>
                <li>Long Term Health Condition (LTHC)</li>
                <li>School Completion</li>
                <li>Non-School Completion</li>
            </ul>
            <h2>How to Use this Site</h2>
            <p class='about-text'>
                <b>Home Page:</b> here you can see the population statistics and some charts showing a quick overview of the key topics.<br>
                <b>Data Overview Page:</b> here you can view the 2021 data for individual Local Government Areas  (LGAs), or summarised by State/Territory, for each topic.<br>
                <b>Data Deep Dive Page:</b> here you can get more detailed category filters. You can view the change in The Gap between Indigenous and Non Indigenous Australians from 2016 to 2021, OR you can select a specific LGA and view other LGAs with similar data results for the set of filters.
            </p>
            <table>
                <tr>
                    <th>Data Overview</th>
                    <th>Data Deep Dive</th>
                </tr>
                <tr>
                    <td>View data by <b>Indigenous status</b></td>
                    <td>View data by <b>Indigenous status & gender</b></td>
                </tr>
                <tr>
                    <td>View data from <b>2021</b></td>
                    <td>View data from <b>2016 & 2021</b></td>
                </tr>
                <tr>
                    <td>View data by <b>a topic</b></td>
                    <td>View data by <b>a topic</b></td>
                </tr>
                <tr>
                    <td>View data by <b>all sub-categories</b></td>
                    <td>View data filtered by <b>one or multiple sub-categories</b></td>
                </tr>
                <tr>
                    <td>View data either by <b>LGA or State/Territory</b></td>
                    <td>View data by <b>LGA</b></td>
                </tr>   
                <tr>
                    <td rowspan='2'>View the count of people in <b>raw or proportional values</b></td>
                    <td>View the count of people in <b>raw values</b>
                        <br>View <b>The Gap</b> between Indigenous and Non Indinegous People
                        <br>View the <b>change in The Gap</b> over time
                    </td>
                </tr>
                <tr>
                    <td><b>Select a specific LGA</b> and view X number of <b>LGAs that are similar</b> in the count of people for your set of filters.</td>
                </tr>
            </table>         
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
                <div class="carousel-inner d-flex align-items-center">
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
            html = html + "<div class='persona-card'>"
                        +    "<div class='persona-image'>"
                        +       "<img src='" + persona.getImageFilePath() + "' alt='" + persona.getName() + "'>"
                        +       "<p>" + persona.getName() + "</p>"
                        +    "</div>"
                        +    "<div class='persona-text'>"
                        +       "<p><b>Attributes: </b>" + persona.getAttributes() + "</p>"
                        +       "<p><b>Background: </b>" + persona.getBackground() + "</p>"
                        +       "<p><b>Needs: </b>" + persona.getNeeds() + "</p>"
                        +       "<p><b>Goals: </b>" + persona.getGoals() + "</p>"
                        +       "<p><b>Skills & Experience: </b>" + persona.getSkillsExp() + "</p>"
                        +    "</div>"
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
                        <li><a href='/faq.html'>FAQ</a></li>
                        <li><a href='/'>Home</a></li>
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
