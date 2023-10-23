package app;

public class SchoolCompletion {
    private String schoolYear;
    
    private int indig_count;

    private int non_indig_count;

    public SchoolCompletion(String schoolYear, int indig_count,int non_indig_count){
        this.schoolYear = schoolYear;
        this.indig_count = indig_count;
        this.non_indig_count = non_indig_count;
    }

    public String getSchoolYear(){
        return this.schoolYear;
    }
    
    public int getIndigCount(){
        return this.indig_count;
    }

    public int getNonIndigCount(){
        return this.non_indig_count;
    }
}
