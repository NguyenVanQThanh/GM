package com.greenmart.cart;

import com.greenmart.common.entity.Cart;
import com.greenmart.common.entity.Customer;
import com.greenmart.common.entity.Product;
import com.greenmart.customer.CustomerService;
import com.greenmart.product.ProductRepository;
import com.greenmart.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    public List<Cart> listCartOfIdCustomer(Long id){

        return cartRepository.findByIdCustomer(id);
    }
    public Cart findById(Long id){
        return cartRepository.findById(id).get();
    }
    public Cart findByCustomerAndProduct(Long id_customer, Long id_product){
        return cartRepository.findProductOfCustomer(id_customer,id_product);
    }
    public void save(Long id_product, Long id_customer){
        Cart ProductOfId = cartRepository.findProductOfCustomer(id_customer,id_product);
        if (ProductOfId == null){
            Product cartProduct =  productService.findById(id_product);
            Customer cartCustomer = customerService.findById(id_customer);
            Cart newCart = new Cart(cartProduct,cartCustomer,1,false);
            cartRepository.save(newCart);
        }
        else {
            ProductOfId.setQuantity(ProductOfId.getQuantity()+1);
            update(ProductOfId);
        }
    }

    private void update(Cart cart){
        Cart cartInDB = cartRepository.findById(cart.getId()).get();
        cartInDB.setQuantity(cart.getQuantity());
        cartRepository.save(cartInDB);
    }
    public void saveByQuantity(Long id_product, Long id_customer,Integer quantity){
        Cart ProductOfId = cartRepository.findProductOfCustomer(id_customer,id_product);
        if (ProductOfId == null){
            Product cartProduct =  productService.findById(id_product);
            Customer cartCustomer = customerService.findById(id_customer);
            Cart newCart = new Cart(cartProduct,cartCustomer,quantity,false);
            cartRepository.save(newCart);
        }
        else {
            ProductOfId.setQuantity(quantity);
            update(ProductOfId);
        }
    }
    public void updateQuantity(Cart cart,Integer quantity){
        Cart cartInDB = cartRepository.findById(cart.getId()).get();
        cartInDB.setQuantity(cartInDB.getQuantity()+quantity);
        cartRepository.save(cartInDB);
    }

    public void delete(Long id){
        cartRepository.deleteById(id);
    }

    public BigDecimal total(Long id_customer){
        List<Cart> cartListOfCustomer = listCartOfIdCustomer(id_customer);
        BigDecimal total = BigDecimal.valueOf(0);
        for (Cart cart : cartListOfCustomer){
            BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity());
            total = total.add(cart.getProduct().getPrice());
            total = total.multiply(quantity);
        }
        return total;
    }
    public void save(Cart cart){
        cartRepository.save(cart);
    }
    public void deleteList(List<Cart> cartList){
        for (Cart cart : cartList){
            cartRepository.deleteById(cart.getId());
        }
    }
}
