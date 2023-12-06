package fr.lediamantrouge.actionbyip.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Type {
    SERVER("server"), COMMAND("command");

    @Getter
    String name;
}
