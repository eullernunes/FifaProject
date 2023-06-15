import java.io.IOException;
import java.io.InputStream;

class BitInputStream {
    private InputStream input;
    private int currentByte;
    private int numBitsRemaining;

    public BitInputStream(InputStream in) {
        input = in;
        currentByte = 0;
        numBitsRemaining = 0;
    }

    public int readBit() throws IOException {
        if (currentByte == -1) {
            return -1;
        }
        if (numBitsRemaining == 0) {
            currentByte = input.read();
            if (currentByte == -1) {
                return -1;
            }
            numBitsRemaining = 8;
        }
        numBitsRemaining--;
        return (currentByte >> numBitsRemaining) & 1;
    }

    public void close() throws IOException {
        input.close();
    }
}