package uk.ac.ucl.applications;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class LS implements Application {


    /**
     * Outputs all the subdirectories in the current directory (if no arguments are given)
     * or the subdirecties in the path provided in the argument
     * if more than one argument is given, an exception is thrown
     *
     * @param commandLineArgs Holds nothing or the requested path
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    Exception thrown by BufferedWriter because of something like a closed pipe
     */

    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        File currDir = getCurrDir(commandLineArgs);
        try {
            printFileNames(writer, currDir);
        } catch (NullPointerException e) {
            throw new RuntimeException("ls: no such directory");
        }

    }

    /**
     * If there are no arguments in the appArgs parameter, it will return the current directory
     * If there is 1 argument, it will return the directory specified by the argument
     * If there are more than 1 arguments, an exception is thrown
     *
     * @param commandLineArgs Holds nothing or the requested directory
     * @return                Returns the File of which the subdirectories will be listed from
     */
    private File getCurrDir(ArrayList<String> commandLineArgs) {
        if (commandLineArgs.isEmpty()) {
            return new File(Jsh.getCurrentDirectory());
        } else if (commandLineArgs.size() == 1) {
            return new File(Jsh.getCurrentDirectory() + System.getProperty("file.separator") + commandLineArgs.get(0));
        }
        throw new RuntimeException("ls: too many arguments");
    }


    /**
     * Outputs all of the subdirectories in the given directory and adds a line separator if at least one subdirectory is outputted
     *
     * @param writer        BufferedWriter used to ouput the subdirectories on the output stream
     * @param currDir       Holds the directory where the subdirectories will be listed from
     * @throws IOException  Exception thrown by BufferedWriter because of something like a closed pipe
     */

    private void printFileNames(BufferedWriter writer, File currDir) throws IOException {
        File[] listOfFiles = currDir.listFiles();
        boolean atLeastOnePrinted = false;
        for (File file : listOfFiles) {
            atLeastOnePrinted = printFileNamesHelper(writer, atLeastOnePrinted, file);
        }
        if (atLeastOnePrinted) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }

    /**
     * Writes name of file to the OutputStream followed by a tab if not the final file name.
     * 
     * @param writer                BufferedWriter used to ouput the subdirectories on the output stream
     * @param atLeastOnePrinted     Checks to see if the file name being outputted is the last one or not
     * @param file                  File to be be outputted
     * @return                      Boolean indicating if line has been printed to the OutputStream.
     * @throws IOException
     */
    private boolean printFileNamesHelper(BufferedWriter writer, boolean atLeastOnePrinted, File file) throws IOException {
        if (!file.getName().startsWith(".")) {
            if (!atLeastOnePrinted) {
                writer.write(file.getName());
                writer.flush();
                return true;
            }
            writer.write("\t" + file.getName());
            writer.flush();
        }
        return false;
    }
}