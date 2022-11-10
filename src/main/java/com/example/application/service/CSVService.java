package com.example.application.service;

import com.example.application.data.entity.Appeal;
import com.example.application.repository.AppealRepository;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CSVService {
  @Autowired
  AppealRepository appealRepository;

  public void saveToCsv(MemoryBuffer file) {
    List<Appeal> appealList = CSVHelper.csvToAppeals(file.getInputStream());

  }

  public void save(MemoryBuffer file) {
    List<Appeal> appealList = CSVHelper.csvToAppeals(file.getInputStream());
    try {
      appealRepository.saveAll(appealList);
    } catch (Exception e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

//  public ByteArrayInputStream load() {
//    List<Appeal> appealList = appealRepository.findAll();
//
//    ByteArrayInputStream in = CSVHelper.tutorialsToCSV(appealList);
//    return in;
//  }
//
//  public List<Appeal> getAllTutorials() {
//    return appealRepository.findAll();
//  }
}
