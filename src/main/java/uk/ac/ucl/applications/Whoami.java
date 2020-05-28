package uk.ac.ucl.applications;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class Whoami implements Application {

    /**
     * Gives the username of the current user to the OutputStream
     * 
     * @param input             The InputStream from which to read from if a pipe or redirection is occuring
     * @param output            The OutputStream to write the result of the application to.
     * @param commandLineArgs   Holds all the arguments given by the user.
     * @throws IOException      Exception thrown by BufferedWriter because of something like a closed pipe
     */

    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        writer.write(System.getProperty("user.name"));
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}