package com.tanglover.sql.jdbc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TangXu
 * @create 2019-04-01 10:42
 * @description:
 */
public class MapExecutor {
    public static Map newMap() {
        return new Hashtable();
    }

    public static Map newHashMap() {
        return new HashMap();
    }

    public static Hashtable newHashtable() {
        return new Hashtable();
    }

    public static ConcurrentHashMap newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static <T> T copyValue(Map from, Map to, Object key) {
        T v = get(from, key);
        if (v == null) {
            return null;
        } else {
            to.put(key, v);
            return v;
        }
    }

    public static <T> T get(Map map, Object key) {
        return (T) map.get(key);
    }

    public static boolean getBoolean(Map map, Object key) {
        Boolean v = (Boolean) map.get(key);
        return v == null ? false : v;
    }

    public static byte getByte(Map map, Object key) {
        Byte v = (Byte) map.get(key);
        return v == null ? 0 : v;
    }

    public static short getShort(Map map, Object key) {
        Short v = (Short) map.get(key);
        return v == null ? 0 : v;
    }

    public static int getInt(Map map, Object key) {
        Integer v = (Integer) map.get(key);
        return v == null ? 0 : v;
    }

    public static long getLong(Map map, Object key) {
        Long v = (Long) map.get(key);
        return v == null ? 0L : v;
    }

    public static float getFloat(Map map, Object key) {
        Float v = (Float) map.get(key);
        return v == null ? 0.0F : v;
    }

    public static double getDouble(Map map, Object key) {
        Double v = (Double) map.get(key);
        return v == null ? 0.0D : v;
    }

    public static BigInteger getBigInteger(Map map, Object key) {
        return (BigInteger) map.get(key);
    }

    public static BigDecimal getBigDecimal(Map map, Object key) {
        return (BigDecimal) map.get(key);
    }

    public static String getString(Map map, Object key) {
        return (String) map.get(key);
    }

    public static Date getDate(Map map, Object key) {
        return (Date) map.get(key);
    }

    public static byte[] getByteArray(Map map, Object key) {
        return (byte[]) ((byte[]) map.get(key));
    }

    public static Map toMap(List l) {
        Map ret = newMap();
        if (l != null && !l.isEmpty()) {
            for (int i = 0; i < l.size(); ++i) {
                Object o = l.get(i);
                ret.put(i, o);
            }

            return ret;
        } else {
            return ret;
        }
    }

    public static Map toHashMap(Map map) {
        Map ret = newHashMap();
        Iterator it = map.keySet().iterator();

        while (it.hasNext()) {
            Object key = it.next();
            Object var = map.get(key);
            ret.put(key, var);
        }

        return ret;
    }

    public static Map toHashtable(Map map) {
        Map ret = newHashtable();
        Iterator it = map.keySet().iterator();

        while (it.hasNext()) {
            Object key = it.next();
            Object var = map.get(key);
            ret.put(key, var);
        }

        return ret;
    }

    public static Map toConcurrentHashMap(Map map) {
        Map ret = newConcurrentHashMap();
        Iterator it = map.keySet().iterator();

        while (it.hasNext()) {
            Object key = it.next();
            Object var = map.get(key);
            ret.put(key, var);
        }

        return ret;
    }

    public static List keyToList(Map map) {
        List list = ListExecutor.newList();
        list.addAll(map.keySet());
        return list;
    }

    public static List valueToList(Map map) {
        List list = ListExecutor.newList();
        list.addAll(map.values());
        return list;
    }

    public static Map toMap(Object[] array) {
        if (array == null) {
            return null;
        } else {
            Map map = new HashMap((int) ((double) array.length * 1.5D));

            for (int i = 0; i < array.length; ++i) {
                Object object = array[i];
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) object;
                    map.put(entry.getKey(), entry.getValue());
                } else {
                    if (!(object instanceof Object[])) {
                        throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
                    }

                    Object[] entry = (Object[]) ((Object[]) object);
                    if (entry.length < 2) {
                        throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
                    }

                    map.put(entry[0], entry[1]);
                }
            }

