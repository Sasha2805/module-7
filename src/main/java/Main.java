import company.Company;
import date.ParseDate;
import fruit.Fruit;
import fruit.TypeOfFruit;
import shop.Client;
import shop.Order;
import shop.Shop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String SHOP_JSON = "src/main/java/jsonFiles/shop.json";
    public static final String ORDER_JSON = "src/main/java/jsonFiles/order.json";
    public static final String FRUIT_DELIVERY_JSON = "src/main/java/jsonFiles/fruitDelivery.json";
    public static final String COMPANY_JSON = "src/main/java/jsonFiles/company.json";

    public static void main(String[] args) throws IOException {
        Shop shop = new Shop();
        shop.getFruitWarehouse().add(new Fruit(TypeOfFruit.banana, 25, "24.07.2017", 15.88));
        shop.getFruitWarehouse().add(new Fruit(TypeOfFruit.mango, 15, "01.07.2017", 30));
        shop.getFruitWarehouse().add(new Fruit(TypeOfFruit.orange, 60, "22.08.2017", 18));
        shop.getFruitWarehouse().add(new Fruit(TypeOfFruit.banana, 25, "01.07.2017", 15));

        shop.addFruits(FRUIT_DELIVERY_JSON);
        shop.save(SHOP_JSON);
        shop.load(SHOP_JSON);
        System.out.println(shop);
        System.out.println("----------------------------------------");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите дату в формате dd.MM.yyyy: ");
        String dateStr = scanner.nextLine();
        Date date = ParseDate.parseDateFromString(dateStr);
        if (date != null) {
            System.out.println(shop.getAvailableFruits(date));
            System.out.println("----------------------------------------");
            System.out.println(shop.getSpoiledFruits(date, TypeOfFruit.banana));
            System.out.println("----------------------------------------");
            System.out.println(shop.getAvailableFruits(date, TypeOfFruit.mango));
            System.out.println("----------------------------------------");
            System.out.println(shop.getAddedFruits(date));
            System.out.println("----------------------------------------");
        }

        List<Client> clients = new ArrayList<>();
        clients.add(new Client("John", TypeOfFruit.pear, 1));
        clients.add(new Client("Ella", TypeOfFruit.banana, 2));
        clients.add(new Client("Sophia", TypeOfFruit.pear, 9));
        shop.saveOrder(new Order("25.07.2017", clients), ORDER_JSON);
        shop.sell(ORDER_JSON);
        // System.out.println(shop);
        System.out.println("----------------------------------------");

        Shop shop1 = new Shop();
        shop1.getFruitWarehouse().add(new Fruit(TypeOfFruit.lemon, 22, "29.08.2017", 7.50));
        shop1.getFruitWarehouse().add(new Fruit(TypeOfFruit.apple, 15, "18.08.2017", 3));
        List<Client> clients1 = new ArrayList<>();
        clients1.add(new Client("John", TypeOfFruit.lemon, 1));
        clients1.add(new Client("Ella", TypeOfFruit.mango, 2));
        shop1.saveOrder(new Order("01.09.2017", clients1), ORDER_JSON);
        shop1.sell(ORDER_JSON);

        Shop shop2 = new Shop();
        shop2.getFruitWarehouse().add(new Fruit(TypeOfFruit.nectarine, 9, "02.09.2017", 15.88));
        shop2.getFruitWarehouse().add(new Fruit(TypeOfFruit.mango, 15, "05.09.2017", 30));
        shop2.getFruitWarehouse().add(new Fruit(TypeOfFruit.nectarine, 9, "02.09.2017", 15.88));
        List<Client> clients2 = new ArrayList<>();
        clients2.add(new Client("John", TypeOfFruit.nectarine, 1));
        clients2.add(new Client("Ella", TypeOfFruit.mango, 1));
        shop2.saveOrder(new Order("07.09.2017", clients2), ORDER_JSON);
        shop2.sell(ORDER_JSON);

        System.out.println("----------------------------------------");
        Company company = new Company();
        company.getShops().add(shop);
        company.getShops().add(shop1);
        company.getShops().add(shop2);
        System.out.println(company.getCompanyBalance());
        System.out.println(company.getShop(1));
        System.out.println(company);
        company.save(COMPANY_JSON);
        company.load(COMPANY_JSON);
        System.out.println(company);
    }

}
