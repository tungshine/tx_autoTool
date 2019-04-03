package com.tanglover.sql.jdbc.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * @author TangXu
 * @create 2019-04-01 10:45
 * @description:
 */
public class NumberExecutor {

    static DecimalFormat _decimalFormat = new DecimalFormat(".00");

    public NumberExecutor() {
    }

    public static byte stringToByte(String s, byte v) {
        try {
            return Byte.parseByte(s);
        } catch (Exception var3) {
            return v;
        }
    }

    public static byte stringToByte(String s) {
        return stringToByte(s, (byte) 0);
    }

    public static byte[] stringToByte(String[] v) {
        byte[] r = new byte[v.length];
        int n = 0;
        String[] var3 = v;
        int var4 = v.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            r[n] = stringToByte(s);
            ++n;
        }

        return r;
    }

    public static short stringToShort(String s, short v) {
        try {
            return Short.parseShort(s);
        } catch (Exception var3) {
            return v;
        }
    }

    public static short stringToShort(String s) {
        return stringToShort(s, (short) 0);
    }

    public static short[] stringToShort(String[] v) {
        short[] r = new short[v.length];
        int n = 0;
        String[] var3 = v;
        int var4 = v.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            r[n] = stringToShort(s);
            ++n;
        }

        return r;
    }

    public static int stringToInt(String s, int v) {
        try {
            return Integer.parseInt(s);
        } catch (Exception var3) {
            return v;
        }
    }

    public static int stringToInt(String s) {
        return stringToInt(s, 0);
    }

    public static int[] stringToInt(String[] v) {
        int[] r = new int[v.length];
        int n = 0;
        String[] var3 = v;
        int var4 = v.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            r[n] = stringToInt(s);
            ++n;
        }

        return r;
    }

    public static long stringToLong(String s, long v) {
        try {
            return Long.parseLong(s);
        } catch (Exception var4) {
            return v;
        }
    }

    public static long stringToLong(String s) {
        return stringToLong(s, 0L);
    }

    public static long[] stringToLong(String[] v) {
        long[] r = new long[v.length];
        int n = 0;
        String[] var3 = v;
        int var4 = v.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            r[n] = stringToLong(s);
            ++n;
        }

        return r;
    }

    public static float stringToFloat(String s, float v) {
        try {
            return Float.parseFloat(s);
        } catch (Exception var3) {
            return v;
        }
    }

    public static float stringToFloat(String s) {
        return stringToFloat(s, 0.0F);
    }

    public static float[] stringToFloat(String[] v) {
        float[] r = new float[v.length];
        int n = 0;
        String[] var3 = v;
        int var4 = v.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            r[n] = stringToFloat(s);
            ++n;
        }

        return r;
    }

    public static double stringToDouble(String s, double v) {
        try {
            return Double.parseDouble(s);
        } catch (Exception var4) {
            return v;
        }
    }

    public static double stringToDouble(String s) {
        return stringToDouble(s, 0.0D);
    }

    public static double[] stringToDouble(String[] v) {
        double[] r = new double[v.length];
        int n = 0;
        String[] var3 = v;
        int var4 = v.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            r[n] = stringToDouble(s);
            ++n;
        }

        return r;
    }

    private static int read(InputStream input) throws IOException {
        int value = input.read();
        if (-1 == value) {
            throw new EOFException("Unexpected EOF reached");
        } else {
            return value;
        }
    }

    public static void writeShort(byte[] data, int offset, short value) {
        data[offset + 0] = (byte) (value >> 8 & 255);
        data[offset + 1] = (byte) (value >> 0 & 255);
    }

    public static short readShort(byte[] data, int offset) {
        return (short) (((data[offset + 0] & 255) << 8) + ((data[offset + 1] & 255) << 0));
    }

    public static int readUnsignedShort(byte[] data, int offset) {
        return ((data[offset + 0] & 255) << 8) + ((data[offset + 1] & 255) << 0);
    }

    public static void writeUnsignedShort(byte[] data, int offset, int value) {
        data[offset + 0] = (byte) (value >> 24 & 255);
        data[offset + 1] = (byte) (value >> 16 & 255);
    }

    public static void writeInt(byte[] data, int offset, int value) {
        data[offset + 0] = (byte) (value >> 24 & 255);
        data[offset + 1] = (byte) (value >> 16 & 255);
        data[offset + 2] = (byte) (value >> 8 & 255);
        data[offset + 3] = (byte) (value >> 0 & 255);
    }

    public static int readInt(byte[] data, int offset) {
        return ((data[offset + 0] & 255) << 24) + ((data[offset + 1] & 255) << 16) + ((data[offset + 2] & 255) << 8) + ((data[offset + 3] & 255) << 0);
    }

    public static void writeLong(byte[] data, int offset, long value) {
        data[offset + 0] = (byte) ((int) (value >> 56 & 255L));
        data[offset + 1] = (byte) ((int) (value >> 48 & 255L));
        data[offset + 2] = (byte) ((int) (value >> 40 & 255L));
        data[offset + 3] = (byte) ((int) (value >> 32 & 255L));
        data[offset + 4] = (byte) ((int) (value >> 24 & 255L));
        data[offset + 5] = (byte) ((int) (value >> 16 & 255L));
        data[offset + 6] = (byte) ((int) (value >> 8 & 255L));
        data[offset + 7] = (byte) ((int) (value >> 0 & 255L));
    }

    public static long readLong(byte[] data, int offset) {
        long high = (long) (((data[offset + 0] & 255) << 24) + ((data[offset + 1] & 255) << 16) + ((data[offset + 2] & 255) << 8) + ((data[offset + 3] & 255) << 0));
        long low = (long) (((data[offset + 4] & 255) << 24) + ((data[offset + 5] & 255) << 16) + ((data[offset + 6] & 255) << 8) + ((data[offset + 7] & 255) << 0));
        return (high << 32) + (4294967295L & low);
    }

    public static void writeFloat(byte[] data, int offset, float value) {
        writeInt(data, offset, Float.floatToIntBits(value));
    }

    public static float readFloat(byte[] data, int offset) {
        return Float.intBitsToFloat(readInt(data, offset));
    }

    public static void writeDouble(byte[] data, int offset, double value) {
        writeLong(data, offset, Double.doubleToLongBits(value));
    }

    public static double readDouble(byte[] data, int offset) {
        return Double.longBitsToDouble(readLong(data, offset));
    }

    public static void writeShort(OutputStream output, short value) throws IOException {
        output.write((byte) (value >> 8 & 255));
        output.write((byte) (value >> 0 & 255));
    }

    public static short readShort(InputStream input) throws IOException {
        return (short) (((read(input) & 255) << 8) + ((read(input) & 255) << 0));
    }

    public static void writeInt(OutputStream output, int value) throws IOException {
        output.write((byte) (value >> 24 & 255));
        output.write((byte) (value >> 16 & 255));
        output.write((byte) (value >> 8 & 255));
        output.write((byte) (value >> 0 & 255));
    }

    public static int readInt(InputStream input) throws IOException {
        int value1 = read(input);
        int value2 = read(input);
        int value3 = read(input);
        int value4 = read(input);
        return ((value1 & 255) << 24) + ((value2 & 255) << 16) + ((value3 & 255) << 8) + ((value4 & 255) << 0);
    }

    public static void writeLong(OutputStream output, long value) throws IOException {
        output.write((byte) ((int) (value >> 56 & 255L)));
        output.write((byte) ((int) (value >> 48 & 255L)));
        output.write((byte) ((int) (value >> 40 & 255L)));
        output.write((byte) ((int) (value >> 32 & 255L)));
        output.write((byte) ((int) (value >> 24 & 255L)));
        output.write((byte) ((int) (value >> 16 & 255L)));
        output.write((byte) ((int) (value >> 8 & 255L)));
        output.write((byte) ((int) (value >> 0 & 255L)));
    }

    public static long readLong(InputStream input) throws IOException {
        byte[] bytes = new byte[8];

        for (int i = 0; i < 8; ++i) {
            bytes[i] = (byte) read(input);
        }

        return readLong(bytes, 0);
    }

    public static void writeFloat(OutputStream output, float value) throws IOException {
        writeInt(output, Float.floatToIntBits(value));
    }

    public static float readFloat(InputStream input) throws IOException {
        return Float.intBitsToFloat(readInt(input));
    }

    public static void writeDouble(OutputStream output, double value) throws IOException {
        writeLong(output, Double.doubleToLongBits(value));
    }

    public static double readDouble(InputStream input) throws IOException {
        return Double.longBitsToDouble(readLong(input));
    }

    public static int readUnsignedShort(InputStream input) throws IOException {
        int value1 = read(input);
        int value2 = read(input);
        return ((value1 & 255) << 8) + ((value2 & 255) << 0);
    }

    public static void writeUnsignedShort(OutputStream os, int value) throws IOException {
        byte[] data = new byte[]{(byte) (value >> 8 & 255), (byte) (value >> 0 & 255)};
        os.write(data);
    }

    public static boolean isByte(String s) {
        try {
            Byte.parseByte(s);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isShort(String s) {
        try {
            Short.parseShort(s);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static String formatDouble(double s) {
        return _decimalFormat.format(Double.toString(s));
    }

    public static String fix6Int(int v) {
        return String.format("%06d", v);
    }

    public static short swapShort(short value) {
        return (short) (((value >> 0 & 255) << 8) + ((value >> 8 & 255) << 0));
    }

    public static int swapInteger(int value) {
        return ((value >> 0 & 255) << 24) + ((value >> 8 & 255) << 16) + ((value >> 16 & 255) << 8) + ((value >> 24 & 255) << 0);
    }

    public static long swapLong(long value) {
        return ((value >> 0 & 255L) << 56) + ((value >> 8 & 255L) << 48) + ((value >> 16 & 255L) << 40) + ((value >> 24 & 255L) << 32) + ((value >> 32 & 255L) << 24) + ((value >> 40 & 255L) << 16) + ((value >> 48 & 255L) << 8) + ((value >> 56 & 255L) << 0);
    }

    public static float swapFloat(float value) {
        return Float.intBitsToFloat(swapInteger(Float.floatToIntBits(value)));
    }

    public static double swapDouble(double value) {
        return Double.longBitsToDouble(swapLong(Double.doubleToLongBits(value)));
    }

    public static void writeSwappedShort(byte[] data, int offset, short value) {
        data[offset + 0] = (byte) (value >> 0 & 255);
        data[offset + 1] = (byte) (value >> 8 & 255);
    }

    public static short readSwappedShort(byte[] data, int offset) {
        return (short) (((data[offset + 0] & 255) << 0) + ((data[offset + 1] & 255) << 8));
    }

    public static int readSwappedUnsignedShort(byte[] data, int offset) {
        return ((data[offset + 0] & 255) << 0) + ((data[offset + 1] & 255) << 8);
    }

    public static void writeSwappedInteger(byte[] data, int offset, int value) {
        data[offset + 0] = (byte) (value >> 0 & 255);
        data[offset + 1] = (byte) (value >> 8 & 255);
        data[offset + 2] = (byte) (value >> 16 & 255);
        data[offset + 3] = (byte) (value >> 24 & 255);
    }

    public static int readSwappedInteger(byte[] data, int offset) {
        return ((data[offset + 0] & 255) << 0) + ((data[offset + 1] & 255) << 8) + ((data[offset + 2] & 255) << 16) + ((data[offset + 3] & 255) << 24);
    }

    public static long readSwappedUnsignedInteger(byte[] data, int offset) {
        long low = (long) (((data[offset + 0] & 255) << 0) + ((data[offset + 1] & 255) << 8) + ((data[offset + 2] & 255) << 16));
        long high = (long) (data[offset + 3] & 255);
        return (high << 24) + (4294967295L & low);
    }

    public static void writeSwappedLong(byte[] data, int offset, long value) {
        data[offset + 0] = (byte) ((int) (value >> 0 & 255L));
        data[offset + 1] = (byte) ((int) (value >> 8 & 255L));
        data[offset + 2] = (byte) ((int) (value >> 16 & 255L));
        data[offset + 3] = (byte) ((int) (value >> 24 & 255L));
        data[offset + 4] = (byte) ((int) (value >> 32 & 255L));
        data[offset + 5] = (byte) ((int) (value >> 40 & 255L));
        data[offset + 6] = (byte) ((int) (value >> 48 & 255L));
        data[offset + 7] = (byte) ((int) (value >> 56 & 255L));
    }

    public static long readSwappedLong(byte[] data, int offset) {
        long low = (long) (((data[offset + 0] & 255) << 0) + ((data[offset + 1] & 255) << 8) + ((data[offset + 2] & 255) << 16) + ((data[offset + 3] & 255) << 24));
        long high = (long) (((data[offset + 4] & 255) << 0) + ((data[offset + 5] & 255) << 8) + ((data[offset + 6] & 255) << 16) + ((data[offset + 7] & 255) << 24));
        return (high << 32) + (4294967295L & low);
    }

    public static void writeSwappedFloat(byte[] data, int offset, float value) {
        writeSwappedInteger(data, offset, Float.floatToIntBits(value));
    }

    public static float readSwappedFloat(byte[] data, int offset) {
        return Float.intBitsToFloat(readSwappedInteger(data, offset));
    }

    public static void writeSwappedDouble(byte[] data, int offset, double value) {
        writeSwappedLong(data, offset, Double.doubleToLongBits(value));
    }

    public static double readSwappedDouble(byte[] data, int offset) {
        return Double.longBitsToDouble(readSwappedLong(data, offset));
    }

    public static void writeSwappedShort(OutputStream output, short value) throws IOException {
        output.write((byte) (value >> 0 & 255));
        output.write((byte) (value >> 8 & 255));
    }

    public static short readSwappedShort(InputStream input) throws IOException {
        return (short) (((read(input) & 255) << 0) + ((read(input) & 255) << 8));
    }

    public static int readSwappedUnsignedShort(InputStream input) throws IOException {
        int value1 = read(input);
        int value2 = read(input);
        return ((value1 & 255) << 0) + ((value2 & 255) << 8);
    }

    public static void writeSwappedInteger(OutputStream output, int value) throws IOException {
        output.write((byte) (value >> 0 & 255));
        output.write((byte) (value >> 8 & 255));
        output.write((byte) (value >> 16 & 255));
        output.write((byte) (value >> 24 & 255));
    }

    public static int readSwappedInteger(InputStream input) throws IOException {
        int value1 = read(input);
        int value2 = read(input);
        int value3 = read(input);
        int value4 = read(input);
        return ((value1 & 255) << 0) + ((value2 & 255) << 8) + ((value3 & 255) << 16) + ((value4 & 255) << 24);
    }

    public static long readSwappedUnsignedInteger(InputStream input) throws IOException {
        int value1 = read(input);
        int value2 = read(input);
        int value3 = read(input);
        int value4 = read(input);
        long low = (long) (((value1 & 255) << 0) + ((value2 & 255) << 8) + ((value3 & 255) << 16));
        long high = (long) (value4 & 255);
        return (high << 24) + (4294967295L & low);
    }

    public static void writeSwappedLong(OutputStream output, long value) throws IOException {
        output.write((byte) ((int) (value >> 0 & 255L)));
        output.write((byte) ((int) (value >> 8 & 255L)));
        output.write((byte) ((int) (value >> 16 & 255L)));
        output.write((byte) ((int) (value >> 24 & 255L)));
        output.write((byte) ((int) (value >> 32 & 255L)));
        output.write((byte) ((int) (value >> 40 & 255L)));
        output.write((byte) ((int) (value >> 48 & 255L)));
        output.write((byte) ((int) (value >> 56 & 255L)));
    }

    public static long readSwappedLong(InputStream input) throws IOException {
        byte[] bytes = new byte[8];

        for (int i = 0; i < 8; ++i) {
            bytes[i] = (byte) read(input);
        }

        return readSwappedLong(bytes, 0);
    }

    public static void writeSwappedFloat(OutputStream output, float value) throws IOException {
        writeSwappedInteger(output, Float.floatToIntBits(value));
    }

    public static float readSwappedFloat(InputStream input) throws IOException {
        return Float.intBitsToFloat(readSwappedInteger(input));
    }

    public static void writeSwappedDouble(OutputStream output, double value) throws IOException {
        writeSwappedLong(output, Double.doubleToLongBits(value));
    }

    public static double readSwappedDouble(InputStream input) throws IOException {
        return Double.longBitsToDouble(readSwappedLong(input));
    }
}