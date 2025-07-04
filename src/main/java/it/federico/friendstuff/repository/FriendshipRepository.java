package it.federico.friendstuff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.federico.friendstuff.model.user.Friendship;
import it.federico.friendstuff.model.user.RequestFriendship;
import it.federico.friendstuff.model.user.User;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

	public Optional<Friendship> findByUserSenderAndUserReceiver(User userSender, User userReceiver);

	public Optional<Friendship> findByUserSenderAndUserReceiverAndRequest(User userSender, User userReceiver,
			RequestFriendship request);

}