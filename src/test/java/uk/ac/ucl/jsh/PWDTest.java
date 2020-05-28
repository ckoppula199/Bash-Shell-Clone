package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class PWDTest extends AbstractJshTest {

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
    public void pwdTestDefaultDirectory() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("pwd", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("pwd printed wrong directory", folder.getRoot().getPath(), scn.next());
        assertFalse("extra lines printed", scn.hasNext());
        scn.close();
    }

    @Test
    public void pwdTestAfterChangedDirectory() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        String folderName = "testFolder";
        File testFolder = folder.newFolder(folderName);
        jshell.eval("cd " + folderName, out);
        jshell.eval("pwd", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals("printed wrong directory after cd", scn.next(), testFolder.getPath());
        assertFalse("extra lines printed", scn.hasNext());
        scn.close();
    }
}