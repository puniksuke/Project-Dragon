package org.osrspvp.sanction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StarterHandler {

    public static void initialize() {
        appendStarters();
        appendStarters2();
    }

    public static ArrayList<String> starterRecieved1 = new ArrayList<String>();
    public static ArrayList<String> starterRecieved2 = new ArrayList<String>();

    public static void appendStarters() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("./Data/starters/FirstStarterRecieved.txt"));
            String data = null;
            try {
                while ((data = in.readLine()) != null) {
                    starterRecieved1.add(data);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendStarters2() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("./Data/starters/SecondStarterRecieved.txt"));
            String data = null;
            try {
                while ((data = in.readLine()) != null) {
                    starterRecieved2.add(data);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addIpToStarter1(String IP) {
        starterRecieved1.add(IP);
        addIpToStarterList1(IP);
    }

    public static void addIpToStarter2(String IP) {
        starterRecieved2.add(IP);
        addIpToStarterList2(IP);
    }

    public static void addIpToStarterList1(String Name) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./Data/starters/FirstStarterRecieved.txt", true));
            try {
                out.newLine();
                out.write(Name);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addIpToStarterList2(String Name) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./Data/starters/SecondStarterRecieved.txt", true));
            try {
                out.newLine();
                out.write(Name);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasRecieved1stStarter(String IP) {
        if (starterRecieved1.contains(IP)) {
            return true;
        }
        return false;
    }

    public static boolean hasRecieved2ndStarter(String IP) {
        if (starterRecieved2.contains(IP)) {
            return true;
        }
        return false;
    }
}
