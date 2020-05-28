package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class WhoamiTest extends AbstractJshTest {

    @Test
    public void whoami() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("whoami", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("whoami not correct", System.getProperty("user.name"), scn.nextLine());
        scn.close();
    }
}