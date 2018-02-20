/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

/**
 *
 * @author simpl_000
 */
public class LeaseDuration {
    int weeks = 0;
    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    public LeaseDuration(int s) {
        this.weeks = s / 604800;
        this.days = (s % 604800) / 86400;
        this.hours = ((s % 604800) % 86400) / 3600;
        this.minutes = (((s % 604800) % 86400) % 3600) / 60;
        this.seconds = (((s % 604800) % 86400) % 3600) % 60;
    }

    public int getWeeks() {
        return weeks;
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }
}