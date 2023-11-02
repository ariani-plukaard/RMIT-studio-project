package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

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
        html = html + "<head>" + """
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Homepage</title>
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
                <div class ='buttons'>
                    <a href='/'>HOME</a>
                    <a href='data-overview.html'>DATA OVERVIEW</a>
                    <a href='data-deep-dive.html'>DATA DEEP DIVE</a>
                    <a href='mission.html'>ABOUT US</a>
                </div>
            </div>
        """;
        //<a href='page2B.html'>Sub Task 2.B</a>
        //<a href='page3B.html'>Sub Task 3.B</a>
        JDBCConnection jdbc = new JDBCConnection();
        // Add header content block
        html = html + """
            <div class='wrapper'>
                <div class='wrapperText'>
                    <h2>Exploring the gap</h2>
                    <p>Between Indigenous & Non-Indigenous Australians</p>
                    <a href='data-overview.html'>DATA OVERVIEW</a>
                </div>
                <div class='wrapperImg'>
                    <img src='vop.png' alt='vop' height='1200' width='672'>
                    <div>Source: SBS News</div>
                </div>
                
            </div>
        """;
        ArrayList<LTHC> LTHC = jdbc.getLTHCgraph();
        ArrayList<NonSchoolCompletion> nonSchoolCompletion = jdbc.getNSEgraph();
        ArrayList<SchoolCompletion> schoolCompletion = jdbc.getSEgraph();
        ArrayList<totalPop> totalPops = jdbc.getTotalPop();
        ArrayList<totalPop> totalStatePops = jdbc.getTotalStatePop();

        int totalIn2016 = totalPops.get(0).getTotal2016()+totalPops.get(1).getTotal2016()+totalPops.get(2).getTotal2016();
        int totalIn2021 = totalPops.get(0).getTotal2021()+totalPops.get(1).getTotal2021()+totalPops.get(2).getTotal2021();
        
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTotalIn2016 = decimalFormat.format(totalIn2016);
        String formattedTotalIn2021 = decimalFormat.format(totalIn2021);
        
        
        // Add HTML for the page content
        html = html + """
            <div class='landingcontent'>
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
                        <p>Our main focus are on the topics of <b>health, education, and population age</b>, as these topics give us 
                        a greater understanding on the disparities between Indigenous and Non-Indigenous Australians</p>
                        <a href='data-overview.html'>See Data Overview</a>
                    </div>
                </div>
                <div class='landingStats'>
                    <h5>Key Statistics at Census:</h5>
                    <ol>""";
                            
        html = html +   "<li>Total Population in 2016: <span style='color:black'>"+formattedTotalIn2016+"</span></li>";
        html = html +   "<li>Total Population in 2021: <span style='color:black'>"+formattedTotalIn2021+"</span></li>";
        html = html + """
                         </ol>
                    <p class='subheading'>Population by Australian States and Territories (2021):</p>
                    <ul>""";
                            
        for(totalPop totalState: totalStatePops){
            html = html + "<li>"+totalState.getState()+": <span style='color:black'>"+decimalFormat.format(totalState.getTotal2021())+"</span></li>";                        
        }
        html = html + """
                    </ul>
                    <p class='subheading'>Total Number of Local Government Areas (LGAs):</p>
                    <ul>
                        """;
                            

        html +=         "<li>In 2016: <span style='color:black'>"+jdbc.getTotalLga("2016")+"</span></li>";
        html +=         "<li>In 2021: <span style='color:black'>"+jdbc.getTotalLga("2021")+"</span></li>";
        html += """       
                    </ul>     
                </div>
            </div>
        """;
        
        // Add HTML for the graphs carousel
        //TO DO: add graphs instead of images on each slide
        html = html + """
            <div id="carouselGraphs" class="carousel slide" data-bs-theme="dark">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselGraphs" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselGraphs" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselGraphs" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner">
                """;
    
        html = html + """
            <div id="carouselGraphs" class="carousel slide" data-bs-theme="dark">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselGraphs" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselGraphs" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselGraphs" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <head>
                            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                            <script type="text/javascript">
                            google.charts.load('current', {'packages':['bar']});
                            google.charts.setOnLoadCallback(drawChart1);
                        
                            function drawChart1() {
                                var data = google.visualization.arrayToDataTable([
                                ['Condition', 'Indigenous', 'Non-Indigenous'],
                                """;
            for (int i = 0; i < LTHC.size(); ++i) {
                String condition = LTHC.get(i).getCondition();
                if (condition.contains("disease") || condition.contains("condition") || condition.contains("health")){
                    condition = condition.replace("disease", "");
                    condition = condition.replace("condition", "");
                    condition = condition.replace("health", "");
                    html = html + "['" + condition + "', " + ((float)LTHC.get(i).getIndigCount()/totalPops.get(0).getTotal2021())*100 + ", " + ((float)LTHC.get(i).getNonIndigCount()/totalPops.get(2).getTotal2021())*100 + "]";
                }
                else{
                    html = html + "['" + condition + "', " + ((float)LTHC.get(i).getIndigCount()/totalPops.get(0).getTotal2021())*100 + ", " + ((float)LTHC.get(i).getNonIndigCount()/totalPops.get(2).getTotal2021())*100 + "]";
                }
                
                if (i < LTHC.size() - 1) {
                    html = html + ", ";
                }
            }                      
        html = html + """
                    ]);
                            var options = {
                                chart: {
                                    title: 'Long Term Health Conditions',
                                    subtitle: 'Proportional values displayed (2021)',
                                  },
                                backgroundColor: {
                                    fill: '#f0f0f0'
                                  },
                                chartArea: {
                                    backgroundColor: '#f0f0f0'
                                  }  
                                };
                        
                                var chart = new google.charts.Bar(document.getElementById('columnchart_material1'));
                        
                                chart.draw(data, google.charts.Bar.convertOptions(options));
                            }
                            </script>
                        </head>
                        <body>
                            <div class='d-block mx-auto w-98 py-5' id="columnchart_material1" style="width: 900px; height: 600px;"></div>
                        </body>
                    </div>
                    <div class="carousel-item">
                        <head>
                            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                            <script type="text/javascript">
                            google.charts.load('current', {'packages':['bar']});
                            google.charts.setOnLoadCallback(drawChart);
                        
                            function drawChart() {
                                var data = google.visualization.arrayToDataTable([
                                ['Non-School Education', 'Indigenous', 'Non-Indigenous'],
                                """;
            for (int i = 0; i < nonSchoolCompletion.size(); ++i) {
                String education = nonSchoolCompletion.get(i).getEducation();
                if (education.contains("Postgrad")){
                    html = html + "['pd, gd & gc', " + ((float)nonSchoolCompletion.get(i).getIndigCount()/totalPops.get(0).getTotal2021())*100 + ", " + ((float)nonSchoolCompletion.get(i).getNonIndigCount()/totalPops.get(2).getTotal2021())*100 + "]";
                }
                else {
                    html = html + "['" + education + "', " + ((float)nonSchoolCompletion.get(i).getIndigCount()/totalPops.get(0).getTotal2021())*100 + ", " + ((float)nonSchoolCompletion.get(i).getNonIndigCount()/totalPops.get(2).getTotal2021())*100 + "]";
                }
                
            
                if (i < nonSchoolCompletion.size() - 1) {
                    html = html + ", ";
                }
            }                                          

        html = html +  """
                                ]);
                        
                                var options = {
                                chart: {
                                    title: 'Non-School Completion',
                                    subtitle: 'Proportional values displayed (2021)',
                                  },
                                backgroundColor: {
                                    fill: '#f0f0f0'
                                  },
                                chartArea: {
                                    backgroundColor: '#f0f0f0'
                                  }  
                                };
                        
                                var chart = new google.charts.Bar(document.getElementById('columnchart_material'));
                        
                                chart.draw(data, google.charts.Bar.convertOptions(options));
                            }
                            </script>
                        </head>
                        <body>
                            <div class='d-block mx-auto w-98 py-5' id="columnchart_material" style="width: 900px; height: 600px;"></div>
                        </body>
                    </div>
                    <div class="carousel-item">
                        <head>
                            <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                            <script type="text/javascript">
                            google.charts.load('current', {'packages':['bar']});
                            google.charts.setOnLoadCallback(drawChart2);
                        
                            function drawChart2() {
                                var data = google.visualization.arrayToDataTable([
                                ['School Year', 'Indigenous', 'Non-Indigenous'],
                                """;
            for (int i = 0; i < schoolCompletion.size(); ++i) {
                html = html + "['" + schoolCompletion.get(i).getSchoolYear() + "', " + ((float)schoolCompletion.get(i).getIndigCount()/totalPops.get(0).getTotal2021())*100 + ", " + ((float)schoolCompletion.get(i).getNonIndigCount()/totalPops.get(2).getTotal2021())*100 + "]";
            
                if (i < schoolCompletion.size() - 1) {
                    html = html + ", ";
                }
            }                                
                                        
        html = html +  """
                                ]);
                        
                                var options2 = {
                                chart: {
                                    title: 'School Completion',
                                    subtitle: 'Proportional values displayed (2021)',
                                  },
                                backgroundColor: {
                                    fill: '#f0f0f0'
                                  },
                                chartArea: {
                                    backgroundColor: '#f0f0f0'
                                  }  
                                };
                        
                                var chart = new google.charts.Bar(document.getElementById('columnchart_material2'));
                        
                                chart.draw(data, google.charts.Bar.convertOptions(options2));
                            }
                            </script>
                        </head>
                        <body>
                            <div class='d-block mx-auto w-98 py-5' id="columnchart_material2" style="width: 900px; height: 600px;"></div>
                        </body>
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselGraphs" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselGraphs" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
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
                        <li><a href='mission.html'>FAQ</a></li>
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
