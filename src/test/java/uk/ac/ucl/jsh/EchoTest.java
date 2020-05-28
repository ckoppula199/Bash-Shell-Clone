package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class EchoTest extends AbstractJshTest {

    @Test
    public void emptyEcho() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void basicEchoTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo foo", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("foo",scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void EchoSingleQuoteTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo \'  foo  \'", out);
        Scanner scn = new Scanner(in);
        assertEquals("  foo  ", scn.nextLine());
        scn.close();
    }

    @Test
    public void EchoDoubleQuoteTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo \"  foo  \"", out);
        Scanner scn = new Scanner(in);
        assertEquals("  foo  ", scn.nextLine());
        scn.close();
    }

    @Test
    public void EchoBackQuoteTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo `echo foo`", out);
        Scanner scn = new Scanner(in);
        assertEquals("foo", scn.nextLine());
        scn.close();
    }

    @Test(expected = RuntimeException.class)
    public void EchoBackQuoteTest2() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo `ee`", out);
        Scanner scn = new Scanner(in);
        assertEquals("ee", scn.nextLine());
        scn.close();
    }


    @Test(expected = RuntimeException.class)
    public void EchoInvalidBackQuoteTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("echo `cat invalid`", out);
    }

    @Test
    public void EchoMultipleArgs() throws Exception {
        String arg1 = "Hello";
        String arg2 = "World";
        String arg3 = "Goodbye";
        String arg4 = "World";
        String combinedArg = "\"" + arg1 + "\" "+ arg2 + " " + arg3 + " \'" + arg4 + "\'";

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval(("echo " + combinedArg), out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(arg1 + " " + arg2 + " " + arg3 + " " + arg4, scn.nextLine());
        scn.close();
    }

}