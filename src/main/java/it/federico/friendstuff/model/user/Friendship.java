package it.federico.friendstuff.model.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name= "friendships")
public class Friendship {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friendship_id", nullable = false, unique = true)
	private Long friendshipId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "request_status", nullable = false)
	@NotNull
	private RequestFriendship request;
	
	@Column(name = "request_date", nullable=false)
	@NotNull
	private LocalDateTime requestDate = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name = "user_sender", nullable=false)
	private User userSender;
	
	@ManyToOne
	@JoinColumn(name = "user_receiver", nullable=false)
	private User userReceiver;
	
	public Friendship() {}

	public Long getFriendshipId() {
		return friendshipId;
	}

	public RequestFriendship getRequest() {
		return request;
	}

	public User getUserSender() {
		return userSender;
	}

	public User getUserReceiver() {
		return userReceiver;
	}

	public void setRequest(RequestFriendship request) {
		this.request = request;
	}

	public void setUserSender(User userSender) {
		this.userSender = userSender;
	}

	public void setUserReceiver(User userReceiver) {
		this.userReceiver = userReceiver;
	}

}
