package stone.starfleet;

import org.junit.Test;
import stone.starfleet.utils.Constants;

import static org.junit.Assert.assertTrue;

/**
 * Created by danielstoneburner on 1/10/16.
 */
public class ConstantsTests {
    @Test
    public void testZOffsetValues() {
        int a = Constants.getZOffsetValue('a');
        assertTrue(a == 1);

        int z = Constants.getZOffsetValue('z');
        assertTrue(z == 26);

        int A = Constants.getZOffsetValue('A');
        assertTrue(A == 27);

        int Z = Constants.getZOffsetValue('Z');
        assertTrue(Z == 52);
    }

    @Test
    public void testZOffsetRepresentations() {
        char a = Constants.getZOffsetRepresentation(1);
        assertTrue(a == 'a');

        char z = Constants.getZOffsetRepresentation(26);
        assertTrue(z == 'z');

        char A = Constants.getZOffsetRepresentation(27);
        assertTrue(A == 'A');

        char Z = Constants.getZOffsetRepresentation(52);
        assertTrue(Z == 'Z');
    }
}
