package it.federico.friendstuff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.federico.friendstuff.model.group.event.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	public Optional<Location> findByLocationName(String locationName);

}
