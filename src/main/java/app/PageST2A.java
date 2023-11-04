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

        html = html + "   <div onclick='showCategory()' class='form-group'>";
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

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<String> healthCategories = jdbc.getCategories("LTHC");
        ArrayList<String> schoolCategories = jdbc.getCategories("SchoolCompletion");
        ArrayList<String> nonSchoolCategories = jdbc.getCategories("NonSchoolCompletion");
        ArrayList<String> ageCategories = jdbc.getCategories("Population");
        
        html = html + "   <div id='age-range' class='form-group' hidden>";
        html = html + "      <label for='age_drop'><h3>Sort by an Age Range</h3></label>";
        html = html + "      <select id='age_drop' name='age_drop'>";
        html = html + "         <option>All Ages</option>";
        for (String age: ageCategories) {
            html = html + "         <option>" + age + "</option>";
        }
        html = html + "      </select>";
        html = html + "   </div>";
        
        html = html + "   <div id='health' class='form-group' hidden>";
        html = html + "      <label for='health_drop'><h3>Sort by a Health Issue</h3></label>";
        html = html + "      <select id='health_drop' name='health_drop'>";
        html = html + "         <option>all health issues</option>";
        for (String condition: healthCategories) {
            html = html + "         <option>" + condition + "</option>";
        }
        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "   <div id='school' lass='form-group' hidden>";
        html = html + "      <label for='school_drop'><h3>Sort by a School Year</h3></label>";
        html = html + "      <select id='school_drop' name='school_drop'>";
        html = html + "         <option>Attended school - any year of completion</option>";
        for (String schoolYear: schoolCategories) {
            html = html + "         <option>" + schoolYear + "</option>";
        }
        html = html + "      </select>";
        html = html + "   </div>";
        
        html = html + "   <div id='non-school' lass='form-group' hidden>";
        html = html + "      <label for='nonSchool_drop'><h3>Sort by a Non-School Category</h3></label>";
        html = html + "      <select id='nonSchool_drop' name='nonSchool_drop'>";
        html = html + "         <option>All Non-School Levels</option>";
        for (String schoolLevel: nonSchoolCategories) {
            html = html + "         <option>" + schoolLevel + "</option>";
        }
        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "   </div>";

        html = html + "   <button type='submit' class='pink-button'>APPLY FILTERS</button>";

        html = html + "</form>";

        /* Get the Form Data from the radio checklist selections
         *  If the form is not filled in, then the form will return null, so we have included default values and made that clear.
        */
        html = html + "<h3>SELECTED FILTERS: ";
        String granularity = context.formParam("granularity");
        if (granularity != null) {
            html = html + granularity;
        } else {
            granularity = "LGA";
            html = html + granularity + " <span class='not-bold'>(default)</span>";
        }
        String dataType = context.formParam("dataType");
        if (dataType != null) {
            html = html + " | " + dataType;
        } else {
            dataType = "Raw";
            html = html + " | " + dataType + " <span class='not-bold'>(default)</span>";
        }
        String population = context.formParam("population");
        if (population != null) {
            html = html + " | " + population;
        } else {
            population = "Indig";
            html = html + " | " + population + " <span class='not-bold'>(default)</span>";
        }
        population = population.toLowerCase();
        String topic = context.formParam("topic");
        if (topic != null) {
            html = html + " | " + topic;
        } else {
            topic = "Population";
            html = html + " | " + topic + " <span class='not-bold'>(default)</span>";
        }
        // age range to sort by
        String ageCatToSort = context.formParam("age_drop");
        if (topic.equals("Population")) {
            if (ageCatToSort != null) {
                html = html + " | Sorted by: " + ageCatToSort;
            } else {
                ageCatToSort = "All Ages"; //default - all
                html = html + " | Sorted by: " + ageCatToSort  + " <span class='not-bold'>(default)</span>";
            }
        }
        // health category to sort by
        String healthCatToSort = context.formParam("health_drop");
        if (topic.equals("LTHC")) {
            if (healthCatToSort != null) {
                html = html + " | Sorted by: " + healthCatToSort;
            } else {
                healthCatToSort = "all health issues"; //default - all
                html = html + " | Sorted by: " + healthCatToSort  + " <span class='not-bold'>(default)</span>";
            }
        }
        String schoolCatToSort = context.formParam("school_drop");
        if (topic.equals("SchoolCompletion")) {
            if (schoolCatToSort != null) {
                html = html + " | Sorted by: " + schoolCatToSort;
            } else {
                schoolCatToSort = "Attended school - any year of completion"; //default - all
                html = html + " | Sorted by: " + schoolCatToSort  + " <span class='not-bold'>(default)</span>";
            }
        }
        // non-school category to sort by
        String nonSchoolCatToSort = context.formParam("nonSchool_drop");
        if (topic.equals("NonSchoolCompletion")) {
            if (nonSchoolCatToSort != null) {
                html = html + " | Sorted by: " + nonSchoolCatToSort;
            } else {
                nonSchoolCatToSort = "All Non-School Levels"; //default - all
                html = html + " | Sorted by: " + nonSchoolCatToSort  + " <span class='not-bold'>(default)</span>";
            }
        }
        String sort = context.formParam("sort");
        if (sort != null) {
            html = html + ", " + sort;
        } else {
            sort = "DESC";
            html = html + ", " + sort + " <span class='not-bold'>(default)</span>";
        }
        html = html + "</h3>";

        // Category to sort
        String category = ""; //category to sort
        if (topic.equals("Population")) {
            category = ageCatToSort;
        } else if (topic.equals("LTHC")) {
            category = healthCatToSort;
        } else if (topic.equals("SchoolCompletion")) {
            category = schoolCatToSort;
        } else if (topic.equals("NonSchoolCompletion")) {
            category = nonSchoolCatToSort;
        }
        // Add state data chart
        if (granularity.equals("State or Territory")) {
            // Button to toggle
            html = html + "<button id='show-graph' onclick='showGraph()' type='button'>Show as Chart</button>";
            // Chart
            html = html + "<div id='state-chart'></div>";
        }
        // Add table of data
        html = html + outputTable(granularity, dataType, population, topic, sort, category);

        // Close Content div
        html = html + "</div>";
        if (granularity.equals("State or Territory")) {
            html = html + "<div id='pagination' hidden>";
        } else {
            html = html + "<div id='pagination'>";
        }
        html = html + """
                <button id="prev">Previous</button>
                <span id="page-num">Page 1</span>
                <button id="next">Next</button>
            </div>
            <div id='deepDive'>
                <a href='data-deep-dive.html'>DATA DEEP DIVE (MORE FILTERS)</a>            
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
                        <li><a href='/faq.html'>FAQ</a></li>
                        <li><a href='/'>Home</a></li>
                    </ul>
                </div>     
            </div>
        """;

        html = html + """
                <script>
                function showCategory() {
                    let topicOptions = document.querySelector('input[name="topic"]:checked');
                    let selectedTopic = '';
                    if (topicOptions) {
                        selectedTopic = topicOptions.value;
                    }
                    let ageCat = document.getElementById('age-range');
                    let healthCat = document.getElementById('health');
                    let schoolCat = document.getElementById('school');
                    let nonSchoolCat = document.getElementById('non-school');
                    if ( selectedTopic.localeCompare('Population') == 0 ) {
                        ageCat.hidden = false;
                        healthCat.hidden = true;
                        schoolCat.hidden = true;
                        nonSchoolCat.hidden = true;
                    } else if ( selectedTopic.localeCompare('LTHC') == 0 ) {
                        ageCat.hidden = true;
                        healthCat.hidden = false;
                        schoolCat.hidden = true;
                        nonSchoolCat.hidden = true;
                    } else if ( selectedTopic.localeCompare('SchoolCompletion') == 0 ) {
                        ageCat.hidden = true;
                        healthCat.hidden = true;
                        schoolCat.hidden = false;
                        nonSchoolCat.hidden = true;
                    } else if ( selectedTopic.localeCompare('NonSchoolCompletion') == 0 ) {
                        ageCat.hidden = true;
                        healthCat.hidden = true;
                        schoolCat.hidden = true;
                        nonSchoolCat.hidden = false;
                    }
                }
            </script>
            <script>
                const itemsPerPage = 51;
                let currentPage = 1;
                
                const table = document.getElementById('myTable');
                const rows = table.getElementsByTagName('tr');
                const totalPages = Math.ceil(rows.length / itemsPerPage);
                
                function showPage(page) {
                    for (let i = 1; i < rows.length; i++) {
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
            <script>
            
            function Bold(tableID, category){
                const table = document.getElementById(tableID);
                const rows = table.getElementsByTagName('tr');

                for (let i = 0; i < rows.length; i++) {
                    const cells = rows[i].getElementsByTagName('td');
                    let wordPresent = false;

                    for (let j = 0; j < cells.length; j++) {
                        if (cells[j].textContent.includes(category)) {
                            wordPresent = true;
                            break;
                        }
                    }

                    if (wordPresent) {
                        for (let j = 0; j < cells.length; j++) {
                            cells[j].style.fontWeight = 'bold';
                            cells[j].style.textDecoration = 'underline';
                        }
                    }
                }
            }
            """;
            html += "Bold('myTable','"+category+"')"
                    + "</script>";
            html = html + "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>";
            html = html + """
                <script type="text/javascript">
                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(drawChart);

                function drawChart() {
                var data = google.visualization.arrayToDataTable(
                """;
                ArrayList<String> categories = new ArrayList<String>();
                if (topic.equals("Population")) {
                    categories = ageCategories;
                } else if (topic.equals("LTHC")) {
                    categories = healthCategories;
                } else if (topic.equals("SchoolCompletion")) {
                    categories = schoolCategories;
                } else if (topic.equals("NonSchoolCompletion")) {
                    categories = nonSchoolCategories;
                }
                html = html + dataArrayForChart(granularity, dataType, population, topic, sort, category, categories);
                html = html + ");";   
                String dataLabel = dataType.equals("Raw") ? "Raw" : "Proportional (Percentage)";
                html = html + "var options = {title: '" + topic +  " by State, " + dataLabel + " Data', ";
                html = html + """
                        width: 1500,
                        height: 1000,
                        legend: { position: 'top', maxLines: 3 },
                        bar: { groupWidth: '75%' },
                        isStacked: true
                };

                var chart = new google.visualization.ColumnChart(document.getElementById('state-chart'));
                chart.draw(data, options);
                }
                </script> 
                <script>
                function showGraph() {
                    let graphButton = document.getElementById('show-graph');
                    let graphChart = document.getElementById('state-chart');
                    let tableDiv = document.getElementById('myTable');
                    let tablePages = document.getElementById('pagination');
                    graphChart.hidden = !graphChart.hidden;
                    tableDiv.hidden = !tableDiv.hidden;
                    tablePages.hidden = !tablePages.hidden;
                    if (graphButton.innerText.toUpperCase().localeCompare('SHOW AS CHART') == 0) {
                        graphButton.innerText = 'Show as Table';
                    } else if (graphButton.innerText.toUpperCase().localeCompare('SHOW AS TABLE') == 0) {
                        graphButton.innerText = 'Show as Chart';
                    }
                }
                </script>      
            """;
        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String outputTable(String granularity, String dataType, String population, String topic, String sort, String category) {
        String html = "";

        // Look up data from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<OverviewData> dataPoints;
        if (dataType.equals("Raw")) {
            dataPoints = jdbc.getRawData2021(granularity, population, topic, sort, category);
        } else {
            dataPoints = jdbc.getPropData2021(granularity, population, topic, sort, category);
        }
        
        html = html + "<table id=\"myTable\"";
        if (granularity.equals("State or Territory")) {
            html = html + " hidden";
        }
        html = html + ">"
                    + "<tr>"
                    +     "<th>Rank: sorted by " + category + ", " + sort + "</th>"
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
                    html = html + "<tr>"
                            + "<td class='rank'>" + rankingCount + "</td>";
                } else {
                    html = html + "<tr>"
                            + "<td> </td>";
                }
                html = html + "<td>" + data.getLocation() + "</td>"
                            + "<td>" + data.getCategory() + "</td>"
                            + "<td>" + data.getCount() + "</td>"
                            + "</tr>";
            }
        } else {
            for (OverviewData data : dataPoints) {
                if (!data.getLocation().equals(nextLocation)) {
                    nextLocation = data.getLocation();
                    rankingCount++;
                    html = html + "<tr>"
                            + "<td class='rank'>" + rankingCount + "</td>";
                } else {
                    html = html + "<tr>"
                            + "<td> </td>";
                }
                html = html + "<td>" + data.getLocation() + "</td>"
                            + "<td>" + data.getCategory() + "</td>"
                            + "<td>" + data.getPropCount() + "</td>"
                            + "</tr>";
            }
        }    
        html = html + "</table>";
        return html;

    }

    public String dataArrayForChart(String granularity, String dataType, String population, String topic, String sort, String categoryToSort, ArrayList<String> categories) {
        String html = "";

        // Look up data from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<OverviewData> dataPoints;
        if (dataType.equals("Raw")) {
            dataPoints = jdbc.getRawData2021(granularity, population, topic, sort, categoryToSort);
        } else {
            dataPoints = jdbc.getPropData2021(granularity, population, topic, sort, categoryToSort);
        }

        html = html + "[['Category'";
        // Populate data array
        for (String cat: categories) {
            html = html + ", '" + cat + "'"; // Categories Array
        }
        html = html + ", { role: 'annotation' } ], ";
        //int rankingCount = 0;
        String nextLocation = "";
        for (OverviewData dataPoint: dataPoints) { // Data points arrays
            if (nextLocation.equals("")) {
                nextLocation = dataPoint.getLocation();
                //rankingCount++;
                html = html + "['" + nextLocation + "'";
            } else if (!dataPoint.getLocation().equals(nextLocation)) {
                nextLocation = dataPoint.getLocation();
                //rankingCount++;
                html = html + ", ''], ['" + nextLocation + "'";
            }
            if (dataType.equals("Raw")) {
                html = html + ", " + dataPoint.getCount();
            } else {
                html = html + ", " + dataPoint.getPropCount();
            }
        }
        html = html + ", '']]";
        return html;
    }
}
