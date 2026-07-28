/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package graph;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;

public class Graph { 
    private Map<String, Vertex> vertices = new HashMap<>();
    private Map<String, List<Edge>> adjList = new HashMap<>();
    private Map<String, List<String>> attractionCache = new HashMap<>();

    public void addVertex(String name, String type) {
        vertices.putIfAbsent(name, new Vertex(name, type));
        adjList.putIfAbsent(name, new ArrayList<>());
    }

    public void addEdge(String v1, String v2, int weight) {
        addVertex(v1, "UNKNOWN");
        addVertex(v2, "UNKNOWN");
        adjList.get(v1).add(new Edge(v2, weight));
        adjList.get(v2).add(new Edge(v1, weight)); // undirected
    }
    
    public List<String> getAttractionsByState(String stateName) {
        if (attractionCache.containsKey(stateName)) {
            return attractionCache.get(stateName);
        }

        List<String> attractions = new ArrayList<>();
        if (!vertices.containsKey(stateName)) return attractions;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(stateName);
        queue.add(stateName);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Vertex v = vertices.get(current);

            if (v != null && "ATTRACTION".equalsIgnoreCase(v.type)) {
                attractions.add(current);
            }

            for (Edge edge : adjList.getOrDefault(current, Collections.emptyList())) {
                String neighbor = edge.to;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        attractionCache.put(stateName, attractions);
        return attractions;
    }

    public Map<String, List<String>> getAttractionsForHistory(List<String> searchHistory) {
        Map<String, List<String>> result = new LinkedHashMap<>();

        for (String state : searchHistory) {
            result.put(state, getAttractionsByState(state));
        }

        return result;
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
                String neighbor = edge.to;
                
                if (edge.weight == 0) {
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


