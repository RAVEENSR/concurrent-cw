/***********************************************************************
 * File:	  Technician.java	(Class)
 * Author:	  Raveen Savinda Rathnayake
 * Date:      10/12/18
 * Version:	  1.0
 ************************************************************************ */

package printingSystem;

import java.util.Random;

/**
 * This class represents the behaviour of a technician of the Printing System.
 */
public class Technician extends Thread {

    protected LaserPrinter printer;
    protected String name;
    protected ThreadGroup threadGroup;

    // the default constructor for the technician class
    public Technician(String name, ThreadGroup group, LaserPrinter printer) {
        super(group, name);
        this.printer = printer;
        this.name = name;
        this.threadGroup = group;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int generateRandomSleepTime(){
        Random ran=new Random();
        int random=ran.nextInt(2000 - 1000 + 1) + 1000;
        return random;
    }
}
