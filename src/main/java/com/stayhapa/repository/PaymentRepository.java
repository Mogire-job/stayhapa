package com.stayhapa.repository;

import com.stayhapa.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBooking_Id(Long bookingId);
    List<Payment> findByStatus(String status);
    List<Payment> findByPaymentMethod(String paymentMethod);
}
