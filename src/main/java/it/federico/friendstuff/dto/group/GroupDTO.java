package it.federico.friendstuff.dto.group;

import it.federico.friendstuff.model.group.GroupMember;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public class GroupDTO {

	@NotBlank(message = "Group name cannot be empty")
	private String groupName;

	@Nullable
	private String groupDescription;

	public GroupDTO() {
	}

	public GroupDTO(String groupName, String groupDescription) {
		this.groupDescription = groupDescription;
		this.groupName = groupName;
	}

	public static GroupDTO toDTO(GroupMember groupMember) {
		GroupDTO dto = new GroupDTO();
		dto.setGroupName(groupMember.getGroup().getGroupName());
		dto.setGroupDescription(groupMember.getGroup().getDescription());
		return dto;
	}

	public GroupDTO(String groupName) {
		this.groupName = groupName;
		this.groupDescription = "";
	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
}
