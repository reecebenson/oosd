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
    /**
     * Variables
     */
    int weeks = 0;
    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    /**
     * @name    LeaseDuration
     * @desc    Default Constructor - converts Seconds into specified values (weeks, days, hours, minutes, seconds)
     * 
     * @param   s 
     */
    public LeaseDuration(int s) {
        this.weeks = s / 604800;
        this.days = (s % 604800) / 86400;
        this.hours = ((s % 604800) % 86400) / 3600;
        this.minutes = (((s % 604800) % 86400) % 3600) / 60;
        this.seconds = (((s % 604800) % 86400) % 3600) % 60;
    }

    /**
     * @name    getWeeks
     * @desc    Retrieve the Weeks Left
     * 
     * @return  int
     */
    public int getWeeks() {
        return weeks;
    }

    /**
     * @name    getDays
     * @desc    Retrieve the Days Left
     * 
     * @return  int
     */
    public int getDays() {
        return days;
    }

    /**
     * @name    getHours
     * @desc    Retrieve the Hours Left
     * 
     * @return  int
     */
    public int getHours() {
        return hours;
    }

    /**
     * @name    getMinutes
     * @desc    Retrieve the Minutes Left
     * 
     * @return  int
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @name    getSeconds
     * @desc    Retrieve the Seconds Left
     * 
     * @return  int
     */
    public int getSeconds() {
        return seconds;
    }
}