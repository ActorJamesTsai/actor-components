package com.avenger.actor.statemachine.engine.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * Date: 2021/4/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class AdjacencyListGraph<T> {

    private List<AdjacencyNode<T>> header = null;

    public void initGraph(List<? extends AdjacencyOrientedEdge<T>> edgeList) {
        initAdjacencyList(edgeList);
    }

    public void breadthFirstTravelEdge(BiConsumer<AdjacencyNode<T>, AdjacencyNode<T>> edgeConsumer) {
        if (this.header == null) {
            throw new RuntimeException("The graph is not initilized");
        }
        for (AdjacencyNode<T> headerNode : this.header) {
            for (AdjacencyNode<T> adjacencyNode = headerNode.nextNode; adjacencyNode != null;
                adjacencyNode = adjacencyNode.nextNode) {
                edgeConsumer.accept(headerNode, adjacencyNode);
            }
        }
    }

    public AdjacencyNode<T> findHeaderNode(List<AdjacencyNode<T>> header, String value) {
        if (header == null) {
            throw new RuntimeException("The graph is not initilized");
        }
        for (AdjacencyNode<T> headerNode : header) {
            if (headerNode.getValue().equals(value)) {
                return headerNode;
            }
        }
        return null;
    }

    public List<AdjacencyNode<T>> getHeader() {
        return this.header;
    }

    private void initAdjacencyList(List<? extends AdjacencyOrientedEdge<T>> edgeList) {
        this.header = new ArrayList<>();
        for (AdjacencyOrientedEdge<T> edge : edgeList) {
            AdjacencyNode<T> headerNode = new AdjacencyNode<>(edge.getStartVertexValue(), null, null);
            if (!this.header.contains(headerNode)) {
                this.header.add(headerNode);
            }
        }
        Set<String> endNodeSet = calculateNoOutDegreeVertex(edgeList);
        for (String endNode : endNodeSet) {
            AdjacencyNode<T> headerNode = new AdjacencyNode<>(endNode);
            if (!this.header.contains(headerNode)) {
                this.header.add(headerNode);
            }
        }
        for (AdjacencyOrientedEdge<T> edge : edgeList) {
            AdjacencyNode<T> headerNode = findHeaderNode(this.header, edge.getStartVertexValue());
            while (headerNode.getNextNode() != null) {
                headerNode = headerNode.getNextNode();
            }
            AdjacencyNode<T> adjacencyHeaderNode = findHeaderNode(this.header, edge.getEndVertexValue());
            AdjacencyNode<T> adjacencyNode = new AdjacencyNode<>(adjacencyHeaderNode.getValue(), null, edge.getData());
            headerNode.setNextNode(adjacencyNode);
        }
    }

    private Set<String> calculateNoOutDegreeVertex(List<? extends AdjacencyOrientedEdge<T>> edgeList) {
        Set<String> endNodeSet = edgeList.stream().map(AdjacencyOrientedEdge::getEndVertexValue)
            .collect(Collectors.toSet());
        Set<String> startNodeSet = edgeList.stream().map(AdjacencyOrientedEdge::getStartVertexValue)
            .collect(Collectors.toSet());
        endNodeSet.removeAll(startNodeSet);
        return endNodeSet;
    }

    public static class AdjacencyNode<T> {

        private String value;

        private AdjacencyNode<T> nextNode;

        private T data;

        public AdjacencyNode(String value) {
            this(value, null);
        }

        public AdjacencyNode(String value, AdjacencyNode<T> nextNode) {
            this(value, nextNode, null);
        }

        public AdjacencyNode(String value, AdjacencyNode<T> nextNode, T data) {
            this.value = value;
            this.nextNode = nextNode;
            this.data = data;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public AdjacencyNode<T> getNextNode() {
            return this.nextNode;
        }

        public void setNextNode(AdjacencyNode<T> nextNode) {
            this.nextNode = nextNode;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public int hashCode() {
            int result = 1;
            result = 31 * result + ((this.value == null) ? 0 : this.value.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            AdjacencyNode<T> other = (AdjacencyNode<T>) obj;
            if (this.value == null) {
                return other.value == null;
            } else {
                return this.value.equals(other.value);
            }
        }
    }
}
