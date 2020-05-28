package uk.ac.ucl.applications;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

import java.util.regex.Matcher;

public class Find implements Application {

    /**
     * Concatenates all the files that are requested as arguments and outputs them to the console
     * if there are incorrenct number of arguments, a file does not exist or a file cannot be opened, exceptions are thrown
     * 
     * @param input             The InputStream from which to read from if a pipe or redirection is occuring
     * @param output            The OutputStream to write the result of the application to.
     * @param commandLineArgs   Holds all the arguments given by the user.
     * @throws IOException      Exception thrown by BufferedWriter because of something like a closed pipe
     */
    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        if (commandLineArgs.size() < 2) {
            throw new RuntimeException("find: missing arguments");
        } else {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
            find(commandLineArgs, writer);
        }
    }

    /**
     * Gets the correct directory from where the files will be searched from and throws an error if the -name flag is missing
     * makes a pattern which will be used with a matcher to find the matching file names via the listMatchingPaths method
     * 
     * @param commandLineArgs Holds all the arguments given by the user
     * @param writer          The BufferedWriter used to output to the output stream
     */
    private void find(ArrayList<String> commandLineArgs, BufferedWriter writer) throws IOException {
        String directory = getCorrectDir(commandLineArgs);
        if (commandLineArgs.get(0).compareTo("-name") != 0) {
            throw new RuntimeException("find: " + commandLineArgs.get(0) + " incorrect argument");
        }
        File file = new File(directory);
        Pattern pattern = Pattern.compile(commandLineArgs.get(1).replace("*", ".*"));
        listMatchingPaths(file, pattern, writer);
    }

    /**
     * Uses recursion to find all matching file paths in depth-first
     * when a file matches the given pattern, the first part of path that is the current directory is replaced by "."
     * the file path is written to the output stream followed by a new line
     * 
     * @param originalFile  Contains the current file that is being searched
     * @param pattern       Contains the pattern that will be used to match the file names
     * @param writer        The BufferedWriter used to output to the output stream
     */
    private void listMatchingPaths(File originalFile, Pattern pattern, Writer writer) throws IOException {
        File[] files = originalFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                listMatchingPaths(file, pattern, writer);
                continue;
            }
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                writer.write(file.getPath().replaceFirst(Jsh.getCurrentDirectory(), "."));
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        }
    }

    /**
     * This method checks if the user has specified a directory to use, if not, uses current directory
     * throws exceptions if user has not specified a valid directory
     * 
     * @param commandLineArgs Holds all the arguments given by the user
     * @return                Returns the correct directory to start the search from
     */
    private String getCorrectDir(ArrayList<String> commandLineArgs) throws IOException {
        if (commandLineArgs.get(0).compareTo("-name") != 0) {
            File dir = new File(Jsh.getCurrentDirectory() + System.getProperty("file.separator") + commandLineArgs.get(0));
            if (!dir.exists()) {
                throw new RuntimeException("find: " + commandLineArgs.get(0) + " is not an existing directory");
            } else if (!dir.isDirectory()) {
                throw new RuntimeException("find: " + commandLineArgs.get(0) + " is not a directory");
            }
            commandLineArgs.remove(0);
            return dir.getPath();
        }
        return Jsh.getCurrentDirectory();
    }
}