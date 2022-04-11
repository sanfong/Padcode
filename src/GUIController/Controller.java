package GUIController;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ui.FileTreeItem;
import ui.NameFile;
import ui.NoteTab;
import ui.MyException.FileIsDirectoryException;
import ui.MyException.FileIsNotTextException;

public class Controller {

    @FXML
    private TreeView<NameFile> explorerView;

    @FXML
    private TabPane tabPane;

    FileChooser fileChooser = new FileChooser();
    DirectoryChooser dirChooser = new DirectoryChooser();

    @FXML
    public void initialize() {
        System.out.println("start");
        for (var tab : tabPane.getTabs()) {
            ((TextArea) tab.getContent()).setTextFormatter(NoteTab.getTabFormat());
        }
    }

    @FXML // Double click on tabpane to create new tab
    public void createNewTab(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                Tab t = new NoteTab();
                tabPane.getTabs().add(t);
                tabPane.getSelectionModel().select(t);
            }
        }
    }

    public void menuOpenFile(ActionEvent e) {
        List<File> list = fileChooser.showOpenMultipleDialog(null);
        if (list != null) {
            for (File file : list) {
                NoteTab tab;
                try {
                    tab = new NoteTab(file);
                } catch (FileIsDirectoryException | FileIsNotTextException e1) {
                    tab = new NoteTab(file.getName());
                    tab.getNote().setText("This file is binary and cannot be displayed.");
                    tab.getNote().setEditable(false);
                }
                tabPane.getTabs().add(tab);
            }
        }
    }

    public void menuOpenFolder(ActionEvent e) {
        NameFile file = new NameFile(dirChooser.showDialog(null));
        FileTreeItem root = new FileTreeItem(file);
        explorerView.setRoot(root);
    }

    public void menuSave(ActionEvent e) {

    }

    public void menuSaveAs(ActionEvent e) {

    }

    public void setExplorerView(TreeView<NameFile> explorerView) {
        this.explorerView = explorerView;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void selectItem(MouseEvent event) {
        TreeItem<NameFile> item = explorerView.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        File file = item.getValue();
        NoteTab tab;
        try {
            tab = new NoteTab(file);
        } catch (FileIsNotTextException e1) {
            tab = new NoteTab(file.getName());
            tab.getNote().setText("This file is binary and cannot be displayed.");
            tab.getNote().setEditable(false);
        } catch (FileIsDirectoryException e2) {
            return;
        }
        tabPane.getTabs().add(tab);
    }
}
