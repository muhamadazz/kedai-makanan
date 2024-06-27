 function addToOrder(item, price, quantityId) {
    let quantity = document.getElementById(quantityId).value;
    if (quantity > 0) {
    let orderItems = [{ item, price, quantity }];
    let order = {
    customer: "Customer Name", // Replace with actual customer name
    address: "Customer Address", // Replace with actual address
    orderItems: orderItems,
    total: price * quantity,
    paymentMethod: "Cash", // Replace with actual payment method
    status: "Pending"
};

    fetch('/order', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(order)
})
    .then(response => response.json())
    .then(data => {
    alert('Order placed successfully!');
    // Optionally redirect to another page or update UI
})
    .catch(error => {
    console.error('Error placing order:', error);
    alert('Failed to place order.');
});
} else {
    alert('Please enter a valid quantity.');
}
}

