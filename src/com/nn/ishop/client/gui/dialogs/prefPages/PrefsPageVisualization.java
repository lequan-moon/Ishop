/*****************************************************************************/
/* File Description  : PrefsPageVisualization                                */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : PrefsPageVisualization.java                           */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.gui.dialogs.prefPages;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.nn.ishop.client.util.Util;

/**
 * <p>Title: Connect Standalone Client</p>
 *  <p>Description: This class represents PrefsPageVisualization</p>
 *  <p>Copyright: Copyright (c) 2004</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Dr. Michael B�ni
 * @version 1.0
 */
public class PrefsPageVisualization extends JPanel 
{
    JPanel generalPanel = new JPanel();
    JLabel visSettingsLabel = new JLabel();
    JCheckBox graphicsAACheckBox = new JCheckBox();
    JCheckBox textAACheckbox = new JCheckBox();
    JPanel dashboardPanel = new JPanel();
    JCheckBox dashboardSimpleVertexCheckbox = new JCheckBox();
    JCheckBox dashboardEdgeLabelCheckbox = new JCheckBox();
    JPanel tagSubgraphPanel = new JPanel();
    JCheckBox tagSubSimpleVertexDecoratorCheckbox = new JCheckBox();
    JCheckBox tagSubEdgeLabelCheckBox = new JCheckBox();
    JPanel kneSubgraphPanel = new JPanel();
    JCheckBox kneSubSimpleVertexDecoratorCheckbox = new JCheckBox();
    JCheckBox kneSubEdgeLabelCheckBox = new JCheckBox();
    JPanel ipeSubgraphPanel = new JPanel();
    JCheckBox ipeSubSimpleVertexDecoratorCheckbox = new JCheckBox();
    JCheckBox ipeSubEdgeLabelCheckBox = new JCheckBox();

    /**
     * Creates a new PrefsPageVisualization object.
     */
    public PrefsPageVisualization() {
        try {
            jbInit();
        } catch (Exception ex) {
//            ConnectApplicationExceptionHandler.processException(
//            		ConnectApplicationExceptionUtil.createException(ex, "",
//                    ConnectApplicationException.INFO));
        }
    }
    
