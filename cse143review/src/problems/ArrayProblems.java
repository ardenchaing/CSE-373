package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        String toReturn = "[";
        if (array.length != 0) {
            toReturn += array[0];
        }
        for (int i = 1; i < array.length; i++) {
            toReturn += ", " + array[i];
        }
        return toReturn + "]";
    }

    public static int[] insertionSort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int value = data[i];
            int j = i -1;
            while (j >= 0 && data[j] > value) {
                data[j+1] = data[j];
                j = j-1;
            }
            data[j+1] = value;
        }
        return data;
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] toReturn = new int[array.length];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = array[toReturn.length - 1 - i];
        }
        return toReturn;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array.length > 1) {
            int temp = array[array.length - 1];
            for (int i = array.length - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = temp;
        }
    }
}
