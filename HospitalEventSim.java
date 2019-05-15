import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

//Hospital event based simulation

public class HospitalEventSim {
    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter output = new PrintWriter(new File("output2.txt"));
        //main sim clock in minutes
        double bigTime = 0.0;
        //limit of big time will be 100 hrs
        double endTime = 6000;
        //create event time variables
        double eventTime;
        //change in time and service rate
        double deltaTime;
        //critical time, time to die, service rate
        double criticalTime = 0, timeToDie, serviceRate = 0;
        //generate event queue and patient queue
        GenericManager<Event> eventQueue = new GenericManager<>();
        GenericManager<Patient> patientQueue = new GenericManager<>();
        //unique death ID
        int mydeadID = 0, illness = 0;
        double patientTimeArrive = 0;
        //number of patient currently in line
        int numInQueue;
        //number of events in the event queue
        int numEventQueue;
        //number through line, treatment, system
        int totalThruLine = 0, totalThruSystem = 0, totalThruFac = 0;
        int totalHeartTreated = 0, totalGastroTreated = 0, totalBleederTreated = 0;
        //set all total times to zero
        double totalHeartTimeNSystem1 = 0, totalGastroTimeNSystem1 = 0, totalBleederTimeNSystem1 = 0, timeTilTreat, timeTilServer;
        double totalTimeInLine = 0, totalTimeInLine2 = 0;
        //patient ID from death event
        int patientdeathID;
        //the number of patient that died
        int totalHeartDied = 0, totalGastroDied = 0, totalBleederDied = 0;
        //variable for first other patients in line beside heart patient
        int firstOtherPatient = 0;
        //boolean value on whether the doctor is busy or not
        boolean smith = false;
        boolean sims = false;
        //patient with smith treating
        Patient smithTreat = new Patient(-9);
        Patient simsTreat = new Patient(-9);
        //service time
        double delTimeServe;
        //time arrival and change in time arrival
        double timeArrive = 0, deltaTimeArrive = 0;
        //patient object for patients entering the queue
        Patient newPatient = new Patient(-9);
        //patient object for those exiting the queue
        Patient comPatient = new Patient(-9);
        //create last event
        Event workEvent = new Event(8, endTime, 0);
        //add event to queue
        numEventQueue = eventQueue.addInOrder(workEvent);
        //add the arrival for the first patient: patient arrive 3 per hr
        deltaTimeArrive = timeToArriveOrServe(0.05);
        //add delta time to big time to set event time
        eventTime = bigTime + deltaTimeArrive;
        //create new event for the first patient
        workEvent = new Event(1, eventTime, 0);
        //store event on queue
        numEventQueue = eventQueue.addInOrder(workEvent);
        //start the process with the first event
        workEvent = eventQueue.getValue(0);
        while(workEvent.getEventType() != 8) {
            //get the change of time with the first event
            deltaTime = workEvent.getTime() - bigTime;
            timeTilTreat = updatePatient(patientQueue, deltaTime);
            //update all patient time in server
            timeTilServer = updateServers(smithTreat, smith, deltaTime, simsTreat, sims);
            bigTime = workEvent.getTime();
            //get the number of patient in line
            numInQueue = patientQueue.getCount();
            //the balk/dead time of patient is heart=
            //event type switch
            switch (workEvent.getEventType()) {
                case 1: //patient arrives
                    //if smith is not busy and there is no one in line, then patient directly go to be treated
                    //set the big time as the time of arrival
                    if (!smith && numInQueue <= 0) {
                        newPatient = new Patient(-9);
                        newPatient.setTimeArrive(bigTime);
                        //generate type of illness
                        newPatient.setIllness(generateIllness());
                        //the service rate of heart=2 per hr, gastro=4 per hr, bleeding=6 per hr
                        Random randomNum = new Random();
                        if(newPatient.getIllness() == 1){
                            serviceRate = 2.0/60.0;
                            criticalTime = randomNum.nextGaussian()*10+35;
                        }
                        else if(newPatient.getIllness() == 2){
                            serviceRate = 4.0/60.0;
                            criticalTime = randomNum.nextGaussian()*30+80;
                        }
                        else if(newPatient.getIllness() == 3){
                            serviceRate = 0.1;
                            criticalTime = randomNum.nextGaussian()*30+65;
                        }
                        smith = true;
                        smithTreat = newPatient;
                        //calculate change in time with service
                        delTimeServe = timeToArriveOrServe(serviceRate);
                        //update event time
                        eventTime = bigTime + delTimeServe;
                        //move event to the leaving treatment
                        workEvent = new Event(5, eventTime, -9);
                        if(smithTreat.getIllness() == 1){
                            totalHeartTreated++;
                            totalHeartTimeNSystem1 += eventTime - smithTreat.getTimeArrive();
                        }
                        else if(smithTreat.getIllness() == 2){
                            totalGastroTreated++;
                            totalGastroTimeNSystem1 += eventTime - smithTreat.getTimeArrive();
                        }
                        else {
                            totalBleederTreated++;
                            totalBleederTimeNSystem1 += eventTime - smithTreat.getTimeArrive();
                        }
                        //put new event to event queue
                        numEventQueue = eventQueue.addInOrder(workEvent);
                    }
                    //smith is busy but sims is not
                    else if(smith && !sims && numInQueue <= 0) {
                        newPatient = new Patient(-9);
                        newPatient.setTimeArrive(bigTime);
                        //generate type of illness
                        newPatient.setIllness(generateIllness());
                        //the service rate of heart=2 per hr, gastro=4 per hr, bleeding=6 per hr
                        Random randomNum = new Random();
                        if (newPatient.getIllness() == 1) {
                            serviceRate = 2.0 / 60.0;
                            criticalTime = randomNum.nextGaussian() * 10 + 35;
                        } else if (newPatient.getIllness() == 2) {
                            serviceRate = 4.0 / 60.0;
                            criticalTime = randomNum.nextGaussian() * 30 + 80;
                        } else if (newPatient.getIllness() == 3) {
                            serviceRate = 0.1;
                            criticalTime = randomNum.nextGaussian() * 30 + 65;
                        }
                        sims = true;
                        simsTreat = newPatient;
                        //calculate change in time with service
                        delTimeServe = timeToArriveOrServe(serviceRate);
                        //update event time
                        eventTime = bigTime + delTimeServe;
                        //move event to the leaving treatment
                        workEvent = new Event(6, eventTime, -9);
                        if (simsTreat.getIllness() == 1) {
                            totalHeartTreated++;
                            totalHeartTimeNSystem1 += eventTime - simsTreat.getTimeArrive();
                        } else if (simsTreat.getIllness() == 2) {
                            totalGastroTreated++;
                            totalGastroTimeNSystem1 += eventTime - simsTreat.getTimeArrive();
                        } else {
                            totalBleederTreated++;
                            totalBleederTimeNSystem1 += eventTime - simsTreat.getTimeArrive();
                        }
                        //put new event to event queue
                        numEventQueue = eventQueue.addInOrder(workEvent);
                    }
                    //both doctor is busy
                    else if (smith && sims) {
                        //generate unigue id
                        mydeadID++;
                        //generate new patient
                        newPatient = new Patient(mydeadID);
                        //generate type of illness
                        newPatient.setIllness(generateIllness());
                        //the service rate of heart=2 per hr, gastro=4 per hr, bleeding=6 per hr
                        Random randomNum = new Random();
                        if(newPatient.getIllness() == 1){
                            serviceRate = 2.0/60.0;
                            criticalTime = randomNum.nextGaussian()*10+35;
                        }
                        else if(newPatient.getIllness() == 2){
                            serviceRate = 4.0/60.0;
                            criticalTime = randomNum.nextGaussian()*30+80;
                        }
                        else if(newPatient.getIllness() == 3){
                            serviceRate = 0.1;
                            criticalTime = randomNum.nextGaussian()*30+65;
                        }
                        //set time of arrival with bigtime
                        newPatient.setTimeArrive(bigTime);
                        //put customer in line at the end unless the patient is a heart patient, then put patient at the end after the last heart patient
                        if(patientQueue.getCount() <= 0){
                            patientQueue.addToFront(newPatient);
                        }
                        else if(newPatient.getIllness() == 1){
                            for(int i=0; i<patientQueue.getCount(); i++){
                                if(patientQueue.getValue(i).getIllness() != 1){
                                    firstOtherPatient = i;
                                    break;
                                }
                            }
                            patientQueue.addToSpecificSpot(firstOtherPatient, newPatient);
                        }
                        else {
                            patientQueue.addToEnd(newPatient);
                        }
                        //create death time
                        timeToDie = bigTime + criticalTime;
                        //create new event
                        workEvent = new Event(7, timeToDie, mydeadID);
                        numEventQueue = eventQueue.addInOrder(workEvent);
                    }
                    //create new event for next customer
                    //calculate change in time arrive 3 per hour
                    deltaTimeArrive = timeToArriveOrServe(0.05);
                    eventTime = bigTime + deltaTimeArrive;
                    //create new event
                    workEvent = new Event(1, eventTime, 0);
                    numEventQueue = eventQueue.addInOrder(workEvent);
                    break;
                case 2: //patient gets in line
                    break;
                case 3: //patient gets treated by smith
                    //decrement the number of patient in line
                    numInQueue = patientQueue.getCount();
                    if(!smith && numInQueue>0){
                        comPatient = patientQueue.getValue(0);
                        patientdeathID = comPatient.getdeadID();
                        //purge event from queue
                        purgeEvent(eventQueue, patientdeathID);
                        //treat patient
                        if(comPatient.getIllness() == 1){
                            serviceRate = 2.0/60.0;
                        }
                        else if(comPatient.getIllness() == 2){
                            serviceRate = 4.0/60.0;
                        }
                        else if(comPatient.getIllness() == 3){
                            serviceRate = 0.1;
                        }
                        patientQueue.remove(0);
                        smith = true;
                        smithTreat = comPatient;
                        //update time
                        delTimeServe = timeToArriveOrServe(serviceRate);
                        eventTime = delTimeServe + bigTime;
                        workEvent = new Event(5, eventTime, -9);
                        if(smithTreat.getIllness() == 1){
                            totalHeartTreated++;
                            totalHeartTimeNSystem1 += eventTime - comPatient.getTimeArrive();
                        }
                        else if(smithTreat.getIllness() == 2){
                            totalGastroTreated++;
                            totalGastroTimeNSystem1 += eventTime - comPatient.getTimeArrive();
                        }
                        else{
                            totalBleederTreated++;
                            totalBleederTimeNSystem1 += eventTime - comPatient.getTimeArrive();
                        }
                        numEventQueue = eventQueue.addInOrder(workEvent);
                    }
                    break;
                case 4: //go to sims if sims is free
                    numInQueue = patientQueue.getCount();
                    if(!sims && numInQueue>0){
                        comPatient = patientQueue.getValue(0);
                        patientdeathID = comPatient.getdeadID();
                        //purge death event from queue
                        purgeEvent(eventQueue, patientdeathID);
                        //treat patient
                        if(comPatient.getIllness() == 1){
                            serviceRate = 2.0/60.0;
                        }
                        else if(comPatient.getIllness() == 2){
                            serviceRate = 4.0/60.0;
                        }
                        else if(comPatient.getIllness() == 3){
                            serviceRate = 0.1;
                        }
                        patientQueue.remove(0);
                        sims = true;
                        simsTreat = comPatient;
                        //update time
                        delTimeServe = timeToArriveOrServe(serviceRate);
                        eventTime = delTimeServe + bigTime;
                        workEvent = new Event(6, eventTime, -9);
                        //update stat
                        if(simsTreat.getIllness() == 1){
                            totalHeartTreated++;
                            totalHeartTimeNSystem1 += eventTime - comPatient.getTimeArrive();
                        }
                        else if(simsTreat.getIllness() == 2){
                            totalGastroTreated++;
                            totalGastroTimeNSystem1 += eventTime - comPatient.getTimeArrive();
                        }
                        else{
                            totalBleederTreated++;
                            totalBleederTimeNSystem1 += eventTime - comPatient.getTimeArrive();
                        }
                        numEventQueue = eventQueue.addInOrder(workEvent);
                    }
                    break;
                case 5: //patient leaves treatment area
                    //update number of customer through line
                    //set server to not busy
                    smith = false;
                    numInQueue = patientQueue.getCount();
                    //if there is another patient in line, get them into treatment
                    if(numInQueue > 0){
                        workEvent = new Event(3, bigTime+0.1, -9);
                        numEventQueue = eventQueue.addInOrder(workEvent);
                    }
                    break;
                case 6: //patient leaves treatment area
                    //update number of customer through line
                    //set server to not busy
                    sims = false;
                    numInQueue = patientQueue.getCount();
                    //if there is another patient in line, get them into treatment
                    if(numInQueue > 0){
                        workEvent = new Event(4, bigTime+0.1, -9);
                        numEventQueue = eventQueue.addInOrder(workEvent);
                    }
                    break;
                case 7: //patient dies
                    //remove patient from line
                    //add to total died
                    if(patientQueue.getCount() > 0) {
                        patientdeathID = workEvent.getPatientID();
                        patientTimeArrive = whenArrive(patientQueue, patientdeathID);
                        illness = removeDead(patientQueue, patientdeathID);
                        if (illness == 1) {
                            totalHeartDied++;
                            //totalHeartTimeNSystem1 += bigTime - patientTimeArrive;
                        } else if (illness == 2) {
                            totalGastroDied++;
                            //totalGastroTimeNSystem1 += bigTime - patientTimeArrive;
                        } else {
                            totalBleederDied++;
                            //totalBleederTimeNSystem1 += bigTime - patientTimeArrive;
                        }
                    }
                    break;
                case 8: //shut down
                    output.println("in event 8, BAD");
                    continue;
                default: //bad event
                    output.println("bad event "+workEvent.getEventType()+" at time"+workEvent.getTime());
            }
            //done with event in switch statement, delete it
            eventQueue.remove(0);
            //start on the next event
            workEvent = eventQueue.getValue(0);
        }
        //print out the stats
        output.println("Statistic for hospital simulation:\n");
        output.println("Total heart patient served: " + totalHeartTreated);
        output.println("Total gastro patient served: " + totalGastroTreated);
        output.println("Total bleeding patient served: " + totalBleederTreated + "\n");
        output.println("Total heart patient died: " + totalHeartDied);
        output.println("Total gastro patient died: " + totalGastroDied);
        output.println("Total bleeding patient died: " + totalBleederDied + "\n");
        output.println("Average time in system for heart patient: " + totalHeartTimeNSystem1 / (totalHeartTreated + totalHeartDied) + " minutes");
        output.println("Average time in system for gastro patient: " + totalGastroTimeNSystem1 / (totalGastroTreated + totalGastroDied) + " minutes");
        output.println("Average time in system for bleeder patient: " + totalBleederTimeNSystem1 / (totalBleederTreated + totalBleederDied) + " minutes");

