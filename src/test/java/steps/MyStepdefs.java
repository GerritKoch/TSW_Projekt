package steps;

import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.Player;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
  private boolean testResult;

  private Color playerColor;
  private Color frogColor;
  private Set<Position> currentboard;
  private Position samplePosition;
  private Player currentPlayer;
  private Player samplePlayer;
  private Gamelogic.GamePhase currentPhase;
  private int gameRound;


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
      if (arg0 == 1) {
          color = "Rot";
      } else if (arg0 == 2) {
          color = "Blau";
      } else if (arg0 == 3) {
          color = "Grün";
      } else if (arg0 == 4) {
          color = "Schwarz";
      }
    setFrogColor(color);

    numofFrogs = container.logicUnderTest.frogsInBagWithColor(frogColor);
    numofFrogs += container.logicUnderTest.getFrogInHandMapSize(frogColor);


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

    int numofFrogs = container.logicUnderTest.frogsInBag();
    numofFrogs += container.logicUnderTest.getFrogInHandMapSize();
    assertThat(numofFrogs).isEqualTo(int1);

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

    einSpielLauft();
    einSpielHatEinSteinVonDerZiehendenFarbeAngelegt();
  }

  @Und("eine Position hat kein Nachbarn")
  public void einePositionHatKeinNachbarn() {
    // Write code here that turns the phrase above into concrete actions
    //Position position = new Position(0, 0);
    samplePosition = new Position(currentPlayer.getPlayerColor(), 2, 2, Color.None);

  }

  @Wenn("ein Stein auf diese Position angelegt")
  public void einSteinAufDiesePositionAngelegt() {

    container.logicUnderTest.anlegen(samplePosition);
  }

  @Dann("wird das Anlegen rückgängig gemacht")
  public void wirdDasAnlegenRuckgangigGemacht() {
    // Write code here that turns the phrase above into concrete actions

    assertThat(container.logicUnderTest.getBoard()).doesNotContain(samplePosition);
  }


  @Und("Gabriel der dritte Spieler ist")
  public void gabrielDerDritteSpielerIst() {

    samplePlayer = container.logicUnderTest.getPlayers()[2];

  }

  @Dann("gibt es mindestens ein Stein der ziehenden Farbe auf dem Feld")
  public void gibtEsMindestensEinSteinDerZiehendenFarbeAufDemFeld() {
    assertThat(currentboard).isNotEmpty();
  }

  @Angenommen("ein Spiel läuft")
  public void einSpielLauft() {
    dasSpielIstMitSpielernGestartet(2);


  }

  @Und("ein Spieler hat ein Stein von der ziehenden Farbe angelegt")
  public void einSpielHatEinSteinVonDerZiehendenFarbeAngelegt() {

    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 0, 0, Color.None));

  }

  @Wenn("Der GUI überprüft, ob ein Stein der ziehenden Farbe angelegt ist.")
  public void derGUIUberpruftObEinSteinDerZiehendenFarbeAngelegtIst() {
    currentboard = container.logicUnderTest.getBoard();
  }

  @Und("es noch kein Stein angelegt wurde")
  public void esNochKeinSteinAngelegtWurde() {
    System.out.println("Kein Stein angelegt");
  }

  @Dann("gibt es kein Stein der ziehenden Farbe auf dem Feld")
  public void gibtEsKeinSteinDerZiehendenFarbeAufDemFeld() {
    assertThat(currentboard).isEmpty();
  }

  @Angenommen("es sind im Spiel sechs Züge durchgeführt worden")
  public void esSindImSpielSechsZugeDurchgefuhrtWorden() {
    einSpielLauft();
  }

  @Wenn("ein Stein in der Mitte gesetzt wird")
  public void einSteinInDerMitteGesetztWird() {
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    samplePosition = new Position(currentPlayer.getPlayerColor(), 0, 0, Color.None);
    container.logicUnderTest.anlegen(samplePosition);
  }

  @Und("funf Steine um ihn herum gesetzt werde")
  public void funfSteineUmIhnHerumGesetztWerde() {
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 1, 0, Color.None));
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), -1, 0, Color.None));
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 0, 1, Color.None));
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 0, -1, Color.None));
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), -1, 1, Color.None));
  }

  @Dann("hat der  Stein  {int} Verbindungen")
  public void hatDerSteinVerbindungen(int arg0) {

    currentboard = container.logicUnderTest.getBoard();
    List<Position> boardPositions = new ArrayList<>(currentboard);
    Set<Position> positionSet = new HashSet<>();

    int bfs = container.logicUnderTest.bfs(samplePosition, boardPositions, positionSet);
    assertThat(arg0 + 1).isEqualTo(bfs);

  }

  @Und("der erste Spieler ist mit einem Zug fertig")
  public void derErsteSpielerIstMitEinemZugFertig() {
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 0, 0, Color.None));
    container.logicUnderTest.endTurn();
  }

  @Wenn("es gefragt wird, ob der dritte Spieler am Zug ist")
  public void esGefragtWirdObDerDritteSpielerAmZugIst() {

    testResult = currentPlayer == samplePlayer;
  }

  @Dann("der dritter Spieler ist nicht am Zug")
  public void derDritterSpielerIstNichtAmZug() {
    assertThat(testResult).isFalse();
  }

  @Und("Peter ist an der Reihe")
  public void peterIstAnDerReihe() {
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
  }

  @Wenn("Peter den ersten Zug macht")
  public void peterDenErstenZugMacht() {
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 0, 0, Color.None));
  }

  @Dann("wird der Stein auf das Spielfeld gelegt")
  public void wirdDerSteinAufDasSpielfeldGelegt() {

    assertThat(container.logicUnderTest.getBoard()).isNotEmpty();
  }

  @Angenommen("Peter hat einen Frosch angelegt")
  public void peterHatEinenFroschAngelegt() {

    esSindImSpielSechsZugeDurchgefuhrtWorden();
    einSteinInDerMitteGesetztWird();
    funfSteineUmIhnHerumGesetztWerde();

    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    samplePosition = new Position(currentPlayer.getPlayerColor(), 1, 1, Color.None);
    container.logicUnderTest.anlegen(samplePosition);


  }

  @Wenn("die Position des Froschs abgefragt wird")
  public void diePositionDesFroschsAbgefragtWird() {
    // Write code here that turns the phrase above into concrete actions
    currentboard = container.logicUnderTest.getBoard();
    samplePosition = currentboard.iterator().next();
  }

  @Dann("hat die Position mehr als null angrenzende Frösche")
  public void hatDiePositionMehrAlsNullAngrenzendeFrosche() {

    List<Position> boardPositions = new ArrayList<>(currentboard);
    Set<Position> positionSet = new HashSet<>();
    int bfs = container.logicUnderTest.bfs(samplePosition, boardPositions, positionSet);
    assertThat(bfs).isGreaterThan(0);
  }

  @Und("das Spiel wurde mit zwei Spielern gestartet")
  public void dasSpielWurdeMitZweiSpielernGestartet() {
    dasSpielIstMitSpielernGestartet(2);
  }

  @Und("es sind null Züge durchgeführt worden")
  public void esSindNullZugeDurchgefuhrtWorden() {

    assertThat(container.logicUnderTest.getGameRound()).isZero();

  }

  @Wenn("die Spielphase abgefragt wird")
  public void dieSpielphaseAbgefragtWird() {

    currentPhase = container.logicUnderTest.getCurrentGamePhase();

  }

  @Dann("ist die Spielphase Anlegephase")
  public void istDieSpielphaseAnlegephase() {

    assertThat(currentPhase).isEqualTo(Gamelogic.GamePhase.ANLEGEN);
  }

  @Und("der erste Spieler hat einen Stein angelegt")
  public void derErsteSpielerHatEinenSteinAngelegt() {
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 0, 0, Color.None));
  }

  @Dann("ist die Spielphase Nachziehphase")
  public void istDieSpielphaseNachziehphase() {

    assertThat(currentPhase).isEqualTo(Gamelogic.GamePhase.NACHZIEHEN);
  }

  @Angenommen("das erste Stein wurde gelegt")
  public void dasErsteSteinWurdeGelegt() {

    dasSpielIstMitSpielernGestartet(2);
    derErsteSpielerHatEinenSteinAngelegt();


  }

  @Wenn("die Reihefolge der Phase durchgeführt wird")
  public void dieReihefolgeDerPhaseDurchgefuhrtWird() {
    container.logicUnderTest.endTurn();
  }

  @Dann("ist die Reihenfolge Nachziehen, Bewegen, Anlegen")
  public void istDieReihenfolgeNachziehenBewegenAnlegen() {
    assertThat(container.logicUnderTest.getCurrentGamePhase()).isEqualTo(
        Gamelogic.GamePhase.NACHZIEHEN);
    container.logicUnderTest.nachziehen();
    assertThat(container.logicUnderTest.getCurrentGamePhase()).isEqualTo(
        Gamelogic.GamePhase.BEWEGEN);
    container.logicUnderTest.bewegen(null);
    assertThat(container.logicUnderTest.getCurrentGamePhase()).isEqualTo(
        Gamelogic.GamePhase.ANLEGEN);
  }

  @Angenommen("Ein Stein wird auf Position \\({double}) gesetzt.")
  public void einSteinWirdAufPositionGesetzt(int arg0, int arg1) {
  }

  @Wenn("Peter die Position des Steins abfrägt")
  public void peterDiePositionDesSteinsAbfragt() {
  }

  @Dann("erhält er die Position \\({double}).")
  public void erhaltErDiePosition(int arg0, int arg1) {
  }

  @Angenommen("Das Spiel ist mit zwei Spielern gestartet.")
  public void dasSpielIstMitZweiSpielernGestartet() {
  }

  @Wenn("Peter versucht ein Stein auf einer Position x[{int}] und y[{int}] zu platzieren.")
  public void peterVersuchtEinSteinAufEinerPositionXUndYZuPlatzieren(int arg0, int arg1) {
  }

  @Dann("wird eine Fehlermeldung angezeigt, dass die Position ungültig ist.")
  public void wirdEineFehlermeldungAngezeigtDassDiePositionUngultigIst() {
  }

  @Angenommen("Peter hat einen Stein angelegt")
  public void peterHatEinenSteinAngelegt() {
  }

  @Wenn("er den Stein bewegen möchte")
  public void erDenSteinBewegenMochte() {
  }

  @Dann("kriegt er Informationen, dass Nachziehen dran ist")
  public void kriegtErInformationenDassNachziehenDranIst() {
  }

  @Angenommen("sind keine Steine im Beutel")
  public void sindKeineSteineImBeutel() {
  }

  @Wenn("der Spieler an der Reihe ist")
  public void derSpielerAnDerReiheIst() {
  }

  @Dann("wird das Nachziehen übersprungen")
  public void wirdDasNachziehenUbersprungen() {
  }
}
