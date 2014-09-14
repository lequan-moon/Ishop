package com.nn.ishop.client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Parent;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.nn.ishop.client.Main;
import com.nn.ishop.client.config.SystemConfiguration;
import com.nn.ishop.client.gui.components.CLineBorder;
import com.nn.ishop.client.gui.components.RoundedCornerBorder;
import com.nn.ishop.client.logic.object.EmployeeWrapper;
import com.nn.ishop.client.logic.util.BusinessUtil;
import com.nn.ishop.server.dto.user.Employee;


/**
 * This class is the core of every class in connect that uses XML based technology to organize and display objects. 
 * This class contains a set of basic common functions and a set of methods that have to be implemented from extending 
 * classes to allow the visualization.
 * Principles
 * This class combines two technologies to display objects via xml: swixml, a swing xml language and htmlrenderer 
 * (flying saucer) to display xhtml/css.
 * In addition to this a template engine (velocity) is used to allow the dynamical generation of xml with content data. 
 * Further a small xml handling library (nanoxml) is used to extract portions of the document for separate handling.
 * Steps
 * 1. instantiate the template that will combine the template file and the data
 * 2. pass the data to the template
 * 3. calculate the generated xml document
 * 4. pass the document to the SwixEngine
 * 5. collect all xhtml panels -> in CSWingEngine
 * 6. extract xhtml of each panel from xml source and pass to each ScalableXHTMLPanel -> in CSWingEngine
 * 7. show panel
 * 8. bind event handlers where desired
 */
public abstract class AbstractGUIManager {

	private CSwingEngine swingEngine;
	private Container rootComponent;
	private Template template;
	private Document document;
	protected VelocityContext velocityContext;
	private String templateFileName;
	private String locale = "en";
	public static final Logger logger = Logger.getLogger(AbstractGUIManager.class);
	 //start velocity (singleton)
	 static {		
		  try{
			  Velocity.setProperty("file.resource.loader.path", "templates/");
			  Velocity.init();
		  } catch(Exception ex) {
			  ex.printStackTrace();
		  }
	 }
	 
	/**
	 * Add more locale for multi languages
	 * @param swixmlTargetObject
	 * @param templateFileName
	 * @param locale
	 */
	protected void initTemplate(Object swixmlTargetObject, String templateFileName, String locale){	  
	  this.locale = locale;
	  initTemplate(swixmlTargetObject, templateFileName);
	}
	
	protected void initTemplate(Object swixmlTargetObject, String templateFileName){	  
		  setTemplateFileName(templateFileName);

		  //container for variables to be passed to Velocity
		  if(velocityContext == null)
			  velocityContext = new VelocityContext();
		  // initialize language
		  if(Language.getInstance() == null)loadLanguage(this.locale);
		  velocityContext.put("locale", locale);
		  velocityContext.put("localePng", locale+".png");		  
		  
		  /**
		   * In development, we use the user with ID=0 to run app.
		   * This will be change later
		   * TODO take care next time
		   */
		  try {
			  velocityContext.put("langPack", Language.getInstance());
			  if(Main.loggedInUser == null)
			  {
				  /**
				   * Get default 1 user
				   */
				  try{
					  BusinessUtil<Employee> bu = new BusinessUtil<Employee>();
					  Employee em = bu.webserviceGetDto(new Integer(1)
					  	, SystemConfiguration.LOCALPART_EMPLOYEE_GET_USER
					  	, new Class[]{Employee.class}
					  	, new Object[]{new Integer(1)}
					  	, SystemConfiguration.EMPLOYEE_SERVICE_TARGET_EPR
					  );
					  Main.loggedInUser = new EmployeeWrapper(em);
				  }catch (Exception e)
				  {
					  logger.error(e.getMessage());
				  }
			  }
			  velocityContext.put("loginUser", Main.loggedInUser);
			} catch (Exception e) {
				e.printStackTrace();		
			}
			//start swixml engine
		    swingEngine = new CSwingEngine(swixmlTargetObject);
		}

	protected void render() {
	  try{
		  //get velocity template
	    template = Velocity.getTemplate(templateFileName);
		//calculate the template to be rendered
		StringWriter writer = new StringWriter();
		template.merge(velocityContext, writer);		
		//get swing components
		document = new SAXBuilder().build(new StringReader(writer.toString()));
		rootComponent = swingEngine.render(document);
		applyStyles();
		checkPermission();
	  } catch (Exception ex){
		ex.printStackTrace();
	  }
	}

	protected abstract void bindEventHandlers();

