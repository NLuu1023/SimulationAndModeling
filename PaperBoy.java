//paperboy simulation

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PaperBoy {

    public static void main(String[] args) throws FileNotFoundException {
        //call printwriter for output.txt
        PrintWriter outpt = new PrintWriter(new File("output.txt"));
        //ORDERING 16 PAPERS PER DAY
        //initiate newsboy object
        NewsBoy bob = new NewsBoy();
        //initiate stats object
        ProfitStats stats = new ProfitStats();
        //initiate demand generator
        Demand dmd = new Demand();
        int day, demand;
        outpt.println("ORDERING 16 PAPERS EACH DAY:");
        //start for loop for 1000 day
        for(day=1; day<=1000; day++){
            //get demand
            demand = dmd.dmdGen();
            bob.setDemand(demand, 1);
            //record profit stats
            stats.setStats(bob.getProfit());
            stats.paperStats(bob.getSold(), demand);
            //order paper
            bob.order(1);
            //print out results
            if(day>=950) {
                outpt.println("For day " + day + ", demand is " + demand + " and papers sold is " + bob.getSold());
                outpt.println("Profit is " + bob.getProfit() + " and ordered " + bob.getOrdered());
            }
        }
        //print the stats of collected info
        outpt.println("STATISTICS FOR THE 1000 DAYS OF SALES WHILE ORDERING 16 PAPERS EACH DAY*****************************************************************************");
        outpt.println("Average papers sold per day is " + stats.getSoldAverage() + ", average demand per day is " + stats.getDmdAverage() + ", sold variance is " + stats.getSoldVariance() + ", and demand variance is " + stats.getDmdVariance());
        outpt.println("Average profit per day is " + stats.getAverage() + " and the variance is " + stats.getVariance());

        //ORDERING THE SAME NUMBER OF PAPERS EACH DAY AS CALLED FOR THE LAST
        //initiate newsboy object
        NewsBoy ron = new NewsBoy();
        //initiate stats object
        ProfitStats stats2 = new ProfitStats();
        //initiate demand generator
        Demand dmd2 = new Demand();
        int demand2;
        outpt.println("\n\nORDERING SAME NUMBER EACH DAY AS CALLED FOR THE LAST:");
        //start for loop for 1000 day
        for(day=1; day<=1000; day++){
            //get demand
            demand2 = dmd2.dmdGen();
            ron.setDemand(demand2, 2);
            //record profit stats
            stats2.setStats(ron.getProfit());
            stats2.paperStats(ron.getSold(), demand2);
            //order paper
            ron.order(2);
            //print out results
            if(day>=950) {
                outpt.println("For day " + day + ", demand is " + demand2 + " and papers sold is " + ron.getSold());
                outpt.println("Profit is " + ron.getProfit() + " and ordered " + ron.getOrdered());
            }
        }
        //print the stats of collected info
        outpt.println("STATISTICS FOR THE 1000 DAYS OF SALES WHILE ORDERING SAME NUMBER EACH AS CALLED FOR THE LAST*****************************************************************************");
        outpt.println("Average papers sold per day is " + stats2.getSoldAverage() + ", average demand per day is " + stats2.getDmdAverage() + ", sold variance is " + stats2.getSoldVariance() + ", and demand variance is " + stats2.getDmdVariance());
        outpt.println("Average profit per day is " + stats2.getAverage() + " and the variance is " + stats2.getVariance());

        //ORDERING ONE LESS EACH DAY THAN THE PREVIOUS
        //initiate newsboy object
        NewsBoy don = new NewsBoy();
        //initiate stats object
        ProfitStats stats3 = new ProfitStats();
        //initiate demand generator
        Demand dmd3 = new Demand();
        int demand3;
        outpt.println("\n\nORDERING ONE LESS EACH DAY THAN THE PREVIOUS:");
        //start for loop for 1000 day
        for(day=1; day<=1000; day++){
            //get demand
            demand3 = dmd3.dmdGen();
            don.setDemand(demand3, 3);
            //record profit stats
            stats3.setStats(don.getProfit());
            stats3.paperStats(don.getSold(), demand3);
            //order paper
            don.order(3);
            //print out results
            if(day>=950) {
                outpt.println("For day " + day + ", demand is " + demand3 + " and papers sold is " + don.getSold());
                outpt.println("Profit is " + don.getProfit() + " and ordered " + don.getOrdered());
            }
        }
        //print the stats of collected info
        outpt.println("STATISTICS FOR THE 1000 DAYS OF SALES WHILE ORDERING ONE LESS THAN THE PREVIOUS DAY*****************************************************************************");
        outpt.println("Average papers sold per day is " + stats3.getSoldAverage() + ", average demand per day is " + stats3.getDmdAverage() + ", sold variance is " + stats3.getSoldVariance() + ", and demand variance is " + stats3.getDmdVariance());
        outpt.println("Average profit per day is " + stats3.getAverage() + " and the variance is " + stats3.getVariance());
        outpt.flush();
    }
}

