package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public class UnsafeDecTest extends AbstractJshTest {


    @Test
    // invalid command should not produce an exception
    public void unsafeDecoratorError() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("_cat", out);
    }

    @Test
    // valid command should work as usual
    public void unsafeDecoratorValid() throws Exception {
        String toWrite = "Hello World\nBye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("_cat " + fileName, out);
        out.close();
        String contents = new String(in.readAllBytes());
        assertEquals("File contents wrong", toWrite, contents);
    }

    @Test
    // invalid commands should not produce an exception
    public void unsafeDecoratorAllCommands() throws Exception {
        String toWrite = "Hello World\nBye World";
        String fileName = "testTextFile.txt";
        createTestFile(fileName, toWrite); 

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("_cat " + fileName, out);
        jshell.eval("_echo " + fileName, out);
        jshell.eval("_cd invalid " + fileName, out);
        jshell.eval("_ls invalid " + fileName, out);
        jshell.eval("_grep invalid " + fileName, out);
        jshell.eval("_tail invalid " + fileName, out);
        jshell.eval("_head invalid " + fileName, out);
        jshell.eval("_pwd invalid " + fileName, out);
        jshell.eval("_find invalid " + fileName, out);
        jshell.eval("_sed invalid " + fileName, out);
        jshell.eval("_wc invalid " + fileName, out);
        jshell.eval("_mkdir " + fileName, out);
        jshell.eval("_date invalid " + fileName, out);
        jshell.eval("_whoami invalid " + fileName, out);
        jshell.eval("_rmdir invalid " + fileName, out);
        out.close();
    }
}