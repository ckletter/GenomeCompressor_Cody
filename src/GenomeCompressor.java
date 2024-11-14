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
    public final static int A_BIT = 0;
    public final static int C_BIT = 1;
    public final static int G_BIT = 2;
    public final static int T_BIT = 3;
    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // Create int array to map each char to corresponding 2-bit value
        int[] letterMap = new int['T' + 1];
        letterMap['A'] = A_BIT;
        letterMap['C'] = C_BIT;
        letterMap['G'] = G_BIT;
        letterMap['T'] = T_BIT;
        // Read DNA sequence
        String genome = BinaryStdIn.readString();
        int length = genome.length();
        // Add file header to binary file
        BinaryStdOut.write(length * 2);
        // Loop through each nucleotide
        for (int i = 0; i < length; i++) {
            // Find the number corresponding to the current nucleotide to the binary file
            int num = letterMap[genome.charAt(i)];
            // Write the binary value of that number to the binary file
            BinaryStdOut.write(num, BITS_PER_CHAR);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        // Create new char array to map each number 0-3 to corresponding nucleotide
        char[] letterMap = new char[4];
        letterMap[0] = 'A';
        letterMap[1] = 'C';
        letterMap[2] = 'G';
        letterMap[3] = 'T';
        // Read file header, size of data
        int length = BinaryStdIn.readInt();
        int i = 0;
        // Keep looping until we read entire contents of our own data
        while (i < length) {
            // Read the next two bits as an int
            int num = BinaryStdIn.readInt(BITS_PER_CHAR);
            // Find the corresponding nucleotide for that bit
            char letter = letterMap[num];
            // Write out that nucleotide to our expanded file
            BinaryStdOut.write(letter);
            i+=2;
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