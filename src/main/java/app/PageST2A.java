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
public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/data-overview.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Data Overview</title>";

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
                <h1>2021 Data by LGA or State</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content - Filters
        html = html + "<h2>FILTERS</h2>";

        html = html + "<form action='/data-overview.html' method='post'>";
        
        html = html + "   <div class = 'filter-box'>";

        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Granularity</h3>";
        html = html + "      <input type='radio' id='granularity1' name='granularity' value='LGA'>";
        html = html + "      <label for='granularity1'>Individual LGAs</label><br>";
        html = html + "      <input type='radio' id='granularity2' name='granularity' value='State or Territory'>";
        html = html + "      <label for='granularity2'>State & Territory</label><br>";
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Data Type</h3>";
        html = html + "      <input type='radio' id='dataType1' name='dataType' value='Raw'>";
        html = html + "      <label for='dataType1'>Raw Total Values</label><br>";
        html = html + "      <input type='radio' id='dataType2' name='dataType' value='Proportional'>";
        html = html + "      <label for='dataType2'>Proportional Values</label><br>";
        html = html + "   </div>";
        
        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Population</h3>";
        html = html + "      <input type='radio' id='population1' name='population' value='Indig'>";
        html = html + "      <label for='population1'>Indigenous</label><br>";
        html = html + "      <input type='radio' id='population2' name='population' value='Non_Indig'>";
        html = html + "      <label for='population2'>Non-Indigenous</label><br>";
        html = html + "   </div>";

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
        html = html + "      <h3>Sort</h3>";
        html = html + "      <input type='radio' id='sort1' name='sort' value='ASC'>";
        html = html + "      <label for='sort1'>Ascending</label><br>";
        html = html + "      <input type='radio' id='sort2' name='sort' value='DESC'>";
        html = html + "      <label for='sort2'>Descending</label><br>";
        html = html + "   </div>";

        html = html + "   </div>";

        html = html + "   <button type='submit' class='pink-button'>APPLY FILTERS</button>";

        html = html + "</form>";

        /* Get the Form Data from the radio checklist selections
         *  If the form is not filled in, then the form will return null, so we have included default values and made that clear.
        */
        html = html + "<h2>SELECTED FILTERS: ";
        String granularity = context.formParam("granularity");
        if (granularity != null) {
            html = html + granularity;
        } else {
            granularity = "LGA";
            html = html + granularity + " <small>(default selection)</small>";
        }
        String dataType = context.formParam("dataType");
        if (dataType != null) {
            html = html + " | " + dataType;
        } else {
            dataType = "Raw";
            html = html + " | " + dataType + " <small>(default selection)</small>";
        }
        String population = context.formParam("population");
        if (population != null) {
            html = html + " | " + population;
        } else {
            population = "Indig";
            html = html + " | " + population + " <small>(default selection)</small>";
        }
        population = population.toLowerCase();
        String topic = context.formParam("topic");
        if (topic != null) {
            html = html + " | " + topic;
        } else {
            topic = "Population";
            html = html + " | " + topic + " <small>(default selection)</small>";
        }
        String sort = context.formParam("sort");
        if (sort != null) {
            html = html + " | " + sort;
        } else {
            sort = "ASC";
            html = html + " | " + sort + " <small>(default selection)</small>";
        }
        html = html + "</h2>";

        // Add table of data
        html = html + outputTable(granularity, dataType, population, topic, sort);

        // Close Content div
        html = html + "</div>";

        html = html + """
            <div id="pagination">
                <button id="prev">Previous</button>
                <span id="page-num">Page 1</span>
                <button id="next">Next</button>
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

        html = html + """
            <script>
                const itemsPerPage = 50;
                let currentPage = 1;
                
                const table = document.getElementById('myTable');
                const rows = table.getElementsByTagName('tr');
                const totalPages = Math.ceil(rows.length / itemsPerPage);
                
                function showPage(page) {
                    for (let i = 0; i < rows.length; i++) {
                        rows[i].style.display = (i >= (page - 1) * itemsPerPage && i < page * itemsPerPage) ? '' : 'none';
                    }
                    document.getElementById('page-num').textContent = `Page ${page}`;
                }
                
                showPage(currentPage);
                
                document.getElementById('next').addEventListener('click', () => {
                    if (currentPage < totalPages) {
                        currentPage++;
                        showPage(currentPage);
                    }
                });
                
                document.getElementById('prev').addEventListener('click', () => {
                    if (currentPage > 1) {
                        currentPage--;
                        showPage(currentPage);
                    }
                })
            </script>
                """;
        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String outputTable(String granularity, String dataType, String population, String topic, String sort) {
        String html = "";

        // Look up data from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<OverviewData> dataPoints;
        if (dataType.equals("Raw")) {
            dataPoints = jdbc.getRawData2021(granularity, population, topic, sort);
        } else {
            dataPoints = jdbc.getPropData2021(granularity, population, topic, sort);
        }
        
        html = html + "<table id=\"myTable\">"
                    + "<tr>"
                    +     "<th>Rank: " + rankingMethod(topic) + "</th>"
                    +     "<th>" + granularity + "</th>"
                    +     "<th>Category</th>"
                    +     "<th>" + dataType + " Data Count</th>"
                    + "</tr>";
        
        int rankingCount = 0;
        String nextLocation = "";
        if (dataType.equals("Raw")) {
            for (OverviewData data : dataPoints) {
                if (!data.getLocation().equals(nextLocation)) {
                    nextLocation = data.getLocation();
                    rankingCount++;
                }
                html = html + "<tr>"
                            + "<td>" + rankingCount + "</td>"
                            + "<td>" + data.getLocation() + "</td>"
                            + "<td>" + data.getCategory() + "</td>"
                            + "<td>" + data.getCount() + "</td>"
                            + "</tr>";
            }
        } else {
            for (OverviewData data : dataPoints) {
                if (!data.getLocation().equals(nextLocation)) {
                    nextLocation = data.getLocation();
                    rankingCount++;
                }
                html = html + "<tr>"
                            + "<td>" + rankingCount + "</td>"
                            + "<td>" + data.getLocation() + "</td>"
                            + "<td>" + data.getCategory() + "</td>"
                            + "<td>" + data.getPropCount() + "</td>"
                            + "</tr>";
            }
        }    
        html = html + "</table>";
        return html;

    }

    private String rankingMethod(String topic) {
        String sortByAttr = "";
        if (topic.equals("Population")) {
            sortByAttr = "sorted by people age 65+";
        } else if (topic.equals("LTHC")) {
            sortByAttr = "sorted by the total LTHC";
        } else if (topic.equals("SchoolCompletion")) {
            sortByAttr = "sorted by year 12 completion";
        } else if (topic.equals("NonSchoolCompletion")) {
            sortByAttr = "sorted by total bachelor and post grad";
        }
        return sortByAttr;
    }
}
