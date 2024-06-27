package com.finalExam.cashier.controller;


import com.finalExam.cashier.model.Payment;
import com.finalExam.cashier.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PayService payService;

    @GetMapping
    public List<Payment> getPayments() {
        return payService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return payService.getPaymentById(id);
    }

    @PostMapping
    public Payment addPayment(@RequestBody Payment payment) {
        return payService.addPayment(payment);
    }
}
