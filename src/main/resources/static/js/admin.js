$(document).ready(function() {
    loadComplid();
    loadNew();

    $("#yourBudget").text (function () {
        $.ajax({
            type: "POST",
            url: "/budget",
            // data: ({name: "user"}),
            // dataType: "html",

            success: function (rezultBudget) {
                $("#yourBudget").text(rezultBudget);
            }
        });
    });

    $("#yourContribution").text(function () {
        $.ajax({
            type: "POST",
            url: "/contribution",
            // data: ({name: "user"}),
            // dataType: "html",

            success: function (rezultContribution) {
                $("#yourContribution").text(rezultContribution);
            }
        });
    });

    $("#saveApprove").click(function () {
        if ($("#approve").val() != "" && $("#approve").val() > 0) {
            // alert("$(#approve) "+$("#approve").val());
            $.ajax({
                type: "POST",
                url: "/changes",
                data: ({idapprove: $("#approve").val()}),
                dataType: "html",
            });
        }
    });

    $("#deletesWish").click(function () {
        if ($("#deletes").val() != "" && $("#deletes").val() > 0) {
            // alert("$(#deletes) "+$("#deletes").val());
            $.ajax({
                type: "POST",
                url: "/deletin",
                data: ({iddeletes: $("#deletes").val()}),
                dataType: "html",
            });
        }
    });

    $("#output").click(function () {
        $.ajax({
            type: "POST",
            url: "/output",

            success: function (rezult) {
                alert("rezult="+rezult);
               window.location.href = rezult;
            }
        });
        return false
    });
});


function loadComplid() {
    $("#tovarcomplid > tbody").empty();

    $.getJSON('/tovcomplidadmin', function(statisticsadmin) {
        // alert("tovcomplid statistics "+statistics);
        // alert("tovcomplid="+Object.getOwnPropertyNames(statistics));
        for (let i = 1; i <= Object.keys(statisticsadmin).length; i++) {
            $('#tovarcomplid > tbody:last-child').append(
                $('<tr>')
                    .append($('<td>').append(statisticsadmin[i]["number"]))
                    .append($('<td>').append(statisticsadmin[i]["name"]))
                    .append($('<td>').append(statisticsadmin[i]["product"]))
                    .append($('<td>').append(statisticsadmin[i]["cost"]))
                    .append($('<td>').append(statisticsadmin[i]["consumers"]))
                    .append($('<td>').append(statisticsadmin[i]["whencreator"]))
                    .append($('<td>').append(statisticsadmin[i]["approver"]))
                    .append($('<td>').append(statisticsadmin[i]["whenapprover"]))
            );
        }
    });
}

function loadNew() {
    $("#tovarnew > tbody").empty();

    $.getJSON('/tovnewadmin', function(statisticsadmin) {
        // alert("tovnew="+Object.getOwnPropertyNames(statisticsadmin));
        // alert("tovnew []="+statistics["1"]);
        // alert("tovnew keys([])="+Object.keys(statisticsadmin["1"]));
        for (let i = 1; i <= Object.keys(statisticsadmin).length; i++) {
            // alert("tovnew number="+statisticsadmin[i]["number"]);
            // alert("tovnew name="+statisticsadmin[i]["name"]);
            // alert("tovnew product="+statisticsadmin[i]["product"]);
            // alert("tovnew cost="+statisticsadmin[i]["cost"]);
            // alert("tovnew consumers="+statisticsadmin[i]["consumers"]);
            // alert("tovnew whencreator="+statisticsadmin[i]["whencreator"]);
            $('#tovarnew > tbody:last-child').append(
                $('<tr>')
                    .append($('<td>').append(statisticsadmin[i]["number"]))
                    .append($('<td>').append(statisticsadmin[i]["name"]))
                    .append($('<td>').append(statisticsadmin[i]["product"]))
                    .append($('<td>').append(statisticsadmin[i]["cost"]))
                    .append($('<td>').append(statisticsadmin[i]["consumers"]))
                    .append($('<td>').append(statisticsadmin[i]["whencreator"]))
            );
        }
    });
}
