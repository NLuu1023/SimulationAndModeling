//Drug simulation

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class InfectionAgentSim {
    //start of main
    public static void main(String[] args) throws FileNotFoundException {
        //print to Printwriter
        PrintWriter output = new PrintWriter(new File("output5.txt"));

        //create array holding the cell objects
        Cell[][] patient1 = new Cell[10][10];
        Cell[][] patient2 = new Cell[10][10];
        Cell[][] patient3 = new Cell[10][10];
        Cell[][] patient4 = new Cell[10][10];
        Cell[][] patient5 = new Cell[10][10];
        Cell[][] patient6 = new Cell[10][10];
        Cell[][] patient7 = new Cell[10][10];
        Cell[][] patient8 = new Cell[10][10];
        Cell[][] patient9 = new Cell[10][10];
        Cell[][] patient10 = new Cell[10][10];
        //create cells and populate the cell arrays
        int i=0;
        int j=0;
        for(i=0;i<=9;i++){
            for(j=0;j<=9;j++){
                patient1[i][j] = new Cell(i,j,10,10);
                patient2[i][j] = new Cell(i,j,10,10);
                patient3[i][j] = new Cell(i,j,10,10);
                patient4[i][j] = new Cell(i,j,10,10);
                patient5[i][j] = new Cell(i,j,10,10);
                patient6[i][j] = new Cell(i,j,10,10);
                patient7[i][j] = new Cell(i,j,10,10);
                patient8[i][j] = new Cell(i,j,10,10);
                patient9[i][j] = new Cell(i,j,10,10);
                patient10[i][j] = new Cell(i,j,10,10);
            }
        }
        //run the simulation for 10 years
        output.println("Growth of muscle tissue for 10 patients for 10 years with YOUTH injection.\n");
        int injections;
        int year = 1;
        while(year != 11){
            //inject youth cells, 5 for first year, 3 for each year after that
            if(year == 1)injections = 5;
            else injections = 3;
            for(i=1;i<=injections;i++) {
                patient1[randPos()][randPos()].injectYouth(50 + year, patient1);
                patient2[randPos()][randPos()].injectYouth(50 + year, patient2);
                patient3[randPos()][randPos()].injectYouth(50 + year, patient3);
                patient4[randPos()][randPos()].injectYouth(50 + year, patient4);
                patient5[randPos()][randPos()].injectYouth(50 + year, patient5);
                patient6[randPos()][randPos()].injectYouth(50 + year, patient6);
                patient7[randPos()][randPos()].injectYouth(50 + year, patient7);
                patient8[randPos()][randPos()].injectYouth(50 + year, patient8);
                patient9[randPos()][randPos()].injectYouth(50 + year, patient9);
                patient10[randPos()][randPos()].injectYouth(50 + year, patient10);
            }
            //simulate infection of cells
            for(i=0;i<=9;i++){
                for(j=0;j<=9;j++){
                    patient1[i][j].changeStatus(patient1, i, j);
                    patient2[i][j].changeStatus(patient1, i, j);
                    patient3[i][j].changeStatus(patient1, i, j);
                    patient4[i][j].changeStatus(patient1, i, j);
                    patient5[i][j].changeStatus(patient1, i, j);
                    patient6[i][j].changeStatus(patient1, i, j);
                    patient7[i][j].changeStatus(patient1, i, j);
                    patient8[i][j].changeStatus(patient1, i, j);
                    patient9[i][j].changeStatus(patient1, i, j);
                    patient10[i][j].changeStatus(patient1, i, j);
                }
            }
            //set back the just got infected status to false
            for(i=0;i<=9;i++){
                for(j=0;j<=9;j++){
                    patient1[i][j].setJustGotInfected();
                    patient2[i][j].setJustGotInfected();
                    patient3[i][j].setJustGotInfected();
                    patient4[i][j].setJustGotInfected();
                    patient5[i][j].setJustGotInfected();
                    patient6[i][j].setJustGotInfected();
                    patient7[i][j].setJustGotInfected();
                    patient8[i][j].setJustGotInfected();
                    patient9[i][j].setJustGotInfected();
                    patient10[i][j].setJustGotInfected();
                }
            }
            year++;
        }
        //print out the table
        printGrid(patient1, "Patient 1", output);
        output.println();
        printGrid(patient2, "Patient 2", output);
        output.println();
        printGrid(patient3, "Patient 3", output);
        output.println();
        printGrid(patient4, "Patient 4", output);
        output.println();
        printGrid(patient5, "Patient 5", output);
        output.println();
        printGrid(patient6, "Patient 6", output);
        output.println();
        printGrid(patient7, "Patient 7", output);
        output.println();
        printGrid(patient8, "Patient 8", output);
        output.println();
        printGrid(patient9, "Patient 9", output);
        output.println();
        printGrid(patient10, "Patient 10", output);

        //add all the healthy cells left to add to stats
        double count1=0, count2=0, count3=0, count4=0, count5=0, count6=0, count7=0, count8=0, count9=0, count10=0;
        for(i=0;i<=9;i++){
            for(j=0;j<=9;j++){
                if(patient1[i][j].getStatus()==1)count1++;
                if(patient2[i][j].getStatus()==1)count2++;
                if(patient3[i][j].getStatus()==1)count3++;
                if(patient4[i][j].getStatus()==1)count4++;
                if(patient5[i][j].getStatus()==1)count5++;
                if(patient6[i][j].getStatus()==1)count6++;
                if(patient7[i][j].getStatus()==1)count7++;
                if(patient8[i][j].getStatus()==1)count8++;
                if(patient9[i][j].getStatus()==1)count9++;
                if(patient10[i][j].getStatus()==1)count10++;
            }
        }
        CellStats stats = new CellStats();
        stats.setStats(count1);
        stats.setStats(count2);
        stats.setStats(count3);
        stats.setStats(count4);
        stats.setStats(count5);
        stats.setStats(count6);
        stats.setStats(count7);
        stats.setStats(count8);
        stats.setStats(count9);
        stats.setStats(count10);

        //print out cell stats
        output.println("\nThe average number of healthy cells is " + stats.getAverage());
        output.println("The variance is " + stats.getVariance());
        output.flush();
    }

    //function to print out the tissue grid
    public static void printGrid(Cell[][] grid, String patient, PrintWriter output){
        int i, j;
        output.println("This is the tissue grid for " + patient + " after 10 years:");
        output.println("Col1    Col2    Col3    Col4    Col5    Col6    Col7    Col8    Col9    Col10");
        for(i=0;i<=9;i++){
            for(j=0;j<=9;j++){
                output.print(numToString(grid[i][j].getStatus()) + "      ");
            }
            output.print("\n");
        }
    }
    //funtion to turn status numbers to actual strings for the printGrid function
    public static String numToString(int number){
        if(number == 1)return "HM";
        else return "MW";
    }
    //random generator for position in the tissue grid to inject YOUTH cells
    public static int randPos(){
        int x = (int)(Math.random()*9);
        return x;
    }
}

