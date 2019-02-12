package ru.sfedu.musicapp.ListEntities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.musicapp.models.Payment;

import java.util.List;

@Root(name="Payments")
public class Payments {

    @ElementList(inline=true)
    private List<Payment> payments;

    public List<Payment> getPayments(){
        return this.payments;
    }

    public void setPayments(List<Payment> payments){
        this.payments = payments;
    }

    public void addPayment(Payment payment){
        this.payments.add(payment);
    }

    public void removePayment(int id){
        Payment payForDel = payments.stream()
                .filter(p -> id==p.getId())
                .findAny()
                .orElse(null);
        payments.remove(payForDel);
    }
}
