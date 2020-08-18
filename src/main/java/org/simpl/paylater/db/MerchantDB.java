package org.simpl.paylater.db;

import org.simpl.paylater.IdGeneratorUtil;
import org.simpl.paylater.pojo.Merchant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class MerchantDB implements IMerchantDB {

    private final Map<String, Merchant> merchantStore;
    private AtomicInteger index;

    @Value("${simpl.merchant.min.discount.percentage}")
    private double discountPercentage;

    public MerchantDB() {
        this.merchantStore = new ConcurrentHashMap<>();
        this.index = new AtomicInteger(1);
        this.init();
    }

    private void init() {
        List<Merchant> merchants = new ArrayList<>();
        merchants.add(new Merchant(IdGeneratorUtil.generateMerchantId(this.index.getAndIncrement()),
                "Mer1", discountPercentage));
        merchants.add(new Merchant(IdGeneratorUtil.generateMerchantId(this.index.getAndIncrement()),
                "Mer2", discountPercentage));
        merchants.add(new Merchant(IdGeneratorUtil.generateMerchantId(this.index.getAndIncrement()),
                "Mer3", discountPercentage));
        merchants.add(new Merchant(IdGeneratorUtil.generateMerchantId(this.index.getAndIncrement()),
                "Mer4", discountPercentage));


        this.merchantStore.putAll(merchants.stream().collect(Collectors.toMap(Merchant::getId, merchant -> merchant)));
    }

    @Override
    public void add(Merchant merchant) {
        this.merchantStore.put(merchant.getId(), merchant);
    }

    @Override
    public Merchant get(String id) {
        return this.merchantStore.get(id);
    }
}
