package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dao.CustomerRepository;
import com.luv2code.ecommerce.dto.PaymentInfo;
import com.luv2code.ecommerce.dto.Purchase;
import com.luv2code.ecommerce.dto.PurchaseResponse;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;


    public CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        // Initialisation de l'API stripe avec la clé secrète
        Stripe.apiKey = secretKey;
    }

    /**
     * Cette méthode permet de créer la commande (Order) afin de l'enregistrer en bases de données
     * @param purchase Données de l'achat (entité Purchase)
     * @return orderTrackingNumber (le numéro de suivi)
     */
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // Récupérer les informations de commande du dto
        Order order = purchase.getOrder();

        // Génère un numéro de suivi
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Remplir la commande avec les articles de la commande
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // Remplir la commande avec billingAddress et shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());


        // Remplir le client avec la commande
        Customer customer = purchase.getCustomer();
        // Check if this is an existing customer
        String theEmail = customer.getEmail();
        // Vérifiez s'il s'agit d'un client existant
        Customer customerFromDB = customerRepository.findByEmail(theEmail);
        if (customerFromDB != null) {
            // Si se client éxiste déja attribuons-les en conséquence
            customer = customerFromDB;
        }

        // Permet d'ajouter une commande, à la liste de commande du customer
        customer.add(order);

        // Enregistrer le customer dans la base de données
        customerRepository.save(customer);

        // Retourner le numéro de suivi
        return new PurchaseResponse(orderTrackingNumber);
    }

    /**
     * Permet de créer un paymentIntent (une intention de paiement), et l'envoyer à stripe
     * @param paymentInfo données à envoyer à stripe
     * @return Les données en temps que clé, valeur
     * @throws StripeException
     */
    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Achat MagasinEnLigne");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        // Génère un numéro UUID aléatoire (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }
}