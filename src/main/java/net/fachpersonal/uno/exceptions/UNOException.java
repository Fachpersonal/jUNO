package net.fachpersonal.uno.exceptions;

public class UNOException extends Exception{
    private UNOERR unoerr;

    public UNOException(UNOERR ue) {
        super(ue.getErrorMessage());
        this.unoerr = ue;
    }

    public UNOERR getUnoError() {return unoerr;}

}
