import java.util.*;

public class Dijkstra {

    private List<Edge> edges;
    private Set<City> known;
    private Set<City> unknown;
    private Map<City, City> prior;
    private Map<City, Integer> distance;

    public Dijkstra(Graph graph) {
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    /**
     * Function that starts the magic
     * @param source of city currently looked at
     */
    public void execute(City source) {
        known = new HashSet<City>();
        unknown = new HashSet<City>();
        distance = new HashMap<City, Integer>();
        prior = new HashMap<City, City>();
        distance.put(source, 0);
        unknown.add(source);
        while (unknown.size() > 0) {
            City city = getMinimum(unknown);
            known.add(city);
            unknown.remove(city);
            findMinimalDistances(city);
        }
    }

    /**
     * Function that get minimum distances of adjacent
     * nodes/cities
     * @param city currently looked at
     */
    private void findMinimalDistances(City city) {
        List<City> adjacentNodes = getAdjacent(city);
        for (City target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(city) + getWeight(city, target)) {
                distance.put(target, getShortestDistance(city) + getWeight(city, target));
                prior.put(target, city);
                unknown.add(target);
            }
        }

    }

    /**
     * Function that return adjacent
     * nodes/cities
     * @param city currently looked at
     * @param target currently looked at
     * @return weight of city to target
     */
    protected int getWeight(City city, City target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(city)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("ERROR");
    }
    /**
     * Function that return adjacent
     * nodes/cities
     * @param city currently looked  at
     * @return adjacent neighbors
     */
    private List<City> getAdjacent(City city) {
        List<City> neighbors = new ArrayList<City>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(city) && (!known.contains(edge.getDestination()))) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private City getMinimum(Set<City> citySet) {
        City minimum = null;
        for (City city: citySet) {
            if (minimum == null) {
                minimum = city;
            } else {
                if (getShortestDistance(city) < getShortestDistance(minimum)) {
                    minimum = city;
                }
            }
        }
        return minimum;
    }
    /**
     * Function that gets the shortest distance current next city
     * @param destination of the user
     */
    private int getShortestDistance(City destination) {
        Integer dist = distance.get(destination);
        return Objects.requireNonNullElse(dist, Integer.MAX_VALUE);
    }

    /**
     * Function that gives the best route
     * based on target
     * @param target of the user
     * @return best path to target on a LinkedList
     */
    public LinkedList<City> getPath(City target) {
        LinkedList<City> path = new LinkedList<City>();
        City city = target;
        if (prior.get(city) == null) {
            return null;
        }
        path.add(city);
        while (prior.get(city) != null) {
            city = prior.get(city);
            path.add(city);
        }
        Collections.reverse(path);
        return path;
    }
}