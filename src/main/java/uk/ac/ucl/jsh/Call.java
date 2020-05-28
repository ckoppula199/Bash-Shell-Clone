package uk.ac.ucl.jsh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* A Call object is expandable if the constructor used to create it takes a list as its only argument, resulting
* from globbing or command substiution.
*/

public class Call implements Command {

    private String command;
    private String argument;
    private List<String> commandLineArgs;
    private String inputFileName;
    private String outputFileName;
    private boolean expandableArgs = false;

    /**
     * This constructor is called when creating the Call object representing the application
     * 
     * @param commandLineArgs Arguments being passed to the application
     * @param inputFileName   Name of the file for input redirection
     * @param outputFileName  Name of the file for output redirection
     */
    public Call(ArrayList<String> commandLineArgs, String inputFileName, String outputFileName) {
        this.command = commandLineArgs.get(0);
        commandLineArgs.remove(0);
        this.commandLineArgs = commandLineArgs;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    /**
     * Called when a command contains single quotes or double quotes (not containing backquotes); these commands
     * are not expandable (cannot add to the number of commandline arguments)
     * @param argument
     */
    public Call(String argument) {
        this.argument = argument;
    }
    /**
     * Called when a command contains a subcommand or globbing as these could be expanded to give more arguments.
     * @param commandLineArgs
     */
    public Call(List<String> commandLineArgs) {
        this.expandableArgs = true;
        this.commandLineArgs = commandLineArgs;
    }

    @Override
    public void accept(CommandVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    public String getArgument() {
        return this.argument;
    }

    public ArrayList<String> getCommandLineArgs() {
        return new ArrayList<>(commandLineArgs);
    }

    public String getCommand() {
        return command;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public boolean isExpandable(){
        return this.expandableArgs;
    }
}