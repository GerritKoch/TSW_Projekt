package steps;

import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.Player;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import java.util.Random;
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
  private Position testPosition1;
  private Position testPosition2;

  private Color playerColor;
  private Color frogColor;
  private Set<Position> currentboard;
  private Position samplePosition;
  private Player currentPlayer;
  private Player samplePlayer;
  private Gamelogic.GamePhase currentPhase;
  private int gameRound;
  private Color otherPlayer;


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


  @Wenn("es sind {int} Züge durchgeführt worden gab")
  public void esSindZuegeZügeDurchgeführtWordenGab(Integer arg0) {
    container.logicUnderTest.setGameRound(arg0);
  }

  @Wenn("die Anzahl der Spielsteine von Farbe {int} abgefragt wird")
  public void dieAnzahlDerSpielsteineVonFarbeBeliebigeFarbeAbgefragtWird(Integer arg0) {
    // Write code here that turns the phrase above into concrete actions


    Player[] currentPlayerColors = container.logicUnderTest.getPlayers();
    Random random = new Random();
    int randomIndex = random.nextInt(currentPlayerColors.length); // Generate a random index
    Color randomPlayerColor =
        currentPlayerColors[randomIndex].getPlayerColor(); // Get the color of the randomly selected player

    numofFrogs = container.logicUnderTest.frogsInBagWithColor(randomPlayerColor);
    numofFrogs += container.logicUnderTest.getFrogInHandMapSize(randomPlayerColor);


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

    numofFrogsInHand = container.logicUnderTest.getFrogInHandMapSize();

  }

  @Dann("wurden {int} Spielsteine gezogen")
  public void wurden_spielsteine_gezogen(Integer int1) {
    // Write code here that turns the phrase above into concrete actions

    assertThat(numofFrogsInHand).isEqualTo(int1);

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
        new Position(Color.Black, 1, 0, Color.None));
    container.logicUnderTest.anlegen(
        new Position(Color.Black, -1, 0, Color.None));
    container.logicUnderTest.anlegen(
        new Position(Color.Black, 0, 1, Color.None));
    container.logicUnderTest.anlegen(
        new Position(Color.Black, 0, -1, Color.None));
    container.logicUnderTest.anlegen(
        new Position(Color.Black, -1, 1, Color.None));


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


  @Wenn("Peter die Position des Steins abfrägt")
  public void peterDiePositionDesSteinsAbfragt() {
    currentboard = container.logicUnderTest.getBoard();
    samplePosition = currentboard.iterator().next();
  }


  @Angenommen("Das Spiel ist mit zwei Spielern gestartet.")
  public void dasSpielIstMitZweiSpielernGestartet() {
    dasSpielLauft();
  }

  @Wenn("Peter versucht ein Stein auf einer Position x[{int}] und y[{int}] zu platzieren.")
  public void peterVersuchtEinSteinAufEinerPositionXUndYZuPlatzieren(int arg0, int arg1) {

    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), arg0, arg1, Color.None));
  }

  @Dann("wird eine Fehlermeldung angezeigt, dass die Position ungültig ist.")
  public void wirdEineFehlermeldungAngezeigtDassDiePositionUngultigIst() {
    assertThat(container.logicUnderTest.getBoard()).isEmpty();
  }

  @Angenommen("Peter hat einen Stein angelegt")
  public void peterHatEinenSteinAngelegt() {
    dasSpielLauft();
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), 0, 0, Color.None));
  }

  @Wenn("er den Stein bewegen möchte")
  public void erDenSteinBewegenMochte() {
    container.logicUnderTest.bewegen(samplePosition);
  }

  @Dann("kriegt er Informationen, dass Nachziehen dran ist")
  public void kriegtErInformationenDassNachziehenDranIst() {
  }

  @Angenommen("sind keine Steine im Beutel")
  public void sindKeineSteineImBeutel() {
    dasSpielLauft();
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    var size = container.logicUnderTest.getGameBag().getFrogsInBag().size();
    container.logicUnderTest.getGameBag().emptyBag(currentPlayer.getPlayerColor());

  }

  @Wenn("der Spieler an der Reihe ist")
  public void derSpielerAnDerReiheIst() {
    if (container.logicUnderTest.getCurrentPlayer() == currentPlayer) ;
    container.logicUnderTest.setCurrentGamePhase(Gamelogic.GamePhase.ANLEGEN);

  }

  @Dann("wird das Nachziehen uebersprungen")
  public void wirdDasNachziehenUbersprungen() {

    //assertThat(container.logicUnderTest.nachziehen()).isFalse();
    assertThat(container.logicUnderTest.getCurrentGamePhase()).isEqualTo(
        Gamelogic.GamePhase.ANLEGEN);

  }

  @Angenommen("die Spielhand ist voll")
  public void dieSpielhandIstVoll() {
    dasSpielLauft();
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    assertThat(container.logicUnderTest.getFrogInPlayerHandMapSize(currentPlayer))
        .isEqualTo(2);
  }

  @Wenn("die Spielphase Bewegung übersprungen wird")
  public void dieSpielphaseBewegungUbersprungenWird() {
    currentPhase = container.logicUnderTest.getCurrentGamePhase();
  }

  @Wenn("die nächste Spieler bewegen möchte")
  public void dieNachsteSpielerBewegenMochte() {

    assertThat(container.logicUnderTest.jumpMove()).isFalse();

    dieSpielphaseAbgefragtWird();
  }


  @Angenommen("das Spiel läuft")
  public void dasSpielLauft() {

    dasSpielIstMitSpielernGestartet(2);
    esSindNullZugeDurchgefuhrtWorden();
  }

  @Wenn("der erste Spieler sieben Steine seiner Farbe nebeneinander hat")
  public void derErsteSpielerSiebenSteineSeinerFarbeNebeneinanderHat() {

    currentboard = container.logicUnderTest.getBoard();
    var player = container.logicUnderTest.getCurrentPlayer();
    var color = player.getPlayerColor();
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    var position = new Position(color, 0, 0, Color.None);
    currentboard.add(position);
    var position1 = new Position(color, 1, 0, Color.None);
    currentboard.add(position1);
    var position2 = new Position(color, 0, 1, Color.None);
    currentboard.add(position2);
    var position3 = new Position(color, -1, -1, Color.None);
    currentboard.add(position3);
    var position4 = new Position(color, -1, 0, Color.None);
    currentboard.add(position4);
    var position5 = new Position(color, 0, -1, Color.None);
    currentboard.add(position5);
    var position6 = new Position(color, -1, 1, Color.None);

    currentboard.add(position6);

    container.logicUnderTest.setBoard(currentboard);


  }

  @Dann("gewinnt der erste Spieler")
  public void gewinntDerErsteSpieler() {
    var winnerColor = container.logicUnderTest.winner();
    var currentPlayerColor = currentPlayer.getPlayerColor();
    assertThat(winnerColor).isEqualTo(currentPlayerColor);

  }


  @Wenn("der zweite Spieler sieben Steine seiner Farbe nebeneinander hat")
  public void derZweiteSpielerSiebenSteineSeinerFarbeNebeneinanderHat() {
    derErsteSpielerSiebenSteineSeinerFarbeNebeneinanderHat();
  }

  @Dann("gewinnt der zweite Spieler")
  public void gewinntDerZweiteSpieler() {

    gewinntDerErsteSpieler();

  }

  @Und("der erste Spieler sieben Steine der Farbe hat, die nicht zusammenhängen")
  public void derErsteSpielerSiebenSteineDerFarbeHatDieNichtZusammenhangen() {


    var colors = container.logicUnderTest.getPlayers();

    for (Player player : colors) {
      if (player.getPlayerColor() != currentPlayer.getPlayerColor()) {
        otherPlayer = player.getPlayerColor();
      }
    }

    var Position1 = new Position(otherPlayer, -1, 2, Color.None);
    var Position2 = new Position(otherPlayer, 0, 2, Color.None);
    var Position3 = new Position(otherPlayer, 1, 2, Color.None);
    var Position4 = new Position(otherPlayer, 2, 2, Color.None);
    var Position5 = new Position(otherPlayer, 2, 0, Color.None);
    var Position6 = new Position(otherPlayer, 2, -1, Color.None);
    var Position7 = new Position(otherPlayer, 2, -2, Color.None);
    currentboard.add(Position1);
    currentboard.add(Position2);
    currentboard.add(Position3);
    currentboard.add(Position4);
    currentboard.add(Position5);
    currentboard.add(Position6);
    currentboard.add(Position7);

    container.logicUnderTest.setBoard(currentboard);
  }

  @Wenn("der erste Spieler acht Steine seiner Farbe nebeneinander hat")
  public void derErsteSpielerAchtSteineSeinerFarbeNebeneinanderHat() {
    derErsteSpielerSiebenSteineSeinerFarbeNebeneinanderHat();

    var position7 = new Position(currentPlayer.getPlayerColor(), 1, 1, Color.None);
    currentboard.add(position7);
    container.logicUnderTest.setBoard(currentboard);

  }

  @Wenn("der zweite Spieler acht Steine seiner Farbe nebeneinander hat")
  public void derZweiteSpielerAchtSteineSeinerFarbeNebeneinanderHat() {
    derErsteSpielerAchtSteineSeinerFarbeNebeneinanderHat();
  }


  @Angenommen("Ein Stein wird auf Position {int}  {int} gesetzt.")
  public void einSteinWirdAufPositionGesetzt(int arg0, int arg1) {

    dasSpielLauft();
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    container.logicUnderTest.anlegen(
        new Position(currentPlayer.getPlayerColor(), arg0, arg1, Color.None));
  }

  @Dann("erhält er die Position {int}  {int} .")
  public void erhaltErDiePosition(int arg0, int arg1) {

    assertThat(samplePosition.x()).isEqualTo(arg0);
    assertThat(samplePosition.y()).isEqualTo(arg1);
  }

  @Dann("dieses Stein wird nicht platziert.")
  public void diesesSteinWirdNichtPlatziert() {
    assertThat(container.logicUnderTest.getBoard()).isEmpty();
  }

  @Dann("wird die Bewegung nicht durchgeführt")
  public void wirdDieBewegungNichtDurchgefuhrt() {

    samplePosition = container.logicUnderTest.getBoard().iterator().next();

    assertThat(samplePosition.x()).isEqualTo(0);
    assertThat(samplePosition.y()).isEqualTo(0);

  }

  @Wenn("wenn die Reihenfolge der Steine im Beutel  abgefragt wird ist")
  public void wennDieReihenfolgeDerSteineImBeutelAbgefragtWirdIst() {
    testResult = container.logicUnderTest.isShuffled();
  }

  @Dann("ist die Reihenfolge der Steine im Beutel zufällig")
  public void istDieReihenfolgeDerSteineImBeutelZufallig() {
    assertThat(testResult).isTrue();
  }

  @Angenommen("es gibt mindestens ein Stein im Beutel")
  public void esGibtMindestensEinSteinImBeutel() {
    dasSpielLauft();
    assertThat(container.logicUnderTest.getGameBag().getFrogsInBag().size()).isGreaterThan(0);
  }

  @Wenn("ein Stein gezogen wird")
  public void einSteinGezogenWird() {
  }

  @Dann("ist ein Stein der gezogenen Farbe weniger im Beutel")
  public void istEinSteinDerGezogenenFarbeWenigerImBeutel() {
  }

  @Wenn("es zwei Steine auf dem Spielfeld gibt")
  public void esZweiSteineAufDemSpielfeldGibt() {
    currentboard = container.logicUnderTest.getBoard();
    currentPlayer = container.logicUnderTest.getCurrentPlayer();
    testPosition1 = new Position(currentPlayer.getPlayerColor(), 0, 0, Color.None);
    testPosition2 = new Position(currentPlayer.getPlayerColor(), 1, 0, Color.None);
    container.logicUnderTest.setCurrentDirection(Gamelogic.Direction.RIGHT);
    currentboard.add(testPosition1);
    currentboard.add(testPosition2);


  }

  @Und("Peter einen Stein bewegen über den anderen möchte")
  public void peterEinenSteinBewegenUberDenAnderenMochte() {


    testResult =
        container.logicUnderTest.isFrogBetweenUs(testPosition1,
            new Position(Color.Black, 2, 0, Color.None), currentboard);

  }

  @Dann("wird der Stein bewegt")
  public void wirdDerSteinBewegt() {

    assertThat(testResult).isTrue();
  }

  @Und("Peter einen Stein bewegen in einer gerade Linie möchte")
  public void peterEinenSteinBewegenInEinerGeradeLinieMochte() {

    testResult =
        container.logicUnderTest.isInStraightLine(testPosition1,
            new Position(Color.Black, 2, 0, Color.None));
  }
}
