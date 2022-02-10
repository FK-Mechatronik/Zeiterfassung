import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Nur zum austesten der Funktioen
 * TODO : im funtionsfähigen Zustand löschen
 */
public class testeTasks {





    public static void main(String[] args) {
        /*Task task = new TaskWartungBMA();
        task.setAuftragsnummer(2345345);
        task.setKunde("Herbert");
        task.setObjekt("Alterheim");



        //System.out.println(task);


        Task task2 = new TaskWartungBMA();
        //System.out.println(task2);


        Arbeitstag tag = new Arbeitstag();
        tag.addTask(task);
        tag.addTask(task2);
        System.out.println(tag);

        tag.speichern();*/
        String Zeit;
        long millis = 9567567;
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        if (minutes > 0) {
            seconds -= minutes * 60;
        }
        if (hours > 0) {
            minutes -= hours * 60;
        }
        if (hours <= 0) {
            if (minutes <= 0) {
                Zeit= String.format("%d sec (%d ms)", seconds, millis);
            } else  {
                Zeit= String.format("%d min %d sec ", minutes, seconds);
            }
        } else {
            Zeit= String.format("%d h %d min %d sec ", hours, minutes, seconds);
        }
        System.out.println(Zeit);





    }

}
