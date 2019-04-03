package com.tanglover.sql.jdbc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @author TangXu
 * @create 2019-04-01 10:46
 * @description:
 */
public class ListExecutor {
    private static Random rnd = new Random(System.currentTimeMillis());

    public ListExecutor() {
    }

    public static final List newList() {
        return new Vector();
    }

    public static final List newArrayList() {
        return new ArrayList();
    }

    public static final List newLinkedList() {
        return new LinkedList();
    }

    public static final List newVector() {
        return new Vector();
    }

    public static final <T> T get(List list, int index) {
        return (T) list.get(index);
    }

    public static final List toList(String s) {
        List l = new Vector();
        StringReader sr = new StringReader(s);
        BufferedReader br = new BufferedReader(sr);

        try {
            while (true) {
                String v = br.readLine();
                if (v == null) {
                    break;
                }

                l.add(v);
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return l;
    }

    public static final List toArrayList(List list) {
        List ret = newArrayList();
        Iterator var2 = list.iterator();

        while (var2.hasNext()) {
            Object e = var2.next();
            ret.add(e);
        }

        return ret;
    }

    public static final List toLinkedList(List list) {
        List ret = newLinkedList();
        Iterator var2 = list.iterator();

        while (var2.hasNext()) {
            Object e = var2.next();
            ret.add(e);
        }

        return ret;
    }

    public static final List toVector(List list) {
        List ret = newVector();
        Iterator var2 = list.iterator();

        while (var2.hasNext()) {
            Object e = var2.next();
            ret.add(e);
        }

        return ret;
    }

    public static final List toArrayList(Object[] array) {
        if (array == null) {
            return null;
        } else {
            List list = newArrayList();
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object e = var2[var4];
                list.add(e);
            }

            return list;
        }
    }

    public static final List toLinkedList(Object[] array) {
        if (array == null) {
            return null;
        } else {
            List list = newLinkedList();
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object e = var2[var4];
                list.add(e);
            }

            return list;
        }
    }

    public static List keyToList(Map map) {
        List list = newList();
        list.addAll(map.keySet());
        return list;
    }

    public static List valueToList(Map map) {
        List list = newList();
        list.addAll(map.values());
        return list;
    }

