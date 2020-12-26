package budget;

import java.util.Scanner;

public interface CategoryStrategy {
    final Scanner scan = new Scanner(System.in);
    public Purchase create();
}
