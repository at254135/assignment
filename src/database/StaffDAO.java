package database;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    public boolean insertCashier(Cashier cashier) {
        String sql = "INSERT INTO staff (name, age, salary, staff_type, charisma, equipment) " +
            "VALUES (?, ?, ?, 'CASHIER', ?, NULL)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cashier.getName());
            statement.setInt(2, cashier.getAge());
            statement.setDouble(3, cashier.getSalary());
            statement.setInt(4, cashier.getCharisma());

            int rowsInserted = statement.executeUpdate();
            statement.close();
            
            if (rowsInserted > 0) {
                System.out.println("Cashier inserted: " + cashier.getName());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Insert cashier failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }
    
    public boolean insertGuard(Guard guard) {
        String sql = "insert into staff (name, age, salary, staff_type, equipment) " +
            "VALUES (?, ?, ?, 'GUARD', NULL, ?)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, guard.getName());
            statement.setInt(2, guard.getAge());
            statement.setDouble(3, guard.getSalary());
            statement.setString(4, guard.getEquipment());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("Guard inserted: " + guard.getName());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Insert guard failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("Found " + staffList.size() + " staff from database");
        } catch (SQLException e) {
            System.out.println("Select all staff failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public List<Cashier> getAllCashiers() {
        List<Cashier> cashiers = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'CASHIER' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return cashiers;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int staffId = resultSet.getInt("staff_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double salary = resultSet.getDouble("salary");
                int charisma = resultSet.getInt("charisma");

                cashiers.add(new Cashier(staffId, name, age, salary, charisma));
            }

            resultSet.close();
            statement.close();
            System.out.println("Retrieved " + cashiers.size() + " cashiers from database");
        } catch (SQLException e) {
            System.out.println("Selecting cashiers has failed.");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return cashiers;
    }

    public List<Guard> getAllGuards() {
        List<Guard> guards = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'GUARD' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return guards;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int staffId = resultSet.getInt("staff_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double salary = resultSet.getDouble("salary");
                String equipment = resultSet.getString("equipment");

                guards.add(new Guard(staffId, name, age, salary, equipment));
            }

            resultSet.close();
            statement.close();
            System.out.println("Retrieved " + guards.size() + " guards from database");
        } catch (SQLException e) {
            System.out.println("Selecting guards has failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return guards;
    }

    public Staff getStaffById(int staffId) {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double salary = resultSet.getDouble("salary");
                String staffType = resultSet.getString("staff_type");
                Staff staff = null;

                if ("CASHIER".equals(staffType)) {
                    int charisma = resultSet.getInt("charisma");
                    staff = new Cashier(staffId, name, age, salary, charisma);
                } else if ("GUARD".equals(staffType)) {
                    String equipment = resultSet.getString("equipment");
                    staff = new Guard(staffId, name, age, salary, equipment);
                }

                resultSet.close();
                statement.close();
                if (staff != null) {
                    System.out.println("Found staff by id: " + staffId);
                }

                return staff;
            }

            System.out.println("No staff with id " + staffId + " were found..");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Select by id failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return null;
    }

    public void displayAllStaff() {
        List<Staff> staffList = getAllStaff();

        System.out.println("\n==========================");
        System.out.println("All staff");
        System.out.println("==========================");
        if (staffList.isEmpty()) {
            System.out.println("No staff members in database");
        } else {
            for (int i = 0; i < staffList.size(); i++) {
                Staff s = staffList.get(i);

                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getRole() + "] ");
                System.out.println(s.toString());
            }
        }

        System.out.println("==========================\n");
    }

    public void demonstratePolymorphism() {
        List<Staff> staffList = getAllStaff();
        System.out.println("\n==========================");
        System.out.println("Polymorphism: staff from database");
        System.out.println("==========================");

        if (staffList.isEmpty()) {
            System.out.println("No staff to demonstrate.");
        } else {
            for (Staff s : staffList) {
                s.work();
            }
        }

        System.out.println("==========================\n");
    }

    public boolean updateCashier(Cashier cashier) {
        String sql = "UPDATE staff SET name = ?, age = ?, salary = ?, charisma = ? " +
                "WHERE staff_id = ? AND staff_type = 'CASHIER'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cashier.getName());
            statement.setInt(2, cashier.getAge());
            statement.setDouble(3, cashier.getSalary());
            statement.setInt(4, cashier.getCharisma());
            statement.setInt(5, cashier.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("Cashier updated: " + cashier.getName());
                return true;
            } else {
                System.out.println("No cashier with this id found: " + cashier.getStaffId());
            }

        } catch (SQLException e) {
            System.out.println("Updating cashier failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean updateGuard(Guard guard) {
        String sql = "UPDATE staff SET name = ?, age = ?, salary = ?, equipment = ? " +
                "WHERE staff_id = ? AND staff_type = 'GUARD'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, guard.getName());
            statement.setInt(2, guard.getAge());
            statement.setDouble(3, guard.getSalary());
            statement.setString(4, guard.getEquipment());
            statement.setInt(5, guard.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("Guard updated: " + guard.getName());
                return true;
            } else {
                System.out.println("No guard with this id found: " + guard.getStaffId());
            }

        } catch (SQLException e) {
            System.out.println("Updating guard failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean deleteStaff(int staffId) {
        String sql = "DELETE FROM staff WHERE staff_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            if (rowsDeleted > 0) {
                System.out.println("Deleted staff with " + staffId + " id");
                return true;
            } else {
                System.out.println("No staff found with id " + staffId);
            }

        } catch (SQLException e) {
            System.out.println("Delete failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public List<Staff> searchByName(String name) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE name ILIKE ? ORDER BY name";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("Found " + staffList.size() + " staff matching '" + name + "'");

        } catch (SQLException e) {
            System.out.println("Searching by name failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public List<Staff> searchBySalaryRange(double minSalary, double maxSalary) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE salary BETWEEN ? AND ? ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);
            statement.setDouble(2, maxSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("Found " + staffList.size() + " staff in this salary range " +
                    minSalary + " - " + maxSalary);

        } catch (SQLException e) {
            System.out.println("Searching by salary failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    public List<Staff> searchByMinSalary(double minSalary) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE salary >= ? ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("Found " + staffList.size() + " staff, whose income >= " + minSalary);

        } catch (SQLException e) {
            System.out.println("Searching by min salary failed");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    private Staff extractStaffFromResultSet(ResultSet resultSet) throws SQLException {
        int staffId = resultSet.getInt("staff_id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        double salary = resultSet.getDouble("salary");
        String staffType = resultSet.getString("staff_type");

        Staff staff = null;

        if ("CASHIER".equals(staffType)) {
            int charisma = resultSet.getInt("charisma");
            staff = new Cashier(staffId, name, age, salary, charisma);

        } else if ("GUARD".equals(staffType)) {
            String equipment = resultSet.getString("equipment");
            staff = new Guard(staffId, name, age, salary, equipment);
        }

        return staff;
    }
}
