//Tree Growth Simulation

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TreeGrowthSim {

    //start main
    public static void main(String[] args) throws FileNotFoundException {
        //call printwriter with output file
        PrintWriter outpt = new PrintWriter(new File("output5.txt"));

        //percent of 1 year trees dying by drought
        double dying1[] = {0.1, 0.05, 0.02};
        //percent of 2 year trees dying by drought
        double dying2[] = {0.1, 0.05, 0.03};
        //percent of 3 year trees dying by drought
        double dying3[] = {0.3, 0.05, 0.03};
        //percent of 4 year trees dying by drought
        double dying4[] = {0.35, 0.05, 0.04};

        //percent of 1 year trees not growing by drought
        double notGrow1[] = {0.7, 0.01, 0.02};
        //percent of 2 year trees not growing by drought
        double notGrow2[] = {0.75, 0.02, 0.03};
        //percent of 3 year trees not growing by drought
        double notGrow3[] = {0.6, 0.02, 0.03};
        //percent of 4 year trees not growing by drought
        double notGrow4[] = {0.65, 0.01, 0.04};

        //percent of 1 year trees attacked by beetles
        double beetle1[] = {0.1, 0.05, 0.0};
        //percent of 2 year trees attacked by beetles
        double beetle2[] = {0.15, 0.05, 0.0};
        //percent of 3 year trees attacked by beetles
        double beetle3[] = {0.3, 0.1, 0.02};
        //percent of 4 year trees attacked by beetles
        double beetle4[] = {0.3, 0.1, 0.02};

        //percent of 1 year trees burned by forest fire
        double fire1[] = {0.15, 0.1, 0.05};
        //percent of 2 year trees burned by forest fire
        double fire2[] = {0.18, 0.12, 0.07};
        //percent of 3 year trees burned by forest fire
        double fire3[] = {0.22, 0.15, 0.1};
        //percent of 4 year trees burned by forest fire
        double fire4[] = {0.3, 0.2, 0.15};

        //variable with the current acres of trees for each year
        int year1 = 400000;
        int year2 = 300000;
        int year3 = 200000;
        int year4 = 100000;

        //call the class tree and stats
        Tree onetree = new Tree(year1, dying1, notGrow1, beetle1, fire1);
        Tree twotree = new Tree(year2, dying2, notGrow2, beetle2, fire2);
        Tree threetree = new Tree(year3, dying3, notGrow3, beetle3, fire3);
        Tree fourtree = new Tree(year4, dying4, notGrow4, beetle4, fire4);
        TreeStats onestats = new TreeStats();
        TreeStats twostats = new TreeStats();
        TreeStats threestats = new TreeStats();
        TreeStats fourstats = new TreeStats();

        outpt.println("Policy: seed the cloud for rain.");

        int i = 0;
        int inch, rain = 0;
        boolean fire;
        //start time loop for 100 years
        for(int year=1; year<=100; year++){
            //generate the weather
            inch = rainWithSeed();
            //inch = rainWithoutSeed();

            if(inch >= 1 && inch <= 3)rain = 0;
            else if(inch >= 4 && inch <= 10)rain = 1;
            else if(inch >= 11)rain = 2;
            //generate fire or not
            fire = forestFire(inch);

            //trees die by fire
            onetree.burnByFire(rain, fire);
            twotree.burnByFire(rain, fire);
            threetree.burnByFire(rain, fire);
            fourtree.burnByFire(rain, fire);

            //trees die by beetle
            onetree.attackByBeetles(rain);
            twotree.attackByBeetles(rain);
            threetree.attackByBeetles(rain);
            fourtree.attackByBeetles(rain);

            //trees die by drought
            onetree.dieByDrought(rain);
            twotree.dieByDrought(rain);
            threetree.dieByDrought(rain);
            fourtree.dieByDrought(rain);

            //trees stagnant
            onetree.notGrowByDrought(rain);
            twotree.notGrowByDrought(rain);
            threetree.notGrowByDrought(rain);
            fourtree.notGrowByDrought(rain);

            //grow the tree to one more year and harvest the four year old trees and replant one years old in its place
            onetree.grow(fourtree.getAcre() - fourtree.getStagnant());
            twotree.grow(onetree.getTempAcre() - onetree.getStagnant());
            threetree.grow(twotree.getTempAcre() - twotree.getStagnant());
            fourtree.grow(threetree.getTempAcre() - threetree.getStagnant());

            //plant new trees that have died
            onetree.plantNew(onetree.getDeadAcre());
            onetree.plantNew(twotree.getDeadAcre());
            onetree.plantNew(threetree.getDeadAcre());
            onetree.plantNew(fourtree.getDeadAcre());

            //cal the stats for the year
            onestats.setStats(onetree.getAcre());
            twostats.setStats(twotree.getAcre());
            threestats.setStats(threetree.getAcre());
            fourstats.setStats(fourtree.getAcre());

            //reset dead acre
            onetree.resetAcre();
            twotree.resetAcre();
            threetree.resetAcre();
            fourtree.resetAcre();

        }

        //calculate the stats for each year
        outpt.println("One years old: avarage = " + onestats.getAverage() + ", variance = " + onestats.getVariance());
        outpt.println("Two years old: avarage = " + twostats.getAverage() + ", variance = " + twostats.getVariance());
        outpt.println("Three years old: avarage = " + threestats.getAverage() + ", variance = " + threestats.getVariance());
        outpt.println("Four years old: avarage = " + fourstats.getAverage() + ", variance = " + fourstats.getVariance());
        outpt.flush();
    }

    //random generator for rainfall inches with no seeding
    public static int rainWithoutSeed(){
        int inch = 0;
        int x = (int)(Math.random()*100);
        if(x <= 1)inch = 1;
        else if(x <= 6)inch = 2;
        else if(x <= 11)inch = 3;
        else if(x <= 14)inch = 4;
        else if(x <= 24)inch = 5;
        else if(x <= 39)inch = 6;
        else if(x <= 59)inch = 7;
        else if(x <= 73)inch = 8;
        else if(x <= 83)inch = 9;
        else if(x <= 93)inch = 10;
        else if(x <= 98)inch = 11;
        else inch = 12;
        return inch;
    }

    //random generator for rainfall inches with seeding
    public static int rainWithSeed(){
        int inch = 0;
        int x = (int)(Math.random()*100);
        if(x <= 1)inch = 1;
        else if(x <= 2)inch = 2;
        else if(x <= 3)inch = 3;
        else if(x <= 5)inch = 4;
        else if(x <= 15)inch = 5;
        else if(x <= 25)inch = 6;
        else if(x <= 45)inch = 7;
        else if(x <= 55)inch = 8;
        else if(x <= 65)inch = 9;
        else if(x <= 75)inch = 10;
        else if(x <= 85)inch = 11;
        else inch = 12;
        return inch;
    }

    //random generator for forest fire
    //return true or false if there is forest fire
    public static boolean forestFire(int inch){
        boolean fire = false;
        int x = (int)(Math.random()*100);
        if(x <= 90 && inch == 1)fire = true;
        else if(x <= 85 && inch == 2)fire = true;
        else if(x <= 65 && inch == 3)fire = true;
        else if(x <= 55 && inch == 4)fire = true;
        else if(x <= 45 && inch == 5)fire = true;
        else if(x <= 30 && inch == 6)fire = true;
        else if(x <= 15 && inch == 7)fire = true;
        else if(x <= 10 && inch == 8)fire = true;
        else if(x <= 5 && inch == 9)fire = true;
        else if(x <= 3 && inch == 10)fire = true;
        else if(x <= 2 && inch == 11)fire = true;
        else if(x <= 1 && inch == 12)fire = true;
        return fire;
    }
}

