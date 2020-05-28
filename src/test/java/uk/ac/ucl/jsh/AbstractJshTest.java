package uk.ac.ucl.jsh;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public abstract class AbstractJshTest {

    Jsh jshell= null;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void resetJsh() {
        jshell = new Jsh();
        Jsh.setCurrentDirectory(folder.getRoot().getPath());
    }

    @After
    public void deleteTempFolder() {
        folder.delete();
    }

    protected void createTestFile(String fileName, String toWrite) throws IOException {
        File file = folder.newFile(fileName);
        if (toWrite != null) {
            FileWriter writer = new FileWriter(file, Jsh.encoding);
            writer.write(toWrite);
            writer.close();
        }
    }
}