package org.simpl.paylater;

public class IdGeneratorUtil {

    public static String generateCustomerId(int index) {
        return "cust_" + index;
    }

    public static String generateMerchantId(int index) {
        return "mer_" + index;
    }

    public static String generateTransactionId(int index) {
        return "trnx_" + index;
    }
}
