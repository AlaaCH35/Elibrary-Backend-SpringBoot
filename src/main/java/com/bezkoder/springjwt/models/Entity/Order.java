package com.bezkoder.springjwt.models.Entity;



import com.bezkoder.springjwt.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "carttotal_price")
    private Double cartTotal;



    @Column(name = "session_id")
    private String sessionId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Cart> orderDetails;

    public List<Cart> getCarts() {
        return orderDetails;
    }

    public void setCarts(List<Cart> carts) {
        this.orderDetails = carts;
    }

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Order() {
    }



    @PrePersist
    private void prePersist() {
        createdDate = new Date();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(Double cartTotal) {
        this.cartTotal = cartTotal;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
