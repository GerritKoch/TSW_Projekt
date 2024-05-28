package unittest;

import aof.Bag;
import de.fhkiel.tsw.armyoffrogs.Color;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    void given2Players_whenTakingAllFrogsFromABag_thenThereShouldBe10FrogsForEachPlayer() {
        final Color player1 = Color.Green;
        final Color player2 = Color.Red;
        final Bag bagUnderTest = new Bag(new Color[]{player1, player2});

        final Map<Color, Integer> frogsPerColor = new HashMap<>();
        while(bagUnderTest.getFrogs() > 0){
            Color frog = bagUnderTest.takeFrog();
            frogsPerColor.put(
                    frog,
                    frogsPerColor.getOrDefault(frog, 0) + 1);
        }

        assertThat(frogsPerColor)
                .containsOnlyKeys(player1, player2);
        final int expectedFrogsForPlayer = 10;
        assertThat(frogsPerColor)
                .containsEntry(player1, expectedFrogsForPlayer)
                .containsEntry(player2, expectedFrogsForPlayer);
    }

    @Test
    void givenBagWith40Frogs_whenTaking1_then39SchouldBeLeft() {
        final Bag bagUnderTest = new Bag();

        bagUnderTest.takeFrog();

        final int expectedFrogs = 39;
        assertThat(bagUnderTest.getFrogs())
                .isEqualTo(expectedFrogs);
    }
}