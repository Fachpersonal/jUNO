package net.Fachpersonal;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.server.UNOServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, UNOException {
        System.out.println("Hello world!");
        UNOServer.DefaultUNOServer();
    }
}