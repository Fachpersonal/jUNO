package net.Fachpersonal.uno.exceptions;

public class UNOException extends Exception {
    private final UNOERR unoerr;

    public UNOException(UNOERR ue) {
        super(ue.getErrorMessage());
        this.unoerr = ue;
    }

    public UNOERR getUnoError() {
        return unoerr;
    }

}
