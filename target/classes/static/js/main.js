$(document).ready(function () {
    $("#submitButton").click(function (e) {

        $.ajax({
            type: "POST",
            url: "/entrance",
            data: ({name: $("#inputName6").val(), pasp: $("#inputPassword6").val()}),
            dataType: "html",

            // beforeSend: function () {
            //     $("#information").text("Waiting");
            // },

            success: function (rezult) {
                if (rezult == "http://localhost:9999/") {
                    alert("Incorrect login or password");
                    $("#inputName6").text("");
                    $("#inputPassword6").text("");
                    window.location.reload();
                } else {
                    window.location.href = rezult;
                }
            }
        });
        return false
    });
});