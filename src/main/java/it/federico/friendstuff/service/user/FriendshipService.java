package it.federico.friendstuff.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.federico.friendstuff.dto.user.FriendshipUserReceiverDTO;
import it.federico.friendstuff.exception.FriendshipException;
import it.federico.friendstuff.model.user.Friendship;
import it.federico.friendstuff.model.user.RequestFriendship;
import it.federico.friendstuff.model.user.User;
import it.federico.friendstuff.repository.FriendshipRepository;
import it.federico.friendstuff.repository.UserRepository;

@Service
public class FriendshipService {

	private final FriendshipRepository friendshipRepository;
	private final UserRepository userRepository;

	public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
		this.friendshipRepository = friendshipRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public void addFriend(FriendshipUserReceiverDTO username, Authentication authentication) {
		User userReceiver = userRepository.findByUsername(username.username())
				.orElseThrow(() -> new UsernameNotFoundException("User" + username.username() + " not found"));
		User userSender = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("user" + authentication.getName() + " not found"));

		if (friendshipRepository.findByUserSenderAndUserReceiver(userSender, userReceiver).isPresent()
				|| friendshipRepository.findByUserSenderAndUserReceiver(userReceiver, userSender).isPresent()) {
			throw new FriendshipException("User already friends");
		}

		Friendship friendship = new Friendship();
		friendship.setUserReceiver(userReceiver);
		friendship.setUserSender(userSender);
		friendship.setRequest(RequestFriendship.PENDING);

		friendshipRepository.save(friendship);
	}

	@Transactional
	public void acceptFriendship(FriendshipUserReceiverDTO username, Authentication authentication) {
		User userSender = userRepository.findByUsername(username.username())
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

		User userReceiver = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("user" + authentication.getName() + " not found"));

		Friendship friendship = friendshipRepository
				.findByUserSenderAndUserReceiverAndRequest(userReceiver, userSender, RequestFriendship.PENDING)
				.orElseThrow(() -> new FriendshipException("Friendship not found"));

		friendship.setRequest(RequestFriendship.ACCEPTED);
		friendshipRepository.save(friendship);
	}

	@Transactional
	public void rejectFriendship(FriendshipUserReceiverDTO username, Authentication authentication) {
		User userSender = userRepository.findByUsername(username.username())
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

		User userReceiver = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("user" + authentication.getName() + " not found"));

		Friendship friendship = friendshipRepository
				.findByUserSenderAndUserReceiverAndRequest(userReceiver, userSender, RequestFriendship.PENDING)
				.orElseThrow(() -> new FriendshipException("Friendship not found"));

		friendship.setRequest(RequestFriendship.DENIED);
		friendshipRepository.save(friendship);
	}

}
