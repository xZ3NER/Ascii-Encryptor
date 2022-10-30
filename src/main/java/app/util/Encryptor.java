package app.util;

import app.model.AsciiKey;

/**
 * @author Peng You
 */
public class Encryptor {

    //This encryptor uses the ascii encryption method.
    private AsciiKey asciiKey;

    public Encryptor() {
        asciiKey = new AsciiKey();
    }

    /**
     * Encrypts a text input.
     * @param text  Text passed by the user.
     * @return Encrypted string.
     */
    public String encrypt(String text) {
        char[] textAsArray = text.toCharArray();
        StringBuilder encryptedText = new StringBuilder();

        for (char c : textAsArray) {
            for (int j = 0; j < asciiKey.getAsciiStream().size(); j++) {

                //'·' = special character as ENTER input defined in AsciiKey.
                if (c == '\n' && asciiKey.getAsciiStream().get(j).equals('·')) {
                    encryptedText.append(asciiKey.getKeyStream().get(j));
                }

                if (c == asciiKey.getAsciiStream().get(j)) {
                    encryptedText.append(asciiKey.getKeyStream().get(j));
                }
            }
        }

        return encryptedText.toString();
    }

    /**
     * Decrypts a text input.
     * @param text  Text passed by the user.
     * @return Decrypted string.
     */
    public String decrypt(String text) {
        char[] textAsArray = text.toCharArray();
        StringBuilder decryptedText = new StringBuilder();

        for (char c : textAsArray) {
            for (int j = 0; j < asciiKey.getKeyStream().size(); j++) {
                if (c == asciiKey.getKeyStream().get(j)) {

                    //'·' = special character as ENTER input defined in AsciiKey.
                    if (asciiKey.getAsciiStream().get(j).equals('·')) {
                        decryptedText.append('\n');
                    } else {
                        decryptedText.append(asciiKey.getAsciiStream().get(j));
                    }
                }
            }
        }

        return decryptedText.toString();
    }

    public AsciiKey getAsciiKey() {
        return asciiKey;
    }

    public void setAsciiKey(AsciiKey asciiKey) {
        this.asciiKey = asciiKey;
    }
}
