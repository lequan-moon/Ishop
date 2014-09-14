package com.nn.ishop.client.gui.dialogs;

public interface ISplashScreen {	

	public void dispose();

	public void setProgressiveValue(int progressiveValue);

	public void setComplete(boolean complete);

	public boolean isComplete();

	public void setMaxProgressBarValues(int maxProgressBarValues);

	public int getMaxProgressBarValues();

	public void setVisible(boolean visible);

	public String getSeletedProject();

	public void setStartLoading(boolean startLoading);

	public boolean isStartLoading();
}