package lk.ijse.dep.pos.dao;

import lk.ijse.dep.pos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerDAO extends JpaRepository<Customer, String> {

    @Query(value = "SELECT customer_id FROM Customer order by customer_id desc LIMIT 1 ",nativeQuery = true)
    String getLastCustomerId() throws Exception;

}
