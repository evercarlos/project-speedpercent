package com.tec.speedpercent.exception;

import lombok.Getter;

@Getter
public enum CalcErrorType {

    HISTORY_400_1("Error en el criterio de ordenación");

    private final String description;


    CalcErrorType(String description) {
        this.description = description;
    }
}
