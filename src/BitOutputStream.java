import java.io.IOException;
import java.io.OutputStream;

class BitOutputStream {
    private OutputStream output;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream out) {
        output = out;
        currentByte = 0;
        numBitsFilled = 0;
    }

    public void writeBit(int bit) throws IOException {
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;

        if (numBitsFilled == 8) {
            output.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    public void flush() throws IOException {
        while (numBitsFilled != 0) {
            writeBit(0);
        }
    }

    public void close() throws IOException {
        flush();
        output.close();
    }
}
