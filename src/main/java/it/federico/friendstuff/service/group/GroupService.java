package it.federico.friendstuff.service.group;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import it.federico.friendstuff.dto.group.GroupDTO;
import it.federico.friendstuff.dto.group.GroupMemberRequestDTO;
import it.federico.friendstuff.dto.group.GroupNameDTO;
import it.federico.friendstuff.exception.GroupException;
import it.federico.friendstuff.model.group.Group;
import it.federico.friendstuff.model.group.GroupMember;
import it.federico.friendstuff.model.group.GroupRole;
import it.federico.friendstuff.model.user.User;
import it.federico.friendstuff.repository.GroupMemberRepository;
import it.federico.friendstuff.repository.GroupRepository;
import it.federico.friendstuff.repository.UserRepository;
import jakarta.validation.Valid;

@Service
public class GroupService {

	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final UserRepository userRepository;

	public GroupService(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository,
			UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.groupMemberRepository = groupMemberRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public GroupDTO createGroup(Authentication authentication, @Valid @RequestBody GroupDTO groupDTO) {
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(authentication.getName() + " not found"));

		if (groupRepository.findBygroupName(groupDTO.getGroupName()).isPresent()) {
			throw new GroupException("Group already exists");
		}

		Group group = new Group();
		group.setGroupName(groupDTO.getGroupName());
		group.setDescription(groupDTO.getGroupDescription());

		GroupMember groupMembers = new GroupMember();
		groupMembers.setGroup(group);
		groupMembers.setUser(user);
		groupMembers.setRole(GroupRole.ADMIN);

		groupRepository.save(group);
		groupMemberRepository.save(groupMembers);

		return groupDTO;

	}

	@Transactional
	public List<GroupDTO> getAllGroup(Authentication authentication) {
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		List<GroupMember> groupList = user.getGroupMembership();
		return groupList.stream().map(GroupDTO::toDTO).collect(Collectors.toList());
	}

	@Transactional
	public void addGroupMember(GroupMemberRequestDTO groupMemberRequestDTO) {
		User user = userRepository.findByUsername(groupMemberRequestDTO.username())
				.orElseThrow(() -> new UsernameNotFoundException(groupMemberRequestDTO.username() + " not found."));
		Group group = groupRepository.findBygroupName(groupMemberRequestDTO.groupName())
				.orElseThrow(() -> new GroupException(groupMemberRequestDTO.groupName() + " not found"));

		List<GroupMember> groupMembersList = group.getGroupMembers();

		boolean exists = groupMembersList.stream()
				.anyMatch(u -> u.getUser().getUsername().equals(groupMemberRequestDTO.username()));

		if (exists) {
			throw new GroupException(groupMemberRequestDTO.username() + " already present");
		}

		GroupMember groupMember = new GroupMember();
		groupMember.setGroup(group);
		groupMember.setUser(user);
		groupMember.setRole(GroupRole.MEMBER);

		groupMemberRepository.save(groupMember);

	}

	@Transactional
	public void removeGroupMember(GroupMemberRequestDTO groupMemberRequestDTO) {
		User user = userRepository.findByUsername(groupMemberRequestDTO.username())
				.orElseThrow(() -> new UsernameNotFoundException(groupMemberRequestDTO.username() + " not found."));
		Group group = groupRepository.findBygroupName(groupMemberRequestDTO.groupName())
				.orElseThrow(() -> new GroupException(groupMemberRequestDTO.groupName() + " not found"));

		List<GroupMember> groupMembersList = group.getGroupMembers();

		boolean exists = groupMembersList.stream()
				.anyMatch(u -> u.getUser().getUsername().equals(groupMemberRequestDTO.username()));

		if (!exists) {
			throw new GroupException(groupMemberRequestDTO.username() + " not present");
		}

		GroupMember groupMember = groupMemberRepository.findByUser(user)
				.orElseThrow(() -> new GroupException("Group Member not found"));
		groupMemberRepository.delete(groupMember);

	}

	@Transactional
	public void deleteGroup(GroupNameDTO groupNameDTO) {
		Group group = groupRepository.findBygroupName(groupNameDTO.groupName())
				.orElseThrow(() -> new GroupException(groupNameDTO.groupName() + " not found"));

		groupRepository.delete(group);
	}

	@Transactional
	public GroupDTO findGroup(String groupName) {
		Group group = groupRepository.findBygroupName(groupName)
				.orElseThrow(() -> new GroupException("Gruppo non trovato"));
		GroupDTO groupInfo = new GroupDTO(group.getGroupName(), group.getDescription());
		return groupInfo;
	}

}
