package fr.lediamantrouge.actionbyip.manager;

import fr.lediamantrouge.actionbyip.model.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionManager implements IActionManager {

    public static List<Action> actions = new ArrayList<>();

    @Override
    public List<Action> getActions() {
        return actions;
    }

    @Override
    public Action getAction(String ip) {
        for (Action action : actions) {
            if (action.getIp().equals(ip)) return action;
        }
        return null;
    }

    @Override
    public void removeAction(Action action) {
        actions.remove(action);
    }

    @Override
    public void createAction(Action action) {
        actions.add(action);
    }
}
