import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import static org.junit.Assert.*;

public class CardGameTest {

    @Test 
    public void packValiditySize() {
        ArrayList<Integer> tooSmall = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7));
        assertFalse(CardGame.packValidity(tooSmall, 2));
        ArrayList<Integer> tooBig = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,15,43,33,35,3443,3344,3,433,44));
        assertFalse(CardGame.packValidity(tooBig, 2));
        ArrayList<Integer> notEnoughUniques = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16));
        assertFalse(CardGame.packValidity(notEnoughUniques, 17));
        ArrayList<Integer> incorrectFrequency = new ArrayList<Integer>(Arrays.asList(1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2));
        assertFalse(CardGame.packValidity(incorrectFrequency, 2));
        ArrayList<Integer> correct = new ArrayList<Integer>(Arrays.asList(1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2));
        assertTrue(CardGame.packValidity(correct, 2));
    }

   
}
