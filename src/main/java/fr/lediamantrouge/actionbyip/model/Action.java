package fr.lediamantrouge.actionbyip.model;

import fr.lediamantrouge.actionbyip.enumeration.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Action {

    String ip;
    Type type;
    List<String> args;


}