        output.flush();
    }
    //function to update all patient time in server
    public static double updateServers(Patient smith, boolean b1, double deltaTime, Patient sims, boolean b2){
        double serverTime = 0.0;
        if(b1&&b2)return serverTime=2*deltaTime;
        else if(b1 || b2)serverTime = deltaTime;
        return serverTime;
    }

    //function to add up all the time spent for patient in line
    public static double updatePatient(GenericManager<Patient> patientLine, double deltaTime){
        double lineTime = 0.0;
        //add patient to number of patient in line
        int patientInLine = patientLine.getCount();
        //if there are no patient in line, there is no change in time yet as the patient move onto being treated
        if(patientInLine == 0) return lineTime;
            //else time in line is update where each patient in line gets the dalta time added
        else return lineTime = deltaTime * patientInLine;
    }

    //random generator for type of sickness 1=heart, 2=gastro, 3=bleeding
    public static int generateIllness(){
        int illness = 0;
        int x = (int)(Math.random()*100);
        if(x <= 30)illness = 1;
        else if(x <= 50)illness = 2;
        else illness = 3;
        return illness;
    }

    //random generator for time to arrive or service time
    public static double timeToArriveOrServe(double rate){
        double deltaTime;
        double bigx = Math.random();
        if(bigx>0.9)bigx = Math.random();
        deltaTime = -Math.log(1.0-bigx)/(rate);
        return deltaTime;
    }

    //function to remove the event from queue
    public static void purgeEvent(GenericManager<Event> eventQueue, int deadPatientID){
        int i, numNLine, patientDiedID;
        Event workEvent = new Event(1, 1.0, 1);
        //find the patient in line
        numNLine = eventQueue.getCount();
        workEvent = eventQueue.getValue(0);
        patientDiedID = workEvent.getPatientID();
        i=0;
        while(patientDiedID != deadPatientID && i <= numNLine-1){
            workEvent = eventQueue.getValue(i);
            patientDiedID = workEvent.getPatientID();
            i++;
        }
        //remove event
        if(patientDiedID == deadPatientID)eventQueue.remove(i-1);
        return;
    }

    //function to remove dead patient
    public static int removeDead(GenericManager<Patient> patient, int deadID){
        int i, numInLine, patientDeadID, illness = 0;
        Patient workPatient = new Patient(-9);
        //get number of patient in queue
        numInLine = patient.getCount();
        //search through the patient queue until found the patient with the unique id
        workPatient = patient.getValue(0);
        patientDeadID = workPatient.getdeadID();
        i=0;
        while(patientDeadID != deadID && i <= numInLine-1){
            workPatient = patient.getValue(i);
            patientDeadID = workPatient.getdeadID();
            i++;
        }
        illness = workPatient.getIllness();
        //remove the patient with the unique id while returning the type of illess the patient had for stat calculation
        if(i==0){
            patient.remove(0);
        }
        else if(patientDeadID == deadID && i > 0){
            patient.remove(i-1);
        }
        return illness;
    }

    //function to find patient original arrival time to figure out how long they have been in the system.
    public static double whenArrive(GenericManager<Patient> patient, int deadID){
        int i, numInLine, patientDeadID;
        double time = 0;
        Patient workPatient = new Patient(-9);
        //get number of patient in queue
        numInLine = patient.getCount();
        //search through the patient queue until found the patient with the unique id
        workPatient = patient.getValue(0);
        patientDeadID = workPatient.getdeadID();
        i=0;
        while(patientDeadID != deadID && i <= numInLine-1){
            workPatient = patient.getValue(i);
            patientDeadID = workPatient.getdeadID();
            i++;
        }
        //remove the patient with the unique id while returning the type of illess the patient had for stat calculation
        return workPatient.getTimeArrive();
    }
}

