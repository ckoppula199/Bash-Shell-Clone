package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;


import org.junit.Test;

public class GrepTest extends AbstractJshTest {
    
    @Test(expected = RuntimeException.class)
    public void grepNoArg() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("grep", out);
    }
    

    @Test(expected = RuntimeException.class)
    public void grepNoFile() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("grep Invalid", out);
    }

    @Test(expected = RuntimeException.class)
    public void grepDirectory() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("grep bye src", out);
    }

    @Test
    public void grepNoMatches() throws Exception {
        String toWrite = "Hello World\n";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("grep hello " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertFalse(scn.hasNextLine());
        scn.close();
    }

    @Test
    public void grepOneMatch() throws Exception {
        String toWrite = "Hello World\n";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("grep by " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.nextLine(), toWrite2);
        assertFalse(scn.hasNextLine());
        scn.close();
    }

    @Test
    public void grepMatchAnything() throws Exception {
        String toWrite = "Hello World";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + System.getProperty("line.separator") + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("grep . " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.nextLine(), toWrite);
        assertEquals(scn.nextLine(), toWrite2);
        assertFalse(scn.hasNextLine());
        scn.close();
    }
}