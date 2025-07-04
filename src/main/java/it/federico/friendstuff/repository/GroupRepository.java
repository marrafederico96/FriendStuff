package it.federico.friendstuff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.federico.friendstuff.model.group.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	public Optional<Group> findBygroupName(String groupName);

}
