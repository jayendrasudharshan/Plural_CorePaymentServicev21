package com.pinelabs.orchestrator.helpers;



import org.springframework.stereotype.Component;

@Component("pinePGTxID")
public class PinePGTxnIdFromToken {

    //

    private String PinePGTXNID;

    private String Pine_Pg_TXN_String;

    public String getIDfromtoken(String Token) {

        if (Token == null) {
            try {
                throw new Exception("Token is empty ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Token extration
            //llPinePgTxnId = Convert.ToInt64(Utils.GetPinePgTnxIdFromToken(PinePgTxnIdTimeStamp));
        }
        return PinePGTXNID;
    }

}