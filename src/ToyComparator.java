import java.util.Comparator;

class ToyComparator implements Comparator<Toy> {
    public int compare(Toy t1, Toy t2) {
        if ((double) t1.getWeight() <(double) t2.getWeight()) return 1;
        else if ((double) t1.getWeight() > (double)t2.getWeight()) return -1;
        return 0;
    }
}