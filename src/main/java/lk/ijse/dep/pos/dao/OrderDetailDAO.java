package lk.ijse.dep.pos.dao;

import lk.ijse.dep.pos.entity.OrderDetail;
import lk.ijse.dep.pos.entity.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, OrderDetailPK> {

    @Query(value = "SELECT  * FROM OrderDetail Where item_code=?1",nativeQuery = true)
    Boolean existsByItemCode(String itemCode) throws Exception;

}
