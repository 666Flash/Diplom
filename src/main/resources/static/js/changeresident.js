$(document).ready(function() {
    loadComplid();

    $("#okButton").click(function () {
        if ($("#password").val() != "") {
            // alert("$(#approve) "+$("#approve").val());
            $.ajax({
                type: "POST",
                url: "/chanresident",
                data: ({residentpas: $("#password").val(), residentrights: $("#rights").val(), residentprior: $("#priority").val()}),
                dataType: "html",
            });
        } else {
            alert("Password is not filled!");
        }
        window.location.reload();
    });
});


function loadComplid() {
    $("#resident > tbody").empty();

    $.getJSON('/residentchan', function(statisticsresidentchan) {
        // alert("statisticsresidentchan["+i+"][number]"+statisticsresidentchan[1]["number"]);
        // alert("statisticsresidentchan["+i+"][name]"+statisticsresidentchan[1]["name"]);
        // alert("statisticsresidentchan["+i+"][admin]"+statisticsresidentchan[1]["admin"]);
        // alert("statisticsresidentchan["+i+"][priority]"+statisticsresidentchan[1]["priority"]);
        $('#resident > tbody:last-child').append(
            $('<tr>')
                .append($('<td>').append(statisticsresidentchan[1]["number"]))
                .append($('<td>').append(statisticsresidentchan[1]["name"]))
                .append($('<td>').append(function (){
                        if (statisticsresidentchan[1]["admin"] == 1) {
                            return "Admin";
                        } else {
                            return "User";
                        };
                    }
                ))
                .append($('<td>').append(function (){
                        if (statisticsresidentchan[1]["priority"] == 1) {
                            return "Unlocked";
                        } else {
                            return "Blocked";
                        };
                    }
                ))
        );
    });
}