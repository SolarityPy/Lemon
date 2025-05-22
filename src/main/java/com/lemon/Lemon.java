package com.lemon;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Lemon {
    private Map<String, String> attributes;
    //private ArrayList<Map<String, >> checkList;

    public Lemon(String title, String os, String user, String remote, String password, boolean opening) {
        attributes = new LinkedHashMap<>();
        attributes.put("name", title);
        attributes.put("title", title);
        attributes.put("user", user);
        attributes.put("os", os);
        attributes.put("remote", remote);
        attributes.put("password", password);

        if (!(opening)) {
            File config = new File("scoring.conf");
            try (FileWriter writer = new FileWriter(config)) {
                    for (Map.Entry<String, String> entry : attributes.entrySet()) {
                        writer.write(entry.getKey() + " = '" + entry.getValue() + "'\n");
                    }
            } catch (IOException e) {
                System.out.print(e);
            }
        }  
    } 

    public Lemon(String title, String os, String user, boolean opening) { // Local Image Constructor
        attributes = new LinkedHashMap<>();
        attributes.put("name", title);
        attributes.put("title", title);
        attributes.put("user", user);
        attributes.put("os", os);

        if (!(opening)) {
            File config = new File("scoring.conf");
            try (FileWriter writer = new FileWriter(config)) {
                    for (Map.Entry<String, String> entry : attributes.entrySet()) {
                        writer.write(entry.getKey() + " = '" + entry.getValue() + "'\n");
                    }
                    writer.write("local = true\n");
            } catch (IOException e) {
                System.out.print(e);
            }
        }


    }   
                    //key value    ["type": "PathExists", "path": "C:\Windows"]
    public void addCheck(String message, String points, ArrayList<String> kind, ArrayList<String> type, ArrayList<Map<String, String>> checks, ArrayList<Boolean> notList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scoring.conf", true))) {
            int count = 0;
            writer.write("\n\n[[check]]\n");
            writer.write("message = '" + message + "'\n");
            writer.write("points = " + points + "\n");
            for (Map<String, String> checkPass : checks) {
                writer.write("\n    " + kind.get(count));
                if (notList.get(count)) {
                    writer.write("\n    type = '" + type.get(count) + "Not'\n");
                } else {
                    writer.write("\n    type = '" + type.get(count) + "'\n");
                }
                count++;
                for (Map.Entry<String, String> entry : checkPass.entrySet()) {
                    //substring needed because the label is created as value needed + ":" so we just 
                    //get rid of it with substring
                    writer.write("    " + entry.getKey().substring(0, entry.getKey().length() - 1) + " = " + "'" + entry.getValue() + "'\n");
                }
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    

    //used Copilot for removing methods can redo if wanted
    public List<Integer> findCheckBlockStartLines(String path) {
    List<Integer> startLines = new ArrayList<>();
    int lineNumber = 1;
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().equals("[[check]]")) {
                startLines.add(lineNumber);
            }
            lineNumber++;
        }
    } catch (IOException e) {
        System.out.println(e);
    }
    return startLines;
    }

    public void removeCheck(int removeIndex) {
        String path = "scoring.conf";
        List<Integer> starts = findCheckBlockStartLines(path);
        if (removeIndex < 0 || removeIndex >= starts.size()) return;

        int startLine = starts.get(removeIndex);
        int endLine = (removeIndex + 1 < starts.size()) ? starts.get(removeIndex + 1) : Integer.MAX_VALUE;

        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            List<String> newLines = new ArrayList<>();
            for (int i = 1; i <= lines.size(); i++) {
                if (i < startLine || i >= endLine) {
                    newLines.add(lines.get(i - 1));
                }
            }
            Files.write(Paths.get(path), newLines);
        } catch (IOException e) {
            System.out.println(e);
            }
    }
    
    @Override
    public String toString() {
        String result = "";
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            result += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        return result;
    }

    public String getOs() {
        return attributes.get("os");
    }

}