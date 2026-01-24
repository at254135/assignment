package model;

public class Cashier extends Staff {
    private int charisma;

    public Cashier(int id, String name, int age, double salary, int charisma) {
        super(id, name, age, salary);
        setCharisma(charisma);
    }

    public void check(String product) {
        System.out.println("Cashier " + name + " checking " + product + "...");
    }

    public void setCharisma(int charisma) {
        if (charisma < 0 || charisma > 100) {
            throw new IllegalArgumentException(
                    "(Cashier): charisma must be limited in range from 0 to 100");
        }
        this.charisma = charisma;
    }

    public boolean isGood() {
        return charisma >= 70;
    }

    @Override
    public void work() {
        System.out.println("Cashier " + name + " serves buyers.");
    }

    @Override
    public String getRole() {
        return "Cashier";
    }

    @Override
    public String toString() {
        return super.toString() + ", charisma: " + charisma;
    }
}
