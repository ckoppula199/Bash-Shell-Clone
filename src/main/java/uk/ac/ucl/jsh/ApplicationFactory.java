package uk.ac.ucl.jsh;

public interface ApplicationFactory {

    /**
     * Based on the name of the app passed to the method it checks to see if an application with a matching
     * name exists. If it does it will create an instance of that class and return it.
     * 
     * @param app name of application to be created
     * @return an instance of the application requested.
     */
    Application createApplication(String app);

}