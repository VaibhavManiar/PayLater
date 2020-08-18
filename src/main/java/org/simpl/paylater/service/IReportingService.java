package org.simpl.paylater.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IReportingService {
    double getTotalDiscount(String merchantId);
    double getTotalDues(String customerId);
    Map<String, Double> getEachUserCreditLimit();
    Map<String, Double> getEachUserDues();
}
