package uk.ac.ucl.applications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import uk.ac.ucl.jsh.Application;
import uk.ac.ucl.jsh.Jsh;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sed implements Application {

    public void execute(ArrayList<String> commandLineArgs, InputStream input, OutputStream output)throws IOException, RuntimeException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Jsh.encoding));
        int argumentNumber = commandLineArgs.size();
        if(argumentNumber == 1 && input != null || argumentNumber == 2) {
            String[] replacementTokens = getReplacementTokens(commandLineArgs);
            if(argumentNumber == 2) {
                String textFileName = commandLineArgs.get(1);
                performOperation(replacementTokens,textFileName,writer);
                return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, Jsh.encoding));
        replace(replacementTokens, reader, writer);
        } else {
            throw new RuntimeException("sed: " + argumentNumber + " is an invaid number of arguments");
        }

    }

    /**
     * Takes the first argument of sed, typically called the Replacement, for example - s/a/b/g and returns an
     * array of replacement tokens with them split by their delimiter - {s,a,b,g}
     * @param commnandLineArgs
     * @return 
     * @throws RuntimeException
     */
    private String[] getReplacementTokens(ArrayList<String> commandLineArgs) throws RuntimeException{
        String replacement = commandLineArgs.get(0);
        String delimiter = getDemlimiter(replacement);
        String[] replacementTokens = replacement.split(Pattern.quote(delimiter), 4);
        testTokens(replacementTokens);
        return replacementTokens;
    }


    /**
     * If the command was sed s/a/b/g test.txt, this method first checks so see if we can connect to test.txt
     * then replaces all occurences of a in test.txt with b, writing to stdout. 
     * @param replacementTokens
     * @param textFileName
     * @param writer
     * @throws IOException
     * @throws RuntimeException
     */
    private void performOperation(String[]replacementTokens, String textFileName, BufferedWriter writer)throws IOException, RuntimeException{
        String currentDirectory = Jsh.getCurrentDirectory();
        File currFile = new File(currentDirectory + File.separator + textFileName);
        if (!currFile.exists()) {
            throw new RuntimeException("sed: " + textFileName + " does not exist");
        }
        Path filePath = Paths.get(currentDirectory + File.separator + textFileName);
            try (BufferedReader reader = Files.newBufferedReader(filePath, Jsh.encoding)) {
                replace(replacementTokens,reader,writer);
            } catch (IOException e) {
                throw new RuntimeException("sed: cannot open " + textFileName);
            }

    }

    /**
     * For replacementTokens {s,a,b,g}, the method replaces all occurrences of a in the inputstream with b 
     * @param replacementTokens
     * @param reader
     * @param writer
     * @throws IOException
     */
    private void replace(String[]replacementTokens, BufferedReader reader, BufferedWriter writer)throws IOException{
        String line = null;
        while ((line = reader.readLine()) != null) {
            String aLine = String.valueOf(line);
            writeProcessedLine(aLine, writer, replacementTokens);
        }

    }

    private void writeProcessedLine(String aLine, BufferedWriter writer,String[]replacementTokens)throws IOException{
        String processedLine = processLine(replacementTokens, aLine);
        writer.write(processedLine);
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }

    private String processLine(String[]replacementTokens, String aFileLine){
        if("".equals(replacementTokens[3])){
            return aFileLine.replaceFirst(replacementTokens[1], replacementTokens[2]);
        }
        return aFileLine.replaceAll(replacementTokens[1], replacementTokens[2]);
    }


    private void testTokens(String[] replacementTokens)throws RuntimeException{
        int length = replacementTokens.length;
        if(length != 4){
            throw new RuntimeException("sed: " + "invalid number of delimiters in replacement token"); 
        }
        if(!"s".equals(replacementTokens[0])){
            throw new RuntimeException("sed: " + "Invalid replacement token format at start");
        }
        else if(!"g".equals(replacementTokens[3]) && !"".equals(replacementTokens[3])){
            throw new RuntimeException("sed: " + "Invalid replacement token format at end");
        }
    }

    private String getDemlimiter(String replacement){
        if(replacement.length() >= 2){
            return Character.toString(replacement.charAt(1));
        }else{
            throw new RuntimeException("sed: " + "Invalid Replacement");
        }
    }
}