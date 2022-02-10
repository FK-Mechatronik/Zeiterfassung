import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Fabian Klüber
 * Klasse StempelAppGUI beschreibt das Aussehen und verhalten der Applikation.
 * Die Klasse speichert die an einem Tag durchgeführten Tasks in einer Liste
 * und diese Wiederum dauerhaft in einer JSON Datei.
 * Beliebige Listen, welche gespeichert wurden, können manipuliert werden.
 * Daten einer fertigen Liste werden in eine PDF geschrieben und
 * diese kann automatisch via E-Mail an eine entsprechende Abteilung gesendet werden.
 */
public class StempelAppGUI {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField tfTaetigkeit;
    private JTextField tfKunde;
    private JTextField tfAuftragsnummer;
    private JTextField tfBeginn;
    private JTextField tfEnde;
    private JTextField tfDatum;
    private JButton BMAWartungButton;
    private JButton taskBeendenButton;
    private JButton werkstattButton;
    private JButton EMAWartungButton;
    private JButton montageButton;
    private JButton sonstigesButton;
    private JButton pauseButton;
    private JPanel aktuellerTask;
    private JTextField tFObjekt;
    private JLabel Kunde;
    private JLabel Objekt;
    private JTextField tfVergangeneZeit;
    private JLabel VergangeneZeit;


