/** *********************************************************************
 * File:	  Student.java	(Class)
 * Author:	  Raveen Savinda Rathnayake
 * Date:      10/12/18
 * Version:	  1.0
 ************************************************************************ */

package printingSystem;

import java.util.Random;

/**
 *
 * This class represent the behaviour of the student who uses the Printing System.
 */
public class Student extends Thread {
    
    private LaserPrinter laserPrinter;
    private ThreadGroup threadGroup;
    private String studentName;
    
    public Student(String threadName, ThreadGroup group, LaserPrinter laserPrinter) {
        super(group, threadName);
        this.laserPrinter = laserPrinter;
        this.threadGroup = group;
        this.studentName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Document doc = new Document(this.studentName, "DOC " + (i + 1) , generateRandomPageNumber());
            this.laserPrinter.printDocument(doc);
            System.out.println("[ Student: " + this.studentName + " finished printing doc " + (i + 1) + " ]");

            try {
                sleep(generateRandomSleepTime());
            } catch (InterruptedException ex) {
                 System.out.println(ex.toString());
            }
        }
    }

    @Override
    public String toString() {
        return "[ Student: " + this.studentName + " finished printing doc ]";
    }
    
    private int generateRandomPageNumber(){
        Random ran = new Random();
        return ran.nextInt(25 - 10 + 1) + 10;
    }
    
     private int generateRandomSleepTime(){
        Random ran = new Random();
         return ran.nextInt(2000 - 1000 + 1) + 1000;
    }
}
