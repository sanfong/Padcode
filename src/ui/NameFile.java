package ui;

import java.io.File;

public class NameFile extends File{

     public NameFile(File parent, String child) {
          super(parent, child);
     }
     
     public NameFile(String path) {
          super(path);
     }

     public NameFile(String parent, String child) {
          super(parent, child);
     }

     public NameFile(File file) {
          super(file.getPath());
     }

     @Override
     public NameFile[] listFiles() {
          File[] files = super.listFiles();
          NameFile[] nameFiles = new NameFile[files.length];
          for (int i = 0; i < nameFiles.length; i++) {
               nameFiles[i] = new NameFile(files[i]);
          }
          return nameFiles;
     }

     @Override
     public String toString() {
          return getName();
     }
}
