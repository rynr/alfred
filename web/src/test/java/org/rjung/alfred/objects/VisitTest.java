package org.rjung.alfred.objects;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;

import javax.print.PrintService;

import org.junit.Ignore;
import org.junit.Test;

public class VisitTest {

    private static final String PRINTER = "QL-570";

    @Test
    @Ignore
    public void test() throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();

        PrintService printer = null;
        PrintService[] printers = PrinterJob.lookupPrintServices();
        for (int i = 0; i < printers.length; i++) {
            if (PRINTER.equals(printers[i].getName())) {
                printer = printers[i];
                break;
            }
        }
        if (printer != null) {
            job.setPrintService(printer);
        }

        PageFormat pageFormat = job.defaultPage();

        System.out
                .println("PageFormat: " + pageFormat.getWidth() + "x"
                        + pageFormat.getHeight() + "; "
                        + pageFormat.getImageableWidth() + "x"
                        + pageFormat.getImageableHeight() + "; "
                        + pageFormat.getImageableX() + "x"
                        + pageFormat.getImageableY());

        pageFormat.setOrientation(PageFormat.LANDSCAPE);
        Paper paper = new Paper();
        double width = 3.5 * 72;
        double height = 1.1 * 72;
        double border = 6;
        paper.setSize(height, width);
        paper.setImageableArea(border, border, height - border, width - border);
        pageFormat.setPaper(paper);

        System.out
                .println("PageFormat: " + pageFormat.getWidth() + "x"
                        + pageFormat.getHeight() + "; "
                        + pageFormat.getImageableWidth() + "x"
                        + pageFormat.getImageableHeight() + "; "
                        + pageFormat.getImageableX() + "x"
                        + pageFormat.getImageableY());

        Visit visit = new Visit();
        visit.setName("Rainer Jung");
        visit.setCompany("SinnerSchrader Deutschland GmbH");
        visit.setEmail("Rainer.Jung@sinnerschrader.com");
        visit.setId(1234643l);
        visit.setCreatedAt(new Date());
        job.setPrintable(visit, pageFormat);

        job.print();
    }

    protected static double fromPPItoCM(double dpi) {
        return dpi / 72 / 0.393700787;
    }

    protected static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

}
