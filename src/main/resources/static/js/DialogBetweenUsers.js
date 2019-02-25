var senderLogin = "";
var recipientLogin = "";
function SetSenderAndRecipient(Sender,Recipient) {
    senderLogin = Sender;
    recipientLogin = Recipient;
}
function json2table(json, classes) {
    var cols = Object.keys(json[0]);

    var headerRow = '';
    var bodyRows = '';

    classes = classes || '';

    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    cols.map(function(col) {
        headerRow += '<th>' + capitalizeFirstLetter(col) + '</th>';
    });

    json.map(function(row) {

        cols.map(function(colName) {
            if(colName == 'id') return;
            if(colName == 'timestamp') return;
            if(colName == 'body') {bodyRows += '<tr>' + '<td>'+ row[colName] + '</td>' + '</tr>' ; return;}
            if(colName == 'nickName') {
                bodyRows += '<tr>' + '<td>'+ '<a class="user_link" href=/' + row[colName] +'>' + row[colName] + '</a>' + '</td>' + '</tr>' ;
                return;
            }
            if(colName == 'picture') {
                bodyRows += '<tr>' + '<td>'+ '<img class="very_reduced_avatar" src="data:image/png;base64,' + row[colName] + '" alt="" />' + '</td>' + '</tr>' ;
                return;
            }
        })

    });

    return '<table class="' +
        classes +
        '"><thead><tr>' +
        '</tr></thead><tbody>' +
        bodyRows +
        '</tbody></table>';
}
function CheckAndGetMessage() {
    $.ajax({
        type : "GET",
        url: '/get_updated_dialog',
        data: {'senderLogin':senderLogin, 'recipientLogin':recipientLogin },
        dataType : 'json',
        success: function (messageList) {
            document.getElementById('messageTable').innerHTML = json2table(messageList, 'table');

            var dom = {
                data: document.getElementById('data'),
                table: document.getElementById('messageTable'),
            };

            dom.data.value = JSON.stringify(messageList);
            dom.data.addEventListener('input', function() {
                dom.table.innerHTML = json2table(JSON.parse(messageList), 'table');
            });
            return;
        },
        error: function(){
            return;
        }

    });
};

setInterval(CheckAndGetMessage,1000);
var nickName = "";
var body  = "";
function sendDataOfForm(theForm) {
    body = theForm.body;
    $.ajax({
        type: "GET",
        url: '/get_updated_dialog',
        async:false,
        data: {'senderLogin':senderLogin, 'recipientLogin':recipientLogin, 'body': body.value },
        dataType: 'json',
        success: function (messageList) {
            document.getElementById('messageTable').innerHTML = json2table(messageList, 'table');

            var dom = {
                data: document.getElementById('data'),
                table: document.getElementById('messageTable'),
            };

            dom.data.value = JSON.stringify(messageList);
            dom.data.addEventListener('input', function() {
                dom.table.innerHTML = json2table(JSON.parse(messageList), 'table');
            });
            return;
        },

        error: function () {
            return;
        }
    });
    theForm.reset();
    return ;
}