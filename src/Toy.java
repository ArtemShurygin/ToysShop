
public class Toy {
    private int id;
    private String name;
    private int quantity;
    private double frequencyCoef;
    private int weight;

    public Toy(int id, String name, int quantity, int frequencyCoef) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.frequencyCoef = frequencyCoef;
    }

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getFrequencyCoef() {
        return frequencyCoef;
    }

    public int getWeight() {
        return weight;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFrequencyCoef(double frequencyCoef) {
        this.frequencyCoef = frequencyCoef;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {

        return "id: " + id + ", name: " + name + ", quantity: " + quantity + ", frequencyCoef: " + frequencyCoef;
    }
}
