package steps;

import static org.assertj.core.api.Assertions.assertThat;

import de.fhkiel.tsw.Gamelogic;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

public class BagSteps {

    Gamelogic logic;

    int frösche;
    int zuege;

    @Angenommen("kein Spiel läuft")
    public void kein_spiel_läuft() {
        logic = new Gamelogic();
    }
    @Angenommen("das Spiel ist mit {int} Spielern gestartet")
    public void das_spiel_ist_mit_spielern_gestartet(Integer spieler) {
        logic = new Gamelogic();
        logic.startGame(spieler);
    }
    @Angenommen("der (erste)(zweite)(dritte)(vierte) Spieler hat {int} (Frosch)(Frösche) gezogen")
    public void der_erste_spieler_hat_frösche_gezogen(Integer anzahl) {
        for(int i = 0; i < anzahl; ++i){
            logic.takeFrogFromBag();
        }
    }
    @Angenommen("es sind {int} Züge durchgeführt worden")
    public void es_sind_züge_durchgeführt_worden(Integer zuege) { this.zuege = zuege; }
    @Wenn("die Anzahl der Frösche im Beutel abgefragt wird")
    public void der_anzahl_der_frösche_im_beutel_abgefragt_wird() {
        frösche = logic.frogsInBag();
    }
    @Dann("sind im Beutel {int} Frösche")
    public void sind_im_beutel_frösche(Integer erwarteteFrösche) {
        assertThat(frösche)
                .isEqualTo(erwarteteFrösche);
    }

}

