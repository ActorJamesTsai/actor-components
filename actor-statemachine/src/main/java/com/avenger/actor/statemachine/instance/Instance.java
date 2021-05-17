package com.avenger.actor.statemachine.instance;

/**
 * Description:
 *
 * Date: 2021/3/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface Instance {

    String getId();

    String getDefId();

    Integer getDefVersion();

    String getCurrentNodeId();

    void setCurrentNodeId(String currentState);


    enum State {
        runnable(0),
        terminated(1);

        private final Integer v;

        State(Integer v) {
            this.v = v;
        }

        public Integer getV() {
            return v;
        }
    }
}
