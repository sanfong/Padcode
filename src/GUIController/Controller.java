package GUIController;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
                newTabInTabPane(file);
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
        newTabInTabPane(file);
    }

    private void newTabInTabPane(File file) {
        NoteTab tab;
        try {
            tab = new NoteTab(file);
        } catch (FileIsNotTextException e1) {
            tab = new NoteTab(file.getName());
            tab.setFileWithoutCheck(file);
            tab.getNote().setText("This file is binary and cannot be displayed.");
            tab.getNote().setEditable(false);
        } catch (FileIsDirectoryException e2) {
            return;
        }
        for (Tab tab2 : tabPane.getTabs()) {
            NoteTab noteTab = (NoteTab) tab2;
            if (noteTab.getFile() != null && noteTab.getFile().equals(file)) {
                tabPane.getSelectionModel().select(tab2);
                return;
            }
        }
        tabPane.getTabs().add(tab);
    }
}
