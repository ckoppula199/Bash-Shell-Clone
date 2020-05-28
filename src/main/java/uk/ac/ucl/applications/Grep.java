package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class Grep implements Application {

    /**
     * Command that when given a pattern and a list of files, returns any lines from those files in which
     * the pattern is matched
     *
     * @param commandLineArgs The ArrayList of type String that contains the command line arguments passed
     * @param input           The InputStream from which to read from if a pipe or redirection is occuring
     * @param output          The OutputStream to write the result of the application to.
     */
    @Override
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        if (commandLineArgs.size() > 1) {
            Path[] filePathArray = getPathArray(commandLineArgs.subList(1, commandLineArgs.size()));
            Pattern grepPattern = Pattern.compile(commandLineArgs.get(0));
            checkForPattern(grepPattern, filePathArray, writer);
        } else if (commandLineArgs.size() == 1 && input != null) {
            Pattern grepPattern = Pattern.compile(commandLineArgs.get(0));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, Jsh.encoding));
            outputMatchedLines(grepPattern, reader, writer);
        } else {
            throw new RuntimeException("grep: wrong number of arguments");
        }
    }

    /**
     * Adds all files provided by the user as arguments to an array after checking if they are valid
     *
     * @param commandLineArgs The ArrayList of type String that contains the command line arguments passed
     * @return                A list of all valid paths
     */
    private Path[] getPathArray(List<String> commandLineArgs) {
        int numOfFiles = commandLineArgs.size();
        Path[] filePathArray = new Path[numOfFiles];
        Path currentDir = Paths.get(Jsh.getCurrentDirectory());
        for (int i = 0; i < numOfFiles; i++) {
            filePathArray[i] = currentDir.resolve(commandLineArgs.get(i));
        }
        return filePathArray;
    }

    /**
     * For each file calls a function to output matched lines
     *
     * @param grepPattern   The Pattern to apply to the file and filter lines on
     * @param filePathArray The array of valid paths provided by the user
     * @param writer        The BufferedWriter used to write lines that match the pattern to the output stream
     */
    private void checkForPattern(Pattern grepPattern, Path[] filePathArray, BufferedWriter writer) {
        for (int j = 0; j < filePathArray.length; j++) {
            try (BufferedReader reader = Files.newBufferedReader(filePathArray[j], Jsh.encoding)) {
                outputMatchedLines(grepPattern, reader, writer);
            } catch (IOException e) {
                throw new RuntimeException("grep: cannot open " + filePathArray[j].getFileName());
            }
        }
    }

    /**
     * Checks to see if provided pattern matches any lines in the file and output to the output stream the line thst do.
     *
     * @param grepPattern  The pattern specified by the user
     * @param reader       The BufferedReader used to read the files
     * @param writer       The BufferedWriter used to write lines that match the pattern to the output stream
     * @throws IOException Thrown when an issue with BufferedReader/Writer due to a closed pipe or file permissions
     */
    private void outputMatchedLines(Pattern grepPattern, BufferedReader reader, BufferedWriter writer) throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = grepPattern.matcher(line);
            if (matcher.find()) {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        }
    }
}