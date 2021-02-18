# CSE Project 0: CSE 143 Review

**Learning Goals**
- Refresh CSE 143 concepts and set the expectations for projects in CSE 373.
- Set up IntelliJ and other systems we’ll use in this class (Java, GitLab, Git, Checkstyle).
- Learn how to use JUnit and run unit tests in IntelliJ.

## Programming

**Array Problems**
- Implement a method that returns a String representation of the contents of the input int[]. The String representation of the int[] should begin with “[” and end with “]”. Each pair of integers in the String representation should be separated by a comma and a space.
- Implement a method that returns a new int[] containing the input int[]‘s elements in reverse order. (The method should not modify the input array.)
- Implement a method that rotates the values in the input int[] to the right (i.e., forward in position) by one. Each element moves right by one, except the last element, which moves to the front.

**LinkedIntList Problems**
- Implement a method to reverse its order (may assume it has exactly three elements).
- Implement a method that moves the first element of the input LinkedIntList to the back end of the list. If the list is empty or has just one element, its contents should not be modified.
- Implement a method which takes two LinkedIntLists as input and returns a new list containing all the items of the first followed by all the items of t he second. Don’t modify the input lists; use the new keyword to create new objects instead.

**IntTree Problems**
- Implement a method that returns the sum of the values stored in a binary tree of integers weighted by the depth of each value. You should return the value at the overallRoot, plus 2 times the values stored at the next level of the tree, plus 3 times the values stored at the next level of the tree, and so on. 
- Implement a method that removes the leaf nodes from an IntTree. (Remember that a leaf node is one that has empty left and right subtrees.)
- Implement a method that accepts parameters for an IntTree and minimum and maximum integer values, and removes from the tree any elements that are not within that range (inclusive). For this method, you should assume that your tree is a binary search tree (BST), so its elements are in valid BST order. Your method should maintain the BST ordering property of the tree.

**Map Problems**
- Implement a method that returns true if any string occurs at least 3 times in the input list, and false otherwise. Use a map as auxiliary storage.
- Implement a method that takes as input two maps from Strings to Integers and returns a new map whose contents are the intersection of the two. The intersection of two maps is defined here as the set of keys-value pairs that exist in both maps. If some key K maps to value V in both the first and second map, include it in your result. If K does not exist as a key in both maps, or if K does not map to the same value V in both maps, exclude that pair from your result.

[In depth descriptions](https://courses.cs.washington.edu/courses/cse373/20au/projects/cse143review/programming/)
