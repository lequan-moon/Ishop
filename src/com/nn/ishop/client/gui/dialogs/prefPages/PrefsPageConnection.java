/*****************************************************************************/
/* */
/*****************************************************************************/

package com.nn.ishop.client.gui.dialogs.prefPages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.util.Util;

/**
 * 
 *
 * @author NguyenNghia
 * @version 1.0
 */
public class PrefsPageConnection extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6574920933827997786L;
	
	JPanel serverPanel = new JPanel();
    JLabel serverSocketLabel = new JLabel("Server address");
    JTextField serverConnectStringField = new JTextField();
    
    JLabel lblServerPort = new JLabel("Port:");
    JTextField serverConnectPortField = new JTextField();
    
    private JCheckBox chkConfirmExit = new JCheckBox();
    private FlowLayout layoutConfirm = new FlowLayout(FlowLayout.LEFT);
    private JPanel pnlConfirmExit = new JPanel();

  
    /**
     * Creates a new PrefsPageConnection object.
     */
    public PrefsPageConnection() {
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
        this.setLayout(new VerticalFlowLayout());
        setBackground(CConstants.BG_COLOR);
        Properties sys = Util.getSystemProperties();
        serverPanel.setBackground(CConstants.BG_COLOR);
        serverPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(
						new CLineBorder(new Color(171,171,175),3)
						, BorderFactory.createEmptyBorder(3, 3, 3, 3))
						, " CONNECT Application Server "
						, TitledBorder.DEFAULT_JUSTIFICATION
						, TitledBorder.DEFAULT_POSITION
						, serverSocketLabel.getFont()));
        
        serverPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2,2,2,2);
                
        serverSocketLabel.setText("Server Address:");
        serverPanel.add(serverSocketLabel,gbc);
        
        serverConnectStringField.setMinimumSize(new Dimension(150, 20));
        serverConnectStringField.setPreferredSize(new Dimension(150, 20));
        serverConnectStringField.setText(sys.getProperty("com.gbits.wsms.server1.addr"));   
        gbc.gridx = 1;
        gbc.gridy = 0;
        serverPanel.add(serverConnectStringField,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        serverPanel.add(lblServerPort,gbc);
        
        serverConnectPortField.setText(sys.getProperty("com.gbits.wsms.server1.port"));
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0f;
        serverPanel.add(serverConnectPortField,gbc);        
        
        this.add(serverPanel);
        // part II - confirm exit
        
        String paramConfirmExit = "";
        
        pnlConfirmExit.setLayout(layoutConfirm);
        pnlConfirmExit.setBackground(CConstants.BG_COLOR);
        pnlConfirmExit.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(
						new CLineBorder(new Color(171,171,175),3)
						, BorderFactory.createEmptyBorder(3, 3, 3, 3))
						, " Exit behavior "
						, TitledBorder.DEFAULT_JUSTIFICATION
						, TitledBorder.DEFAULT_POSITION
						, serverSocketLabel.getFont()));
        
                        
        paramConfirmExit = sys.getProperty("com.gbits.wsms.confirmexit");
        
        if(paramConfirmExit.equalsIgnoreCase("true"))
        	chkConfirmExit.setSelected(true);
        else if(paramConfirmExit.equalsIgnoreCase("false"))
        	chkConfirmExit.setSelected(false);
                
        chkConfirmExit.setText("Confirm before exit");//Util.getLabel("quitTitle"));
        chkConfirmExit.setBackground(CConstants.BG_COLOR);
        pnlConfirmExit.add(chkConfirmExit);
        this.add(pnlConfirmExit);
        
        Dimension d = this.getPreferredSize();
		d.width = 328;
		setPreferredSize(d);
    }
    /**
     * get address of server1 
     *
     * @return address of server1
     */
    public String getServer1Addr() {
        return serverConnectStringField.getText();
    }

    /**
     * get port of server1 
     *
     * @return port of server1
     */
    public String getServer1Port() {
        return serverConnectPortField.getText();
    }

    
    /**
     * get port of server1 
     *
     * @return port of server1
     */
    public String getConfirmExit() 
    {
    	if(chkConfirmExit.isSelected() == false)
    		return new String("false");
    	else
    		return new String("true");
    }
    //
    public void setServerAddress(String serverAddress){
    	serverConnectStringField.setText(serverAddress);    	
    }
    public void setServerPort(String port){
    	serverConnectPortField.setText(port);    	
    }
    public void setCheckConfirmExit(boolean selected){
    	chkConfirmExit.setSelected(selected);
    }
    //
}
