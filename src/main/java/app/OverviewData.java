package app;

/**
 * Class representing data overview for 2021 by state or LGA, from the Studio Project database
 */
public class OverviewData {
    // Data Overview table attributes, all text values
   private String location, category;
   private int count;
   private float propCount;

   /**
    * Create a Data Overview object and set the fields
    */
   public OverviewData(String location, String category, int count) {
      this.location = location;
      this.category = category;
      this.count = count;
   }

   public OverviewData(String location, String category, float propCount) {
      this.location = location;
      this.category = category;
      this.propCount = propCount;
   }

   public String getLocation() {
      return location;
   }

   public String getCategory() {
      return category;
   }

    public int getCount() {
      return count;
   }

   public float getPropCount(){
      return propCount;
   }
}
