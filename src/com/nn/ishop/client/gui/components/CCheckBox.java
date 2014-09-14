package com.nn.ishop.client.gui.components;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * @author connect.shift-think.com
 *
 */
public class CCheckBox
    extends JCheckBox {

	private static final long serialVersionUID = 3725578518194829588L;
private Object tag;
  public CCheckBox() {
    jbInit();
  }

  public CCheckBox(boolean isSelected) {
    super();
    this.setSelected(isSelected);
    jbInit();
  }

  public CCheckBox(Icon icon) {
    super(icon);
    jbInit();
  }

  public CCheckBox(Icon icon, boolean selected) {
    super(icon, selected);
    jbInit();
  }

  public CCheckBox(String text) {
    super(text);
    jbInit();
  }

  public CCheckBox(Action a) {
    super(a);
    jbInit();
  }

  public CCheckBox(String text, boolean selected) {
    super(text, selected);
    jbInit();
  }

  public CCheckBox(String text, Icon icon) {
    super(text, icon);
    jbInit();
  }

  public CCheckBox(String text, Icon icon, boolean selected) {
    super(text, icon, selected);
    jbInit();
  }

  private void jbInit() {
    //this.setFont(SettingFactory.getSetting().getFont());
//    this.setFocusPainted(false);


  }

  public Object getTag() {
    return tag;
  }

  public void setTag(Object tag) {
    this.tag = tag;
  }

  public void setFixedSize(Dimension dim) {
    this.setPreferredSize(dim);
    this.setMinimumSize(dim);
    this.setMaximumSize(dim);
  }

  public void setFixedSize(int width, int height) {
    setFixedSize(new Dimension(width, height));
  }

}
