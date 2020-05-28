package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public class GlobbingTest extends AbstractJshTest {

    @Test
    public void globbingHidden() throws IOException {
    PipedInputStream in = new PipedInputStream();
    PipedOutputStream out = new PipedOutputStream(in);
    folder.newFile(".hidden");
    folder.newFile("notHidden");
    String actualResult = "notHidden" + System.getProperty("line.separator");
    jshell.eval("echo *", out);
    out.close();
    String contents = new String(in.readAllBytes());
    assertEquals(actualResult, contents);
    
    }

    @Test
    public void globWithDirectories() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        String folderName = "testFolder";
        File testFolder = folder.newFolder(folderName);
        File file1 = new File(testFolder.getAbsolutePath() + System.getProperty("file.separator") + "file1.txt");
        file1.createNewFile();
        String actualResult = "testFolder/file1.txt" + System.getProperty("line.separator");
        jshell.eval("echo " + folderName + "/*" , out);
        out.close();
        String contents = new String(in.readAllBytes());
        assertEquals(actualResult, contents);
    }
}