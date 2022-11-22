package com.pinelabs.orchestrator.serviceimpl;


import com.pinelabs.orchestrator.helpers.*;
import com.pinelabs.orchestrator.model.OrchestratorProcessRequest;
import com.pinelabs.orchestrator.model.ProcessPaymentRequest;
import com.pinelabs.orchestrator.model.ProcessPaymentResponse;
import com.pinelabs.orchestrator.util.HeaderUtils;
import com.pinelabs.orchestrator.util.OrchestratorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ProcessPaymentService implements IProcessPaymentService {

    @Autowired
    private PinePGTxnIdFromToken pinePGTxID;

    @Autowired
    private NavigationMode navigation;

    @Autowired
    private PinePgTxnIdValidation pine_pg_txn_idValidat;

    @Autowired
   private ProcessPaymentHelper processPaymentHelper;

    @Autowired
    private ProcessPaymentValidationHandler processPaymentValidationHandler;



    @Override

    public ProcessPaymentResponse processPayment(String X_CLIENT_ID, String Token, ProcessPaymentRequest processPaymentRequest) {
        String llPinePgTxnId;

        llPinePgTxnId = pinePGTxID.getIDfromtoken(Token);

        OrchestratorProcessRequest orchestratorProcessRequest = new OrchestratorProcessRequest();
        orchestratorProcessRequest.setPine_pg_txn_id(llPinePgTxnId);

        if(X_CLIENT_ID.equals(OrchestratorConstants.X_CLIENT_ID)){
            //is plural txns
            orchestratorProcessRequest.setPluralTxn(true);
        }

    }
















        /*  long llPinePgTxnId = 0;

        try {
            llPinePgTxnId = paymentBusinessLayerService.getPinePgTxnIdFromToken(token);

            if (processPaymentRequest == null) {
                throw new Exception("objIncomingPaymentData is null");
            }

            ProcessPaymentRequest processPaymentRequest = new ProcessPaymentRequest();
            processPaymentRequest.setPine_pg_txn_id(llPinePgTxnId);

            if (Request.Headers.Contains(Constants.X_CLIENT_ID)) {
                String xClientId = Request.Headers.GetValues(Constants.X_CLIENT_ID).FirstOrDefault();
                if (xClientIdForPlural.Equals(xClientId, StringComparison.OrdinalIgnoreCase))
                {
                    m_strLogMessage = m_strLogMessage.Append("[PinePGPaymentController] [ProcessPayment] This is the plural tansaction");
                    LogMessage.LogDebugMessage(m_strLogMessage);
                    processPaymentRequest.isPluralTxn = true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
            */

        return null;



}
