package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Jsh;


public class Head extends AbstractHeadTail {

    /**
     * Carries out a series of helper functions in order to implement head functionalty
     * Aims to print to the output stream the specified number of lines (or 10 if not specified) from the start of a
     * specified file.
     *
     * @param commandLineArgs The ArrayList of type String that contains the command line arguments passed
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    If file cannot be opened or read from due to permission issues or otherwise.
     */
    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        this.output = output;
        this.name = "head";
        superExecute(commandLineArgs, input);
    }
    
    /**
     * Outputs specified number of lines from start of specified file to the output stream
     * if less lines than specified outputs entire file
     *
     * @param lines         The integer representing the number of lines to be outputed to the output stream
     * @param reader        The BufferedReader used to read the file
     * @throws IOException  If reader or writer can't be accessed or if there is an issue with the file
     */
    @Override
    protected void implementation(int lines, BufferedReader reader) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.output, Jsh.encoding));
        for (int i = 0; i < lines; i++) {
            String line = null;
            if ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        }
    }
}