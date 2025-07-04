package it.federico.friendstuff.dto.group.event;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.federico.friendstuff.model.group.event.EventCategory;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventDTO(@NotBlank(message = "Event name cannot be empty") String eventName,
		@Nullable String eventDescription,
		@NotNull(message = "Event category cannot be empty") EventCategory eventCategory,
		@NotNull(message = "Start date cannot be empty") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate startDate,
		@NotNull(message = "End date cannot be empty") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate endDate,
		LocationDTO locationDTO, String groupName) {

}
