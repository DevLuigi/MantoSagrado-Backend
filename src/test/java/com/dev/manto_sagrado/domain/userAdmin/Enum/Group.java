package com.dev.manto_sagrado.domain.userAdmin.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Group {
    ADMIN,
    ESTOQUISTA;

    @JsonCreator
    public static Group fromString(String value) {
        for (Group group : Group.values()) {
            if (group.name().equalsIgnoreCase(value)) {
                return group;
            }
        }
        throw new IllegalArgumentException("Invalid user group: " + value);
    }
}
