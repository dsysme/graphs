package com.dsysme.graphs;

import java.util.*;
import java.util.stream.Collectors;

public class GraphUtils {

    /**
     * @see   <a href="https://youtu.be/AfSk24UTFS8?t=2864">Depth-First Search (DFS), Topological Sort</>
     * @param graph
     * @param topologicalSort
     * @return
     */
    public static boolean isTopologicalSort(DirectedGraph graph, List<Integer> topologicalSort) {

        //"for any edge (u, v) v finishes before u finishes"
        if (containsCycle(graph)) {
            return false;
        }

        return graph.getEdges().stream()
                .allMatch(edge -> topologicalSort.indexOf(edge.getKey()) < topologicalSort.indexOf(edge.getValue()));

    }


    private static boolean  acyclicHelper(DirectedGraph graph, Set<Integer> safeVertexes, Stack<Integer> path) {
        if (path.isEmpty()) {
            Optional<Integer> vertex = graph.getVertexes().stream().filter(v -> !safeVertexes.contains(v)).findAny();
            if (!vertex.isPresent()) {
                // we are done, none of the vertexes og graph are part of a cycle
                return true;
            }
            path.add(vertex.get());
            return acyclicHelper(graph, safeVertexes, path);
        }
        Integer vertex = path.peek();
        Set<Integer> nextCandidates = graph.getNeighbors(vertex).stream()
                .filter(v -> !safeVertexes.contains(v))
                .collect(Collectors.toSet());
        if (nextCandidates.isEmpty()) {
            //this vertex is not on any cycle
            safeVertexes.add(vertex);
            path.pop();
            return acyclicHelper(graph, safeVertexes, path);
        }
        if (nextCandidates.stream().anyMatch(v -> path.contains(v))) {
            // we found a back edge
            return false;
        }
        path.add(nextCandidates.iterator().next());
        return acyclicHelper(graph, safeVertexes, path);
    }

    public static boolean isAcyclic(DirectedGraph graph) {
        if ( graph.getEdges().isEmpty() || graph.getEdges().size() >= (graph.getVertexes().size()*graph.getVertexes().size())) {
            return false;
        }
        Stack<Integer> path = new Stack<>();
        path.add(graph.getVertexes().iterator().next());
        Set<Integer> safeVertexes = new HashSet<>();
        return acyclicHelper(graph, safeVertexes, path);
    }

    public static boolean containsCycle(DirectedGraph graph) {
        return !isAcyclic(graph);
    }

    public static void main(String[] args) {
        DirectedGraph.Builder dgb = new DirectedGraph.Builder();
        dgb.addEdgIfNotPresent(1,2);
        dgb.addEdgIfNotPresent(2,3);
        dgb.addEdgIfNotPresent(2,4);
        dgb.addEdgIfNotPresent(4,5);
        dgb.addEdgIfNotPresent(4,6);
        dgb.addEdgIfNotPresent(5,6);
        dgb.addEdgIfNotPresent(6,3);

        DirectedGraph graph = dgb.build();

        if (GraphUtils.isAcyclic(graph)) {
            System.out.println(String.format("as expected graph %s is acyclic", graph));
        } else {
            System.out.println(String.format("Opps graph %s should be acyclic", graph));
        }

        List<Integer> dag = TopologicalSort.sort(graph);

        if (GraphUtils.isTopologicalSort(graph, dag)) {
            System.out.println(String.format("as expected dag %s is a topological sort of %s", dag, graph));
        } else {
            System.out.println(String.format("Opps dag %s should be a topological sort of %s", dag, graph));
        }

        Integer [] notDag = {1, 2, 4, 5, 3, 6};
        if (GraphUtils.isTopologicalSort(graph, Arrays.asList(notDag))) {
            System.out.println(String.format("Opps dag %s should NOT be a topological sort of %s", Arrays.toString(notDag), graph));
        } else {
            System.out.println(String.format("as expected dag %s is NOT a topological sort of %s", Arrays.toString(notDag), graph));
        }

        dgb.addEdgIfNotPresent(0,2);
        dgb.addEdgIfNotPresent(0,3);
        dgb.addEdgIfNotPresent(1,0);
        dgb.addEdgIfNotPresent(2,1);
        dgb.addEdgIfNotPresent(3,4);
        dgb.addEdgIfNotPresent(4,0);

        graph = dgb.build();

        if (GraphUtils.containsCycle(graph)) {
            System.out.println(String.format("as expected graph %s is Cyclic", graph));
        } else {
            System.out.println(String.format("Opps graph %s should be Cyclic", graph));
        }

        if (TopologicalSort.sort(graph).isEmpty()) {
            System.out.println(String.format("as expected no dag as graph %s is Cyclic", graph));
        } else {
            System.out.println(String.format("Opps graph %s is Cyclic and should have no dag", graph));
        }
    }
}
