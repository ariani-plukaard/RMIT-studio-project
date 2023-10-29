package app;

public class Deepdive {
    private String lga;
    private int count2016;
    private int count2021;
    private int gap2016;
    private int gap2021;
    private int improve;
    private int count;

    public Deepdive(String lga, int count2016, int count2021, int gap2016, int gap2021, int improve){
        this.lga = lga;
        this.count2016 = count2016;
        this.count2021 = count2021;
        this.gap2016 = gap2016;
        this.gap2021 = gap2021;
        this.improve = improve;
    }

    public Deepdive(String lga, int count){
        this.lga = lga;
        this.count = count;
    }

    public Deepdive(String lga, int count2021, int gap2021){
        this.lga = lga;
        this.count2021 = count2021;
        this.gap2021 = gap2021;
    }

    public String getLga(){
        return lga;
    }

    public int getCount2016(){
        return count2016;
    }

    public int getCount2021(){
        return count2021;
    }

    public int getGap2016(){
        return gap2016;
    }

    public int getGap2021(){
        return gap2021;
    }

    public int improve(){
        return improve;
    }

    public int getCount(){
        return count;
    }
}
