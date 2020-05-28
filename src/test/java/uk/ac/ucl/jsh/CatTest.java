package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public class CatTest extends AbstractJshTest {

  @Test(expected = RuntimeException.class)
  public void catTestNoArg() throws Exception {
      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat", out);
  }

  @Test(expected = RuntimeException.class)
  public void catTestInvalidFile() throws Exception {
      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat Invalid", out);
  }

  @Test(expected = RuntimeException.class)
  public void catDirectory() throws Exception {
      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat src", out);
  }

  @Test(expected = RuntimeException.class)
  public void catValidDirectory() throws Exception {
      String folderName = "src";
      folder.newFolder(folderName);
      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat " + folderName, out);
  }

  @Test(expected = RuntimeException.class)
  public void catSecondArgInvalid() throws Exception {
      String fileName = "testTextFile.txt";
      createTestFile(fileName, null);
      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat " + fileName + " Invalid", out);
  }

  @Test
  public void catEmptyFile() throws Exception {
      String fileName = "testTextFile.txt";
      createTestFile(fileName, null);
      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat " + fileName, out);
      out.close();
      String contents = new String(in.readAllBytes());
      assertEquals("file not empty", "", contents);
  }

  @Test
  public void catOneFile() throws Exception {
      String toWrite = "Hello World\nBye World";
      String fileName = "testTextFile.txt";
      createTestFile(fileName, toWrite); 

      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat " + fileName, out);
      out.close();
      String contents = new String(in.readAllBytes());
      assertEquals("File contents wrong", toWrite, contents);
  }

  @Test
  public void catExtraSpaceAndNewlines() throws Exception {
      String toWrite = "Hello World    \nBye World\n\n\n";
      String fileName = "testTextFile.txt";
      createTestFile(fileName, toWrite); 

      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat " + fileName, out);
      out.close();
      String contents = new String(in.readAllBytes());
      assertEquals("File contents wrong", toWrite, contents);
  }

  @Test
  public void catMultipleFiles() throws Exception {
      String toWrite = "Hello World    \nBye World";
      String fileName = "testTextFile.txt";
      createTestFile(fileName, toWrite);
      PipedInputStream in = new PipedInputStream();
      PipedOutputStream out = new PipedOutputStream(in);
      jshell.eval("cat " + fileName + " " + fileName + " " + fileName, out);
      out.close();
      String contents = new String(in.readAllBytes());
      assertEquals("File contents wrong", toWrite + toWrite + toWrite, contents);
    }

}