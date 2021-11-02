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

    $("#deletesProduct").click(function () {
        if ($("#deletes").val() != "" && $("#deletes").val() > 0) {
            // alert("$(#deletes) "+$("#deletes").val());
            $.ajax({
                type: "POST",
                url: "/deletinprod",
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
                window.location.href = rezult;
            }
        });
    });
});


function loadComplid() {
    $("#tovarcomplid > tbody").empty();

    $.getJSON('/tovcomplid', function(statistics) {
        // alert("tovcomplid statistics "+statistics);
        // alert("tovcomplid="+Object.getOwnPropertyNames(statistics));
        for (let i = 1; i <= Object.keys(statistics).length; i++) {
            $('#tovarcomplid > tbody:last-child').append(
                $('<tr>')
                    .append($('<td>').append(statistics[i]["number"]))
                    .append($('<td>').append(statistics[i]["name"]))
                    .append($('<td>').append(statistics[i]["product"]))
                    .append($('<td>').append(statistics[i]["cost"]))
            );
        }
    });
}

function loadNew() {
    $("#tovarnew > tbody").empty();

    $.getJSON('/tovnew', function(statistics) {
        // alert("Object.keys( statistics ).length="+Object.keys(statistics).length);
        // alert("tovnew="+Object.getOwnPropertyNames(statistics));
        // alert("tovnew []="+statistics["1"]);
        // alert("tovnew keys([])="+Object.keys(statistics["1"]));
        for (let i = 1; i <= Object.keys(statistics).length; i++) {
            // alert("tovnew number="+statistics[i]["number"]);
            // alert("tovnew name="+statistics[i]["name"]);
            // alert("tovnew product="+statistics[i]["product"]);
            // alert("tovnew cost="+statistics[i]["cost"]);
            $('#tovarnew > tbody:last-child').append(
                $('<tr>')
                    .append($('<td>').append(statistics[i]["number"]))
                    .append($('<td>').append(statistics[i]["name"]))
                    .append($('<td>').append(statistics[i]["product"]))
                    .append($('<td>').append(statistics[i]["cost"]))
            );
        }
    });
}
