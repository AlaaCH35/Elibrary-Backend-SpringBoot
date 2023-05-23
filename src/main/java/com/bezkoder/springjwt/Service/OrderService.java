package com.bezkoder.springjwt.Service;


import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.Order;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.OrderRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderService {

private  final OrderRepository orderRepository;
    private  final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    public Integer placeOrder(Long userId, Order order,Double totalPrice){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        order.setCartTotal(totalPrice);
        order.setUser(user);


        // Save the order in the database
        Order savedOrder = orderRepository.save(order);

        // Return the generated order ID
        return savedOrder.getId();


    }
    public List<Order> listOrders(Long UserId) {
        User user = userRepository.findById(UserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

}
