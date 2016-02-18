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

/**
 * @author jack
 */
public class BonusDumper {

    /**
     * Dumps item bonuses
     *
     * @param parameters
     */
    public static void main(String[] parameters) {
        /**
         * Dumps data
         */
        raidSite();
    }

    public static void raidSite() {
        ItemDef.unpackConfig();
        int[] bonuses = new int[12];
        int bonus = 0;
        for (int i = 0; i < 13223; i++) {
            ItemDef item = ItemDef.forId(i);
            URL url;
            try {
                try {
                    try {
                        url = new URL("http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_"));
                        System.out.println("Connecting to: " + url);
                        URLConnection con = url.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        BufferedWriter writer = new BufferedWriter(new FileWriter("item.cfg", true));
                        while ((line = in.readLine()) != null) {
                            try {
                                if (line.contains("<td style=\"text-align: center; width: 35px;\">")) {
                                    line = line.replace("</td>", "").replace("%", "").replace("?", "").replace("\"\"", "")
                                        .replace("<td style=\"text-align: center; width: 35px;\">", "");
                                    bonuses[bonus] = Integer.parseInt(line);
                                    bonus++;
                                } else if (line.contains("<td style=\"text-align: center; width: 30px;\">")) {
                                    if (bonus == 10) {
                                        line = line.replace("</td>", "").replace("%", "").replace("?", "").replace("%", "")
                                            .replace("<td style=\"text-align: center; width: 30px;\">", "");
                                        bonuses[bonus] = Integer.parseInt(line);
                                        bonus++;
                                    }
                                }
                            } catch (NumberFormatException e) {

                            }
                            if (bonus >= 11)
                                bonus = 0;
                            // in.close();
                        }
                        in.close();
                        writer.write("item	=	" + i + "	" + item.name.replace(" ", "_") + "	" + item.description
                            .replace(" ",
                                "_") + "	" + item.price + "	" + item.price + "	" + item.price + "	" + bonuses[0] + "	" + bonuses[1] + "	" + bonuses[2] + "	" + bonuses[3] + "	" + bonuses[4] + "	" + bonuses[5] + "	" + bonuses[6] + "	" + bonuses[7] + "	" + bonuses[8] + "	" + bonuses[9] + "	" + bonuses[10] + "	" + bonuses[11]);
                        for (int i1 = 0; i1 < bonuses.length; i++) {
                            bonuses[i1] = 0;
                        }
                        writer.newLine();
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
