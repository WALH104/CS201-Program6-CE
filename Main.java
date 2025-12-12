import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ExchangeManager manager = new ExchangeManager();
        String file = "clothing.csv";

        // try to load existing data (no error if missing)
        manager.loadFromCSV(file);

        while (true) {
            System.out.println("\nSCLOTHING EXCHANGE MENU ");
            System.out.println("1. Add Item");
            System.out.println("2. Claim Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Modify Donor Name");
            System.out.println("5. Search Items");
            System.out.println("6. Show All Items");
            System.out.println("7. Show Available Items");
            System.out.println("8. Save");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = getNumber(in);

            if (choice == 1) {
                addItem(in, manager);
            } else if (choice == 2) {
                System.out.print("Enter ID to claim: ");
                int id = getNumber(in);
                if (manager.claimItem(id)) System.out.println("Item claimed.");
                else System.out.println("Not found or already taken.");
            } else if (choice == 3) {
                System.out.print("Enter ID to remove: ");
                int id = getNumber(in);
                if (manager.removeItem(id)) System.out.println("Item removed.");
                else System.out.println("Not found.");
            } else if (choice == 4) {
                System.out.print("Enter ID to modify: ");
                int id = getNumber(in);
                System.out.print("Enter new donor name: ");
                String name = in.nextLine();
                if (manager.updateItem(id, name)) System.out.println("Donor updated.");
                else System.out.println("Not found.");
            } else if (choice == 5) {
                searchMenu(in, manager);
            } else if (choice == 6) {
                List<Clothing> all = manager.getAllItems();
                if (all.isEmpty()) System.out.println("No items.");
                else for (Clothing c : all) System.out.println(c);
            } else if (choice == 7) {
                List<Clothing> avail = manager.getAvailableItems();
                if (avail.isEmpty()) System.out.println("No available items.");
                else for (Clothing c : avail) System.out.println(c);
            } else if (choice == 8) {
                if (manager.saveToCSV(file)) System.out.println("Saved to " + file);
                else System.out.println("Save failed.");
            } else if (choice == 9) {
                // save on exit
                manager.saveToCSV(file);
                System.out.println("Saved and exiting. Goodbye.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        in.close();
    }

    //helpers

    // safe integer input
    private static int getNumber(Scanner in) {
        while (true) {
            String line = in.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (Exception e) {
                System.out.print("Please enter a number: ");
            }
        }
    }

    // add item (simple menus for type/size/condition)
    private static void addItem(Scanner in, ExchangeManager manager) {
        System.out.println("\nChoose type:");
        System.out.println("1. Shirt");
        System.out.println("2. Pants");
        System.out.println("3. Jacket");
        System.out.println("4. Shoes");
        System.out.println("5. Other");
        System.out.print("Choice: ");
        int t = getNumber(in);
        String type = "Other";
        if (t == 1) type = "Shirt";
        else if (t == 2) type = "Pants";
        else if (t == 3) type = "Jacket";
        else if (t == 4) type = "Shoes";

        System.out.println("\nChoose size:");
        System.out.println("1. XS");
        System.out.println("2. S");
        System.out.println("3. M");
        System.out.println("4. L");
        System.out.println("5. XL");
        System.out.print("Choice: ");
        int s = getNumber(in);
        String size = "Unknown";
        if (s == 1) size = "XS";
        else if (s == 2) size = "S";
        else if (s == 3) size = "M";
        else if (s == 4) size = "L";
        else if (s == 5) size = "XL";

        System.out.println("\nChoose condition:");
        System.out.println("1. New");
        System.out.println("2. Good");
        System.out.println("3. Fair");
        System.out.println("4. Worn");
        System.out.print("Choice: ");
        int c = getNumber(in);
        String condition = "Unknown";
        if (c == 1) condition = "New";
        else if (c == 2) condition = "Good";
        else if (c == 3) condition = "Fair";
        else if (c == 4) condition = "Worn";

        System.out.print("\nDonor name: ");
        String donor = in.nextLine().trim();

        // create with automatic date/time string
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // manager expects addClothing(type,size,condition,donor,available)
        // but our ExchangeManager implementation stores date/time automatically when adding (or you may use a constructor that accepts dateTime)
        Clothing created = manager.addClothing(type, size, condition, donor, true);
        if (created != null) {
            // If your Clothing stores date/time inside constructor, it's already set.
            // If you want to overwrite dateTime field (depending on your Clothing class), you can add a setter or use DonationItem creation in manager.
            System.out.println("Added: " + created);
        } else {
            System.out.println("Item not added (possible duplicate).");
        }
    }

    // search menu
    private static void searchMenu(Scanner in, ExchangeManager manager) {
        System.out.println("\nSearch by:");
        System.out.println("1. Type");
        System.out.println("2. Size");
        System.out.println("3. Condition");
        System.out.print("Choice: ");
        int choice = getNumber(in);
        System.out.print("Enter search text: ");
        String text = in.nextLine().trim();

        List<Clothing> results = new ArrayList<>();
        if (choice == 1) results = manager.searchByType(text);
        else if (choice == 2) results = manager.searchBySize(text);
        else if (choice == 3) results = manager.searchByCondition(text);
        else {
            System.out.println("Invalid search choice.");
            return;
        }

        if (results.isEmpty()) System.out.println("No results.");
        else for (Clothing c : results) System.out.println(c);
    }
}
