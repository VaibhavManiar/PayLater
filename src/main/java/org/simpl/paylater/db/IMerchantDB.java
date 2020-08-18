package org.simpl.paylater.db;

import org.simpl.paylater.pojo.Merchant;

public interface IMerchantDB {
    void add(Merchant merchant);
    Merchant get(String id);
}
