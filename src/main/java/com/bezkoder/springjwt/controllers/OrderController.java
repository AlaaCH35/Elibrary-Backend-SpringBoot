package com.bezkoder.springjwt.controllers;


import com.bezkoder.springjwt.Service.OrderService;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Integer> placeOrder(@PathVariable("userId") Long userId, @RequestBody Order checkedOutItems,@RequestParam("Totalprice") Double Totalprice) {


        int orderId = orderService.placeOrder(userId, checkedOutItems,Totalprice);
        return ResponseEntity.ok(orderId);
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getOrders(@PathVariable("userId") Long UserId) {
        List<Order> body = orderService.listOrders(UserId);
        return new ResponseEntity<List<Order>>(body, HttpStatus.OK);
    }






}