    /**
     * initialize components 
     *
     * @throws Exception An instance of type Exception
     */
    void jbInit() throws Exception {
        this.add(generalPanel);

        this.setLayout(new VerticalFlowLayout());
        visSettingsLabel.setFont(new java.awt.Font("Dialog", 1, 14));
        visSettingsLabel.setVerifyInputWhenFocusTarget(true);
        visSettingsLabel.setText("Visualization Settings");
        this.add(visSettingsLabel, null);

        generalPanel.setBorder(new TitledBorder(BorderFactory.
        		createEtchedBorder(Color.white, new Color(165, 163, 151)),
                "General Visualization Settings"));
        generalPanel.setLayout(new XYLayout());
        graphicsAACheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
        graphicsAACheckBox.setText("Anti-Aliasing of Graphics");
        textAACheckbox.setHorizontalAlignment(SwingConstants.LEADING);
        textAACheckbox.setHorizontalTextPosition(SwingConstants.LEFT);
        textAACheckbox.setText("Anti-Aliasing of Text");

        dashboardPanel.setBorder(new TitledBorder(BorderFactory.
        		createEtchedBorder(Color.white, new Color(168, 168, 168)),
                "Dashboard"));
        dashboardPanel.setLayout(new XYLayout());
        dashboardSimpleVertexCheckbox.setText("Simple Graph Decoration");
        dashboardSimpleVertexCheckbox.setHorizontalTextPosition(SwingConstants.LEFT);
        dashboardSimpleVertexCheckbox.setHorizontalAlignment(SwingConstants.LEADING);
        dashboardEdgeLabelCheckbox.setText("Show Edge Labels");
        dashboardEdgeLabelCheckbox.setHorizontalAlignment(SwingConstants.LEADING);
        dashboardEdgeLabelCheckbox.setHorizontalTextPosition(SwingConstants.LEADING);
        dashboardPanel.add(dashboardEdgeLabelCheckbox, new XYConstraints(179, 0, -1, -1));
        dashboardPanel.add(dashboardSimpleVertexCheckbox, new XYConstraints(4, 0, -1, -1));
        this.add(dashboardPanel);

        tagSubgraphPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,
                    new Color(156, 156, 158)), "Tag Subgraph"));
        tagSubgraphPanel.setLayout(new XYLayout());
        tagSubgraphPanel.setPreferredSize(new Dimension(390, 50));
        tagSubSimpleVertexDecoratorCheckbox.setText("Simple Graph Decoration");
        tagSubSimpleVertexDecoratorCheckbox.setHorizontalTextPosition(SwingConstants.LEADING);
        tagSubEdgeLabelCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        tagSubEdgeLabelCheckBox.setText("Show Edge Labels");
        generalPanel.add(graphicsAACheckBox, new XYConstraints(1, 4, -1, -1));
        generalPanel.add(textAACheckbox, new XYConstraints(24, 30, -1, -1));
        tagSubgraphPanel.add(tagSubSimpleVertexDecoratorCheckbox, new XYConstraints(5, 0, -1, -1));
        tagSubgraphPanel.add(tagSubEdgeLabelCheckBox, new XYConstraints(179, 0, -1, -1));
        this.add(tagSubgraphPanel);

        kneSubgraphPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,
                    new Color(156, 156, 158)), "KNE Subgraph"));
        kneSubgraphPanel.setLayout(new XYLayout());
        kneSubgraphPanel.setPreferredSize(new Dimension(390, 50));
        kneSubSimpleVertexDecoratorCheckbox.setText("Simple Graph Decoration");
        kneSubSimpleVertexDecoratorCheckbox.setHorizontalTextPosition(SwingConstants.LEADING);
        kneSubEdgeLabelCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        kneSubEdgeLabelCheckBox.setText("Show Edge Labels");
        generalPanel.add(graphicsAACheckBox, new XYConstraints(1, 4, -1, -1));
        generalPanel.add(textAACheckbox, new XYConstraints(24, 30, -1, -1));
        kneSubgraphPanel.add(kneSubSimpleVertexDecoratorCheckbox, new XYConstraints(5, 0, -1, -1));
        kneSubgraphPanel.add(kneSubEdgeLabelCheckBox, new XYConstraints(179, 0, -1, -1));
        this.add(kneSubgraphPanel);

        ipeSubgraphPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,
                    new Color(156, 156, 158)), "Entity Subgraph (IPE)"));
        ipeSubgraphPanel.setLayout(new XYLayout());
        ipeSubgraphPanel.setPreferredSize(new Dimension(390, 50));
        ipeSubSimpleVertexDecoratorCheckbox.setText("Simple Graph Decoration");
        ipeSubSimpleVertexDecoratorCheckbox.setHorizontalTextPosition(SwingConstants.LEADING);
        ipeSubEdgeLabelCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        ipeSubEdgeLabelCheckBox.setText("Show Edge Labels");
        generalPanel.add(graphicsAACheckBox, new XYConstraints(1, 4, -1, -1));
        generalPanel.add(textAACheckbox, new XYConstraints(24, 30, -1, -1));
        ipeSubgraphPanel.add(ipeSubSimpleVertexDecoratorCheckbox, new XYConstraints(5, 0, -1, -1));
        ipeSubgraphPanel.add(ipeSubEdgeLabelCheckBox, new XYConstraints(179, 0, -1, -1));
        this.add(ipeSubgraphPanel);

        // Now set the system properties (checkboxes) as found in the cfg file
        String prefix = "com.gbits.wsms.visualization.decorations.";
        Properties sp = Util.getSystemProperties();
        graphicsAACheckBox.setSelected(sp.getProperty(prefix + "graphicsaa", "false").equals("true"));
        textAACheckbox.setSelected(sp.getProperty(prefix + "textaa", "false").equals("true"));

        dashboardSimpleVertexCheckbox.setSelected(sp.getProperty(prefix + "dashboard.simpleGraphDecoration", "false")
                                                    .equals("true"));
        dashboardEdgeLabelCheckbox.setSelected(sp.getProperty(prefix + "dashboard.showEdgeLabels", "false")
                                                 .equals("true"));
        tagSubSimpleVertexDecoratorCheckbox.setSelected(sp.getProperty(prefix + "tagsubnet.simpleGraphDecoration",
                "false").equals("true"));
        tagSubEdgeLabelCheckBox.setSelected(sp.getProperty(prefix + "tagsubnet.showEdgeLabels", "false").equals("true"));
        kneSubSimpleVertexDecoratorCheckbox.setSelected(sp.getProperty(prefix + "knesubnet.simpleGraphDecoration",
                "false").equals("true"));
        kneSubEdgeLabelCheckBox.setSelected(sp.getProperty(prefix + "knesubnet.showEdgeLabels", "false").equals("true"));
        ipeSubSimpleVertexDecoratorCheckbox.setSelected(sp.getProperty(prefix + "ipesubnet.simpleGraphDecoration",
                "false").equals("true"));
        ipeSubEdgeLabelCheckBox.setSelected(sp.getProperty(prefix + "ipesubnet.showEdgeLabels", "false").equals("true"));
    }

    /**
     * get GraphicsAASetting
     *
     * @return boolean Selected state of GraphicsAACheckBox
     */
    public boolean getGraphicsAASetting() {
        return graphicsAACheckBox.isSelected();
    }

    /**
     * get TextAASetting
     *
     * @return boolean Selected state of TextAACheckbox
     */
    public boolean getTextAASetting() {
        return textAACheckbox.isSelected();
    }

    /**
     * get DashboardSimpleVertexDecorator
     *
     * @return boolean Selected state of DashboardSimpleVertexCheckbox
     */
    public boolean getDashboardSimpleVertexDecorator() {
        return dashboardSimpleVertexCheckbox.isSelected();
    }

    /**
     * get DashboardShowEdgeLabels
     *
     * @return boolean Selected state of DashboardEdgeLabelCheckbox
     */
    public boolean getDashboardShowEdgeLabels() {
        return dashboardEdgeLabelCheckbox.isSelected();
    }

    /**
     * get TagSimpleVertexDecorator
     *
     * @return boolean Selected state of TagSubSimpleVertexDecoratorCheckbox
     */
    public boolean getTagSimpleVertexDecorator() {
        return tagSubSimpleVertexDecoratorCheckbox.isSelected();
    }

    /**
     * get TagShowEdgeLabels
     *
     * @return boolean Selected state of TagSubEdgeLabelCheckBox
     */
    public boolean getTagShowEdgeLabels() {
        return tagSubEdgeLabelCheckBox.isSelected();
    }

    /**
     * get KneSimpleVertexDecorator
     *
     * @return boolean Selected state of KneSubSimpleVertexDecoratorCheckbox
     */
    public boolean getKneSimpleVertexDecorator() {
        return kneSubSimpleVertexDecoratorCheckbox.isSelected();
    }

    /**
     * get KneShowEdgeLabels
     *
     * @return boolean Selected state of KneSubEdgeLabelCheckBox
     */
    public boolean getKneShowEdgeLabels() {
        return kneSubEdgeLabelCheckBox.isSelected();
    }

    /**
     * get IpeSimpleVertexDecorator
     *
     * @return boolean Selected state of IpeSubSimpleVertexDecoratorCheckbox
     */
    public boolean getIpeSimpleVertexDecorator() {
        return ipeSubSimpleVertexDecoratorCheckbox.isSelected();
    }

    /**
     * get IpeShowEdgeLabels
     *
     * @return boolean Selected state of IpeSubEdgeLabelCheckBox
     */
    public boolean getIpeShowEdgeLabels() {
        return ipeSubEdgeLabelCheckBox.isSelected();
    }
}
