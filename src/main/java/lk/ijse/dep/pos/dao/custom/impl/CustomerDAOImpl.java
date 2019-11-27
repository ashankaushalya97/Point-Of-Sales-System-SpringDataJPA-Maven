package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDAOImpl extends CrudDAOImpl<Customer,String>  implements CustomerDAO{


    @Override
    public String getLastCustomerId() throws Exception {
        //return (String) emf.createEntityManager().createNativeQuery("SELECT customer_id FROM Customer order by customer_id desc LIMIT 1 ").getSingleResult();
        System.out.println(entityManager.createNativeQuery("SELECT * FROM Customer").getResultList());
        return null;
    }

}
