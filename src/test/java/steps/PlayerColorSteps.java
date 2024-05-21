package steps;

import static org.assertj.core.api.Assertions.assertThat;

import de.fhkiel.tsw.armyoffrogs.Color;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

public class PlayerColorSteps {

    LogicContainer container;

    public PlayerColorSteps(LogicContainer container) {
        this.container = container;
    }

    Color[] player;

    @Wenn("die Spielerfarben abgefragt werden")
    public void die_spielerfarben_abgefragt_werden() {
        player = container.logicUnderTest.players();
    }

    @Dann("(ist )die {word} Farbe {word}")
    public void farbe_an_position(String positionString, String colorString) {
        int position = switch (positionString){
            case "erste" -> 0;
            case "zweite" -> 1;
            case "dritte" -> 2;
            case "vierte" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + positionString);
        };
        Color color = switch (colorString){
            case "Rot" -> Color.Red;
            case "Grün" -> Color.Green;
            case "Blau" -> Color.Blue;
            case "Weiß" -> Color.White;
            default -> throw new IllegalStateException("No color named: " + colorString);
        };
        assertThat(player).isNotNull();
        assertThat(player[position]).isEqualTo(color);
    }
}
