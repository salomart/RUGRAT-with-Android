package edu.uta.cse.TestProggen.util;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.uta.cse.proggen.util.ProgGenUtil;

class ProgGenUtilTest {

	@Test
	void testGetInheritanceList() {
		// Case 1: chain, depth, and classes aren't positive integers
		int noOfInheritanceChains = 0;
		int minInheritanceDepth = 0;
		int noOfClasses = 0;
		ArrayList<ArrayList<Integer>> list = ProgGenUtil.getInheritanceList(noOfInheritanceChains, minInheritanceDepth, noOfClasses);
		
		// Should return an empty array
		assertTrue(list.size() == 0);
		
		// Case 2: chain, depth, and classes are valid values
		noOfInheritanceChains = 1;
		minInheritanceDepth = 1;
		noOfClasses = 5;
		list = ProgGenUtil.getInheritanceList(noOfInheritanceChains, minInheritanceDepth, noOfClasses);
		
		assertTrue(list.size() > 0);
		
		// Case 3: classes is smaller than chain * depth
		noOfInheritanceChains = 1;
		minInheritanceDepth = 2;
		noOfClasses = 1;
		list = ProgGenUtil.getInheritanceList(noOfInheritanceChains, minInheritanceDepth, noOfClasses);
		
		assertNull(list);
	}
}
