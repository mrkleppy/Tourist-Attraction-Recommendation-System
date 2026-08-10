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

    public void addEdge(String v1, String v1Type, String v2, String v2Type, int weight) {
        addVertex(v1, v1Type);
        addVertex(v2, v2Type);

        String k1 = normalize(v1);
        String k2 = normalize(v2);

        adjList.get(k1).add(new Edge(v1, v1Type, v2, v2Type, weight));
        adjList.get(k2).add(new Edge(v2, v2Type, v1, v1Type, weight)); // Undirected
    }

    public void loadGraph() {
        List<City> cities = File.readCityFile();
        List<Attraction> attractions = File.readAttractionFile(attractionByName);

        // Add States
        for (State state : State.values()) {
            addVertex(state.toString(), "STATE");
        }

        // Add Cities and link City <-> State
        for (City city : cities) {
            addVertex(city.getName(), "CITY");
            // Edge between City name and State name
            addEdge(city.getName(), "CITY", city.getState().toString(), "STATE", 0);
        }

        // Add Attractions and link Attraction <-> City
        for (Attraction attraction : attractions) {
            addVertex(attraction.getName(), "ATTRACTION");                      
            String cityName = attraction.getCity().getName();             
            addEdge(attraction.getName(), "ATTRACTION", cityName, "CITY", 0);
        }
    }

    public List<Attraction> getAttractionsByState(String stateName) {
        System.out.println("DEBUG: Input state: '" + stateName + "'");
        System.out.println("DEBUG: Known vertices keys: " + vertices.keySet());
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
                if (attraction != null) {
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
        
        // Need to do something to avoid redundant result in the list and dont break the sequence
        for (State state : searchHistory.getStates()) {
            attractions.addAll(getAttractionsByState(state.name()));
        }
        
        return attractions;
    }

    public List<String> findRouteToAttraction(String startState, String targetAttraction) {
        List<String> route = new ArrayList<>();
        if (!vertices.containsKey(startState) || !vertices.containsKey(targetAttraction)) {
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

            if (current.equals(targetAttraction)) {
                break;
            }

            for (Edge edge : adjList.getOrDefault(current, Collections.emptyList())) {
                String neighbor = edge.getTo();
                
                if (edge.getWeight()== 0) {
                    continue;
                }
                
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    previous.put(neighbor, current);
                }
            }
        }

        if (!previous.containsKey(targetAttraction)) {
            return route;
        }

        String step = targetAttraction;
        while (step != null) {
            route.add(0, step);
            step = previous.get(step);
        }

        return route;
    }
}