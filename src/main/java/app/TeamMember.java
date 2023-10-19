package app;

/**
 * Class represeting a TeamMember from the Studio Project database
 */

public class TeamMember {
   // TeamMember table attributes, all text values
   private String studentNo, name, email;

   /**
    * Create a TeamMember and set the fields
    */
   public TeamMember(String studentNo, String name, String email) {
      this.studentNo = studentNo;
      this.name = name;
      this.email = email;
   }

   public String getStudentNo() {
      return studentNo;
   }

   public String getName() {
      return name;
   }

   public String getEmail() {
      return email;
   }
}
