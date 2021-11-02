$(document).ready(function() {
    loadComplid();

    $("#okButton").click(function (addprod) {
        if ($("#addNames").val() != "" && $("#costOfGoods").val() != "" && $("#costOfGoods").val() > 0 && $("#coment").val() != "") {
            // alert("$(#approve) "+$("#approve").val());
            $.ajax({
                type: "POST",
                url: "/addproduct",
                data: ({productname: $("#addNames").val(), productcost: $("#costOfGoods").val(), productcoment: $("#coment").val(), productinitiator: $("#initiator").val()}),
                dataType: "html",
            });
        } else {
            alert("'Name product', 'Cost of goods' and 'Coment' is not filled!");
        }
        window.location.reload();
    });
});

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