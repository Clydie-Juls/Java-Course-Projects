package metro;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import metro.commands.CommandParser;
import metro.linkedList.DoublyLinkedList;
import metro.station.ConnectedStation;
import metro.station.Station;
import metro.station.StationEdges;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static final Map<String, DoublyLinkedList<Station>> directions = new HashMap<>();
    private static final Map<String, Map<String, Integer>> linkedListMapper = new HashMap<>();

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        try {
            jsonToMappedLinkedList(args[0]);
            String input = scanner.nextLine().trim();
            String[] data = CommandParser.parseCommand(input);
            while (!data[0].equals("/exit")) {
                switch (data[0]) {
                    case "/output":
                        output(data[1]);
                        break;
                    case "/append":
                        append(data[1], data[2], data.length == 4 ? Integer.parseInt(data[3]) : 0);
                        break;

                    case "/add-head":
                        addHead(data[1], data[2], data.length == 4 ? Integer.parseInt(data[3]) : 0);
                        break;
                    case "/remove":
                        remove(data[1], data[2]);
                        break;
                    case "/connect":
                        connect(data[1],data[2], data[3], data[4]);
                        break;
                    case "/route":
                        route(data[1], data[2], data[3], data[4], false);
                        break;
                    case "/fastest-route":
                        route(data[1], data[2], data[3], data[4], true);
                        break;
                    default:
                        System.out.println("Invalid command");
                }
                input =  scanner.nextLine().trim();
                data = CommandParser.parseCommand(input);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void append(String lineName, String stationName, int time) {
        directions.get(lineName).
                insertByIndex(directions.get(lineName).getSize() - 1, new Station(stationName, lineName, time));
        linkedListMapper.get(lineName).put(stationName, directions.get(lineName).getSize() - 1);
    }

    private static void addHead(String lineName, String stationName, int time) {
        directions.get(lineName).
                insertByIndex(1, new Station(stationName, lineName, time));
        linkedListMapper.get(lineName).put(stationName, 1);
    }

    private static void remove(String lineName, String stationName) {
        List<ConnectedStation> connectedStations = directions.get(lineName).
                getValue(linkedListMapper.get(lineName).get(stationName)).transfer;
        //Remove ties with other stations.
        for (ConnectedStation station: connectedStations) {
            directions.get(station.getLine()).
                    getValue(linkedListMapper.get(station.getLine()).get(station.getStation())).transfer.
                    removeIf(n -> n.getLine().equals(lineName) && n.getStation().equals(stationName));
        }
        //Remove the station itself.
        directions.get(lineName).
                remove(directions.get(lineName).getValue(linkedListMapper.get(lineName).get(stationName)));

    }
    
    private static void output(String lineName) {
        directions.get(lineName).forEach(n -> {
            System.out.print(n.getName());
            n.transfer.forEach(j -> System.out.printf(" - %s (%s)", j.getStation(), j.getLine()));
            System.out.println();
        });
    }

    private static void connect(String line1, String station1,
                                String line2, String station2) {
        directions.get(line1).getValue(linkedListMapper.get(line1).get(station1)).
                transfer.add(new ConnectedStation(line2, station2));
        directions.get(line2).getValue(linkedListMapper.get(line2).get(station2)).
                transfer.add(new ConnectedStation(line1, station1));
    }

    private static void route(String initialLine, String initialStation,
                              String targetLine, String targetStation, boolean isFastestRoute) {

        List<Station> visitedStations = new ArrayList<>();
        Station firstStation = directions.get(initialLine).
                getValue(linkedListMapper.get(initialLine).get(initialStation));
        visitedStations.add(firstStation);
        Map<Integer, Queue<StationEdges>> connectedStations = getAllUnvisitedStations(
                getAllStationEdges(initialLine,
                        new StationEdges(null, firstStation, 0), 0, isFastestRoute), visitedStations);
        Map<Integer, Queue<StationEdges>> unvisitedStations = new TreeMap<>(connectedStations);

        Station finalStation = directions.get(targetLine).
                getValue(linkedListMapper.get(targetLine).get(targetStation));


        StationEdges dequeStationEdge = unvisitedStations.get(unvisitedStations.entrySet().iterator().next().getKey()).poll();
        if (dequeStationEdge != null) {
            while (dequeStationEdge.getCurrentStation() != finalStation) {
                if (visitedStations.contains(dequeStationEdge.getCurrentStation())) {
                    int distance = unvisitedStations.entrySet().iterator().next().getKey();
                    if (unvisitedStations.get(distance).size() == 0) {
                        unvisitedStations.remove(distance);
                        distance = unvisitedStations.entrySet().iterator().next().getKey();
                    }
                    dequeStationEdge = unvisitedStations.get(distance).poll();
                    continue;
                }

                int distance = unvisitedStations.entrySet().iterator().next().getKey();
                connectedStations = getAllUnvisitedStations(
                        getAllStationEdges(dequeStationEdge.getCurrentStation().
                                getLine(),dequeStationEdge, distance, isFastestRoute), visitedStations);
                for (Map.Entry<Integer, Queue<StationEdges>> set: connectedStations.entrySet()) {
                    if (!unvisitedStations.containsKey(set.getKey())) {
                        unvisitedStations.put(set.getKey(), new LinkedList<>());
                    }
                    set.getValue().forEach(n -> unvisitedStations.get(set.getKey()).add(n));
                }

                if (unvisitedStations.get(distance).size() == 0) {
                    unvisitedStations.remove(distance);
                    distance =  unvisitedStations.entrySet().iterator().next().getKey();

                }


                visitedStations.add(dequeStationEdge.getCurrentStation());
                dequeStationEdge = unvisitedStations.get(distance).poll();
                if (dequeStationEdge.getCurrentStation() == null) {
                    break;
                }

            }
            DoublyLinkedList<Station> destination = new DoublyLinkedList<>();
            StationEdges stationEdge = dequeStationEdge;
            int totalTime = stationEdge.getWeight();
            while (stationEdge.getCurrentStation() != firstStation) {
                destination.insertToFirst(stationEdge.getCurrentStation());
                stationEdge = stationEdge.getPreviousStation();
                totalTime += stationEdge.getWeight();
            }
            destination.insertToFirst(firstStation);
            printRoute(destination, isFastestRoute, totalTime);
        }
    }

    private static void printRoute(DoublyLinkedList<Station> route, boolean isFastestRoute, int totalTime) {
        route.forEach(n -> {
            if (route.getNode(n).getPrevious() != null) {
                if (!route.getNode(n).getPrevious().getValue().getLine().equals(n.getLine())) {
                    System.out.println("Transition to line " + n.getLine());
                }
            }
            System.out.println(n.getName());
        });

        if (isFastestRoute) {
            System.out.printf("Total: %s minutes in the way\n", totalTime);
        }
    }

    private static Map<Integer, Queue<StationEdges>> getAllStationEdges
            (String line, StationEdges stationEdge, int distance, boolean isFastestRoute) {
        Map<Integer, Queue<StationEdges>> connectedStations = new LinkedHashMap<>();
        int index = directions.get(line).getIndex(stationEdge.getCurrentStation());

        for (String prevName : stationEdge.getCurrentStation().prev) {
            if (linkedListMapper.get(line).containsKey(prevName)) {
                int edgeDistance = isFastestRoute ? directions.get(line).getValue(linkedListMapper.
                        get(line).get(prevName)).getTime() : 1;
                if (!connectedStations.containsKey(distance + edgeDistance)) {
                    connectedStations.put(distance + edgeDistance, new LinkedList<>());
                }
                connectedStations.get(distance + edgeDistance).add(new StationEdges(stationEdge, directions.
                        get(line).getValue(linkedListMapper.get(line).get(prevName)), edgeDistance));
            }
        }


        for (String nextName : stationEdge.getCurrentStation().next) {
            if (linkedListMapper.get(line).containsKey(nextName)) {
                int edgeDistance = isFastestRoute ? directions.get(line).getValue(index).getTime() : 1;
                if (!connectedStations.containsKey(distance + edgeDistance)) {
                    connectedStations.put(distance + edgeDistance, new LinkedList<>());
                }
                connectedStations.get(distance + edgeDistance).add(new StationEdges(stationEdge, directions.
                        get(line).getValue(linkedListMapper.get(line).get(nextName)), edgeDistance));
            }
        }


        for (ConnectedStation connectedStation : stationEdge.getCurrentStation().transfer) {
            int transferDistance = isFastestRoute ? 5 : 0;
            Station transferStation = directions.get(connectedStation.getLine()).
                    getValue(linkedListMapper.get(connectedStation.getLine()).
                            get(connectedStation.getStation()));
            if (!connectedStations.containsKey(distance + transferDistance)) {
                connectedStations.put(distance + transferDistance, new LinkedList<>());
            }
            connectedStations.get(distance + transferDistance).add(new StationEdges(stationEdge, transferStation, 5));
        }
        return connectedStations;
    }

    private static  Map<Integer, Queue<StationEdges>> getAllUnvisitedStations
            ( Map<Integer, Queue<StationEdges>> stations, List<Station> visitedStations) {
        Map<Integer, Queue<StationEdges>>  unvisitedStations = new LinkedHashMap<>();
        for (Map.Entry<Integer, Queue<StationEdges>> set: stations.entrySet()) {
            for (StationEdges stationEdge: set.getValue()) {
                if (!visitedStations.contains(stationEdge.getCurrentStation())) {
                    if (!unvisitedStations.containsKey(set.getKey())) {
                        unvisitedStations.put(set.getKey(), new LinkedList<>());
                    }
                    unvisitedStations.get(set.getKey()).add(stationEdge);
                }
            }
        }

        return unvisitedStations;
    }

    private static void jsonToMappedLinkedList(String pathName) throws Exception {
        Path path = Path.of(pathName);
        if (!Files.exists(path)) {
            throw new Exception("Error! Such a file doesn't exist!");
        }

        if (!Pattern.compile("\\.json").matcher(pathName).find()) {
            throw new Exception("Incorrect file");
        }

        Type mapType = new TypeToken<Map<String, List<Station>>>(){}.getType();
        Map<String,List<Station>> mappedJson = new Gson().fromJson(Files.readString(path), mapType);
        for (Map.Entry<String, List<Station>> lineSet: mappedJson.entrySet()) {
            linkedListMapper.put(lineSet.getKey(), new HashMap<>());
            directions.put(lineSet.getKey(), new DoublyLinkedList<>());
            lineSet.getValue().forEach(n -> {
                Station station = n;
                n.setLine(lineSet.getKey());
                directions.get(lineSet.getKey()).insertToLast(station);
                linkedListMapper.get(lineSet.getKey()).put(n.getName(), directions.get(lineSet.getKey()).getSize());
            });

            directions.get(lineSet.getKey()).insertToFirst(new Station("depot", lineSet.getKey(), 0));
            linkedListMapper.get(lineSet.getKey()).put("depotFirst", 0);
            directions.get(lineSet.getKey()).insertToLast(new Station("depot", lineSet.getKey(), 0));
            linkedListMapper.get(lineSet.getKey()).put("depotLast", directions.get(lineSet.getKey()).getSize() - 1);
        }
    }
}
