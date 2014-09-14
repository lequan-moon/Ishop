package com.nn.ishop.client.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;
import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionEvent.GUIActionType;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.gui.ITwoModePanel;
import com.nn.ishop.client.gui.dialogs.CConstants;
import com.nn.ishop.client.util.Library;

public class CTwoModePanel extends CWhitePanel  implements ITwoModePanel, GUIActionListener {
	private static final long serialVersionUID = -1974971155103478616L;
	/* The layout of minimize panel can be Vertical or Horizontal, default is Horizontal */
	CWhitePanel minimizeBar = new CWhitePanel(new FlowLayout(FlowLayout.LEFT, 2,2));
	private CLabel minimizeBarTitle = new CLabel(); 
	public CWhitePanel contentPanel= new CWhitePanel(new BorderLayout());
	
	public boolean isMaximize = true;
	private Vector<GUIActionListener> guiActionListenerVector = new Vector<GUIActionListener>();    
	CButton2 minButton = new CButton2("-");
	CButton2 maxButton = new CButton2("+");
	DOCKING_DIRECTION dockingBarDriection = DOCKING_DIRECTION.North;
	LayoutManager  dockableBarLayout = new FlowLayout(FlowLayout.LEFT, 2, 2);

	public enum DOCKING_DIRECTION {
		East,North
	}
	
	public CTwoModePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public CTwoModePanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public CTwoModePanel(LayoutManager layout) {
		super(layout);
	}

