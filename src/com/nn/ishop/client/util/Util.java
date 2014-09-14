package com.nn.ishop.client.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.nn.ishop.client.Main;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.components.ImageLabel;
import com.toedter.calendar.JDateChooser;


public abstract class Util {
	private static Properties systemProperties;

	private static Properties logProperties;


	public static String parseString(int num){
		String ret = "";
		try {
			ret = String.valueOf(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * no filter
	 */
	public static final short NO_FILTER = 0;

	/**
	 * locale filter
	 */
	public static final short LOCALE_FILTER = 1;

	static Logger logger = Logger.getLogger(Main.class);

	/**
	 * getTimestamp getTimestamp is a simple static method that returns a
	 * timestamp in a formatted manner.
	 *
	 * @return Timestamp An instance of type Timestamp
	 */
	public static Timestamp getTimestamp() {
		/**
		 * myTimezone hold the defined timezone for the log timestamps. Default
		 * is CET.
		 */
		TimeZone myTimezone;

		/** myLocale hold the defined Locale for the log timestamps. */
		Locale myLocale;

		/**
		 * TS_FORMAT is the format string for the timestamp See {@link
		 * http://www.mycore.de/library/jdk1.2.2/docs/api/java/text/SimpleDateFormat.html}
		 */
//		String TS_FORMAT = "dd.MM.yyyy kk:mm:ss z";

		myTimezone = TimeZone.getDefault();
		myLocale = Locale.getDefault();

		// String timestamp = "";
		Calendar rightNow = Calendar.getInstance(myTimezone, myLocale);

		/*
		 * SimpleDateFormat df = new SimpleDateFormat(TS_FORMAT); timestamp =
		 * df.format(rightNow.getTime());
		 */
		return new Timestamp(rightNow.getTimeInMillis());
	}

	/**
	 * filterTimestamp getTimestamp is a simple static method that returns a
	 * timestamp in a formatted manner.
	 *
	 * @param t
	 *            An instance of type Timestamp
	 * @param outputFormat
	 *            Format of output
	 *
	 * @return out String represents filter
	 */
	public static String filterTimestamp(Timestamp t, short outputFormat) {
		String out = null;

		switch (outputFormat) {
		case LOCALE_FILTER:
			out = ((t != null) ? DateFormat.getDateTimeInstance(
					DateFormat.SHORT, DateFormat.SHORT).format(t) : "");

			break;

		default:
			out = ((t != null) ? t.toString() : "");
		}

		return out;
	}

	/**
	 * Tries to parse the passed string to get a Date Object
	 *
	 * @param dateStr
	 *            A String that represents date
	 *
	 * @return d An instance of type Date
	 */
	public static Date parseDate(String dateStr) {
		SimpleDateFormat df = null;

		if (dateStr.indexOf(".") != -1) {
			df = new SimpleDateFormat("dd.MM.yy");
		}

		if (dateStr.indexOf("-") != -1) {
			df = new SimpleDateFormat("yy-MM-dd");
		} else {
			df = new SimpleDateFormat();
		}

		Date d = null;

		try {
			d = df.parse(dateStr);
		} catch (Exception ex) {
//			ConnectApplicationExceptionHandler
//					.processException(ConnectApplicationExceptionUtil
//							.createException(
//									ex,
//									"Date ["
//											+ dateStr
//											+ "] is null or invalid. Date set to null.",
//									ConnectApplicationException.INFO));
		}

		return d;
	}

	/**
	 * Tries to parse the passed string to get an SQL Date Object
	 *
	 * @param dateStr
	 *            An instance of type java.sql.Date
	 *
	 * @return java.sql.Timestamp
	 */
	public static java.sql.Date parseSQLDate(String dateStr) {
		Date date = parseDate(dateStr);

		return ((date != null) ? new java.sql.Date(date.getTime()) : null);
	}

	/**
	 * Tries to parse the passed string to get an SQL Timestamp Object
	 *
	 * @param dateStr
	 *            A String that represents date
	 *
	 * @return java.sql.Timestamp
	 */
	public static Timestamp parseSQLTimestamp(String dateStr) {
		Date date = parseDate(dateStr);

		return ((date != null) ? new Timestamp(date.getTime()) : null);
	}


	/**
	 * returns the properties from properties file
	 *
	 * @param filePath
	 *            Path of file
	 *
	 * @return pps An instance of type Properties
	 */
	public static Properties getProperties(String filePath) {
		Properties pps = new Properties();
		InputStream propIn;

		try {
			propIn = new FileInputStream(new File(filePath));
			pps.load(propIn);
			propIn.close();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		} finally {
			propIn = null;
		}

		return pps;
	}

	/**
	 * Sets the properties in file connect.cfg
	 *
	 * @param pps
	 *            the new properties, taht sobstitutes the old ones
	 * @param filePath
	 *            where the properties have to be written
	 * @param title
	 *            title
	 */
	public static void setProperties(Properties pps, String filePath,
			String title) {
		OutputStream propOut = null;

		try {
			propOut = new FileOutputStream(new File(filePath));
			pps.store(propOut, title);
			propOut.close();
		} catch (Exception ex) {
		} finally {
			propOut = null;
		}
	}

	/**
	 * Returns the system properties in file application_en.properties
	 *
	 * @return systemProperties An instance of type Properties
	 */
	public static Properties getSystemProperties() {
		if (systemProperties == null) {
			systemProperties = getProperties("cfg/application_en.properties");
		}

		return systemProperties;
	}

	/**
	 * Sets the system properties in file application_en.properties
	 *
	 * @param systemProperties
	 *            New list of system properties
	 */
	public static void setSystemProperties(Properties systemProperties) {
		if (systemProperties == null) {
			getSystemProperties();
		}

		setProperties(systemProperties, "cfg/application_en.properties",
				"iShop Application Properties");
		// update
		Util.systemProperties =  systemProperties;
	}

	/**
	 * Returns the log properties in file log4j.properties
	 *
	 * @return logProperties An instance of type Properties
	 */
	public static Properties getLogProperties() {
		if (logProperties == null) {
			logProperties = getProperties("cfg/log4j.properties");
		}

		return logProperties;
	}

	/**
	 * Sets the log properties in file log4j.properties
	 *
	 * @param logProperties
	 *            An instance of type Properties
	 */
	public static void setLogProperties(Properties logProperties) {
		if (logProperties == null) {
			getLogProperties();
		}

		setProperties(logProperties, "cfg/log4j.properties",
				"iShop Log Properties");
	}

	/**
	 * gets a DOM document out of <code>fileName</code>.xml file in the
	 * default directory
	 *
	 * @param path
	 *            Directory of the file
	 * @param fileName
	 *            Name of xml file without .xml extension
	 *
	 * @return document An instance of type Document
	 */
	public static Document getXMLDocument(String path, String fileName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(path + fileName);
		} catch (Exception ex) {
			// Error generated during parsing
//			ConnectApplicationExceptionHandler
//					.processException(ConnectApplicationExceptionUtil
//							.createException(ex, "Couldn't load xml document "
//									+ fileName,
//									ConnectApplicationException.ERROR));
		}

		return document;
	}

//	/**
//	 * get the taxonomy
//	 *
//	 * @param fileName
//	 *            Name of xml document containing the taxonomy
//	 * @param taxName
//	 *            Ccorresponds to the element's
//	 *
//	 * @return taxonomy An instance of type Taxonomy
//	 */
//	public static Taxonomy getTaxonomy(String fileName, String taxName) {
//		// init taxonomies hashtable if needed
//		if (taxonomies == null) {
//			taxonomies = new Hashtable();
//		}
//
//		Taxonomy taxonomy;
//
//		// if file was already loaded return the taxonomy
//		if (taxonomies.containsKey(taxName)
//				&& (taxonomies.get(taxName) != null)) {
//			return (Taxonomy) taxonomies.get(taxName);
//		}
//		// else the taxonomy has to be loaded
//		else {
//			taxonomy = new Taxonomy();
//			taxonomies.put(taxName, taxonomy);
//
//			// get the XMLDocument
//			Document document = getXMLDocument("cfg/", fileName);
//
//			if (document == null) {
//				return taxonomy;
//			}
//
//			// get document root and taxonomy element
//			Element rootElt = document.getDocumentElement();
//			NodeList taxElts = rootElt.getElementsByTagName("taxonomy");
//			Element taxonomyElt = null;
//
//			for (int i = 0; i < taxElts.getLength(); i++)
//				if (((Element) taxElts.item(i)).getAttribute("type").equals(
//						taxName)) {
//					taxonomyElt = (Element) taxElts.item(i);
//
//					break;
//				}
//
//			// return null if no tax was found
//			if (taxonomyElt == null) {
//				logger.warn("requested taxonomy '" + taxName
//						+ "' is not available");
//
//				return taxonomy;
//			} else {
//				String s;
//
//				/**
//				 *
//				 * @todo: optimize using XPath
//				 */
//
//				// get rules
//				NodeList rulesElts = taxonomyElt.getElementsByTagName("rules");
//
//				if (rulesElts.getLength() > 0) {
//					// build basis structure
//					TaxonomyRule rule;
//					TaxonomyRuleDirective directive;
//					NodeList ruleElts = ((Element) rulesElts.item(0))
//							.getElementsByTagName("rule");
//					Element ruleElt;
//					Element directiveElt;
//					NodeList directiveElts;
//					String dirContext;
//
//					// add the single rules
//					for (int i = 0; i < ruleElts.getLength(); i++) {
//						rule = new TaxonomyRule();
//						ruleElt = (Element) ruleElts.item(i);
//						// get the appropriate type container
//						s = "type";
//						rule.setProperty(s, ruleElt.hasAttribute(s) ? ruleElt
//								.getAttribute(s) : "default");
//						// get the appropriate target container
//						s = "context";
//						rule.setProperty(s, ruleElt.hasAttribute(s) ? ruleElt
//								.getAttribute(s) : "entry");
//						// get the rule (set of directives) for which the rule
//						// applies to
//						s = "applyTo";
//						rule.setProperty(s, ruleElt.hasAttribute(s) ? ruleElt
//								.getAttribute(s) : "default");
//						// get the directives
//						directiveElts = ruleElt.getChildNodes();
//
//						for (int j = 0; j < directiveElts.getLength(); j++) {
//							if (directiveElts.item(j).getNodeType() == Node.ELEMENT_NODE) {
//								// create the directive
//								directiveElt = (Element) directiveElts.item(j);
//								dirContext = directiveElt
//										.hasAttribute("category") ? "category"
//										: "entry";
//								s = directiveElt.getAttribute(dirContext);
//								directive = new TaxonomyRuleDirective(
//										dirContext, s);
//								rule.addDirective(directiveElt.getNodeName(),
//										directive);
//							}
//						}
//
//						// add the directive to the rule set
//						taxonomy.addRule(rule);
//					}
//				}
//
//				// get entries
//				TaxonomyEntry entry;
//				NodeList entryElts = ((Element) taxonomyElt
//						.getElementsByTagName("entries").item(0))
//						.getElementsByTagName("entry");
//				Element elt = null;
//				NodeList inferElts = null;
//
//				for (int i = 0; i < entryElts.getLength(); i++) {
//					elt = (Element) entryElts.item(i);
//
//					if (elt.hasAttribute("taxId")) {
//						// entry struct
//						// String 'category'
//						// String 'def'
//						// Hashtable 'intlDefs':
//						// String(s) [for]
//						Node textNode = elt.getElementsByTagName("def").item(0)
//								.getFirstChild();
//						entry = new TaxonomyEntry(elt.getAttribute("taxId"),
//								((textNode == null) ? "" : textNode
//										.getNodeValue()));
//						entry.setProperty("category", elt
//								.getAttribute("category"));
//						inferElts = elt.getElementsByTagName("infer");
//
//						if (inferElts.getLength() > 0) {
//							for (int j = 0; j < inferElts.getLength(); j++)
//								entry.setInferrableValue(((Element) inferElts
//										.item(j)).getAttribute("for"),
//										inferElts.item(j).getFirstChild()
//												.getNodeValue());
//						}
//
//						taxonomy.addEntry(entry);
//					}
//
//					if (entryElts.getLength() == 0) {
//						logger.warn("taxonomy '" + taxName + "' is empty");
//					}
//				}
//			} // if there was a taxonomy
//		}
//
//		return taxonomy;
//	}

//	/**
//	 * return label
//	 *
//	 * @param label
//	 *            Label of taxonomy
//	 *
//	 * @return labels An instance of type Taxonomy
//	 */
//	public static String getLabel(String label) {
//		if (labels == null) {
//			labels = Util.getTaxonomy("labels.xml", "labels");
//		}
//
//		return ((labels.getEntry(label) != null) ? labels.getEntry(label)
//				.toString() : label);
//	}
//
//	/**
//	 * return array of labels
//	 *
//	 * @param label
//	 *            Label of taxonomy
//	 *
//	 * @return labels An array of type String
//	 */
//	public static String[] getMultiLineLabel(String label) {
//		return getLabel(label).split("\\\\n"); // escape twice: java string +
//												// regexp
//	}

	/**
	 * return icon
	 *
	 * @param fileName
	 *            name of file
	 *
	 * @return ImageIcon An instance of type ImageIcon
	 */
	public static ImageIcon getIcon(String fileName) {
		return new ImageIcon(Main.class.getResource(Library.IMAGES_PATH
				+ fileName));
	}

	/**
	 * return image
	 *
	 * @param fileName
	 *            name of file
	 *
	 * @return Image An instance of type Image
	 */
	public static Image getImage(String fileName) {
		return Toolkit.getDefaultToolkit().createImage(
				Main.class.getResource(Library.IMAGES_PATH + fileName));
	}

	/**
	 * Make first letter of every word in str uppercase
	 *
	 * @param str
	 *            String
	 *
	 * @return capString String that is capitalized
	 */
	public static String capitalizeString(String str) {
		String capString = "";
		char ch;
		char prevCh;
		prevCh = '.';

		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);

			if (Character.isLetter(ch) && !Character.isLetter(prevCh)) {
				capString += Character.toUpperCase(ch);
			} else {
				capString += ch;
			}

			prevCh = ch;
		}

		return capString;
	}

