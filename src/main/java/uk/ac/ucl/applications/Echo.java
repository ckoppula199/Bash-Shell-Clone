package uk.ac.ucl.applications;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class Echo implements Application {

    /**
     * All paramters passed to this application gets written to the OutputStream.
     * 
     * @param commandLineArgs Holds the new dirctory that is being requested
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    Exception thrown by BufferedWriter because of something like a closed pipe
     */

    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        echoPrintLines(commandLineArgs, writer);
        if (commandLineArgs.size() != 0) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }

    /**
     * Outputs arguments passed as strings to the console
     *
     * @param commandLineArgs ArrayList of arguments passed to the function
     * @param writer          The BufferedWriter used to output to the output stream
     * @throws IOException    Thrown if an error occurs writing to the console due to a closed pipe or otherwise
     */

    private void echoPrintLines(ArrayList<String> commandLineArgs, BufferedWriter writer) throws IOException {
        boolean first = true;
        for (String arg : commandLineArgs) {
            if (!first) {
                writer.write(" ");
            }
            writer.write(arg);
            writer.flush();
            first = false;
        }
    }
}