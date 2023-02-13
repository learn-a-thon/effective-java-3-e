package com.example.effective.item12;

public class PhoneNumber {
    private short areaCode;
    private short prefix;
    private short lineNum;

    PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "지역코드");
        this.prefix = rangeCheck(prefix, 999, "프리픽스");
        this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max) {
            throw new IllegalArgumentException(arg + " : " + val);
        }
        return (short) val;
     }

    /**
     * 전화번호 문자열 표현 반환
     * 'XXX-YYY-ZZZ"의 12자로 구성
     *
     * 전화번호의 각 부분의 값이 너무 작아 자릿수를 채울 수 없다면,
     * 앞에서부터 0으로 채워감.
     * @return
     */
    @Override
    public String toString() {
        return String.format("%3d-%03d-%04d", areaCode, prefix, lineNum);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber)o;
        return pn.lineNum == lineNum && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;

    }

    public static PhoneNumber of(String phoneNumberString) {
        String[] split = phoneNumberString.split("-");
        PhoneNumber phoneNumber = new PhoneNumber(
                Short.parseShort(split[0]),
                Short.parseShort(split[1]),
                Short.parseShort(split[2]));
        return phoneNumber;
    }

    public static void main(String[] args) {
        PhoneNumber jenny = new PhoneNumber(707, 867, 5309);
        System.out.println("제니의 번호 : " + jenny);

        PhoneNumber phoneNumber = PhoneNumber.of("707-867-5309");
        System.out.println(phoneNumber);
        System.out.println(jenny.equals(phoneNumber));
    }
}
