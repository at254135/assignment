package menu;

import errors.OutOfBoundsChoice;
import model.Cashier;
import model.CatalogItem;
import model.Guard;
import model.Staff;

import java.util.ArrayList;
import java.util.Scanner;

public class MarketMenu implements Menu {
    private final Scanner scanner;
    private ArrayList<Staff> staffList;
    private ArrayList<CatalogItem> catalog;

    public MarketMenu() {
        staffList = new ArrayList<>();
        catalog = new ArrayList<>();
        scanner = new Scanner(System.in);

        try {
            staffList.add(new Cashier(100, "Mukhamed", 20, 55000, 94));
            staffList.add(new Guard(200, "Artur", 35, 90000, "pistol"));

            catalog.add(new CatalogItem("Milk", 500, "Milk products"));
            catalog.add(new CatalogItem("Phone", 90000, "Electronics"));
            catalog.add(new CatalogItem("Pen", 90000, "Stationery"));
        } catch (IllegalArgumentException e) {
            System.out.println("Error initialising test data. " + e.getMessage());
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=============================");
        System.out.println("Grocery Market Menu");
        System.out.println("=============================");
        System.out.println("1. Add cashier");
        System.out.println("2. Add guard");
        System.out.println("3. View all staff");
        System.out.println("4. View cashiers only");
        System.out.println("5. View guards only");
        System.out.println("6. Make all staff work (polymorphism)");
        System.out.println("7. Add item");
        System.out.println("8. View all catalog items");
        System.out.println("9. Buy an item");
        System.out.println("0. Exit");
        System.out.println("=============================");
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.println("Enter your choice:");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addCashier();
                        break;
                    case 2:
                        addGuard();
                        break;
                    case 3:
                        viewStaff();
                        break;
                    case 4:
                        viewCashiers();
                        break;
                    case 5:
                        viewGuards();
                        break;
                    case 6:
                        demonstratePolymorphism();
                        break;
                    case 7:
                        addItem();
                        break;
                    case 8:
                        viewItems();
                        break;
                    case 9:
                        buyItem();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Error: Invalid choice");
                }
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("Error: Enter a valid number. " + e.getMessage());
                scanner.nextLine();
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    private void addCashier() {
        System.out.println("\n--- Add cashier ---");

        try {
            System.out.println("Enter id:");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter name:");
            String name = scanner.nextLine();

            System.out.println("Enter salary:");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter age:");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter charisma (0-100):");
            int charisma = scanner.nextInt();
            scanner.nextLine();

            staffList.add(new Cashier(id, name, age, salary, charisma));
            System.out.println("Cashier " + name + " added.");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Invalid input type.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Validation failed - " + e.getMessage());
        }
    }

    private void addGuard() {
        System.out.println("\n--- Add guard ---");

        try {
            System.out.println("Enter id:");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter name:");
            String name = scanner.nextLine();

            System.out.println("Enter salary:");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter age:");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter equipment:");
            String equipment = scanner.nextLine();

            staffList.add(new Guard(id, name, age, salary, equipment));
            System.out.println("Guard " + name + " added.");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Invalid input type.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }

    private void viewStaff() {
        System.out.println("\n==========================");
        System.out.println("All staff");
        System.out.println("==========================");

        if (staffList.isEmpty()) {
            System.out.println("No staff members found.");
            return;
        }

        for (int i = 0; i < staffList.size(); i++) {
            Staff s = staffList.get(i);
            System.out.print((i + 1) + ". ");

            if (s instanceof Cashier) {
                System.out.print("[cashier] ");
                Cashier cashier = (Cashier) s;
                if (cashier.isGood()) {
                    System.out.println("TOP CASHIER!");
                }
            } else if (s instanceof Guard) {
                System.out.print("[guard] ");
                Guard guard = (Guard) s;
                if (guard.isPro()) {
                    System.out.println("PROFICIENT GUARD!");
                }
            }

            System.out.println(s.toString());
        }
    }

    private void viewCashiers() {
        System.out.println("\n==========================");
        System.out.println("Cashiers");
        System.out.println("==========================");

        boolean found = false;

        for (Staff s : staffList) {
            if (s instanceof Cashier) {
                Cashier cashier = (Cashier) s;
                System.out.println(cashier.toString());
                if (cashier.isGood()) {
                    System.out.println("    GOOD CASHIER (70+ charisma)");
                }

                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No cashiers found.");
        }
    }

    private void viewGuards() {
        System.out.println("\n==========================");
        System.out.println("Guards");
        System.out.println("==========================");

        boolean found = false;

        for (Staff s : staffList) {
            if (s instanceof Guard) {
                Guard guard = (Guard) s;
                System.out.println(guard.toString());
                if (guard.isPro()) {
                    System.out.println("    PROFICIENT (salary >= 80000)");
                }
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No guards found.");
        }
    }

    private void demonstratePolymorphism() {
        System.out.println("\n==========================");
        System.out.println("Polymorphism");
        System.out.println("==========================");

        if (staffList.isEmpty()) {
            System.out.println("No staff found.");
            return;
        }

        for (Staff s : staffList) {
            s.work();
        }
    }

    private void addItem() {
        try {
            System.out.println("\n--- Add item ---");
            System.out.println("Enter item name:");
            String name = scanner.nextLine();

            System.out.println("Enter price:");
            double price = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter category:");
            String category = scanner.nextLine();

            catalog.add(new CatalogItem(name, price, category));
            System.out.println("Added item successfully!");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: invalid input type");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }

    private void viewItems() {
        System.out.println("\n==========================");
        System.out.println("View catalog items");
        System.out.println("==========================");

        if (catalog.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        for (int i = 0; i < catalog.size(); i++) {
            System.out.println((i + 1) + ". " + catalog.get(i).toString());
        }
    }

    private void buyItem() {
        System.out.println("\n--- Buying item ---");

        if (catalog.isEmpty()) {
            System.out.println("No available items");
            return;
        }

        for (int i = 0; i < catalog.size(); i++) {
            System.out.println((i + 1) + ". " + catalog.get(i).getName());
        }

        try {
            System.out.print("Select item to buy: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > catalog.size()) {
                throw new OutOfBoundsChoice("Invalid item number!");
            }

            CatalogItem item = catalog.get(choice - 1);
            item.buy();
            item.deliver();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Input a valid number");
            scanner.nextLine();
        } catch (OutOfBoundsChoice e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
