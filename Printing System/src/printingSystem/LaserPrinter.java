/**
 * ********************************************************************
 * File:	  LaserPrinter.java	(Class)
 * Author:	  Raveen Savinda Rathnayake
 * Date:      10/12/18
 * Version:	  1.0
 * ***********************************************************************
 */

package printingSystem;

/**
 *
 * This class represents the behaviour of a shared Laser Printer in the Printing System.
 */
public class LaserPrinter implements ServicePrinter {

    private int currentPaperLevel;
    private int currentTonerLevel;
    private int printedDocCount;
    private final String printerName;
    private ThreadGroup students;

    public LaserPrinter(String printerName, ThreadGroup students) {
        this.printerName = printerName;
        this.currentPaperLevel = ServicePrinter.Full_Paper_Tray;
        this.currentTonerLevel = ServicePrinter.Full_Toner_Level;
        this.printedDocCount = 0;
        this.students = students;
    }

    @Override
    public String toString() {
        return "[ PrinterID:" + printerName + ", Paper Level: " + currentPaperLevel + ", Toner Level: "
                + currentTonerLevel + ", Documents Printed: " + printedDocCount + " ]";
    }

    @Override
    public synchronized void printDocument(Document document) {
        System.out.println(this.toString());
        // wait until sufficient resources are obtained to print
        while (this.currentPaperLevel < document.getNumberOfPages()
                || this.currentTonerLevel < document.getNumberOfPages()) {
            try {
                System.out.println("Waiting for printing");
                wait();
            } catch (InterruptedException exception) {
                System.out.println(exception.toString());
            }
        }

        // check whether the printer has sufficient resources to print the document
        if (this.currentPaperLevel > document.getNumberOfPages()
                && this.currentTonerLevel > document.getNumberOfPages()) {
            currentPaperLevel -= document.getNumberOfPages();
            currentTonerLevel -= document.getNumberOfPages();
            printedDocCount += 1;
            System.out.println("Printing Successful");
        }

        System.out.println(this.toString());

        // notify all other threads
        notifyAll();
    }

    @Override
    public synchronized void refillPaper() {
        while (this.currentPaperLevel + LaserPrinter.SheetsPerPack > LaserPrinter.Full_Paper_Tray) {
            try {
                if (this.checkStudentAvailability()) {
                    System.out.println("Waiting to refill paper");
                    wait(5000);
                } else {
                    System.out.println("Student finished printing process and refilling papers can be done");
                    break;
                }
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }

        if (this.currentPaperLevel + LaserPrinter.SheetsPerPack < LaserPrinter.Full_Paper_Tray) {
            this.currentPaperLevel += LaserPrinter.SheetsPerPack;
            System.out.println("Paper refilling Successful");
            System.out.println(this.toString());
        }

        // notify all other threads
        notifyAll();
    }

    @Override
    public synchronized void replaceTonerCartridge() {
        while (this.currentTonerLevel > LaserPrinter.Minimum_Toner_Level) {
            try {
                if (this.checkStudentAvailability()) {
                    System.out.println("Waiting to replace toner");
                    wait(5000);
                } else {
                    System.out.println("Student finished printing process and replacing toner can be done");
                    break;
                }
            } catch (InterruptedException exception) {
                System.out.println(exception.toString());
            }
        }

        if (this.currentTonerLevel < LaserPrinter.Minimum_Toner_Level) {
            this.currentTonerLevel = LaserPrinter.Full_Toner_Level;
            System.out.println("Toner replacing Successful");
            System.out.println(this.toString());
        }

        // notify all other threads
        notifyAll();
    }

    private boolean checkStudentAvailability() {
        return students.activeCount() > 0;
    }
}
