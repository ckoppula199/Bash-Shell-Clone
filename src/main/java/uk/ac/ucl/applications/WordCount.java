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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

public class WordCount implements Application {

    @Override
    /**
     *
     * Control function which carries out the execution of the application. Will give the word count, character count
     * or line count depending on what tag is passed. If no tag given then will output all three.
     *
     * @param input The InputStream from which to read from if a pipe or redirection is occuring
     * @param output The OutputStream to write the result of the application to.
     * @param commandLineArgs holds all the arguments given by the user.
     * @throws IOException exception thrown by BufferedWriter because of something like a closed pipe
     */
    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        if (commandLineArgs.isEmpty()) {
            if (input == null) {
                throw new RuntimeException("wc: missing arguments");
            }
            String line = new String(input.readAllBytes());
            wordCountFromStdIn("-a", line, writer);
        } else if (commandLineArgs.size() == 1) {
            if (commandLineArgs.get(0).startsWith("-")) {
                if (input == null) {
                    throw new RuntimeException("wc: missing arguments");
                }
                String line = new String(input.readAllBytes());
                wordCountFromStdIn(commandLineArgs.get(0), line, writer);
            } else {
                wordCountFromFiles("-a", commandLineArgs, writer);
            }
        } else {
            String option = getOption(commandLineArgs);
            ArrayList<String> fileNames = new ArrayList<>(commandLineArgs);
            wordCountFromFiles(option, fileNames, writer);
        }
    }

    /**
     * handles the wc function for when stdin is being used
     * switch case is used to perform the correct operation via helper methods depending on what the user has requested
     * 
     * @param flag this is the option that the user has chosen to be performed
     * @param input this is the input from stdin that will be used
     * @param writer used to write the result to the output stream
     * @throws IOException thrown when there is an error when writing to the output stream
     */
    private void wordCountFromStdIn(String flag, String input, BufferedWriter writer) throws IOException {
        switch (flag) {
            case "-m":
                writer.write(countChars(input).toString() + System.getProperty("line.separator"));
                writer.flush();
                break;
            case "-w":
                writer.write(countWords(input).toString() + System.getProperty("line.separator"));
                writer.flush();
                break;
            case "-l":
                writer.write(countLines(input).toString() + System.getProperty("line.separator"));
                writer.flush();
                break;
            case "-a":
                writer.write(countLines(input).toString() + ' ');
                writer.write(countWords(input).toString() + ' ');
                writer.write(countChars(input).toString());
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                break;
            default:
                throw new RuntimeException("wc: invalid flag " + flag);
        }
    }

    /**
     * helper function used to count number of characters/bytes in the input
     * 
     * @param input contains the current input that is being processed as a string
     * @return the number of bytes/characters in the input as an Integer
     */
    private Integer countChars(String input) {
        return input.length();
    }

    /**
     * helper function used to count number of words in the input
     * 
     * @param input contains the current input that is being processed as a string
     * @return the number of words in the input as an Integer
     */
    private Integer countWords(String input) {
        int words = 0;
        String formattedInput = input.replaceAll("[\n]+", " ").replaceAll("[ ]+", " ");
        if (formattedInput.equals(" ")) {
            return 0;
        }
        if (!input.endsWith(" ") && !input.endsWith("\n")) {
            words++;
        }
        if (input.startsWith(" ") || input.startsWith("\n")) {
            words--;
        }
        Matcher m = Pattern.compile(" ").matcher(formattedInput);
        while (m.find()) {
            words++;
        }
        return words;
    }

    /**
     * helper function used to count number of newline characters in the input
     * 
     * @param input contains the current input that is being processed as a string
     * @return the number of newline characters in the input as an Integer
     */
    private Integer countLines(String input) {
        Matcher ma = Pattern.compile("\n").matcher(input);
        int lines = 0;
        while (ma.find()) {
            lines++;
        }
        return lines;
    }

    /**
     * handles the wc function when using files instead of stdin
     * the file is converted to a string which is then passed to the relevant helper functions depending on what the user has requested
     * 
     * @param flag this is the option that the user has chosen to be performed
     * @param fileName the name of the current file that the user has requested for this function
     * @param writer used to write the result to the output stream
     * @param totalCount holds the sum of the count of the number of chars/word/lines in all the files so far
     * @throws IOException thrown when there is an error when writing to the output stream
     */
    private void wordCountFromFilesHelper(String flag, String fileName, BufferedWriter writer, int[] totalCount) throws IOException {
        String file = getFileContents(fileName);
        Integer count;
        switch (flag) {
            case "-m":
                count = countChars(file);
                writer.write(count.toString() + ' ' + fileName + System.getProperty("line.separator"));
                writer.flush();
                totalCount[0] += count;
                break;
            case "-w":
                count = countWords(file);
                writer.write(count.toString() + ' ' + fileName + System.getProperty("line.separator"));
                writer.flush();
                totalCount[0] += count;
                break;
            case "-l":
                count = countLines(file);
                writer.write(count.toString() + ' ' + fileName + System.getProperty("line.separator"));
                writer.flush();
                totalCount[0] += count;
                break;
            case "-a":
                countAll(file, fileName, writer, totalCount);
                break;
            default:
                throw new RuntimeException("wc: invalid flag " + flag);
        }
    }

    /**
     * this function handles the wc function when the number of chars, words and lines are requested. 
     * Makes sure that the result is outputted in the correct format and that the totals are counted correctly
     * 
     * @param fileContents the contents of the file as a string used to perform wc
     * @param fileName the name of the file currently being used. 
     * @param writer used to write the result to the output stream
     * @param totalCount holds the sum of the count of the number of chars/word/lines in all the files so far
     * @throws IOException thrown when there is an error when writing to the output stream
     */
    private void countAll(String fileContents, String fileName, BufferedWriter writer, int[] totalCount) throws IOException {
        Integer count = countLines(fileContents);
        totalCount[0] += count;
        writer.write(count.toString() + ' ');
        count = countWords(fileContents);
        totalCount[1] += count;
        writer.write(count.toString() + ' ');
        count = countChars(fileContents);
        totalCount[2] += count;
        writer.write(count.toString() + ' ' + fileName + System.getProperty("line.separator"));
        writer.flush();
    }

    /**
     * returns the contents of the file that has been specified as a string
     * throws an exception if the file cannot be read from
     * 
     * @param fileName the name of the file whose contents need to be converted into a string
     * @return the contents of the file as a string
     */
    private String getFileContents(String fileName) {
        Path filePath = Paths.get(Jsh.getCurrentDirectory() + File.separator + fileName);
        try {
            return Files.readString(filePath, Jsh.encoding);
        } catch (IOException e) {
            throw new RuntimeException("wc: cannot open " + fileName);
        }
    }

    /**
     * iterates through all the files specified by the user
     * correctly formats the "total" array which is used to keep track of the count of the number of chars/word/lines in all the files
     * 
     * @param option the option that the user has chosen. Used to make the "total" array
     * @param fileNames holds the list of files requested by the user
     * @param writer used to write the result to the output stream
     * @throws IOException exception is thrown when there is an error writing to the output stream
     */
    private void wordCountFromFiles(String option, ArrayList<String> fileNames, BufferedWriter writer) throws IOException {
        int[] total;
        if (option.equals("-a")) {
            total = new int[]{0, 0, 0};
        } else {
            total = new int[]{0};
        }
        for (String file : fileNames) {
            wordCountFromFilesHelper(option, file, writer, total);
        }
        if (fileNames.size() > 1) {
            printTotal(total, writer);
        }
    }

    /**
     * When using wc with multiple files, writes a final line that shows the total count(s) to the ouput stream
     * 
     * @param total         Contains the 
     * @param writer        Used to write the result to the output stream
     * @throws IOException  Exception is thrown when there is an error writing to the output stream
     */
    private void printTotal(int[] total, BufferedWriter writer) throws IOException {
        for (Integer c : total) {
            writer.write(c.toString() + ' ');
        }
        writer.write("total\n");
        writer.flush();
    }

    /**
     * Returns which option was chosen by the user and if the user explicitly chose an option, removes this from the arraylist of arguments
     * 
     * @param args Holds the arguments given with the wc command by the user
     * @return     A string which tells us which option that the user chose
     */
    private String getOption(ArrayList<String> args) {
        String choice = args.get(0);
        if (!choice.equals("-m") && !choice.equals("-w") && !choice.equals("-l")) {
            return "-a";
        }
        args.remove(0);
        return choice;
    }
}