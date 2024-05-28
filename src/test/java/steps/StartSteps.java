package steps;

import static org.assertj.core.api.Assertions.assertThat;

import aof.Gamelogic;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

public class StartSteps {

    private LogicContainer container;

    private boolean success;

    public StartSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Wenn("das Spiel( ist) mit {int} Spielern gestartet( wird)")
    public void das_spiel_mit_spielern_gestartet_wird(int anzahl) {
        container.logicUnderTest = new Gamelogic();
        success = container.logicUnderTest.newGame(anzahl);
    }

    @Dann("wird ein Spiel erstellt")
    public void wird_ein_spiel_erstellt() {
        assertThat(success)
                .isTrue();
    }

    @Dann("wird kein Spiel erstellt")
    public void wird_kein_spiel_erstellt() {
        assertThat(success)
                .isFalse();
    }

    @Dann("es spielen {int} Spieler")
    public void es_spielen_spieler(int anzahl) {
        assertThat(container.logicUnderTest.players())
                .hasSize(anzahl);
    }




}
