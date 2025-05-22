package com.lemon;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.moandjiezana.toml.Toml;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OpenCheck {

    private ArrayList<LinkedHashMap<String, Object>> checksList;
    private Lemon lemonObj;

    public static List<Map<String, Object>> parseTablesInOrder(String filePath) throws Exception {
        List<Map<String, Object>> tables = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        Map<String, Object> currentTable = null;
        String currentHeader = null;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("[[")) {
                if (currentTable != null) {
                    tables.add(currentTable);
                }
                currentTable = new LinkedHashMap<>();
                currentHeader = line;
                currentTable.put("_header", currentHeader);
            } else if (!line.isEmpty() && currentTable != null && !line.startsWith("#")) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    currentTable.put(parts[0].trim(), parts[1].trim().replaceAll("^['\"]|['\"]$", ""));
                }
            }
        }
        if (currentTable != null) {
            tables.add(currentTable);
        }
        return tables;
    }

    public OpenCheck() {
        checksList = new ArrayList();
    }

    public static String getConfigPath(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Config File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public void readConfig(Stage stage) {
        String path = getConfigPath(stage);
        Toml toml = new Toml().read(new File(path));
        String[] arr = {"name", "title", "user", "os"};
        
        String title;
        String user;
        String os;
        String remote = null;
        String password = null;

        for (String attribute : arr) {
            if (toml.getString(attribute) == null) {
                Checks.throwError("Error", attribute + " does not exist...");
                return;
            }
        }
        
        title = toml.getString("title");
        user = toml.getString("user");
        os = toml.getString("os");
        remote = toml.getString("remote");
        password = toml.getString("password");
        if (remote != null && password != null) {
        lemonObj = new Lemon(title, os, user, remote, password, true);
        } else {
        lemonObj = new Lemon(title, os, user, true);
        }
        //Checks checkObj = new Checks(lemonObj, stage);
        try {
            List<Map<String, Object>> tables = parseTablesInOrder(path);
            for (Map<String, Object> table : tables) {
                // Print the table header (e.g., [[check]])
                System.out.println(table.get("_header"));
                // Iterate through all keys except "_header"
                for (Map.Entry<String, Object> entry : table.entrySet()) {
                    if (!entry.getKey().equals("_header")) {
                        System.out.println(entry.getKey() + " = " + entry.getValue());
                    }
                }
                System.out.println(); // Blank line between tables
            }

        } catch (Exception e) {
            Checks.throwError("Error", e.toString());
        }
        

    }


}