            return map;
        }
    }

    public static Map toHashMap(Object[] array) {
        Map m = toMap(array);
        return toHashMap(m);
    }

    public static Map toHashtable(Object[] array) {
        Map m = toMap(array);
        return toHashtable(m);
    }

    public static Map toConcurrentHashMap(Object[] array) {
        Map m = toMap(array);
        return toConcurrentHashMap(m);
    }

    public static Map propertiesToMap(String s) {
        Properties p = new Properties();

        try {
            StringReader sr = new StringReader(s);
            BufferedReader br = new BufferedReader(sr);
            p.load(br);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return p;
    }

    public static String getString(String smap, Object key) {
        try {
            smap = smap == null ? "" : smap;
            Map m = propertiesToMap(smap);
            String var = (String) m.get(key);
            return var;
        } catch (Exception var4) {
            return "";
        }
    }

    public static String[] getString(String smap, Object key, String split) {
        String svar = getString(smap, key);
        return svar.split(split);
    }

    public static String setString(String smap, String key, String var) {
        StringBuffer sb = new StringBuffer();

        try {
            smap = smap == null ? "" : smap;
            Map m = propertiesToMap(smap);
            m.put(key, var);
            int size = m.size();
            int p = 0;
            Iterator keys = m.keySet().iterator();

            while (keys.hasNext()) {
                ++p;
                Object k = keys.next();
                Object v = m.get(k);
                sb.append(k).append("=").append(v);
                if (p < size) {
                    sb.append("\n");
                }
            }

            return sb.toString();
        } catch (Exception var10) {
            return "";
        }
    }

    public static String setString(String smap, String key, String[] vars, String split) {
        StringBuffer sb = new StringBuffer();
        int length = vars.length;
        int p = 0;
        String[] var7 = vars;
        int var8 = vars.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            String v = var7[var9];
            ++p;
            sb.append(v);
            if (p < length) {
                sb.append(split);
            }
        }

        return setString(smap, key, sb.toString());
    }

    public static String setString(String smap, int key, String var) {
        String skey = String.valueOf(key);
        return setString(smap, skey, var);
    }

    public static String setString(String smap, int key, String[] vars, String split) {
        String skey = String.valueOf(key);
        return setString(smap, skey, vars, split);
    }

    public static int getInt(String smap, String key) {
        String var = getString((String) smap, key);
        return NumberExecutor.stringToInt(var);
    }

    public static int[] getInt(String smap, String key, String split) {
        String var = getString((String) smap, key);
        String[] vars = var.split(split);
        int length = vars.length;
        if (length <= 0) {
            return new int[0];
        } else {
            int[] ret = new int[length];
            int p = 0;
            String[] var8 = vars;
            int var9 = vars.length;

            for (int var10 = 0; var10 < var9; ++var10) {
                String string = var8[var10];
                int i = NumberExecutor.stringToInt(string);
                ret[p] = i;
                ++p;
            }

            return ret;
        }
    }

    public static int getInt(String smap, int key) {
        String skey = String.valueOf(key);
        return getInt(smap, skey);
    }

    public static int[] getInt(String smap, int key, String split) {
        String skey = String.valueOf(key);
        return getInt(smap, skey, split);
    }

    public static String setInt(String smap, String key, int i) {
        String var = String.valueOf(i);
        return setString(smap, key, var);
    }

    public static String setInt(String smap, String key, int[] is, String split) {
        String[] vars = new String[is.length];
        int p = 0;
        int[] var6 = is;
        int var7 = is.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            int i = var6[var8];
            String var = String.valueOf(i);
            vars[p] = var;
            ++p;
        }

        return setString(smap, key, vars, split);
    }

    public static String setInt(String smap, int key, int i) {
        String skey = String.valueOf(key);
        return setInt(smap, skey, i);
    }

    public static String setInt(String smap, int key, int[] is, String split) {
        String skey = String.valueOf(key);
        return setInt(smap, skey, is, split);
    }

    public static final Iterator iterator(String smap) {
        try {
            smap = smap == null ? "" : smap;
            Map m = propertiesToMap(smap);
            return m.keySet().iterator();
        } catch (Exception var2) {
            return null;
        }
    }

    public static void main(String[] args) {
        Map colorMap = toMap((Object[]) (new Object[][]{{"RED", 16711680}, {"GREEN", 65280}, {"BLUE", 255}}));
        System.out.println(colorMap);
        int[] vars = new int[]{1, 2, 3, 4, 5, 6};
        String s = "";
        s = setInt(s, "1", 111);
        s = setString(s, "2", "222");
        s = setString(s, 3, "333");
        s = setString(s, "4", "444");
        s = setInt(s, 5, 555);
        s = setInt(s, 6, vars, ",");
        System.out.println(s);
        System.out.println(getInt(s, "1"));
        System.out.println(getInt(s, "2"));
        System.out.println(getInt(s, 3));
        System.out.println(getInt(s, "4"));
        System.out.println(getInt(s, "5"));
        int[] vars2 = getInt(s, 6, ",");
        int[] var5 = vars2;
        int var6 = vars2.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            int i = var5[var7];
            System.out.print(i + ",");
        }

        System.out.println("-------------------");
        Iterator it = iterator(s);

        while (it.hasNext()) {
            String key = (String) it.next();
            String var = getString((String) s, key);
            System.out.println(key + " = " + var);
        }

    }
}