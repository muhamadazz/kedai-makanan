let orders = [];

async function fetchOrders() {
    const response = await fetch('http://localhost:8080/order');
    orders = await response.json();

    const pendingOrdersTable = document.getElementById('pending-list');
    const paidOrdersTable = document.getElementById('paid-list');

    orders.forEach(order => {
        const row = document.createElement('tr');
        if (order.status === 'Pending') {
            row.innerHTML = `
                <td>${order.id}</td>
                <td>${order.date}</td>
                <td>${order.customer}</td>
                <td>${order.address}</td>
                <td>${order.orderItems.map(item => item.menu.name + ' (Qty: ' + item.quantity + ')').join(', ')}</td>
                <td>${order.total}</td>
                <td>${order.paymentMethod}</td>
                <td>${order.status}</td>
                <td><button onclick="editOrder(${order.id})">Edit</button></td>
            `;
            pendingOrdersTable.appendChild(row);
        } else if (order.status === 'paid') {
            row.innerHTML = `
                <td>${order.id}</td>
                <td>${order.date}</td>
                <td>${order.customer}</td>
                <td>${order.address}</td>
                <td>${order.orderItems.map(item => item.menu.name + ' (Qty: ' + item.quantity + ')').join(', ')}</td>
                <td>${order.total}</td>
                <td>${order.paymentMethod}</td>
                <td>${order.status}</td>
                <td><button onclick="deleteOrder(${order.id})">Delete</button></td>
            `;
            paidOrdersTable.appendChild(row);
        }
    });
}

function editOrder(orderId) {
    const order = orders.find(o => o.id === orderId);
    if (order) {
        document.getElementById('orderId').value = order.id;
        document.getElementById('customer').value = order.customer;
        document.getElementById('address').value = order.address;
        document.getElementById('orderItems').value = order.orderItems.map(item => item.menu.id + ':' + item.quantity).join(', ');
        document.getElementById('paymentMethod').value = order.paymentMethod;
        document.getElementById('edit-order-form').style.display = 'block';
    }
}

async function submitOrderUpdate() {
    const orderId = document.getElementById('orderId').value;
    const customer = document.getElementById('customer').value;
    const address = document.getElementById('address').value;
    const orderItemsInput = document.getElementById('orderItems').value.split(',').map(item => {
        const [menuId, quantity] = item.split(':').map(i => i.trim());
        return { menu: { id: parseInt(menuId) }, quantity: parseInt(quantity) };
    });
    const paymentMethod = document.getElementById('paymentMethod').value;

    const updatedOrder = {
        customer,
        address,
        orderItems: orderItemsInput,
        paymentMethod,
        status: 'Pending'
    };

    const response = await fetch(`http://localhost:8080/order/${orderId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedOrder)
    });

    if (response.ok) {
        alert('Order updated successfully');
        location.reload();
    } else {
        alert('Failed to update order');
    }
}

async function deleteOrder(orderId) {
    const response = await fetch(`http://localhost:8080/order/${orderId}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        alert('Order deleted successfully');
        location.reload();
    } else {
        alert('Failed to delete order');
    }
}

document.addEventListener('DOMContentLoaded', fetchOrders);
