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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cat implements Application {

    /**
     * Concatenates all the files that are requested as arguments and outputs them to the console
     * if there are no arguments, a file does not exist or a file cannot be opened, exceptions are thrown
     *
     * @param commandLineArgs Holds all the files requested by the user
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     */
    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        if (!commandLineArgs.isEmpty()) {
            concatFiles(commandLineArgs, writer);
        } else {
            if (input == null) {
                throw new RuntimeException("cat: missing arguments");
            } else {
                concatFromStdIn(input, writer);
            }
        }
    }

    /**
     * Writes to the OutputStream from the InputStream, used during a pipe or redirection
     * 
     * @param input     The InputStream from which to read from if a pipe or redirection is occuring
     * @param writer    The BufferedWriter used to output to the output stream
     * @throws IOException 
     */
    private void concatFromStdIn(InputStream input, BufferedWriter writer) throws IOException {
        String line = new String(input.readAllBytes());
        writer.write(line);
        writer.flush();
    }

    /**
     * Goes through each of the requested files and attempts to output them to the console
     * if the file does not exist or the file cannot be opened, an exception is thrown
     *
     * @param commandLineArgs Holds all the files requested by the user
     * @param writer          The BufferedWriter used to output to the output stream
     */
    private void concatFiles(ArrayList<String> commandLineArgs, BufferedWriter writer) {
        String currentDirectory = Jsh.getCurrentDirectory();
        for (String arg : commandLineArgs) {
            writeFile(writer, currentDirectory, arg);
        }
    }

    /**
     * Writes the output of the file to the OutputStream.
     * 
     * @param writer           The BufferedWriter used to output to the output stream
     * @param currentDirectory Current directory in which JSH is operatin in.
     * @param currentFile      Current file being written.
     */
    private void writeFile(BufferedWriter writer, String currentDirectory, String currentFile) {
        File currFile = new File(currentDirectory + File.separator + currentFile);
        if (!currFile.exists()) {
            throw new RuntimeException("cat: file " + currFile.getName() + " does not exist");
        }
        Path filePath = Paths.get(currentDirectory + File.separator + currentFile);
        try {
            String content = Files.readString(filePath, Jsh.encoding);
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("cat: cannot open " + currentFile);
        }
    }
}