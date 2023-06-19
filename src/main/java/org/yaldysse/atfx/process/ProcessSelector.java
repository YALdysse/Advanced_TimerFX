package org.yaldysse.atfx.process;

import org.yaldysse.atfx.fxGui;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class ProcessSelector extends Stage
{
    public final double PREFERRED_WIDTH;
    public final double PREFERRED_HEIGHT;
    private Scene scene;
    private VBox root;
    private ScrollPane scrollPane;
    private ArrayList<Process> processes;
    private Process selectedProcess;
    private Background hoverItemBackground;
    private Background defaultBackground;
    private TextField search_TextField;
    private TableView<Process> process_TableView;

    public ProcessSelector()
    {
        PREFERRED_WIDTH = fxGui.rem * 17.0D;
        PREFERRED_HEIGHT = fxGui.rem * 20.0D;

        hoverItemBackground = new Background(new BackgroundFill(Color.LINEN,
                new CornerRadii(fxGui.rem * 0.2D), Insets.EMPTY));

        initializeComponents();
        initializeTableView();

        setScene(scene);
        setWidth(PREFERRED_WIDTH);
        setHeight(PREFERRED_HEIGHT);
        setTitle("Process Selector");
    }

    private void initializeComponents()
    {
        root = new VBox(fxGui.rem * 1.3D);
        root.setFillWidth(true);
        root.setPadding(new Insets(fxGui.rem * 0.8D));
        scene = new Scene(root);

        search_TextField = new TextField();
        search_TextField.setPromptText("Search...");
        search_TextField.textProperty().addListener(this::search_TextChanged);
        HBox searchPane = new HBox(search_TextField);
        searchPane.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(search_TextField, Priority.ALWAYS);

        scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(fxGui.rem * 0.2D));

        root.getChildren().addAll(searchPane);
    }

    private void search_TextChanged(ObservableValue observable, String oldValue, String newValue)
    {
        if (newValue.length() < oldValue.length())
        {
            process_TableView.setItems(FXCollections.observableArrayList(processes));
        }
        searchAndShow(search_TextField.getText());
    }

    private void searchAndShow(final String text)
    {
        ObservableList<Process> items = process_TableView.getItems();
        ObservableList<Process> filtered = FXCollections.observableArrayList();

        for (Process process : items)
        {
            if (process.getShortCommand().toLowerCase().contains(text.toLowerCase()))
            {
                filtered.add(process);
            }
        }

        process_TableView.setItems(filtered);
    }


    public void setProcessesList(final ArrayList<Process> aProcessesList)
    {
        processes = aProcessesList;
        updateData();
    }

    private void updateData()
    {
        process_TableView.getItems().setAll(processes);
    }

    public Process getSelectedProcess()
    {
        return selectedProcess;
    }

    private void initializeTableView()
    {
        process_TableView = new TableView<>();

        TableColumn<Process, Integer> pid_column = new TableColumn<>("PID");
        pid_column.setCellValueFactory(new PropertyValueFactory<>("pid"));

        TableColumn<Process, String> command_column = new TableColumn<>("Command");
        command_column.setCellValueFactory(new PropertyValueFactory<>("shortCommand"));

        process_TableView.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
            {
                selectItem_Action(process_TableView.getSelectionModel().getSelectedItem());
            }
        });
        process_TableView.getColumns().addAll(pid_column, command_column);
        process_TableView.setRowFactory(new Callback<TableView<Process>, TableRow<Process>>()
        {
            @Override
            public TableRow<Process> call(TableView<Process> param)
            {
                return new TableRow<Process>()
                {
                    @Override
                    protected void updateItem(Process item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        setOnMouseClicked(event ->
                        {
                            if (event.getClickCount() >= 2)
                            {
                                selectItem_Action(item);
                            }
                        });
                    }
                };
            }
        });

//        ObservableList<Process> data = FXCollections.observableArrayList(new Process(
//                352,"yalka","atFX"),
//                new Process(632, "yaroslav", "firefox"));
//        process_TableView.setItems(data);


        process_TableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//        table.getSelectionModel().selectedIndexProperty().addListener(
//                new RowSelectChangeListener());


        root.getChildren().add(process_TableView);
    }

    private void selectItem_Action(final Process item)
    {
        selectedProcess = item;
        System.out.println(selectedProcess.getPid());
        hide();
    }

}


