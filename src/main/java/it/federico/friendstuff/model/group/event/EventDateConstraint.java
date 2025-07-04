package it.federico.friendstuff.model.group.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EventDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventDateConstraint {
	String message() default "End date cannot be earlier than start date.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
