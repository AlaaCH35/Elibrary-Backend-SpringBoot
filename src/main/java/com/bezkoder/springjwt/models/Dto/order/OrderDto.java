package com.bezkoder.springjwt.models.Dto.order;


import com.bezkoder.springjwt.models.Entity.Order;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;



public class OrderDto {
    private Integer id;
    private @NotNull Integer userId;





    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
        //this.setId(order.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
