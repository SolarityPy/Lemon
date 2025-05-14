package com.lemon;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lemon {
    private Map<String, String> attributes;

    public Lemon(String title, String os, String user, String remote, String password) {
        attributes = new HashMap();
        attributes.put("title", title);
        attributes.put("os", os);
        attributes.put("user", user);
        attributes.put("remote", remote);
        attributes.put("password", password);

        File config = new File("scoring.conf");
        try (FileWriter writer = new FileWriter(config)) {
                for (Map.Entry<String, String> entry : attributes.entrySet()) {
                    writer.write(entry.getKey() + " = '" + entry.getValue() + "'\n");
                }
        } catch (IOException e) {
            System.out.print(e);
        }
    }   
                    //key value    ["type": "PathExists", "path": "C:\Windows"]
    public void addCheck(String message, int points, ArrayList<Map<String, String>> checks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scoring.conf", true))) {
            writer.write("\n\n[[check]]\n");
            writer.write("message = '" + message + "'\n");
            writer.write("points = " + points + "\n");
            for (Map<String, String> checkPass : checks) {
                writer.write("    [[check.pass]]\n");
                for (Map.Entry<String, String> entry : checkPass.entrySet()) {
                    writer.write("    " + entry.getKey() + " = " + "'" + entry.getValue() + "'\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int lineFinder(String path, String text, int start) {
        int lineNumber = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while (lineNumber <= start && reader.readLine() != null) {
                lineNumber++;
            }
            
            while ((line = reader.readLine()) != null) {
                if (line.contains(text)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
        return -1;
    }
    
    public void removeLinesBetween(String filePath, int startLine, int endLine) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            if (startLine > 0 && endLine <= lines.size() && startLine < endLine) {
                lines.subList(startLine - 1, endLine - 1).clear();
            }
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void removeCheck(String message){
        int messageLine = lineFinder("scoring.conf", message, 1);
        int checkLine = lineFinder("scoring.conf", "[[check]]", messageLine);
        removeLinesBetween("scoring.conf", messageLine - 1, checkLine - 1);
    } 
    
    @Override
    public String toString() {
        String result = "";
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            result += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        return result;
    }

}
