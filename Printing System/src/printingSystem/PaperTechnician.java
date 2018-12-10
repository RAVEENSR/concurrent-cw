/**
 * ********************************************************************
 * File:	  PaperTechnician.java	(Class)
 * Author:	  Raveen Savinda Rathnayake
 * Date:      10/12/18
 * Version:	  1.0
 * ***********************************************************************
 */

package printingSystem;

/**
 *
 * This class represent the behaviour of the technician who refills the papers for the Printing System.
 */
public class PaperTechnician extends Technician {

    // constructor for the paper technician class
    public PaperTechnician(String name, ThreadGroup group, LaserPrinter printer) {
        // calling the parent constructor
        super(name, group, printer);
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                // call the method that tries to refill papers
                this.printer.refillPaper();
                System.out.println(this);

                // sleep the current thread for a random amount of time
                sleep(generateRandomSleepTime());
            } catch (InterruptedException exception) {
                System.out.println(exception.toString());
            }
        }
    }

    @Override
    public String toString() {
        return "[ Paper Technician: " + this.name + " finished refilling paper process ]";
    }
}
