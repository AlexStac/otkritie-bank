package com.example.application.views.uploadfile;

import com.example.application.data.AppealDTO;
import com.example.application.data.entity.Appeal;
import com.example.application.repository.AppealRepository;
import com.example.application.service.CSVHelper;
import com.example.application.service.CSVService;
import com.example.application.service.TextService;
import com.example.application.views.MainLayout;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.util.SharedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@PageTitle("Upload/file")
@Route(value = "upload/file", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class UploadfileView extends VerticalLayout {

    @Autowired
    private AppealRepository appealRepository;

    @Autowired
    CSVService csvService;

    @Autowired
    TextService textService;

    public UploadfileView() {
        Grid<String[]> grid = new Grid<>();

        TextField filter = new TextField();
        filter.setPlaceholder("Введите обращение");
        filter.setValueChangeMode(ValueChangeMode.EAGER);

        Button button = new Button("Добавить", VaadinIcon.PLUS.create());
        button.addClickListener(buttonClickEvent -> {
            Appeal appeal = textService.checkCategory(filter);
            textService.save(appeal);
            filter.setValue("");
            List<String> entries = new ArrayList<>();
            entries.add(appeal.getAppealText());
            entries.add(appeal.getCategory());
            entries.add("false");
            String[] headers = {"appeal", "category", "emotion"};

            for (int i = 0; i < headers.length; i++) {
                int colIndex = i;
                grid.addColumn(row -> row[colIndex])
                        .setHeader(SharedUtil.camelCaseToHumanFriendly(headers[colIndex]));
            }

            GridListDataView<String[]> gridListDataView = grid.setItems();
        });

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addSucceededListener(e -> {
            displayCsv(buffer);
        });

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("This place intentionally left empty"));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        add(grid);
        add(new HorizontalLayout(filter, button));
        add(new HorizontalLayout(upload));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void displayCsv(MemoryBuffer file) {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);

//                message = "Uploaded the file successfully: " + file.getOriginalFilename();
//                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getFileName() + "!";
                System.out.println(message);
//                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
//        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
//        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(resourceAsStream)).withCSVParser(parser).build();
//
//        try {
//            List<String[]> entries = reader.readAll();
//            String[] headers = entries.get(0);
//
//            for (int i = 0; i < headers.length; i++) {
//                int colIndex = i;
//                grid.addColumn(row -> row[colIndex])
//                        .setHeader(SharedUtil.camelCaseToHumanFriendly(headers[colIndex]));
//            }
//
//            grid.setItems(entries.subList(1, entries.size()));
//
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        }
    }

}
