package fruit;

import java.text.SimpleDateFormat;

public class Fruit {
    private TypeOfFruit type;
    private int shelfLife;
    private String date;
    private double price;

    public Fruit(TypeOfFruit type, int shelfLife, String date, double price) {
        this.type = type;
        this.shelfLife = shelfLife;
        this.date = date;
        this.price = price;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy");
        return "Fruit{" +
                "type = " + type +
                ", shelfLife = " + shelfLife +
                ", date = '" + date + '\'' +
                ", price = " + price +
                '}';
    }

    public TypeOfFruit getType() {
        return type;
    }

    public void setType(TypeOfFruit type) {
        this.type = type;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
