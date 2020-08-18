package org.simpl.paylater.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BuyRequest {
    private final String product;
    private final String userId;
    private final String merchantId;
    private final double amount;

    @JsonCreator
    public BuyRequest(@JsonProperty("product") String product,
                      @JsonProperty("userId") String userId,
                      @JsonProperty("merchantId") String merchantId,
                      @JsonProperty("amount") double amount) {
        this.product = product;
        this.userId = userId;
        this.merchantId = merchantId;
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public String getUserId() {
        return userId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public double getAmount() {
        return amount;
    }
}
