var currentdata="";
function doAjax() {
    $.ajax({
        url: 'checkStrength',
        data: {password : $('#password').val()},
        success: function(data) {
            currentdata = data;
            var col=document.getElementById("strengthValue");
            if(data == "слабый") {
                col.style.color = "#ff4000";
            }
            else if(data == "средний") {
                col.style.color = "#00bfff";
            }
            else if(data == "сильный") {
                col.style.color = "#00ff00";
            }

            $('#strengthValue').html(data);

        },
        error: function () {
            return;
        }
    });
}
function Check() {
    var col = document.getElementById("myPopup");
    if ($(myPopup).is(":hidden") && currentdata != ""  ) {
        col.style.display = 'block';
        col.classList.toggle("show");
    }
    else {
        if(currentdata == "") {
            col.style.display = 'none';
            return;
        }
    }
}
setInterval(Check,1000);