package app.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "trx_id")
    private Long trx_id;

    @Column(name = "acc_number")
    private Long acc_number;
    @Column(name = "trx_amount")
    private Double trx_amount;
    @Column(name = "trx_description")
    private String description;
    @Column(name = "trx_date")
    private LocalDate trx_date;
    @Column(name = "trx_time")
    private LocalTime trx_time;

    @Column(name = "cust_id")
    private Long custID;

    @Override
    public String toString() {
        return "Customer{" +
                "trx_id=" + trx_id +
                ", acc_number=" + acc_number +
                ", trx_amount=" + trx_amount +
                ", description='" + description + '\'' +
                ", trx_date=" + trx_date +
                ", trx_time=" + trx_time +
                ", custID=" + custID +
                '}';
    }

    public Long getTrx_id() {
        return trx_id;
    }

    public void setTrx_id(Long trx_id) {
        this.trx_id = trx_id;
    }

    public Long getAcc_number() {
        return acc_number;
    }

    public void setAcc_number(Long acc_number) {
        this.acc_number = acc_number;
    }

    public Double getTrx_amount() {
        return trx_amount;
    }

    public void setTrx_amount(Double trx_amount) {
        this.trx_amount = trx_amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTrx_date() {
        return trx_date;
    }

    public void setTrx_date(LocalDate trx_date) {
        this.trx_date = trx_date;
    }

    public LocalTime getTrx_time() {
        return trx_time;
    }

    public void setTrx_time(LocalTime trx_time) {
        this.trx_time = trx_time;
    }

    public Long getCustID() {
        return custID;
    }

    public void setCustID(Long custID) {
        this.custID = custID;
    }



    public Customer(){

    }

    public Customer(Long trx_id, Long acc_number, Double trx_amount, String description, LocalDate trx_date, LocalTime trx_time, Long custID) {
        this.trx_id = trx_id;
        this.acc_number = acc_number;
        this.trx_amount = trx_amount;
        this.description = description;
        this.trx_date = trx_date;
        this.trx_time = trx_time;
        this.custID = custID;
    }
}
