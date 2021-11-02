$(document).ready(function() {
    $("#okButton").click(function () {
        if ($("#addNames").val() != "" && $("#costOfGoods").val() != "" && $("#costOfGoods").val() > 0 && $("#coment").val() != "") {
            // alert("$(#approve) "+$("#approve").val());
            $.ajax({
                type: "POST",
                url: "/addproductuser",
                data: ({productname: $("#addNames").val(), productcost: $("#costOfGoods").val(), productcoment: $("#coment").val()}),
                dataType: "html",
            });
        } else {
            alert("'Name product', 'Cost of goods' and 'Coment' is not filled!");
        }
        window.location.reload();
    });
});
