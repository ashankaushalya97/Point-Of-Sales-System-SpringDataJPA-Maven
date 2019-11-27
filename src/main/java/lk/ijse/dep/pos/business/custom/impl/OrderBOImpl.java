package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.custom.*;

import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;
import lk.ijse.dep.pos.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Transactional
@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private QueryDAO queryDAO;
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public int getLastOrderId() throws Exception {
        int orderId;
        orderId=orderDAO.getLastOrderId();
        return orderId;
    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {
        int oId = order.getId();
        orderDAO.save(new Orders(oId,new java.sql.Date(new Date().getTime()),customerDAO.find(order.getCustomerId())));

        for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
            orderDetailDAO.save(new OrderDetail(oId,orderDetail.getCode(),orderDetail.getQty(),orderDetail.getUnitPrice()));

            Item item = itemDAO.find(orderDetail.getCode());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            itemDAO.update(item);

        }
    }
    @Transactional(readOnly = true)
    @Override
    public List<OrderDTO2> getOrderInfo() throws Exception {
        List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo();
        List<OrderDTO2> dtos = new ArrayList<>();
        for (CustomEntity info : ordersInfo) {
            dtos.add(new OrderDTO2(info.getOrderId(),
                    info.getOrderDate(),info.getCustomerId(),info.getCustomerName(),info.getOrderTotal()));
        }
        return dtos;
    }

    @Override
    public List<OrderDTO2> getSearchInfo(String searchText) throws Exception {
        List<CustomEntity> ordersInfo = queryDAO.getSearchInfo(searchText);
        List<OrderDTO2> dtos = new ArrayList<>();
        for (CustomEntity info : ordersInfo) {
            dtos.add(new OrderDTO2(info.getOrderId(),
                    info.getOrderDate(),info.getCustomerId(),info.getCustomerName(),info.getOrderTotal()));
        }
        return dtos;
    }
}
