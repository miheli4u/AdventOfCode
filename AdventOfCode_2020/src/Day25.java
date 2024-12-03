public class Day25 {

    public static int calcEncryptionKey (int publicKey1, int publicKey2) {

        int subjectNum = 7;
        int divisor = 20201227;
        double value1 = 1;
        int loopSizeCounter = 0;

        // trial and error to find the loopSize of one device
        while (value1 != publicKey1) {
            value1 = value1 * subjectNum;
            value1 = value1 % divisor;
            loopSizeCounter++;
        }

        // transform the public key of the other device to get the encryption key
        value1 = 1;
        for (int i = 0; i < loopSizeCounter; i++) {
            value1 = value1 * publicKey2;
            value1 = value1 % divisor;
        }

        return (int) value1;
    }
}
