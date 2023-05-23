package com.bezkoder.springjwt.models.Dto.order;

import com.bezkoder.springjwt.models.Entity.Order;


public class PlaceOrderRequestDTO {

    private Order checkedOutItems;
    private Double totalPrice;

    public PlaceOrderRequestDTO() {
    }

    public PlaceOrderRequestDTO(Order checkedOutItems, Double totalPrice) {
        this.checkedOutItems = checkedOutItems;
        this.totalPrice = totalPrice;
    }

    public Order getCheckedOutItems() {
        return checkedOutItems;
    }

    public void setCheckedOutItems(Order checkedOutItems) {
        this.checkedOutItems = checkedOutItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

// Constructors, getters, and setters
}