package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class HeadTailTest extends AbstractJshTest {

    @Test(expected = RuntimeException.class)
    public void headTestNoArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head", out);
    }

    @Test(expected = RuntimeException.class)
    public void tailTestNoArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail", out);
    }

    @Test(expected = RuntimeException.class)
    public void headTestWrongNumOfArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head arg1 arg2", out);
    }

    @Test(expected = RuntimeException.class)
    public void tailTestWrongNumOfArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail arg1 arg2", out);
    }

    @Test(expected = RuntimeException.class)
    public void tailInvalidOption() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail -x arg1 arg2", out);
    }

    @Test(expected = RuntimeException.class)
    public void tailTooManyArg() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail -x arg1 arg2 arg3 arg4", out);
    }

    @Test(expected = RuntimeException.class)
    public void tailInvalidNumber() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail -n Ten arg1", out);
    }

    @Test(expected = RuntimeException.class)
    public void headTestFileDoesNotExist() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head -n 2 unexistentFile.txt", out);
    }

    @Test(expected = RuntimeException.class)
    public void headTestDir() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head -n 2 src", out);
    }

    @Test(expected = RuntimeException.class)
    public void headTestValidDir() throws Exception {
        folder.newFolder("src");
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head -n 2 src", out);
    }

    @Test(expected = RuntimeException.class)
    public void tailTestFileDoesNotExist() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head -n 2 unexistentFile.txt", out);
    }

    @Test
    public void headTestValidWithArgs() throws Exception {
        String toWrite = "Hello World\n";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head -n 2 " + fileName, out);
        Scanner scn = new Scanner(in);
        assertEquals(scn.nextLine(), toWrite.substring(0, toWrite.length()-1));
        assertEquals(scn.nextLine(), toWrite2);
        scn.close();
    }

    @Test
    public void tailTestValidWithArgs() throws Exception {
        String toWrite = "Hello World\n";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail -n 1 " + fileName, out);
        Scanner scn = new Scanner(in);
        assertEquals(scn.nextLine(), toWrite2);
        scn.close();
    }

    @Test
    // Should not throw an error
    public void headLessLinesInFileThanArg() throws Exception {
        String toWrite = "Hello World\n";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head " + fileName, out);
        Scanner scn = new Scanner(in);
        assertEquals(scn.nextLine(), toWrite.substring(0, toWrite.length()-1));
        assertEquals(scn.nextLine(), toWrite2);
        scn.close();
    }

    @Test
    // Should not throw error
    public void tailLessLinesInFileThanArg() throws Exception {
        String toWrite = "Hello World\n";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail " + fileName, out);
        Scanner scn = new Scanner(in);
        assertEquals(scn.nextLine(), toWrite.substring(0, toWrite.length()-1));
        scn.close();
    }

}