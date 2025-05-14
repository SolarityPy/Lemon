package com.lemon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Lemon lemon = new Lemon("Invincible (Hard)", "Windows 10", "cstedman", "https://solarify.cc", "CyberPatriots2024!");
        ArrayList<Map<String, String>> checks = new ArrayList<>();
        ArrayList<Map<String, String>> checks2 = new ArrayList<>();
        
        Map<String, String> check1 = new HashMap();
        check1.put("type", "UserExistsNot");
        check1.put("name", "cstedman");

        Map<String, String> check2 = new HashMap();
        check2.put("type", "RegistryKey");
        check2.put("name", "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Winlogon");
        check2.put("value", "1");
        
        checks.add(check1);
        checks2.add(check2);
        
        lemon.addCheck("sup", 4, checks);
        lemon.addCheck("secondCheck", 2, checks2);

        lemon.removeCheck("sup");
    }
}