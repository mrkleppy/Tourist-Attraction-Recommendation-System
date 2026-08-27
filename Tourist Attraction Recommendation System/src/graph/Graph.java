package graph;

import Class.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

public class Graph { 
    // attributes
    private Map<String, Vertex> vertices;
    private Map<String, List<Edge>> adjList;
    
    // default constructor
    public Graph() {
        this.vertices = new HashMap<>();
        this.adjList = new HashMap<>();
    }
    
    // Accessor
    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public Map<String, List<Edge>> getAdjList() {
        return adjList;
    }
    
    // Mutator
    public void setVertices(Map<String, Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setAdjList(Map<String, List<Edge>> adjList) {
        this.adjList = adjList;
    }
    
    // get an vertex
    public Vertex getVertex(String name) {
        return vertices.get(normalize(name));
    }
    
    // get neighbors of a vertex
    public List<Edge> getNeighbors(String name) {
        return adjList.getOrDefault(normalize(name), Collections.emptyList());
    }
    
    // Verify the exist of the vertex
    public boolean hasVertex(String name) {
        return vertices.containsKey(normalize(name));
    }
    
    // Normalize keys to uppercase so searches are case-insensitive
    private String normalize(String input) {
        return input == null ? "" : input.trim().toUpperCase();
    }
    
    // Add a new vertex
    public void addVertex(String name, String type) {
        String key = normalize(name);
        vertices.putIfAbsent(key, new Vertex(name, type.toUpperCase()));
        adjList.putIfAbsent(key, new ArrayList<>());
    }
    
    // Add a new edge
    public void addEdge(String v1, String v1Type, String v2, String v2Type) {
        addVertex(v1, v1Type);
        addVertex(v2, v2Type);

        String k1 = normalize(v1);
        String k2 = normalize(v2);

        adjList.get(k1).add(new Edge(v1, v1Type, v2, v2Type));
        adjList.get(k2).add(new Edge(v2, v2Type, v1, v1Type)); 
    }
    
    // Remove a vertex and it's connected edges
    public boolean removeVertex(String name) {
        String key = normalize(name);

        if (!vertices.containsKey(key)) {
            return false; // Vertex does not exist
        }

        // 1. Remove all outgoing edges from this vertex's neighbors pointing back to it
        List<Edge> connectedEdges = adjList.get(key);
        if (connectedEdges != null) {
            for (Edge edge : connectedEdges) {
                String neighborKey = normalize(edge.getTo()); // target vertex key
                List<Edge> neighborEdges = adjList.get(neighborKey);
                if (neighborEdges != null) {
                    // Remove any edge where destination matches the target key being deleted
                    neighborEdges.removeIf(e -> normalize(e.getTo()).equals(key));
                }
            }
        }
        
        adjList.remove(key);
        vertices.remove(key);
        return true;
    }
    
    // Remove an edge
    public boolean removeEdge(String v1, String v2) {
        String k1 = normalize(v1);
        String k2 = normalize(v2);

        // Verify both vertices exist
        if (!adjList.containsKey(k1) || !adjList.containsKey(k2)) {
            return false;
        }

        // Remove edge v1 -> v2
        boolean removedFromV1 = adjList.get(k1).removeIf(edge -> normalize(edge.getTo()).equals(k2));

        // Remove edge v2 -> v1
        boolean removedFromV2 = adjList.get(k2).removeIf(edge -> normalize(edge.getTo()).equals(k1));

        return removedFromV1 || removedFromV2;
    }
    
    // Load every vertices and edges form the files
    public void loadGraph() {
        List<City> cities = File.readCityFile(); // get every cities
        List<Attraction> attractions = File.readAttractionFile(); // get every attractions

        // Add States
        for (State state : State.values()) {
            addVertex(state.toString(), "STATE"); // add every states as vertices
        }
        
        // Default link state <-> state
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
            // Edge between City and State
            addEdge(city.getName(), "CITY", city.getState().toString(), "STATE");
        }

        // Add Attractions and link Attraction <-> City
        for (Attraction attraction : attractions) {
            addVertex(attraction.getName(), "ATTRACTION");                      
            String cityName = attraction.getCity().getName();  
            // Edge between Attraction and City
            addEdge(attraction.getName(), "ATTRACTION", cityName, "CITY");
        }
    }
    
    // BFS path finding
    public List<String> findShortestPathBFS(String startName, String targetName) {
        String startKey = normalize(startName); // Normalizing start key
        String targetKey = normalize(targetName); // Normalizing target key

        if (!vertices.containsKey(startKey) || !vertices.containsKey(targetKey)) {
            return Collections.emptyList(); // Verify the start and target key is exist
        }

        if (startKey.equals(targetKey)) { // verify start is target or not
            return Collections.singletonList(vertices.get(startKey).getName());
        }

        Queue<String> queue = new LinkedList<>(); // Queue for searching
        Set<String> visited = new HashSet<>(); // Store explored vertex
        Map<String, String> parentMap = new HashMap<>(); // Tracks node -> previous node to reconstruct path

        queue.add(startKey);
        visited.add(startKey);

        boolean found = false; // flag

        while (!queue.isEmpty()) {
            String currentKey = queue.poll(); // get first element(vertex)

            if (currentKey.equals(targetKey)) {
                found = true; // found then end search
                break;
            }
            
            // get neighbors
            for (Edge edge : getNeighbors(currentKey)) {
                String neighborKey = normalize(edge.getTo());

                if (!visited.contains(neighborKey)) { // if not explored before
                    visited.add(neighborKey);
                    parentMap.put(neighborKey, currentKey);
                    queue.add(neighborKey);
                }
            }
        }

        if (!found) { // no solution
            return Collections.emptyList();
        }

        // Reconstruct path from target back to start
        LinkedList<String> path = new LinkedList<>();
        String curr = targetKey;
        while (curr != null) {
            path.addFirst(vertices.get(curr).getName()); // Store display name
            curr = parentMap.get(curr);
        }

        return path;
    }
    
    // BFS vertices searching
    public List<Vertex> findVerticesByTypeBFS(String startName, String targetType) {
        String startKey = normalize(startName); // normalizing the start key
        String normalizedType = targetType == null ? "" : targetType.toUpperCase(); // normalizing type, if null then set empty

        if (!vertices.containsKey(startKey)) {
            return Collections.emptyList(); // verify teh start key is exist
        }

        List<Vertex> result = new ArrayList<>(); // use to store target vertices
        Queue<String> queue = new LinkedList<>(); // queue for searching
        Set<String> visited = new HashSet<>(); // store explored vertex

        queue.add(startKey);
        visited.add(startKey);

        while (!queue.isEmpty()) {
            String currentKey = queue.poll(); // get first element
            Vertex currentVertex = vertices.get(currentKey); // get vertex by key

            if (currentVertex.getType().equalsIgnoreCase(normalizedType)) {
                result.add(currentVertex); // add target vertex
            }
            
            // get neighbors
            for (Edge edge : getNeighbors(currentKey)) { 
                String neighborKey = normalize(edge.getTo());
                if (!visited.contains(neighborKey)) { // if not explored before
                    visited.add(neighborKey);
                    queue.add(neighborKey);
                }
            }
        }

        return result;
    }
}