function CheckAndGetMessage() {
    $.ajax({
        url: 'message_for_current_user',
        success: function(data) {
            $('#MessageForCurrentUser').html(data);
            var col=document.getElementById("MessageForCurrentUser");
        }
    });
};
setInterval(CheckAndGetMessage,1000);