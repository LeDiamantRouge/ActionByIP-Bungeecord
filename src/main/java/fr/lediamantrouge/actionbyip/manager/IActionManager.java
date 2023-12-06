package fr.lediamantrouge.actionbyip.manager;

import fr.lediamantrouge.actionbyip.model.Action;

import java.util.List;

public interface IActionManager {

    List<Action> getActions();

    Action getAction(String ip);

    void removeAction(Action action);

    void createAction(Action action);
}
