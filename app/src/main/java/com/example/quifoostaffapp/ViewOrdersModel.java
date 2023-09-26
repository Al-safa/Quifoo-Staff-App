package com.example.quifoostaffapp;

public class ViewOrdersModel {

    String OrderDate, OrderId, OrderTime, OrderStatus;

    ViewOrdersModel()
    {

    }

    public ViewOrdersModel(String orderDate, String orderId, String orderTime, String orderStatus) {
        OrderDate = orderDate;
        OrderId = orderId;
        OrderTime = orderTime;
        OrderStatus = orderStatus;
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

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }
}
