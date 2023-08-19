package by.senla.training.bulyha.carservice.printwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static List<String> dataImport(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        List<String> dataList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String dataLine = scanner.nextLine();
            dataList.add(dataLine);
        }
        return dataList;
    }

    public static void dataExport(List<String> stringList, File file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
        for (String string : stringList) {
            printWriter.println(string);
        }
        printWriter.close();
    }
}
