package it.federico.friendstuff.service.group.event;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.federico.friendstuff.dto.group.event.EventDTO;
import it.federico.friendstuff.dto.group.event.EventNameDTO;
import it.federico.friendstuff.exception.EventException;
import it.federico.friendstuff.exception.GroupException;
import it.federico.friendstuff.model.group.Group;
import it.federico.friendstuff.model.group.event.Event;
import it.federico.friendstuff.model.group.event.Location;
import it.federico.friendstuff.repository.EventRepository;
import it.federico.friendstuff.repository.GroupRepository;
import it.federico.friendstuff.repository.LocationRepository;

@Service
public class EventService {

	private final EventRepository eventRepository;
	private final LocationRepository locationRepository;
	private final GroupRepository groupRepository;

	public EventService(EventRepository eventRepository, LocationRepository locationRepository,
			GroupRepository groupRepository) {
		this.eventRepository = eventRepository;
		this.locationRepository = locationRepository;
		this.groupRepository = groupRepository;
	}

	@Transactional
	public void createEvent(EventDTO eventDTO) {

		Event event = new Event();

		Group group = groupRepository.findBygroupName(eventDTO.groupName())
				.orElseThrow(() -> new GroupException(eventDTO.groupName() + " not found"));

		event.setGroup(group);

		if (eventRepository.findByEventName(eventDTO.eventName()).isPresent()) {
			throw new EventException("Event name already exists");
		}

		if (eventDTO.locationDTO() == null || eventDTO.locationDTO().getLocationName() == null) {
			throw new EventException("Location is required");
		}

		Optional<Location> location = locationRepository.findByLocationName(eventDTO.locationDTO().getLocationName());

		if (location.isPresent()) {
			event.setLocation(location.get());
		} else {
			Location newLocation = new Location();
			newLocation.setLocationName(eventDTO.locationDTO().getLocationName());
			newLocation.setCity(eventDTO.locationDTO().getCity());
			newLocation.setStreetNumber(eventDTO.locationDTO().getStreetNumber());
			newLocation.setStreet(eventDTO.locationDTO().getStreet());
			locationRepository.save(newLocation);
			event.setLocation(newLocation);
		}

		event.setCategory(eventDTO.eventCategory());
		event.setEventName(eventDTO.eventName());
		event.setDescription(eventDTO.eventDescription());
		event.setStartDate(eventDTO.startDate());
		event.setEndDate(eventDTO.endDate());

		eventRepository.save(event);
	}

	@Transactional
	public void deleteEvent(EventNameDTO eventNameDTO) {
		Event event = eventRepository.findByEventName(eventNameDTO.eventName())
				.orElseThrow(() -> new EventException("Event not found"));

		eventRepository.delete(event);
	}

}
