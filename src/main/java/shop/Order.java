package shop;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String date;
    private List<Client> clients;

    public Order() {
        this("", new ArrayList<>());
    }

    public Order(String date, List<Client> clients) {
        this.date = date;
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Order{" +
                "date = '" + date + '\'' +
                ", clients = " + clients +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

}
