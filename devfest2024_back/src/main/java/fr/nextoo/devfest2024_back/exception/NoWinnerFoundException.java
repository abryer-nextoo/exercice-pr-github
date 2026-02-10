package fr.nextoo.devfest2024_back.exception;

public class NoWinnerFoundException extends RuntimeException {
    public NoWinnerFoundException(String message) { super(message); }
}