//class cell
class Cell{
    //define variables
    //location of cell in the grid i and j
    protected int myi;
    protected int myj;
    //direction of the cell
    protected int myDir;
    //rows of cells
    protected int nrows;
    //columns of cells
    protected int ncols;
    //status of cell: 2=MW or 1=HM
    protected int status;
    //boolean value true=just got infected and can't infect other cells until next year, false=was infected before this year and can infect other cells
    protected boolean justGotInfected;
    //constructor
    public Cell(int i, int j, int n, int m){
        myi = i;
        myj = j;
        nrows = n-1;
        ncols = m-1;
        myDir = virusDir();
        status = seedCell();
        justGotInfected = false;
    }
    //random generator for deciding the direction of the DMD virus cell
    //1=up, 2=down, 3=back, 4=front
    public int virusDir(){
        int dir;
        int x = (int)(Math.random()*100);
        if(x<=15)dir = 1;
        else if(x<=30)dir = 2;
        else if(x<=65)dir = 3;
        else dir = 4;
        return dir;
    }
    //getters
    public int getMyi() {
        return myi;
    }

    public int getMyj() {
        return myj;
    }

    public int getMyDir() {
        return myDir;
    }

    public int getStatus() {
        return status;
    }

    public boolean isJustGotInfected() {
        return justGotInfected;
    }

