package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public interface Application {
    
    /**
     * This method is the starting point for all applications to begin executing their behaviour.
     * Using the arguments and InputStream provided it will call helper functions if needed and output
     * the result of the application to an OutputStream.
     * 
     * @param commandLineArgs ArrayList of arguments passed to the application
     * @param input           InputStream used to read input from if a pipe or redirection is being conducted
     * @param output          OutputStream used to write the output of the application to
     * @throws IOException    Exception thrown by BufferedWriter because of something like a closed pipe
     */

    void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException;

}