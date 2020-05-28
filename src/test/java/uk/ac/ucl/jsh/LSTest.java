package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class LSTest extends AbstractJshTest {


    @Test(expected = RuntimeException.class)
    public void lsTestTooManyArgs() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("ls arg1 arg2", out);
    }

    @Test(expected = RuntimeException.class)
    public void lsTestDirectoryDoesNotExist() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("ls Invalid", out);
    }

    @Test(expected = RuntimeException.class)
    public void lsTestDirectoryIsFile() throws Exception {
        String fileName = "testTextFile.txt";
        createTestFile(fileName, null);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("ls " + fileName, out);
    }

    @Test
    public void lsTestNoArgs() throws Exception {
        String folder1 = "Folder1";
        String folder2 = "Folder2";
        String file1 = "file1.txt";
        String file2 = "file2.txt";

        folder.newFolder(folder1);
        folder.newFolder(folder2);
        folder.newFolder(".hidden");
        folder.newFile(file1);
        folder.newFile(file2);
        folder.newFile(".hiddenTxt.txt");

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("ls", out);
        Scanner scn = new Scanner(in);
        out.close();
        String ls = scn.nextLine();
        assertTrue("ls printed does not print '" + folder1 + "' (no args given)", ls.contains(folder1));
        ls = ls.replaceFirst(folder1, "");
        assertTrue("ls printed does not print '" + folder2 + "' (no args given)", ls.contains(folder2));
        ls = ls.replaceFirst(folder2, "");
        assertTrue("ls printed does not print '" + file1 + "' (no args given)", ls.contains(file1));
        ls = ls.replaceFirst(file1, "");
        assertTrue("ls printed does not print '" + file2 + "' (no args given)", ls.contains(file2));
        ls = ls.replaceFirst(file2, "").replace("\t","");
        assertEquals("ls contains extra characters", ls, "");
        scn.close();
    }

    @Test
    public void lsTestOneArg() throws Exception {
        String folderName = "Folder1";
        String file1Name = "File1.txt";
        String file2Name = "File2.txt";
        File newFolder = folder.newFolder(folderName);
        File file1 = new File(newFolder, file1Name);
        File file2 = new File(newFolder, file2Name);
        file1.createNewFile();
        file2.createNewFile();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("ls " + folderName, out);
        Scanner scn = new Scanner(in);
        out.close();
        String ls = scn.nextLine();
        assertTrue("ls printed does not print '" + file1Name + "' (no args given)", ls.contains(file1Name));
        ls = ls.replaceFirst(file1Name, "");
        assertTrue("ls printed does not print '" + file2Name + "' (no args given)", ls.contains(file2Name));
        ls = ls.replaceFirst(file2Name, "").replace("\t","");;
        assertEquals("ls contains extra characters (no args given)", ls, "");
        scn.close();
    }

    @Test
    public void lsEmpty() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jshell.eval("ls", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertFalse(scn.hasNext());
        scn.close();
    }

}