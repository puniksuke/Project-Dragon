package org.osrspvp.util.cache.defs;

import org.osrspvp.util.cache.Buffer;
import org.osrspvp.util.cache.FileOperations;

public class NPCDef {

    public static final NPCDef forId(int i) {
        for (int j = 0; j < 20; j++)
            if (cache[j].type == (long) i)
                return cache[j];

        cacheIndex = (cacheIndex + 1) % 20;
        NPCDef npcDef = cache[cacheIndex] = new NPCDef();
        npcData.currentOffset = streamIndices[i];
        npcDef.type = i;
        npcDef.readValues(npcData);
        return npcDef;
    }

    public int getSize() {
        return this.boundDim;
    }

    public static final void unpackConfig() {
        npcData = new Buffer(FileOperations.readFile("./data/cache/npc.dat"));
        Buffer indexStream = new Buffer(FileOperations.readFile("./data/cache/npc.idx"));
        totalNpcs = indexStream.readUnsignedWord();
        streamIndices = new int[totalNpcs];
        int i = 2;
        for (int j = 0; j < totalNpcs; j++) {
            streamIndices[j] = i;
            i += indexStream.readUnsignedWord();
        }

        cache = new NPCDef[20];
        for (int k = 0; k < 20; k++)
            cache[k] = new NPCDef();
        System.out.println("size: " + forId(6654).name);
        System.out.println("Succesfully loaded npc config");
    }

    public void readValues(Buffer stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            byte[] description;
            boolean aBoolean87;
            int anInt79;
            int anInt75;
            int anInt92;
            byte anInt85;
            boolean aBoolean93;
            int anInt86;
            int anInt91;
            boolean aBoolean84;
            if (i == 1) {
                int j = stream.readUnsignedByte();
                int[] anIntArray94 = new int[j];
                for (int j1 = 0; j1 < j; j1++)
                    anIntArray94[j1] = stream.readUnsignedWord();

            } else if (i == 2)
                name = stream.readString();
            else if (i == 3)
                description = stream.readBytes();
            else if (i == 12)
                boundDim = stream.readSignedByte();
            else if (i == 13)
                standAnim = stream.readUnsignedWord();
            else if (i == 14)
                walkAnim = stream.readUnsignedWord();
            else if (i == 17) {
                walkAnim = stream.readUnsignedWord();
                int anInt58 = stream.readUnsignedWord();
                int anInt83 = stream.readUnsignedWord();
                int anInt55 = stream.readUnsignedWord();
            } else if (i >= 30 && i < 40) {
                if (actions == null)
                    actions = new String[10];
                actions[i - 30] = stream.readString();
                if (actions[i - 30].equalsIgnoreCase("hidden"))
                    actions[i - 30] = null;
            } else if (i == 40) {
                int k = stream.readUnsignedByte();
                int[] anIntArray76 = new int[k];
                int[] anIntArray70 = new int[k];
                for (int k1 = 0; k1 < k; k1++) {
                    anIntArray76[k1] = stream.readUnsignedWord();
                    anIntArray70[k1] = stream.readUnsignedWord();
                }

            } else if (i == 60) {
                int l = stream.readUnsignedByte();
                int[] anIntArray73 = new int[l];
                for (int l1 = 0; l1 < l; l1++)
                    anIntArray73[l1] = stream.readUnsignedWord();

            } else if (i == 90)
                stream.readUnsignedWord();
            else if (i == 91)
                stream.readUnsignedWord();
            else if (i == 92)
                stream.readUnsignedWord();
            else if (i == 93)
                aBoolean87 = false;
            else if (i == 95)
                combatLevel = stream.readUnsignedWord();
            else if (i == 97)
                anInt91 = stream.readUnsignedWord();
            else if (i == 98)
                anInt86 = stream.readUnsignedWord();
            else if (i == 99)
                aBoolean93 = true;
            else if (i == 100)
                anInt85 = stream.readSignedByte();
            else if (i == 101)
                anInt92 = stream.readSignedByte() * 5;
            else if (i == 102)
                anInt75 = stream.readUnsignedWord();
            else if (i == 103)
                anInt79 = stream.readUnsignedWord();
            else if (i == 106) {
                int anInt57 = stream.readUnsignedWord();
                if (anInt57 == 65535)
                    anInt57 = -1;
                int anInt59 = stream.readUnsignedWord();
                if (anInt59 == 65535)
                    anInt59 = -1;
                int i1 = stream.readUnsignedByte();
                int[] childrenIDs = new int[i1 + 1];
                for (int i2 = 0; i2 <= i1; i2++) {
                    childrenIDs[i2] = stream.readUnsignedWord();
                    if (childrenIDs[i2] == 65535)
                        childrenIDs[i2] = -1;
                }

            } else if (i == 107)
                aBoolean84 = false;
        } while (true);
    }

    NPCDef() {
        boundDim = 1;
        type = -1L;
    }

    public int standAnim;
    public int walkAnim;
    public int combatLevel;
    private static int cacheIndex;
    private static Buffer npcData;
    public static int totalNpcs;
    public static int newTotalNpcs;
    public String name;
    public String actions[];
    public byte boundDim;
    private static int streamIndices[];
    public long type;
    public static NPCDef cache[];
    public String examine;

}
