package uk.ac.ucl.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class Mkdir implements Application {

    /**
     * Will create a directory inside the current directory with the name provided assuing the name is valid
     * Does not create directory within directories e.g. mkdir one/two will not work.
     * 
     * @param commandLineArgs Holds nothing or the requested path
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    Exception thrown by BufferedWriter because of something like a closed pipe
     */

    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        if (commandLineArgs.size() != 1) {
            throw new RuntimeException("mkdir: only one argument allowed");
        }
        String dirName = commandLineArgs.get(0);
        File newDir = new File(Jsh.getCurrentDirectory() + System.getProperty("file.separator") + dirName);
        if (newDir.isFile()) {
            throw new RuntimeException("mkdir: cannot create directory '" + dirName + "': File Exists");
        } else if (newDir.isDirectory()) {
            throw new RuntimeException("mkdir: cannot create directory '" + dirName + "': Directory Exists");
        }
        newDir.mkdir();
    }
}