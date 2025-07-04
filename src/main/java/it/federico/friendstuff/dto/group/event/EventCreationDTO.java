package it.federico.friendstuff.dto.group.event;

import it.federico.friendstuff.dto.group.GroupNameDTO;
import jakarta.validation.constraints.NotNull;

public record EventCreationDTO(@NotNull EventDTO event, @NotNull GroupNameDTO groupName) {

}
