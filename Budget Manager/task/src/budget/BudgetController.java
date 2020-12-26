package budget;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetController {
    private double balance;
    private double total;
    private List<Purchase> items;
    private final File file = new File("purchases.txt");

    public BudgetController() {
        total = 0;
        balance = 0;
        items = new ArrayList<>();
    }

    public void addIncome(double income) {
        balance += income;
    }

    public double getBalance() {
        return balance;
    }

    public void addItem(Purchase item) {
        items.add(item);
        balance -= item.getPrice();
    }

    public List<Purchase> getItems(PurchaseType type) {
        List<Purchase> list = new ArrayList<>();
        for (Purchase item:items) {
            if (item.getType() == type) {
                list.add(item);
            }
        }
        return list;
    }

    public List<Purchase> getAll() {
        return items;
    }

    public double getSum(List<Purchase> list) {
        total = 0;
        list.forEach(item -> total += item.getPrice());

        return total;
    }

    public void saveChanges() {
        try(ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(file))) {
                input.writeObject(items);
                input.writeDouble(balance);
        } catch (IOException ex) {
            System.out.println("Save file error");
        }
    }

    public void loadSaves() {
        items.clear();
        try(ObjectInputStream output = new ObjectInputStream(new FileInputStream(file))) {
                items = (ArrayList) output.readObject();
                balance = output.readDouble();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Load file error");
        }
    }
    
}
