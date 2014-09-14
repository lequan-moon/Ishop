package com.nn.ishop.client.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.util.Library;
import com.nn.ishop.client.util.Util;

public class CHeaderPanel extends JPanel {
	   /**
     *
     */
    private static final long serialVersionUID = 5264079740349088128L;

    public CHeaderPanel()
    {
       setBackground(Color.WHITE);
    }

    public CHeaderPanel(boolean isDoubleBuffered)
    {
        super(isDoubleBuffered);
    }

    public CHeaderPanel(LayoutManager layout, boolean isDoubleBuffered)
    {
        super(layout, isDoubleBuffered);
    }

    public CHeaderPanel(LayoutManager layout)
    {
        super(layout);
    }
    public CHeaderPanel(JLabel headerTitle, JLabel row2Title
    		, String logoPath)
    {
        super();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new CLineBorder(new Color(171,171,175),4));

        JPanel leftHeaderPnl = new JPanel();
        leftHeaderPnl.setBackground(Color.WHITE);
        leftHeaderPnl.setBorder(new CLineBorder(new Color(171,171,175),4));

        JPanel rightHeaderPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightHeaderPnl.setBackground(Color.WHITE);

        JLabel headerLogo = new JLabel(Util.getIcon(logoPath));
        headerLogo.setBorder(new CLineBorder(new Color(171,171,175),4));
        rightHeaderPnl.add(headerLogo);

        headerTitle.setFont(Library.HEADER_TITLE_FONT);
        GridBagLayout gbl = new GridBagLayout();
        leftHeaderPnl.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.WEST;
        gbc.insets = new Insets(3,10,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftHeaderPnl.add(headerTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(3,20,0,0);
        gbc.fill = GridBagConstraints.WEST;
        leftHeaderPnl.add(row2Title, gbc);


        add(leftHeaderPnl, BorderLayout.WEST);
        add(rightHeaderPnl, BorderLayout.EAST);

    }
    public CHeaderPanel(JLabel headerTitle, JLabel row2Title)
    {
        super();
        // TODO Auto-generated constructor stub
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new CLineBorder(new Color(171,171,175),4));

        JPanel leftHeaderPnl = new JPanel();
        leftHeaderPnl.setBackground(Color.WHITE);
        leftHeaderPnl.setBorder(new CLineBorder(new Color(171,171,175),4));

        JPanel rightHeaderPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightHeaderPnl.setBackground(Color.WHITE);

        JLabel headerLogo = new JLabel(com.nn.ishop.client.util.Util.getIcon("dialog/page_title.png"));
        headerLogo.setBorder(new CLineBorder(new Color(171,171,175),4));
        rightHeaderPnl.add(headerLogo);

        //TODO next time we will change to reading dynamic langPack
        headerTitle.setFont(Library.HEADER_TITLE_FONT);
        GridBagLayout gbl = new GridBagLayout();
        leftHeaderPnl.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.WEST;
        gbc.insets = new Insets(3,10,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftHeaderPnl.add(headerTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(3,20,0,0);
        gbc.fill = GridBagConstraints.WEST;
        leftHeaderPnl.add(row2Title, gbc);


        add(leftHeaderPnl, BorderLayout.WEST);
        add(rightHeaderPnl, BorderLayout.EAST);

    }
}
