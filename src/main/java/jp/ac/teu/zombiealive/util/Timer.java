package jp.ac.teu.zombiealive.util;

import java.util.Calendar;

public class Timer {

    Calendar timeUp = Calendar.getInstance();

    public Timer() {
        timeUp.add(Calendar.MINUTE, 15);//Timerクラス初期化の15分後にセット
    }

    public boolean getTimer() {//時間がたったかどうかの判断
        Calendar start = Calendar.getInstance();
        boolean time = start.before(timeUp);
        return time;
    }
}
