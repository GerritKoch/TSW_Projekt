package steps;

import static org.assertj.core.api.Assertions.assertThat;

import aof.Gamelogic;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

public class BagSteps {

    private LogicContainer container;

    public BagSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    int frösche;

    @Angenommen("es läuft kein Spiel")
    public void es_läuft_kein_spiel() {
        container.logicUnderTest = new Gamelogic();
    }

    @Angenommen("der (erste)(zweite)(dritte)(vierte) Spieler hat {int} Frösche gezogen")
    public void der_erste_spieler_hat_frösche_gezogen(Integer anzahl) {
        for(int i = 0; i < anzahl; ++i){
            container.logicUnderTest.takeFrogFromBag();
        }
    }

    @Wenn("der Beutelinhalt abgefragt wird")
    public void der_beutelinhalt_abgefragt_wird() {
        frösche = container.logicUnderTest.frogsInBag();
    }

    @Dann("sind {int} Frösche im Beutel")
    public void sind_frösche_im_beutel(Integer erwarteteFrösche) {
        assertThat(frösche)
                .isEqualTo(erwarteteFrösche);
    }


}