    //random generator for if a cell being DMD cell
    public int seedCell(){
        int DMD;
        int x = (int)(Math.random()*100);
        if(x<=10)DMD = 2;
        else DMD = 1;
        return DMD;
    }
    //function for behavior of cell during infections
    public void changeStatus(Cell[][] state, int myi, int myj){
        //RULES:
        //change only happens of the cell is next to the infected cell and the infected cell is heading toward this direction
        int i, j;
        //determine of the cell is a HM or MW cell
        //if cell is a MW cell, then start looking for to see if the surrounding cells are HW and are in this cell's direction
        //determine of the cell is a HM or MW cell
        //if cell is a HM cell, then start looking for to see if the surrounding cells are MW and are heading this way
        if(status == 1){
            //check if this cell is in the corners of the tissue
            if(myi==0 && myj==0){
                //if cell is in the upper left corner of the tissue, then start looking to the right and down
                if(state[1][0].getStatus() == 2 && state[1][0].getMyDir() == 1 && !state[1][0].isJustGotInfected()){
                    //if the cell below is MW and are heading in this cell's direction, then this cell is infected with DMD turning the status of this cell to MW
                    status = 2;
                    justGotInfected = true;
                }
                else if(state[0][1].getStatus() == 2 && state[0][1].getMyDir() == 3 && !state[0][1].isJustGotInfected()){
                    //if cell in front is MW, then this cell be infected with DMD
                    status = 2;
                    justGotInfected = true;
                }
            }
            else if(myi==9 && myj==9){
                //if cell is in the lower right corner, then check the cell above and to the left for DMD
                if(state[9][8].getStatus() == 2 && state[9][8].getMyDir() == 4 && !state[9][8].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                else if(state[8][9].getStatus() == 2 && state[8][9].getMyDir() == 2 && !state[8][9].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
            }
            //do the same checking for all the other corners
            else if(myi==9 && myj==0){
                if(state[9][1].getStatus() == 2 && state[9][1].getMyDir() == 3 && !state[9][1].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                else if(state[8][0].getStatus() == 2 && state[8][0].getMyDir() == 2 && !state[8][0].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
            }
            else if(myi==0 && myj==9){
                if(state[0][8].getStatus() == 2 && state[0][8].getMyDir() == 4 && !state[0][8].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                else if(state[1][9].getStatus() == 2 && state[1][9].getMyDir() == 1 && !state[1][9].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
            }
            //if this cell is at the edge if the tissue grid
            //at the top edge
            else if(myi==0){
                //below cell
                if(state[myi+1][myj].getStatus() == 2 && state[myi+1][myj].getMyDir() == 1 && !state[myi+1][myj].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //back cell
                else if(state[myi][myj-1].getStatus() == 2 && state[myi][myj-1].getMyDir() == 4 && !state[myi][myj-1].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //front cell
                else if(state[myi][myj+1].getStatus() == 2 && state[myi][myj+1].getMyDir() == 3 && !state[myi][myj+1].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
            }
            //at the bottom edge
            else if(myi==9){
                //above cell
                if(state[myi-1][myj].getStatus() == 2 && state[myi-1][myj].getMyDir() == 2 && !state[myi-1][myj].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //back cell
                else if(state[myi][myj-1].getStatus() == 2 && state[myi][myj-1].getMyDir() == 4 && !state[myi][myj-1].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //front cell
                else if(state[myi][myj+1].getStatus() == 2 && state[myi][myj+1].getMyDir() == 3 && !state[myi][myj+1].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
            }
            //at the left edge
            else if(myj==0){
                //above cell
                if(state[myi-1][myj].getStatus() == 2 && state[myi-1][myj].getMyDir() == 2 && !state[myi-1][myj].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //below cell
                else if(state[myi+1][myj].getStatus() == 2 && state[myi+1][myj].getMyDir() == 1 && !state[myi+1][myj].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //front cell
                else if(state[myi][myj+1].getStatus() == 2 && state[myi][myj+1].getMyDir() == 3 && !state[myi][myj+1].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
            }
            //at the right edge
            else if(myj==9){
                //above cell
                if(state[myi-1][myj].getStatus() == 2 && state[myi-1][myj].getMyDir() == 2 && !state[myi-1][myj].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //below cell
                else if(state[myi+1][myj].getStatus() == 2 && state[myi+1][myj].getMyDir() == 1 && !state[myi+1][myj].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
                //back cell
                else if(state[myi][myj-1].getStatus() == 2 && state[myi][myj-1].getMyDir() == 4 && !state[myi][myj-1].isJustGotInfected()){
                    status = 2;
                    justGotInfected = true;
                }
            }
            //if this cell is not in the corners or at the edge, check all the cells surrounding this cell for DMD
            //above cell
            else if(state[myi-1][myj].getStatus() == 2 && state[myi-1][myj].getMyDir() == 2 && !state[myi-1][myj].isJustGotInfected()){
                status = 2;
                justGotInfected = true;
            }
            //below cell
            else if(state[myi+1][myj].getStatus() == 2 && state[myi+1][myj].getMyDir() == 1 && !state[myi+1][myj].isJustGotInfected()){
                status = 2;
                justGotInfected = true;
            }
            //back cell
            else if(state[myi][myj-1].getStatus() == 2 && state[myi][myj-1].getMyDir() == 4 && !state[myi][myj-1].isJustGotInfected()){
                status = 2;
                justGotInfected = true;
            }
            //front cell
            else if(state[myi][myj+1].getStatus() == 2 && state[myi][myj+1].getMyDir() == 3 && !state[myi][myj+1].isJustGotInfected()){
                status = 2;
                justGotInfected = true;
            }
        }
        //if the cell is a MW cell, then do nothing as the behavior of the cells surround it will change when its that cell's turn
    }
    //function to change status of justGotInfected to false
    public void setJustGotInfected(){
        justGotInfected = false;
    }
    //function to chance cell back to HM
    public void injectYouth(int age, Cell[][] state){
        //change status of current cell
        status = 1;
        //change the status of surrounding 2 cells according to random generator
        //check if this cell is in the corners of the tissue
        if(myi==0 && myj==0){
            //if cell is in the upper left corner of the tissue, then start looking to the right and down
            if(youthCuredNearCell(age)){
                state[myi+1][myj].setStatus();
                state[myi][myj+1].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi+2][myj].setStatus();
                state[myi][myj+2].setStatus();
            }
        }
        else if(myi==9 && myj==9){
            //if cell is in the lower right corner, then check the cell above and to the left
            if(youthCuredNearCell(age)){
                state[myi-1][myj].setStatus();
                state[myi][myj-1].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi-2][myj].setStatus();
                state[myi][myj-2].setStatus();
            }
        }
        //do the same checking for all the other corners
        else if(myi==9 && myj==0){
            if(youthCuredNearCell(age)){
                state[myi-1][myj].setStatus();
                state[myi][myj+1].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi-2][myj].setStatus();
                state[myi][myj+2].setStatus();
            }
        }
        else if(myi==0 && myj==9){
            if(youthCuredNearCell(age)){
                state[myi][myj-1].setStatus();
                state[myi+1][myj].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi][myj-2].setStatus();
                state[myi+2][myj].setStatus();
            }
        }
        //if this cell is at the edge if the tissue grid
        //check to see if the far cell exist
        //at the top edge
        else if(myi==0){
            if(youthCuredNearCell(age)){
                state[myi+1][myj].setStatus();
                state[myi][myj+1].setStatus();
                state[myi][myj-1].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi+2][myj].setStatus();
                if(myj+2 < 10)state[myi][myj+2].setStatus();
                if(myj-2 >= 0)state[myi][myj-2].setStatus();
            }
        }
        //at the bottom edge
        else if(myi==9){
            if(youthCuredNearCell(age)){
                state[myi-1][myj].setStatus();
                state[myi][myj+1].setStatus();
                state[myi][myj-1].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi-2][myj].setStatus();
                if(myj+2 < 10)state[myi][myj+2].setStatus();
                if(myj-2 >= 0)state[myi][myj-2].setStatus();
            }
        }
        //at the left edge
        else if(myj==0){
            if(youthCuredNearCell(age)){
                state[myi][myj+1].setStatus();
                state[myi+1][myj].setStatus();
                state[myi-1][myj].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi][myj+2].setStatus();
                if(myi+2 < 10)state[myi+2][myj].setStatus();
                if(myi-2 >= 0)state[myi-2][myj].setStatus();
            }
        }
        //at the right edge
        else if(myj==9){
            if(youthCuredNearCell(age)){
                state[myi][myj-1].setStatus();
                state[myi+1][myj].setStatus();
                state[myi-1][myj].setStatus();
            }
            if(youthCuredFarCell(age)){
                state[myi][myj-2].setStatus();
                if(myi+2 < 10)state[myi+2][myj].setStatus();
                if(myi-2 >= 0)state[myi-2][myj].setStatus();
            }
        }
        //if this cell is not in the corners or at the edge, check all the cells surrounding this cell
        //above cell
        else if(youthCuredNearCell(age)){
            state[myi][myj-1].setStatus();
            state[myi][myj+1].setStatus();
            state[myi+1][myj].setStatus();
            state[myi-1][myj].setStatus();
        }
        if(youthCuredFarCell(age)){
            if(myj-2 >= 0)state[myi][myj-2].setStatus();
            if(myj+2 < 10)state[myi][myj+2].setStatus();
            if(myi+2 < 10)state[myi+2][myj].setStatus();
            if(myi-2 >= 0)state[myi-2][myj].setStatus();
        }
    }
    //random generator for if the cell is cured with YOUTH
    public boolean youthCuredNearCell(int age){
        boolean cured = false;
        int x = (int)(Math.random()*100);
        if(x<=85 && age<60)cured = true;
        else if(x<=80 && age==60)cured = true;
        return cured;
    }
    public boolean youthCuredFarCell(int age){
        boolean cured = false;
        int x = (int)(Math.random()*100);
        if(x<=81 && age<60)cured = true;
        else if(x<=74 && age==60)cured = true;
        return cured;
    }
    //function to set status of cell
    public void setStatus(){
        status = 1;
    }
}

//create object to maintain statistics
class CellStats{
    //create variables needed
    //cell for the year
    private double cell;
    //sum of all the cell
    private double cellSum;
    //sum of cell squared
    private double cellSum2;
    //average cell
    private double average;
    //variance
    private double variance;
    //amount of cell total
    private int count;
    //constructor to set all variables to 0
    public CellStats(){
        cell = cellSum = cellSum2 = average = variance = 0.0;
        count = 0;
    }
    //function use to cell to set the statistics
    public void setStats(double x){
        cell = x;
        cellSum += cell;
        cellSum2 += cell * cell;
        count++;
        average = cellSum / count;
        variance = cellSum2 / count - average * average;
    }
    //getters for the variables
    public double getAverage() {
        return average;
    }
    public double getVariance() {
        return variance;
    }
}