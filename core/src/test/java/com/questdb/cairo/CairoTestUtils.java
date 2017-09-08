package com.questdb.cairo;

import com.questdb.factory.configuration.JournalStructure;
import com.questdb.misc.FilesFacade;
import com.questdb.misc.FilesFacadeImpl;

public class CairoTestUtils {
    public static void createAllTable(CharSequence root, int partitionBy) {
        createTable(FilesFacadeImpl.INSTANCE, root, new JournalStructure("all").
                $int("int").
                $short("short").
                $byte("byte").
                $double("double").
                $float("float").
                $long("long").
                $str("str").
                $sym("sym").
                $bool("bool").
                $bin("bin").
                $date("date").partitionBy(partitionBy));
    }

    public static String createTable(FilesFacade ff, CharSequence root, JournalStructure struct) {
        String name = struct.getName();
        try {
            if (TableUtils.exists(ff, root, name) == 1) {
                TableUtils.create(ff, root, struct.build(), 509);
            } else {
                throw CairoException.instance(0).put("Table ").put(name).put(" already exists");
            }
        } finally {
            TableUtils.freeThreadLocals();
        }
        return name;
    }
}