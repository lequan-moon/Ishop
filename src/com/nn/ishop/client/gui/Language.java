package com.nn.ishop.client.gui;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;

import net.n3.nanoxml.NonValidator;
import net.n3.nanoxml.StdXMLBuilder;
import net.n3.nanoxml.StdXMLParser;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLElement;

public class Language extends TreeMap<String, String>
{
    static final long serialVersionUID = 4941525634108401848L;

    private static Language lang;
    private String locale;
    public String getLocale() {
		return locale;
	}

	public static Language getInstance()
    {
    	if(lang == null)
    		lang =  new Language();
    	return lang;
    }
    
    /**
     * Ue <code>this.add(InputStream in)<code> for loading language
     * @throws Exception
     */
    private Language()
    {
        // We call the superclass default constructor
        super();
    }
    public static final Logger logger = Logger.getLogger(Language.class);
    public static void loadLanguage(String locale)
    {	
    	try {    		
			InputStream in = new FileInputStream(System.getProperty("user.dir")
					+ "/templates/lang_" + locale + ".xml");
			if(lang == null )lang = new Language();
			lang.locale = locale;
    		lang.add(in);
		} catch (Exception e) {
			logger.error(e.getMessage());
			SystemMessageDialog.showMessageDialog(null
					,"Loi nap ngon ngu: " + locale,
					SystemMessageDialog.SHOW_OK_OPTION);
		}
    }
    @SuppressWarnings("unchecked")
	public void add(InputStream in) throws Exception
    {
    	// Clear first when we use Singleton
    	lang.clear();    	
        // Initialises the parser
        StdXMLParser parser = new StdXMLParser();
        parser.setBuilder(new StdXMLBuilder());
        parser.setReader(new StdXMLReader(in));
        parser.setValidator(new NonValidator());

        // We get the data
        XMLElement data = (XMLElement) parser.parse();

        // We check the data
        if (!"langpack".equalsIgnoreCase(data.getName()))
            throw new Exception("This is not 2N XML langpack file");

        // We fill the Hash table
        Vector<XMLElement> children = data.getChildren();
        int size = children.size();
        for (int i = 0; i < size; i++)
        {
            XMLElement e = (XMLElement) children.get(i);
            String text = e.getContent();
            if (text != null && !"".equals(text))
            {
                lang.put(e.getAttribute("id"), text.trim());
            }
            else
            {
            	lang.put(e.getAttribute("id"), e.getAttribute("txt"));
            }
        }

    }

    /**
     * Convenience method to retrieve an element.
     * 
     * @param key The key of the element to retrieve.
     * @return The element value or the key if not found.
     */
    public String getString(String key)
    {
        String val = (String) lang.get(key);
			if (val == null)
				val = key;
		
		return val;
    }
}