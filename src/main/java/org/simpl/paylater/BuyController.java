package org.simpl.paylater;

import org.simpl.paylater.api.BuyRequest;
import org.simpl.paylater.api.Response;
import org.simpl.paylater.api.Success;
import org.simpl.paylater.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/paylater/buy")
public class BuyController extends BaseController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping()
    public Response<String> buy(@RequestBody BuyRequest buyRequest, @RequestParam("requestId") String reqId) {
        transactionService.buy(buyRequest.getProduct(), buyRequest.getUserId(), buyRequest.getMerchantId(), buyRequest.getAmount());
        return new Success<>("Product: " + buyRequest.getProduct() + " bought with credit Rs." + buyRequest.getAmount());
    }
}
