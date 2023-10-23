package app;

public class LTHC {
    private String condition;
    
    private int indig_count;

    private int non_indig_count;

    public LTHC(String condition, int indig_count,int non_indig_count){
        this.condition = condition;
        this.indig_count = indig_count;
        this.non_indig_count = non_indig_count;
    }

    public String getCondition(){
        return this.condition;
    }
    
    public int getIndigCount(){
        return this.indig_count;
    }

    public int getNonIndigCount(){
        return this.non_indig_count;
    }
    
}
