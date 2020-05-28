package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class FindTest extends AbstractJshTest {


    @Test(expected = RuntimeException.class)
    public void findNoArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find", out);
    }

    @Test(expected = RuntimeException.class)
    public void findMissingArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find -name", out);
    }

    @Test(expected = RuntimeException.class)
    public void findMissingArgs2() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find src *.txt", out);
    }

    @Test(expected = RuntimeException.class)
    public void findInvalidDirectory() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find invalid -name *.txt", out);
    }

    @Test(expected = RuntimeException.class)
    public void findFileInsteadOfDirectory() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find " + fileName + " -name *.txt", out);
    }

    @Test(expected = RuntimeException.class)
    public void findFileInvalidFlag() throws Exception {
        String dirName = "sub1";
        folder.newFolder(dirName, "sub1-1", "sub1-1-1");
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find " + dirName + " -x *.txt", out);
    }

    @Test
    public void findFileNoMatches() throws Exception {
        createTestFile("test.txt", null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find -name notMatching.txt", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertFalse(scn.hasNext());
        scn.close();
    }
    

    @Test
    public void findTxtFiles() throws Exception {
        createTestFile("test.txt", null);
        folder.newFolder("sub1", "sub1-1", "sub1-1-1");
        folder.newFolder("sub2", "sub2-1");
        folder.newFolder("sub1", "sub1-2");

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("find -name *.txt", out);
        Scanner scn = new Scanner(in);
        out.close();
        assertEquals(scn.next(), "./test.txt");
        assertFalse(scn.hasNext());
        scn.close();
    }
}