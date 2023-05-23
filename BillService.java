package com.greenmart.bill;

import com.greenmart.cart.CartService;
import com.greenmart.common.entity.*;
import com.greenmart.customer.CustomerService;
import com.greenmart.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service

public class BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    public void save(Long id_customer, String address_delivery){
        List<Cart> cartListOfCustomer = cartService.listCartOfIdCustomer(id_customer);
        Customer customer = customerService.findById(id_customer);
        BigDecimal total = cartService.total(id_customer);
        Bill bill = new Bill(customer, LocalDate.now(),total, Bill.OrderStatus.ORDERED,address_delivery);
        List<BillDetails> billDetailsList = new ArrayList<>();
        for (Cart cart : cartListOfCustomer){
            Product product = productService.findById(cart.getProduct().getId());
            BillDetailsId id_billDetails= new BillDetailsId(bill.getBillId(), product.getId());
            BillDetails billDetails = new BillDetails(id_billDetails,bill,product,cart.getQuantity());
            billDetailsList.add(billDetails);
//            billDetailsRepository.save(billDetails);
        }
        bill.setBillDetails(billDetailsList);
        billRepository.save(bill);
        customer.addBill(bill);
        customerService.save(customer);
        billDetailsRepository.saveAll(billDetailsList);
    }
    public Bill findById(Long id){
        return billRepository.findById(id).get();
    }
//    public void delete(Bill bill){
//        Long id_Bill = bill.getBillId();
//        List<BillDetails> billDetailsList = billDetailsRepository.findAll();
//        for (BillDetails billDetails : billDetailsList){
//            if (billDetails.checkIdBill(id_Bill))
//                billDetailsRepository.delete(billDetails);
//        }
//        billRepository.delete(bill);
//    }
}
