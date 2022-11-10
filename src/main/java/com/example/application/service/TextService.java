package com.example.application.service;

import com.example.application.data.entity.Appeal;
import com.example.application.repository.AppealRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.el.stream.Stream;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Arrays;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Service
public class TextService {
    @Autowired
    AppealRepository appealRepository;

    public void save(Appeal appeal) {
        try {
            appealRepository.save(appeal);
        } catch (Exception e) {
            throw new RuntimeException("fail to store appeal: " + e.getMessage());
        }
    }

    public Appeal checkCategory(TextField text) {
        Appeal appeal = new Appeal();
        appeal.setAppealText(text.getValue());

        PythonInterpreter pyInterp = new PythonInterpreter();
        pyInterp.execfile("pythonMock.py");
        PyObject pyObject = pyInterp.get("payment");
        String payment = pyObject.toString();
//        pyInterp.set("data", text.getValue());

//        Scanner sc = null;
//        try {
//            sc = new Scanner(new File("final.csv"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        sc.useDelimiter("|");   //sets the delimiter pattern
//        while (sc.hasNext())  //returns a boolean value
//        {
//            String next = sc.next();
//        }
//        sc.close();  //closes the scanner

        appeal.setCategory(payment);
        return appeal;
    }

}
