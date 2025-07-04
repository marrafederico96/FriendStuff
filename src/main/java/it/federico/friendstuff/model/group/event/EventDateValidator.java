package it.federico.friendstuff.model.group.event;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventDateValidator implements ConstraintValidator<EventDateConstraint, Event> {

	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		return !event.getEndDate().isBefore(event.getStartDate());
	}

}
