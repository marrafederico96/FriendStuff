package it.federico.friendstuff.model.group;

import java.time.LocalDateTime;

import it.federico.friendstuff.model.user.User;
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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "group_members", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "group_id" }))
public class GroupMember {

	@Id
	@Column(name = "group_member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupMemberId;

	@Column(name = "join_date", nullable = false)
	@NotNull
	private LocalDateTime joindDate = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	@NotNull
	private GroupRole role;

	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull
	private User user;

	@ManyToOne()
	@JoinColumn(name = "group_id", nullable = false)
	@NotNull
	private Group group;

	public Long getGroupMemberId() {
		return groupMemberId;
	}

	public LocalDateTime getJoindDate() {
		return joindDate;
	}

	public void setJoindDate(LocalDateTime joindDate) {
		this.joindDate = joindDate;
	}

	public GroupRole getRole() {
		return role;
	}

	public void setRole(GroupRole role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
