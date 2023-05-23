package com.greenmart.common.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BILL_DEATAILS")
public class BillDetails {
    @EmbeddedId
    private BillDetailsId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BILL_ID", insertable = false, updatable = false)
    @MapsId("billId")
    private Bill bill;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false)
    @MapsId("productId")
    private Product product;


    @Column(name = "QUANTITY")
    private Integer quantity;

    public BillDetails() {
    }

    public BillDetails(BillDetailsId id, Bill bill, Product product, Integer quantity) {
        this.id = id;
        this.bill = bill;
        this.product = product;
        this.quantity = quantity;
    }

    public BillDetailsId getId() {
        return id;
    }

    public void setId(BillDetailsId id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
        if (bill != null) {
            this.id.setBillId(bill.getBillId());
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public boolean checkIdBill(Long id_Bill){
        return Objects.equals(this.id.getBillId(), id_Bill);
    }

}
