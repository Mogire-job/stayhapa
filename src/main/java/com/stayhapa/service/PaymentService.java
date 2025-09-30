package com.stayhapa.service;

import com.stayhapa.model.Payment;
import com.stayhapa.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByBookingId(Long bookingId) {
        return paymentRepository.findByBooking_Id(bookingId);
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    public List<Payment> getPaymentsByPaymentMethod(String paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setBooking(paymentDetails.getBooking());
                    payment.setAmount(paymentDetails.getAmount());
                    payment.setPaymentDate(paymentDetails.getPaymentDate());
                    payment.setPaymentMethod(paymentDetails.getPaymentMethod());
                    payment.setTransactionId(paymentDetails.getTransactionId());
                    payment.setStatus(paymentDetails.getStatus());
                    payment.setUpdatedAt(java.time.LocalDateTime.now());
                    return paymentRepository.save(payment);
                })
                .orElse(null);
    }

    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
