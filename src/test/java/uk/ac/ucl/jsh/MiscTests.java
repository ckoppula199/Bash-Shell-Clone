package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import org.junit.Test;

public class MiscTests extends AbstractJshTest {

    @Test
    // all exceptions should be caught
    public void jshMain() throws Exception {
        String toWrite = "Hello World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 
        String[] args = {"hello"};
        Jsh.main(args);
        String[] args2 = {"hello", "world"};
        Jsh.main(args2);
        String[] validFlag = {"-c", "world"};
        Jsh.main(validFlag);
        String[] validInput = {"-c", "echo"};
        Jsh.main(validInput);
    }

    @Test
    public void empty() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval(" ", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertFalse(scn.hasNext());
        scn.close();
    }


    @Test 
    //---------------------- DoubleQuotesWithBackQuotes-------------
    public void doubleBackQuoteTest() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        String filename = "myFile.txt";
        String contents = "hello world";
        createTestFile(filename, contents);
        jshell.eval("echo \"`cat " + filename + "`\"", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("hello",scn.next());
        scn.close();
    }

    @Test
    public void multipleArgsTest() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo b`echo foo`a", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("bfooa",scn.next());
        scn.close();
    }

    //--------------------- !! previous command substitution -----------

    @Test
    public void prevCommandTest() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo hello", out);
        jshell.eval("!!", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("hello", scn.nextLine());
        assertEquals("echo hello", scn.nextLine());
        assertEquals("hello", scn.nextLine());
        scn.close();
    }

    @Test
    public void prevCommandBackquotedTest() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo hello", out);
        jshell.eval("echo `!!`", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("hello", scn.nextLine());
        assertEquals("echo `echo hello`", scn.nextLine());
        assertEquals("hello", scn.nextLine());
        scn.close();
    }

}