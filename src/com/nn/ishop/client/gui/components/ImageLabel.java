package com.nn.ishop.client.gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.nn.ishop.client.CActionEvent;
import com.nn.ishop.client.CActionListener;

public class ImageLabel extends JLabel {
	
	
	private static final long serialVersionUID = 49441792105124950L;
	private String iconPath;
	
	private List<CActionListener> listener = new ArrayList<CActionListener>();
	public void  addCActionListener(CActionListener al){
		listener.add(al);		
	}
	public void  removeCActionListener(CActionListener al){
		listener.remove(al);		
	}
	
	public void fireCActionEvent(CActionEvent evt){
		for(CActionListener al:listener)
			al.cActionPerformed(evt);
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public ImageLabel() {
		initIcon();
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setHorizontalAlignment(JLabel.CENTER);
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e) {
				changePicture();
			}
		});
		/*setBorder(
				BorderFactory.createCompoundBorder(
						new CLineBorder(null, CLineBorder.DRAW_ALL_BORDER),
						BorderFactory.createEmptyBorder(1,1,1,1)				
				)
		);*/
		setIconTextGap(5);
	
	}
	public void initIcon(){
		try {
			setIconPath("templates/html/images/person.png");
			setIcon(new ImageIcon(Toolkit.getDefaultToolkit()
					.getImage(iconPath)));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	void changePicture(){
		try{
			JFileChooser fileChooserDlg = new JFileChooser();
			fileChooserDlg.setCurrentDirectory(new File(iconPath).getParentFile());
			fileChooserDlg.showOpenDialog(this);
			this.iconPath = fileChooserDlg.getSelectedFile().getPath();
			
			setIcon(new ImageIcon(this.iconPath));
			System.out.println("FIRE CHANGE PICTURE EVENT");
			fireCActionEvent(new CActionEvent(ImageLabel.this,CActionEvent.UPDATE, getIconPath()));
		}
		catch(Exception ex){
			
		}
	}
	
	public ImageLabel(String text, String imgPath) {
		super(text);
		setIcon(new ImageIcon(imgPath));
		setOpaque(false);
	}
	public ImageLabel(String text) {
		super(text);
		setOpaque(false);
	}

	public ImageLabel(Icon image) {
		super(image);
		setOpaque(false);
	}

	public ImageLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		setOpaque(false);
	}

	public ImageLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		setOpaque(false);
	}

	public ImageLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		setOpaque(false);
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
	   graphics.setColor(new Color(187,187,187));
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
}
