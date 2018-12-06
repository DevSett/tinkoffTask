package web.service;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import web.enums.Codes;

import java.io.*;
import java.nio.CharBuffer;

@Log4j
@Service
public class FindindService {


    /**
     * Метод для нахождение значения в файле
     * @param number - число
     * @param fileName - имя файла
     * @return
     */
    public Codes isNumberFromFile(Integer number, String fileName) {
        File file = new File(fileName);
        if (!file.isFile()) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                return generateError(e);
            }
        }
        String stringNumber = String.valueOf(number);

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));


            CharBuffer charBuffer = CharBuffer.allocate(16 * 1024);

            String tempEndFile = "";
//        long start = System.currentTimeMillis();
            while (bufferedReader.read(charBuffer) > 0) {
                StringBuilder xTempStroke = new StringBuilder();

                charBuffer.flip();
                while (charBuffer.hasRemaining()) {
                    xTempStroke.append(charBuffer.get());
                }
                charBuffer.clear();

                String stringStroke = xTempStroke.toString();
                String[] splitedStroke = stringStroke.split(",");

                if (!stringStroke.contains(",") && (tempEndFile + stringStroke).equals(stringNumber)) return Codes.OK;
                if (!tempEndFile.isEmpty() && stringStroke.charAt(',') == 0 && tempEndFile.equals(stringNumber))
                    return Codes.OK;
                if (!tempEndFile.isEmpty() && (tempEndFile + splitedStroke[0]).equals(stringNumber) && stringStroke.charAt(',') != 0)
                    return Codes.OK;
                for (int i = 0; i < splitedStroke.length - 1; i++) {
                    if (i == 0 && !tempEndFile.isEmpty() && stringStroke.charAt(',') != 0) continue;
                    if (splitedStroke[i].equals(stringNumber)) {
                        return Codes.OK;
                    }
                }


                if (stringStroke.charAt(xTempStroke.length() - 1) != ',') {
                    tempEndFile = stringStroke.substring(stringStroke.lastIndexOf(",") + 1);
                } else {
                    tempEndFile = "";
                }
                if (tempEndFile.isEmpty() && splitedStroke[splitedStroke.length - 1].equals(stringNumber)) {
                    tempEndFile = stringNumber;
                }

            }
            if (!tempEndFile.isEmpty() && tempEndFile.equals(stringNumber)) return Codes.OK;

//        long time = System.currentTimeMillis() - start;
//        System.out.printf("Took %.1f seconds checked a file of %.3f GB\n", time / 1e3, file.length() / 1e9);
        } catch (IOException e) {
            return generateError(e);

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }

        return Codes.NOT_FOUND;
    }

    /**
     * Для того чтобы убрать однотипный код ошибок
     * @param e - Ошибка
     * @return
     */
    private Codes generateError(Exception e){
        log.error(e);

        Codes codes = Codes.ERROR;
        codes.setError(e.getMessage());
        return codes;
    }
}
