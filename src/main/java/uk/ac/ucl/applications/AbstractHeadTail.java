package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public abstract class AbstractHeadTail implements Application {
    protected OutputStream output;
    protected String name;

    /**
     * Outputs specified number of lines to the outputstream of the application. Depending on the application the implementation will
     * differ. This method should be implemented with the desired behaviour (read lines from start or end of file)
     * 
     * @param lines     The number of lines the application should operate over
     * @param reader    The BufferedReader used to read the InputStream given to the application
     * @throws IOException
     */
    protected abstract void implementation(int lines, BufferedReader reader) throws IOException;
    private static final int DEFAULT_NUM_OF_LINES = 10;

    /**
     * Checks to see if the correct number of arguments have been passed. If not enough paramters have been
     * passed it then checks to see if the InputStream is not null to see if it can use the result of a pipe
     * or IO redirection to make up the missing argument. If not a RuntimeException is thrown.
     * 
<<<<<<< HEAD
     * @param appArgs The list of arguments passed to the application
     * @param input   The InputStream the application has access to, will contain results of a pipe or IO redirection if one has occured.
=======
     * @param commandLineArgs The list of arguments passed to the application
     * @param input The InputStream the application has access to, will contain results of a pipe or IO redirection if one has occured.
>>>>>>> 49d97f2923221019681d97aebec35d88483fddf1
     */
    private void checkArgs(ArrayList<String> commandLineArgs, InputStream input) {
        int numOfArgs = commandLineArgs.size();
        if (numOfArgs == 1) {
            return;
        } else if (numOfArgs == 3) {
            if (!"-n".equals(commandLineArgs.get(0)))
                throw new RuntimeException(name + ": wrong argument " + commandLineArgs.get(0));
        } else if (numOfArgs == 2 && input != null) {
            if (!"-n".equals(commandLineArgs.get(0)))
                throw new RuntimeException(name + ": wrong argument " + commandLineArgs.get(0));
        } else if (!(numOfArgs == 0 && input != null)) {
            throw new RuntimeException(name + ": wrong number of arguments ");
        }
    }

    /**
     * Based on number of command line arguments given, works out which argument indicates the number of lines
     * to be read. If the argument cannot be interpreted as an Integer value an RuntimeException will be thrown
     * and application execution is stopped.
     * 
     * @param appArgs The list of arguments passed to the application
     * @return        The number of lines over which the application should operate over. If not specified use the default
     *                value of 10.
     */
    private int findNumberOfLines(ArrayList<String> commandLineArgs) {
        if (commandLineArgs.size() == 3 || commandLineArgs.size() == 2) {
            try {
                return Integer.parseInt(commandLineArgs.get(1));
            } catch (Exception e) {
                throw new RuntimeException(name + ": wrong argument " + commandLineArgs.get(1));
            }
        } else {
            return AbstractHeadTail.DEFAULT_NUM_OF_LINES;
        }
    }

    /**
     * Based on the number of arguments passed to the application, returns the item at the array index that
     * corresponds to the file name over which to operate.
     * 
     * @param appArgs The list of arguments passed to the application
     * @return        The String representing the file name
     */
    private String findFileArg(ArrayList<String> commandLineArgs) {
        if (commandLineArgs.size() == 3) {
            return commandLineArgs.get(2);
        } else {
            return commandLineArgs.get(0);
        }
    }

    /**
     * Determines whether to provide head or tail functionality and sets up resources needed by the
     * implementation functions
     *
     * @param fileArg The string holding the name of the file to be operated over
     * @param lines   The integer representing the number of lines to be outputed to the console
     */
    private void displayLines(String fileArg, int lines) {
        Path filePath = Paths.get((String) Jsh.getCurrentDirectory() + File.separator + fileArg);
        try (BufferedReader reader = Files.newBufferedReader(filePath, Jsh.encoding)) {
            implementation(lines, reader);
        } catch (IOException e) {
            throw new RuntimeException(name + ": cannot open " + fileArg);
        }
    }

    /**
     * Sets the encoding to be used and attempts to write the specified number of lines to the ouputstream. If
     * there's an issue opening the file then a RuntimeException. This method is overloaded. This particular function
     * is used when a pipe or redirection is occuring.
     * 
     * @param input The inputstream that the application should write to.
     * @param lines The number of lines the application should display.
     * @throws IOException
     */
    private void displayLines(InputStream input, int lines) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, Jsh.encoding));
        implementation(lines, reader);
    }

    /**
     * Method should be called in the applications execute method. This method carries out most of the functionailty
     * of the application by calling helper funtions in the interface.
     * 
     * @param commandLineArgs The ArrayList of type String that contains the command line arguments passed
     * @param input           Inputstream to read from if application is part of a pipe or redirection
     * @throws IOException
     */
    public void superExecute(ArrayList<String> commandLineArgs, InputStream input) throws IOException {
        checkArgs(commandLineArgs,input);
        int lines = findNumberOfLines(commandLineArgs);
        if (commandLineArgs.size() == 1 || commandLineArgs.size() == 3) {
            String fileArg = findFileArg(commandLineArgs);
            File file = new File(Jsh.getCurrentDirectory() + File.separator + fileArg);
            if (file.exists()) {
                displayLines(fileArg, lines);
            } else {
                throw new RuntimeException(name + ": " + fileArg + " does not exist");
            }
        } else {
            displayLines(input, lines);
        }
    }
}