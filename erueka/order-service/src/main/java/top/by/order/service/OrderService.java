package top.by.order.service;

import top.by.order.entity.Order;

public interface OrderService {

   Order queryOrderById(String orderId);

}
