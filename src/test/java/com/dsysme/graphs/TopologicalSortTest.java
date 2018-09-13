package com.dsysme.graphs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortTest {

    @Test
    void topologicalSortForMultiConnectivityGraph() {
        DirectedGraph.Builder dgb = new DirectedGraph.Builder();
        dgb.addEdgIfNotPresent(0,14);
        dgb.addEdgIfNotPresent(1,4);
        dgb.addEdgIfNotPresent(2,3);
        dgb.addEdgIfNotPresent(3,6);
        dgb.addEdgIfNotPresent(3,10);
        dgb.addEdgIfNotPresent(4,7);
        dgb.addEdgIfNotPresent(7,8);
        dgb.addEdgIfNotPresent(7,11);
        dgb.addEdgIfNotPresent(8,9);
        dgb.addEdgIfNotPresent(8,12);
        dgb.addEdgIfNotPresent(11,15);
        dgb.addEdgIfNotPresent(12,15);
        dgb.addEdgIfNotPresent(13,16);
        dgb.addEdgIfNotPresent(13,17);
        dgb.addEdgIfNotPresent(15,16);

        DirectedGraph graph = dgb.build();

        List<Integer> topologicalSort = TopologicalSort.sort(graph);
        System.out.println(String.format("Given graph %s, one topological sort is %s", graph, topologicalSort));
        assertTrue(GraphUtils.isTopologicalSort(graph, topologicalSort));

    }

    @Test
    void topologicalSortSimpleCase() {
        DirectedGraph.Builder dgb = new DirectedGraph.Builder();
        dgb.addEdgIfNotPresent(1,2);
        dgb.addEdgIfNotPresent(2,3);
        dgb.addEdgIfNotPresent(2,4);
        dgb.addEdgIfNotPresent(4,5);
        dgb.addEdgIfNotPresent(4,6);
        dgb.addEdgIfNotPresent(5,6);
        dgb.addEdgIfNotPresent(6,3);

        DirectedGraph graph = dgb.build();

        List<Integer> topologicalSort = TopologicalSort.sort(graph);

        System.out.println(String.format("Given graph %s, one topological sort is %s", graph, topologicalSort));
    }


    @Test
    void topologicalSortForaCyclicGraph() {

        DirectedGraph.Builder dgb = new DirectedGraph.Builder();
        dgb.addEdgIfNotPresent(0,2);
        dgb.addEdgIfNotPresent(0,3);
        dgb.addEdgIfNotPresent(1,0);
        dgb.addEdgIfNotPresent(2,1);
        dgb.addEdgIfNotPresent(3,4);
        dgb.addEdgIfNotPresent(4,0);

        DirectedGraph cyclicGraph = dgb.build();
        assertTrue(TopologicalSort.sort(cyclicGraph).isEmpty());
    }
}