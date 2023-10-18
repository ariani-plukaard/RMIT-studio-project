package app;

/**
 * Class represeting a Persona from the Studio Project database
 */
public class Persona {
   // Persona table attributes, all text values
   private String name, imageFilePath, attributes, background, needs, goals, skillsExp;

   /**
    * Create a Persona and set the fields
    */
   public Persona(String name, String imageFilePath, String attributes, String background, String needs, String goals, String skillsExp) {
      this.name = name;
      this.imageFilePath = imageFilePath;
      this.attributes = attributes;
      this.background = background;
      this.needs = needs;
      this.goals = goals;
      this.skillsExp = skillsExp;
   }

   public String getName() {
      return name;
   }

   public String getImageFilePath() {
      return imageFilePath;
   }

    public String getAttributes() {
      return attributes;
   }

    public String getBackground() {
      return background;
   }

   public String getNeeds() {
      return needs;
   }

   public String getGoals() {
      return goals;
   }

   public String getSkillsExp() {
      return skillsExp;
   }
}