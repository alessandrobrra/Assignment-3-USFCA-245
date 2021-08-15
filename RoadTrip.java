import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RoadTrip {
    public Hashtable<String, String> attractions;
    public List<String> citiesList;
    public List<Data> roads;

    public List<City> cities;
    public List<Edge> edges;
    public List<City> answer;
    Dijkstra dijkstraAlgorithm;
    String[] nameOfCityArr;
    Integer[] numberOfCityArr;

    /**
     * Function that  reads attractions
     * and stores them in a Hashtable
     *@param directory of the file
     */
    public void readAttractions(String directory) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(directory));
        attractions = new Hashtable<String, String>();
        String line = br.readLine();
        line = br.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            attractions.put(fields[0],(fields[1]));
            line = br.readLine();
        }
    }
    /**
     * Function that  reads cities
     * and stores them in an Arraylist
     *@param directory of the file
     */
    public void readCities(String directory) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(directory));
        citiesList = new ArrayList<String>();
        String line = br.readLine();
        while (line!=null){
            String[] fields = line.split(",");
            if(!citiesList.contains(fields[0])){
                citiesList.add(fields[0]);
            }
            if(!citiesList.contains(fields[1])){
                citiesList.add(fields[1]);
            }
            line = br.readLine();
        }
    }

    /**
     * Function that  reads roads
     * and stores them in an Arraylist
     * of type Data
     *@param directory of the file
     */
    public void readRoads(String directory) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(directory));
        roads = new ArrayList<Data>();
        String line = br.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            String source = fields[0];
            String target = fields[1];
            int weight = Integer.parseInt(fields[2]);
            roads.add(new Data(source,target,weight));
            line = br.readLine();
        }
    }

    /**
     * Function that gives the best route
     * based on user inputs
     * @param startingCity of the user
     * @param endingCity of the user
     * @param userAttractions of the user
     */
    public void route(String startingCity, String endingCity, List<String> userAttractions){
        cities = new ArrayList<City>();
        edges = new ArrayList<Edge>();
        int cost = 0;
        for (int i = 0; i < citiesList.size(); i++) {
            City city = new City(citiesList.get(i));
            cities.add(city);
        }
        for (int i = 0; i < roads.size(); i++) {
            addEdge(citiesList.indexOf(roads.get(i).getOrigin()), citiesList.indexOf(roads.get(i).getDestination()), roads.get(i).getCost());
            addEdge(citiesList.indexOf(roads.get(i).getDestination()), citiesList.indexOf(roads.get(i).getOrigin()), roads.get(i).getCost());
        }

        Graph graph = new Graph(cities, edges);
        dijkstraAlgorithm = new Dijkstra(graph);

        System.out.println("====ROUTE====");
        if (userAttractions.isEmpty()) {
            dijkstraAlgorithm.execute(cities.get(citiesList.indexOf(startingCity)));
            answer = dijkstraAlgorithm.getPath(cities.get(citiesList.indexOf(endingCity)));
            cost += printPath(answer);
        } else{
            shortestFromStart(userAttractions, startingCity);
            for (int i = 0; i <= nameOfCityArr.length; i++) {
                if(i==0){
                    dijkstraAlgorithm.execute(cities.get(citiesList.indexOf(startingCity)));
                    answer = dijkstraAlgorithm.getPath(cities.get(citiesList.indexOf(nameOfCityArr[i])));
                    cost += printPath(answer);
                } else if(i == nameOfCityArr.length){
                    dijkstraAlgorithm.execute(cities.get(citiesList.indexOf(nameOfCityArr[i-1])));
                    answer = dijkstraAlgorithm.getPath(cities.get(citiesList.indexOf(endingCity)));
                    cost += printPath(answer);
                }
                else{
                    dijkstraAlgorithm.execute(cities.get(citiesList.indexOf(nameOfCityArr[i-1])));
                    answer = dijkstraAlgorithm.getPath(cities.get(citiesList.indexOf(nameOfCityArr[i])));
                    cost += printPath(answer);
                }
            }

        }
        System.out.println("Total cost is: "  +  cost  + " miles");
    }

    /**
     * Function that adds edges
     * of all the roads
     * @param source of the city number
     * @param target of the city number
     * @param cost of the source to target in miles
     */
    private void addEdge(int source, int target, int cost) {
        Edge edge = new Edge(cities.get(source), cities.get(target), cost);
        edges.add(edge);
    }

    /**
     * Function that prints the best route
     * based on Dijkstra's algorithms
     * @param answer of the best path
     * @return int calculating the cost in miles
     */
    private int printPath(List<City> answer){
        int cost = 0;
        for (int i = 0; i < answer.size()-1; i++) {
            System.out.println("* " + answer.get(i).getName() + " -> " + answer.get(i+1).getName());
            cost+= dijkstraAlgorithm.getWeight(answer.get(i), answer.get(i+1));
        }
        return cost;
    }

    /**
     * Function that calculates the shortest attractions
     * based on the starting city from the user
     * @param startCity of the user
     * @param userAttractions of the user
     */
    public void shortestFromStart(List<String> userAttractions, String startCity){
        int sf = citiesList.indexOf(startCity);
        ArrayList<String> nameOfCity = new ArrayList<String>();
        List<Integer> numberOfCity = new ArrayList<Integer>();

        for (int i = 0; i < userAttractions.size(); i++) {
            String user =  attractions.get(userAttractions.get(i));
            int userNum = citiesList.indexOf(user);
            numberOfCity.add(sf-userNum);
            nameOfCity.add(user);
        }
        nameOfCityArr = nameOfCity.toArray(new String[0]);
        numberOfCityArr = numberOfCity.toArray(new Integer[0]);
        for (int i = 1; i < nameOfCityArr.length; i++) {
            int temp = numberOfCityArr[i];
            String temp2 = nameOfCityArr[i];
            int index = i;
            while (index > 0 && numberOfCityArr[index-1] > temp) {
                numberOfCityArr[index] = numberOfCityArr[index-1];
                nameOfCityArr[index] = nameOfCityArr[index-1];
                --index;
            }
            numberOfCityArr[index] = temp;
            nameOfCityArr[index] = temp2;
        }
    }


    public static void main(String[] args) throws IOException {
        if(args.length!=2){
            System.out.println("ERROR\nMissing path of roads.csv, or attractions.csv (IN THAT ORDER)");
            System.exit(-1);
        }

        String roads = args[0];
        String attractions = args[1];

        RoadTrip roadtrip = new RoadTrip();
        roadtrip.readCities(roads);
        roadtrip.readRoads(roads);
        roadtrip.readAttractions(attractions);

        List<String> attractionsList = new ArrayList<String>();
        Scanner scanner= new Scanner(System.in);
        String start;
        String end;
        while(true) {
            System.out.print("Name of starting city (or EXIT to quit): ");
            start = scanner.nextLine();
            if(start.equalsIgnoreCase("exit")){
                System.exit(0);
            }
            if(!roadtrip.citiesList.contains(start)){
                System.out.println("City: " + start +  " unknown");
            } else{
                break;
            }
        }
        while (true) {
            System.out.print("Name of ending city: ");
            end = scanner.nextLine();
            if(!roadtrip.citiesList.contains(end)){
                System.out.println("City: " + end + " unknown" );
            } else {
                break;
            }
        }
        while(true) {
            System.out.print("List an attraction (or ENOUGH to stop listing): ");
            String attraction  = scanner.nextLine();
            if(attraction.equalsIgnoreCase("ENOUGH")) {
                break;
            }
            if(!roadtrip.attractions.containsKey(attraction)) {
                System.out.println("Attraction: "+ attraction+ " unknown.");
            }
            else {
                attractionsList.add(attraction);
            }
        }

        roadtrip.route(start, end, attractionsList);

    }
}

