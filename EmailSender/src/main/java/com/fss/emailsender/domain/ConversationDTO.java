package com.fss.emailsender.domain;

import java.util.Date;

public class ConversationDTO {
	
	private Date conversationDate;
	
	private String conversationBy;
	
	private String conversation;

	public Date getConversationDate() {
		return conversationDate;
	}

	public void setConversationDate(Date conversationDate) {
		this.conversationDate = conversationDate;
	}

	public String getConversationBy() {
		return conversationBy;
	}

	public void setConversationBy(String conversationBy) {
		this.conversationBy = conversationBy;
	}

	public String getConversation() {
		return conversation;
	}

	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	

}
