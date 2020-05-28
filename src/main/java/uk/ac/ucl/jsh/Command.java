package uk.ac.ucl.jsh;

import java.io.IOException;

/**
 * This interface is part of the visitor pattern. It has an accept method that calls the visit method of 
 * a CommandVisitor passed to it
 */
public interface Command {

    void accept(CommandVisitor visitor) throws IOException;
    
}