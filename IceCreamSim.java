//mother's ice cream parlor profit sim

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class IceCreamSim {
    //start main
    public static void main(String[] args) throws FileNotFoundException {
        //start printwriter to ouput file
        PrintWriter outpt = new PrintWriter(new File("output2.txt"));
        //initiate ice cream parlor object
        Parlor mother = new Parlor();
        //initiate stats
        IceCreamStat motherStat = new IceCreamStat();
        //initiate random generator
        IceCreamGenerator icGen = new IceCreamGenerator();
        outpt.println("MOTHER'S ORDERING PLAN: 50, 60, 60, 50\n");
        int year, week=1, quarter, demand, amount, ordered;
        //start running the sim through 10 year = 52 weeks each
        mother.orderGallons(week);
        for(year=1; year<=10; year++){
            for(week=1; week<=52; week++) {
                //get demand according to the quarter
                if (week <= 13) quarter = 1;
                else if (week <= 26) quarter = 2;
                else if (week <= 39) quarter = 3;
                else quarter = 4;
                demand = icGen.generateGallon(quarter);
                //get items delivered with the generator
                amount = icGen.generateFreq();
                //set demand for the parlor for the week
                mother.setDemand(demand, amount);
                motherStat.setStats(mother.getProfit());
                //order for next week, if week=52, then start again for week=1
                if(week != 52)mother.orderGallons(week+1);
                else mother.orderGallons(1);
            }
        }
        outpt.println("Mother's average = " + motherStat.getAverage() + ", variance = " + motherStat.getVariance() + ", standard deviation = " + motherStat.getStDev() + "\n");
        outpt.flush();
    }
}

//create class for ice cream parlor
class Parlor{
    //create required variables
    //amount of gallons ordered
    private int ordered;
    //amount of gallons delivered
    private int delivered;
    //amount of gallons sold
    private int sold;
    //amount of gallons customers demanded
    private int demand;
    //profits made
    private double profit;
    //create constructor setting all variables to zero
    public Parlor(){
        ordered = delivered = sold = demand = 0;
        profit = 0.0;
    }
    //order function to order gallons
    //int argument for which quarter 1=I, 2=II, 3=III, 4=IV
    public void orderGallons(int week){
        if(week <= 13)ordered = 50;
        else if(week <= 26)ordered = 60;
        else if(week <= 39)ordered = 60;
        else ordered = 50;
    }
    //function for behavior of the sales in the parlor
    private void behavior(){
        //set the sold # according to the amount of gallons in demand
        //if demand is >= delivered, amount sold will be = to delivered
        //otherwise # of paper sold = to the demand
        if(demand >= delivered)sold = delivered;
        else sold = demand;
        //calculate the profit
        profit = (17*sold) - (ordered*7);
    }
    //setter for the demand
    public void setDemand(int demand, int amount){
        this.demand = demand;
        if(amount == 5)delivered = ordered - 5;
        else if(amount == 10)delivered = ordered + 10;
        else delivered = ordered;
        //call behavior() for the demand
        behavior();
    }
    //getters
    public double getProfit() {
        return profit;
    }
}

//create object to maintain statistics of profits
class IceCreamStat{
    //create variables needed
    //profit for the day
    private double profit;
    //sum of all the profit
    private double profSum;
    //sum of profit squared
    private double profSum2;
    //average profit
    private double average;
    //standard deviation
    private double stDev;
    //variance
    private double variance;
    private int count;
    //constructor to set all variables to 0
    public IceCreamStat(){
        profit = profSum = profSum2 = average = stDev = variance = 0.0;
        count = 0;
    }
    //function use to profit to set the statistics
    public void setStats(double x){
        profit = x;
        profSum += profit;
        profSum2 += profit * profit;
        count++;
        average = profSum / count;
        variance = profSum2 / count - average * average;
        stDev = Math.sqrt(variance);
    }
    //getters for the variables
    public double getAverage() {
        return average;
    }
    public double getVariance() {
        return variance;
    }
    public double getStDev() {
        return stDev;
    }
}

//create class of process generators
class IceCreamGenerator{
    //variables required
    //gallons return from the generator
    private int gallon;
    //frequency of ordered gallons delivered
    private int delivered;
    //constructor activating gallon=0
    public IceCreamGenerator(){
        gallon = delivered = 0;
    }
    //function to generate the amount of gallons sold
    //the int in the argument is for what quarter: 1=I, 2=II, 3=III, 4=IV
    public int generateGallon(int quarter){
        int percent;
        percent = (int)(Math.random()*100);
        if(quarter == 1) {
            if (percent <= 30) gallon = 40;
            else if (percent <= 50) gallon = 50;
            else if (percent <= 80) gallon = 60;
            else if (percent <= 90) gallon = 70;
            else gallon = 80;
        }
        else if(quarter == 2) {
            if (percent <= 15) gallon = 40;
            else if (percent <= 55) gallon = 50;
            else if (percent <= 80) gallon = 60;
            else if (percent <= 90) gallon = 70;
            else gallon = 80;
        }
        else if(quarter == 3) {
            if (percent <= 5) gallon = 40;
            else if (percent <= 15) gallon = 50;
            else if (percent <= 45) gallon = 60;
            else if (percent <= 85) gallon = 70;
            else gallon = 80;
        }
        else {
            if (percent <= 40) gallon = 40;
            else if (percent <= 80) gallon = 50;
            else if (percent <= 90) gallon = 60;
            else if (percent <= 95) gallon = 70;
            else gallon = 80;
        }
        return gallon;
    }
    //function for generating frequency of gallons delivered
    //the function will return 5=5 less than ordered, 0=same amount ordered, 10=10 more than ordered
    public int generateFreq(){
        int percent;
        percent = (int)(Math.random()*100);
        if (percent <= 10) delivered = 5;
        else if (percent <= 80) delivered = 0;
        else delivered = 10;
        return delivered;
    }
}