package com.nn.ishop.client.gui.dialogs.prefPages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.nn.ishop.client.gui.components.CDialogsLabelButton;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.util.Util;

public class PrefsPageDataImpExp extends JPanel{
	/** Serial version UID*/
	private static final long serialVersionUID = -5251859643265293465L;
	JFileChooser importExportRepo;
	
	JCheckBox showProjectSelection;
	JCheckBox alwaysImportCurrentProject;
	
	JComboBox defaultExportType;
	JComboBox exportEncoding;
	
	JTextField txtImportRepo;
	JTextField txtExportRepo;
	CDialogsLabelButton btnImpRepo;
	CDialogsLabelButton btnExpRepo;
	
	
	private ImageIcon normalBtnIcon = null;
    public ImageIcon activeBtnIcon = null;

	public PrefsPageDataImpExp() {
		setLayout(new GridLayout(2,1));
		setBackground(CConstants.BG_COLOR);
		setOpaque(true);
		jbInit();
	}
	private void jbInit(){
		
		this.normalBtnIcon = Util.getIcon(CConstants.NORMAL_BUTTON_ICON);
	    this.activeBtnIcon = Util.getIcon(CConstants.ACTIVE_BUTTON_ICON);		

		
		//initialize control
		btnImpRepo =  new CDialogsLabelButton("Browse"/*
				, normalBtnIcon, activeBtnIcon*/);
		btnExpRepo =  new CDialogsLabelButton("Browse"/*
				, normalBtnIcon, activeBtnIcon*/);
		
		btnExpRepo.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				importExportRepo = new JFileChooser();
				importExportRepo.setCurrentDirectory(new File(System.getProperty("user.dir")));
				importExportRepo.setMultiSelectionEnabled(false);
				importExportRepo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				importExportRepo.addChoosableFileFilter(importExportRepo.getAcceptAllFileFilter());
				int retVal = importExportRepo.showOpenDialog(PrefsPageDataImpExp.this);
				  if (retVal == JFileChooser.APPROVE_OPTION) 
			      {
					  
					  txtExportRepo.setText(
							  importExportRepo.getSelectedFile().toString());
			      }
								
			}
		});
		btnImpRepo.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				importExportRepo = new JFileChooser();
				importExportRepo.setCurrentDirectory(new File(System.getProperty("user.dir")));
				importExportRepo.setMultiSelectionEnabled(false);
				importExportRepo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				importExportRepo.addChoosableFileFilter(importExportRepo.getAcceptAllFileFilter());
				int retVal = importExportRepo.showOpenDialog(PrefsPageDataImpExp.this);
				  if (retVal == JFileChooser.APPROVE_OPTION) 
			      {
					  txtImportRepo.setText(importExportRepo.getSelectedFile().toString());
			      }
				
			}
		});

		
		txtImportRepo = new JTextField(System.getProperty("user.dir"));
		txtImportRepo.setBackground(CConstants.BG_COLOR);
		
		txtExportRepo = new JTextField(System.getProperty("user.dir"));
		txtExportRepo.setBackground(CConstants.BG_COLOR);
		
//		Util.setLookAndFeelForComponent(txtExportRepo);
		
		showProjectSelection = new JCheckBox(
				"Show project selection on import");
		showProjectSelection.setBackground(CConstants.BG_COLOR);
//		Util.setLookAndFeelForComponent(showProjectSelection);
		
		showProjectSelection.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(!showProjectSelection.isEnabled())return;
				if(showProjectSelection.isSelected()){
					alwaysImportCurrentProject.setEnabled(false);
				}else
				{
					alwaysImportCurrentProject.setEnabled(true);
				}
			}
		});
				
		alwaysImportCurrentProject = new JCheckBox(
				"Always import to current project");
		alwaysImportCurrentProject.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(!alwaysImportCurrentProject.isEnabled())return;
				if(alwaysImportCurrentProject.isSelected()){
					showProjectSelection.setEnabled(false);
				}else
				{
					showProjectSelection.setEnabled(true);
				}

			}			
		});
		alwaysImportCurrentProject.setBackground(CConstants.BG_COLOR);
//		Util.setLookAndFeelForComponent(alwaysImportCurrentProject);
	
		defaultExportType = new JComboBox(new String[]{"NetML","CSV"});
		exportEncoding = new JComboBox(new String[]{"UTF-8","iso-8859-1"});
		defaultExportType.setBackground(CConstants.BG_COLOR);
		exportEncoding.setBackground(CConstants.BG_COLOR);
