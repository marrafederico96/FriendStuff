package it.federico.friendstuff.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.federico.friendstuff.dto.group.event.EventDTO;
import it.federico.friendstuff.dto.group.event.EventNameDTO;
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
	public ResponseEntity<HttpStatus> createEvent(@Valid @RequestBody EventDTO eventDTO) {
		eventService.createEvent(eventDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/delete")
	public ResponseEntity<HttpStatus> deleteEvent(@Valid @RequestBody EventNameDTO eventNameDTO) {
		eventService.deleteEvent(eventNameDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