	/**
	 * truncate a string
	 *
	 * @param str
	 *            String that needed truncate
	 * @param length
	 *            Length of string
	 *
	 * @return str String that truncated
	 */
	public static String truncate(String str, int length) {
		str = ((str.length() < (length + 2)) ? str
				: (str.substring(0, length) + "..."));

		return str;
	}

	/**
	 * truncate a string at specific width
	 *
	 * @param c
	 *            An instance of type Component
	 * @param str
	 *            String that needed truncate
	 * @param width
	 *            Width of string
	 *
	 * @return str String that truncated
	 */
	public static String truncateAtWidth(Component c, String str, int width) {
		FontMetrics fm = c.getFontMetrics(c.getFont());

		if (fm == null)
			return null;

		int fullWidth = 0;
		try {
			fullWidth = fm.stringWidth(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if ((width > 0) && (fullWidth > width)) {
			String susp = "...";
			int guessedWidth = (str.length() * width) / fullWidth;
			String cutStr = str.substring(0, guessedWidth) + susp;

			while ((guessedWidth > 0) && (fm.stringWidth(cutStr) > width)) {
				guessedWidth--;
				cutStr = str.substring(0, guessedWidth) + susp;
			}

			str = cutStr;
		}

		return str;
	}

	/*
	 * public static HTMLDocument getStyledHTMLDocument(String style){
	 * StyleSheet styleSheet = new StyleSheet(); try {
	 * styleSheet.importStyleSheet(new URL(Library.CSS_PATH + style + ".css")); }
	 * catch(Exception e) { logger.error("No stylesheet found for style " +
	 * style); } HTMLDocument doc = new HTMLDocument(styleSheet);
	 * doc.insertString(0,""); return doc; }
	 */
	/**
	 * convert a byte[] array to readable string format.
	 *
	 * @param in
	 *            byte[] buffer to convert to string format
	 *
	 * @return result String buffer in String format
	 */
	public static String toHexString(byte[] in) {
		byte ch = 0x00;
		int i = 0;

		if ((in == null) || (in.length <= 0)) {
			return null;
		}

		String[] pseudo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F" };
		StringBuffer out = new StringBuffer(in.length * 2);

		while (i < in.length) {
			ch = (byte) (in[i] & 0xF0); // Strip off high nibble
			ch = (byte) (ch >>> 4); // shift the bits down
			ch = (byte) (ch & 0x0F); // must do this is high order bit is on!
			out.append(pseudo[(int) ch]); // convert the nibble to a String
											// Character
			ch = (byte) (in[i] & 0x0F); // Strip off low nibble
			out.append(pseudo[(int) ch]); // convert the nibble to a String
											// Character
			i++;
		}

		String rslt = new String(out);

		return rslt;
	}

//	/**
//	 * add NetworkDataTable to Component
//	 *
//	 * @param jc
//	 *            An instance of type Component
//	 * @param title
//	 *            Title of table
//	 * @param data
//	 *            Data of table
//	 * @param columnTitles
//	 *            An array ot column title
//	 *
//	 * @return contentPanel An instance of type NetworkDataTable
//	 */
//	public static NetworkDataTable addNetworkDataTableToComponent(
//			final JComponent jc, String title, String[][] data,
//			String[] columnTitles) {
//		// remove previous contents
//		jc.setVisible(false);
//		jc.removeAll();
//
//		NetworkDataTable contentPanel = new NetworkDataTable(title, data,
//				columnTitles);
//
//		// content has to be wrapped in ScrollPane
//		jc.setLayout(new BorderLayout());
//
//		final JScrollPane scrollPane = new JScrollPane(contentPanel);
//		scrollPane.setBorder(null);
//
//		jc.add(scrollPane, BorderLayout.CENTER);
//
//		/**
//		 *
//		 * @todo scrollPane should be always on top..
//		 */
//
//		// and that's a not elegant workaround for a swing bug..
//		Runnable doScrollAtBegin = new Runnable() {
//			public void run() {
//				scrollPane.getVerticalScrollBar().setValue(0);
//				jc.setVisible(true);
//			}
//		};
//
//		SwingUtilities.invokeLater(doScrollAtBegin);
//
//		return contentPanel;
//	}

	/**
	 * addListener to ComponentWidgets
	 *
	 * @param c
	 *            An instance of type Component
	 * @param l
	 *            An instance of type EventListener
	 */
	public static void addListenerToComponentWidgets(Component c,
			EventListener l) {
		// add focus listener
		if (l instanceof ActionListener) {
			final ActionListener al = (ActionListener) l;

			if (c instanceof JTextComponent) {
				JTextComponent jtc = (JTextComponent) c;
				jtc.addCaretListener(new CaretListener() {
					public void caretUpdate(CaretEvent e) {
						al.actionPerformed(new ActionEvent(this, 0, "caret"));
					}
				});
			} else if (c instanceof JSlider) {
				JSlider js = (JSlider) c;
				js.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						al.actionPerformed(new ActionEvent(this, 0, "slider"));
					}
				});
			}
			else if (c instanceof JComboBox)
			{
				JComboBox jcb = (JComboBox) c;
				jcb.addActionListener(al);
			}
			else if (c instanceof AbstractButton) {
				AbstractButton ab = (AbstractButton) c;
				ab.addActionListener(al);
			}
		}

