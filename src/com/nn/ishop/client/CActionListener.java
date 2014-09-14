package com.nn.ishop.client;



public interface CActionListener {
	public void addCActionListener(CActionListener al);
	public void removeCActionListener(CActionListener al);
	/**
	 * Perform following flow
	 * 1. Perform local task
	 * 2. Call other listeners for not in duty tasks
	 * @param action
	 */
	public void cActionPerformed(com.nn.ishop.client.CActionEvent action);
	
	
}