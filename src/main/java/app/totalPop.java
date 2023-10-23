package app;

public class totalPop {
    private String status;
    private int total2016;
    private int total2021;
    private String state;

    public totalPop(String status, int total2016, int total2021){
        this.status = status;
        this.total2016 = total2016;
        this.total2021 = total2021;
    }

    public totalPop(String state, int total2021){
        this.state = state;
        this.total2021 = total2021;
    }

    public String getStatus(){
        return this.status;
    }

    public int getTotal2016(){
        return this.total2016;
    }

    public int getTotal2021(){
        return this.total2021;
    }

    public String getState(){
        return this.state;

    }
}