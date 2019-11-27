package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dao.ItemDAO;
import lk.ijse.dep.pos.dao.OrderDetailDAO;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Transactional
@Component
public class ItemBOImpl implements ItemBO {

    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        itemDAO.save(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        itemDAO.update(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public void deleteItem(String itemCode) throws Exception {
        if (orderDetailDAO.existsByItemCode(itemCode)){
            throw new AlreadyExistsInOrderException("Item already exists in an order, hence unable to delete");
        }
        System.out.println("Record not not exist in orderDetails");
        itemDAO.delete(itemCode);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDTO> findAllItems() throws Exception {
            List<Item> allItems = itemDAO.findAll();
            List<ItemDTO> dtos = new ArrayList<>();
            for (Item item : allItems) {
                dtos.add(new ItemDTO(item.getCode(),
                        item.getDescription(),
                        item.getQtyOnHand(),
                        item.getUnitPrice()));
            }
            return dtos;
    }
    @Transactional(readOnly = true)
    @Override
    public String getLastItemCode() throws Exception {
        String itemCode;
        itemCode= itemDAO.getLastItemCode();
        return itemCode;
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        Item item;
        item = itemDAO.find(itemCode);
        return new ItemDTO(item.getCode(),
                item.getDescription(),
                item.getQtyOnHand(),
                item.getUnitPrice());
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllItemCodes() throws Exception {
        List<Item> allItems;
        allItems = itemDAO.findAll();
        List<String> codes = new ArrayList<>();
        for (Item item : allItems) {
            codes.add(item.getCode());
        }
        return codes;
    }
}
