package steps;

import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class MyStepdefs {

    LogicContainer container;
    public MyStepdefs(LogicContainer container) {
        this.container = container;
    }
    Gamelogic logic;
    private int anzahlSpieler;
    private int numofFrogs;
    private int numofFrogsInHand;

    private boolean gameStarted;

    private Color playerColor;
    private Color frogColor;
    private Set<Position> currentboard;
    private Position samplePosition;


    //Testfall 2
    @Angenommen("das Spiel ist mit {int} Spielern  gestartet")
    public void dasSpielIstMitSpielernGestartet(Integer arg0) {
        container.logicUnderTest = new Gamelogic();
        gameStarted = container.logicUnderTest.newGame(arg0);
        anzahlSpieler = arg0;
        System.out.println("Game started: " + gameStarted);

    }

    @Wenn("die Anzahl der Spieler abgefragt wird")
    public void dieAnzahlDerSpielerAbgefragtWird() {
        container.logicUnderTest.numberOfPlayers();
    }


    @Dann("wurde das Spiel mit {int} Spieler  gestartet")
    public void wurdeDasSpielMitSpielerAGestartet(Integer arg0) {

        assertThat(container.logicUnderTest.numberOfPlayers()).isEqualTo(arg0);
    }

    @Angenommen("die Anzahl der Spieler {int} ist kleiner als zwei")
    public void dieAnzahlDerSpielerSpielerIstKleinerAlsZwei(Integer arg0) {
        anzahlSpieler = arg0;

    }


    @Wenn("das Spiel gestartet wird")
    public void dasSpielGestartetWird() {
        container.logicUnderTest = new Gamelogic();
        container.logicUnderTest.newGame(anzahlSpieler);
    }

    @Dann("wird das Spiel beendet")
    public void wirdDasSpielBeendet() {
        assertThat(container.logicUnderTest.numberOfPlayers()).isZero();
    }

    @Angenommen("die Anzahl der Spieler {int} ist großer als vier")
    public void dieAnzahlDerSpielerSpielerIstGroßerAlsVier(Integer arg0) {
        anzahlSpieler = arg0;

    }

    @Dann("gibt es {int} Spielfarben auf dem Spielfeld")
    public void gibtEsSpielerSpielfarbenAufDemSpielfeld(Integer arg0) {
        assertThat(container.logicUnderTest.numberOfPlayers()).isEqualTo(arg0);
    }

    private void setPlayerColor(String playerColor) {

        switch (playerColor) {
            case "Rot":
                this.playerColor = Color.Red;
                break;
            case "Grün":
                this.playerColor = Color.Green;
                break;
            case "Blau":
                this.playerColor = Color.Blue;
                break;
            case "Schwarz":
                this.playerColor = Color.Black;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + playerColor);
        }


    }

    private void setFrogColor(String frogColor) {

        switch (frogColor) {
            case "Rot":
                this.frogColor = Color.Red;
                break;
            case "Grün":
                this.frogColor = Color.Green;
                break;
            case "Blau":
                this.frogColor = Color.Blue;
                break;
            case "Schwarz":
                this.frogColor = Color.Black;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + frogColor);
        }
    }


    @Wenn("es sind {int} Züge durchgeführt worden gab")
    public void esSindZuegeZügeDurchgeführtWordenGab(Integer arg0) {
        container.logicUnderTest.setGameRound(arg0);
    }
    @Wenn("die Anzahl der Spielsteine von Farbe {int} abgefragt wird")
    public void dieAnzahlDerSpielsteineVonFarbeBeliebigeFarbeAbgefragtWird(Integer arg0) {
        // Write code here that turns the phrase above into concrete actions
        String color = "";
        if(arg0 == 1)
            color = "Rot";
        else if(arg0 == 2)
            color = "Blau";
        else if(arg0 == 3)
            color = "Grün";
        else if(arg0 == 4)
            color = "Schwarz";
        setFrogColor(color);

        numofFrogs = container.logicUnderTest.frogsInBag_withColor(frogColor);


    }

    @Dann("gibt es {int} Spielsteine der Farbe {int}")
    public void gibtEsSpielsteineDerFarbeBeliebigeFarbe(Integer arg0, Integer arg1) {
        assertThat(numofFrogs).isEqualTo(arg0);
    }

    @Wenn("Es wurde kein Spielsteine auf dem Spielfeld gelegt")
    public void es_wurde_kein_spielsteine_auf_dem_spielfeld_gelegt() {
        // Write code here that turns the phrase above into concrete actions
       assertThat(container.logicUnderTest.isFrogonBoard()).isFalse();

    }

    @Wenn("Es wurde kein Spielsteine aus der Beutel gezogen")
    public void es_wurde_kein_spielsteine_aus_der_beutel_gezogen() {
        // Write code here that turns the phrase above into concrete actions

        assertThat(container.logicUnderTest.getGameBag().isBagFilled()).isTrue();

    }

    @Wenn("die Anzahl der Spielsteine abgefragt wird")
    public void die_anzahl_der_spielsteine_abgefragt_wird() {
        // Write code here that turns the phrase above into concrete actions
        container.logicUnderTest.frogsInBag();

    }

    @Dann("sind im Beutel {int} Steine")
    public void sind_im_beutel_steine(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        assertThat(container.logicUnderTest.frogsInBag()).isEqualTo(int1);

    }

    @Wenn("jeder Spieler hat zweimal gezogen")
    public void jeder_spieler_hat_zweimal_gezogen() {
        // Write code here that turns the phrase above into concrete actions
        container.logicUnderTest.takeFrogFromBag();
        container.logicUnderTest.takeFrogFromBag();


    }

    @Wenn("die Anzahl der Spielsteine in der Hand abgefragt wird")
    public void die_anzahl_der_spielsteine_in_der_hand_abgefragt_wird() {
        // Write code here that turns the phrase above into concrete actions
        switch (anzahlSpieler) {
            case 2:
                numofFrogs = container.logicUnderTest.getFrogsInHand(Color.Red).size();
                numofFrogs += container.logicUnderTest.getFrogsInHand(Color.Blue).size();
                break;
            case 3:
                int a = container.logicUnderTest.getFrogsInHand(Color.Red).size();
                int b = container.logicUnderTest.getFrogsInHand(Color.Blue).size();
                int c = container.logicUnderTest.getFrogsInHand(Color.Green).size();
                numofFrogs = a + b + c;
                break;
            case 4:
                int d = container.logicUnderTest.getFrogsInHand(Color.Red).size();
                int e = container.logicUnderTest.getFrogsInHand(Color.Blue).size();
                int f = container.logicUnderTest.getFrogsInHand(Color.Green).size();
                int g = container.logicUnderTest.getFrogsInHand(Color.Black).size();
                numofFrogs = d + e + f + g;
                break;
            default:
                // Handle the case where anzahlSpieler is not 2, 3, or 4
                break;
        }

    }

    @Dann("wurden {int} Spielsteine gezogen")
    public void wurden_spielsteine_gezogen(Integer int1) {
        // Write code here that turns the phrase above into concrete actions

        assertThat(numofFrogs).isEqualTo(int1);

    }


    @Angenommen("Es gibt mindestens ein Spielstein auf dem Spielfeld")
    public void esGibtMindestensEinSpielsteinAufDemSpielfeld() {

        currentboard = container.logicUnderTest.getBoard();
        assertThat(currentboard).isNotEmpty();
    }

    @Und("eine Position hat kein Nachbarn")
    public void einePositionHatKeinNachbarn() {
        // Write code here that turns the phrase above into concrete actions
        //Position position = new Position(0, 0);
        samplePosition = currentboard.iterator().next();

    }

    @Wenn("ein Stein auf diese Position angelegt")
    public void einSteinAufDiesePositionAngelegt() {
        // Write code here that turns the phrase above into concrete actions
        //container.logicUnderTest.anlegen(samplePosition, Color.Red);
    }

    @Dann("wird das Anlegen rückgängig gemacht")
    public void wirdDasAnlegenRuckgangigGemacht() {
        // Write code here that turns the phrase above into concrete actions
        //assertThat(container.logicUnderTest.getBoard()).doesNotContain(samplePosition);
    }

    @Angenommen("es sind <Züge> Züge durchgeführt worden")
    public void esSindZugeZugeDurchgefuhrtWorden() {
        
    }

    @Und("Gabriel der dritte Spieler ist")
    public void gabrielDerDritteSpielerIst() {
        
    }

    @Dann("gibt es mindestens ein Stein der ziehenden Farbe auf dem Feld")
    public void gibtEsMindestensEinSteinDerZiehendenFarbeAufDemFeld() {
    }
}
