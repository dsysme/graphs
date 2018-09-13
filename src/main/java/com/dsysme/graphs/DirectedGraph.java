package com.dsysme.graphs;


import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectedGraph {

    private Map<Integer, Set<Integer>> adjacencyMap;
    private static Pattern pattern = Pattern.compile("\\[(?<from>[0-9]*),(?<to>[0-9]*)\\]");

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        adjacencyMap.entrySet().stream().forEach(entry -> {
            entry.getValue().stream().forEach(value -> sb.append(String.format("[%s,%s] ", entry.getKey(), value)));
        });
        return sb.toString();
    }


    static public class Builder {
        private Map<Integer, Set<Integer>> adjacencyMap;

        public Builder() {
            this.adjacencyMap = new HashMap<>();
        }

        public void reset() {
            adjacencyMap.clear();
        }

        public boolean addVertexIfNotPresent(Integer vertex) {
            if (!adjacencyMap.containsKey(vertex)) {
                adjacencyMap.put(vertex, new HashSet<>());
                return true;
            }
            return false;
        }

        public boolean addEdgIfNotPresent(Integer fromVertex, Integer toVertex) {
            addVertexIfNotPresent(fromVertex);
            addVertexIfNotPresent(toVertex);
            return adjacencyMap.get(fromVertex).add(toVertex);
        }

        public DirectedGraph build() {
            return new DirectedGraph(adjacencyMap);
        }
    }


    public DirectedGraph(Map<Integer, Set<Integer>> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
    }


    public Set<Integer> getNeighbors(Integer vertex) {
        if (adjacencyMap.containsKey(vertex)) {
            return Collections.unmodifiableSet(adjacencyMap.get(vertex));
        }
        return Collections.emptySet();
    }

    public Set<Integer> getVertexes() {
        return Collections.unmodifiableSet(adjacencyMap.keySet());
    }

    public Set<Pair<Integer, Integer>> getEdges() {
        Set<Pair<Integer, Integer>> edges = new HashSet<>();
        adjacencyMap
               .entrySet()
               .stream()
               .forEach(e ->
                   e.getValue().stream().forEach(v -> edges.add(new Pair<>(e.getKey(), v))));
       return Collections.unmodifiableSet(edges);

    }


    public static Optional<DirectedGraph> fromString(String str) {
        String[] edges = str.split(" ");

        if (!isValidIntegerGraphString(str.split(" "))) {
            return Optional.empty();
        }

        Builder builder = new Builder();
        Arrays.asList(edges).stream()
                .map(edge ->  {
                    Matcher m = pattern.matcher(edge);
                    m.find();
                    return new Pair<>(Integer.valueOf(m.group("from")), (Integer.valueOf(m.group("to"))));
                })
                .forEach(pair -> builder.addEdgIfNotPresent(pair.getKey(), pair.getValue()));
        return Optional.of(builder.build());
    }

    private static boolean isValidIntegerGraphString(String[] edges) {
        return Arrays.asList(edges).stream()
                .allMatch(edge -> pattern.matcher(edge).matches());
    }

    public static void main(String[] args) {
        String str = "[1,2] [1,3] [1,4]";
        Optional<DirectedGraph> graph = DirectedGraph.fromString(str);
        if (!graph.isPresent()) {
            System.out.println(String.format("Could not parse graph from %s", str));
        } else {
            System.out.println(String.format("graph as String %s should match %s", graph.get(), str));
            assert(str.equals(graph.get().toString()));
        }

    }

}