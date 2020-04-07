
package easycommute.EaCeWithMetro.models.Myhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TxnEntry {

    @SerializedName("commuter_id")
    @Expose
    private Integer commuterId;
    @SerializedName("txn_value")
    @Expose
    private Integer txnValue;
    @SerializedName("ref_tbl_id")
    @Expose
    private Integer refTblId;
    @SerializedName("ref_key_val")
    @Expose
    private Integer refKeyVal;
    @SerializedName("txn_desc_id")
    @Expose
    private Integer txnDescId;
    @SerializedName("transaction_description")
    @Expose
    private TransactionDescription transactionDescription;

    public Integer getCommuterId() {
        return commuterId;
    }

    public void setCommuterId(Integer commuterId) {
        this.commuterId = commuterId;
    }

    public Integer getTxnValue() {
        return txnValue;
    }

    public void setTxnValue(Integer txnValue) {
        this.txnValue = txnValue;
    }

    public Integer getRefTblId() {
        return refTblId;
    }

    public void setRefTblId(Integer refTblId) {
        this.refTblId = refTblId;
    }

    public Integer getRefKeyVal() {
        return refKeyVal;
    }

    public void setRefKeyVal(Integer refKeyVal) {
        this.refKeyVal = refKeyVal;
    }

    public Integer getTxnDescId() {
        return txnDescId;
    }

    public void setTxnDescId(Integer txnDescId) {
        this.txnDescId = txnDescId;
    }

    public TransactionDescription getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(TransactionDescription transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

}
