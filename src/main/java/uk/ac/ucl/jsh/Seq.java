package uk.ac.ucl.jsh;

import java.io.IOException;

public class Seq implements Command {

    private Command left;
    private Command right;

    public Seq(Command left, Command right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(CommandVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    public Command getLeft(){
        return left;
    }

    public Command getRight(){
        return right;
    }   
}