package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class FilePrinter extends JFrame {
 
	private static final long serialVersionUID = 4020504783850909617L;

private PageFormat pageFormat;

  private FilePageRenderer pageRenderer;

  private String title;

  public FilePrinter() {
    super();
    init();
    PrinterJob pj = PrinterJob.getPrinterJob();
    pageFormat = pj.defaultPage();
    setVisible(true);
  }

  protected void init() {
    setSize(350, 300);
    center();
    Container content = getContentPane();
    content.setLayout(new BorderLayout());

    // Add the menu bar.
    JMenuBar mb = new JMenuBar();
    JMenu file = new JMenu("File", true);
    file.add(new FileOpenAction()).setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
    file.add(new FilePrintAction()).setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
    file.add(new FilePageSetupAction()).setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK
            | Event.SHIFT_MASK));
    file.addSeparator();
    file.add(new FileQuitAction()).setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
    mb.add(file);
    JMenu page = new JMenu("Page", true);
    page.add(new PageNextPageAction()).setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0));
    page.add(new PagePreviousPageAction()).setAccelerator(
        KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0));
    mb.add(page);
    setJMenuBar(mb);

    getContentPane().setLayout(new BorderLayout());

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  protected void center() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    int x = (screenSize.width - frameSize.width) / 2;
    int y = (screenSize.height - frameSize.height) / 2;
    setLocation(x, y);
  }

  public void showTitle() {
    int currentPage = pageRenderer.getCurrentPage() + 1;
    int numPages = pageRenderer.getNumPages();
    setTitle(title + " - page " + currentPage + " of " + numPages);
  }

  public class FileOpenAction extends AbstractAction {
    public FileOpenAction() {
      super("Open...");
    }

    public void actionPerformed(ActionEvent ae) {
      // Pop up a file dialog.
      JFileChooser fc = new JFileChooser(".");
      int result = fc.showOpenDialog(FilePrinter.this);
      if (result != 0) {
        return;
      }
      java.io.File f = fc.getSelectedFile();
      if (f == null) {
        return;
      }
      // Load the specified file.
      try {
        pageRenderer = new FilePageRenderer(f, pageFormat);
        title = "[" + f.getName() + "]";
        showTitle();
        JScrollPane jsp = new JScrollPane(pageRenderer);
        getContentPane().removeAll();
        getContentPane().add(jsp, BorderLayout.CENTER);
        validate();
      } catch (java.io.IOException ioe) {
        System.out.println(ioe);
      }
    }
  }
  public static void main(String[] args) {
    new FilePrinter();
  }

  public class FilePrintAction extends AbstractAction {
    public FilePrintAction() {
      super("Print");
    }

    public void actionPerformed(ActionEvent ae) {
      PrinterJob pj = PrinterJob.getPrinterJob();
      pj.setPrintable(pageRenderer, pageFormat);
      if (pj.printDialog()) {
        try {
          pj.print();
        } catch (PrinterException e) {
          System.out.println(e);
        }
      }
    }
  }

  public class FilePageSetupAction extends AbstractAction {
    public FilePageSetupAction() {
      super("Page setup...");
    }

    public void actionPerformed(ActionEvent ae) {
      PrinterJob pj = PrinterJob.getPrinterJob();
      pageFormat = pj.pageDialog(pageFormat);
      if (pageRenderer != null) {
        pageRenderer.pageInit(pageFormat);
        showTitle();
      }
    }
  }

  public class FileQuitAction extends AbstractAction {
    public FileQuitAction() {
      super("Quit");
    }
    public void actionPerformed(ActionEvent ae) {
      System.exit(0);
    }
  }

  public class PageNextPageAction extends AbstractAction {
    public PageNextPageAction() {
      super("Next page");
    }

    public void actionPerformed(ActionEvent ae) {
      if (pageRenderer != null)
        pageRenderer.nextPage();
      showTitle();
    }
  }

  public class PagePreviousPageAction extends AbstractAction {
    public PagePreviousPageAction() {
      super("Previous page");
    }

    public void actionPerformed(ActionEvent ae) {
      if (pageRenderer != null)
        pageRenderer.previousPage();
      showTitle();
    }
  }
  class FilePageRenderer extends JComponent implements Printable {
    private int currentPageIndex;

    private Vector lineVector;

    private Vector pageVector;

    private Font font;

    private int fontSize;

    private Dimension preferredSize;

    public FilePageRenderer(File file, PageFormat pageFormat)
        throws IOException {
      fontSize = 12;
      font = new Font("Serif", Font.PLAIN, fontSize);
      BufferedReader in = new BufferedReader(new FileReader(file));
      String line;
      lineVector = new Vector();
      while ((line = in.readLine()) != null)
        lineVector.addElement(line);
      in.close();
      pageInit(pageFormat);
    }

    public void pageInit(PageFormat pageFormat) {
      currentPageIndex = 0;
      pageVector = new Vector();
      float y = fontSize;
      Vector page = new Vector();
      for (int i = 0; i < lineVector.size(); i++) {
        String line = (String) lineVector.elementAt(i);
        page.addElement(line);
        y += fontSize;
        if (y + fontSize * 2 > pageFormat.getImageableHeight()) {
          y = 0;
          pageVector.addElement(page);
          page = new Vector();
        }
      }

      if (page.size() > 0)
        pageVector.addElement(page);

      preferredSize = new Dimension((int) pageFormat.getImageableWidth(),
          (int) pageFormat.getImageableHeight());
      repaint();
    }

    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      java.awt.geom.Rectangle2D r = new java.awt.geom.Rectangle2D.Float(0, 0,
          preferredSize.width, preferredSize.height);
      g2.setPaint(Color.white);
      g2.fill(r);
      Vector page = (Vector) pageVector.elementAt(currentPageIndex);

      g2.setFont(font);
      g2.setPaint(Color.black);
      float x = 0;
      float y = fontSize;
      for (int i = 0; i < page.size(); i++) {
        String line = (String) page.elementAt(i);
        if (line.length() > 0)
          g2.drawString(line, (int) x, (int) y);
        y += fontSize;
      }
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
      if (pageIndex >= pageVector.size())
        return NO_SUCH_PAGE;
      int savedPage = currentPageIndex;
      currentPageIndex = pageIndex;
      Graphics2D g2 = (Graphics2D) g;
      g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
      paint(g2);
      currentPageIndex = savedPage;
      return PAGE_EXISTS;
    }

    public Dimension getPreferredSize() {
      return preferredSize;
    }

    public int getCurrentPage() {
      return currentPageIndex;
    }

    public int getNumPages() {
      return pageVector.size();
    }

    public void nextPage() {
      if (currentPageIndex < pageVector.size() - 1)
        currentPageIndex++;
      repaint();
    }

    public void previousPage() {
      if (currentPageIndex > 0)
        currentPageIndex--;
      repaint();
    }
  }  
}