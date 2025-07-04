package it.federico.friendstuff.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.federico.friendstuff.dto.group.GroupDTO;
import it.federico.friendstuff.dto.group.GroupMemberRequestDTO;
import it.federico.friendstuff.dto.group.GroupNameDTO;
import it.federico.friendstuff.service.group.GroupService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/group")
public class GroupController {

	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping("/get")
	public ResponseEntity<List<GroupDTO>> getAllGroup(Authentication authentication) {
		List<GroupDTO> groups = groupService.getAllGroup(authentication);
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createGroup(@Valid @RequestBody GroupDTO groupDTO,
			Authentication authentication) {
		groupService.createGroup(authentication, groupDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/add-member")
	public ResponseEntity<HttpStatus> addGroupMember(@RequestBody GroupMemberRequestDTO groupMemberRequestDTO) {
		groupService.addGroupMember(groupMemberRequestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/remove-member")
	public ResponseEntity<HttpStatus> removeMeber(@RequestBody GroupMemberRequestDTO groupMemberRequestDTO) {
		groupService.removeGroupMember(groupMemberRequestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<HttpStatus> deleteGroup(@RequestBody GroupNameDTO groupNameDTO) {
		groupService.deleteGroup(groupNameDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/info")
	public ResponseEntity<GroupDTO> getGroupInfo(@RequestParam String groupName) {
		return new ResponseEntity<>(groupService.findGroup(groupName), HttpStatus.OK);
	}
}
