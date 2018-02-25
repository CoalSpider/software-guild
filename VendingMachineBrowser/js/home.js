var moneyInMachine = 0.00;
const DOLLAR = 1.00;
const QUARTER = 0.25;
const DIME = 0.10;
const NICKLE = 0.05;
const PENNY = 0.01;
$(document).ready(function () {
    loadItems();

    var moneyInput = $("#moneyInput");
    function addMoney(amount) {
        moneyInMachine += amount;
        moneyInput.val(moneyInMachine.toFixed(2));
    }
    $("#addDollar").click(function () {
        addMoney(DOLLAR);
    });
    $("#addQuarter").click(function () {
        addMoney(QUARTER);
    });
    $("#addDime").click(function () {
        addMoney(DIME);
    });
    $("#addNickle").click(function () {
        addMoney(NICKLE);
    });
    $("#makePurchase").click(function () {
        vendItem($("#itemChoice").val(), moneyInMachine);
    });
    $("#changeReturn").click(function () {
        var quarters = Math.round(moneyInMachine / QUARTER);
        moneyInMachine %= QUARTER;
        var dimes = Math.round(moneyInMachine / DIME);
        moneyInMachine %= DIME;
        var nickles = Math.round(moneyInMachine / NICKLE);
        moneyInMachine %= NICKLE;
        var pennies = Math.round(moneyInMachine);
        $("#changeInput").val(
            (quarters > 0 ? quarters +" quarters ": "") +
            (dimes > 0 ? dimes +" dimes " : "") +
            (nickles > 0 ? nickles +" nickles " : "") +
            (pennies > 0 ? pennies +" pennies " : "")
        );

        moneyInMachine = 0.00;
        // clear stuff
        $("#moneyInput").val("");
        $("#messageInput").val("");
        $("#itemChoice").val("");
    });
});
function vendItem(id, amount) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/money/" + amount + "/item/" + id,
        success: function (data, status) {
            $("#changeInput").val(
                (data.quarters > 0 ? data.quarters+" quarters " : "") +
                (data.dimes > 0 ? data.dimes +" dimes ": "") +
                (data.nickels > 0 ? data.nickels+" nickles " : "") +
                (data.pennies > 0 ? data.pennies+" pennies " : "")
            );

            $("#messageInput").val("Thank You!");
            // clear money input
            moneyInMachine = 0.00;
            $("#moneyInput").val("");
            // reload items to update quantities
            clearItems();
            loadItems();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#messageInput").val(jqXHR.responseJSON.message);
        }
    });
}
function clearItems() {
    $("#itemRow").empty();
}
function loadItems() {
    clearItems();
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/items",
        success: function (data, status) {
            $.each(data, function (index, item) {
                var id = item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                buildItem(id, name, price, quantity);
            });
        },
        error: function () {
            console.log("errrrrrrrrrrrrrrrrrrrrrrrrrrror");
        }
    });
}

function buildItem(id, name, price, quantity) {
    $("#itemRow").append(
        '<div class="col item" id=' + id + '>' +
        '<div class="row itemPart itemNum">' + id + '</div>' +
        '<div class="row itemPart">' + name + '</div>' +
        '<div class="row itemPart">' + price + '</div>' +
        '<div class="row itemPart itemQuantity">Quantity Left: ' + quantity + '</div>' +
        '</div>'
    );
    $("#" + id).click(function () {
        $("#itemChoice").val(id);
    });
}