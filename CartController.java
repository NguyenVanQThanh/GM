package com.greenmart.cart;

import com.greenmart.bill.BillService;
import com.greenmart.common.entity.Bill;
import com.greenmart.common.entity.BillDetails;
import com.greenmart.common.entity.Cart;
import com.greenmart.common.entity.Customer;
import com.greenmart.customer.CustomerDetails;
import com.greenmart.customer.CustomerService;
import com.greenmart.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @Autowired
    private BillService billService;
    @GetMapping("/cart")
    public String viewCart(@AuthenticationPrincipal CustomerDetails customerDetails,
                           Model model){
        String email = customerDetails.getUsername();
        Long id_cus = customerService.getCustomerByEmail(email).getId();
        List<Cart> listCartOfId = cartService.listCartOfIdCustomer(id_cus);
        BigDecimal total = cartService.total(id_cus);
        model.addAttribute("listCartOfId",listCartOfId);
        model.addAttribute("total",total);
        return "cart";
    }
    @GetMapping("/cart/delete/{id}")
    public String deleteCart(@PathVariable(name = "id") Long id,
                             @AuthenticationPrincipal CustomerDetails customerDetails,
                             RedirectAttributes redirectAttributes)
    {
        Cart cart = cartService.findById(id);
        if (cart == null){
            redirectAttributes.addFlashAttribute("error","The product don't have in cart");
        }
        cartService.delete(id);
        redirectAttributes.addFlashAttribute("message","The product deleted successfully");
        return "redirect:/cart";
    }
    @GetMapping("/cart/{id}/plus-1")
    public String plusCart(@PathVariable(name = "id") Long id,
                           @AuthenticationPrincipal CustomerDetails customerDetails){
        Cart cart = cartService.findById(id);
        cartService.updateQuantity(cart,1);
        return "redirect:/cart";
    }
    @GetMapping("/cart/{id}/minus-1")
    public String minusCart(@PathVariable(name = "id") Long id,
                           @AuthenticationPrincipal CustomerDetails customerDetails){
        Cart cart = cartService.findById(id);
        if (cart.getQuantity()==1) cartService.delete(id);
        else cartService.updateQuantity(cart,-1);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update-quantity/{id}")
    public String updateQuantity(@PathVariable(name = "id") Long id,
                                 @RequestParam(name = "quantity") Integer quantity,
                                 @AuthenticationPrincipal CustomerDetails customerDetails,
                                 RedirectAttributes redirectAttributes) {
        Cart cart = cartService.findById(id);
        if (cart == null) {
            redirectAttributes.addFlashAttribute("error", "The product doesn't exist in the cart");
        } else {
            if (quantity <= 0) {
                // Nếu số lượng <= 0, xóa sản phẩm khỏi giỏ hàng
                cartService.delete(id);
                redirectAttributes.addFlashAttribute("message", "The product has been removed from the cart");
            } else {
                // Cập nhật số lượng mới
                cart.setQuantity(quantity);
                cartService.save(cart);
                redirectAttributes.addFlashAttribute("message", "The quantity has been updated successfully");
            }
        }
        return "redirect:/cart";
    }
    @GetMapping("/cart/confirm")
    public String cartConfirm(@AuthenticationPrincipal CustomerDetails customerDetails,
                              Model model){
        String email = customerDetails.getUsername();
        Long id_cus = customerService.getCustomerByEmail(email).getId();
        List<Cart> listCartOfCustomer = cartService.listCartOfIdCustomer(id_cus);
        String delivery = customerService.getCustomerByEmail(email).getAddress();
        BigDecimal total = cartService.total(id_cus);
        model.addAttribute("listCartOfId",listCartOfCustomer);
        model.addAttribute("total", total);
        model.addAttribute("delivery",delivery);
        return "confirmOrder";
    }

    @PostMapping("/cart/add-order")
    public String addOrder(RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal CustomerDetails customerDetails,
                           @RequestParam(name = "delivery") String delivery,
                           @ModelAttribute(name = "listCartOfId") ArrayList<Cart> listCartOfId){
        String email = customerDetails.getUsername();
        Customer customer = customerService.getCustomerByEmail(email);
        billService.save(customer.getId(),delivery);
//        cartService.deleteList(listCartOfId);
        redirectAttributes.addFlashAttribute("message","ORDER COMPLETE!");
        return "redirect:/cart";
    }
}
