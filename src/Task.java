import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


/**
 * @author Fabian Klüber
 * Abstrakte Klasse Task definiert einen grundsätzlichen Task und enthält alle Informationen,
 * welche für jeden Task relevant sind. Zudem ist die Zeitsetzung und berechnung hier implementiert.
 */
public abstract class Task {
    private String datum;
    private String startZeit;
    private String endZeit;
    private String zeitDifferenz;
    private String auftragsnummer;
    private String kunde;
    private String objekt;
    private String teatigkeit;


    /**
     * Konstruktor, setzt die Startzeit und das Datum des Tasks
     */
    public Task() {
        this.startZeit=Uhrzeit();
        this.datum=Datum();
    }


    /**
     * Metthode Datum, gibt das aktuelle Systemdatum als String zurück.
     * Die Methode ist statisch um auch in der Klasse Arbeitstag, welche keine Subklasse ist,
     * aufgerufen werden zu können.
     * @return aktuelles Datum
     */
    public static String Datum(){
        GregorianCalendar heute = new GregorianCalendar();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        return df.format(heute.getTime());
    }


    /**
     * Methode Uhrzeit gibt die aktuelle Systemzeit zurück
     * @return aktuelle Uhrzeit
     */
    private String Uhrzeit(){
        GregorianCalendar heute = new GregorianCalendar();
        DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        return df.format(heute.getTime());
    }


    /**
     * Methode ZeitDifferenz berechnet mithilfe der Start- und Endzeit des aktuellen Tasks die vergangene Zeit
     * und gibt diese in Stunden, Minuten und Sekunden aus.
     * @return vergangene Zeit während des Tasks
     * @throws ParseException
     */
    String ZeitDifferenz () throws ParseException {
        String Zeit;
        String startZ = this.startZeit;
        String endZ = this.endZeit;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date start = sdf.parse(startZ);
        Date ende = sdf.parse(endZ);
        long millis = ende.getTime()- start.getTime();
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
                this.zeitDifferenz=Zeit;
            } else  {
                Zeit= String.format("%d min %d sec ", minutes, seconds);
                this.zeitDifferenz=Zeit;
            }
        } else {
            Zeit= String.format("%d h %d min %d sec ", hours, minutes, seconds);
            this.zeitDifferenz=Zeit;
        }
        return Zeit;
    }


    /**
     * Methode taskBeenden setzt die Endzeit des aktuellen Tasks entsprechend von bedingungen
     */
    public void taskBeenden(){
        this.endZeit = Uhrzeit();
        //TODO bedingungen
    }


    /**
     *Getter getEndZeit gibt die Endzeit eines Tasks zurück
     * @return Eindzeit des Tasks
     */
    public String getEndZeit() {
        return endZeit;
    }


    /**
     * Setter setEndZeit setzt die Einzeit eines Tasks
     * @param endZeit des Tasks
     */
    public void setEndZeit(String endZeit) {
        this.endZeit = Uhrzeit();
    }


    /**
     * Getter getAuftraggsnummer gibt die Auftragsnummer eines Tasks zurück
     * @return Auftragsnummer des Tasks
     */
    public String getAuftragsnummer() {
        return auftragsnummer;
    }


    /**
     * Setter setAuftragsnummer setzt die Auftragsnummer eines Tasks
     * @param auftragsnummer des Tasks
     */
    public void setAuftragsnummer(String auftragsnummer) {
        this.auftragsnummer = auftragsnummer;
    }


    /**
     * Getter getKunde gibt den Kunden eines Tasks zurück
     * @return Kunde des Tasks
     */
    public String getKunde() {
        return kunde;
    }


    /**
     * Setter setKunde setzt den Kunden eines Tasks
     * @param kunde des Tasks
     */
    public void setKunde(String kunde) {
        this.kunde = kunde;
    }


    /**
     * Getter getObjekt gibt das Objekt eines Tasks zurück
     * @return Objekt des Tasks
     */
    public String getObjekt() {
        return objekt;
    }


    /**
     * Setter setObjekt setzt das Objekt eines Tasks
     * @param objekt des Tasks
     */
    public void setObjekt(String objekt) {
        this.objekt = objekt;
    }


    /**
     * Getter getTaetigkeit gibt die Tätigkeit eines Tasks zurück
     * @return Tätigkeit des Tasks
     */
    public String getTeatigkeit() {
        return teatigkeit;
    }


    /**
     * Setter settaetigkeit setzt die Tätigkeit eines Tasks
     * @param teatigkeit des Tasks
     */
    public void setTeatigkeit(String teatigkeit) {
        this.teatigkeit = teatigkeit;
    }


    /**
     * Getter getDatum gibt das Datum eines Tasks zurück
     * @return Datum des Tasks
     */
    public String getDatum() {
        return datum;
    }


    /**
     * Setter setDatum setzt das Datum des Tasks
     * @param datum des Tasks
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }


    /**
     * Getter getStartZeit gibt die Startzeit eines Tasks zurück
     * @return Startzeit des Tasks
     */
    public String getStartZeit() {
        return startZeit;
    }


    /**
     *Setter setStartzeit setzt die Startzeit eines Tasks
     * @param startZeit des Tasks
     */
    public void setStartZeit(String startZeit) {
        this.startZeit = startZeit;
    }


    /**
     * Getter getZeitdifferenz gibt die Zeitdifferenz eines Tasks zurück.
     * Diese existiert erst nach Beendigung eines Tasks weswegen bis dahin
     * eine alternative Hinweisausgabe getätigt wird.
     * @return Zeitdifferenz des Tasks
     * @throws ParseException
     */
    public String getZeitDifferenz() throws ParseException {
        String zeitDifferenz;
        if(getEndZeit()==null){
            zeitDifferenz="Anzeige bei Taskbeendigung";
        }else{
            zeitDifferenz=ZeitDifferenz();
        }
        return zeitDifferenz;
    }


    /**
     * Methode toString ermöglicht es, einen Task als String auszugeben
     * @return
     */
    @Override
    public String toString() {
        return "Kunde: "+this.kunde+"\n"+"Objekt: "+this.objekt+"\n"+"Auftragsnummer: "+this.auftragsnummer+"\n"+
                "Startzeit: "+this.startZeit+"\n"+"Endzeit: "+ this.endZeit+"\n"+"Zeisdifferenz: "+
                this.zeitDifferenz+"\n"+"Tätigkeit: "+this.teatigkeit+"\n"+"Datum: "+this.datum+"\n";
    }
}
