package com.example.notasrecordatorio.Enum;

public enum BaseUrlEnum {
    BASE_URL_USUARIO(1),
    BASE_URL_NOTAS(2),
    BASE_URL_CATEGORIA(3),
    BASE_URL_RECORDATORIO(4),
    BASE_URL_COMENTARIO(5);

    private final int value;
    BaseUrlEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