	public CTwoModePanel(){
		setLayout(new BorderLayout());
		minButton.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		minButton.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
		maxButton.setBackground(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		maxButton.setOriBgColor(Library.DEFAULT_WINDOW_AND_MENU_BACKGROUND);
		
		maxButton.setOpaque(false);
		minButton.setOpaque(false);
	    minButton.setPreferredSize(new Dimension(16,16));
		maxButton.setPreferredSize(new Dimension(16,16));
		
		minButton.setVerticalTextPosition(SwingConstants.TOP);
		maxButton.setVerticalTextPosition(SwingConstants.TOP);
		
		minButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(isMaximize == true)minimizeMode();
				super.mouseReleased(e);
			}
		});
		
		maxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(isMaximize != true)maximizeMode();
				super.mouseReleased(e);
			}
		});
		minimizeBar.add(maxButton);
		minimizeBar.add(minButton);
		minimizeBar.add(minimizeBarTitle);
		
		
		
		add(minimizeBar, BorderLayout.NORTH);
		
		add(contentPanel, BorderLayout.CENTER);
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(1,1,1,1),				
				new RoundedCornerBorder(CConstants.BORDER_CORNER_8PX_RADIUS)
				));
	}
	public void setTitle(String title){
		minimizeBarTitle.setText( "\t  "+ title);
		repaint();
	}
	/**
	 * Update current title, gathering specify that panel contain new record or update record
	 * @param extraInfo
	 */
	public void updateTitleWithStatus(boolean isNew, String extraInfo){		
		String suffix =(isNew)? "(*) - " + extraInfo: "(^) - " + extraInfo;
		if(minimizeBarTitle.getText() != null && !minimizeBarTitle.getText().contains(suffix))
			minimizeBarTitle.setText(/*minimizeBarTitle.getText() + */suffix);
		repaint();
	}
	
	public void setDockableBarLayout(LayoutManager  layout, DOCKING_DIRECTION dockingBarDriection){
		this.dockingBarDriection = dockingBarDriection;
		dockableBarLayout = layout;
	}
	private void layoutMaximize(){
		minimizeBar.remove(maxButton);		
		minimizeBar.remove(minButton);		
		minimizeBar.remove(minimizeBarTitle);
		minimizeBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		minimizeBar.add(maxButton);
		minimizeBar.add(minButton);						
		minimizeBar.add(minimizeBarTitle);
		addMinimizeBar(DOCKING_DIRECTION.North.toString());
		minimizeBar.setBorder(new CLineBorder(CConstants.TEXT_BORDER_COLOR, CLineBorder.DRAW_BOTTOM_BORDER));
	}
	private void layoutMinimize(){
		minimizeBar.remove(minButton);
		minimizeBar.remove(maxButton);
		minimizeBar.remove(minimizeBarTitle);
		minimizeBar.setLayout(dockableBarLayout);
		minimizeBar.add(maxButton);
		minimizeBar.add(minButton);					
		minimizeBar.add(minimizeBarTitle);
		addMinimizeBar(dockingBarDriection.toString());
		minimizeBar.setBorder(null);
	}
	@Override
	public void add(Component comp, Object constraints) {
		if(constraints.equals(BorderLayout.CENTER) && !checkAddToSelf(comp, contentPanel)){
			contentPanel.add(comp, BorderLayout.CENTER);		
			maximizeMode();	
			super.add(contentPanel, constraints);
		}else{
			super.add(comp, constraints);
		}		
	}
	public static boolean checkAddToSelf(Component comp, Container pCont){
		boolean ret = false;
	        if (comp instanceof Container) {
	            for (Container cn = pCont; cn != null; cn=cn.getParent()) {
	                if (cn == comp) {
	                    ret = true;
	                }
	            }
	        }
	        return ret;
	    }
	private void addMinimizeBar(String dockingDirection){
	    remove(minimizeBar);
		add(minimizeBar, dockingDirection);
	}

	public void addContent(Component c){		
		if(checkAddToSelf(c, contentPanel))
			return;
		contentPanel.add(c, BorderLayout.CENTER);		
		add(contentPanel, BorderLayout.CENTER);		
		maximizeMode();
	}
	
	public void maximizeMode() {
		remove(contentPanel);
		remove(minimizeBar);
		
		layoutMaximize();		
		add(contentPanel, BorderLayout.CENTER);
		isMaximize = true;	
		if(managerClazz != null)
			fireGUIEvent(new GUIActionEvent(managerClazz, GUIActionType.MAXIMIZE_WINDOW, null));
		if(getParent() != null){
			getParent().validate();
			getParent().repaint();			
		}
	}
	
	public void minimizeMode() {
		remove(contentPanel);
		layoutMinimize();	
		doLayout();
		getParent().doLayout();		
		revalidate();
		repaint();
		isMaximize = false;
		if(dockingBarDriection.equals(DOCKING_DIRECTION.North))
			minimizeBar.setBorder(new CLineBorder(CConstants.TEXT_BORDER_COLOR, CLineBorder.DRAW_BOTTOM_BORDER));		
		Dimension data;
		if(!dockingBarDriection.equals(DOCKING_DIRECTION.North))
			data = new Dimension(minimizeBar.getSize().width, 22);
		else
			data = new Dimension(minimizeBar.getSize().width, 25);
		if(managerClazz != null)
			fireGUIEvent(new GUIActionEvent(managerClazz, GUIActionType.MINIMIZE_WINDOW, data));
		getParent().validate();
		getParent().repaint();		
	}	
	
	public void guiActionPerformed(GUIActionEvent action) {
	}	
	
	void fireGUIEvent(GUIActionEvent action){
		for(GUIActionListener g:guiActionListenerVector)
			g.guiActionPerformed(action);		
		
	}
	public void addGUIActionListener(GUIActionListener l){
		guiActionListenerVector.add(l);
	}
	public void removeGUIActionListener(GUIActionListener l){
		guiActionListenerVector.remove(l);
	}
	
	private Vector<CActionListener> cListenerVector = new Vector<CActionListener>();
	private void fireCActionEvent(CActionEvent evt){
		for(CActionListener cActionListener:cListenerVector)
			cActionListener.cActionPerformed(evt);

	}
	public void addCActionListener(CActionListener cActionListener)
	{
		cListenerVector.add(cActionListener);
	}
	public void removeCActionListener(CActionListener cActionListener)
	{
		cListenerVector.remove(cActionListener);
	}
   
	public static void main(String[] args){
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		/*CWhitePanel dockContent = new CWhitePanel(new BorderLayout());
		dockContent.add(new JLabel(" Test test"));
		
		CTwoModePanel dockContainer = new CTwoModePanel();
		dockContainer.addContent(dockContent);
		
		dockContainer.setBackground(Color.GRAY);
		CWhitePanel container = new CWhitePanel(new BorderLayout());
		//-- test Dock Container activity
		JPanel wrapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,2,2));
		wrapPanel.add(dockContainer);		
		wrapPanel.add(new JLabel("EAST LABEL"));
		
		JPanel wrapPanelCont = new JPanel(new BorderLayout());
		wrapPanelCont.add(wrapPanel, BorderLayout.CENTER);
		container.add(wrapPanelCont, BorderLayout.NORTH);		
		
		CWhitePanel p3 = new CWhitePanel(new BorderLayout());
		p3.add(new JLabel(" Test test"));
		p3.setBackground(Color.gray);
		container.add(p3, BorderLayout.CENTER);*/
	
		//RoundedBorderComponent container = new RoundedBorderComponent();
		CTwoModePanel container = new CTwoModePanel();
		f.add(container);
		f.pack();
		f.doLayout();
		f.validate();
		f.setVisible(true);
//		dockContainer.setDockableBarLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP)
//		, DOCKING_DIRECTION.North);
//		dockContainer.setDockableBarLayout(new BorderLayout(),DOCKING_DIRECTION.North);
		
	}
	@Override
	protected void paintComponent(Graphics g) {        
        graphics = (Graphics2D) g;
        graphics.setColor(getBackground());
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        super.paintComponent(graphics);
   }
	
   @Override
   protected void paintBorder(Graphics g) {
	   graphics = (Graphics2D) g;
	   graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
   			RenderingHints.VALUE_ANTIALIAS_ON);
	   graphics.setColor(CConstants.TEXT_BORDER_COLOR);
       graphics.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
   }
   
   @Override
   public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 8, 8);
        }
        return shape.contains(x, y);
   }
   private Shape shape;
   private Graphics2D graphics;
   
   /* Define a class to recognize this two mode panel content*/
   Class managerClazz;
	
	public Class getManagerClazz() {
		return managerClazz;
	}

	public void setManagerClazz(Class managerClazz) {
		this.managerClazz = managerClazz;
	}
}
