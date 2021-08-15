public class Data {
    int cost;
    String origin;
    String destination;


    public Data(String origin, String destination, int cost){
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }
}
