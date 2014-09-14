package com.nn.ishop.client.gui.components;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.nn.ishop.client.gui.GUIActionEvent;
import com.nn.ishop.client.gui.GUIActionListener;
import com.nn.ishop.client.util.Library;

public class CWhitePanel extends JPanel implements GUIActionListener {

	private static final long serialVersionUID = 8540325637598875416L;

	public CWhitePanel() {
		super();
		initGUI();
	}

	public CWhitePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public CWhitePanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public CWhitePanel(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	void initGUI() {
		setBackground(Library.DEFAULT_FORM_BACKGROUND);
	}

	public void guiActionPerformed(GUIActionEvent action) {
		if (action.getAction() == GUIActionEvent.GUIActionType.MINIMIZE_WINDOW) {

		} else if (action.getAction() == GUIActionEvent.GUIActionType.MAXIMIZE_WINDOW) {

		}

	}

	/*private Graphics2D graphics;

	@Override
	protected void paintComponent(Graphics g) {
		graphics = (Graphics2D) g;
		// Paint a gradient from top to bottom
		GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255), 0,
				getHeight() - 1, new Color(178, 178, 178));
		graphics.setPaint(gp);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		//graphics.setColor(getBackground());
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		//graphics.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
		setOpaque(false);
		super.paintComponent(graphics);
		setOpaque(true);
	}*/
}
