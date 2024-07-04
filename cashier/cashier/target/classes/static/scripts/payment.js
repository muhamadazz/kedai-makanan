async function fetchPayments() {
    const response = await fetch('http://localhost:8080/payment');
    const payments = await response.json();

    const paymentTable = document.getElementById('payment-list');

    payments.forEach(payment => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${payment.id}</td>
            <td>${payment.date}</td>
            <td>${payment.ordersId}</td>
            <td>${payment.paymentMethod}</td>
            <td>${payment.amount}</td>
        `;
        paymentTable.appendChild(row);
    });
}

document.addEventListener('DOMContentLoaded', fetchPayments);

document.getElementById('ordersId').addEventListener('change', async function() {
    const ordersId = this.value;
    const orderResponse = await fetch(`http://localhost:8080/order/${ordersId}`);
    if (orderResponse.ok) {
        const order = await orderResponse.json();
        document.getElementById('paymentMethod').value = order.paymentMethod;
    } else {
        document.getElementById('paymentMethod').value = '';
        alert('Order not found');
    }
});

document.getElementById('payment-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const ordersId = document.getElementById('ordersId').value;
    const paymentMethod = document.getElementById('paymentMethod').value;
    const amount = document.getElementById('amount').value;

    // Fetch the order to validate the amount
    const orderResponse = await fetch(`http://localhost:8080/order/${ordersId}`);
    if (!orderResponse.ok) {
        alert('Order not found');
        return;
    }

    const order = await orderResponse.json();
    if (order.total !== parseFloat(amount)) {
        alert('Payment amount does not match the order total');
        return;
    }

    const response = await fetch('http://localhost:8080/payment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ordersId, paymentMethod, amount }),
    });

    if (response.ok) {
        alert('Payment submitted successfully');
        location.reload(); // Reload the page to update the table
    } else {
        alert('Failed to submit payment');
    }
});
