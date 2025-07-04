package it.federico.friendstuff.dto.group.event;

import java.time.LocalDate;
import java.util.List;

import it.federico.friendstuff.dto.group.GroupDTO;
import it.federico.friendstuff.dto.group.event.expense.ExpenseDTO;
import it.federico.friendstuff.model.group.event.EventCategory;

public record EventResponseDTO(String eventName, String eventDescription, EventCategory category, LocalDate startDate,
        LocalDate endDate, String locationName, String city, String street, String streetNumber, GroupDTO group,
        List<ExpenseDTO> expenses) {

}
