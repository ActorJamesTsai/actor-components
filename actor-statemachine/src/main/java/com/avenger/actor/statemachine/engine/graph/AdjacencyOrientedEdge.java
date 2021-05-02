package com.avenger.actor.statemachine.engine.graph;

/**
 * Description:
 *
 * Date: 2021/4/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface AdjacencyOrientedEdge<T> {

    String getStartVertexValue();

    String getEndVertexValue();

    T getData();
}
