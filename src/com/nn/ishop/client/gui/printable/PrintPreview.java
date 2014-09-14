package com.nn.ishop.client.gui.printable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

public class PrintPreview extends JDialog {
	protected int m_wPage;
	protected int m_hPage;

	protected Printable m_target;
	protected JComboBox m_cbScale;
	protected PreviewContainer m_preview;
	Printable delegatePrintableObject;

	// protected PrintUtilities the_PrintUtilities;

	public PrintPreview(JDialog parentJDialog, Printable target) {
		this(parentJDialog, target, "Print Preview");
	}// C1 ends

	public PrintPreview(JDialog parentJDialog, Printable target, String title) {
		super(parentJDialog, title);
		delegatePrintableObject = target;
		setSize(800, 600);
		setModal(true);
		this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);

		JToolBar tb = new JToolBar();
		JButton bt1 = new JButton("Print", new ImageIcon(PrintPreview.class.getResource("print.png")));
		ActionListener lst = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((PrintUtilities)delegatePrintableObject).print();
			}
		};

		bt1.addActionListener(lst);
		bt1.setAlignmentY(0.5f);
		bt1.setMargin(new Insets(4, 6, 4, 6));
		// tb.add(bt);

		JButton bt = new JButton("Close");
		lst = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};

		bt.addActionListener(lst);
		bt.setAlignmentY(0.5f);
		bt.setMargin(new Insets(2, 6, 2, 6));
		tb.add(bt1);
		tb.add(bt);

		String[] scales = { "10", "25", "50", "100" };

		m_cbScale = new JComboBox(scales);

		lst = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread runner = new Thread() {
					public void run() {
						String str = m_cbScale.getSelectedItem().toString();
						System.out.println("m_cbScale: " + str);
						if (str.endsWith("%")) {
							str.substring(0, str.length() - 1);
						}
						str.trim();
						int scale = 0;

						try {
							scale = Integer.parseInt(str);
						} catch (NumberFormatException ex) {
							System.out.println("scale: " + scale);
							return;
						}

						int w = (int) (m_wPage * scale / 100);
						int h = (int) (m_hPage * scale / 100);

						Component[] comps = m_preview.getComponents();

						for (int k = 0; k < comps.length; k++) {
							if (!(comps[k] instanceof PagePreview)) {
								continue;
							}

							PagePreview pp = (PagePreview) comps[k];
							pp.setScaledSize(w, h);
						}// for() ends
						m_preview.doLayout();
						m_preview.getParent().getParent().validate();

					}// run() ends
				};// Thread ends

				runner.start();
			}
		};// ActionListener ends

		m_cbScale.addActionListener(lst);
		m_cbScale.setMaximumSize(m_cbScale.getPreferredSize());
		m_cbScale.setEditable(true);
		tb.addSeparator();
		tb.add(m_cbScale);

		getContentPane().add(tb, BorderLayout.NORTH);

		m_preview = new PreviewContainer();

		PrinterJob prnJob = PrinterJob.getPrinterJob();
		PageFormat pageFormat = prnJob.defaultPage();

		if (pageFormat.getHeight() == 0 || pageFormat.getWidth() == 0) {
			// error
			return;
		}
		m_wPage = (int) (pageFormat.getWidth());
		m_hPage = (int) (pageFormat.getHeight());

		int scale = 50;

		int w = (int) (m_wPage * scale / 100);
		int h = (int) (m_hPage * scale / 100);

		int pageIndex = 0;

		try {
			while (true) {
				BufferedImage img = new BufferedImage(m_wPage, m_hPage,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = img.getGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, m_wPage, m_hPage);
				if (target.print(g, pageFormat, pageIndex) != Printable.PAGE_EXISTS) {
					break;
				}
				PagePreview pp = new PagePreview(w, h, img);
				m_preview.add(pp);
				pageIndex++;

			}
		} catch (PrinterException pe) {
			pe.printStackTrace();
		}

		JScrollPane ps = new JScrollPane(m_preview);
		getContentPane().add(ps, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}// C2 ends

}// class PrintPreview ends

class PreviewContainer extends JPanel {
	protected int H_GAP = 16;
	protected int V_GAP = 10;

	public Dimension getPreferredSize() {
		int n = getComponentCount();

		if (n == 0) {
			return new Dimension(H_GAP, V_GAP);
		}
		Component comp = getComponent(0);
		Dimension dc = comp.getPreferredSize();

		int w = dc.width;
		int h = dc.height;

		Dimension dp = getParent().getSize();
		int nCol = Math.max((dp.width - H_GAP) / (w + H_GAP), 1);
		int nRow = n / nCol;

		if (nRow * nCol < n)
			nRow++;

		int ww = nCol * (w + H_GAP) + H_GAP;
		int hh = nRow * (h + V_GAP) + V_GAP;

		Insets ins = getInsets();
		return new Dimension(ww + ins.left + ins.right, hh + ins.top
				+ ins.bottom);
	}// getPreferredSize()ends

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void doLayout() {
		Insets ins = getInsets();

		int x = ins.left + H_GAP;
		int y = ins.top + V_GAP;

		int n = getComponentCount();

		if (n == 0)
			return;

		Component comp = getComponent(0);
		Dimension dc = comp.getPreferredSize();
		int w = dc.width;
		int h = dc.height;

		Dimension dp = getParent().getSize();
		int nCol = Math.max((dp.width - H_GAP) / (w + V_GAP), 1);
		int nRow = n / nCol;

		if (nRow * nCol < n)
			nRow++;

		int index = 0;
		for (int k = 0; k < nRow; k++) {
			for (int m = 0; m < nCol; m++) {
				if (index >= n)
					return;
				comp = getComponent(index++);
				comp.setBounds(x, y, w, h);
				x += w + H_GAP;
			}// for(m.) ends
			y += h + V_GAP;
			x = ins.left + H_GAP;
		}// for(k) ends

	}// doLayout() ends

}// class PreviewContainer ends

class PagePreview extends JPanel {
	protected int m_w;
	protected int m_h;
	protected Image m_source;
	protected Image m_img;

	public PagePreview(int w, int h, Image source) {
		m_w = w;
		m_h = h;

		m_source = source;
		m_img = m_source.getScaledInstance(m_w, m_h, Image.SCALE_SMOOTH);
		m_img.flush();
		setBackground(Color.white);
		setBorder(new MatteBorder(1, 1, 2, 2, Color.black));
	}// PagePreview ends

	public void setScaledSize(int w, int h) {
		m_w = w;
		m_h = h;
		m_img = m_source.getScaledInstance(m_w, m_h, Image.SCALE_SMOOTH);
		repaint();
	}// void setScaledSize() ends

	public Dimension getPreferredSize() {
		Insets ins = getInsets();
		return new Dimension(m_w + ins.left + ins.right, m_h + ins.top
				+ ins.bottom);
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(m_img, 0, 0, this);
		paintBorder(g);
	}
}
// /************************ useage **************/

