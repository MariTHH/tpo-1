package org.example.algorithm;

import java.util.*;

public class DepthFirstSearch {
    private final Map<Integer, List<Integer>> graph;

    public DepthFirstSearch() {
        this.graph = new HashMap<>();
    }

    public void addEdge(int from, int to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    public List<Integer> dfs(int start) {
        List<Integer> nodes = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        if (!graph.containsKey(start)) return nodes;
        dfsTraversal(start, nodes, visited);
        return nodes;
    }

    private void dfsTraversal(int node, List<Integer> nodes, Set<Integer> visited) {
        if (visited.contains(node)) {
            return;
        }

        visited.add(node);
        nodes.add(node);
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            dfsTraversal(neighbor, nodes, visited);
        }

    }
}