package com.greenmart.common.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long id;
    @Column(name = "FULLNAME")
    private String fullName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE")
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TOTAL_PURCHASE_AMOUNT")
    private BigDecimal total;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "USERLOGIN_ID")
    private LoginCustomer loginCustomer;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cart> carts;
    @OneToMany(mappedBy = "customer")
    private List<Bill> bills;

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public void addBill(Bill bill){
        this.bills.add(bill);
    }
    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }


    public Customer() {
    }

    public Customer(String fullName, String address, String phone, LocalDate birthday, String email) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.email = email;
        this.total= BigDecimal.valueOf(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LoginCustomer getLoginCustomer() {
        return loginCustomer;
    }

    public void setLoginCustomer(LoginCustomer loginCustomer) {
        this.loginCustomer = loginCustomer;
    }
}
