package com.example.quifoostaffapp;

public class OrderItemModel {
    String Name, Category, DishType;
    int Quantity;

    OrderItemModel(){

    }

    public OrderItemModel(String name, int quantity, String dishType) {
        Name = name;
        Quantity = quantity;
        DishType = dishType;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getDishType() {
        return DishType;
    }

    public void setDishType(String dishType) {
        DishType = dishType;
    }
}

