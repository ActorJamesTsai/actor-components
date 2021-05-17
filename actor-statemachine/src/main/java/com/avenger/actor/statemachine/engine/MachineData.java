package com.avenger.actor.statemachine.engine;

import com.avenger.actor.statemachine.engine.graph.AdjacencyOrientedEdge;
import java.util.List;

/**
 * Description:
 *
 * Date: 2021/4/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class MachineData {

    private String startState;

    private List<String> finishState;

    private List<TransitTableItem> transitTableItemList;

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public List<String> getFinishState() {
        return finishState;
    }

    public void setFinishState(List<String> finishState) {
        this.finishState = finishState;
    }

    public List<TransitTableItem> getTransitTableItemList() {
        return transitTableItemList;
    }

    public void setTransitTableItemList(
        List<TransitTableItem> transitTableItemList) {
        this.transitTableItemList = transitTableItemList;
    }

    public static class TransitTableItem implements AdjacencyOrientedEdge<TransitTableItem> {

        private String fromStateKey;

        private int fromStateType;

        private String toStateKey;

        private int toStateType;

        private String actionName;


        public String getFromStateKey() {
            return fromStateKey;
        }

        public void setFromStateKey(String fromStateKey) {
            this.fromStateKey = fromStateKey;
        }

        public int getFromStateType() {
            return fromStateType;
        }

        public void setFromStateType(int fromStateType) {
            this.fromStateType = fromStateType;
        }

        public String getToStateKey() {
            return toStateKey;
        }

        public void setToStateKey(String toStateKey) {
            this.toStateKey = toStateKey;
        }

        public int getToStateType() {
            return toStateType;
        }

        public void setToStateType(int toStateType) {
            this.toStateType = toStateType;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getStartVertexValue() {
            return this.fromStateKey;
        }

        public String getEndVertexValue() {
            return this.toStateKey;
        }

        public TransitTableItem getData() {
            return this;
        }

        public int hashCode() {
            int result = 1;
            result = 31 * result + ((this.fromStateKey == null) ? 0 : this.fromStateKey.hashCode());
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
            TransitTableItem other = (TransitTableItem) obj;
            if (this.fromStateKey == null) {
                return other.fromStateKey == null;
            } else {
                return this.fromStateKey.equals(other.fromStateKey);
            }
        }

        public String toString() {
            return "TransitTableItem [fromStateKey=" + this.fromStateKey + ", fromStateType="
                + this.fromStateType + ", toStateKey=" + this.toStateKey + ", toStateType="
                + this.toStateType + ", actionName=" + this.actionName + "]";
        }
    }
}
