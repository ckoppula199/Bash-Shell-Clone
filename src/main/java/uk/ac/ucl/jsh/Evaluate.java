package uk.ac.ucl.jsh;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class Description
 * 
 * This class is part of the Visitor pattern. It is used to decide what should be done when a certain command is recieved.
 * It makes use of stacks of InputStreams and OutputStreams. For each redirection or pipe InputStreams and OutputStreans will be pushed
 * onto the stacks. When visitng a Call they will popped off and passed to the execute method of the application. 
 */

public class Evaluate implements CommandVisitor {

    private ApplicationFactory factory = new StandardApplicationFactory();
    private InputStream input;
    private OutputStream output;
    private Stack<InputStream> inputs = new Stack<>();
    private Stack<OutputStream> outputs = new Stack<>();

    public Evaluate(OutputStream output, InputStream input) {
        this.output = output;
        this.input = input;
        inputs.push(this.input);
        outputs.push(this.output);
    }

    /**
     * Checks for input and output redirection and modifies the stack accoridingly. Then pops off the top input and output streams 
     * and passes them to the execute method of the application.
     */

    @Override
    public void visit(Call call) throws IOException {
        resolveRedirect(call);

        InputStream input = inputs.pop();
        OutputStream output = outputs.pop();

        ArrayList<String> commandLineArgs = call.getCommandLineArgs();
        Application application = factory.createApplication(call.getCommand());
        application.execute(commandLineArgs, input, output);
    }

    private void resolveRedirect(Call call) throws FileNotFoundException {
        String fileInputName = call.getInputFileName();
        String fileOutputName = call.getOutputFileName();
        if (fileInputName != null) {
            inputs.pop();
            String filePath = Jsh.getCurrentDirectory() + System.getProperty("file.separator") + fileInputName;
            inputs.push(new FileInputStream(new File(filePath)));
        }
        if (fileOutputName != null) {
            outputs.pop();
            String filePath = Jsh.getCurrentDirectory() + System.getProperty("file.separator") + fileOutputName;
            outputs.push(new FileOutputStream(new File(filePath)));
        }
    }

    /**
     * Calls accept method on the left child, resets the input and output streams to what was passed to the visitor initially, 
     * then calls accept on the right child
     */

    @Override
    public void visit(Seq seq) throws IOException {
        seq.getLeft().accept(this);
        inputs.push(this.input);
        outputs.push(this.output);
        Command right = seq.getRight();
        if (right != null) right.accept(this);

    }

    /**
     * A PipedOutputStream is connected to a PipedInputStream and pushed onto the stack. It is then used and written to by the left child.
     * This means the output of the left child is now stored in the PipedInputStream. This is then pushed onto the stack and used by the right child. 
     */

    @Override
    public void visit(Pipe pipe) throws IOException {
        //use of PipedInputStream and PipedOutputStream recommended by Sergey for sake of simplicity despite not being a perfect solution.

        InputStream input = new PipedInputStream(100000); //size of PipedInputStream also recomended to us by Sergey. Done so it can handle larger files
        OutputStream output = new PipedOutputStream((PipedInputStream) input);

        outputs.push(output);
        pipe.getLeft().accept(this);
        output.close();
        inputs.push(input);
        pipe.getRight().accept(this);
    }
}