package com.example.notasrecordatorio.Enum;

public enum BaseUrlEnum {
    BASE_URL_USUARIO(1),
    BASE_URL_NOTAS(2),//ejemplo de nota
    BASE_URL_CATEGORIA(3);//ejemplo de categoria

    private final int value;
    BaseUrlEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
