package test;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
 
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
public class TestToolBarTabs {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
 
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    UIManager.getDefaults().put("TabbedPane.contentBorderInsets",
                            new Insets(0, 0, 0, 0));
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
 
                final CardLayout cardLayout = new CardLayout();
                final JPanel panel = new JPanel(cardLayout);
                panel.add(createLabel("Tab One"), "Tab One");
                panel.add(createLabel("Tab Two"), "Tab Two");
                panel.add(createLabel("Tab Three"), "Tab Three");
                cardLayout.show(panel, "Tab One");
 
                JButton button = new JButton("Button");
                button.setAlignmentY(0.5f);
 
                final JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP,
                        JTabbedPane.SCROLL_TAB_LAYOUT);
                pane.addTab("Tab One", new JPanel());
                pane.addTab("Tab Two", new JPanel());
                pane.addTab("Tab Three", new JPanel());
 
                // guessed the fixed width
                final Dimension landscapeSize = new Dimension(170, button.getPreferredSize().height);
                final Dimension portraitSize = new Dimension(70, landscapeSize.height * pane.getTabCount());
                pane.setMinimumSize(landscapeSize);
                pane.setPreferredSize(landscapeSize);
                pane.setMaximumSize(landscapeSize);
 
                pane.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        cardLayout.show(panel, pane.getTitleAt(pane.getSelectedIndex()));
                    }
                });
 
                final JToolBar bar = new JToolBar();
                bar.add(pane);
                bar.add(button);
                bar.addHierarchyListener(new HierarchyListener() {
                    public void hierarchyChanged(HierarchyEvent e) {
                        Container parent = bar.getParent();
                        if (parent != null) {
                            Object constraints = ((BorderLayout) parent.getLayout())
                                    .getConstraints(bar);
                            if (BorderLayout.NORTH.equals(constraints)
                                    || BorderLayout.CENTER.equals(constraints)) {
                                pane.setTabPlacement(JTabbedPane.TOP);
 
                                pane.setMinimumSize(landscapeSize);
                                pane.setPreferredSize(landscapeSize);
                                pane.setMaximumSize(landscapeSize);
 
                            }
                            else if (BorderLayout.WEST.equals(constraints)) {
                                pane.setTabPlacement(JTabbedPane.LEFT);
 
                                pane.setMinimumSize(portraitSize);
                                pane.setPreferredSize(portraitSize);
                                pane.setMaximumSize(portraitSize);
                            }
                            else if (BorderLayout.SOUTH.equals(constraints)) {
                                pane.setTabPlacement(JTabbedPane.BOTTOM);
 
                                pane.setMinimumSize(landscapeSize);
                                pane.setPreferredSize(landscapeSize);
                                pane.setMaximumSize(landscapeSize);
                            }
                            else if (BorderLayout.EAST.equals(constraints)) {
                                pane.setTabPlacement(JTabbedPane.RIGHT);
 
                                pane.setMinimumSize(portraitSize);
                                pane.setPreferredSize(portraitSize);
                                pane.setMaximumSize(portraitSize);
                            }
                        }
                        else {
                            pane.setTabPlacement(JTabbedPane.TOP);
 
                            pane.setMinimumSize(landscapeSize);
                            pane.setPreferredSize(landscapeSize);
                            pane.setMaximumSize(landscapeSize);
                        }
                    }
                });
 
                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(bar, BorderLayout.PAGE_START);
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
 
            private Component createLabel(String text) {
                JLabel label = new JLabel(text);
                label.setPreferredSize(new Dimension(400, 300));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });
    }
}