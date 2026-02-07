package model;

public abstract class Staff {
    protected int staffId;
    protected String name;
    protected int age;
    protected double salary;

    public Staff(int id, String name, int age, double salary) {
        setStaffId(id);
        setName(name);
        setAge(age);
        setSalary(salary);
    }

    public void setStaffId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "(Staff): positive non-zero id expected, got " + this.staffId);
        }
        this.staffId = id;
    }

    public void setAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException(
                    "(Staff): age must be 18 or more, got " + this.age);
        }
        this.age = age;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "(Staff): name must not be empty.");
        }
        if (name.length() < 2) {
            throw new IllegalArgumentException(
                    "(Staff): name must have at least 3 characters.");
        }
        this.name = name;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException(
                    "(Staff): That would be great, but salary cannot be negative.");
        }
        this.salary = salary;
    }

    public abstract void work();
    public abstract String getRole();

    public int getStaffId() { return staffId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return "[" + getRole() + "] " + name + "@" + staffId + "\n" +
                "age: " + age + ", salary: " + salary;
    }
}
