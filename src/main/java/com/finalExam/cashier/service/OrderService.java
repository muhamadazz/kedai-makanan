package com.finalExam.cashier.service;


import com.finalExam.cashier.model.Menu;
import com.finalExam.cashier.model.OrderItem;
import com.finalExam.cashier.model.Orders;
import com.finalExam.cashier.repository.MenuRepository;
import com.finalExam.cashier.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private MenuRepository menuRepository;

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders getOrderById(long id) {
        return ordersRepository.findById(id).orElse(null);
    }


    @Transactional
    public Orders addOrder(Orders order) {
        int total = 0;
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem item : orderItems) {
            Optional<Menu> menuOpt = menuRepository.findById(item.getMenu().getId());
            if (!menuOpt.isPresent()) {
                throw new RuntimeException("Menu item not found: " + item.getMenu().getId());
            }
            Menu menu = menuOpt.get();
            int quantity = item.getQuantity();

            if (menu.getStock() >= quantity) {
                menu.setStock(menu.getStock() - quantity);
                menuRepository.save(menu);

                item.setPrice(menu.getPrice() * quantity);
                total += item.getPrice();
            } else {
                throw new RuntimeException("Not enough stock for menu item: " + menu.getName());
            }
        }

        order.setTotal(total);
        return ordersRepository.save(order);
    }

    @Transactional
    public Orders updateOrder(Long orderId, Orders updatedOrder) {
        Optional<Orders> existingOrderOpt = ordersRepository.findById(orderId);

        if (!existingOrderOpt.isPresent()) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }

        Orders existingOrder = existingOrderOpt.get();
        List<OrderItem> existingItems = existingOrder.getOrderItems();
        List<OrderItem> updatedItems = updatedOrder.getOrderItems();

        // Restore stock for existing order items
        for (OrderItem item : existingItems) {
            Menu menu = item.getMenu();
            menu.setStock(menu.getStock() + item.getQuantity());
            menuRepository.save(menu);
        }

        // Clear existing order items
        existingOrder.getOrderItems().clear();
        ordersRepository.save(existingOrder);

        // Update order details and recalculate stock and total
        int total = 0;

        for (OrderItem item : updatedItems) {
            Optional<Menu> menuOpt = menuRepository.findById(item.getMenu().getId());
            if (!menuOpt.isPresent()) {
                throw new RuntimeException("Menu item not found: " + item.getMenu().getId());
            }
            Menu menu = menuOpt.get();
            int quantity = item.getQuantity();

            if (menu.getStock() >= quantity) {
                menu.setStock(menu.getStock() - quantity);
                menuRepository.save(menu);

                item.setPrice(menu.getPrice() * quantity);
                total += item.getPrice();
                existingOrder.getOrderItems().add(item);
            } else {
                throw new RuntimeException("Not enough stock for menu item: " + menu.getName());
            }
        }

        existingOrder.setDate(updatedOrder.getDate());
        existingOrder.setCustomer(updatedOrder.getCustomer());
        existingOrder.setAddress(updatedOrder.getAddress());
        existingOrder.setTotal(total);
        existingOrder.setPaymentMethod(updatedOrder.getPaymentMethod());
        existingOrder.setStatus(updatedOrder.getStatus());

        return ordersRepository.save(existingOrder);
    }


}
