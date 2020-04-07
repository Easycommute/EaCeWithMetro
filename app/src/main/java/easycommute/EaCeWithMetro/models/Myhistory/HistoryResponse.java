
package easycommute.EaCeWithMetro.models.Myhistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryResponse {

    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    @SerializedName("returnCode")
    @Expose
    private String returnCode;
    @SerializedName("message")
    @Expose
    private Object message;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}
