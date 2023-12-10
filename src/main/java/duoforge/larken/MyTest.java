package duoforge.larken;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class MyTest {
    @Test
    public void testCorruptFile() {
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader("results.txt"));
            BufferedReader reader2 = new BufferedReader(new FileReader("results.txt"));
            // Read lines from the file
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            // Assert that the read lines are not null
            assertNotNull(line1, "First line is null");
            assertNotNull(line2, "Second line is null");
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }
}