package com.dsysme.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * I implemented differently than https://www.geeksforgeeks.org/topological-sorting/
 * I think that the algorithm in geeksforgeeks is breadth first while mine is depth first. Is that right?
 * Which is better for this use case?
 */
public class TopologicalSort {

    private static Stack<Integer> dagHelper(DirectedGraph directedGraph, Stack<Integer> visited, Stack<Integer> finished) {
        if (finished.size() == directedGraph.getVertexes().size()) {
            return finished;
        }

        Integer vertex;
        if (visited.isEmpty()) {
            // start new connectivity group
            vertex = directedGraph
                    .getVertexes()
                    .stream()
                    .filter(v -> !finished.contains(v)).findAny().get();
            visited.push(vertex);
        } else {
            vertex = visited.peek();
        }

        Set<Integer> neighbors = directedGraph.getNeighbors(vertex);
        if (neighbors.stream().anyMatch(v -> visited.contains(v))) {
            // found a cycle no DAG
            return new Stack<>();
        }

        Set<Integer> nextCandidate = neighbors.stream()
                .filter(neighbor -> !finished.contains(neighbor))
                .collect(Collectors.toSet());

        if (nextCandidate.isEmpty()) {
            // all edges from vertex were already visited
            finished.push(vertex);
            visited.pop();
        } else {
            visited.push(nextCandidate.iterator().next());
        }
        return dagHelper(directedGraph, visited, finished);

    }

    public static List<Integer> sort(DirectedGraph directedGraph) {
        Stack<Integer> visited = new Stack<>();
        if (directedGraph.getVertexes().isEmpty()) {
            return visited;
        }

        visited.push(directedGraph.getVertexes().iterator().next());
        Stack<Integer> finished = new Stack<>();
        Stack<Integer> dependenciesReversed = dagHelper(directedGraph, visited, finished);

        List<Integer> result = new ArrayList<>();
        while (!dependenciesReversed.empty()) {
            result.add(dependenciesReversed.pop());
        }
        return result;
    }


    public static void main(String[] args) {

    }
}
