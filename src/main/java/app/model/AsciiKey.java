package app.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Peng You
 */
public class AsciiKey {
    //All the english Ascii characters list.
    private final ArrayList<Character> asciiStream = new ArrayList<>();

    //Our Ascii key (shuffled asciiStream)
    private ArrayList<Character> keyStream;

    public AsciiKey() {
        initAsciiStream();
        generateNewKeyStream();
    }

    /**
     * Initialize all the ascii characters and adds it to the list.
     */
    private void initAsciiStream() {
        int initialAscii = 32;
        int endAscii = 126;

        /*
            ENTER input it's not recognized, so we add it as a special character,
            and later encrypt/decrypt as a \n input.
         */
        char nextLineId = '·';
        asciiStream.add(nextLineId);

        for (int i = initialAscii; i <= endAscii; i++) {
            asciiStream.add((char) i);
        }
    }

    /**
     * Shuffle the ascii list, and saving it as a new key.
     */
    public void generateNewKeyStream() {
        keyStream = (ArrayList<Character>) asciiStream.clone();

        Collections.shuffle(keyStream);
    }

    /**
     * Validates a user input key.
     * @param customKey Key imported by the user.
     * @return Valid/Invalid key.
     */
    public boolean validateKey(String customKey) {
        boolean validKey = true;

        if (!(customKey.toCharArray().length == asciiStream.size())) {
            return false;
        }

        for (char c : asciiStream) {
            if (!customKey.contains(c + "")) {
                validKey = false;
                break;
            }
        }

        return validKey;
    }

    /**
     * Set the current key to an imported key.
     * @param customKey Key imported by the user.
     */
    public void setCustomKey(String customKey) {
        keyStream = new ArrayList<>();
        for (char c : customKey.toCharArray()) {
            keyStream.add(c);
        }
    }

    /**
     * Returns the key characters list, as a string.
     * @return Key string.
     */

    public String getKeyAsString() {
        StringBuilder key = new StringBuilder();

        for (char c : keyStream) {
            key.append(c);
        }

        return key.toString();
    }

    public ArrayList<Character> getKeyStream() {
        return keyStream;
    }

    public ArrayList<Character> getAsciiStream() {
        return asciiStream;
    }

}
