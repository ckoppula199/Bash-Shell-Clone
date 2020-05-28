package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class PipeTest extends AbstractJshTest {

    @Test
    public void pipeEchoCat() throws Exception {
        String arg = "Hello World";
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo " + arg + " | cat", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("Output wrong", arg, scn.nextLine());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void pipeCatCat() throws Exception {
        String toWrite = "Hello World, Goodbye World\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | cat", out);
        out.close();
        String contents = new String(in.readAllBytes());
        assertEquals("Output wrong", toWrite, contents);
    }

    @Test
    public void pipeHeadCat() throws Exception {
        String line1 = "Goodmorning World";
        String line2 = "GoodAfternoon World";
        String line3 = "GoodEvening World";
        String line4 = "Goodbye World";
        String toWrite = line1 + System.getProperty("line.separator") + line2 + System.getProperty("line.separator") + line3 + System.getProperty("line.separator") + line4;
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("head -n 2 " + fileName + " | cat", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("Output wrong", line1 , scn.nextLine());
        assertEquals("Output wrong", line2 , scn.nextLine());
        assertFalse("Extra lines", scn.hasNextLine());
        scn.close();
    }

    @Test
    public void pipeTailCat() throws Exception {
        String line1 = "Goodmorning World";
        String line2 = "GoodAfternoon World";
        String line3 = "GoodEvening World";
        String line4 = "Goodbye World";
        String toWrite = line1 + System.getProperty("line.separator") + line2 + System.getProperty("line.separator") + line3 + System.getProperty("line.separator") + line4;
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("tail -n 2 " + fileName + " | cat", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("Output wrong", line3 , scn.nextLine());
        assertEquals("Output wrong", line4 , scn.nextLine());
        assertFalse("Extra lines", scn.hasNextLine());
        scn.close();
    }

    @Test
    public void pipeCatHead() throws Exception {
        String line1 = "Goodmorning World";
        String line2 = "GoodAfternoon World";
        String line3 = "GoodEvening World";
        String line4 = "Goodbye World";
        String toWrite = line1 + System.getProperty("line.separator") + line2 + System.getProperty("line.separator") + line3 + System.getProperty("line.separator") + line4;
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | head -n 2", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("Output wrong", line1 , scn.nextLine());
        assertEquals("Output wrong", line2 , scn.nextLine());
        assertFalse("Extra lines", scn.hasNextLine());
        scn.close();
    }

    @Test
    public void pipeCatTail() throws Exception {
        String line1 = "Goodmorning World";
        String line2 = "GoodAfternoon World";
        String line3 = "GoodEvening World";
        String line4 = "Goodbye World";
        String toWrite = line1 + System.getProperty("line.separator") + line2 + System.getProperty("line.separator") + line3 + System.getProperty("line.separator") + line4;
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | tail -n 2", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("Output wrong", line3 , scn.nextLine());
        assertEquals("Output wrong", line4 , scn.nextLine());
        assertFalse("Extra lines", scn.hasNextLine());
        scn.close();
    }

    @Test
    public void pipeCatGrep() throws Exception {
        String line1 = "Goodmorning World";
        String line2 = "GoodAfternoon World";
        String line3 = "GoodEvening World";
        String line4 = "Goodbye World";
        String toWrite = line1 + System.getProperty("line.separator") + line2 + System.getProperty("line.separator") + line3 + System.getProperty("line.separator") + line4;
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | grep ing", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("Output wrong", line1 , scn.nextLine());
        assertEquals("Output wrong", line3 , scn.nextLine());
        assertFalse("Extra lines", scn.hasNextLine());
        scn.close();
    }

    @Test
    public void pipeCatSed() throws Exception {
        String line1 = "Goodmorning World";
        String line2 = "GoodAfternoon World";
        String line3 = "GoodEvening World";
        String line4 = "Goodbye World";
        String toWrite = line1 + System.getProperty("line.separator") + line2 + System.getProperty("line.separator") + line3 + System.getProperty("line.separator") + line4;
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | sed s/Good/Bad/", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("Output wrong", line1.replace("Good", "Bad") , scn.nextLine());
        assertEquals("Output wrong", line2.replace("Good", "Bad") , scn.nextLine());
        assertEquals("Output wrong", line3.replace("Good", "Bad") , scn.nextLine());
        assertEquals("Output wrong", line4.replace("Good", "Bad") , scn.nextLine());
        assertFalse("Extra lines", scn.hasNextLine());
        scn.close();
    }

    @Test
    public void pipeCatWcWC() throws Exception {
        String toWrite = "1 lines 6 words 25 chars\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " " + fileName + " | wc -w", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("12", scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void pipeCatWcLC() throws Exception {
        String toWrite = "1 lines 6 words 25 chars\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " " + fileName + " | wc -l", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("2", scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void pipeCatWcCC() throws Exception {
        String toWrite = "1 lines 6 words 25 chars\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " " + fileName + " | wc -m", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("50", scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void pipeCatWc() throws Exception {
        String toWrite = "1 lines 6 words 25 chars\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " " + fileName + " | wc ", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("2", scn.next());
        assertEquals("12", scn.next());
        assertEquals("50", scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void doublePipe() throws Exception {
        String toWrite = "1 lines 6 words 25 chars\n";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | sed s/o/oo/g | wc ", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("1", scn.next());
        assertEquals("6", scn.next());
        assertEquals("26", scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test(expected = RuntimeException.class)
    public void pipeTailWrongArgs() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, "");
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | tail arg1 arg2", out);
    }

    @Test
    public void pipeTailCorrectArgs() throws Exception {
        String fileName = "testTextFile.txt";
        String toWrite = "Hello";
        createTestFile(fileName, toWrite);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("cat " + fileName + " | tail", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(toWrite, scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void pipeMissingSecondHalf() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo hello | ", out);
    }
}