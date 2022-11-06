package net.fachpersonal.uno.exceptions;

public enum UNOERR {
    CARD_MISSING_INDEX("No index given! Cannot create new Card!"),
    PACKAGE_INVALID_FORMAT("Given package format is invalid");

    private String msg;

    UNOERR(String msg) {
        this.msg = msg;
    }

    public String getErrorMessage() {
        return msg;
    }
}
