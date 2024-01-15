package com.magasinEnLigne.ecommerce.dto;

import com.magasinEnLigne.ecommerce.entity.Address;
import com.magasinEnLigne.ecommerce.entity.Customer;
import com.magasinEnLigne.ecommerce.entity.Order;
import com.magasinEnLigne.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;
@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
