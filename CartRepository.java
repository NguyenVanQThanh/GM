package com.greenmart.cart;


import com.greenmart.common.entity.Cart;
import com.greenmart.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE c.customer.id = :id")
    public List<Cart> findByIdCustomer(@Param("id") Long id);
    @Query("SELECT c FROM Cart c WHERE c.customer.id = :id_customer AND c.product.id = :id_product")
    public Cart findProductOfCustomer(@Param("id_customer") Long id_customer,
                                            @Param("id_product") Long id_product);
}
