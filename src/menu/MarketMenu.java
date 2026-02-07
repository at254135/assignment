package menu;

import database.StaffDAO;
import model.Cashier;
import model.Guard;
import model.Staff;

import java.util.List;
import java.util.Scanner;

public class MarketMenu implements Menu {
    private final Scanner scanner;
    private StaffDAO staffDAO;

    public MarketMenu() {
        this.scanner = new Scanner(System.in);
        this.staffDAO = new StaffDAO();

        System.out.println("\n=============================");
        System.out.println("New menu with database integration");
        System.out.println("=============================");
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
        System.out.println("6. Update staff");
        System.out.println("7. Delete staff");
        System.out.println("8. Search by name");
        System.out.println("9. Search by salary range");
        System.out.println("10. Salary >= X");
        System.out.println("11. Demonstrate polymorphism");
        System.out.println("0. Exit");
        System.out.println("=============================");
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.println("\nEnter your choice:");

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
                        updateStaff();
                        break;
                    case 7:
                        deleteStaff();
                        break;
                    case 8:
                        searchByName();
                        break;
                    case 9:
                        searchBySalaryRange();
                        break;
                    case 10:
                        searchHighPaidStaff();
                        break;
                    case 11:
                        demonstratePolymorphism();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Error: Invalid choice");
                }

