/**
 * ********************************************************************
 * File:	  PrintingSystem.java	(Class)
 * Author:	  Raveen Savinda Rathnayake
 * Date:      10/12/18
 * Version:	  1.0
 * ***********************************************************************
 */

package printingSystem;

/**
 *
 * This class controls the behaviour of the whole Printing System.
 */
public class PrintingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // declaring and initializing the two thread groups technician and student
        ThreadGroup studentThreadGroup = new ThreadGroup("Student Thread Group");
        ThreadGroup technicianThreadGroup = new ThreadGroup("Technician Thread Group");

        // the laser printer object
        LaserPrinter laserPrinter = new LaserPrinter("Laser Printer-01", studentThreadGroup);

        // the student and technician threads
        Student stud1 = new Student("Student1", studentThreadGroup, laserPrinter);
        Student stud2 = new Student("Student2", studentThreadGroup, laserPrinter);
        Student stud3 = new Student("Student3", studentThreadGroup, laserPrinter);
        Student stud4 = new Student("Student4", studentThreadGroup, laserPrinter);

        Technician paperTechnician = new PaperTechnician("Technician1", technicianThreadGroup, laserPrinter);
        Technician tonerTechnician = new TonerTechnician("Technician2", technicianThreadGroup, laserPrinter);

        // starting all threads
        stud1.start();
        stud2.start();
        stud3.start();
        stud4.start();
        paperTechnician.start();
        tonerTechnician.start();

        // wait for all the threads to complete
        try {
            stud1.join();
            stud2.join();
            stud3.join();
            stud4.join();
            paperTechnician.join();
            tonerTechnician.join();
        } catch (InterruptedException exception) {
            System.out.println(exception.toString());
        }
    }
}
