package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class SedTest extends AbstractJshTest {

    @Test(expected = RuntimeException.class)
    public void sedNoArg() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed", out);
    }

    @Test(expected = RuntimeException.class)
    public void sedMissingFile() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed /s/hello/bye/", out);
    }

    @Test(expected = RuntimeException.class)
    public void sedMissingPattern() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s " + fileName, out);
    }


    @Test(expected = RuntimeException.class)
    public void sedMissingDelimiter() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/Hello/Goodbye " + fileName, out);
    }
    
    @Test(expected = RuntimeException.class)
    public void sedTooManyDelimiters() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/Hello/Goodbye/g/g " + fileName, out);
    }

    @Test(expected = RuntimeException.class)
    public void sedIncorrectEndToken() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/Hello/Goodbye/x " + fileName, out);
    }

    @Test(expected = RuntimeException.class)
    public void sedIncorrectStartToken() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed x/Hello/Goodbye/g " + fileName, out);
    }

    @Test(expected = RuntimeException.class)
    public void sedInvalidFile() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/Hello/Goodbye/ invalid", out);
    }

    @Test(expected = RuntimeException.class)
    public void sedDirectory() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/Hello/Goodbye/ src", out);
    }

    @Test(expected = RuntimeException.class)
    public void sedValidDirectory() throws Exception {
        folder.newFolder("src");
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/Hello/Goodbye/ src", out);
    }

    @Test(expected = RuntimeException.class)
    public void sedTooManyArgs() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/Hello/Goodbye/ " + fileName + " " + fileName, out);
    }

    @Test
    public void sedNoMatches() throws Exception {
        String toWrite = "Hello World";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + System.getProperty("line.separator") + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/GoodAfternoon/GoodEvening/ " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.nextLine(), toWrite);
        assertEquals(scn.nextLine(), toWrite2);
        assertFalse(scn.hasNextLine());
        scn.close();
    }

    @Test
    public void sedReplaceFirst() throws Exception {
        String toWrite = "Hello World";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + System.getProperty("line.separator") + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/o/!/ " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.nextLine(), toWrite.replaceFirst("o", "!"));
        assertEquals(scn.nextLine(), toWrite2.replaceFirst("o", "!"));
        assertFalse(scn.hasNextLine());
        scn.close();
    }

    @Test
    public void sedReplaceAll() throws Exception {
        String toWrite = "Hello World";
        String toWrite2 = "Goodbye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite + System.getProperty("line.separator") + toWrite2);

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("sed s/o/!/g " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.nextLine(), toWrite.replace("o", "!"));
        assertEquals(scn.nextLine(), toWrite2.replace("o", "!"));
        assertFalse(scn.hasNextLine());
        scn.close();
    }

}