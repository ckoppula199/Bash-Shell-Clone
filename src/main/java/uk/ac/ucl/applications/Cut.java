package uk.ac.ucl.applications;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class Cut implements Application {


    /**
     * Uses the forrmat cut -d <char to split on> <file name> -f <index in split list STARTING AT 1 NOT 0>
     * splits a file using a specified character then returns the split text at the specified index int the resulting
     * list where the array indexing starts at 1.
     *
     * @param commandLineArgs Holds the new dirctory that is being requested
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     * @throws IOException    Exception thrown by BufferedWriter because of something like a closed pipe
     */

    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        checkArguments(commandLineArgs);
        String[] splitUpFile = getFileContents(commandLineArgs.get(2)).split(commandLineArgs.get(1));
        int index = getIndex(commandLineArgs.get(4));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        writeIndex(splitUpFile, index, writer);
    }

    /**
     * Outputs the split text at the specified index assuming the index provided is valid, otherwise throws an exception
     * due to an invalid index.
     * 
     * @param splitUpFile  List of split up strings from the file 
     * @param index        Index of the split up array to be returned using array indexing starting at 1
     * @param writer       BufferedWriter used to write to the OutputStream
     * @throws IOException Exception thrown by BufferedWriter because of something like a closed pipe
     */
    private void writeIndex(String[] splitUpFile, int index, BufferedWriter writer) throws IOException {
        try {
            String result = splitUpFile[index];
            writer.write(result);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        } catch(IndexOutOfBoundsException ioobe) {
            throw new RuntimeException("cut: invalid index argument " + (index + 1));
        }
    }

    private int getIndex(String indexArg) {
        try {
            return Integer.parseInt(indexArg) - 1;
        } catch(NumberFormatException nfe) {
            throw new RuntimeException("cut: invalid index argument " + indexArg);
        }
    }

    private void checkArguments(ArrayList<String> commandLineArgs) {
        if(commandLineArgs.size() != 5) {
            throw new RuntimeException("Cut: Invalid number of arguments");
        } else if(!commandLineArgs.get(0).equals("-d")) {
            throw new RuntimeException("Cut: Invlaid argument: " + commandLineArgs.get(0));
        } else if(!commandLineArgs.get(3).equals("-f")) {
            throw new RuntimeException("Cut: Invlaid argument: " + commandLineArgs.get(3));
        } else if(commandLineArgs.get(1).length() != 1) {
            throw new RuntimeException("Cut: Invalid argument: " + commandLineArgs.get(1) + ". Must be a single character");
        }
    }

    /**
     * Returns the contents of the file that has been specified as a string
     * Throws an exception if the file cannot be read from
     * 
     * @param fileName The name of the file whose contents need to be converted into a string
     * @return         The contents of the file as a string
     */
    private String getFileContents(String fileName) {
        Path filePath = Paths.get(Jsh.getCurrentDirectory() + File.separator + fileName);
        try {
            return Files.readString(filePath, Jsh.encoding);
        } catch (IOException e) {
            throw new RuntimeException("cut: cannot open " + fileName);
        }
    }
}