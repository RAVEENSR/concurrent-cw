/**
 * ********************************************************************
 * File:	  TonerTechnician.java	(Class)
 * Author:	  Raveen Savinda Rathnayake
 * Date:      10/12/18
 * Version:	  1.0
 * ***********************************************************************
 */

package printingSystem;

/**
 * This class represent the behaviour of the technician who refills the toner of the Printing System.
 */
public class TonerTechnician extends Technician {

    public TonerTechnician(String name, ThreadGroup group, LaserPrinter printer) {
        // calling the parent constructor
        super(name, group, printer);
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                // call the method that tries to replace the toner cartridge
                this.printer.replaceTonerCartridge();
                System.out.println("[ Toner Technician: " + this.name
                        + " finished replacing toner process " + (i + 1) + " ]");

                // sleep the current thread for a random amount of time
                sleep(generateRandomSleepTime());
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