//create class tree that will go through all the behaviors of the trees
class Tree{
    //create the required variables
    //current number of acres
    private double acre;
    private double tempAcre;
    //number of acre is dead
    private double deadAcre;
    //number of acres of tree not growing
    private double stagnant;
    //# of drought dying fish
    private double drDying[] = {0.0, 0.0, 0.0, 0.0};
    //# of not growing fish in drought
    private double drNotGrow[] = {0.0, 0.0, 0.0, 0.0};
    //# of beetle affected fish
    private double beetle[] = {0.0, 0.0, 0.0, 0.0};
    //# of trees burned by forest fire
    private double burn[] = {0.0, 0.0, 0.0, 0.0};
    //constructor setting inputting the current number of trees and the probabilities array of drought, beetle, and fire
    public Tree(double currentAcre, double[] drDyingPerc, double[] drNotGrowPerc, double[] beetlePerc, double[] burnPerc){
        acre = currentAcre;
        tempAcre = 0;
        deadAcre = 0;
        stagnant = 0;
        for(int i=0;i<=2;i++){
            drDying[i] = drDyingPerc[i];
            drNotGrow[i] = drNotGrowPerc[i];
            beetle[i] = beetlePerc[i];
            burn[i] = burnPerc[i];
        }

    }
    //function to compute trees dying by drought
    public void dieByDrought(int rain){
        deadAcre += acre * drDying[rain];
        acre -= acre * drDying[rain];
    }
    //function to compute trees not growing by drought
    public void notGrowByDrought(int rain){
        stagnant = acre * drNotGrow[rain];
    }
    //function to compute trees die by beetles
    public void attackByBeetles(int rain){
        deadAcre += acre * beetle[rain];
        acre -= acre * beetle[rain];
    }
    //function to compute # trees burn in forest fire
    public void burnByFire(int rain, boolean fire){
        if(fire){
            deadAcre += acre * burn[rain];
            acre -= acre * burn[rain];
        }
    }
    //function to add all the dead acre from the other age group for plant new trees in one year old
    public void plantNew(double moreAcre){
        acre = acre + moreAcre;
    }
    //function to grow trees by one year
    public void grow(double newGrowth){
        tempAcre = acre;
        acre = newGrowth + stagnant;
    }
    //getters
    public double getStagnant() {
        return stagnant;
    }
    public double getAcre() {
        return acre;
    }
    public double getDeadAcre() {
        return deadAcre;
    }
    public double getTempAcre() {
        return tempAcre;
    }
    //function to reset dead acre number
    public void resetAcre(){
        deadAcre = 0;
        tempAcre = 0;
    }
}

//create object to maintain statistics of tree acre
class TreeStats{
    //create variables needed
    //acre for the year
    private double acre;
    //sum of all the acre
    private double acreSum;
    //sum of acre squared
    private double acreSum2;
    //average acre
    private double average;
    //variance
    private double variance;
    //amount of acre total
    private int count;
    //constructor to set all variables to 0
    public TreeStats(){
        acre = acreSum = acreSum2 = average = variance = 0.0;
        count = 0;
    }
    //function use to acre to set the statistics
    public void setStats(double x){
        acre = x;
        acreSum += acre;
        acreSum2 += acre * acre;
        count++;
        average = acreSum / count;
        variance = acreSum2 / count - average * average;
    }
    //getters for the variables
    public double getAverage() {
        return average;
    }
    public double getVariance() {
        return variance;
    }
}
