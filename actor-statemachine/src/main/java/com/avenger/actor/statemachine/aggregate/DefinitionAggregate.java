package com.avenger.actor.statemachine.aggregate;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.statemachine.definition.Definition;
import com.avenger.actor.statemachine.node.Node;
import com.avenger.actor.statemachine.router.Router;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 *
 * Date: 2021/5/9
 *
 * @author JiaDu
 * @version 1.0.0
 */
@Getter
@Setter
public class DefinitionAggregate implements Aggregate {

    private static final long serialVersionUID = -200152193364258775L;

    private Definition definition;

    private List<Node> nodeList;

    private List<Router> routerList;

    @Override
    public Object getUniqueId() {
        return definition.getId();
    }
}
