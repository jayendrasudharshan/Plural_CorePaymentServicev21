package com.pinelabs.orchestrator.serviceimpl;


import com.pinelabs.orchestrator.model.ProcessPaymentRequest;
import com.pinelabs.orchestrator.model.ProcessPaymentResponse;

public interface IProcessPaymentService {
    ProcessPaymentResponse processPayment( String X_CLIENT_ID ,String TOKEN,ProcessPaymentRequest processPaymentRequest);

}
