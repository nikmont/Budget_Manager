package budget;

import java.util.*;
import java.util.stream.Collectors;

public class BudgetUI {

    private static final BudgetController controller = new BudgetController();
    private static final Scanner scan = new Scanner(System.in);
    private static CategoryStrategy currentCategory;
    private static List<Purchase> list;
    private static String currentTypeName = "";

    public static void start() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\nChoose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit");

            switch (Integer.parseInt(scan.nextLine())) {
                case 1:
                    System.out.println("\nEnter income:");
                    controller.addIncome(Double.parseDouble(scan.nextLine()));
                    System.out.println("Income was added!");
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    showList();
                    break;
                case 4:
                    System.out.printf("%nBalance: $%.2f%n", controller.getBalance());
                    break;
                case 5:
                    controller.saveChanges();
                    System.out.println("\nPurchases were saved!");
                    break;
                case 6:
                    controller.loadSaves();
                    System.out.println("\nPurchases were loaded!");
                    break;
                case 7:
                    analyze();
                    break;
                case 0:
                    System.out.println("\nBye!");
                    isExit = true;
                    break;
            }
        }
    }

    private static void addPurchase() {

        boolean isBack = false;

        while (!isBack) {
            System.out.println("\nChoose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");

            switch (Integer.parseInt(scan.nextLine())) {
                case 1:
                    currentCategory = new FoodPurchase();
                    break;
                case 2:
                    currentCategory = new ClothesPurchase();
                    break;
                case 3:
                    currentCategory = new EntertaimentPurchase();
                    break;
                case 4:
                    currentCategory = new OtherPurchase();
                    break;
                case 5:
                    isBack = true;
                    continue;
            }

            controller.addItem(currentCategory.create());
            System.out.println("Purchase was added!");
        }
    }

    private static void showList() {

        if (controller.getAll().size() == 0) {
            System.out.println("\nPurchase list is empty!");
            return;
        }

        boolean isBack = false;
        while (!isBack) {
            System.out.println("\nChoose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");

            int userInput = Integer.parseInt(scan.nextLine());

            if (userInput != 6) {
                list = getByType(userInput);
                System.out.println("\n" + currentTypeName + ":");
                if (list.size() != 0) {
                    list.forEach(System.out::println);
                    System.out.printf("Total sum: $%.2f%n", controller.getSum(list));
                } else System.out.println("Purchase list is empty!");
            } else { isBack = true; }

        }
    }

    private static List<Purchase> getByType(int type) {

        switch (type) {
            case 1:
                currentTypeName = "Food";
                list = controller.getItems(PurchaseType.FOOD);
                break;
            case 2:
                currentTypeName = "Clothes";
                list = controller.getItems(PurchaseType.CLOTHES);
                break;
            case 3:
                currentTypeName = "Entertainment";
                list = controller.getItems(PurchaseType.ENTERTAIMENT);
                break;
            case 4:
                currentTypeName = "Other";
                list = controller.getItems(PurchaseType.OTHER);
                break;
            case 5:
                currentTypeName = "All";
                list = controller.getAll();
                break;
        }

        return list;
    }

    private static void analyze() {
        boolean isBack = false;

        while (!isBack) {
            System.out.println("\nHow do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");

            switch (Integer.parseInt(scan.nextLine())) {
                case 1:
                    list = getByType(5);
                    if (list.size() != 0) {
                        Collections.sort(list);
                        System.out.println("\n" + currentTypeName + ":");
                        list.forEach(System.out::println);
                        System.out.printf("Total: $%.2f%n", controller.getSum(list));
                    } else System.out.println("\nPurchase list is empty!");
                    break;
                case 2:
                    Map<String, Double> types = new TreeMap<>();
                    types.put("Food", controller.getSum(getByType(1)));
                    types.put("Clothes", controller.getSum(getByType(2)));
                    types.put("Entertainment", controller.getSum(getByType(3)));
                    types.put("Other", controller.getSum(getByType(4)));

                    Map<String, Double> sortedMap =
                            types.entrySet().stream()
                                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                            (e1, e2) -> e1, LinkedHashMap::new));

                    System.out.println("\nTypes:");
                    for (var type:sortedMap.entrySet()) {
                        System.out.printf("%s - $%.2f%n", type.getKey(), type.getValue());
                    }
                    System.out.printf("Total sum: $%.2f%n", controller.getSum(getByType(5)));

                    break;
                case 3:
                    System.out.println("\nChoose the type of purchase\n" +
                            "1) Food\n" +
                            "2) Clothes\n" +
                            "3) Entertainment\n" +
                            "4) Other");

                    list = getByType(Integer.parseInt(scan.nextLine()));
                    if (list.size() != 0) {
                        Collections.sort(list);
                        System.out.println("\n" + currentTypeName + ":");
                        list.forEach(System.out::println);
                        System.out.printf("Total: $%.2f%n", controller.getSum(list));
                    } else System.out.println("\nPurchase list is empty!");
                    break;
                case 4:
                    isBack = true;
            }
        }
    }
}
