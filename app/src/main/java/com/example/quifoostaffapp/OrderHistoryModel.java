package com.example.quifoostaffapp;

public class OrderHistoryModel {
    String User, OrderTime, OrderDate, OrderId, OrderTotalPrice, OrderStatus;

    OrderHistoryModel()
    {

    }

    public OrderHistoryModel(String user, String orderTime, String orderDate, String orderId, String orderTotalPrice, String orderStatus) {
        User = user;
        OrderTime = orderTime;
        OrderDate = orderDate;
        OrderId = orderId;
        OrderTotalPrice = orderTotalPrice;
        OrderStatus = orderStatus;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderTotalPrice() {
        return OrderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        OrderTotalPrice = orderTotalPrice;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }
}
