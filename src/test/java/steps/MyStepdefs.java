package steps;

import aof.Gamelogic;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import static org.assertj.core.api.Assertions.assertThat;


public class MyStepdefs {

    Gamelogic logic;

    //Testfall 2
    @Angenommen("das Spiel ist mit {int} Spielern a gestartet")
    public void dasSpielIstMitSpielernGestartet(Integer arg0) {
        logic = new Gamelogic();
        logic.newGame(arg0);
        //throw new io.cucumber.java.PendingException();
    }

    @Wenn("die Anzahl der Spieler abgefragt a wird")
    public void dieAnzahlDerSpielerAbgefragtWird() {
         logic.numberOfPlayers();
    }


    @Dann("wurde das Spiel mit {int} Spieler a gestartet")
    public void wurdeDasSpielMitSpielerAGestartet(Integer arg0) {

        assertThat(logic.numberOfPlayers()).isEqualTo(arg0);
    }
}
