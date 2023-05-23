package com.greenmart.bill;

import com.greenmart.common.entity.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDetailsRepository extends JpaRepository<BillDetails,Long> {
    @Query("SELECT b FROM BillDetails b WHERE b.id.billId= :id_bill")
    public List<BillDetails> findBillDetailsByBill(@Param("id_bill") Long id_bill);
}
