package company;

import fruit.Fruit;
import fruit.TypeOfFruit;
import jsonFiles.ObjectProcessingJSON;
import shop.Shop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Company {
    private List<Shop> shops;
    private double moneyBalance;

    public Company(){
        this.shops = new ArrayList<>();
        this.moneyBalance = 0;
    }

    public void save(String pathToJsonFile) throws IOException {
        ObjectProcessingJSON.save(pathToJsonFile, this);
    }

    public void load(String pathToJsonFile) throws FileNotFoundException {
        Company company = (Company) ObjectProcessingJSON.load(pathToJsonFile, this);
        this.shops = company.getShops();
        this.moneyBalance = company.getMoneyBalance();
        System.out.println(this);
    }

    // Возвращаем Shop по индексу
    public Shop getShop(int index){
        if (shops.contains(shops.get(index))){
            return shops.get(index);
        }
        return null;
    }

    // Возвращаем сумму балансов всех лавок
    public int getCompanyBalance(){
        int companyBalance = 0;
        for (Shop shop: shops) {
            companyBalance += (int) shop.getMoneyBalance();
        }
        return companyBalance;
    }

    // Возвращаем испорченные продукты со всех лавок
    public List<Fruit> getSpoiledFruits(Date date){
        List<Fruit> spoiledFruits = new ArrayList<>();
        for (Shop shop: shops) {
            spoiledFruits.addAll(shop.getSpoiledFruits(date));
        }
        return spoiledFruits;
    }

    // Перегруженный метод (принимает тип фрукта)
    public List<Fruit> getSpoiledFruits(Date date, TypeOfFruit type){
        List<Fruit> spoiledFruits = getSpoiledFruits(date);
        return Shop.getFruitsByType(spoiledFruits, type);
    }

    // Возвращаем список готовых к продаже продуктов(по всем лавкам)
    public List<Fruit> getAvailableFruits(Date date){
        List<Fruit> availableFruits = new ArrayList<>();
        for (Shop shop: shops) {
            availableFruits.addAll(shop.getAvailableFruits(date));
        }
        return availableFruits;
    }

    // Перегруженный метод (принимает тип фрукта)
    public List<Fruit> getAvailableFruits(Date date, TypeOfFruit type){
        List<Fruit> availableFruits = getAvailableFruits(date);
        return Shop.getFruitsByType(availableFruits, type);
    }

    // Возвращаем продукты которые были доставлены в заданную дату(по всем лавкам)
    public List<Fruit> getAddedFruits(Date date){
        List<Fruit> addedFruits = new ArrayList<>();
        for (Shop shop: shops) {
            addedFruits.addAll(shop.getAddedFruits(date));
        }
        return addedFruits;
    }

    // Перегруженный метод (принимает тип фрукта)
    public List<Fruit> getAddedFruits(Date date, TypeOfFruit type){
        List<Fruit> addedFruits = getAddedFruits(date);
        return Shop.getFruitsByType(addedFruits, type);
    }

    @Override
    public String toString() {
        String string = "Money balance: " + getMoneyBalance() + "\n";
        for (Shop shop: shops) {
            string = string.concat(shop.toString() + "-------------------------------------" + "\n");
        }
        return string;
    }

    public List<Shop> getShops() {
        return shops;
    }

    // Округляем баланс Company
    public double getMoneyBalance() {
        moneyBalance = getCompanyBalance();
        return moneyBalance;
    }

}
