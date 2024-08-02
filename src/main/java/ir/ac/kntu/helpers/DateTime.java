package ir.ac.kntu.helpers;

import java.time.LocalDateTime;

public class DateTime {
    private LocalDateTime time;

    public DateTime() {
        this.time = LocalDateTime.now();
    }

    public DateTime(LocalDateTime time) {
        this.time = time;
    }

    public DateTime(String date, String time) {
        this.time = LocalDateTime.of(Integer.parseInt(date.split("/")[0]), Integer.parseInt(date.split("/")[1]),
                Integer.parseInt(date.split("/")[2]), Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
    }

    public LocalDateTime getTime() {
        return time;
    }


    public int compareTo(DateTime dateTime){
        return this.time.compareTo(dateTime.getTime());
    }

    @Override
    public String toString() {
        return time.getYear() + "/" + time.getMonthValue() + "/" + time.getDayOfMonth() + " " + time.getHour() + ":"
                + time.getMinute();
    }
}
