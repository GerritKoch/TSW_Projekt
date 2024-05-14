package steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;

public class StepExample {

  String name;

  @Wenn("{word} lehrt")
  public void lehrt(String name) {
    this.name = name;
  }

  @Dann("wird ein Beispiel ausgefürt")
  public void wirdEinBeispielAusgefuert() {
    System.out.println("Vrumm");
  }

  @Und("es läuft( nicht)")
  public void esLauft() {
    assertThat(name).isEqualTo("Kai");
  }

  @Angenommen("es gibt mindestens {int} Frösche auf dem Spielfeld")
  public void esGibtMindestensFroscheAufDemSpielfeld(int arg0) {
  }

  @Und("Feld {double} hat keinen Frosch")
  public void feldHatKeinenFrosch(int arg0, int arg1) {
  }

  @Und("Feld {double} hat mindestens {int} Grenzen")
  public void feldHatMindestensGrenzen(int arg0, int arg1, int arg2) {
  }

  @Wenn("abgefragt wird ob das Anlegen auf Feld {double} möglich ist")
  public void abgefragtWirdObDasAnlegenAufFeldMoglichIst(int arg0, int arg1) {
  }

  @Dann("ist das Anlegen möglich")
  public void istDasAnlegenMoglich() {
  }

  @Und("Feld {int}, {int} hat {int} Grenze zu Feld {int}, {int}")
  public void feldHatGrenzeZuFeld(int arg0, int arg1, int arg2, int arg3, int arg4) {
  }

  @Und("Feld {int}, {int} hat {int} Grenzen zu Feld {int}, {int} und Feld {int}, {int}")
  public void feldHatGrenzenZuFeldUndFeld(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
  }

  @Dann("ist das Anlegen nicht möglich")
  public void istDasAnlegenNichtMoglich() {
  }

  @Und("es wird eine Fehlermeldung {string} ausgegeben")
  public void esWirdEineFehlermeldungAusgegeben(String arg0) {
  }
}
