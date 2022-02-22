package org.uhc.dao.dto;

import java.util.Date;

public class CommunicationDto {

	private long messageID;
	private String comMessage;
	private Date displayStart;
	private Date displayEnd;
	private int popUp;
	private int displayOnce;
	private int priority;
	public long getMessageID() {
		return messageID;
	}
	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}
	public String getComMessage() {
		return comMessage;
	}
	public void setComMessage(String comMessage) {
		this.comMessage = comMessage;
	}
	public Date getDisplayStart() {
		return displayStart;
	}
	public void setDisplayStart(Date displayStart) {
		//DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		this.displayStart = displayStart;
	}
	public Date getDisplayEnd() {
		return displayEnd;
	}
	public void setDisplayEnd(Date displayEnd) {
		//DateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT.getValue());
		this.displayEnd = displayEnd;
	}
	public int getPopUp() {
		return popUp;
	}
	public void setPopUp(int popUp) {
		this.popUp = popUp;
	}
	public int getDisplayOnce() {
		return displayOnce;
	}
	public void setDisplayOnce(int displayOnce) {
		this.displayOnce = displayOnce;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
}
