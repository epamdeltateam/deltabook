function AcceptRequest() {
    document.FriendRequestForm.action="/accept_friend_request";
    document.FriendRequestForm.submit()
};
function DeclineRequest() {
    document.FriendRequestForm.action="/decline_friend_request";
    document.FriendRequestForm.submit()
};