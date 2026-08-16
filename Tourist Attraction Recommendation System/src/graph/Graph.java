package graph;

import Class.*;
import java.util.*;

public class Graph { 
    private Map<String, Vertex> vertices = new HashMap<>();
    private Map<String, List<Edge>> adjList = new HashMap<>();
    private Map<String, Attraction> attractionByName = new HashMap<>();

    // Normalize keys to uppercase so searches are case-insensitive
    private String normalize(String input) {
        return input == null ? "" : input.trim().toUpperCase();
    }

    public void addVertex(String name, String type) {
        String key = normalize(name);
        vertices.putIfAbsent(key, new Vertex(name, type.toUpperCase()));
        adjList.putIfAbsent(key, new ArrayList<>());
    }

    public void addEdge(String v1, String v1Type, String v2, String v2Type) {
        addVertex(v1, v1Type);
        addVertex(v2, v2Type);

        String k1 = normalize(v1);
        String k2 = normalize(v2);

        adjList.get(k1).add(new Edge(v1, v1Type, v2, v2Type));
        adjList.get(k2).add(new Edge(v2, v2Type, v1, v1Type)); // Undirected
    }

    public void loadGraph() {
        List<City> cities = File.readCityFile();
        List<Attraction> attractions = File.readAttractionFile(attractionByName);

        // Add States
        for (State state : State.values()) {
            addVertex(state.toString(), "STATE");
        }
        
        addEdge("PERLIS", "STATE", "KEDAH", "STATE");
        addEdge("KEDAH", "STATE", "PENANG", "STATE");
        addEdge("KEDAH", "STATE", "PERAK", "STATE");
        addEdge("PENANG", "STATE", "PERAK", "STATE");
        addEdge("PERAK", "STATE", "PAHANG", "STATE");
        addEdge("PERAK", "STATE", "SELANGOR", "STATE");
        addEdge("KELANTAN", "STATE", "TERENGGANU", "STATE");
        addEdge("KELANTAN", "STATE", "PAHANG", "STATE");
        addEdge("TERENGGANU", "STATE", "PAHANG", "STATE");
        addEdge("PAHANG", "STATE", "SELANGOR", "STATE");
        addEdge("PAHANG", "STATE", "NEGERISEMBILAN", "STATE");
        addEdge("PAHANG", "STATE", "JOHOR", "STATE");
        addEdge("SELANGOR", "STATE", "KUALALUMPUR", "STATE");
        addEdge("SELANGOR", "STATE", "PUTRAJAYA", "STATE");
        addEdge("SELANGOR", "STATE", "NEGERISEMBILAN", "STATE");
        addEdge("SELANGOR", "STATE", "SABAH", "STATE");
        addEdge("SELANGOR", "STATE", "SARAWAK", "STATE");
        addEdge("NEGERISEMBILAN", "STATE", "MELAKA", "STATE");
        addEdge("NEGERISEMBILAN", "STATE", "JOHOR", "STATE");
        addEdge("MELAKA", "STATE", "JOHOR", "STATE");
        
        // Add Cities and link City <-> State
        for (City city : cities) {
            addVertex(city.getName(), "CITY");
            // Edge between City name and State name
            addEdge(city.getName(), "CITY", city.getState().toString(), "STATE");
        }

        // Add Attractions and link Attraction <-> City
        for (Attraction attraction : attractions) {
            addVertex(attraction.getName(), "ATTRACTION");                      
            String cityName = attraction.getCity().getName();             
            addEdge(attraction.getName(), "ATTRACTION", cityName, "CITY");
        }
    }

    public List<Attraction> getAttractionsByState(String stateName) {
        List<Attraction> attractions = new ArrayList<>();
        String startKey = normalize(stateName);

        if (!vertices.containsKey(startKey)) {
            return attractions; // State not found
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(startKey);
        queue.add(startKey);

        while (!queue.isEmpty()) {
            String currentKey = queue.poll();
            Vertex vertex = vertices.get(currentKey);

            if (vertex != null && "ATTRACTION".equals(vertex.getType())) {
                // Find attraction using stored original name
                Attraction attraction = attractionByName.get(vertex.getName());
                if (attraction != null && attraction.getCity().getState().name().equals(startKey)) {
                    attractions.add(attraction);
                }
            }

            for (Edge edge : adjList.getOrDefault(currentKey, Collections.emptyList())) {
                String neighborKey = normalize(edge.getTo());

                if (visited.add(neighborKey)) {
                    queue.add(neighborKey);
                }
            }
        }

        return attractions;
    }
    
    public List<Attraction> getAttractionsByHistory(SearchHistory searchHistory) {
        List<Attraction> attractions = new ArrayList<>();
        Set<Attraction> attractionSet = new LinkedHashSet<>();
        
        for (State state : searchHistory.getStates()) {
            attractionSet.addAll(getAttractionsByState(state.name()));
        }
        
        attractions.addAll(attractionSet);
        
        return attractions;
    }

    public List<String> findRouteToAttraction(String destination, String startState) {
        List<String> route = new ArrayList<>();
        destination = normalize(destination);
        startState = normalize(startState);
        
        if (!vertices.containsKey(startState) || !vertices.containsKey(destination)) {
            System.out.println("Not found start or end"); //DEBUG
            return route;
        }

        if (startState.equals(destination)) {
            route.add(startState);
            System.out.println("End is Start"); // DEBUG
            return route;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, String> previous = new HashMap<>();

        visited.add(startState);
        queue.add(startState);
        previous.put(startState, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(destination)) {
                break;
            }

            for (Edge edge : adjList.getOrDefault(current, Collections.emptyList())) {
                String neighbor = normalize(edge.getTo());
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    previous.put(neighbor, current);
                }
            }
        }

        if (!previous.containsKey(destination)) {
            System.out.println("End no found"); // DEBUG
            return route; // unreachable
        }

        // Reconstruct path
        String step = destination;
        while (step != null) {
            route.add(0, step);
            step = previous.get(step);
        }
        
        System.out.println(route); //DEBUG
        return route;
    }
}