import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Fabian Klüber
 * Die Klasse Arbeitstag erstellt für einen Tag eine Liste.
 * Existiert bereits eine gespeicherte Liste, mit dem aktuellen Datum, in form einer JSON Datei,
 * wird diese zuerst in die neu initialisierte Liste eingelesen. Existiert keine gespeicherte Liste
 * mit dem aktuellen Datum so wird eine leere Liste erzeugt.
 */
public class Arbeitstag {
    private String datum;
    private List<Task> tasks;


    /**
     * Getter für einzelne Tasks des Arbeitstags.
     * Einige Daten der Taskt erhält man nur durch eingabe des Benutzers.
     */
    public List<Task> getTasks() {
        return tasks;
    }


    /**
     * Setter für einen Task
     * Ggf. vergessen Tasks könner der list hinzugefügt werden.
     * @param tasks neuer Task
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    /**
     * Konstruktor erzeugt die Listte und enthält das aktuelle Datum
     */
    public Arbeitstag() {
        this.datum = Task.Datum();
        this.tasks = new ArrayList<>();
    }


    /**
     * Methode addTask deligiert das hinzufügen eines Listenelemets
     * @param task
     */
    public void addTask(Task task){
        this.tasks.add(task);
    }


    /**
     * Methode speichern, speichert die Liste mit allen Tasks in einer JSON Datei mithilfen von GSON.
     */
    public void speichern(){
        //speichern wen nix null ist und die Endzeit gegeben(via buttentask beenden
        Gson gson = new Gson();
        String json = gson.toJson(this.tasks);
        try(Writer writer = new FileWriter("Stempelung vom "+this.datum)){
            gson = new GsonBuilder().create();
            gson.toJson(tasks,writer);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Methode laden
     *
     */
    public void laden(){
        //TODO: falls liste mit aktuellem datum existiert(Gson) liste mit den daten erzeuegen sonst leere liste
    }


    /**
     * Methode toString ermöglicht es, einen Arbeitstag als String auszugeben
     * @return
     */
    @Override
    public String toString() {
        String ausgabe="Arbeitstag\n"+this.datum+"\n\n";
        for(Task task:tasks){
            ausgabe+=task+"\n";
        }
        return  ausgabe;
    }
}
