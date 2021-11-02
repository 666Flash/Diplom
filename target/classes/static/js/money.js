$(document).ready(function() {
    loadComplid();
    loadmoneyComplid();

    $("#deletesMoney").click(function (del) {
        if ($("#deletes").val() != "" && $("#deletes").val() > 0) {
            // alert("$(#deletes) "+$("#deletes").val());
            $.ajax({
                type: "POST",
                url: "/deletinmoney",
                data: ({iddeletes: $("#deletes").val()}),
                dataType: "html",
            });
        }
    });

    $("#okButton").click(function (addprod) {
        if ($("#money").val() != "" && $("#money").val() > 0) {
            // alert("$(#approve) "+$("#approve").val());
            $.ajax({
                type: "POST",
                url: "/addmoney",
                data: ({moneycost: $("#money").val(), moneyinitiator: $("#initiator").val()}),
                dataType: "html",
            });
        } else {
            alert("Money is not filled!");
        }
        window.location.reload();
    });
});

function loadmoneyComplid() {
    $("#moneycomplid > tbody").empty();

    $.getJSON('/moneycomplid', function(statisticsadmin) {
        // alert("tovcomplid statistics "+statistics);
        // alert("tovcomplid="+Object.getOwnPropertyNames(statistics));
        for (let i = 1; i <= Object.keys(statisticsadmin).length; i++) {
            $('#moneycomplid > tbody:last-child').append(
                $('<tr>')
                    .append($('<td>').append(statisticsadmin[i]["number"]))
                    .append($('<td>').append(statisticsadmin[i]["cost"]))
                    .append($('<td>').append(statisticsadmin[i]["depositor"]))
                    .append($('<td>').append(statisticsadmin[i]["whencreator"]))
            );
        }
    });
}

function loadComplid() {
    $('#initiator').empty();

    $.getJSON('/residentadmin', function(statisticsresident) {
        // alert("statisticsresident statistics "+statisticsresident);
        // alert("statisticsresident="+Object.getOwnPropertyNames(statisticsresident));

        var select = document.getElementById("initiator");
        for (let i = 1; i <= Object.keys(statisticsresident).length; i++) {
            // alert("statisticsresident["+i+"][number]"+statisticsresident[i]["number"]);
            // alert("statisticsresident["+i+"][name]"+statisticsresident[i]["name"]);
            // alert("statisticsresident["+i+"][admin]"+statisticsresident[i]["admin"]);
            // alert("statisticsresident["+i+"][priority]"+statisticsresident[i]["priority"]);

            var opt = statisticsresident[i]["name"];
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            select.appendChild(el);
        }
    });
}