//		Util.setLookAndFeelForComponent(defaultExportType);
//		Util.setLookAndFeelForComponent(exportEncoding);
		JLabel l = new JLabel();
		// import
		JPanel importPanel = new JPanel(new GridBagLayout());
		importPanel.setBackground(CConstants.BG_COLOR);
		// Below should be last actions for this panel
		importPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(
						new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(3, 0, 3, 0))
				, " Import Data "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, l.getFont()));
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.fill = GridBagConstraints.REMAINDER;
		gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc1.insets = new Insets(3,3,3,3);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 3;
		importPanel.add(new JLabel("Default File Location:"),gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.gridwidth = 2;
//		Util.setLookAndFeelForComponent(txtImportRepo);
		txtImportRepo.setOpaque(true);
		txtImportRepo.setMinimumSize(new Dimension(230,20));
		txtImportRepo.setMaximumSize(new Dimension(230,20));		
		txtImportRepo.setPreferredSize(new Dimension(230,20));
		importPanel.add(txtImportRepo,gbc1);
		
			
		gbc1.gridx = 2;
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		importPanel.add(btnImpRepo,gbc1);
		

		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.gridwidth = 3;
		importPanel.add(showProjectSelection,gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 3;
		gbc1.gridwidth = 3;
		gbc1.weightx = 1.0f;
		importPanel.add(alwaysImportCurrentProject,gbc1);
		add(importPanel);
		
		//export
		JPanel exportPanel = new JPanel(new GridBagLayout());
		exportPanel.setBackground(CConstants.BG_COLOR);
		
		exportPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(new CLineBorder(new Color(171,171,175),3)
				, BorderFactory.createEmptyBorder(3, 0, 3, 0))
				, " Export Data "
				, TitledBorder.DEFAULT_JUSTIFICATION
				, TitledBorder.DEFAULT_POSITION
				, l.getFont()));
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.fill = GridBagConstraints.REMAINDER;
		gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc2.insets = new Insets(3,3,3,3);
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 3;
		exportPanel.add(new JLabel("Default file location:"),gbc2);

		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.gridwidth = 2;
//		Util.setLookAndFeelForComponent(txtExportRepo);
		txtExportRepo.setOpaque(true);
		txtExportRepo.setPreferredSize(
				txtImportRepo.getPreferredSize());
		txtExportRepo.setMaximumSize(
				txtImportRepo.getPreferredSize());
		txtExportRepo.setMinimumSize(
				txtImportRepo.getPreferredSize());
		exportPanel.add(txtExportRepo,gbc2);
		
		gbc2.gridx = 2;
		gbc2.gridy = 1;
		gbc2.gridwidth = 1;
		exportPanel.add(btnExpRepo,gbc2);	
		
		gbc2.gridx = 0;
		gbc2.gridy = 2;
		gbc2.gridwidth = 1;
		exportPanel.add(new JLabel("Default Type:"),gbc2);		
		
		gbc2.gridx = 1;
		gbc2.gridy = 2;
		gbc2.gridwidth = 1;
		exportPanel.add(new JLabel("Default Encoding:"),gbc2);		
		
		gbc2.gridx = 2;
		gbc2.gridy = 2;
		gbc2.gridwidth = 1;
		exportPanel.add(new JLabel(""),gbc2);
		
		gbc2.gridx = 0;
		gbc2.gridy = 3;
		gbc2.gridwidth = 1;
		exportPanel.add(defaultExportType,gbc2);		
		defaultExportType.setMaximumSize(new Dimension(110,20));
		defaultExportType.setMinimumSize(new Dimension(110,20));
		
		
		gbc2.gridx = 1;
		gbc2.gridy = 3;
		gbc2.gridwidth = 1;
		exportEncoding.setMaximumSize(new Dimension(110,20));
		exportEncoding.setMinimumSize(new Dimension(110,20));
		exportPanel.add(exportEncoding,gbc2);
		
		gbc2.gridx = 2;
		gbc2.gridy = 3;
		gbc2.gridwidth = 1;
		gbc2.weightx = 1.0f;
		exportPanel.add(new JLabel(""),gbc2);
		
		
		add(exportPanel);
		
		
		Dimension d = this.getPreferredSize();
		d.width = 328;
		setPreferredSize(d);			
	}
	/***/
    public String getExportRepo(){
    	return txtExportRepo.getText();
    }
    public boolean isShowProjectSelect()
    {
    	return showProjectSelection.isSelected();
    }
    public boolean isAlwaysImportCurrentPorject(){
    	return alwaysImportCurrentProject.isSelected();
    }
    
    public String getDefaultExportType(){
    	return (String)defaultExportType.getSelectedItem();    	
    }
    public String getExportDefaultEncoding(){
    	return (String)exportEncoding.getSelectedItem();
    }
    public String getImportRepo(){
    	return txtImportRepo.getText();
    }
	public void setShowProjectSelection(boolean isSelected){
		showProjectSelection.setSelected(isSelected);
	}
	public void setAlwaysImportCurrentProject(boolean selected)
	{
		alwaysImportCurrentProject.setSelected(selected);
	}
	
	public void setDefaultExportType(String expType)
	{
		defaultExportType.setSelectedItem(expType);
	}
	public void setExportEncoding(String encoding)
	{
		exportEncoding.setSelectedItem(encoding);
	}
	
	public void setTxtImportRepo(String impRepo)
	{
		txtImportRepo.setText(impRepo);
	}
	public void setTxtExportRepo(String expRepo)
	{
		txtExportRepo.setText(expRepo);
	}
}
