package com.pinelabs.orchestrator.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrchestratorProcessRequest {

    private String pine_pg_txn_id;

    private String merchant_id;

    private boolean isPluralTxn;
}
