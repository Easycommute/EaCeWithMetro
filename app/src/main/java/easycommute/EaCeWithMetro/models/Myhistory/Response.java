
package easycommute.EaCeWithMetro.models.Myhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("txnEntry")
    @Expose
    private TxnEntry txnEntry;
    @SerializedName("timeEntry")
    @Expose
    private String timeEntry;

    public TxnEntry getTxnEntry() {
        return txnEntry;
    }

    public void setTxnEntry(TxnEntry txnEntry) {
        this.txnEntry = txnEntry;
    }

    public String getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(String timeEntry) {
        this.timeEntry = timeEntry;
    }

}
