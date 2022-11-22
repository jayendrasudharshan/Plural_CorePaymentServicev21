package com.pinelabs.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pinelabs.orchestrator.model.*;
import com.pinelabs.orchestrator.serviceimpl.IProcessPaymentService;
import com.pinelabs.orchestrator.util.JsonMasker;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/v2/process-payment")
public class ProcessPaymentController {

    @Autowired
    private JsonMasker jsonMasker;

    @Autowired
    IProcessPaymentService processPaymentService;

    private static final Logger logger = LoggerFactory.getLogger(ProcessPaymentController.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Gson gson = new Gson();

    /**
     * Process payment Card Api
     * @param processPaymentRequest
     * @return processpaymentResponse
     * @throws IOException
     * */
    @PostMapping(value="/paymentprocess" , consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessPaymentResponse paymentprocess(@ApiParam(value = "Process payment request details", required = true)@Valid @RequestHeader("X_CLIENT_ID") String X_CLIENT_ID, @Valid @RequestParam ("TOKEN") String TOKEN,@Valid @RequestBody ProcessPaymentRequest processPaymentRequest) throws IOException{
        logger.info("Process Payment Request: {}" , jsonMasker.mask(mapper.readTree(gson.toJson(processPaymentRequest))));
        return processPaymentService.processPayment(X_CLIENT_ID,TOKEN,processPaymentRequest);

    }

}
