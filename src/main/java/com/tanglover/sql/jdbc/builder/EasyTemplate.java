package com.tanglover.sql.jdbc.builder;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class EasyTemplate {
    public static final Map<String, Cache> caches = new Hashtable();

    public static EasyTemplate _template = new EasyTemplate();

    public Cache newCache() {
        return new Cache();
    }

    public static final String make(File file, Map<String, String> params, String encode) throws Exception {
        byte[] b = readFully(file);
        String s = new String(b, encode);
        return make(s, params);
    }

    public static final String make2(File file, Map<String, String> params, String encode) throws Exception {
        String fname = file.getPath();
        byte[] b;
        if (caches.containsKey(fname)) {
            Cache c = (Cache) caches.get(fname);
            if ((c == null) || (c.lastModified < file.lastModified())) {
                c = _template.newCache();
                b = readFully(file);
                c.b = b;
                c.lastModified = file.lastModified();
                caches.put(fname, c);
            } else {
                b = c.b;
            }
        } else {
            Cache c = _template.newCache();
            b = readFully(file);
            c.b = b;
            c.lastModified = file.lastModified();
            caches.put(fname, c);
        }

        String s = new String(b, encode);
        return make(s, params);
    }

    public static final String make(String s, Map<String, String> params) throws Exception {
        Iterator it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String v = params.get(key);
            String k = String.format("${%s}", new Object[]{key});
            while (s.contains(k))
                s = s.replace(k, v);
        }
        return s;
    }

    public static final byte[] readFully(File f) throws Exception {
        if ((f == null) || (!f.exists())) {
            throw new IOException("file no exists");
        }
        int len = (int) f.length();
        byte[] b = new byte[len];
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        dis.readFully(b);
        fis.close();

        return b;
    }

    public static final Map<String, String> newMap() {
        return new HashMap();
    }

    public static void main(String[] args) throws Exception {
        String s = "C:/Java/WTK2.5.2_01/docs/api/midp/index.html";
        String str = make(new File(s), newMap(), "UTF8");
        System.out.println(str);
        String str2 = make(new File(s), newMap(), "UTF8");
        System.out.println(str2);
        String str3 = make(new File(s), newMap(), "UTF8");
        System.out.println(str3);
    }

    private class Cache {
        public byte[] b;
        public long lastModified;

        private Cache() {
        }
    }
}