package com.nn.ishop.client;

/**
 * <p>Title: Interface to listen to task progress reports</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: shiftTHINK Ltd.</p>
 * @author Uris Kalatchoff
 * @version 1.0
 */
public interface ProgressListener {

	/**
	 * passes progress rate to listener
	 * @param evt ProgressEvent
	 */
	public void progressStatusChanged(com.nn.ishop.client.ProgressEvent evt);
}