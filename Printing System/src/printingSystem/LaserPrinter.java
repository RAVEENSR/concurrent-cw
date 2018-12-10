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
        displayMessage(0, 0);

        // wait until sufficient resources are obtained to print
        while (this.currentPaperLevel < document.getNumberOfPages()
                || this.currentTonerLevel < document.getNumberOfPages()) {
            try {
                this.displayMessage(1, 0);
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
            displayMessage(2, 0);
        }

        this.displayMessage(0, 0);

        // notify all other threads
        notifyAll();
    }

    @Override
    public synchronized void refillPaper() {
        while (this.currentPaperLevel + LaserPrinter.SheetsPerPack > LaserPrinter.Full_Paper_Tray) {
            try {
                if (this.checkStudentAvailability()) {
                    displayMessage(3, 0);
                    wait(5000);
                } else {
                    displayMessage(7, 0);
                    break;
                }
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }

        if (this.currentPaperLevel + LaserPrinter.SheetsPerPack < LaserPrinter.Full_Paper_Tray) {
            int newPaperLevel = this.currentPaperLevel += LaserPrinter.SheetsPerPack;
            this.displayMessage(4, newPaperLevel);
        }

        // notify all other threads
        notifyAll();
    }

    @Override
    public synchronized void replaceTonerCartridge() {
        while (this.currentTonerLevel > LaserPrinter.Minimum_Toner_Level) {
            try {
                if (this.checkStudentAvailability()) {
                    displayMessage(5, 0);
                    wait(5000);
                } else {
                    displayMessage(7, 0);
                    break;
                }
            } catch (InterruptedException exception) {
                System.out.println(exception.toString());
            }
        }

        if (this.currentTonerLevel < LaserPrinter.Minimum_Toner_Level) {
            this.currentTonerLevel = LaserPrinter.Full_Toner_Level;
            displayMessage(6, 0);
        }

        // notify all other threads
        notifyAll();
    }

    private boolean checkStudentAvailability() {
        if (students.activeCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void displayMessage(int message, int count) {
        switch (message) {
            case 0:
                System.out.println(this.toString());
                break;
            case 1:
                System.out.println("Waiting for printing");
                break;
            case 2:
                System.out.println("Printing Successful");
                break;
            case 3:
                System.out.println("Wait for paper refill");
                break;
            case 4:
                System.out.println("Paper refilling Successful");
                break;
            case 5:
                System.out.println("Wait for toner replace");
                break;
            case 6:
                System.out.println("Toner replacing Successful");
                break;
            case 7:
                System.out.println("Student finished printing process");
                break;
        }
    }
}
