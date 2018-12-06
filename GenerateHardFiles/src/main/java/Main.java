import service.NumberedFileService;

public class Main {

    public static void main(String[] args) {
        String path = Main.class.getResource("/").getPath();

        NumberedFileService fileService = new NumberedFileService(path);

        double maxLengthFile = 1.1;
        double minLengthFile = 0.9;

        for (int i = 0; i < 20; i++) {
            double size = (Math.random() * (maxLengthFile - minLengthFile)) + minLengthFile;
            fileService.generateNumbersToFile(i + ".txt", size);
        }

    }

}
