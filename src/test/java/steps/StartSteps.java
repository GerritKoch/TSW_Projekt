package steps;

import static org.assertj.core.api.Assertions.assertThat;

import aof.Gamelogic;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

public class StartSteps {

    LogicContainer container;

    public StartSteps(LogicContainer container) {
        this.container = container;
    }

    boolean gameStarted;

    @Wenn("das Spiel( ist) mit {int} Spielern gestartet( wird)")
    public void das_spiel_mit_spielern_gestartet_wird(int anzahl) {
        container.logicUnderTest = new Gamelogic();
        gameStarted = container.logicUnderTest.newGame(anzahl);
    }

    @Dann("wird ein Spiel erstellt")
    public void wird_ein_spiel_erstellt() {
        assertThat(container.logicUnderTest).isNotNull();
        assertThat(gameStarted).isTrue();
    }
    @Dann("es spielen {int} Spieler")
    public void es_spielen_spieler(int anzahl) {
        assertThat(container.logicUnderTest.players())
                .hasSize(anzahl);
    }
    @Dann("wird kein Spiel erstellt")
    public void wird_kein_spiel_erstellt() {
        assertThat(container.logicUnderTest).isNotNull();
        assertThat(gameStarted).isFalse();
    }
}
