package it.federico.friendstuff.dto.user;

import it.federico.friendstuff.model.user.RequestFriendship;

public record FriendshipDTO(UserInfoDTO userSender, UserInfoDTO userReceiver, RequestFriendship request) {

}
