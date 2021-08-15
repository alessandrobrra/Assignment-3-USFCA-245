public class Edge  {
    private final City source;
    private final City destination;
    private final int weight;

    public Edge(City source, City destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public City getDestination() {
        return destination;
    }

    public City getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}