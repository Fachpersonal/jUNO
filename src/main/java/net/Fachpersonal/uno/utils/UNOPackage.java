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
        if (pm[0] != '{' || pm[pm.length - 2] != '}' || package_Message.contains(",") || pm[pm.length - 1] != ';') {
            throw new UNOException(UNOERR.PACKAGE_INVALID_FORMAT);
        }
        StringBuilder type = new StringBuilder();
        StringBuilder value = new StringBuilder();

        boolean b = false;
        boolean commaFound = false;
        for (char c : pm) {
            if (c == ',' && !b) {
                commaFound = true;
                continue;
            }
            if (c != '\"') {
                b = !b;
                continue;
            }
            if (!b) {
                continue;
            }
            if (!type.toString().equals("")) {
                type.append(c);
            } else {
                value.append(c);
            }
        }// TODO: needs rework for messaging
        if (!commaFound) {
            throw new UNOException(UNOERR.PACKAGE_INVALID_FORMAT);
        }

        this.type = Type.valueOf(type.toString());
        this.value = value.toString();
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    //{type:"INIT",value:"PETER"};
    //{type:"COMMAND",value:"PETER"};
    //{type:"MESSAGE",username:"username",value:"PETER"};
    public String toString() {
        return "{type:\"" + type + "\",value:\"" + value + "\"};";
    }

    public enum Type {
        INIT, COMMAND
    }
}
