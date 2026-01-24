package model;

public class CatalogItem implements Buyable {
    String name;
    double price;
    String category;

    public CatalogItem(String name, double price, String category) {
        setName(name);
        setPrice(price);
        setCategory(category);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "(CatalogItem): name must not be empty.");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException(
                    "(CatalogItem): price must be positive");
        }
        this.price = price;
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "(CatalogItem): category must not be empty.");
        }
        this.category = category;
    }

    @Override
    public void buy() {
        System.out.println("Buying " + name + " (" + category + ")...");
        System.out.println("Checking price... " + price);
        System.out.println("Packing...");
        System.out.println("Success! " + name + " will be delivered soon!");
    }

    @Override
    public void deliver() {
        System.out.println("Deliver placed! " + name + " will be delivered soon!" );
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }


    @Override
    public String toString() {
        return "[" + category + "] " + name + " - " + price + " KZT";
    }
}