//patient class
class Patient implements Comparable{
    //create the needed variables
    //time patient is in line
    protected double timeNLine;
    //time patient is in server
    protected double timeNServer;
    //time patient is in system
    protected double timeNSystem;
    //time the patient arrived
    protected double timeArrive;
    //unique id of patient
    protected int deadID;
    //type of illness 1=heart, 2=gastro, 3=bleeding
    protected int illness;
    //death time
    protected double deathtime;
    //constructor with dead ID as the parameter and setting the rest as zero
    public Patient(int x){
        timeNLine=timeNServer=timeNSystem=deathtime=illness=0;
        deadID=x;
    }
    //function to compare the patient's timeNLine
    public int compareTo(Object o) {
        if(getTimeNLine() > ((Patient)o).getTimeNLine())return 1;
        else if(getTimeNLine() < ((Patient)o).getTimeNLine())return -1;
        else return 0;
    }
    //function to set arrival time
    public void setTimeArrive(double x){
        timeArrive = x;
    }
    //function to add time in line as the patient is waiting in line
    public void setTimeNLine(double x){
        timeNLine += x;
    }
    //function to add time in server
    public void setTimeNServer(double x){
        timeNServer += x;
    }
    //function to add time in line and time in server together as time in system
    public void setTimeNSystem(){
        timeNSystem = timeNLine + timeNServer;
    }
    //function to set the deadID
    public void setdeadID(int x){
        deadID = x;
    }
    //function to set the illness type
    public void setIllness(int x) {
        illness = x;
    }
    //getters for all the variables
    public double getTimeArrive() {
        return timeArrive;
    }
    public double getTimeNServer() {
        return timeNServer;
    }
    public double getTimeNLine() {
        return timeNLine;
    }
    public double getTimeNSystem() {
        return timeNSystem;
    }
    public int getdeadID() {
        return deadID;
    }
    public int getIllness() {
        return illness;
    }
}

