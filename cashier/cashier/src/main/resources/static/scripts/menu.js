 function addToOrder(menuName, price, quantityId) {
     const quantity = document.getElementById(quantityId).value;
     if (quantity < 1) {
         alert("Please enter a valid quantity");
         return;
     }

     const menuId = quantityId.replace('quantity', '');
     fetch(`/menu/addToCart?menuId=${menuId}`, {
         method: 'POST',
     })
         .then(response => {
             if (!response.ok) {
                 throw new Error('Failed to add item to cart');
             }
             return response.json();
         })
         .then(data => {
             console.log("Item added to cart");
         })
         .catch(error => {
             console.error('Error:', error);
         });
 }