	public void restart(Object swixmlTargetObject)
	{
		  swingEngine.cleanup();
		  swingEngine = new CSwingEngine(swixmlTargetObject);
		  render();		
	}
	public void restart() {
		render();
	}
	/**
	 * Get new instance of single sub component from resource by id
	 * @param componentId String represent for id attribute in xml resource for the expected 
	 * component
	 * @return
	 */
	protected Component getSubComponent(String componentId)
	{
		Container ret = null;
		 try {
			//get velocity template
			template = Velocity.getTemplate(templateFileName);
			//calculate the template to be rendered
			StringWriter writer = new StringWriter();
			template.merge(velocityContext, writer);
			//get element of swixml that has to be updated (use jdocument)
			document = new SAXBuilder().build(new StringReader(writer
					.toString()));
			Element elt = (componentId.equals("root")
					|| document.getRootElement().getAttribute("id").equals(
							componentId) ? document.getRootElement()
					: (Element) XPath.selectSingleNode(document
							.getRootElement(), "//*[@id=\"" + componentId
							+ "\"]"));
			Parent parentNode = elt.getParent();
			parentNode.removeContent(elt);
			
			//render component
			Document tempDocument = new Document(elt);		
			CSwingEngine subSwingEngine = new CSwingEngine(this);
			ret = subSwingEngine.render(tempDocument);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * This use to read Swixml and 
	 * @param componentId
	 */
	public void updateComponent(String componentId) {
	  try{
		    //get velocity template
		    template = Velocity.getTemplate(templateFileName);
			//calculate the template to be rendered
			StringWriter writer = new StringWriter();
			template.merge(velocityContext, writer);		
			
			//get element of swixml that has to be updated (use jdocument)
			document = new SAXBuilder().build(new StringReader(writer.toString()));
			Element elt = (componentId.equals("root")||document.getRootElement().getAttribute("id").equals(componentId) ?
				document.getRootElement() :
				(Element)XPath.selectSingleNode(document.getRootElement(), "//*[@id=\"" + componentId + "\"]")
			);
			Parent parentNode = elt.getParent();
			parentNode.removeContent(elt);
			//get component that hat to be updated (via reflection)
			JComponent oldComponent = (JComponent)(parentNode instanceof Document ?
				rootComponent :
				swingEngine.getClient().getClass().getDeclaredField(componentId).get(swingEngine.getClient())
			);
			//get parent of element that has to be updated
			JComponent parentComponent = (JComponent)oldComponent.getParent();
			//render component
			Document tempDocument = new Document(elt);			
			Container newComponent = swingEngine.render(tempDocument);
			
			//add to parent
            Component[] childComponents = parentComponent.getComponents();
            if(parentComponent instanceof JTabbedPane){
            	JTabbedPane jtp = (JTabbedPane)parentComponent;
            	int pos = jtp.indexOfComponent(oldComponent);
        		String title = jtp.getTitleAt(pos);
        		jtp.remove(pos);
        		jtp.insertTab(title, null, newComponent, "Abc", pos);
        		jtp.setSelectedIndex(pos);
        		// validate tabbed pane
        		jtp.revalidate();   
        		jtp.repaint();
            }
            else for(int i = 0; i < childComponents.length; i++){
            	if(childComponents[i].equals(oldComponent)){
            		parentComponent.remove(oldComponent);
            		parentComponent.add(newComponent, i);
            		break;
            	}
            }
            
			applyStyles();
			bindEventHandlers();
			
			newComponent.setVisible(true);
			//readd xml to origial document
			tempDocument.removeContent(elt);
			if(parentNode instanceof Document){
				//root has changed
				rootComponent = newComponent;
				((Document)parentNode).addContent(elt);
			}
			else ((Element)parentNode).addContent(elt);
			
			//update ui
			rootComponent.validate();
		  } catch (Exception ex){
			ex.printStackTrace();
		  }
	}

	public abstract Object getData(String var);

	public void setData(String var, Object data){
		
	}

	protected abstract void applyStyles();
	
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getTemplateFileName() {
		return this.templateFileName;
	}

	public Container getRootComponent(){
		return swingEngine.getRootComponent();
	}
	
	public abstract void update();
	public abstract void updateList();
	protected abstract void checkPermission();
	
	public void loadLanguage(String locale)
	{	
		this.locale = locale;
		try {
			Language.loadLanguage(locale);
			velocityContext.put("langPack", Language.getInstance());
			velocityContext.put("locale", locale);
			velocityContext.put("localePng", locale+".png");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	protected Component getComponentById(Container container, String componentId){
		List<Component> comps = getAllComponents(container);
		Component ret = null;
		for(Component c: comps){
			if(componentId.equals(c.getName()))
            {                	
                ret = c;
            }
		}
		return ret;		
    }
	public List<Component> getAllComponents(final Container c) {
	    Component[] comps = c.getComponents();
	    List<Component> compList = new ArrayList<Component>();
	    for (Component comp : comps) {
	      compList.add(comp);
	      if (comp instanceof Container) {
	        compList.addAll(getAllComponents((Container) comp));
	      }
	    }
	    return compList;
	  }
	protected enum LOG_LEVEL{
		INFO,DEBUG,ERROR
	}
	@SuppressWarnings("unchecked")
	protected void logAction(Class targetClzz, LOG_LEVEL level, Object message){
		if(level== LOG_LEVEL.INFO)
			logger.info(targetClzz.getName()+": " +message);
		if(level== LOG_LEVEL.DEBUG)
			logger.debug(targetClzz.getName()+": " +message);
		if(level== LOG_LEVEL.ERROR)
			logger.error(targetClzz.getName()+": " +message);
	}
	public void customizeSplitPaneHideFirstComponent(final JSplitPane targetJSplit){
		/*targetJSplit.setBackground(new Color(226,226,226));		
		BasicSplitPaneDivider dividerContainer = (BasicSplitPaneDivider) targetJSplit.getComponent(0);		
		dividerContainer.setBackground(new Color(226,226,226));		
		if(targetJSplit.getOrientation() == 0){
			dividerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
			dividerContainer.setBorder(new CLineBorder(null,CLineBorder.DRAW_TOP_BOTTOM_BORDER));
		}else{
			dividerContainer.setLayout(new BoxLayout(dividerContainer, BoxLayout.Y_AXIS));
			dividerContainer.setBorder(new CLineBorder(null,CLineBorder.DRAW_LEFT_RIGHT_BORDER));
		}
		
		final JButton btnSplit = new JButton(); 
		btnSplit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		if(targetJSplit.getOrientation() == 0)
			btnSplit.setPreferredSize(new Dimension(20,6));
		else{
			btnSplit.setOpaque(true);
			btnSplit.setPreferredSize(new Dimension(6,20));
			btnSplit.setMaximumSize(new Dimension(6,20));
			btnSplit.setMinimumSize(new Dimension(6,20));
			btnSplit.setSize(new Dimension(6,20));
		}
		
		btnSplit.setBorder(new RoundedCornerBorder(new Color(216,182,101),3));
		btnSplit.setBackground(new Color(255,233,157));
		btnSplit.setFocusPainted(false);
		if(targetJSplit.getOrientation() == 0)
			dividerContainer.add(btnSplit);
		else{
			int h = 350;
			dividerContainer.add(Box.createRigidArea(new Dimension(5,h)));
			dividerContainer.add(btnSplit);
		}
		dividerContainer.setDividerSize(8);
		
		btnSplit.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent arg0) {		
				//boolean selected = btnSplit.isSelected();
				BasicSplitPaneUI ui = (BasicSplitPaneUI) targetJSplit.getUI();				
				JButton btnHideBottomComp = (JButton) ui.getDivider().getComponent(1);
				JButton btnHideTopComp = (JButton) ui.getDivider().getComponent(0);			
				 if(targetJSplit.getDividerLocation() == 1.0d){
					 btnHideBottomComp.doClick();
				 }else{	
					 btnHideTopComp.doClick();
				 }
			}
		});*/
	}
	public void customizeSplitPaneHideSecondComponent(final JSplitPane targetJSplit){
		/*targetJSplit.setBackground(new Color(226,226,226));		
		BasicSplitPaneDivider dividerContainer = (BasicSplitPaneDivider) targetJSplit.getComponent(0);	
		dividerContainer.setBackground(new Color(226,226,226));
		
		if(targetJSplit.getOrientation() == 0){
			dividerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
			dividerContainer.setBorder(new CLineBorder(null,CLineBorder.DRAW_TOP_BOTTOM_BORDER));
		}else{
			dividerContainer.setLayout(new BoxLayout(dividerContainer, BoxLayout.Y_AXIS));
			dividerContainer.setBorder(new CLineBorder(null,CLineBorder.DRAW_LEFT_RIGHT_BORDER));
		}
		final JButton btnSplit = new JButton(); 
		btnSplit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		if(targetJSplit.getOrientation() == 0)
			btnSplit.setPreferredSize(new Dimension(20,6));
		else{
			btnSplit.setOpaque(true);
			btnSplit.setPreferredSize(new Dimension(6,20));
			btnSplit.setMaximumSize(new Dimension(6,20));
			btnSplit.setMinimumSize(new Dimension(6,20));
			btnSplit.setSize(new Dimension(6,20));
		}
		btnSplit.setBorder(new RoundedCornerBorder(new Color(216,182,101),3));
		btnSplit.setBackground(new Color(255,233,157));
		btnSplit.setFocusPainted(false);
		if(targetJSplit.getOrientation() == 0)
			dividerContainer.add(btnSplit);
		else{			
			int h = 350;
			dividerContainer.add(Box.createRigidArea(new Dimension(5,h)));
			dividerContainer.add(btnSplit);		
		}
		dividerContainer.setDividerSize(8);
		
		btnSplit.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent arg0) {		
				//boolean selected = btnSplit.isSelected();
				BasicSplitPaneUI ui = (BasicSplitPaneUI) targetJSplit.getUI();				
				JButton btnHideBottomComp = (JButton) ui.getDivider().getComponent(1);
				JButton btnHideTopComp = (JButton) ui.getDivider().getComponent(0);
				
				if(targetJSplit.getDividerLocation() == 0.0d){
					btnHideTopComp.doClick();
				}else{	
					targetJSplit.setDividerLocation(1.0d);
					btnHideBottomComp.doClick();
				}
			}
		});*/
	}

}
