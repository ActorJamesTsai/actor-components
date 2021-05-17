package com.avenger.actor.statemachine.task;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface Task {

    String getId();

    enum State {

        unprocessed(0),
        processed(1);

        private final Integer v;

        State(Integer v) {
            this.v = v;
        }

        public Integer getV() {
            return v;
        }
    }
}
