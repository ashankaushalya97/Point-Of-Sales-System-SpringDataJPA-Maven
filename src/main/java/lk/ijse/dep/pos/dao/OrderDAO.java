package lk.ijse.dep.pos.dao;

import lk.ijse.dep.pos.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDAO extends JpaRepository<Orders, Integer> {

    @Query(value = "Select id FROM Orders Order BY id DESC LIMIT 1  ",nativeQuery = true)
    Integer getLastOrderId() throws Exception;

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM Orders WHERE customer_id=?1)THEN 'true' ELSE 'false' END",nativeQuery = true)
    boolean existsByCustomerId(String customerId) throws Exception;



}
