package easycommute.EaCeWithMetro.api.data;

import com.google.gson.annotations.SerializedName;

import easycommute.EaCeWithMetro.api.data.response.ApiResponse;

/**
 * Created by Ram Prasad on 11/8/2015.
 */
public class AccountBalance {
    @SerializedName("commuter_id")
    public int commuterId;

    @SerializedName("free_rides_balance")
    public Integer freeRideBalance;

    @SerializedName("free_rides_account_message")
    public String freeRideBalanceMessage;

    @SerializedName("points_balance")
    public int availCredits;

    @SerializedName("returnCode")
    public ApiResponse.ResponseStatus status;

    @SerializedName("from_stop")
    public String fromStop;

    @SerializedName("to_stop")
    public String toStop;

    @SerializedName("every_month_payment")
    public int monthlyPayment;

    @SerializedName("net_payment")
    public int netPayment;

    @SerializedName("monthly_pass_enabled")
    public boolean isMonthlyPassEnabled;

    @SerializedName("this_month_due")
    public int due;

    @SerializedName("monthly_pass_month")
    public String monthly_pass_month;

    @SerializedName("custom_recharge_enabled")
    public Boolean custom_recharge_enabled = false;

    @SerializedName("min_recharge_amount")
    public Integer minRechargeAmount=100;

    @SerializedName("max_recharge_amount")
    public Integer maxRechargeAmount=100000;
}
