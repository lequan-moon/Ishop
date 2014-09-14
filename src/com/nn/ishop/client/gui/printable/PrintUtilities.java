package com.nn.ishop.client.gui.printable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RepaintManager;

public class PrintUtilities implements Printable {
	private Component componentToBePrinted;

	public static void printComponent(Component c) {
		new PrintUtilities(c).print();
	}

	public PrintUtilities(Component componentToBePrinted) {
		this.componentToBePrinted = componentToBePrinted;
	}

	public void print() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		if (printJob.printDialog())
			try {
				printJob.print();
			} catch (PrinterException pe) {
				System.out.println("Error printing: " + pe);
			}
	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
		if (pageIndex > 0) {
			return (NO_SUCH_PAGE);
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pageFormat.getImageableX(), pageFormat
					.getImageableY());
			disableDoubleBuffering(componentToBePrinted);
			componentToBePrinted.paint(g2d);
			enableDoubleBuffering(componentToBePrinted);
			return (PAGE_EXISTS);
		}
	}

	/**
	 * The speed and quality of printing suffers dramatically if any of the
	 * containers have double buffering turned on. So this turns if off
	 * globally.
	 * 
	 * @see enableDoubleBuffering
	 */
	public static void disableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	/** Re-enables double buffering globally. */

	public static void enableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}
	
	 public void showPreview(JDialog parentJDialog){
		 new PrintPreview(parentJDialog, PrintUtilities.this, "MyPage- preview") ;
	 }
	
	public static void main(String args[]) throws Exception
	{
		JDialog f = new JDialog();
		f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel abc = new JPanel(new BorderLayout());
		JLabel lbl = new JLabel(new ImageIcon(PrintPreview.class.getResource("test.png")));
		lbl.setBorder(BorderFactory.createEtchedBorder());
		abc.add(lbl, BorderLayout.NORTH);
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
		Object[][] data = {
				{"Kathy", "Smith","Snowboarding", new Integer(5), new Boolean(false)},
				{"John", "Doe","Rowing", new Integer(3), new Boolean(true)},
				{"Sue", "Black","Knitting", new Integer(2), new Boolean(false)},
				{"Jane", "White","Speed reading", new Integer(20), new Boolean(true)},
				{"Joe", "Brown","Pool", new Integer(10), new Boolean(false)}
		};
		
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		abc.add(scrollPane, BorderLayout.CENTER);
		
		f.getContentPane().add(abc);		
		f.pack();
		//f.setVisible(true);
		PrintUtilities utl = new PrintUtilities(abc);
		utl.showPreview(f);
	}
}
