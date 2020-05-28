package uk.ac.ucl.jsh;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public class MkdirRmdirTest extends AbstractJshTest {

    @Test(expected = RuntimeException.class)
    public void mkdirNoArg() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("mkdir", out);
    }

    @Test(expected = RuntimeException.class)
    public void mkdirExistingDir() throws Exception {
        folder.newFolder("src");
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("mkdir src", out);
    }

    @Test(expected = RuntimeException.class)
    public void mkdirExistingFile() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("mkdir " + fileName, out);
    }

     @Test(expected = RuntimeException.class)
     public void rmdirNoArg() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir", out);
     }
     
     @Test(expected = RuntimeException.class)
     public void rmdirInvalid() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir invalid", out);
     }
 
     @Test(expected = RuntimeException.class)
     public void rmdirInvalidWithFlag() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir -r invalid", out);
     }
 
     @Test(expected = RuntimeException.class)
     public void rmdirInvalidFlag() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir -x invalid", out);
     }
     
     @Test(expected = RuntimeException.class)
     public void rmdirTxtFile() throws Exception {
         String fileName = "testTextFile.txt";
         createTestFile(fileName, null);
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir " + fileName, out);
     }
 
     @Test(expected = RuntimeException.class)
     public void rmdirDirNotExist() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir sauce", out);
     }
 
     @Test(expected = RuntimeException.class)
     public void rmdirDirNotExistWithFlag() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir -r sauce", out);
     }
 
     @Test(expected = RuntimeException.class)
     public void rmdirRmNonEmptyInvalid() throws Exception {
         String folderName = "src";
         folder.newFolder(folderName, "mayonnaise");
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jshell.eval("rmdir " + folderName, out);
     }

     @Test
    public void mkdirANDrmdir() throws Exception {
        String folderName = "src";
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("mkdir " + folderName, out);
        File dir = new File(Jsh.getCurrentDirectory() + System.getProperty("file.separator") + folderName);
        assertTrue("dir not made", dir.exists());
        jshell.eval("rmdir -r " + folderName, out);
        assertFalse("dir not deleted", dir.exists());
    }

    @Test
    public void mkdirANDrmdirNoOption() throws Exception {
        String folderName = "src";
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("mkdir " + folderName, out);
        File dir = new File(Jsh.getCurrentDirectory() + System.getProperty("file.separator") + folderName);
        assertTrue("dir not made", dir.exists());
        jshell.eval("rmdir " + folderName, out);
        assertFalse("dir not deleted", dir.exists());
    }

    @Test
    public void rmdirRmNonEmpty() throws Exception {
        String folderName = "src";
        File newFolder1 = folder.newFolder(folderName);
        File newFolder2 = folder.newFolder(folderName, "textFiles");
        File file1 = new File(newFolder2, "test.txt");
        file1.createNewFile();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("rmdir -r " + folderName, out);
        assertFalse("entire dir not deleted", file1.exists());
        assertFalse("entire dir not deleted", newFolder2.exists());
        assertFalse("entire dir not deleted", newFolder1.exists());
    }
}