    public static final List toVector(Object[] array) {
        if (array == null) {
            return null;
        } else {
            List list = newVector();
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object e = var2[var4];
                list.add(e);
            }

            return list;
        }
    }

    public static final List copy(List src) {
        List dest = newVector();
        Collections.copy(dest, src);
        return dest;
    }

    public static final List reverse(List src) {
        Collections.reverse(src);
        return src;
    }

    public static final List rotate(List src, int distance) {
        Collections.rotate(src, distance);
        return src;
    }

    public static final List shuffle(List src) {
        Collections.shuffle(src);
        return src;
    }

    public static final List shuffleRnd(List src) {
        Collections.shuffle(src, rnd);
        return src;
    }

    public static final List sort(List src) {
        Collections.sort(src);
        return src;
    }

    public static final List sort2(List src, Comparator comparator) {
        Collections.sort(src, comparator);
        return src;
    }

    public static final List<Map> sortIntMap(List<Map> m1, final Object key) {
        Collections.sort(m1, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                int i1 = (Integer) o1.get(key);
                int i2 = (Integer) o2.get(key);
                return i1 - i2;
            }
        });
        return m1;
    }

    public static final List<Map> sortLongMap(List<Map> m1, final Object key) {
        Collections.sort(m1, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                long i1 = (Long) o1.get(key);
                long i2 = (Long) o2.get(key);
                return i1 > i2 ? 1 : -1;
            }
        });
        return m1;
    }

    public static final List<Byte> distinctByte(List<Byte> vars) {
        List<Byte> ret = new Vector();
        Map<Byte, Byte> mvars = new Hashtable();
        Iterator var3 = vars.iterator();

        while (var3.hasNext()) {
            Byte v = (Byte) var3.next();
            mvars.put(v, v);
        }

        ret.addAll(mvars.values());
        return ret;
    }

    public static final List<Short> distinctShort(List<Short> vars) {
        List<Short> ret = new Vector();
        Map<Short, Short> mvars = new Hashtable();
        Iterator var3 = vars.iterator();

        while (var3.hasNext()) {
            Short v = (Short) var3.next();
            mvars.put(v, v);
        }

        ret.addAll(mvars.values());
        return ret;
    }

    public static final List<Integer> distinctInteger(List<Integer> vars) {
        List<Integer> ret = new Vector();
        Map<Integer, Integer> mvars = new Hashtable();
        Iterator var3 = vars.iterator();

        while (var3.hasNext()) {
            Integer v = (Integer) var3.next();
            mvars.put(v, v);
        }

        ret.addAll(mvars.values());
        return ret;
    }

    public static final List<Long> distinctLong(List<Long> vars) {
        List<Long> ret = new Vector();
        Map<Long, Long> mvars = new Hashtable();
        Iterator var3 = vars.iterator();

        while (var3.hasNext()) {
            Long v = (Long) var3.next();
            mvars.put(v, v);
        }

        ret.addAll(mvars.values());
        return ret;
    }

    public static final List<String> distinctString(List<String> vars) {
        List<String> ret = new Vector();
        Map<String, String> mvars = new Hashtable();
        Iterator var3 = vars.iterator();

        while (var3.hasNext()) {
            String v = (String) var3.next();
            mvars.put(v, v);
        }

        ret.addAll(mvars.values());
        return ret;
    }

    public static final String add(String list, String v, String split) {
        list = list == null ? "" : list;
        if (v != null && !v.trim().isEmpty()) {
            v = v.trim();
            StringBuffer sb = new StringBuffer();
            String[] lists = list.split(split);
            String[] var5 = lists;
            int var6 = lists.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String s = var5[var7];
                s = s.trim();
                if (!s.isEmpty()) {
                    sb.append(s);
                    sb.append(split);
                }
            }

            sb.append(v);
            sb.append(split);
            return sb.toString();
        } else {
            return list;
        }
    }

    public static final String add(String list, int n, String split) {
        String v = String.valueOf(n);
        return add(list, v, split);
    }

    public static final String set(String list, int i, String v, String split) {
        list = list == null ? "" : list;
        if (v != null && !v.trim().isEmpty()) {
            if (i <= 0) {
                return list;
            } else {
                v = v.trim();
                StringBuffer sb = new StringBuffer();
                String[] lists = list.split(split);
                int length = lists.length;
                if (i >= length) {
                    return list;
                } else {
                    int p = 0;
                    String[] var8 = lists;
                    int var9 = lists.length;

                    for (int var10 = 0; var10 < var9; ++var10) {
                        String s = var8[var10];
                        ++p;
                        if (p == i) {
                            s = v;
                        }

                        s = s.trim();
                        if (!s.isEmpty()) {
                            sb.append(s);
                            sb.append(split);
                        }
                    }

                    return sb.toString();
                }
            }
        } else {
            return list;
        }
    }

    public static final String set(String list, int i, int n, String split) {
        String v = String.valueOf(n);
        return set(list, i, v, split);
    }

    public static final String insert(String list, int i, String v, String split) {
        list = list == null ? "" : list;
        if (v != null && !v.trim().isEmpty()) {
            v = v.trim();
            StringBuffer sb = new StringBuffer();
            String[] lists = list.split(split);
            int length = lists.length;
            int var9;
            if (i <= 0) {
                sb.append(v);
                sb.append(split);
                String[] var7 = lists;
                int var8 = lists.length;

                for (var9 = 0; var9 < var8; ++var9) {
                    String s = var7[var9];
                    s = s.trim();
                    if (!s.isEmpty()) {
                        sb.append(s);
                        sb.append(split);
                    }
                }
            } else {
                if (i >= length) {
                    return add(list, v, split);
                }

                int p = 0;
                String[] var13 = lists;
                var9 = lists.length;

                for (int var14 = 0; var14 < var9; ++var14) {
                    String s = var13[var14];
                    s = s.trim();
                    if (!s.isEmpty()) {
                        sb.append(s);
                        sb.append(split);
                        ++p;
                        if (p == i) {
                            sb.append(v);
                            sb.append(split);
                        }
                    }
                }
            }

            return sb.toString();
        } else {
            return list;
        }
    }

    public static final String insert(String list, int i, int n, String split) {
        String v = String.valueOf(n);
        return insert(list, i, v, split);
    }

    public static final String remove(String list, int i, String split) {
        list = list == null ? "" : list;
        if (i <= 0) {
            return list;
        } else {
            StringBuffer sb = new StringBuffer();
            String[] lists = list.split(split);
            int length = lists.length;
            if (i >= length) {
                return list;
            } else {
                int p = 0;
                String[] var7 = lists;
                int var8 = lists.length;

                for (int var9 = 0; var9 < var8; ++var9) {
                    String s = var7[var9];
                    ++p;
                    if (p != i) {
                        s = s.trim();
                        if (!s.isEmpty()) {
                            sb.append(s);
                            sb.append(split);
                        }
                    }
                }

                return sb.toString();
            }
        }
    }

    public static final String getString(String list, int i, String split) {
        list = list == null ? "" : list;
        if (i <= 0) {
            return "";
        } else {
            String[] lists = list.split(split);
            return i >= lists.length ? "" : lists[i];
        }
    }

    public static final int getInt(String list, int i, String split) {
        String v = getString(list, i, split);
        return NumberExecutor.stringToInt(v);
    }

    public static final int indexOf(String list, String v, String split) {
        list = list == null ? "" : list;
        String[] lists = list.split(split);
        int p = 0;
        String[] var5 = lists;
        int var6 = lists.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String s = var5[var7];
            if (s.equals(v)) {
                return p;
            }

            ++p;
        }

        return -1;
    }

    public static final int indexOf(String list, int n, String split) {
        String v = String.valueOf(n);
        return indexOf(list, v, split);
    }

    public static final Iterator iterator(String list, String split) {
        List l = newList();
        list = list == null ? "" : list;
        String[] lists = list.split(split);
        String[] var4 = lists;
        int var5 = lists.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String s = var4[var6];
            l.add(s);
        }

        return l.iterator();
    }

    public static void main(String[] args) {
        String list = "";
        list = add(list, "111", ",");
        list = add(list, "222", ",");
        list = add(list, "333", ",");
        list = add(list, "555", ",");
        list = remove(list, 2, ",");
        list = set(list, 3, "aaa", ",");
        list = insert(list, 1, "000", ",");
        System.out.println(list);
        int n1 = getInt(list, 2, ",");
        System.out.println(n1);
        System.out.println(indexOf(list, 111, ","));
        System.out.println("------------------");
        Iterator it = iterator(list, ",");

        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }
}