package budget;

public class OtherPurchase implements CategoryStrategy {
    @Override
    public Purchase create() {

        System.out.println("\nEnter purchase name:");
        String name = scan.nextLine();
        System.out.println("Enter its price:");
        double price = Double.parseDouble(scan.nextLine());

        return new Purchase(PurchaseType.OTHER, name, price);
    }
}
