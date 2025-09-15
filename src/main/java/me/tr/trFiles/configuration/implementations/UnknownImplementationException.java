package me.tr.trFiles.configuration.implementations;

import me.tr.trFiles.configuration.management.FileUtility;

import java.io.File;

public class UnknownImplementationException extends RuntimeException {
    public UnknownImplementationException(String message) {
        super(message);
    }

    public UnknownImplementationException(File file) {
        super("The implementation of " + FileUtility.getExtension(file) + " is not recognized or not supported.");
    }
}
