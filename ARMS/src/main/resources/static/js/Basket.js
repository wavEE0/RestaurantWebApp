var removeItemButtons = document.getElementsByClassName('remove_button')
updateBasketTotal()
for(var i =0;i<removeItemButtons.length;i++){
    var removeItemButton = removeItemButtons[i]
    removeItemButton.addEventListener('click', function(event){
        var buttonClicked = event.target
        buttonClicked.parentElement.parentElement.parentElement.remove()
        updateBasketTotal()
    })
}
var incrementButtons = document.getElementsByClassName('increment_button')
for(var i =0;i<incrementButtons.length;i++){
    var incrementButton = incrementButtons[i]
    incrementButton.addEventListener('click',increment_quantity)
}
var decrementButtons = document.getElementsByClassName('decrement_button')
for(var i =0;i<decrementButtons.length;i++){
    var decrementButton = decrementButtons[i]
    decrementButton.addEventListener('click',decrement_quantity)
}

function increment_quantity(event){
    var button = event.target
    var  valcenter = button.parentElement
    var quantity = parseFloat(valcenter.getElementsByClassName("quantity121")[0].innerHTML)
    quantity++
    valcenter.getElementsByClassName("quantity121")[0].innerHTML = quantity
    updateBasketTotal()
}
function decrement_quantity(event){
    var button = event.target
    var  valcenter = button.parentElement
    var quantity = parseFloat(valcenter.getElementsByClassName("quantity121")[0].innerHTML)
    if(quantity<=1){

    }else{
        quantity--
    valcenter.getElementsByClassName("quantity121")[0].innerHTML = quantity
    updateBasketTotal()
    }
    
}
function updateBasketTotal(){
    var total =0
    var items = document.getElementsByClassName('list_item')
    for(var i =0;i<items.length;i++){
        var item = items[i]
        var elementPrice =item.getElementsByClassName('total_price121')[0]
        var quantity = item.getElementsByClassName('quantity121')[0]
        var price = parseFloat(elementPrice.innerHTML*quantity.innerHTML)
        // console.log(price)
        // total = checkoutInfo.getElementsByClassName('subtotal')[0].innerHTML 
        total +=price 
        // console.log(elementPrice.innerHTML,quantity.innerHTML,price,total)
    }
    Total_after_all_fees = total+3.5
    total =parseFloat(total).toPrecision(2)
    // console.log(total)
    var checkoutInfo = item.parentElement.parentElement.parentElement
    checkoutInfo.getElementsByClassName('subtotal')[0].innerHTML = total
    
    Total_after_all_fees=parseFloat(Total_after_all_fees).toFixed(2)
    // Total_after_all_fees = parseFloat(Total_after_all_fees).toPrecision(2)
    checkoutInfo.getElementsByClassName('Total_after_all_fees')[0].innerHTML = Total_after_all_fees
}


// var unit_price = 1.6;
// var quantity;
// var itemTotal;
// var arr;


// function something(i){
//     var total_price = quantity*unit_price;
//     arr[i] = document.getElementsByClassName("total_price121")[i].innerHTML =total_price.toFixed(2);
// }

// function item_subtotal(){
//     itemTotal = document.getElementsByClassName("total_price121")[0].innerHTML+document.getElementsByClassName("total_price121")[1].innerHTML+document.getElementsByClassName("total_price121")[2].innerHTML;
//     document.getElementsByClassName("subtotal")[0].innerHTML = itemTotal;
// }
// function remove_item(i){
//     var div1 = document.getElementsByClassName("image")[i];
//     var div2 = document.getElementsByClassName("details")[i];
//     var div3 = document.getElementsByClassName("quantity")[i];
//     var div4 = document.getElementsByClassName("unit_price")[i];
//     var div5 = document.getElementsByClassName("cross")[i];
//     div1.remove();
//     div2.remove();
//     div3.remove();
//     div4.remove();
//     div5.remove();
// }
function add_item(){

}
var checkout_button = document.getElementById("checkout_button111");
var close_button = document.getElementById("cross101");
var div = document.getElementsByClassName("popup_menu");
checkout_button.onclick = function() {
    div.style.display = 'block';
}
close_button.onclick = function() {
    div.style.display ='none';
}

