package org.osrspvp.sanction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RankHandler {

    private static final Logger log = Logger.getLogger(RankHandler.class.getName());

    private static final String LOCATION = "./Data/sanction/ranks/";

    private static ArrayList<String> playerModerators = new ArrayList<String>();
    private static ArrayList<String> administrators = new ArrayList<String>();
    private static ArrayList<String> developers = new ArrayList<String>();
    private static ArrayList<String> premium = new ArrayList<String>();
    private static ArrayList<String> superPremium = new ArrayList<String>();
    private static ArrayList<String> extremePremium = new ArrayList<String>();
    private static ArrayList<String> supporters = new ArrayList<String>();
    private static ArrayList<String> dicers = new ArrayList<String>();
    private static ArrayList<String> veterans = new ArrayList<String>();
    private static ArrayList<String> youtuber = new ArrayList<String>();

    public static boolean isModerator(String name) {
        if (playerModerators.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isAdministrator(String name) {
        if (administrators.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isDeveloper(String name) {
        if (developers.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isPremium(String name) {
        if (premium.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isSuperPremium(String name) {
        if (superPremium.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isExtremePremium(String name) {
        if (extremePremium.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isSupporter(String name) {
        if (supporters.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isDicer(String name) {
        if (dicers.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isVeteran(String name) {
        if (veterans.contains(name)) {
            return true;
        }
        return false;
    }

    public static boolean isYoutuber(String name) {
        if (youtuber.contains(name)) {
            return true;
        }
        return false;
    }

    public static void loadRankList() {
        loadModList();
        loadAdminList();
        loadDeveloperList();
        loadPremiumList();
        loadSuperPremiumList();
        loadExtremePremiumList();
        loadSupportersList();
        loadDicerList();
        loadVeteranList();
        loadYoutuberList();
    }

    public static byte getRank(String name) {
        if (playerModerators.contains(name)) {
            return 1;
        }
        if (administrators.contains(name)) {
            return 2;
        }
        if (developers.contains(name)) {
            return 3;
        }
        if (premium.contains(name)) {
            return 4;
        }
        if (superPremium.contains(name)) {
            return 5;
        }
        if (extremePremium.contains(name)) {
            return 6;
        }
        if (supporters.contains(name)) {
            return 7;
        }
        if (dicers.contains(name)) {
            return 8;
        }
        if (veterans.contains(name)) {
            return 9;
        }
        if (youtuber.contains(name)) {
            return 10;
        }
        return 0;
    }

    private static void loadModList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "mods.txt"));
            String names = "";
            try {
                log.info("Reading mods.txt...");
                while ((names = reader.readLine()) != null) {
                    playerModerators.add(names);
                }
                log.info(playerModerators.size() + " moderators loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadAdminList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "admins.txt"));
            String names = "";
            try {
                log.info("Reading admins.txt...");
                while ((names = reader.readLine()) != null) {
                    administrators.add(names);
                }
                log.info(administrators.size() + " admins loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadDeveloperList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "devs.txt"));
            String names = "";
            try {
                log.info("Reading devs.txt...");
                while ((names = reader.readLine()) != null) {
                    developers.add(names);
                }
                log.info(developers.size() + " devs loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadPremiumList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "premium.txt"));
            String names = "";
            try {
                log.info("Reading premium.txt...");
                while ((names = reader.readLine()) != null) {
                    premium.add(names);
                }
                log.info(premium.size() + " premiums loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadSuperPremiumList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "super.txt"));
            String names = "";
            try {
                log.info("Reading super.txt...");
                while ((names = reader.readLine()) != null) {
                    superPremium.add(names);
                }
                log.info(superPremium.size() + " supers loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadExtremePremiumList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "extreme.txt"));
            String names = "";
            try {
                log.info("Reading extreme.txt...");
                while ((names = reader.readLine()) != null) {
                    extremePremium.add(names);
                }
                log.info(extremePremium.size() + " extremes loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadSupportersList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "supporters.txt"));
            String names = "";
            try {
                log.info("Reading supporters.txt...");
                while ((names = reader.readLine()) != null) {
                    supporters.add(names);
                }
                log.info(supporters.size() + " supporters loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadDicerList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "dicers.txt"));
            String names = "";
            try {
                log.info("Reading dicers.txt...");
                while ((names = reader.readLine()) != null) {
                    dicers.add(names);
                }
                log.info(dicers.size() + " dicers loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadVeteranList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "vets.txt"));
            String names = "";
            try {
                log.info("Reading vets.txt...");
                while ((names = reader.readLine()) != null) {
                    veterans.add(names);
                }
                log.info(veterans.size() + " veterans loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void loadYoutuberList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOCATION + "youtubers.txt"));
            String names = "";
            try {
                log.info("Reading youtubers.txt...");
                while ((names = reader.readLine()) != null) {
                    youtuber.add(names);
                }
                log.info(youtuber.size() + " youtubers loaded.");
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveMod(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "mods.txt", true));
            playerModerators.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveAdmin(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "admins.txt", true));
            administrators.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveDev(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "devs.txt", true));
            developers.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void givePremium(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "premium.txt", true));
            premium.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveSuperPremium(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "super.txt", true));
            superPremium.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveExtremePremium(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "extreme.txt", true));
            extremePremium.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveSupporter(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "supporters.txt", true));
            supporters.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveDicer(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "dicers.txt", true));
            dicers.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveVeteran(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "vets.txt", true));
            veterans.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void giveYoutuber(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOCATION + "youtubers.txt", true));
            youtuber.add(username);
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void deleteFromSactionList(String file, String name) {
        try {
            if (file.contains("mods")) {
                playerModerators.remove(name);
            }
            if (file.contains("admins")) {
                administrators.remove(name);
            }
            if (file.contains("devs")) {
                developers.remove(name);
            }
            if (file.contains("premium")) {
                premium.remove(name);
            }
            if (file.contains("super")) {
                superPremium.remove(name);
            }
            if (file.contains("extreme")) {
                extremePremium.remove(name);
            }
            if (file.contains("supporters")) {
                supporters.remove(name);
            }
            if (file.contains("dicers")) {
                dicers.remove(name);
            }
            if (file.contains("vets")) {
                veterans.remove(name);
            }
            if (file.contains("youtubers")) {
                youtuber.remove(name);
            }
            BufferedReader r = new BufferedReader(new FileReader(file));
            ArrayList<String> contents = new ArrayList<String>();
            while (true) {
                String line = r.readLine();
                if (line == null) {
                    break;
                } else {
                    line = line.trim();
                }
                if (!line.equalsIgnoreCase(name)) {
                    contents.add(line);
                }
            }
            r.close();
            BufferedWriter w = new BufferedWriter(new FileWriter(file));
            for (String line : contents) {
                w.write(line, 0, line.length());
                w.newLine();
            }
            w.flush();
            w.close();
        } catch (Exception e) {
        }
    }

}
