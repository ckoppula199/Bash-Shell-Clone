package uk.ac.ucl.jsh;

import java.io.IOException;

/**
 * This interface is part of the visitor pattern. It defines the types of Commands for which it can define behaviour for.
 * Based on the instance of the Command passed to the visit method a different method will be executed with specific
 * behaviour and logic.
 */

public interface CommandVisitor {

    void visit(Call call) throws IOException;
    void visit(Pipe pipe) throws IOException;
    void visit(Seq seq) throws IOException;
}