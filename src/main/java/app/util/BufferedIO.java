package app.util;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Peng You
 */

/*
    Class that read/write from/to a file using BufferedReader/Writer.
 */
public class BufferedIO {

    private File file;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public BufferedIO(String path) {
        this.file = new File(path);
    }

    public ArrayList<String> readFile() {
        ArrayList<String> linesList = new ArrayList<>();

        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.file));
            String line;

            while ((line = this.bufferedReader.readLine()) != null) {
                linesList.add(line);
            }

            this.bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return linesList;
    }

    public void writeFile(String line, boolean newLine, boolean append) {
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(this.file, append));

            if (newLine) {
                this.bufferedWriter.newLine();
            }

            this.bufferedWriter.write(line);

            this.bufferedWriter.flush();
            this.bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

}
