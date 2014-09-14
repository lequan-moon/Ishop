package test;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

public class GroupRadio {
  private static final String sliceOptions[] = { "4 slices", "8 slices",
      "12 slices", "16 slices" };

  private static final String crustOptions[] = { "Sicilian", "Thin Crust",
      "Thick Crust", "Stuffed Crust" };

  public static void main(String args[]) {
    JFrame frame = new JFrame("Grouping Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container sliceContainer = RadioButtonUtils.createRadioButtonGrouping(
        sliceOptions, "Slice Count");
    Container crustContainer = RadioButtonUtils.createRadioButtonGrouping(
        crustOptions, "Crust Type");
    Container contentPane = frame.getContentPane();
    contentPane.add(sliceContainer, BorderLayout.WEST);
    contentPane.add(crustContainer, BorderLayout.EAST);
    frame.setSize(300, 200);
    frame.setVisible(true);
  }
}

class RadioButtonUtils {
  private RadioButtonUtils() {
    // private constructor so you can't create instances
  }

  public static Enumeration getSelectedElements(Container container) {
    Vector selections = new Vector();
    Component components[] = container.getComponents();
    for (int i = 0, n = components.length; i < n; i++) {
      if (components[i] instanceof AbstractButton) {
        AbstractButton button = (AbstractButton) components[i];
        if (button.isSelected()) {
          selections.addElement(button.getText());
        }
      }
    }
    return selections.elements();
  }

  public static Container createRadioButtonGrouping(String elements[]) {
    return createRadioButtonGrouping(elements, null, null, null, null);
  }

  public static Container createRadioButtonGrouping(String elements[],
      String title) {
    return createRadioButtonGrouping(elements, title, null, null, null);
  }

  public static Container createRadioButtonGrouping(String elements[],
      String title, ItemListener itemListener) {
    return createRadioButtonGrouping(elements, title, null, itemListener,
        null);
  }

  public static Container createRadioButtonGrouping(String elements[],
      String title, ActionListener actionListener) {
    return createRadioButtonGrouping(elements, title, actionListener, null,
        null);
  }

  public static Container createRadioButtonGrouping(String elements[],
      String title, ActionListener actionListener,
      ItemListener itemListener) {
    return createRadioButtonGrouping(elements, title, actionListener,
        itemListener, null);
  }

  public static Container createRadioButtonGrouping(String elements[],
      String title, ActionListener actionListener,
      ItemListener itemListener, ChangeListener changeListener) {
    JPanel panel = new JPanel(new GridLayout(0, 1));
    //   If title set, create titled border
    if (title != null) {
      Border border = BorderFactory.createTitledBorder(title);
      panel.setBorder(border);
    }
    //   Create group
    ButtonGroup group = new ButtonGroup();
    JRadioButton aRadioButton;
    //   For each String passed in:
    //   Create button, add to panel, and add to group
    for (int i = 0, n = elements.length; i < n; i++) {
      aRadioButton = new JRadioButton(elements[i]);
      panel.add(aRadioButton);
      group.add(aRadioButton);
      if (actionListener != null) {
        aRadioButton.addActionListener(actionListener);
      }
      if (itemListener != null) {
        aRadioButton.addItemListener(itemListener);
      }
      if (changeListener != null) {
        aRadioButton.addChangeListener(changeListener);
      }
    }
    return panel;
  }
}