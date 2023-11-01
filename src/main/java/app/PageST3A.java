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
        html = html + "<link rel='stylesheet' type='text/css' href='page3.css' />";
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
                <h1>Change in The Gap from 2016 to 2021<br><i>OR</i> Comparison of Similar LGAs</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content - Filters
        html = html + "<h2>FILTERS</h2>";

        html = html + "<form action='/data-deep-dive.html' method='post'>";
                        // Start filter box div
        html = html + "   <div class = 'filter-box'>";

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

        html = html + "   <div id='category-placeholder'>";
        html = html + "      <h3>Select Topic to View <br>Categories</h3>";
        html = html + "   </div>";

        html = html + "   <div id='age-range' class='form-group' hidden>";
        html = html + "      <h3>Age Range</h3>";
        html = html + "      <label for='minAge'>Min</label>";
        html = html + "      <input type='number' id='minAge' name='minAge' placeholder='0' min='0' max='200'>";
        html = html + "      <label for='maxAge'>Max</label>";
        html = html + "      <input type='number' id='maxAge' name='maxAge' placeholder='65' min='0' max='200'>";
        html = html + "      <p><small>(max should be greater than min)</small></p>";
        html = html + "   </div>";

        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<String> healthConditions = jdbc.getCategories("LTHC");
        
        html = html + "   <div id='health' class='form-group' hidden>";
        html = html + "      <label for='health_drop'><h3>Health Issue</h3></label>";
        html = html + "      <select id='health_drop' name='health_drop' multiple>";
        for (String condition: healthConditions) {
            html = html + "         <option>" + condition + "</option>";;
        }
        html = html + "      </select>";
        html = html + "      <p><small>('Shift' select multiple)</small></p>";
        html = html + "   </div>";

        html = html + "   <div id='school' class='form-group' hidden>";
        html = html + "      <h3>School Year</h3>";
        html = html + "      <label for='minYear'>Min</label>";
        html = html + "      <input type='number' id='minYear' name='minYear' placeholder='8' min='0' max='12'>";
        html = html + "      <label for='maxYear'>Max</label>";
        html = html + "      <input type='number' id='maxYear' name='maxYear' placeholder='12' min='0' max='12'>";
        html = html + "      <p><small>(max should be greater than min)</small></p>";
        html = html + "   </div>";

        ArrayList<String> nonSchoolCategories = jdbc.getCategories("NonSchoolCompletion");
        
        html = html + "   <div id='non-school' lass='form-group' hidden>";
        html = html + "      <label for='nonSchool_drop'><h3>School Category</h3></label>";
        html = html + "      <select id='nonSchool_drop' name='nonSchool_drop' multiple>";
        for (String schoolLevel: nonSchoolCategories) {
            html = html + "         <option>" + schoolLevel + "</option>";;
        }
        html = html + "      </select>";
        html = html + "      <p><small>('Shift' select multiple)</small></p>";
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Population Demographic</h3>";
        html = html + "      <input type='checkbox' id='population1' name='population1' value='indig'>";
        html = html + "      <label for='population1'>Indigenous</label><br>";
        html = html + "      <input type='checkbox' id='population2' name='population2' value='non_indig'>";
        html = html + "      <label for='population2'>Non Indigenous</label><br>";
        html = html + "   </div>";

        html = html + "   <div class='form-group'>";
        html = html + "      <h3>Gender</h3>";
        html = html + "      <input type='checkbox' id='gender1' name='gender1' value='m'>";
        html = html + "      <label for='gender1'>Male (M)</label><br>";
        html = html + "      <input type='checkbox' id='gender2' name='gender2' value='f'>";
        html = html + "      <label for='gender2'>Female (F)</label><br>";
        html = html + "   </div>";

        html = html + "   <div class='form-group' id='sort-gap'>";
        html = html + "      <h3>Sort Change in Gap</h3>";
        html = html + "      <input type='radio' id='sort1' name='sort' value='ASC'>";
        html = html + "      <label for='sort1'>Ascending</label><br>";
        html = html + "      <input type='radio' id='sort2' name='sort' value='DESC'>";
        html = html + "      <label for='sort2'>Descending</label><br>";
        html = html + "   </div>";

        html = html + "   <div id='sort-placeholder' hidden></div>";

        html = html + "   <div class='form-group LGA-filter'>";
        html = html + "      <h3>Select Individual LGA for Comparison</h3>";
        html = html + "      <label class='toggle-box'>";
        html = html + "         <input type='checkbox' id='SelectLGA' name='SelectLGA' value='SelectLGA'>";
        html = html + "         <span class='toggle-slider'></span>";
        html = html + "      </label>";
        html = html + "   </div>";

        html = html + "   <div class='form-group LGA-filter LGA-optional' hidden>";
        html = html + "      <h3>Select Census Year For LGA</h3>";
        html = html + "      <input type='radio' id='year1' name='year' value='2016'>";
        html = html + "      <label for='year1'>2016</label><br>";
        html = html + "      <input type='radio' id='year2' name='year' value='2021'>";
        html = html + "      <label for='year2'>2021</label><br>";
        html = html + "   </div>";

        ArrayList<String> LGANames = jdbc.getLGANames();
        
        html = html + "   <div class='form-group LGA-filter LGA-optional' hidden>";
        html = html + "      <label for='LGA_drop'><h3>Select LGA</h3></label>";
        html = html + "      <select id='LGA_drop' name='LGA_drop'>";
        html = html + "      <option value='' disabled selected>Select...</option>";
        for (String LGA: LGANames) {
            html = html + "         <option>" + LGA + "</option>";;
        }
        html = html + "      </select>";
        html = html + "   </div>";

        int LGACountForComparison = LGANames.size();
        html = html + "   <div class='form-group LGA-filter LGA-optional' hidden>";
        html = html + "      <h3>No. of Similar LGAs to View</h3>";
        html = html + "      <label for='NumLGA'>Input number:</label>";
        html = html + "      <input type='number' id='NumLGA' name='NumLGA' placeholder='5' min='1' max='" + LGACountForComparison + "'>";
        html = html + "   </div>";

                        // Close filter box div
        html = html + "   </div>";

        html = html + "   <button type='submit' class='pink-button'>APPLY FILTERS</button>";

        html = html + "</form>";

        /* Get the Form Data
         *  If part of the form is not filled in, then that part of the form will return null. We have included default values where applicable
        */
        html = html + "<h2>SELECTED FILTERS: ";
        // topic - single selection
        String topic = context.formParam("topic");
        html = html + "Topic: Age";
        if (topic == null) {
            topic = "Population"; //default
            html = html + " <span class='not-bold'>(default selection)</span>";
        }
        // population demographic - multiple selection
        String indig = context.formParam("population1");
        String nonIndig = context.formParam("population2");
        String population = getPopulation(indig, nonIndig);
        if (indig != null && nonIndig != null) {
            html = html + " | Indigenous & Non-Indigenous";
        } else if (indig != null) {
            html = html + " | Indigenous";
        } else if (nonIndig != null) {
            html = html + " | Non-Indigenous";
        } else {
            html = html + " | Indigenous & Non-Indigenous <span class='not-bold'>(default selection)</span>";
        }
        // gender - multiple selection
        String male = context.formParam("gender1"); 
        String female = context.formParam("gender2");
        String gender = getGender(male, female);
        if (male != null && female != null) {
            html = html + " | Male & Female";
        } else if (male != null) {
            html = html + " | Male";
        } else if (female != null) {
            html = html + " | Female";
        } else {
            html = html + " | Male & Female <span class='not-bold'>(default selection)</span>";
        }
        // age range - min & max
        String minAge = context.formParam("minAge");
        String maxAge = context.formParam("maxAge");
        if (topic.equals("Population")) {
            if (minAge != null && !minAge.isEmpty() && maxAge != null && !maxAge.isEmpty() && (minAge.compareTo(maxAge) <= 0 && maxAge.length() >= minAge.length())) {
                html = html + " | Age " + minAge + " to " + maxAge;
            } else if (minAge != null && !minAge.isEmpty() && (maxAge == null || maxAge.isEmpty())){
                maxAge = "200"; //default
                html = html + " | Age " + minAge + " to " + maxAge;
            } else if ((minAge == null || minAge.isEmpty()) && maxAge != null && !maxAge.isEmpty()){
                minAge = "0"; //default
                html = html + " | Age " + minAge + " to " + maxAge + " years";
            } else {
                maxAge = "200"; //default
                minAge = "0"; //default
                html = html + " | Age " + minAge + " to " + maxAge + " years <span class='not-bold'>(default selection)</span>";
            }
        }
        // school year - min & max
        String minSchoolYear = context.formParam("minYear");
        String maxSchoolYear = context.formParam("maxYear");
        if (topic.equals("SchoolCompletion")) {
            if (minSchoolYear != null && !minSchoolYear.isEmpty() && maxSchoolYear != null && !maxSchoolYear.isEmpty() && (minSchoolYear.compareTo(maxSchoolYear) <= 0 || maxSchoolYear.length() > minSchoolYear.length())) {
                html = html + " | School Year " + minSchoolYear + " to " + maxSchoolYear;
            } else if (minSchoolYear != null && !minSchoolYear.isEmpty() && (maxSchoolYear == null || maxSchoolYear.isEmpty())){
                maxSchoolYear = "12"; //defaults
                html = html + " | School Year " + minSchoolYear + " to " + maxSchoolYear;
            } else if ((minSchoolYear == null || minSchoolYear.isEmpty()) && maxSchoolYear != null && !maxSchoolYear.isEmpty()){
                minSchoolYear = "8"; //defaults
                html = html + " | School Year " + minSchoolYear + " to " + maxSchoolYear;
            } else {
                maxSchoolYear = "12"; //defaults
                minSchoolYear = "8"; //defaults
                html = html + " | School Year " + minSchoolYear + " to " + maxSchoolYear + " <span class='not-bold'>(default selection)</span>";
            }
        }
        // health - multiple selection
        ArrayList<String> health = new ArrayList<String>(context.formParams("health_drop"));
        String healthCategoriesString = "";
        if (topic.equals("LTHC")) {
            if (!health.isEmpty()) {
                healthCategoriesString = getCategories(health);
                html = html + " | Categories: " + healthCategoriesString;
            } else {
                healthCategoriesString = getCategories(healthConditions); //default - all
                html = html + " | Categories: " + healthCategoriesString  + " <span class='not-bold'>(default selection)</span>";
            }
        }
        // non-school - multiple selection
        ArrayList<String> nonSchool = new ArrayList<String>(context.formParams("nonSchool_drop"));
        String nonSchoolCategoriesString = "";
        if (topic.equals("NonSchoolCompletion")) {
            if (!nonSchool.isEmpty()) {
                nonSchoolCategoriesString = getCategories(nonSchool);
                html = html + " | Categories: " + nonSchoolCategoriesString;
            } else {
                nonSchoolCategoriesString = getCategories(nonSchoolCategories); //default - all
                html = html + " | Categories: " + nonSchoolCategoriesString + " <span class='not-bold'>(default selection)</span>";
            }
        }
        // toggle LGA selection - single selection
        String toggleLGA = context.formParam("SelectLGA");
        if (toggleLGA != null) {
            html = html + " | " + toggleLGA;
        }
        // select census year - single selection
        String censusYear = context.formParam("year");
        if (censusYear != null) {
            if (censusYear.equals("2016") && topic.equals("LTHC")) { 
                censusYear = "2021"; // no 2016 values for LTHC
                html = html + ": " + censusYear + " <small>(no 2016 data for LTHC)</small>";
            } else {
                html = html + ": " + censusYear;
            }
        } else if (toggleLGA != null) {
            censusYear = "2021"; // default
            html = html + ": " + censusYear + " <span class='not-bold'>(default selection)</span>";
        }
        // select LGA - single selection
        String selectedLGA = context.formParam("LGA_drop");
        if (selectedLGA != null) {
            html = html + ", " + selectedLGA;
        } else if (toggleLGA != null) {
            selectedLGA = "Melbourne"; //default
            html = html + ": " + selectedLGA + " <span class='not-bold'>(default selection)</span>";
        }
        // number of LGAs - single selection
        String numberOfLGA = context.formParam("NumLGA");
        if (numberOfLGA != null && !numberOfLGA.isEmpty()) {
            html = html + ", view " + numberOfLGA + " similar LGAs";
        } else if (toggleLGA != null) {
            numberOfLGA = "5"; //default number of similar LGAs
            html = html + ": " + numberOfLGA + " <span class='not-bold'>(default selection)</span>";
        }
        // sort Change in The Gap results - single selection
        String sort = context.formParam("sort");
        if (sort != null && toggleLGA == null) {
            html = html + " | " + sort;
        } else if (toggleLGA == null) {
            sort = "DESC"; //default
            html = html + " | " + sort + " <span class='not-bold'>(default selection)</span>";
        }

        html = html + "</h2>";

        // Call JDBC methods to get the data and use it to create tables based on filters
        if (toggleLGA == null) { 
            // To display data for change in The Gap
            ArrayList<Deepdive> gapData;
            if (topic.equals("Population")) {
                gapData = jdbc.getGap(gender, population, sort, topic, "", minAge, maxAge);
                html = html + outputGapTable(gapData);
            } else if (topic.equals("SchoolCompletion")) {
                gapData = jdbc.getGap(gender, population, sort, topic, "", minSchoolYear, maxSchoolYear);
                html = html + outputGapTable(gapData);
            } else if (topic.equals("NonSchoolCompletion")) {
                gapData = jdbc.getGap(gender, population, sort, topic, nonSchoolCategoriesString, "", "");
                html = html + outputGapTable(gapData);
            } else if (topic.equals("LTHC")) {
                gapData = jdbc.getHealthGap2021(gender, population, sort, healthCategoriesString);
                html = html + output2021HealthGapTable(gapData);
            }
        } else { 
            // To display data for LGAs similar to selected LGA
            ArrayList<Deepdive> LGAData = new ArrayList<Deepdive>();
            if (topic.equals("Population")) {
                LGAData = jdbc.getSimilarLga(gender, population, topic, "", minAge, maxAge, censusYear, selectedLGA, numberOfLGA);
            } else if (topic.equals("SchoolCompletion")) {
                LGAData = jdbc.getSimilarLga(gender, population, topic, "", minSchoolYear, maxSchoolYear, censusYear, selectedLGA, numberOfLGA);
            } else if (topic.equals("NonSchoolCompletion")) {
                LGAData = jdbc.getSimilarLga(gender, population, topic, nonSchoolCategoriesString, "", "", censusYear, selectedLGA, numberOfLGA);
            } else if (topic.equals("LTHC")) {
                LGAData = jdbc.getSimilarLga(gender, population, topic, healthCategoriesString, "", "", censusYear, selectedLGA, numberOfLGA);
            }
            html = html + outputSimilarLgaTable(LGAData);
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

        // Add javascript script
        html = html + """
            <script>
                let selectLGA = document.getElementById('SelectLGA');
                let filtersForLGA = document.getElementsByClassName('LGA-optional');
                let sortGap = document.getElementById('sort-gap');
                let noSort = document.getElementById('sort-placeholder');
                selectLGA.addEventListener( 'change', () => {
                    if ( selectLGA.checked ) {
                        for (let i = 0; i < filtersForLGA.length; i++) {
                            filtersForLGA[i].hidden = false;
                            sortGap.hidden = true;
                            noSort.hidden = false;
                        }
                        
                    } else {
                        for (let i = 0; i < filtersForLGA.length; i++) {
                            filtersForLGA[i].hidden = true;
                            sortGap.hidden = false;
                            noSort.hidden = true;
                        }
                        
                    }
                });
                function showCategory() {
                    let topicOptions = document.querySelector('input[name="topic"]:checked');
                    let selectedTopic = '';
                    if (topicOptions) {
                        selectedTopic = topicOptions.value;
                    }
                    let ageCategories = document.getElementById('age-range');
                    let healthCategories = document.getElementById('health');
                    let schoolCategories = document.getElementById('school');
                    let categoriesNonSchool = document.getElementById('non-school');
                    let noCategory = document.getElementById('category-placeholder');
                    if ( selectedTopic.localeCompare('Population') == 0 ) {
                        ageCategories.hidden = false;
                        healthCategories.hidden = true;
                        schoolCategories.hidden = true;
                        categoriesNonSchool.hidden = true;
                        noCategory.hidden = true;
                    } else if ( selectedTopic.localeCompare('LTHC') == 0 ) {
                        ageCategories.hidden = true;
                        healthCategories.hidden = false;
                        schoolCategories.hidden = true;
                        categoriesNonSchool.hidden = true;
                        noCategory.hidden = true;
                    } else if ( selectedTopic.localeCompare('SchoolCompletion') == 0 ) {
                        ageCategories.hidden = true;
                        healthCategories.hidden = true;
                        schoolCategories.hidden = false;
                        categoriesNonSchool.hidden = true;
                        noCategory.hidden = true;
                    } else if ( selectedTopic.localeCompare('NonSchoolCompletion') == 0 ) {
                        ageCategories.hidden = true;
                        healthCategories.hidden = true;
                        schoolCategories.hidden = true;
                        categoriesNonSchool.hidden = false;
                        noCategory.hidden = true;
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

    public String getGender(String gender1, String gender2) {
        // Gender as 1 string for SQL queries
        String selection;
        if (gender1 != null && gender2 != null) {
            selection = "'" + gender1 + "', '" + gender2 + "'";
        } else if (gender1 != null) {
            selection = "'" + gender1 + "'";
        } else if (gender2 != null) {
            selection = "'" + gender2 + "'";
        } else { //default if both are null
            selection = "'m', 'f'";
        }
        return selection;
    }

    public String getPopulation(String population1, String population2) {
        // Population as 1 string for SQL queries
        String selection;
        if (population1 != null && population2 != null) {
            selection = "'" + population1 + "', '" + population2 + "'";
        } else if (population1 != null) {
            selection = "'" + population1 + "'";
        } else if (population2 != null) {
            selection = "'" + population2 + "'";
        } else { //default if both are null
            selection = "'indig', 'non_indig'";
        }
        return selection;
    }

    public String getCategories(ArrayList<String> categoryList) {
        // Categories converted from ArrayList to 1 string for SQL queries
        String selection = "";
        for (String cat : categoryList) {
            if (selection.isEmpty()) {
                selection += "'" + cat + "'";
            } else {
                selection += ", " + "'" + cat + "'";
            }
        }
        return selection;
    }

    public String outputGapTable(ArrayList<Deepdive> data) {
        String html = "";
        
        html = html + "<table>"
                    + "<tr>"
                    +     "<th>Rank (by Change in The Gap)</th>"
                    +     "<th>Local Government Area</th>"
                    +     "<th>2016 Count of People Based on Filters</th>"
                    +     "<th>2021 Count of People Based on Filters</th>"
                    +     "<th>The Gap 2016 (Non Indig minus Indig, based on filters)</th>"
                    +     "<th>The Gap 2021 (Non Indig minus Indig, based on filters)</th>"
                    +     "<th>Change in The Gap (2021 minus 2016)</th>"
                    + "</tr>";
        
        int rankingCount = 0;
        for (Deepdive dataPoint : data) {
            rankingCount++;
            html = html + "<tr>"
                        + "<td>" + rankingCount + "</td>"
                        + "<td>" + dataPoint.getLga() + "</td>"
                        + "<td>" + dataPoint.getCount2016() + "</td>"
                        + "<td>" + dataPoint.getCount2021() + "</td>"
                        + "<td>" + dataPoint.getGap2016() + "</td>"
                        + "<td>" + dataPoint.getGap2021() + "</td>"
                        + "<td>" + dataPoint.improve() + "</td>"
                        + "</tr>";
        }

        html = html + "</table>";
        return html;

    }

    public String output2021HealthGapTable(ArrayList<Deepdive> data) {
        String html = "";
        
        html = html + "<table>"
                    + "<tr>"
                    +     "<th>Rank (by the 2021 Gap)</th>"
                    +     "<th>Local Government Area</th>"
                    +     "<th>2016 Count of People</th>"
                    +     "<th>2021 Count of People Based on Filters</th>"
                    +     "<th>The Gap 2016</th>"
                    +     "<th>The Gap 2021 (Indig minus Non Indig, based on filters)</th>"
                    +     "<th>Change in The Gap (2021 minus 2016)</th>"
                    + "</tr>";
        
        int rankingCount = 0;
        for (Deepdive dataPoint : data) {
            rankingCount++;
            html = html + "<tr>"
                        + "<td>" + rankingCount + "</td>"
                        + "<td>" + dataPoint.getLga() + "</td>"
                        + "<td>No 2016 Data</td>"
                        + "<td>" + dataPoint.getCount2021() + "</td>"
                        + "<td>No 2016 Data</td>"
                        + "<td>" + dataPoint.getGap2021() + "</td>"
                        + "<td>N/A</td>"
                        + "</tr>";
        }

        html = html + "</table>";
        return html;

    }

    public String outputSimilarLgaTable(ArrayList<Deepdive> data) {
        String html = "";
        
        html = html + "<table>"
                    + "<tr>"
                    +     "<th>Rank (most similar to least similar)</th>"
                    +     "<th>Local Government Area</th>"
                    +     "<th>Count of People Based on Filters</th>"
                    + "</tr>";
        
        int rankingCount = 0;
        for (Deepdive dataPoint : data) {
            html = html + "<tr>"
                        + "<td>" + rankingCount + "</td>"
                        + "<td>" + dataPoint.getLga() + "</td>"
                        + "<td>" + dataPoint.getCount() + "</td>"
                        + "</tr>";
            rankingCount++;
        }

        html = html + "</table>";
        return html;

    }

}
