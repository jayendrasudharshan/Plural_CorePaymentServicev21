package com.pinelabs.orchestrator.helpers;


import org.springframework.stereotype.Component;
@Component("pine_pg_txn_idValidat")

public class PinePgTxnIdValidation {

    public long getPine_pg_txn_id() {
        return pine_pg_txn_id;
    }

    public void setPine_pg_txn_id(long pine_pg_txn_id) {
        this.pine_pg_txn_id = pine_pg_txn_id;
    }

    public long pine_pg_txn_id;
}