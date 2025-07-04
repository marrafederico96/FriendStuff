package it.federico.friendstuff.service.group.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.federico.friendstuff.dto.group.GroupDTO;
import it.federico.friendstuff.dto.group.GroupNameDTO;
import it.federico.friendstuff.dto.group.event.EventCreationDTO;
import it.federico.friendstuff.dto.group.event.EventNameDTO;
import it.federico.friendstuff.dto.group.event.EventResponseDTO;
import it.federico.friendstuff.dto.group.event.expense.ExpenseDTO;
import it.federico.friendstuff.exception.EventException;
import it.federico.friendstuff.exception.GroupException;
import it.federico.friendstuff.model.group.Group;
import it.federico.friendstuff.model.group.event.Event;
import it.federico.friendstuff.model.group.event.Location;
import it.federico.friendstuff.model.group.event.expense.Expense;
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
	public void createEvent(EventCreationDTO eventCreationDTO) {

		Event event = new Event();

		Group group = groupRepository.findBygroupName(eventCreationDTO.groupName().groupName())
				.orElseThrow(() -> new GroupException(eventCreationDTO.groupName().groupName() + " not found"));

		event.setGroup(group);

		if (eventCreationDTO.event().locationName().toLowerCase() == null) {
			throw new EventException("Location name is required");
		}

		Optional<Location> location = locationRepository.findByLocationName(eventCreationDTO.event().locationName());

		if (location.isPresent()) {
			event.setLocation(location.get());
		} else {
			Location newLocation = new Location();
			newLocation.setLocationName(eventCreationDTO.event().locationName().toLowerCase());
			newLocation.setCity(eventCreationDTO.event().city().trim());
			newLocation.setStreetNumber(eventCreationDTO.event().streetNumber().trim());
			newLocation.setStreet(eventCreationDTO.event().street().trim());
			locationRepository.save(newLocation);
			event.setLocation(newLocation);
		}

		event.setCategory(eventCreationDTO.event().eventCategory());
		event.setEventName(eventCreationDTO.event().eventName().trim());
		event.setDescription(eventCreationDTO.event().eventDescription());
		event.setStartDate(eventCreationDTO.event().startDate());
		event.setEndDate(eventCreationDTO.event().endDate());

		eventRepository.save(event);
	}

	@Transactional
	public void deleteEvent(EventNameDTO eventNameDTO) {
		Event event = eventRepository.findByEventName(eventNameDTO.eventName())
				.orElseThrow(() -> new EventException("Event not found"));

		eventRepository.delete(event);
	}

	@Transactional
	public List<EventResponseDTO> getEvent(GroupNameDTO groupNameDTO) {

		List<EventResponseDTO> eventResponse = new ArrayList<>();

		Group group = groupRepository.findBygroupName(groupNameDTO.groupName())
				.orElseThrow(() -> new GroupException("Group not found"));

		GroupDTO groupDTO = new GroupDTO(group.getGroupName(), group.getDescription());

		group.getEventList().stream().forEach(e -> {
			List<ExpenseDTO> expenseListDTO = mapExpenseToDTO(e.getExpenseList());
			EventResponseDTO eventResponseDTO = new EventResponseDTO(e.getEventName(), e.getDescription(),
					e.getCategory(), e.getStartDate(), e.getEndDate(), e.getLocation().getLocationName(),
					e.getLocation().getCity(), e.getLocation().getStreet(), e.getLocation().getStreetNumber(), groupDTO,
					expenseListDTO);
			eventResponse.add(eventResponseDTO);
		});
		return eventResponse;
	}

	public List<ExpenseDTO> mapExpenseToDTO(List<Expense> expenseList) {

		if (expenseList == null || expenseList.isEmpty()) {
			return new ArrayList<>();
		}

		return expenseList.stream()
				.map(expense -> new ExpenseDTO(expense.getExpenseName(), expense.getAmount(),
						expense.getExpenseDescription(), expense.getExpenseDate(), expense.isExpenseClose(),
						expense.getExpenseContributions()))
				.collect(Collectors.toList());
	}
}
