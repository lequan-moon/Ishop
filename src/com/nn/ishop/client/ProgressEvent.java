package com.nn.ishop.client;

import java.util.EventObject;

/**
 * <p>Title: Connect Standalone Client</p>
 * <p>Description: This class represents change event for entity</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: shiftTHINK Ltd.</p>
 * @author Uris Kalatchoff
 * @version 1.0
 */
public class ProgressEvent extends EventObject {
	private static final long serialVersionUID = -239371935680868022L;
	float progressRate;
	String progressMessage;
	boolean isInfinitive = true;

	public boolean isInfinitive() {
		return isInfinitive;
	}

	public void setProgressMessage(String progressMessage) {
		this.progressMessage = progressMessage;
	}

	public ProgressEvent(Object source, float progressRate, String msg) {
		super(source);
		this.progressMessage = msg;
	}
	
	public ProgressEvent(Object source, float progressRate, String msg, boolean infinitive) {
		super(source);
		this.progressMessage = msg;
		this.isInfinitive = infinitive;
	}

	/**
	 * Constructs a ProgressEvent indicating progress status
	 * @param source an instance of EventObject
	 * @param progressRate float
	 */
	public ProgressEvent(Object source, float progressRate) {
		super(source);
	}

	/**
	 * gets the source that caused the event
	 * @return String
	 */
	public void setSource(Object src) {
		throw new UnsupportedOperationException();
	}

	public float getProgressRate() {
		return this.progressRate;
	}

	public String getProgressMessage() {
		return this.progressMessage;
	}
}