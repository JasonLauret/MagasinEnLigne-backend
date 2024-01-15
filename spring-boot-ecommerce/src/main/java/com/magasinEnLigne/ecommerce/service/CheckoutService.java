package com.magasinEnLigne.ecommerce.service;

import com.magasinEnLigne.ecommerce.dto.PaymentInfo;
import com.magasinEnLigne.ecommerce.dto.Purchase;
import com.magasinEnLigne.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

}
