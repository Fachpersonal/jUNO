package net.Gruppe.uno.exceptions;

/**
 * Klasse die alle Custom Exceptions-Messages beinhaltet
 */
public enum UNOERR {
    CARD_MISSING_INDEX("No index given! Cannot create new Card!"), PACKAGE_INVALID_FORMAT("Given package format is invalid"),
    CANNOT_CREATE_CLIENT("CANNOT CREATE CLIENT"),
    GAME_STARTED("Sorry but the game on this server has already started!");

    private final String msg;

    UNOERR(String msg) {
        this.msg = msg;
    }

    public String getErrorMessage() {
        return msg;
    }
}
