$(document).ready(function() {
    loadComplid();

    $("#changeResident").click(function (chen) {
        // alert("changeRes="+$("#changeRes").val());
        if ($("#changeRes").val() != "" && $("#changeRes").val() > 0) {
            $.ajax({
                type: "POST",
                url: "/changeresident",
                data: ({changeid: $("#changeRes").val()}),
                dataType: "html",

                // beforeSend: function () {
                //     $("#information").text("Waiting");
                // },

                success: function (rezult) {
                    // alert("rezult="+rezult);
                    if (rezult != "http://localhost:9999/")
                        window.location.href = rezult;
                }
            });
        }
    });

    $("#deletesResident").click(function (del) {
        if ($("#deletes").val() != "" && $("#deletes").val() > 0) {
            // alert("$(#deletes) "+$("#deletes").val());
            $.ajax({
                type: "POST",
                url: "/deletinresident",
                data: ({iddeletes: $("#deletes").val()}),
                dataType: "html",
            });
        }
    });

    $("#okButton").click(function (add) {
        if ($("#addNames").val() != "" && $("#password").val() != "") {
            // alert("$(#approve) "+$("#approve").val());
            $.ajax({
                type: "POST",
                url: "/addresident",
                data: ({residentname: $("#addNames").val(), residentpas: $("#password").val(), residentrights: $("#rights").val(), residentprior: $("#priority").val()}),
                dataType: "html",
            });
        } else {
            alert("Username or password is not filled!");
        }
        window.location.reload();
    });
});


function loadComplid() {
    $("#resident > tbody").empty();

    $.getJSON('/residentadmin', function(statisticsresident) {
        // alert("statisticsresident statistics "+statisticsresident);
        // alert("statisticsresident="+Object.getOwnPropertyNames(statisticsresident));
        for (let i = 1; i <= Object.keys(statisticsresident).length; i++) {
            // alert("statisticsresident["+i+"][number]"+statisticsresident[i]["number"]);
            // alert("statisticsresident["+i+"][name]"+statisticsresident[i]["name"]);
            // alert("statisticsresident["+i+"][admin]"+statisticsresident[i]["admin"]);
            // alert("statisticsresident["+i+"][priority]"+statisticsresident[i]["priority"]);
            $('#resident > tbody:last-child').append(
                $('<tr>')
                    .append($('<td>').append(statisticsresident[i]["number"]))
                    .append($('<td>').append(statisticsresident[i]["name"]))
                    .append($('<td>').append(function (){
                        if (statisticsresident[i]["admin"] == 1) {
                            return "Admin";
                        } else {
                            return "User";
                        };
                    }
                    ))
                    .append($('<td>').append(function (){
                        if (statisticsresident[i]["priority"] == 1) {
                                return "Unlocked";
                            } else {
                                return "Blocked";
                            };
                        }
                    ))
            );
        }
    });
}