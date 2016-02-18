package org.osrspvp.util.cache.tools;

import org.osrspvp.util.cache.defs.ItemDef;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GEPriceDumper {

    public static void main(String[] args) {
        ItemDef.unpackConfig();
        dumpBonuses(13223);
    }

    public static void dumpBonuses(int totalItems) {
        for (int i = 0; i < totalItems; i++) {
            ItemDef item = ItemDef.forId(i);
            URL url;
            try {
                try {
                    try {
                        if (item.name.length() <= 0)
                            continue;
                        url = new URL("http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_"));
                        URLConnection con = url.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        System.out.println("Connection to " + url + "");
                        BufferedWriter writer = new BufferedWriter(new FileWriter("geprices.txt", true));
                        while ((line = in.readLine()) != null) {
                            try {
                                if (line.contains("<span id=\"GEPrice\"><span class=\"GEItem\"><span>")) {
                                    line = line.replace("</span></span> coins</span>", "").replace(",", "")
                                        .replace("</th><td>", "")
                                        .replace("<span id=\"GEPrice\"><span class=\"GEItem\"><span>", "");
                                    if (line.contains("' not found.--></span>"))
                                        continue;
                                    line = line.replace(" (<a href=\"/wiki/Exchange:" + item.name
                                        .replaceAll(" ", "_") + "\" title=\"Exchange:" + item.name + "\">info</a>) -", "");
                                    System.out.println("Line: " + line);
                                    writer.write(item.id + " " + line);
                                    writer.newLine();
                                }
                            } catch (NumberFormatException e) {

                            }
                        }
                        in.close();
                        writer.close();
                    } catch (NullPointerException e) {

                    }
                } catch (FileNotFoundException e) {

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
