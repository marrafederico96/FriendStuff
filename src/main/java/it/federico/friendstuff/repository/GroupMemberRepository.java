package it.federico.friendstuff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.federico.friendstuff.model.group.GroupMember;
import it.federico.friendstuff.model.group.GroupRole;
import it.federico.friendstuff.model.user.User;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

	public Optional<GroupMember> findByUserAndRole(User user, GroupRole role);

	public Optional<GroupMember> findByUser(User user);

}