//event class
class Event implements Comparable{
    //add needed variables
    //event type
    protected int eventType;
    //event time
    protected double time;
    //dying patient identifier
    protected int patientID;
    //constructor
    public Event(int etype, double etime, int patientdie){
        eventType=etype;
        time=etime;
        //if dying event, patient ID = dying ID
        if(etype==5)patientID = patientdie;
        else patientID = -9;
    }
    //function to compare event time
    public int compareTo(Object o) {
        if(getTime() > ((Event)o).getTime())return 1;
        else if(getTime() < ((Event)o).getTime())return -1;
        else return 0;
    }
    //getters
    public double getTime() {
        return time;
    }
    public int getEventType() {
        return eventType;
    }
    public int getPatientID() {
        return patientID;
    }
}

//generator class for queue
class GenericManager<T extends Comparable>{
    //add needed variables
    //generic arraylist
    protected ArrayList<T> myList = new ArrayList<T>();
    protected int count;
    //constructor setting the count to zero
    public GenericManager(){
        count = 0;
    }
    //function to add onto the end of the array
    public int addToEnd(T x){
        myList.add(count, x);
        count++;
        return count;
    }
    //function to add in the front of array
    public int addToFront(T x){
        myList.add(0, x);
        count++;
        return count;
    }
    //function to add event in order
    public int addInOrder(T x){
        int i;
        if(count ==  0 || x.compareTo(myList.get(0)) == -1) myList.add(0, x);
        else if(x.compareTo(myList.get(count-1)) == 1 || x.compareTo(myList.get(count-1)) == 0) myList.add(count, x);
        else{
            i=0;
            while (i<count && x.compareTo(myList.get(i)) == 1) i++;
            myList.add(i, x);
        }
        count++;
        return count;
    }
    //function to add to a specific spot in array
    public int addToSpecificSpot(int spot, T x){
        myList.add(spot, x);
        count++;
        return count;
    }
    //function to get value from array
    public T getValue(int i){
        if(i<count)return myList.get(i);
        else return myList.get(0);
    }
    //function to remove event
    public void remove(int i){
        if(i>=0 && i<=count-1){
            myList.remove(i);
            count--;
        }
    }
    //getters
    public int getCount() {
        return count;
    }
}