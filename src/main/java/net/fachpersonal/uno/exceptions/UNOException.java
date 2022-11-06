package net.fachpersonal.uno.exceptions;

public class UNOException extends Exception{
    public enum CustomException{
        CARD_MISSING_INDEX("No index given! Cannot create new Card!");

        private String msg;
        CustomException (String msg) {
            this.msg = msg;
        }
        public String getErrorMessage() {return msg;}
    }

    private CustomException customException;

    public UNOException(CustomException ce) {
        super(ce.getErrorMessage());
        this.customException = ce;
    }

    public CustomException getCustomException() {return customException;}

}
