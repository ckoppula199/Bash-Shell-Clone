package uk.ac.ucl.jsh;

import uk.ac.ucl.applications.*;

public class StandardApplicationFactory implements ApplicationFactory {
    private String initialAppName = null;

    @Override
    public Application createApplication(String app) {
        this.initialAppName = app;
        if(app.startsWith("_")){
            return new UnsafeDecorator(giveSafeApp(app.substring(1)));
        }
        return giveSafeApp(app);
    }

    private Application giveSafeApp(String safeApp){
        switch(safeApp){
           
            case "echo": return new Echo();
    
            case "cd"  : return new CD();
    
            case "ls"  : return new LS();
    
            case "grep"  : return new Grep();
    
            case "cat"  : return new Cat();
    
            case "tail"  : return new Tail();
    
            case "head"  : return new Head();
    
            case "pwd"  : return new Pwd();
    
            case "find" : return new Find();
    
            case "sed"  : return new Sed();
            
            case "wc"   : return new WordCount();
    
            case "mkdir" : return new Mkdir();
    
            case "date" : return new Datetime();
    
            case "whoami" : return new Whoami();
    
            case "rmdir" : return new Rmdir();

            case "cut" : return new Cut();
    
            default    : throw new RuntimeException("Invalid Application name " + this.initialAppName); 
        }
    }
}