package it.federico.friendstuff.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.federico.friendstuff.dto.group.GroupNameDTO;
import it.federico.friendstuff.dto.group.event.EventCreationDTO;
import it.federico.friendstuff.dto.group.event.EventNameDTO;
import it.federico.friendstuff.dto.group.event.EventResponseDTO;
import it.federico.friendstuff.model.group.event.EventCategory;
import it.federico.friendstuff.service.group.event.EventService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/event")
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createEvent(@Valid @RequestBody EventCreationDTO eventCreationDTO) {
		eventService.createEvent(eventCreationDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/delete")
	public ResponseEntity<HttpStatus> deleteEvent(@Valid @RequestBody EventNameDTO eventNameDTO) {
		eventService.deleteEvent(eventNameDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/category")
	public List<String> getCategory() {
		return Arrays.stream(EventCategory.values()).map(Enum::name).toList();
	}

	@GetMapping("/get")
	public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam GroupNameDTO groupName) {
		return new ResponseEntity<>(eventService.getEvent(groupName), HttpStatus.OK);
	}
}
