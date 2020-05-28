package uk.ac.ucl.jsh;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public class CDTest extends AbstractJshTest {

    @Test(expected = RuntimeException.class)
    public void cdNoArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cd", out);
    }

    @Test(expected = RuntimeException.class)
    public void cdTooManyArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cd arg1 arg2", out);
    }

    @Test(expected = RuntimeException.class)
    public void cdDirectoryNotExists() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cd unexistent", out);
    }

    @Test(expected = RuntimeException.class)
    public void cdToFileNotDirectory() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cd " + fileName, out);
    }

    @Test
    //given a valid directory cd should not produce any error
    public void cdValidInput() throws Exception {
        String folderName = "testFolder";
        folder.newFolder(folderName);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cd " + folderName, out);
    }

}