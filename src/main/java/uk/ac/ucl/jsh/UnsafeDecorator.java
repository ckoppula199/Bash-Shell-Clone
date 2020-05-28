package uk.ac.ucl.jsh;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class UnsafeDecorator implements Application {
    Application application;

    public UnsafeDecorator(Application application) {
        this.application = application;
    }

    /**
     * Wraps the execute method of the application method in a try catch. If an error id caught then it instead of propogating it back up
     * the error message is printed to the OutputStream. This prevents the command from being immediatly terminated.
     * 
     * @param commandLineArgs holds the new dirctory that is being requested
     * @param input The InputStream from which to read from if a pipe or redirection is occuring
     * @param output The OutputStream to write the result of the application to.
     * @throws IOException exception thrown by BufferedWriter because of something like a closed pipe
     */
    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output)throws IOException {
        try{
            application.execute(commandLineArgs, input, output);
        } catch(Exception exception) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            writer.write(exception.getMessage());
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }

    }

}