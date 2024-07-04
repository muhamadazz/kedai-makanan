document.addEventListener('DOMContentLoaded', (event) => {
    fetch('http://localhost:8080/menu')
        .then(response => response.json())
        .then(data => {
            const menuTableBody = document.querySelector('#menuTable tbody');
            data.forEach(menu => {
                const row = document.createElement('tr');
                row.innerHTML = `
                        <td>${menu.name}</td>
                        <td>${menu.description}</td>
                        <td>Rp ${menu.price.toLocaleString()}</td>
                        <td>${menu.stock}</td>
                        <td>${menu.category}</td>
                        <td><input type="checkbox" data-id="${menu.id}"></td>
                        <td><input type="number" min="1" max="${menu.stock}" disabled></td>
                    `;
                menuTableBody.appendChild(row);
            });

            const checkboxes = document.querySelectorAll('input[type="checkbox"]');
            checkboxes.forEach(checkbox => {
                checkbox.addEventListener('change', (event) => {
                    const inputNumber = checkbox.parentElement.nextElementSibling.firstChild;
                    if (checkbox.checked) {
                        inputNumber.disabled = false;
                    } else {
                        inputNumber.disabled = true;
                        inputNumber.value = '';
                    }
                });
            });
        })
        .catch(error => console.error('Error fetching menu:', error));
});

function submitOrder() {
    const customer = document.getElementById('customer').value;
    const address = document.getElementById('address').value;
    const paymentMethod = document.getElementById('paymentMethod').value;

    const orderItems = [];
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');

    checkboxes.forEach(checkbox => {
        const menuId = checkbox.getAttribute('data-id');
        const quantity = checkbox.parentElement.nextElementSibling.firstChild.value;
        if (quantity) {
            orderItems.push({
                menu: { id: parseInt(menuId) },
                quantity: parseInt(quantity)
            });
        }
    });

    if (customer && address && orderItems.length > 0) {
        const orderData = {
            customer: customer,
            address: address,
            orderItems: orderItems,
            paymentMethod: paymentMethod
        };

        fetch('http://localhost:8080/order', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        })
            .then(response => response.json())
            .then(data => {
                alert('Order submitted successfully');
                // Reset form
                document.getElementById('orderForm').reset();
                const inputs = document.querySelectorAll('input[type="number"]');
                inputs.forEach(input => input.disabled = true);
                const checkboxes = document.querySelectorAll('input[type="checkbox"]');
                checkboxes.forEach(checkbox => checkbox.checked = false);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Order submitted successfully');
            });
    } else {
        alert('Please fill all customer information and add items to the order');
    }
}