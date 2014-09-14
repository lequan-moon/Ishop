package com.nn.ishop.client.gui.components;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;

public class CButtonUI extends BasicButtonUI {
	
	protected void installDefaults(AbstractButton b) {
        // load shared instance defaults
        String pp = getPropertyPrefix();

        defaultTextShiftOffset = UIManager.getInt(pp + "textShiftOffset");

        // set the following defaults on the button
//        if (b.isContentAreaFilled()) {
            LookAndFeel.installProperty(b, "opaque", Boolean.FALSE);
//        } else {
//            LookAndFeel.installProperty(b, "opaque", Boolean.FALSE);
//        }

        if(b.getMargin() == null || (b.getMargin() instanceof UIResource)) {
            b.setMargin(UIManager.getInsets(pp + "margin"));
        }

//	LookAndFeel.installColorsAndFont(b, pp + "background",
//                                         pp + "foreground", pp + "font");
        LookAndFeel.installBorder(b, pp + "border");

        Object rollover = UIManager.get(pp + "rollover");
        if (rollover != null) {
            LookAndFeel.installProperty(b, "rolloverEnabled", rollover);
        }

        LookAndFeel.installProperty(b, "iconTextGap", new Integer(4));
    }
	private static Rectangle viewRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();
    private static Rectangle iconRect = new Rectangle();
	public void paint(Graphics g, JComponent c) 
    {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();

        String text = layout(b, SwingUtilities2.getFontMetrics(b, g),
               b.getWidth(), b.getHeight());

        clearTextShiftOffset();

        // perform UI specific press action, e.g. Windows L&F shifts text
        if (model.isArmed() && model.isPressed()) {
            paintButtonPressed(g,b); 
        }

        // Paint the Icon
        if(b.getIcon() != null) { 
            paintIcon(g,c,iconRect);
        }

        if (text != null && !text.equals("")){
	    View v = (View) c.getClientProperty(BasicHTML.propertyKey);
	    if (v != null) {
		v.paint(g, textRect);
	    } else {
		paintText(g, b, textRect, text);
	    }
        }

        if (b.isFocusPainted() && b.hasFocus()) {
            // paint UI specific focus
            paintFocus(g,b,viewRect,textRect,iconRect);
        }
    }
	  private String layout(AbstractButton b, FontMetrics fm,
              int width, int height) {
		Insets i = b.getInsets();
		viewRect.x = i.left;
		viewRect.y = i.top;
		viewRect.width = width - (i.right + viewRect.x);
		viewRect.height = height - (i.bottom + viewRect.y);
		
		textRect.x = textRect.y = textRect.width = textRect.height = 0;
		iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
		
		// layout the text and icon
		return SwingUtilities.layoutCompoundLabel(
		b, fm, b.getText(), b.getIcon(), 
		b.getVerticalAlignment(), b.getHorizontalAlignment(),
		b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
		viewRect, iconRect, textRect, 
		b.getText() == null ? 0 : b.getIconTextGap());
	}
	
	
}
