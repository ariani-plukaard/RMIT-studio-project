package app;

public class totalPop {
    private String stat;
    private int total2016;
    private int total2021;

    public totalPop(String stat, int total2016, int total2021){
        this.stat = stat;
        this.total2016 = total2016;
        this.total2021 = total2021;
    }

    public String getStat(){
        return this.stat;
    }

    public int getTotal2016(){
        return this.total2016;
    }

    public int getTotal2021(){
        return this.total2021;
    }

}