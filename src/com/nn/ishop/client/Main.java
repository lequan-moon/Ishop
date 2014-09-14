package com.nn.ishop.client;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.nn.ishop.client.cache.CacheManager;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.GUIManager;
import com.nn.ishop.client.gui.Language;
import com.nn.ishop.client.gui.dialogs.SystemMessageDialog;
import com.nn.ishop.client.gui.dialogs.LoginDialog;
import com.nn.ishop.client.gui.dialogs.SplashScreen;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.client.util.CredentialSetter;
import com.nn.ishop.client.util.CryptoToolkit;
import com.nn.ishop.client.util.ItemWrapper;
import com.nn.ishop.license.PropertyUtil;
import com.nn.ishop.server.dto.user.Employee;

/**
 * This is the main class of the Connect client program
 * - it initializes all the components (GUI, storage)
 * - it executes business logic on entity level
 * - it handles and redirects action requests
 */
public class Main implements ProgressListener, DataChangeListener, CActionListener
{
	private GUIManager guiManager;
	
	@SuppressWarnings("unused")
	private CacheManager cacheManager;
	public static boolean showAssociatedMode = true;
	public static ItemWrapper selectedEntity;
	public static PropertyUtil propUtil = null;
	
	//** Cached logged in User here **/
	public static EmployeeWrapper loggedInUser = null;
	
	/** Count login times */
	private static int loginCount = 0;
	public static Logger logger = Logger.getLogger(Main.class);
	LoginDialog loginD;
	CredentialSetter loginSetter;
	public Main() {
		 //*********************************************************************************************************
		 //* Work flow:
		 //* 1. Load Language --> 2. Test Server Connection --> 3. Login --> 4. Init GUI --> 5. Add Action Listener
		 //* 
		 //*********************************************************************************************************
		JFrame tempJFrame = new JFrame();
		if(propUtil == null ) 
			propUtil = new PropertyUtil();
		//--- load language by default
		try {
			Language.loadLanguage(propUtil.getProperty("default.language"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			SystemMessageDialog.showMessageDialog(tempJFrame
					,"Error loading default language: " 
					+ propUtil.getProperty("default.language"),
					SystemMessageDialog.SHOW_OK_OPTION);
			System.exit(0);
		}
		//-- Check server status
		BusinessUtil<String> bu = new BusinessUtil<String>();
		String version = null;
		try {
			version = bu.wsGetContainerVersion();
			logger.info(version);
		} catch (Exception e) {
			logger.error("Cannot connect to server "+e.getMessage());
			SystemMessageDialog.showMessageDialog(tempJFrame
					,Language.getInstance().getString("connect.to.server.error") + "\n" + e.getMessage(),
					SystemMessageDialog.SHOW_OK_OPTION);
			System.exit(0);
			tempJFrame.dispose();
		}
		//-- doLogin
		if(propUtil.getProperty("login.infor.need.login").equals("true")){
			loginSetter = new CredentialSetter();
			loginD = new LoginDialog(tempJFrame,loginSetter );
			loginD.setVisible(false);
			while(loginCount < 3){
				try {
					doLogin(tempJFrame);
					if(loggedInUser != null)break;
				} catch (Exception e) {
					e.printStackTrace();
					loginCount++;
					logger.error("Login count = "+ loginCount);
					if(loginCount == 3)
					{
						SystemMessageDialog.showMessageDialog(tempJFrame
								,Language.getInstance().getString("connect.over.three.times"),
								SystemMessageDialog.SHOW_OK_OPTION);
						System.exit(0);
					}
					tempJFrame.dispose();
				}
			}
		}
		SplashScreen splashScreen = new SplashScreen(tempJFrame);
		splashScreen.setVisible(true);
		splashScreen.setMaxProgressBarValues(100);
		splashScreen.setProgressiveValue(5);
		
		//-- Init main GUI
		guiManager = new GUIManager();
		guiManager.addCActionListener(this);
		splashScreen.dispose();
	}
	private void doLogin(JFrame frame) throws Exception
	  {

		logger.info("Process logging in!");
		
		loginD.setVisible(true);
		loginD.setLblError(Language.getInstance().getString("login.process"));
		loginD.requestFocus();
		loginD.setFocus();

		// The actual login procedure
		if (loginSetter.getOkPressed()) {
			String pass = CryptoToolkit
					.getSHA512Hash(loginSetter.getPassword());
			String loginId = loginSetter.getUsername();
			BusinessUtil<Employee> bu = new BusinessUtil<Employee>();
			Employee em = bu
					.webserviceGetDto(
							new Integer(-1),
							SystemConfiguration.LOCALPART_EMPLOYEE_GET_USER_BY_LOGIN_ID,
							new Class[] { Employee.class },
							new Object[] { loginId },
							SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR);
			if (em == null) {
				loginD.setLblError(Language.getInstance().getString(
				"login.invalid"));
				return;
			}
			try {
				boolean loginSucceed = pass.equalsIgnoreCase(em
						.getLogin_password());
				if (!loginSucceed) {
					loginD.setLblError(Language.getInstance().getString(
							"login.invalid.password"));
					throw new Exception("Invalid password!");
				}
				loggedInUser = new EmployeeWrapper(em);
			} catch (Exception e) {
				loginD.setLblError(Language.getInstance().getString(
						"login.invalid.password") );
				logger.error(e.getMessage());
			}

		} else {
			System.exit(0);
		}

	  }

	public void progressStatusChanged(ProgressEvent pe) {
		
	}

	public void dataChanged(DataChangeEvent dce) {
	}

	public void cActionPerformed(CActionEvent action) {		
		if (action.getAction() == CActionEvent.ADD) {
		}else if (action.getAction() == CActionEvent.SAVE) {
		}else if (action.getAction() == CActionEvent.REMOVE) {
			
		} else if (action.getAction() == CActionEvent.LIST_SELECT_ITEM) {
			Object data = action.getData(); 
			if(data instanceof ItemWrapper)
				selectedEntity = (ItemWrapper) data;
		}else if (action.getAction() == CActionEvent.LOG_OUT) {
			//-- do logout
			GUIManager.mainFrame.setVisible(false);
			new Main();
			GUIManager.mainFrame.setVisible(true);
		}
	}	
	
	public static void main(String[] agrs) {		
		new Main();		
	}

	public void setGuiManager(GUIManager guiManager) {
		this.guiManager = guiManager;
	}

	public GUIManager getGuiManager() {
		return this.guiManager;
	}
	public void addCActionListener(CActionListener al) {
		
	}
	public void removeCActionListener(CActionListener al) {
		
	}
	
}