                if (choice != 0) {
                    pressEnter();
                }
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("Error: Enter a valid number. " + e.getMessage());
                scanner.nextLine();
                pressEnter();
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
                pressEnter();
            }
        }

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

            staffDAO.insertCashier(new Cashier(id, name, age, salary, charisma));
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

            staffDAO.insertGuard(new Guard(id, name, age, salary, equipment));
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Invalid input type.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }

    private void viewStaff() {
        staffDAO.displayAllStaff();
    }

    private void viewCashiers() {
        List<Cashier> cashiers = staffDAO.getAllCashiers();

        System.out.println("\n==========================");
        System.out.println("Cashiers");
        System.out.println("==========================");

        if (cashiers.isEmpty()) {
            System.out.println("No cashiers in db");
        } else {
            for (int i = 0; i < cashiers.size(); i++) {
                Cashier cashier = cashiers.get(i);
                System.out.println((i+1) + ". " + cashier.toString());
                if (cashier.isGood()) {
                    System.out.println("    GOOD CASHIER (70+ charisma)");
                }

                System.out.println();
            }
            System.out.println("Total cashiers: " + cashiers.size());
        }
    }

    private void viewGuards() {
        List<Guard> guards = staffDAO.getAllGuards();

        System.out.println("\n==========================");
        System.out.println("Guards");
        System.out.println("==========================");

        if (guards.isEmpty()) {
            System.out.println("No guards in db");
        } else {
            for (int i = 0; i < guards.size(); i++) {
                Guard guard = guards.get(i);
                System.out.println((i+1) + ". " + guards.toString());
                if (guard.isPro()) {
                    System.out.println("    PROFICIENT (salary >= 80000)");
                }

                System.out.println();
            }
            System.out.println("Total guards: " + guards.size());
        }
    }

    private void updateStaff() {
        System.out.println("\n==========================");
        System.out.println("Update staff");
        System.out.println("==========================");
        System.out.print("Enter staff id to update: ");

        try {
            int staffId = scanner.nextInt();
            scanner.nextLine();

            Staff existingStaff = staffDAO.getStaffById(staffId);

            if (existingStaff == null) {
                System.out.println("No staff found with id: " + staffId);
                return;
            }

            System.out.println("Current info");
            System.out.println("---------------------");
            System.out.println(existingStaff.toString());

            System.out.println("\nEnter new values (or enter to keep)");
            System.out.println("---------------------");

            System.out.print("New name (" + existingStaff.getName() + "): ");
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) {
                newName = existingStaff.getName();
            }

            System.out.print("New age (" + existingStaff.getAge() + "): ");
            String ageInput = scanner.nextLine();
            int newAge = ageInput.trim().isEmpty() ?
                    existingStaff.getAge() : Integer.parseInt(ageInput);

            System.out.print("New salary (" + existingStaff.getSalary() + "): ");
            String salaryInput = scanner.nextLine();
            double newSalary = salaryInput.trim().isEmpty() ?
                    existingStaff.getSalary() : Double.parseDouble(salaryInput);

            if (existingStaff instanceof Cashier) {
                Cashier cashier = (Cashier) existingStaff;
                System.out.print("New charisma lvl (" + cashier.getCharisma() + "): ");
                String charismaInput = scanner.nextLine();
                int newCharisma = charismaInput.trim().isEmpty() ?
                        cashier.getCharisma() : Integer.parseInt(charismaInput);

                Cashier updatedCashier = new Cashier(staffId, newName, newAge, newSalary, newCharisma);
                staffDAO.updateCashier(updatedCashier);
            } else if (existingStaff instanceof Guard) {
                Guard guard = (Guard) existingStaff;
                System.out.print("New equipment (" + guard.getEquipment() + "): ");
                String newEquipment = scanner.nextLine();
                if (newEquipment.trim().isEmpty()) {
                    newEquipment = guard.getEquipment();
                }

                Guard updatedGuard = new Guard(staffId, newName, newAge, newSalary, newEquipment);
                staffDAO.updateGuard(updatedGuard);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid number format");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void deleteStaff() {
        System.out.println("\n==========================");
        System.out.println("Delete staff");
        System.out.println("==========================");
        System.out.print("Enter staff id to delete: ");

        try {
            int staffId = scanner.nextInt();
            scanner.nextLine();
            Staff staff = staffDAO.getStaffById(staffId);

            if (staff == null) {
                System.out.println("No staff found with this id: " + staffId);
                return;
            }

            System.out.println("Staff to delete:");
            System.out.println(staff.toString());
            System.out.println("Are you sure? y/n");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                staffDAO.deleteStaff(staffId);
            } else {
                System.out.println("Deletion canceled");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: invalid input");
            scanner.nextLine();
        }
    }

    private void searchByName() {
        System.out.println("\n==========================");
        System.out.println("Search by name");
        System.out.println("==========================");
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();

        List<Staff> results = staffDAO.searchByName(name);

        displaySearchResults(results, "Searching '" + name + "'");
    }

    private void searchBySalaryRange() {
        try {
            System.out.println("\n==========================");
            System.out.println("Search by salary range");
            System.out.println("==========================");
            System.out.print("Enter minimum salary: ");
            double minSalary = scanner.nextDouble();

            System.out.print("Enter maximum salary: ");
            double maxSalary = scanner.nextDouble();
            scanner.nextLine();

            List<Staff> results = staffDAO.searchBySalaryRange(minSalary, maxSalary);

            displaySearchResults(results, "Salary: " + minSalary + " - " + maxSalary + " KZT");

        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: invalid number");
            scanner.nextLine();
        }
    }

    private void searchHighPaidStaff() {
        try {
            System.out.println("\n==========================");
            System.out.println("High-paid staff");
            System.out.println("==========================");
            System.out.print("Enter minimum salary: ");
            double minSalary = scanner.nextDouble();
            scanner.nextLine();

            List<Staff> results = staffDAO.searchByMinSalary(minSalary);
            displaySearchResults(results, "Salary >= " + minSalary + " KZT");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: invalid number!");
            scanner.nextLine();
        }
    }

    private void displaySearchResults(List<Staff> results, String criteria) {
        System.out.println("\nSearch results");
        System.out.println("---------------------");
        System.out.println("Criteria: " + criteria);

        if (results.isEmpty()) {
            System.out.println("No staff found matching criteria.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                Staff s = results.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getRole() + "] ");
                System.out.println(s.toString());
            }
            System.out.println("---------------------");
            System.out.println("Total results: " + results.size());
        }
    }

    private void demonstratePolymorphism() {
        staffDAO.demonstratePolymorphism();
    }

    private void pressEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
