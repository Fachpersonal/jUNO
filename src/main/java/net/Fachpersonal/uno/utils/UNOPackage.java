package net.Fachpersonal.uno.utils;

import net.Fachpersonal.uno.exceptions.UNOERR;
import net.Fachpersonal.uno.exceptions.UNOException;

public class UNOPackage {

    private final Type type;
    private final String value;

    public UNOPackage(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public UNOPackage(String package_Message) throws UNOException {
        char[] pm = package_Message.toCharArray();
        if (pm[0] != '{' || pm[pm.length - 2] != '}' || !package_Message.contains(",") || pm[pm.length - 1] != ';') {
            System.out.println("["+(pm[0] != '{')+","+ (pm[pm.length - 3] != '}')+","+package_Message.contains(",")+","+(pm[pm.length - 2] != ';')+"]");
            throw new UNOException(UNOERR.PACKAGE_INVALID_FORMAT);
        }
        StringBuilder type = new StringBuilder();
        StringBuilder value = new StringBuilder();

        boolean b = false;
        boolean commaFound = false;
        if(pm[0] != '{') {
            System.out.println("Missing \'{\'");
            throw new UNOException(UNOERR.PACKAGE_INVALID_FORMAT);
        }
        if(pm[pm.length-1] != ';') {
            System.out.println("Missing \';\'");
            throw new UNOException(UNOERR.PACKAGE_INVALID_FORMAT);
        }
        if(pm[pm.length-2] == '}') {
            System.out.println("Missing \'}\'");
            throw new UNOException(UNOERR.PACKAGE_INVALID_FORMAT);
        }

        // TODO: needs rework for messaging
        if (!commaFound) {
            throw new UNOException(UNOERR.PACKAGE_INVALID_FORMAT);
        }
        System.out.println(type+","+value);
        this.type = Type.valueOf(type.toString());
        this.value = value.toString();
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "{type:\"" + type + "\",value:\"" + value + "\"};";
    }

    public enum Type {
        INIT, COMMAND
    }
}
