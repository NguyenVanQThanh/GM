package com.greenmart.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillDetailsId implements Serializable {
    @Column(name = "BILL_ID")
    private Long billId;
    @Column(name = "PRODUCT_ID")
    private Long productId;

    public BillDetailsId() {
    }

    public BillDetailsId(Long billId, Long productId) {
        this.billId = billId;
        this.productId = productId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetailsId that = (BillDetailsId) o;
        return Objects.equals(billId, that.billId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billId, productId);
    }

}
