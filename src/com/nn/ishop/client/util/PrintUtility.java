package com.nn.ishop.client.util;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

public class PrintUtility implements Printable,Pageable {
	private Component componentToBePrinted;
	/** Use 72 unit per inch */
	private static double PIXELS_PER_INCH = 72.0;
	private static PageFormat pageFormat;
	static PrinterJob printJob;


	public static void printComponent(Component c) {
		new PrintUtility(c).print();
	}
	
	public PrintUtility(Component componentToBePrinted) {
		this.componentToBePrinted = componentToBePrinted;
	}
	
	public void print() {		
		printJob = PrinterJob.getPrinterJob();		
		pageFormat();
		printJob.setPrintable(this);		
		if (printJob.printDialog())
		try {
			printJob.setPageable(this);
			printJob.print();
		} catch(PrinterException pe) {
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		else
		{
			// no need to show print dialog
			try {
				printJob.setPageable(this);
				printJob.print();
			} catch(PrinterException pe) {
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	static void pageFormat()
	{
		pageFormat = printJob.defaultPage();
		pageFormat.setOrientation(PageFormat.PORTRAIT);		
		
		Paper paper = new Paper();
		double w = 8.5 * PIXELS_PER_INCH;
		double h = 11 * PIXELS_PER_INCH;
	    
		paper.setSize(w,h);
		paper.setImageableArea(0, 0, w, h);		
		pageFormat.setPaper(paper);
	}
	public int print(Graphics g, PageFormat pf, int pageIndex) {
		int response = NO_SUCH_PAGE;
		Graphics2D g2 = (Graphics2D) g;
		// for faster printing, turn off double buffering
		disableDoubleBuffering(componentToBePrinted);
		Dimension d = componentToBePrinted.getSize(); //get size of document
		
		double panelWidth = d.width; //width in pixels
		double panelHeight = d.height; //height in pixels
		double pageHeight = pf.getImageableHeight(); //height of printer page
		double pageWidth = pf.getImageableWidth(); //width of printer page
		//double scale = 1.00; //Scale of output
		double scale = 1.00;
		//total number of pages across.
		int totalPagesX = (int) Math.ceil(scale * panelWidth/pageWidth);
		//total number of pages down.
		int totalPagesY = (int) Math.ceil(scale * panelHeight/pageHeight);
		int totalNumPages = totalPagesX + totalPagesY; //total number of pages
		int xOffset = pageIndex % totalPagesX; //which page across to print
		int yOffset = pageIndex / totalPagesX; //which page down to print
		// make sure not print empty pages
		if (pageIndex >= totalNumPages) {
			response = NO_SUCH_PAGE;
		}
		else {
			// shift Graphic to line up with beginning of print-imageable region
			g2.translate(pf.getImageableX(), pf.getImageableY());
			// shift Graphic to line up with beginning of next page to print
			g2.translate(-xOffset * pageWidth, -yOffset * pageHeight);
			// scale the page so the width fits...
			g2.scale(scale, scale);
			componentToBePrinted.paint(g2);
//			enableDoubleBuffering(componentToBePrinted);
			response = Printable.PAGE_EXISTS;
		}
		enableDoubleBuffering(componentToBePrinted);
		return response;
	}
	
	public static void disableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}
	
	public static void enableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}

	public int getNumberOfPages() {
		return 1;
	}

	public PageFormat getPageFormat(int pageIndex)
			throws IndexOutOfBoundsException {
		return pageFormat;
	}

	public Printable getPrintable(int pageIndex)
			throws IndexOutOfBoundsException {
		return this;
	}
}
