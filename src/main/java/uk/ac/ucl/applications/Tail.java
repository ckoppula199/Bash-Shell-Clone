package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Jsh;

public class Tail extends AbstractHeadTail {

    /**
     * Carries out a series of helper functions in order to implement the tail functionalty
     * Aims to print to the output stream the specified number of lines (or 10 if not specified) from the end of a
     * specified file.
     *
     * @param commandLineArgs The ArrayList of type String that contains the command line arguments passed
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    If file cannot be opened or read from due to permission issues or otherwise.
     */

    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        this.output = output;
        this.name = "tail";
        superExecute(commandLineArgs, input);
    }

    @Override
    /* Outputs specified number of lines from end of specified file to the output stream
       if less lines than specified outputs entire file */
    protected void implementation(int lines, BufferedReader reader) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.output, Jsh.encoding));
        ArrayList<String> storage = readInFile(reader);
        int index = 0;
        if (lines > storage.size()) {
            index = 0;
        } else {
            index = storage.size() - lines;
        }
        for (int i = index; i < storage.size(); i++) {
            writer.write(storage.get(i) + System.getProperty("line.separator"));
            writer.flush();
        }
    }

    /**
     * Stores contents of a buffer into and array of strings, one line at a time
     *
     * @param reader        The BufferedReader that has the data to be read in to
     * @return              The ArrayList of strings in the buffer
     * @throws IOException  If unable to read from the buffer due to pipe being closed or otherwise
     */
    private ArrayList<String> readInFile(BufferedReader reader) throws IOException {
        String line = null;
        ArrayList<String> storage = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            storage.add(line);
        }
        return storage;
    }
}