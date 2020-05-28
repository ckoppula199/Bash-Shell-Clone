package uk.ac.ucl.applications;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class Datetime implements Application {
    //Run application by typing in date, application named Datetime to avoid name conflict with imported Date package


    /**
     * Outputs to the OutputStream the current date and time in the following format.
     * E M d H:m:s z Y
     * according to date and time patterns described in the oracle docs for SimpleDateFormat.
     *
     * @param commandLineArgs Holds the new dirctory that is being requested
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    Exception thrown by BufferedWriter because of something like a closed pipe
     */

    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        writer.write(date.toString());
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}