    /**
     * Konstruktor der GUI hier werden alle Ein- und Ausgaben des Programms beschrieben
     */
    public StempelAppGUI() {
        Arbeitstag aktuellerTag = new Arbeitstag();//mus datumsabhäning sein!!
        List<JButton> buttons = new ArrayList<>();
        buttons.add(BMAWartungButton);
        buttons.add(EMAWartungButton);
        buttons.add(werkstattButton);
        buttons.add(montageButton);
        buttons.add(werkstattButton);
        buttons.add(sonstigesButton);
        buttons.add(pauseButton);


        BMAWartungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktuellerTag.addTask(new TaskWartungBMA());
                buttonChangeDesign(BMAWartungButton);
                akiviereButtons(buttons,BMAWartungButton,false);

                einUndAusgabe(aktuellerTag);


            }
        });
        EMAWartungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktuellerTag.addTask(new TaskWartungEMA());
                buttonChangeDesign(EMAWartungButton);
                akiviereButtons(buttons,EMAWartungButton,false);
                einUndAusgabe(aktuellerTag);
            }
        });
        montageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktuellerTag.addTask(new TaskMontage());
                buttonChangeDesign(montageButton);
                akiviereButtons(buttons,montageButton,false);
                einUndAusgabe(aktuellerTag);
            }
        });
        werkstattButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktuellerTag.addTask(new TaskWerkstatt());
                buttonChangeDesign(werkstattButton);
                akiviereButtons(buttons,werkstattButton,false);
                einUndAusgabe(aktuellerTag);
            }
        });
        sonstigesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktuellerTag.addTask(new TaskSonstige());
                buttonChangeDesign(sonstigesButton);
                akiviereButtons(buttons,sonstigesButton,false);
                einUndAusgabe(aktuellerTag);
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktuellerTag.addTask(new TaskPause());
                buttonChangeDesign(pauseButton);
                akiviereButtons(buttons,pauseButton,false);
                einUndAusgabe(aktuellerTag);
            }
        });
        taskBeendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                akiviereButtons(buttons,taskBeendenButton,true);
                getTask(aktuellerTag).taskBeenden();
                try {
                    ausgabeTextFelder(aktuellerTag);
                    tfVergangeneZeit.setText(""+getTask(aktuellerTag).getZeitDifferenz());
                    tfEnde.setText(""+getTask(aktuellerTag).getEndZeit());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                System.out.println(aktuellerTag.toString());
            }
        });


    }


    /**
     * Methode einUndAusgabe enthält eingaben welch vom User gemacht werden müssen und
     * setzt alle bekannten Daten ind den Oberflächenteil "Aktueller Task".
     * @param aktuellerTag Liste aller Tasks eines Tages
     */
    private void einUndAusgabe(Arbeitstag aktuellerTag) {
        eingabeTextfelder(aktuellerTag);
        try {
            ausgabeTextFelder(aktuellerTag);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Methode eingabeTextfelder fordert den User auf nötige Eingaben zu tätigen.
     * @param aktuellerTag Liste aller Tasks eines Tages
     */
    private void eingabeTextfelder(Arbeitstag aktuellerTag) {
        JFrame jFrame = new JFrame();
        Task thisTask= getTask(aktuellerTag);
        if (thisTask.getTeatigkeit().equals("Sonstige")){
            thisTask.setTeatigkeit(JOptionPane.showInputDialog(jFrame, "Tätigkeit"));
        }
        thisTask.setKunde(JOptionPane.showInputDialog(jFrame, "Kundeneingabe"));
        thisTask.setObjekt(JOptionPane.showInputDialog(jFrame, "Objekteingabe"));
        thisTask.setAuftragsnummer(JOptionPane.showInputDialog(jFrame, "Auftragsnummer"));
    }


    /**
     * Methode ausgabeTextFelder setzt alle Textfelder im Panel "Aktueller Task"
     * mit den informationen des aktuellen tasks.
     * @param aktuellerTag Liste aller Tasks eines Tages
     * @throws ParseException
     */
    private void ausgabeTextFelder(Arbeitstag aktuellerTag) throws ParseException {
        Task thisTask = getTask(aktuellerTag);
        if(aktuellerTag.getTasks().size()-1!=0){
            Task task2 = aktuellerTag.getTasks().get(aktuellerTag.getTasks().size()-2);
            thisTask.setStartZeit(task2.getEndZeit());
        }
        tfTaetigkeit.setText(""+ thisTask.getTeatigkeit());
        tfKunde.setText(""+ thisTask.getKunde());
        tFObjekt.setText(""+thisTask.getObjekt());
        tfAuftragsnummer.setText(""+thisTask.getAuftragsnummer());
        tfBeginn.setText(""+thisTask.getStartZeit());
        tfEnde.setText("--:--:--");
        tfVergangeneZeit.setText(""+thisTask.getZeitDifferenz());
        tfDatum.setText(""+thisTask.getDatum());
    }


    /**
     * Methode Task gibt den letzten Task der Liste bzw den aktuellen Task zurück um af diesen zugreifen zu können.
     * @param aktuellerTag Liste aller Tasks eines Tages
     * @return aktueller Task
     */
    private Task getTask(Arbeitstag aktuellerTag) {
        int TaskNR = aktuellerTag.getTasks().size()-1;
        Task thisTask = aktuellerTag.getTasks().get(TaskNR);
        return thisTask;
    }


    /**
     * Methode buttonChangeDesign änder die Anzeige eines Buttons, wenn dieser geklickt wurde.
     * @param button JButton der grafischen Oberfläche
     */
    private void buttonChangeDesign(JButton button) {
        button.setForeground(new Color(68,68,68));
        button.setBackground(new Color(30,187,57));
    }


    /**
     * Methode buttonDesignReset setzt die Anzeige eines Buttons in den initialen Zustand.
     * @param button JButton der grafischen Oberfläche
     */
    private void buttonDesignReset(JButton button) {
        button.setForeground(new Color(30,187,57));
        button.setBackground(new Color(68,68,68));

    }


    /**
     * Methode aktiviereButtons kann alle JButtens welche einen Task beschreiben aktivieren oder deaktivieren.
     * Wird ein Task beendet mit dem "taskBeendenButton" wird zudem das Design aller Buttons zurückgesetzt.
     * @param buttons Liste aller JButtons
     * @param button JButton der grafischen Oberfläche
     * @param aktiviert Boolen: true: aktiviert, false: deaktiviert.
     */
    public void akiviereButtons(List<JButton> buttons, JButton button, Boolean aktiviert){
        for(int i=0;i<buttons.size();i++){
            buttons.get(i).setEnabled(aktiviert);
            if (button==taskBeendenButton){
                buttonDesignReset(buttons.get(i));
            }
        }
    }


    /**
     * Mathode Main führt die Applikation aus
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("StempelAppGUI");
        frame.setContentPane(new StempelAppGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
}
