package com.dsysme.graphs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphUtilsTest {


    private static DirectedGraph simpleAcyclicGraph;
    private static DirectedGraph cyclicGraph;
    private static List<Integer> tpForSimpleAcyclicGraph;

    @BeforeAll
    public static void setUp() {
        DirectedGraph.Builder dgb = new DirectedGraph.Builder();
        dgb.addEdgIfNotPresent(1,2);
        dgb.addEdgIfNotPresent(2,3);
        dgb.addEdgIfNotPresent(2,4);
        dgb.addEdgIfNotPresent(4,5);
        dgb.addEdgIfNotPresent(4,6);
        dgb.addEdgIfNotPresent(5,6);
        dgb.addEdgIfNotPresent(6,3);

        simpleAcyclicGraph = dgb.build();

        dgb.addEdgIfNotPresent(0,2);
        dgb.addEdgIfNotPresent(0,3);
        dgb.addEdgIfNotPresent(1,0);
        dgb.addEdgIfNotPresent(2,1);
        dgb.addEdgIfNotPresent(3,4);
        dgb.addEdgIfNotPresent(4,0);

        cyclicGraph = dgb.build();

        Integer[] arr = {1, 2, 4, 5, 6, 3};
        tpForSimpleAcyclicGraph = Arrays.asList(arr);
    }

    @Test
    void isTopologicalSortShouldReturnTrueForTopologicalSort() {
        assertTrue(GraphUtils.isTopologicalSort(simpleAcyclicGraph, tpForSimpleAcyclicGraph));
    }

    @Test
    void isTopologicalSortShouldReturnFalseForNonTopologicalSort() {
        Integer [] notATopologicalSort = {1, 2, 4, 5, 3, 6};
        assertFalse(GraphUtils.isTopologicalSort(simpleAcyclicGraph, Arrays.asList(notATopologicalSort)));
    }

    @Test
    void isAcyclicForAcyclicGraph() {
        assertTrue(GraphUtils.isAcyclic(simpleAcyclicGraph));
    }

    @Test
    void isAcyclicForGraphThatContainCycle() {
        assertFalse(GraphUtils.isAcyclic(cyclicGraph));
    }

    @Test
    void containsCycleForAcyclic() {
        assertFalse(GraphUtils.containsCycle(simpleAcyclicGraph));
    }

    @Test
    void containsCycleForAGraphWithCycle() {
        assertTrue(GraphUtils.containsCycle(cyclicGraph));
    }
}