package shop;

import com.alibaba.fastjson.JSON;
import fruit.Fruit;
import date.ParseDate;
import fruit.TypeOfFruit;
import jsonFiles.ObjectProcessingJSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Shop {
    private List<Fruit> fruitWarehouse;
    private double moneyBalance;

    public Shop() {
        this.fruitWarehouse = new ArrayList<>();
        this.moneyBalance = 0;
    }

    // Дополняем коллекцию обьектами типа Fruit
    public void addFruits(String pathToJsonFile) throws FileNotFoundException {
        String supply = new Scanner(new File(pathToJsonFile)).useDelimiter("\\Z").next();
        fruitWarehouse.addAll(JSON.parseArray(supply, Fruit.class));
    }

    // Сохраняем содержимое Shop в файл
    public void save(String pathToJsonFile) throws IOException {
        ObjectProcessingJSON.save(pathToJsonFile, this);
    }

    // Загружаем новый Shop из сохраненной версии
    public void load(String pathToJsonFile) throws FileNotFoundException {
        Shop shop = (Shop) ObjectProcessingJSON.load(pathToJsonFile, this);
        this.fruitWarehouse = shop.getFruitWarehouse();
        this.moneyBalance = shop.getMoneyBalance();
    }

    // Возвращаем продукты которые ипортятся к заданой дате
    public List<Fruit> getSpoiledFruits(Date date){
        List<Fruit> spoiledFruits = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (Fruit fruit: fruitWarehouse){
            calendar.setTime(ParseDate.parseDateFromString(fruit.getDate()));
            calendar.add(Calendar.DAY_OF_MONTH, fruit.getShelfLife());
            if (date.after(calendar.getTime())){
                spoiledFruits.add(fruit);
            }
        }
        return spoiledFruits;
    }

    // Перегруженный метод (принимает тип фрукта)
    public List<Fruit> getSpoiledFruits(Date date, TypeOfFruit type){
        List<Fruit> spoiledFruit = getSpoiledFruits(date);
        return getFruitsByType(spoiledFruit, type);
    }

    // Возвращаем список готовых к продаже продуктов
    public List<Fruit> getAvailableFruits(Date date){
        List<Fruit> availableFruits = new ArrayList<>();
        availableFruits.addAll(fruitWarehouse);
        availableFruits.removeAll(getSpoiledFruits(date));
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < availableFruits.size(); i++){
            calendar.setTime(ParseDate.parseDateFromString(availableFruits.get(i).getDate()));
            if (date.before(calendar.getTime())){
               availableFruits.remove(i);
            }
        }
        return availableFruits;
    }

    // Перегруженный метод (принимает тип фрукта)
    public List<Fruit> getAvailableFruits(Date date, TypeOfFruit type){
        List<Fruit> availableFruits = getAvailableFruits(date);
        return getFruitsByType(availableFruits, type);
    }

    // Возвращаем продукты которые были доставлены в заданную дату
    public List<Fruit> getAddedFruits(Date date){
        List<Fruit> addedFruits = new ArrayList<>();
        for (Fruit fruit : fruitWarehouse) {
            if (date.equals(ParseDate.parseDateFromString(fruit.getDate()))){
                addedFruits.add(fruit);
            }
        }
        return addedFruits;
    }

    // Перегруженный метод (принимает тип фрукта)
    public List<Fruit> getAddedFruits(Date date, TypeOfFruit type){
        List<Fruit> addedFruits = getAddedFruits(date);
        return getFruitsByType(addedFruits, type);
    }

    // Сохраняем заказ в файл
    public void saveOrder(Order order, String pathToJsonFile) throws IOException {
       ObjectProcessingJSON.save(pathToJsonFile, order);
    }

    // Продаем продукты по заданному дню
    public void sell(String pathToJsonFile) throws FileNotFoundException {
        String orderString = new Scanner(new File(pathToJsonFile)).useDelimiter("\\Z").next();
        Order order = JSON.parseObject(orderString, Order.class);
        List<Client> clients = order.getClients();
        List<Fruit> availableFruits;
        for (Client client: clients) {
            availableFruits = getAvailableFruits(ParseDate.parseDateFromString(order.getDate()), client.getType());
            if(availableFruits.size() >= client.getCount()){
                for (int i = 0; i < client.getCount(); i++){
                    moneyBalance += availableFruits.get(i).getPrice();
                    fruitWarehouse.remove(availableFruits.get(i));
                }
            }
        }
    }

    // Возвращаем фрукты по типу
    public static List<Fruit> getFruitsByType(List<Fruit> fruits, TypeOfFruit type){
        List<Fruit> fruitsByType = new ArrayList<>();
        for (Fruit fruit : fruits) {
            if (fruit.getType() == type){
                fruitsByType.add(fruit);
            }
        }
        return fruitsByType;
    }

    @Override
    public String toString() {
        String string = "Money balance: " + getMoneyBalance() + "\n";
        for (Fruit fruit: fruitWarehouse) {
            string = string.concat(fruit.toString() + "\n");
        }
        return string;
    }

    public List<Fruit> getFruitWarehouse() {
        return fruitWarehouse;
    }

    public void setMoneyBalance(double moneyBalance) {
        this.moneyBalance = moneyBalance;
    }

    // Округляем баланс Shop
    public double getMoneyBalance() {
        BigDecimal bigDecimal = new BigDecimal(moneyBalance);
        moneyBalance = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        return moneyBalance;
    }

}
