package com.greenmart.common.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "BILL")
public class Bill {
    public enum OrderStatus {
        ORDERED("ORDERED"),
        PREPARING("PREPARING"),
        SHIPPING("SHIPPING"),
        SUCCESS("SUCCESS");

        private String value;

        OrderStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BILL_ID")
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BILL_DATE", nullable = false)
    private LocalDate billDate;

    @Column(name = "TOTAL_MONEY", precision = 11, scale = 2)
    private BigDecimal totalMoney;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ADDRESS_DELIVERY", length = 100)
    private String addressDelivery;

    @OneToMany(mappedBy = "bill")
    private List<BillDetails> billDetails;

    public List<BillDetails> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetails> billDetails) {
        this.billDetails = billDetails;
    }

    public Bill(Customer customer, LocalDate billDate, BigDecimal totalMoney, OrderStatus status, String addressDelivery) {
        this.customer = customer;
        this.billDate = billDate;
        this.totalMoney = totalMoney;
        this.status = status;
        this.addressDelivery = addressDelivery;
    }

    public Bill() {
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAddressDelivery() {
        return addressDelivery;
    }

    public void setAddressDelivery(String addressDelivery) {
        this.addressDelivery = addressDelivery;
    }
}
