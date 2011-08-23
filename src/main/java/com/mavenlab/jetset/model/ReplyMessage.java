package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "Chopper.ReplyMessage.emptyPrizeReply", 
			query = "SELECT replyMessage.message FROM ReplyMessage replyMessage " + 
			"WHERE replyMessage.status.status = 'prize'")
})

@Entity
@Table(name = "reply_messages")
@SequenceGenerator(
		name = "SEQ_REPLY",
		sequenceName = "sequence_reply",
		initialValue = 1,
		allocationSize = 1
)
public class ReplyMessage extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4598775750332003443L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPLY")
	private int id;
	
	@Column(name = "message", nullable = false)
	private String message;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
