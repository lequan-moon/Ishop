package test.swixml;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.nn.ishop.client.gui.AbstractGUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.purchase.PurchaseMasterGUIManager;

public class TestUI {
	public static void main(String[] args ) throws Exception{
		AbstractGUIManager guiManager; 
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}	
		Language.loadLanguage("vn");
		
		JFrame f = new JFrame();
		
		guiManager = new PurchaseMasterGUIManager(Language.getInstance().getLocale());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(guiManager.getRootComponent());		
		
		f.pack();
		f.validate();
		
		f.setVisible(true);
	}
}
