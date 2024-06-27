package com.finalExam.cashier.service;

import com.finalExam.cashier.model.Orders;
import com.finalExam.cashier.model.Payment;
import com.finalExam.cashier.repository.OrdersRepository;
import com.finalExam.cashier.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment addPayment(Payment payment) {
        Optional<Orders> order = ordersRepository.findById(payment.getOrdersId());
        if (order.isPresent()) {
            Orders existOrder = order.get();
            existOrder.setStatus("paid");
            ordersRepository.save(existOrder);
            return paymentRepository.save(payment);

        } else {
            throw new RuntimeException("Order not found");
        }

    }

}
