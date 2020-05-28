package uk.ac.ucl.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class CD implements Application {


    /**
     * Checks if the correct number of arguments are given and then creates a file to the directory specified by the argument.
     * exception is thrown if the given argument does not point to a directory
     *
     * @param commandLineArgs Holds the new dirctory that is being requested
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    Exception thrown by BufferedWriter because of something like a closed pipe
     */

    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        checkForCorrectNoOfArgs(commandLineArgs);
        String dirString = commandLineArgs.get(0);
        File dir = new File(Jsh.getCurrentDirectory(), dirString);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("cd: " + dirString + " is not an existing directory");
        }
        Jsh.setCurrentDirectory(dir.getCanonicalPath());
    }



     /**
     * Checks if the appArgs holds the correct number of arguments
     *
     * @param commandLineArgs Holds the array of arguments
     */
    private void checkForCorrectNoOfArgs(ArrayList<String> commandLineArgs) {
        if (commandLineArgs.isEmpty()) {
            throw new RuntimeException("cd : missing argument(s)");
        } else if (commandLineArgs.size() > 1) {
            throw new RuntimeException("cd : too many arguments");
        }
    }
}