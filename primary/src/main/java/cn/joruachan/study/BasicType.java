package cn.joruachan.study;

public class BasicType {

    public static final void floatAndDouble() {
        int intValue = 8;
        System.out.println("int类型8, 二进制表示为: " + Integer.toBinaryString(intValue));

        float floatValue = 8.45f;
        int intBits = Float.floatToIntBits(floatValue);

        System.out.println("float类型8, 二进制表示为: " + Integer.toBinaryString(intBits));

        double doubleValue = 8.45;
        long longBits = Double.doubleToLongBits(doubleValue);

        System.out.println("double类型8, 二进制表示为: " + Long.toBinaryString(longBits));

        // float 或者 double的值不要直接比较，可能值不一致但==为true；

        char charValue = '我';
        int charInt = charValue;
        System.out.println(charInt);


        byte byteValue = 2;
        short shortValue = byteValue;
        intValue = shortValue;
        long longValue = intValue;

        floatValue = charValue;

        System.out.println(floatValue);


        boolean b = true;

        floatValue = 5.12f;
        int i = Float.floatToIntBits(floatValue);
        System.out.println(Integer.toBinaryString(i));

        System.out.println(Integer.toBinaryString(129));

        longValue = Long.parseLong("11000000101000111111111111111111", 2);
        System.out.println(Double.valueOf(longValue));
    }

    // 十进制转二进制
    public static final void decimal2Binary4Float(float fValue) {
        // 转成2进制形式
        Float f = Float.valueOf(fValue);

        String sValue = f.toString();

        int dotIndex = sValue.indexOf(".");


        boolean lessThanZero = fValue < 0;

        // 整数部分
        Integer integerBeforeDot = Integer.valueOf(sValue.substring(0, dotIndex));
        if (lessThanZero) integerBeforeDot = -integerBeforeDot;

        System.out.println("整数部分，10进制表示为：" + integerBeforeDot);

        String binValueBeforeDot = Integer.toBinaryString(integerBeforeDot);
        System.out.println("整数部分，2进制表示为：" + binValueBeforeDot);

        // 小数部分
        Float floatAfterDot = Float.valueOf("0." + sValue.substring(dotIndex + 1));
        System.out.println("小数部分，10进制表示为：" + floatAfterDot);

//        String binValueAfterDot = Integer.toBinaryString(Float.floatToIntBits(floatAfterDot));
//        System.out.println(binValueAfterDot);

        // 1. 转成二进制形式
        String binaryString = binValueBeforeDot + ".";

        int recycleCount = 0;
        float temp = floatAfterDot;
        do {
            temp = temp * 2;
            if (temp >= 1.0f) {
                temp = temp - 1;

                binaryString += "1";
            } else {
                binaryString += "0";
            }
        } while (++recycleCount < 32);

        System.out.println("浮点数：" + sValue + ", 2进制表示为：" + binaryString);

        // 2. 向右移动几位，即可得到指数
        dotIndex = binaryString.indexOf(".");

        int power = dotIndex - 1;
        System.out.println("浮点数：" + sValue + ", 2进制的指数为：" + power);

        String powerBinary = Integer.toBinaryString(power + 127);
        int powerMakeCount = powerBinary.length() - 8;
        if (powerMakeCount < 0) {
            for (int i = 0; i < powerMakeCount - 1; i++) {
                powerBinary = "0" + powerBinary;
            }
        }
        System.out.println("浮点数：" + sValue + ", 2进制的8位指数表示为：" + powerBinary);

        // 3. 有效数字, 小数点前必为1，省略小数点前的那个1;
        String omitEffNum = binaryString.replace(".", "").substring(1);
        if (omitEffNum.length() > 23) omitEffNum = omitEffNum.substring(0, 23);

        String effectiveNumStr = "1." + omitEffNum;
        System.out.println("浮点数：" + sValue + ", 2进制的有效数字为：" + effectiveNumStr + ",省略后为：" + omitEffNum);

        // 4. 转成IEEE表达式，首位符号位，8位指数位，23位有效数字
        String i3eString = (lessThanZero ? "1" : "0") + powerBinary + omitEffNum;
        int i3eLen = i3eString.length();
        for (int i = 0; i < 32; i++) {

            if (i > i3eLen - 1) {
                System.out.print("0");
            } else {
                System.out.print(i3eString.substring(i, i + 1));
            }

            if (i % 8 == 7) {
                System.out.print("  ");
            }
        }
    }

    private static final int get(float value) {
        if (value * 2 >= 1.f) {
            return 1;
        } else {
            return 0;
        }
    }

    public static final int test() {
        int i = 1;
        try {
            i++;
            System.out.println("try:" + i);
            return i;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
        } finally {
            i++;
            System.out.println("finally:" + i);
            return i;
        }
    }

    public static void main(String[] args) {
//        floatAndDouble();

//        decimal2Binary4Float(-9.625f);

        System.out.println(test());
    }
}
