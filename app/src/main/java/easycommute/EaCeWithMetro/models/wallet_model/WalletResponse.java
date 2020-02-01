package easycommute.EaCeWithMetro.models.wallet_model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WalletResponse
{
    @SerializedName("accountBalance")
    private String accountBalance;

    @SerializedName("numButtonsPerRow")
    private String numButtonsPerRow;

    @SerializedName("optionsList")
    public List<RechargePack> rechargePacks;

    public HashMap<String,String> rpOrderMap;

    public List<RechargePack> getRechargePacks() {
        return rechargePacks;
    }

    public void setRechargePacks(List<RechargePack> rechargePacks) {
        this.rechargePacks = rechargePacks;
    }

    public HashMap<String, String> getRpOrderMap() {
        return rpOrderMap;
    }

    public void setRpOrderMap(HashMap<String, String> rpOrderMap) {
        this.rpOrderMap = rpOrderMap;
    }


    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getNumButtonsPerRow() {
        return numButtonsPerRow;
    }

    public void setNumButtonsPerRow(String numButtonsPerRow) {
        this.numButtonsPerRow = numButtonsPerRow;
    }
}

