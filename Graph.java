import java.util.List;

public class Graph {
    private final List<City> city;
    private final List<Edge> edges;


    public Graph(List<City> city, List<Edge> edges) {
        this.city = city;
        this.edges = edges;
    }
    public List<Edge> getEdges() {
        return edges;
    }



}