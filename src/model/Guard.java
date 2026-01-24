package model;

public class Guard extends Staff {
    String equipment;

    public Guard(int id, String name, int age, double salary, String equipment) {
        super(id, name, age, salary);
        setEquipment(equipment);
    }

    public void setEquipment(String equipment) {
        if (equipment == null || equipment.isEmpty()) {
            throw new IllegalArgumentException(
                    "(Guard): equipment must not be empty");
        }
        this.equipment = equipment;
    }

    @Override
    public void work() {
        System.out.println("Guard " + name + " protects the market with " + equipment);
    }

    public boolean isPro() {
        return salary >= 80000;
    }

    @Override
    public String getRole() {
        return "Guard";
    }

    @Override
    public String toString() {
        return super.toString() + ", equiped with " + equipment;
    }
}
