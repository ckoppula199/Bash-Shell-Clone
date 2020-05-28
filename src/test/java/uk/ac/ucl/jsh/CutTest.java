package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class CutTest extends AbstractJshTest {
    @Test(expected = RuntimeException.class)
    public void cutTestWrongNumberOfArgs() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut -d t " + fileName + " -f 1 extra", out);
    }

    @Test(expected = RuntimeException.class)
    public void cutTestWrongFirstArg() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut wrong t " + fileName + " -f 1", out);
    }

    @Test(expected = RuntimeException.class)
    public void cutTestWrongSecondArg() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut -d multchar " + fileName + " -f 1", out);
    }

    @Test(expected = RuntimeException.class)
    public void cutTestWrongThirdArg() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut -d t notAFile -f 1", out);
    }

    @Test(expected = RuntimeException.class)
    public void cutTestWrongFourthArg() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut -d t " + fileName + " wrong 1", out);
    }

    @Test(expected = RuntimeException.class)
    public void cutTestWrongFifthArg() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut -d t " + fileName + " -f 1.2", out);
    }

    @Test(expected = RuntimeException.class)
    public void cutTestIndexOutOfBounds() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut -d t " + fileName + " -f 30", out);
    }

    @Test
    public void validCutTest() throws Exception {
        String toWrite = "this is a test file to test with";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cut -d t " + fileName + " -f 2", out);
        jshell.eval("cut -d t " + fileName + " -f 3", out);
        jshell.eval("cut -d t " + fileName + " -f 4", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("his is a ",scn.nextLine());
        assertEquals("es",scn.nextLine());
        assertEquals(" file ", scn.nextLine());
        scn.close();
    }
}