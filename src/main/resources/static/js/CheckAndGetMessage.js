var old_friend_request_id = -1;
var old_message_id = -1;
var MessageText = "";
var ContactText= "";
function CheckAndGetMessage() {
    $.ajax({
        type : "GET",
        contentType : "application/json",
        url: 'get_last_message',
        data: {'idOfPreviousMessage':old_message_id},
        dataType : 'json',
        success: function (result) {
            var SendMessage = '<p><img class="very_reduced_avatar" src="data:image/png;base64,' + result.picture +  '" alt="" /></p>'  ;
            SendMessage += '<p>' + result.nickName + '</p>' ;
            SendMessage += '<p>' + result.body  + '</p>' ;
            old_message_id = result.id;
            $('#MessageForCurrentUser').html(SendMessage);
            var col = document.getElementById("MessageForCurrentUser");
            col.classList.toggle("show");
            MessageText = SendMessage;
        },
        error: function(){
            return;
        }

    });
};

function CheckAndGetFriendRequest() {
    $.ajax({
        type : "GET",
        contentType : "application/json",
        url: 'get_last_friend_request',
        data: {'idOfPreviousContact':old_friend_request_id},
        dataType : 'json',
        success: function (result) {
            var ContactMesssge = '<p><img class="very_reduced_avatar" src="data:image/png;base64,' + result.picture +  '" alt="" /></p>'  ;
            ContactMesssge += "<p>  Запрос в друзья от  " + result.friendNickname + "</p>"  ;
            ContactMesssge += "<p>" + result.requestMessage + "</p>" ;
            old_friend_request_id = result.id;
            $('#FriendRequestForCurrentUser').html(ContactMesssge);
            var col = document.getElementById("FriendRequestForCurrentUser");
            col.classList.toggle("show");
            ContactText = ContactMesssge;
        },
        error: function(){
            return;
        }

    });
};


setInterval(CheckAndGetMessage,1000);
setInterval(CheckAndGetFriendRequest,1000);

function Check() {
    //alert($(MessageForCurrentUser).is(":visible"));
    if ($(MessageForCurrentUser).is(":hidden") && MessageText != "") {
        $('#MessageForCurrentUser').html(MessageText);
        var col = document.getElementById("MessageForCurrentUser");
        col.classList.toggle("show");
    }
    if ($(FriendRequestForCurrentUser).is(":hidden") && ContactText != "") {
        $('#FriendRequestForCurrentUser').html(ContactText);
        var col = document.getElementById("FriendRequestForCurrentUser");
        col.classList.toggle("show");
    }
}
setInterval(Check,1000);