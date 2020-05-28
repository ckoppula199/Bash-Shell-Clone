package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;


import org.junit.Test;

public class SeqTest extends AbstractJshTest {
    
    @Test
    public void seqEmptySecondCommand() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        String arg = "hello";
        jshell.eval("echo " + arg + " ;", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(arg, scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

    @Test
    public void seqBasicTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        String arg1 = "hello";
        String arg2 = "world";
        jshell.eval("echo " + arg1 + " ; echo " + arg2, out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(arg1, scn.next());
        assertEquals(arg2, scn.next());
        assertFalse(scn.hasNext());
        scn.close();
    }

}