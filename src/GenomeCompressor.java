/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {
    public final static int BITS_PER_CHAR = 2;
    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        int[] letterMap = new int['T' + 1];
        letterMap['A'] = 0;
        letterMap['C'] = 1;
        letterMap['G'] = 2;
        letterMap['T'] = 3;
        // TODO: complete the compress() method
        String genome = BinaryStdIn.readString();
        int length = genome.length();
        for (int i = 0; i < length; i++) {
            int num = letterMap[genome.charAt(i)];
            BinaryStdOut.write(num, BITS_PER_CHAR);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        char[] letterMap = new char[4];
        letterMap[0] = 'A';
        letterMap[1] = 'C';
        letterMap[2] = 'G';
        letterMap[3] = 'T';
        while (!BinaryStdIn.isEmpty()) {
            int num = BinaryStdIn.readInt(BITS_PER_CHAR);
            char letter = letterMap[num];
            BinaryStdOut.write(letter);
        }
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}