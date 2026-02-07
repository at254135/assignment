import database.StaffDAO;
import menu.MarketMenu;
import menu.Menu;

public class Main {
    public static void main(String[] args) {
        Menu marketMenu = new MarketMenu();
        marketMenu.run();
    }
}