package app;

public class NonSchoolCompletion {
    private String education;
    
    private int indig_count;

    private int non_indig_count;

    public NonSchoolCompletion(String education, int indig_count,int non_indig_count){
        this.education = education;
        this.indig_count = indig_count;
        this.non_indig_count = non_indig_count;
    }

    public String getEducation(){
        return this.education;
    }
    
    public int getIndigCount(){
        return this.indig_count;
    }

    public int getNonIndigCount(){
        return this.non_indig_count;
    }
}