//create the newsboy object
class NewsBoy{
    //create the variables needed for the day
    //newspaper demand for the day
    private int demand;
    //amount of newspaper purchased
    private int ordered;
    //amount of newspaper customer bought
    private int bought;
    //amount of newspaper sold
    private int sold;
    //amount of profit made
    private double profit;
    //create constructor for the newsboy object and set all variables to 0
    public NewsBoy(){
        demand = ordered = bought = sold =0;
        profit = 0.0;
    }
    //create function to show how many newspaper the newsboy will order daily
    public int order(int n){
        int x;
        if(n==1) {
            //the newsboy order 16 papers daily
            x = 16;
        }
        else if(n==2){
            //order the same number each day as called last
            x = demand;
        }
        else{
            //order one less than previous demand
            x = demand - 1;
        }
        ordered = x;
        return x;
    }
    //create the function to calculate the profit while setting the # of papers sold and ordered
    private void behavior(int n){
        //receive the ordered paper as bought
        bought = ordered;
        //set the sold # according to the amount of papers in demand
        //if demand is >= bought, amount sold will be = to bought
        //otherwise # of paper sold = to the demand
        if(demand >= bought){
            sold = bought;
            //calculate the profit
            profit = sold - ((bought * 0.35)-((bought - sold) * 0.05));
        }
        else {
            sold = demand;
            //calculate the profit
            profit = sold - (bought * 0.35);
        }
        //then order for tomorrow
        ordered = order(n);
    }
    //setter for the demand
    public void setDemand(int x, int n){
        demand = x;
        //call behavior() for the demand
        behavior(n);
    }
    //getters for the profit, sold, and ordered
    public double getProfit() {
        return profit;
    }
    public int getSold() {
        return sold;
    }
    public int getOrdered() {
        return ordered;
    }
}

//create object to maintain statistics of profits
class ProfitStats{
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
    //amount of papers sold
    private int sold;
    //sum of sold
    private int soldSum;
    //amount of demand
    private int demand;
    //sum of demand
    private int dmdSum;
    //have all the variables similar to profit
    private int soldSum2;
    private int dmdSum2;
    private int dmdAverage;
    private int soldAverage;
    private int soldVariance;
    private int dmdVariance;
    private int count;
    //constructor to set all variables to 0
    public ProfitStats(){
        profit = profSum = profSum2 = average = stDev = variance = 0.0;
        sold = soldSum = soldSum2 = soldAverage = soldVariance = 0;
        demand = dmdSum = dmdSum2 = dmdAverage = dmdVariance = 0;
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
        return;
    }
    //function for stats for papers using sold and demand
    public void paperStats(int x, int y){
        //sold stats
        sold = x;
        soldSum += sold;
        soldSum2 += sold * sold;
        soldAverage = soldSum / count;
        soldVariance = soldSum2 / count - soldAverage * soldAverage;
        //demand stats
        demand = y;
        dmdSum += demand;
        dmdSum2 += demand * demand;
        dmdAverage = dmdSum / count;
        dmdVariance = dmdSum2 / count - dmdAverage * dmdAverage;
    }
    //getters for the variables
    public double getAverage() {
        return average;
    }
    public double getVariance() {
        return variance;
    }
    public int getSoldAverage() {
        return soldAverage;
    }
    public int getDmdAverage() {
        return dmdAverage;
    }
    public int getDmdVariance() {
        return dmdVariance;
    }
    public int getSoldVariance() {
        return soldVariance;
    }
}

//object to maintain demand
class Demand{
    //create variables
    //amount of paper demand
    private int demand;
    //create constructor setting the demand at 0
    public Demand(){
        demand = 0;
    }
    //random generator for the demand
    public int dmdGen(){
        int x = (int)(Math.random()*100);
        if(x <= 8.3) demand = 15;
        else if(x <= 16.6) demand = 16;
        else if(x <= 58.3) demand = 17;
        else if(x <= 75) demand = 18;
        else if(x <= 91.7) demand = 19;
        else demand = 20;
        return demand;
    }
}
