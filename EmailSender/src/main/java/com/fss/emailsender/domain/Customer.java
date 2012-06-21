package com.fss.emailsender.domain;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Customer {

	private String name;
	private transient Map templateDetails;
	private transient TicketDTO ticketDTO;
	/*
	 * @Autowired private transient MailSender mailTemplate;
	 */

	@Autowired
	private transient JavaMailSenderImpl mailSender;

	@Autowired
	private transient Configuration configuration;
	private transient String freemarkerTemplate = "mailTemplate.html";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void iniatialize(TicketDTO ticketDTO) {
		templateDetails = new HashMap();

		// ticketInformation

		Map ticketInformation = new HashMap();
		templateDetails.put("ticketInformation", ticketInformation);
		ticketInformation.put("ticketId", ticketDTO.getTicketId());
		ticketInformation.put("severity", ticketDTO.getSeverity());
		ticketInformation.put("subject", ticketDTO.getSubject());
		ticketInformation.put("summary", ticketDTO.getSummary());
		ticketInformation.put("status", ticketDTO.getStatus());
		ticketInformation.put("statusReason", ticketDTO.getStatusReason());
	
		// customerInformation
		Map customerInformation = new HashMap();
		templateDetails.put("customerInformation", customerInformation);
		customerInformation.put("customerId", ticketDTO.getCustomerId());
		customerInformation.put("customerName", ticketDTO.getCustomerName());
		customerInformation.put("customerPhone", ticketDTO.getCustomerPhone());
		customerInformation.put("customerEmail", ticketDTO.getCustomerEmail());
		
		
		// assigneeInformation
		Map assigneeInformation = new HashMap();
		templateDetails.put("assigneeInformation", assigneeInformation);
		assigneeInformation.put("assignedGroupId",
				ticketDTO.getAssignedGroupId());
		assigneeInformation.put("assignedGroupName",
				ticketDTO.getAssignedGroupName());
		assigneeInformation
				.put("assignedUserId", ticketDTO.getAssignedUserId());
		assigneeInformation.put("assignedUserName",
				ticketDTO.getAssignedUserName());
		
		
		// slaInformation
		Map slaInformation = new HashMap();
		templateDetails.put("slaInformation", slaInformation);
		slaInformation.put("slaDueTime", ticketDTO.getSlaDueTime());
		
		
		// conversationDetail
		Map conversationDetail = new HashMap();
		templateDetails.put("conversationDetail", conversationDetail);
		conversationDetail.put("conversationDate", ticketDTO
				.getConversationDTO().getConversationDate());
		conversationDetail.put("conversationBy", ticketDTO.getConversationDTO()
				.getConversationBy());
		conversationDetail.put("conversation", ticketDTO.getConversationDTO()
				.getConversation());

	}

	public void createTicketDTO() {
		ticketDTO = new TicketDTO();
		ticketDTO.setTicketId("123456");
		ticketDTO.setSeverity("Medium");
		ticketDTO.setSubject("Test Ticket Subject");
		ticketDTO.setSummary("Test Ticket Summary ");
		ticketDTO.setStatus("Open");
		ticketDTO.setStatusReason("Open");

		ticketDTO.setCustomerId("7890");
		ticketDTO.setCustomerName("kiran");
		ticketDTO.setCustomerPhone("9940245555");
		ticketDTO.setCustomerEmail("xyz@xyz.com");

		ticketDTO.setAssignedGroupId("455355");
		ticketDTO.setAssignedGroupName("Startrek");
		ticketDTO.setAssignedUserId("35345");
		ticketDTO.setAssignedUserName("cooper");

		ticketDTO.setSlaDueTime(new Date());

		ConversationDTO conversationDTO = new ConversationDTO();
		conversationDTO.setConversation("Test Ticket Conversation ");
		conversationDTO.setConversationBy("stanley");
		conversationDTO.setConversationDate(new Date());

		ticketDTO.setConversationDTO(conversationDTO);

	}

	public void sendMessage(final String mailFrom, final String subject,
			final String mailTo, String message) {
		/*
		 * org.springframework.mail.SimpleMailMessage mailMessage = new
		 * org.springframework.mail.SimpleMailMessage();
		 * mailMessage.setFrom(mailFrom); mailMessage.setSubject(subject);
		 * mailMessage.setTo(mailTo); mailMessage.setText(message);
		 * mailTemplate.send(mailMessage);
		 */

		/*
		 * final Map root = new HashMap(); root.put("user", "Kiran Reddy"); Map
		 * latest = new HashMap(); root.put("latestProduct", latest);
		 * latest.put("url", "http://reddy.cloudfoundry.com/");
		 * latest.put("name", "My PizzaHut"); root.put("customerName",
		 * "Kiran Kumar reddy"); root.put("customerId", "12298");
		 * root.put("project", "Help desk");
		 */
		createTicketDTO();
		iniatialize(ticketDTO);
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				message.setFrom(mailFrom);
				message.setTo(mailTo);
				message.setSubject(subject);
				try {
					final String resultString = FreeMarkerTemplateUtils
							.processTemplateIntoString(configuration
									.getTemplate(freemarkerTemplate),
									templateDetails);
					message.setText(resultString, true);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}

				// to add attachment
				/*
				 * message.addInline("myLogo", new
				 * ClassPathResource("img/mylogo.gif"));
				 * message.addAttachment("myDocument.pdf", new
				 * ClassPathResource("doc/myDocument.pdf"));
				 */
			}
		});
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Customer().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCustomers() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Customer o",
				Long.class).getSingleResult();
	}

	public static List<Customer> findAllCustomers() {
		return entityManager().createQuery("SELECT o FROM Customer o",
				Customer.class).getResultList();
	}

	public static Customer findCustomer(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Customer.class, id);
	}

	public static List<Customer> findCustomerEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Customer o", Customer.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			Customer attached = Customer.findCustomer(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public Customer merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Customer merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
