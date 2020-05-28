package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class WCTest extends AbstractJshTest {

    @Test(expected = RuntimeException.class)
    public void wcNoArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc", out);
    }

    @Test(expected = RuntimeException.class)
    public void wcMissingFile() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -l", out);
    }

    @Test(expected = RuntimeException.class)
    public void wcInvalidOption() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -x " + fileName, out);
    }

    @Test(expected = RuntimeException.class)
    public void wcInvalidFile() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc invalid.txt", out);
    }

    @Test
    public void wcWordCountCheck() throws Exception {
        String toWrite = "\n\nHello World\nThere    are 9 words in this file\n\n\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -w " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.next(), "9");
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void wcWordCountCheck2() throws Exception {
        String toWrite = " Hello World\nThere    are 9 words in this file\n\n ";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -w " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.next(), "9");
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void wcWordCountCheck3() throws Exception {
        String toWrite = " Hello World\nThere    are 9 words in this file";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -w " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.next(), "9");
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void wcWordCountCheck4() throws Exception {
        String toWrite = " \n\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -w " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.next(), "0");
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }


    @Test
    public void wcCharCountCheck() throws Exception {
        String toWrite = "\n\nHello World\nThere   are 50 chars in this file\n\n\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -m " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.next(), "50");
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void wcLineCountCheck() throws Exception {
        String toWrite = "\n\nHello World\nThere   are 6 newline chars in this file\n\n\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -l " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.next(), "6");
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void wcNoOptionCheck() throws Exception {
        String toWrite = "1 lines 6 words 25 chars\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("1", scn.next());
        assertEquals("6", scn.next());
        assertEquals("25", scn.next());
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void wcOneWord() throws Exception {
        String toWrite = "\n    \n\n  BOO!";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc -w " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("1", scn.next());
        assertEquals(scn.next(), "testTextFile.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void wcMultiFiles() throws Exception {
        String toWrite = "1 lines 6 words 25 chars\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("wc " + fileName + " " + fileName + " " + fileName, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("1", scn.next());
        assertEquals("6", scn.next());
        assertEquals("25", scn.next());
        assertEquals(scn.next(), "testTextFile.txt");
        assertEquals("1", scn.next());
        assertEquals("6", scn.next());
        assertEquals("25", scn.next());
        assertEquals(scn.next(), "testTextFile.txt");
        assertEquals("1", scn.next());
        assertEquals("6", scn.next());
        assertEquals("25", scn.next());
        assertEquals(scn.next(), "testTextFile.txt");
        assertEquals("3", scn.next());
        assertEquals("18", scn.next());
        assertEquals("75", scn.next());
        assertEquals(scn.next(), "total");
        assertFalse(scn.hasNext());
        scn.close();
    }
}