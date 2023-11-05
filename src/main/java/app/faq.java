package app;

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
public class faq implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/faq.html";

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
                <h1>FAQ</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content mission-info'>";

        // Add HTML for the page content
        html = html + """
            <h2>What we cover</h2>
            <p>Our main focus are on the topics of <b>health, education, and population age</b>, as these topics give 
            us a greater understanding on the disparities between Indigenous and Non-Indigenous Australians</p>

            <h2>How to Use this Site</h2>
            <p class='about-text'>
                <b>Home Page:</b> here you can see the population statistics and some charts showing a quick overview of the key topics.<br>
                <b>Data Overview Page:</b> here you can view the 2021 data for individual Local Government Areas  (LGAs), or summarised by State/Territory, for each topic.<br>
                <b>Data Deep Dive Page:</b> here you can get more detailed category filters. You can view the change in The Gap between Indigenous and Non Indigenous Australians from 2016 to 2021, OR you can select a specific LGA and view other LGAs with similar data results for the set of filters.
            </p>

            <h2>Our Data Sources</h2>
            <p class='about-text'>
                All the data on this website has been obtained from the <a href='https://www.abs.gov.au/census/find-census-data'>Austalian Bureau of Statistics</a>
            </p>
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
                        <li><a href='/faq.html'>FAQ</a></li>
                        <li><a href='/'>Home</a></li>
                    </ul>
                </div>     
            </div>
        """;

        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
