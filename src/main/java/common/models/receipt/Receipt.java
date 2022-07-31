package common.models.receipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private int id;
    private List<ReceiptRecord> records;
    private BigDecimal totalSum;
 //   private static AtomicInteger count = new AtomicInteger(0);

    public Receipt(int id){
        records = new ArrayList<>();
        this.id = id;
    }

    public void addRecord(ReceiptRecord receiptRecord){records.add(receiptRecord);}
    public List<ReceiptRecord> getRecords(){return this.records;}
    public int getId(){return this.id ;}
    public BigDecimal getTotalSum(){return this.totalSum ;}

    public void setTotalSum(BigDecimal totalSum){this.totalSum = totalSum;}
    public void print(){
        System.out.println("\n\n\nReceipt " + id );
        for (var record : records) {
           record.print();
        }
        System.out.println("\nTotal sum  " + totalSum);
    }

}
