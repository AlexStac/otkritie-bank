package com.example.application.service;

import com.example.application.data.entity.Appeal;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
  public static String TYPE = "text/csv";
//  static String[] HEADERs = { "Id", "Title", "Description", "Published" };
  static String[] HEADERs = { "0" };
  public static boolean hasCSVFormat(MemoryBuffer file) {

    String mimeType = file.getFileData().getMimeType();

    if (!mimeType.equals(TYPE)) {
      return false;
    }

    return true;
  }

  public static List<Appeal> csvToAppeals(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter('|'))) {

      List<Appeal> appealList = new ArrayList<Appeal>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        Appeal appeal = new Appeal(
              Long.parseLong(csvRecord.get("id")),
              csvRecord.get("comment")
            );

        appealList.add(appeal);
      }

      return appealList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream tutorialsToCSV(List<Appeal> appealList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Appeal appeal : appealList) {
        List<String> data = Arrays.asList(
              String.valueOf(appeal.getAppealId()),
              appeal.getAppealText()
            );
        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

}
