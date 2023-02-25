package com.example.effective.item16;

public final class Time {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;

    // public 클래스의 필드가 불변이라면 직접 노출할 때의 단점은 줄어들지만
    // 결코 좋은 코드라고 할 수는 없다.
    public final int hour;
    public final int minute;

    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
}
