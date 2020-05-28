package uk.ac.ucl.jsh;

import static org.junit.Assert.assertTrue;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Date;
import java.util.Scanner;

import org.junit.Test;

public class DateTest extends AbstractJshTest {
    
    @Test
    public void date() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        Date dateBefore = new Date(System.currentTimeMillis());
        jshell.eval("date", out);
        Date dateAfter = new Date(System.currentTimeMillis());
        Scanner scn = new Scanner(in);
        out.close();
        String actualTime = scn.nextLine();
        boolean check = actualTime.equals(dateBefore.toString()) || actualTime.equals(dateAfter.toString());
        assertTrue("Date not current", check);
        scn.close();
    }

}