package service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Random;

public class NumberedFileService {

    private String path;

    public NumberedFileService(String path) {
        if (path == null) throw new NullPointerException();
        this.path = path;
    }

    public File generateNumbersToFile(final String nameFile, final double wantedSize) {

        Random random = new Random();
        File file = new File(path + "/" + nameFile);

        long start = System.currentTimeMillis();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")), false);
            String maxLengthInt = Integer.MAX_VALUE + "";
            Integer iterable = maxLengthInt.getBytes(Charset.forName("UTF-8")).length * 1024;

            do {
                for (int i = 0; i < iterable; i++) {
                    int number = random.nextInt(Integer.MAX_VALUE);
                    writer.print(number);
                    writer.print(",");
                }
            }
            while (file.length() < wantedSize * 1e9);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null ) writer.close();
        }

        long time = System.currentTimeMillis() - start;
        System.out.printf("Took %.1f seconds to create a file of %.3f GB\n", time / 1e3, file.length() / 1e9);

        return file;
    }

}
