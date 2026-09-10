import java.util.Scanner;

public class Canteen {

  // handles user input
  static Scanner s = new Scanner(System.in);
  static char studentInput = ' ';

  // stores the available items
  static String[] itemNames = {
    "Burger",
    "Pizza",
    "Pasta",
    "Sandwich",
    "Milk Tea",
  };

  // stores the price of each item
  static float[] itemPrices = { 80, 120, 100, 70, 90 };

  // keeps track of the order totals
  static int totalItemsCount = 0;
  static float totalBeforeDiscount = 0f;
  static float totalDiscountAmt = 0f;
  static float totalFinalAmount = 0f;

  // displays the available items
  static void displayMenu() {
    System.out.println("=====   M E N U   =====");
    for (int i = 0; i < itemNames.length; i++) {
      System.out.printf(
        "%d. %-12s - $%.2f%n",
        i + 1,
        itemNames[i],
        itemPrices[i]
      );
    }
    System.out.println();
  }

  // gets the order details from the user
  static String[] takeOrder() {
    System.out.print("Enter item number: ");
    int itemNumber = s.nextInt();
    boolean isError1 = inputCheckerItem(itemNumber);

    System.out.print("Enter quantity: ");
    int quantity = s.nextInt();
    boolean isError2 = inputCheckerQty(quantity);

    // checks for invalid item or quantity
    if (isError1 || isError2) {
      System.out.println(displayErrorMessage(isError1, isError2, false));
      return new String[] { "error" };
    }

    System.out.print("Are you a student? (Y/N): ");
    studentInput = s.next().charAt(0);
    boolean isError3 = inputCheckerStudent(studentInput);

    System.out.println();

    // checks the student input
    if (isError3) {
      System.out.println(displayErrorMessage(false, false, isError3));
      return new String[] { "error" };
    }

    return new String[] {
      String.valueOf(itemNumber),
      String.valueOf(quantity),
      String.valueOf(studentInput),
    };
  }

  // checks if the item number is valid
  static boolean inputCheckerItem(int itemNumber) {
    return (itemNumber < 1 || itemNumber > itemNames.length);
  }

  // checks if the quantity is within the allowed range
  static boolean inputCheckerQty(int quantity) {
    return (quantity < 1 || quantity > 10);
  }

  // checks if the student input is valid
  static boolean inputCheckerStudent(char studentInput) {
    return !String.valueOf(studentInput).matches("[YN]");
  }

  // returns the appropriate error message
  static String displayErrorMessage(
    boolean isError1,
    boolean isError2,
    boolean isError3
  ) {
    if (isError1 && isError2 && isError3) {
      return "Invalid order! Please enter a valid item number, quantity, and character.";
    } else if (isError1 && isError2) {
      return "Invalid order! Please enter a valid item number and quantity.";
    } else if (isError1 && isError3) {
      return "Invalid order! Please enter a valid item number and character.";
    } else if (isError2 && isError3) {
      return "Invalid order! Please enter a valid quantity and character.";
    } else if (isError1) {
      return "Invalid order! Please enter a valid item number.";
    } else if (isError2) {
      return "Invalid order! Please enter a valid quantity.";
    } else if (isError3) {
      return "Invalid order! Please enter a valid character.";
    } else {
      return "";
    }
  }

  // calculates the discount and order total
  static void processOrder(String[] orderDetails) {
    int itemNumber = Integer.parseInt(orderDetails[0]);
    int quantity = Integer.parseInt(orderDetails[1]);
    char studentInput = orderDetails[2].charAt(0);

    float subtotal = itemPrices[itemNumber - 1] * quantity;
    float discount = 0f;

    // applies the appropriate discount
    if (studentInput == 'Y') {
      if (subtotal >= 500) {
        discount = subtotal * .15f;
      } else {
        discount = subtotal * .10f;
      }
    } else {
      if (subtotal >= 500) {
        discount = subtotal * .05f;
      }
    }

    float total = subtotal - discount;

    System.out.printf("Subtotal: $%.2f\n", subtotal);
    System.out.printf("Discount: $%.2f\n", discount);
    System.out.printf("Order total: $%.2f\n", total);

    // updates the overall order totals
    totalItemsCount += quantity;
    totalBeforeDiscount += subtotal;
    totalDiscountAmt += discount;
    totalFinalAmount += total;
  }

  // displays the totals after ordering
  static void displayOrderSummary() {
    System.out.println("===== ORDER SUMMARY =====");
    System.out.printf("Total items: %d\n", totalItemsCount);
    System.out.printf("Total before discount: $%.2f\n", totalBeforeDiscount);
    System.out.printf("Total discount: $%.2f\n", totalDiscountAmt);
    System.out.printf("Final amount: $%.2f\n", totalFinalAmount);
    System.out.println("Thank you for ordering!");
  }

  // asks if the user wants another order
  static char askOrderAgain() {
    char willOrderAgain = ' ';

    do {
      System.out.println();
      System.out.print("Do you want to order again? (Y/N): ");
      willOrderAgain = s.next().charAt(0);
      System.out.println();
    } while (willOrderAgain != 'Y' && willOrderAgain != 'N');

    return willOrderAgain;
  }

  public static void main(String[] args) {
    displayMenu();

    while (true) {
      String[] orderResult = takeOrder();

      // processes the order if the input is valid
      if (orderResult.length == 3) {
        processOrder(orderResult);
      }

      char willOrderAgain = askOrderAgain();

      if (willOrderAgain == 'Y') {
        continue;
      } else {
        displayOrderSummary();
        s.close();
        break;
      }
    }
  }
}
