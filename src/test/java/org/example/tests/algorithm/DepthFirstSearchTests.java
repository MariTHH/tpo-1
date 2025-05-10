package org.example.tests.algorithm;

import org.example.algorithm.DepthFirstSearch;
import org.example.tests.extensions.PrivateMethodTestsExtension;
import org.example.tests.extensions.TestPrivateMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PrivateMethodTestsExtension.class)
public class DepthFirstSearchTests {

    @Test
    void testSimple() {
        DepthFirstSearch graph = new DepthFirstSearch();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(0, 3);
        graph.addEdge(3, 4);

        List<Integer> result = graph.dfs(0);
        List<Integer> expected = List.of(0, 1, 2, 3, 4);
        assertEquals(expected, result);
    }

    @Test
    void testGraphWithCycle() {
        DepthFirstSearch graph = new DepthFirstSearch();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        List<Integer> result = graph.dfs(0);
        List<Integer> expected = List.of(0, 1, 2, 3, 4);
        assertEquals(expected, result);
    }

    @Test
    void testDisconnectedGraph() {
        DepthFirstSearch graph = new DepthFirstSearch();
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);

        List<Integer> result = graph.dfs(0);
        List<Integer> expected = List.of(0, 1);
        assertEquals(expected, result);
    }

    @Test
    void testStartFromMiddle() {
        DepthFirstSearch graph = new DepthFirstSearch();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> result = graph.dfs(2);
        List<Integer> expected = List.of(2, 3);
        assertEquals(expected, result);
    }

    @Test
    void testSelfLoop() {
        DepthFirstSearch graph = new DepthFirstSearch();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 2);

        List<Integer> result = graph.dfs(0);
        List<Integer> expected = List.of(0, 1, 2);
        assertEquals(expected, result);
    }
    @Test
    void testEmptyGraph() {
        DepthFirstSearch graph = new DepthFirstSearch();
        List<Integer> result = graph.dfs(0);
        assertTrue(result.isEmpty());
    }


    @Test
    @TestPrivateMethod(
            className = "org.example.algorithm.DepthFirstSearch",
            methodName = "dfsTraversal",
            paramTypes = {int.class, List.class, Set.class}
    )
    public void testDfsTraversal() throws Exception {
        DepthFirstSearch dfs = new DepthFirstSearch();
        dfs.addEdge(0, 1);
        dfs.addEdge(0, 2);
        dfs.addEdge(1, 3);
        dfs.addEdge(2, 3);

        List<Integer> nodeList = new ArrayList<>();
        Set<Integer> visitedSet = new HashSet<>();

        Method method = DepthFirstSearch.class.getDeclaredMethod("dfsTraversal", int.class, List.class, Set.class);
        method.setAccessible(true);

        method.invoke(dfs, 0, nodeList, visitedSet);

        List<Integer> expected = Arrays.asList(0, 1, 3, 2);
        assertEquals(expected, nodeList);
    }
}
