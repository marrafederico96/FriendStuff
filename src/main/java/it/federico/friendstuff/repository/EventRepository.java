package it.federico.friendstuff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.federico.friendstuff.model.group.event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	public Optional<Event> findByEventName(String eventName);

}
