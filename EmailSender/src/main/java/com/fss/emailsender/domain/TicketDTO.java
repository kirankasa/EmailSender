package com.fss.emailsender.domain;

import java.util.Date;

public class TicketDTO {
	
	private String ticketId;
	
	private String severity;
	
	private String subject;
	
	private String summary;
	
	private String status;
	
	private String statusReason;
	
	
	
	private String customerId;
	
	private String customerName;
	
	private String customerPhone;
	
	private String customerEmail;
	
	
	
	private String assignedGroupId;
	
	private String assignedGroupName;
	
	private String assignedUserId;
	
	private String assignedUserName;
	
	
	private Date slaDueTime;
	
	
	private ConversationDTO conversationDTO;


	public String getTicketId() {
		return ticketId;
	}


	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}


	public String getSeverity() {
		return severity;
	}


	public void setSeverity(String severity) {
		this.severity = severity;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusReason() {
		return statusReason;
	}


	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getCustomerPhone() {
		return customerPhone;
	}


	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}


	public String getCustomerEmail() {
		return customerEmail;
	}


	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}


	public String getAssignedGroupId() {
		return assignedGroupId;
	}


	public void setAssignedGroupId(String assignedGroupId) {
		this.assignedGroupId = assignedGroupId;
	}


	public String getAssignedGroupName() {
		return assignedGroupName;
	}


	public void setAssignedGroupName(String assignedGroupName) {
		this.assignedGroupName = assignedGroupName;
	}


	public String getAssignedUserId() {
		return assignedUserId;
	}


	public void setAssignedUserId(String assignedUserId) {
		this.assignedUserId = assignedUserId;
	}


	public String getAssignedUserName() {
		return assignedUserName;
	}


	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}


	public Date getSlaDueTime() {
		return slaDueTime;
	}


	public void setSlaDueTime(Date slaDueTime) {
		this.slaDueTime = slaDueTime;
	}


	public ConversationDTO getConversationDTO() {
		return conversationDTO;
	}


	public void setConversationDTO(ConversationDTO conversationDTO) {
		this.conversationDTO = conversationDTO;
	}
	
	

}
