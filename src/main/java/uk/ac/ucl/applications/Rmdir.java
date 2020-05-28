package uk.ac.ucl.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class Rmdir implements Application {

    /**
     * Will remove an empty directory within the current directory or if the -r tag is appllied will remove
     * all subdirectories and files from the named directory passed as an argument.
     * 
     * @param commandLineArgs Holds nothing or the requested path
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    Exception thrown by BufferedWriter because of an issue such as a closed pipe
     */

    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        checkArgs(commandLineArgs);
        File directory;
        if (commandLineArgs.size() == 2) {
            directory = new File(Jsh.getCurrentDirectory() + System.getProperty("file.separator") + commandLineArgs.get(1));
            if (directory.isDirectory()) {
                removeEntireDirectory(directory);
            } else {
                throw new RuntimeException("Directory does not exist");
            }
        } else {
            directory = new File(Jsh.getCurrentDirectory() + System.getProperty("file.separator") + commandLineArgs.get(0));
            removeEmptyDirectory(directory);
        }
    }

    /**
     * If -r flag applied the the method will recursively call itself and delete all folders and
     * files inside the folder passed as an argument.
     * 
     * @param directory The File or directory to be deleted.
     */
    private void removeEntireDirectory(File directory) {
        if (directory.isDirectory()) {
            if (directory.list().length == 0) {
                directory.delete();
            } else {
                String files[] = directory.list();
                for (String fileName : files) {
                    File deleteFile = new File(directory, fileName);
                    removeEntireDirectory(deleteFile);
                }
                directory.delete();
            }
        } else {
            directory.delete();
        }
    }

    /**
     * If no flag is given then checks to see if the directory exists and is empty, if so then the 
     * directory is deleted.
     * 
     * @param directory The name of the directory to be deleted.
     */
    private void removeEmptyDirectory(File directory) {
        if (!directory.exists()) {
            throw new RuntimeException("Directory does not exist");
        } else if (!directory.isDirectory()) {
            throw new RuntimeException(directory.getName() + " is not a directory");
        }
        if (directory.list().length == 0) {
            directory.delete();
        } else {
            throw new RuntimeException("Directory is not empty");
        }
    }

    /**
     * Performs sanity checks of the number of arguments passed to the application
     * 
     * @param commandLineArgs List of arguments passed to the application
     */
    private void checkArgs(ArrayList<String> commandLineArgs) {
        if (commandLineArgs.size() != 1 && commandLineArgs.size() != 2) {
            throw new RuntimeException("rmdir: Wrong number of arguments");
        }
        if (commandLineArgs.size() == 2 && !commandLineArgs.get(0).equals("-r")) {
            throw new RuntimeException("rmdir: " + commandLineArgs.get(0) + " is not a valid flag");
        }
    }
}