		if (c instanceof Container) {
			Component[] children = ((Container) c).getComponents();

			for (int i = 0; i < children.length; i++) {
				addListenerToComponentWidgets(children[i], l);
			}
		}
	}

	/**
	 * set font for array of components
	 *
	 * @param jcs
	 *            An array of type Component
	 * @param family
	 *            name of fond family
	 * @param style
	 *            fond style
	 * @param size
	 *            fond size
	 */
	public static void setFontToComponents(Component[] jcs, String family,
			int style, int size) {
		for (int i = 0; i < jcs.length; i++)
			setFontToComponent(jcs[i], family, style, size);
	}

	/**
	 * set font recursvely in component tree
	 *
	 * @param jc
	 *            An array of type Component
	 * @param family
	 *            name of fond family
	 * @param style
	 *            fond style
	 * @param size
	 *            fond size
	 * @param deep
	 *            deep
	 */
	public static void setFontToComponent(Component jc, String family,
			int style, int size, boolean deep) {
		setFontToComponent(jc, family, style, size);

		if (deep) {
			Component[] children = ((Container) jc).getComponents();

			for (int i = 0; i < children.length; i++)
				if (children[i] instanceof Component) {
					setFontToComponent(children[i], family, style, size, true);
				}
		}
	}

	/**
	 * set font starting from the old one set
	 *
	 * @param jc
	 *            An array of type Component
	 * @param family
	 *            name of fond family
	 * @param style
	 *            fond style
	 * @param size
	 *            fond size
	 */
	public static void setFontToComponent(Component jc, String family,
			int style, int size) {
		Font oldFont;
		Font newFont;

		if (jc != null) {
			oldFont = jc.getFont();
			newFont = new Font((family == null) ? oldFont.getName() : family,
					(style < 0) ? oldFont.getStyle() : style,
					(size < 0) ? oldFont.getSize() : size);
			jc.setFont(newFont);
		}
	}

	/**
	 * check whether a component has parent class
	 *
	 * @param c
	 *            An instance of type Component
	 * @param cl
	 *            Parent class of specified component
	 *
	 * @return boolean A boolean value indicates whether component has parent
	 *         class
	 */
	public static boolean hasParentClass(Component c, Class<?> cl) {
		Component parent = c.getParent();

		if (cl.isInstance(parent)) {
			return true;
		} else if (parent != null) {
			return hasParentClass(parent, cl);
		}

		return false;
	}

	/**
	 * benchmark
	 *
	 * @param d
	 *            An instance of type java.util.Date
	 * @param msg
	 *            message
	 *
	 * @return d An instance of type java.util.Date
	 */
	public static Date benchmark(Date d, String msg) {
		if (d == null) {
			d = new Date(getTimestamp().getTime());

			String s = "Starting benchmark at time: "
					+ DateFormat.getTimeInstance().format(d) + " (" + msg + ")";
			logger.debug(s);
		} else {
			Timestamp newT = getTimestamp();
			String s = msg + ": " + DateFormat.getTimeInstance().format(d)
					+ " (diff: " + (newT.getTime() - d.getTime()) + ")";
			logger.debug(s);
			d.setTime(newT.getTime());
		}

		return d;
	}

	/**
	 * setLookAndFeel
	 *
	 * @param c An instance of type Container
	 */
	public static void setLookAndFeel(Container c) {
		// set system look and feel
		try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}


	/**
	 *  Extended method for pop up menu
	 * @param c a component
	 */
	public static void updateCommonPopupMenu(Component c)
	{
		if (c instanceof JTextField || c instanceof JTextArea
				|| c instanceof JTextComponent
				|| c instanceof JDateChooser){
			final JComponent jc2 = (JComponent)c;
			c.addMouseListener(new MouseAdapter(){
		    	public void mousePressed(MouseEvent e) {
		    		if(e.isPopupTrigger())
		    		buildPopupMenu4EditForm(jc2, e);
		    	}
		    	public void mouseReleased(MouseEvent e) {
		    		if(e.isPopupTrigger())
			    		buildPopupMenu4EditForm(jc2, e);
		    	}
	    	});
		}
	}
	/**
	 * store and retrieve serialized objects
	 *
	 * @param id Id of object
	 * @param obj An instance of type Serializable
	 */
	public static void setObjectToCache(String id, Serializable obj)
	{
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		File cacheDir = null;

		try
		{
			id = id.replace(':', '_');

			cacheDir = new File("cache/");

			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}

			// get encryption key
			SecretKey key = CryptoToolkit.loadSecretKeyFromKeystore(
					"CACHE_KEY", "admin");

			if (key == null) {
				key = CryptoToolkit.generateKeyAES();
				CryptoToolkit
						.saveSecretKeyToKeystore("CACHE_KEY", key, "admin");
			}

			Cipher encCipher = CryptoToolkit.getEncryptionCipher(key);
			SealedObject so = CryptoToolkit.encryptObject(obj, encCipher);
			File f = new File("cache/" + id + ".dat");
			fout = new FileOutputStream(f);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(so);
			oos.close();
		}
		catch (Exception e)
		{
//			ConnectApplicationExceptionHandler
//					.processException(ConnectApplicationExceptionUtil
//							.createException(e, "Error trying to write " + id
//									+ " object to cache.",
//							ConnectApplicationException.ERROR));
		}
	}

	/**
	 * get object from cache
	 *
	 * @param id Id of object
	 * @return obj An instance of type Serializable
	 */
	public static Object getCachedObject(String id)
	{
		Serializable obj = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		File cacheDir = null;
		File cacheFile = null;

		try
		{
			id = id.replace(':', '_');

			cacheDir = new File("cache/");
			cacheFile = new File("cache/" + id + ".dat");

			if (cacheDir.exists() && cacheFile.exists())
			{
				fin = new FileInputStream("cache/" + id
						+ ".dat");
				ois = new ObjectInputStream(fin);

				// decrypt
				SecretKey key = CryptoToolkit.loadSecretKeyFromKeystore(
						"CACHE_KEY", "admin");

				if (key != null) {
					Cipher decCipher = CryptoToolkit
							.getDecryptionCipher(CryptoToolkit
									.loadSecretKeyFromKeystore("CACHE_KEY",
											"admin"));
					SealedObject so = (SealedObject) ois.readObject();
					obj = CryptoToolkit.decryptObject(so, decCipher);
					ois.close();
				} else {
					obj = (Serializable) ois.readObject();
					ois.close();
				}
			}
		}
		catch (Exception e)
		{
//			ConnectApplicationExceptionHandler
//					.processException(ConnectApplicationExceptionUtil
//							.createException(e, "Error trying to get " + id
//									+ " object from cache.",
//									ConnectApplicationException.ERROR));
		}

		return obj;
	}

	/**
	 * join and toString an array
	 *
	 * @param strs An array of type String
	 * @return String A string that contains all of elements of input array
	 */
	public static String joinToString(String[] strs) {
		return joinToString(strs, ", ");
	}

	/**
	 * concat all elements of String array into new string, separate by a comma
	 *
	 * @param strs An array of type Object
	 * @param separator A separator is used to separate between array elements
	 * @return String A string that contains all of elements of input array
	 */
	public static String joinToString(Object[] strs, String separator) {
		StringBuffer sb = new StringBuffer();

		for (int x = 0; x < (strs.length - 1); x++) {
			sb.append(strs[x].toString());
			sb.append(separator);
		}

		sb.append(strs[strs.length - 1]);

		return sb.toString();
	}

	public static int extractIntFromString(String s){
		char c[] = s.trim().toCharArray();
		s = "";
		for(int i = 0; i < c.length && Character.isDigit(c[i]); i++)s += c[i];
		if(s.equals(""))s = "0";
		return Integer.parseInt(s);

	}
	private static void buildPopupMenu4EditForm(Component invoker
			, MouseEvent e){
		 JPopupMenu popup = new JPopupMenu();

	     // Cut
		 JMenuItem cutBtn = new JMenuItem(new DefaultEditorKit.CutAction());
		 cutBtn.setText("Cut");
	     cutBtn.setBackground(Color.WHITE);
	     cutBtn.setBorderPainted(false);

	     popup.add(cutBtn);
	     JSeparator s1 = new JSeparator();
         s1.setBackground(Color.WHITE);
         popup.add(s1);

		 // Copy
	     JMenuItem copyBtn = new JMenuItem(new DefaultEditorKit.CopyAction());
	     copyBtn.setText("Copy");
	     copyBtn.setBackground(Color.WHITE);
	     copyBtn.setBorderPainted(false);

	     popup.add(copyBtn);
	     JSeparator s2 = new JSeparator();
         s2.setBackground(Color.WHITE);
         popup.add(s2);
		 // Paste
	     JMenuItem pasteBtn = new JMenuItem(new DefaultEditorKit.PasteAction());
	     pasteBtn.setText("Paste");
	     pasteBtn.setBackground(Color.WHITE);
	     pasteBtn.setBorderPainted(false);

	     popup.add(pasteBtn);

	     popup.show(invoker, e.getX(), e.getY());
	}
	  /**
    *
    * @param numString
    * @return
    */
   public static String translateNumsToWords(String numString){
   	String ret = "";
   	String newString ="";


   	for(int i=0;i<numString.length();i++){
   		if((int)numString.charAt(i) != 160){
   			newString += String.valueOf(numString.charAt(i));
   		}
   	}
   	numString = newString;
   	numString = numString.replaceAll(" ", "");
   	numString = numString.replaceAll(",", "");
   	Hashtable<String, String> chuSo = new Hashtable<String,String>();
   	chuSo.put("0", Language.getInstance().getString("zero"));
   	chuSo.put("1", Language.getInstance().getString("one"));
   	chuSo.put("2", Language.getInstance().getString("two"));
   	chuSo.put("3", Language.getInstance().getString("three"));
   	chuSo.put("4", Language.getInstance().getString("four"));
   	chuSo.put("5", Language.getInstance().getString("five"));
   	chuSo.put("6", Language.getInstance().getString("six"));
   	chuSo.put("7", Language.getInstance().getString("seven"));
   	chuSo.put("8", Language.getInstance().getString("eight"));
   	chuSo.put("9", Language.getInstance().getString("nine"));

   	Hashtable<String, String> tuNoi = new Hashtable<String,String>();
   	tuNoi.put("lam", Language.getInstance().getString("lam"));
   	tuNoi.put("le", Language.getInstance().getString("le"));
   	tuNoi.put("muoi", Language.getInstance().getString("muoi"));

   	Hashtable<String, String> tenNhom = new Hashtable<String,String>();
   	tenNhom.put("ty", Language.getInstance().getString("ty"));
   	tenNhom.put("trieu", Language.getInstance().getString("trieu"));
   	tenNhom.put("ngan", Language.getInstance().getString("ngan"));
   	tenNhom.put("tram", Language.getInstance().getString("tram"));
   	tenNhom.put("muoif", Language.getInstance().getString("muoif"));

   	if(numString ==null || numString.equals("")
   			||numString.length() > 12)return ":-(";
   	numString = numString.replaceAll(" ", "");
//   	numString = numString.replaceAll(",", "");
//   	numString = numString.replaceAll(".", "");
   	int b = Math.round(numString.length() /3);
		int mod = numString.length() %3;
		Vector<String> vctAll = new Vector<String>();
		char[] chars1 = new char[mod];
		for(int i=0;i<mod;i++)
		{
			chars1[i]=numString.charAt(i);
		}
		if(mod >0)
		vctAll.add(new String(chars1));

		for(int i=mod;i<numString.length();i+=3)
		{
			chars1 = new char[3];
			chars1[0] = numString.charAt(i);
			chars1[1] = numString.charAt(i+1);
			chars1[2] = numString.charAt(i+2);
			vctAll.add(new String(chars1));
		}
		int sz = vctAll.size();
		String groupName ="";
		switch(sz)
		{
		case 1:
			String so = vctAll.get(0);
			ret = doi3SoHangTram(so,chuSo,tuNoi,tenNhom);
			break;
		case 2:
			ret = doi3So(vctAll.get(0), groupName, chuSo, tuNoi, tenNhom)+
			tenNhom.get("ngan")+
				  doi3SoHangTram(vctAll.get(1), chuSo, tuNoi, tenNhom);
			break;
		case 3:
			ret = doi3So(vctAll.get(0), groupName, chuSo, tuNoi, tenNhom)+
			 		tenNhom.get("trieu")+
			      doi3So(vctAll.get(1), groupName, chuSo, tuNoi, tenNhom)+
			      tenNhom.get("ngan")+
			      doi3SoHangTram(vctAll.get(2), chuSo, tuNoi, tenNhom);
			;
			break;
		case 4:
			ret = doi3So(vctAll.get(0), groupName, chuSo, tuNoi, tenNhom)+
				  tenNhom.get("ty") +
			      doi3So(vctAll.get(1), groupName, chuSo, tuNoi, tenNhom)+
			      tenNhom.get("trieu")+
			      doi3So(vctAll.get(2), groupName, chuSo, tuNoi, tenNhom)+
			      tenNhom.get("ngan")+
			      doi3SoHangTram(vctAll.get(3), chuSo, tuNoi, tenNhom);
			break;
		}

   	//--parse string to elements of 03 chars
   	//-- translate from last 03 chars to the beginning of the chars (reverse order)
		ret = ret + Language.getInstance().getString("vietnam.dong");
		ret = ret.trim();
		ret = Character.toUpperCase(ret.charAt(0))+ret.substring(1);
   	return ret;
   }
   private static String doi3SoHangTram(String source,
   		Hashtable<String, String> chuSo,
   		Hashtable<String, String> tuNoi,
   		Hashtable<String, String> tenNhom
   		)
	{
   	String ret = " ";
   	int len = source.length();
   	if(len==1) ret = chuSo.get(source);
   	if(len==2){
   		String chuCuoi =
   			(!source.substring(1, 2).equals("0"))?
       				chuSo.get(source.substring(1, 2)):"";
       	String
			chu2 = (source.substring(0, 1).equals("0"))?""
					:(!source.substring(0, 1).equals("1"))?
		    				chuSo.get(source.substring(0, 1))+ tuNoi.get("muoi")
		    				:tenNhom.get("muoif");
   		ret = chu2
   		+ chuCuoi;
   		if(source.substring(1, 2).equals("0")
   				&& source.substring(0, 1).equals("0"))
   		{
   			ret = "";
   		}
   	}
   	if(len==3){
   		String chuCuoi =
   			(!source.substring(2, 3).equals("0"))?
       				chuSo.get(source.substring(2, 3)):"";
			String
			chu2 = (source.substring(1, 2).equals("0"))?""
					:(!source.substring(1, 2).equals("1"))?
		    				chuSo.get(source.substring(1, 2))+ tuNoi.get("muoi")
		    				:tenNhom.get("muoif");
   		ret = chuSo.get(source.substring(0, 1))
   		+ tenNhom.get("tram")
   		+chu2
   		+ chuCuoi
   		;
   		if(source.substring(1, 2).equals("0")
   				&& source.substring(0, 1).equals("0")
   				&& source.substring(2, 3).equals("0"))
   		{
   			ret = "";
   		}
   	}

   	return ret;

	}
   private static String doi3So(String source
   		, String groupName,
   		Hashtable<String, String> chuSo,
   		Hashtable<String, String> tuNoi,
   		Hashtable<String, String> tenNhom
   		)
	{

   	String ret = groupName;
   	int len = source.length();
   	if(len==1){
   		ret = chuSo.get(source);

   	}
   	if(len==2){
   		String chuCuoi =
   			(!source.substring(1, 2).equals("0"))?
       				chuSo.get(source.substring(1,2)):"";
			String
			chu2 = (source.substring(0, 1).equals("0"))?""
					:(!source.substring(0, 1).equals("1"))?
		    				chuSo.get(source.substring(0, 1))+ tuNoi.get("muoi")
		    				:tenNhom.get("muoif");
       	ret = chu2
   		+ chuCuoi
   		+ ret
   		;
       	if(source.substring(1, 2).equals("0")
   				&& source.substring(0, 1).equals("0"))
   		{
   			ret = "";
   		}
   	}
   	if(len==3){
   		String chuCuoi =
   			(!source.substring(2, 3).equals("0"))?
       				chuSo.get(source.substring(2, 3)):"";
			String
			chu2 = (source.substring(1, 2).equals("0"))?""
					:(!source.substring(1, 2).equals("1"))?
		    				chuSo.get(source.substring(1, 2))+ tuNoi.get("muoi")
		    				:tenNhom.get("muoif");
       	ret = chuSo.get(source.substring(0, 1))
   		+ tenNhom.get("tram")
   		+ chu2
   		+ chuCuoi
   		+ ret
   		;
       	if(source.substring(1, 2).equals("0")
   				&& source.substring(0, 1).equals("0")
   				&& source.substring(2, 3).equals("0"))
   		{
   			ret = "";
   		}
   	}

   	return ret;

	}
  	
   private static ImageIcon convertImageFromByteArray(byte[] imgBytes, String fileName) throws Exception{	
	    if(imgBytes == null || imgBytes.length ==0){
	    	return null;
	    }
		InputStream in = new ByteArrayInputStream(imgBytes);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		File imgFile = new File(fileName);
		imgFile.deleteOnExit();
		ImageIO.write(bImageFromConvert, "png", new File(fileName));				
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(fileName));
	}
   public static void setImageLabelIcon(ImageLabel imgLabel, byte[] imgBytes) throws Exception{
	   byte[] metaInfoBytes = new byte[1024];
	   byte[] imgRealBytes = new byte[imgBytes.length - 1024];
	   
	   for(int i=0; i<imgBytes.length;i++){
		   if(i<1024){
			   metaInfoBytes[i] = imgBytes[i];
		   }else{
			   imgRealBytes[i-1024] =  imgBytes[i];
		   }
	   }
	   	   
	   String fileName = new String(metaInfoBytes);
	 //-- remove null byte in 1024 byte meta data
	   String oriFileName = fileName.replaceAll("\0", "");
	   imgLabel.setIconPath(oriFileName);
	   
	   imgLabel.setIcon(convertImageFromByteArray(imgRealBytes, oriFileName));
   }
   
   /**
	 * Parse image to byte array with meta data information of first 1024 bytes
	 * This method should be available on client side
	 * @param imagePath
	 * @return
	 * @throws Exception
	 */
	public static  byte[] getImageAsByteArray(String imagePath)throws Exception{
		File file = new File(imagePath);
		
		//-- Build meta data information for the image (use for next useage)
		String imgMetaData  = "tmp/" + file.getName();
		byte[] imgMetaDataBytes = new byte[1024];
		byte[] realMetaDataBytes = imgMetaData.getBytes();
		
		if(realMetaDataBytes.length > 1024)
			throw new Exception("File name is too long.");
		
		for(int i=0;i<realMetaDataBytes.length;i++){
			imgMetaDataBytes[i] = realMetaDataBytes[i];
		}		
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
       byte[] buf = new byte[1024];
       try {
       	FileInputStream fis = new FileInputStream(file);
           for (int readNum; (readNum = fis.read(buf)) != -1;) {
               bos.write(buf, 0, readNum);
           }
       } catch (IOException ex) {
           throw ex;// ex.printStackTrace();
       }
       byte[] bytes = bos.toByteArray();
       
       
       byte[] finalByte = new byte[bytes.length + imgMetaDataBytes.length];
       for(int i=0;i<finalByte.length;i++){
       	if(i<1024){
       		finalByte[i] = imgMetaDataBytes[i];
       	}else{
       		finalByte[i] = bytes[i-1024];
       	}
       }      
       return finalByte;
	}
	
	public static String generateSaleOrder(){
		String ret = null;
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		ret = "SO_" + sdf.format(d) + "_"+String.valueOf(System.currentTimeMillis()); 
		return ret;
	}
	public static String generateSaleIssueSlipSerial(){
		String ret = null;
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		ret = "ISL_" + sdf.format(d) + "_"+String.valueOf(System.currentTimeMillis()); 
		return ret;
	}
	
	public static String generateSerial(String prefix){
		String ret = null;
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		ret = prefix + sdf.format(d) + "_"+String.valueOf(System.currentTimeMillis()); 
		return ret;
	}
	
	public static Date getDateOfMonth(Date d, boolean beginDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int maxDateInt = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int minDateInt = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		if(beginDate)
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), minDateInt);
		else
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), maxDateInt);
		return cal.getTime();
		
		
	}
	public static Date getDateOfYear(Date d, boolean beginDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);		
		int yearNum = cal.get(Calendar.YEAR);
		
		if(beginDate)
			cal.set(yearNum, 1, 1);
		else
			cal.set(yearNum, 12, 31);
		return cal.getTime();
		
		
	}
	public static Calendar getCalendarOfMonth(Date d, boolean beginDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int maxDateInt = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int minDateInt = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		if(beginDate)
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), minDateInt);
		else
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), maxDateInt);
		return cal;
	}
	public static Calendar getCalendarOfYear(Date d, boolean beginDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);		
		int yearNum = cal.get(Calendar.YEAR);
		
		if(beginDate)
			cal.set(yearNum, 1, 1);
		else
			cal.set(yearNum, 12, 31);
		return cal;
		
		
	}
}
