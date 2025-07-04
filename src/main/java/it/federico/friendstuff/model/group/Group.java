package it.federico.friendstuff.model.group;

import java.time.LocalDateTime;
import java.util.List;

import it.federico.friendstuff.model.group.event.Event;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user_groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id", nullable = false, unique = true)
	private Long groupId;

	@Column(name = "group_name", nullable = false)
	@NotBlank
	private String groupName;

	@Column(name = "group_description")
	private String description;

	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate = LocalDateTime.now();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GroupMember> groupMembers;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Event> eventList;

	public Group() {
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public Long getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

}
