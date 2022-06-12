package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bot.tree.*;

class EstimateTest {

    @Test
    void random() {
        assertNotEquals(new Estimate("!estimate").toString().indexOf("Kappa"), -1);
    }

    @Test
    void realEstimateOnePersonFourHours() {
        assertEquals(new Estimate("!estimate 1 4").toString().indexOf("Kappa"), -1);
        assertNotEquals(new Estimate("!estimate 1 4").toString().indexOf("2735"), -1);
        assertNotEquals(new Estimate("!estimate 1 4").toString().indexOf("5015"), -1);
    }

    @Test
    void realEstimateThreePeopleSixHours() {
        assertEquals(new Estimate("!estimate 3 6").toString().indexOf("Kappa"), -1);
        assertNotEquals(new Estimate("!estimate 3 6").toString().indexOf("10085"), -1);
        assertNotEquals(new Estimate("!estimate 3 6").toString().indexOf("12365"), -1);
        assertEquals(new Estimate("!estimate 3 6 5").toString().indexOf("Kappa"), -1);
        assertEquals(new Estimate("!estimate -6 -4").toString().indexOf("Kappa"), -1);
    }

    @Test
    void realEstimateBrokenParams() {
        assertNotEquals(new Estimate("!estimate 3 1").toString().indexOf("Kappa"), -1);
        assertNotEquals(new Estimate("!estimate 3").toString().indexOf("Kappa"), -1);
        assertNotEquals(new Estimate("!estimate 0 6").toString().indexOf("Kappa"), -1);
        assertNotEquals(new Estimate("!estimate 7 4").toString().indexOf("Kappa"), -1);
        assertNotEquals(new Estimate("!estimate -7 4").toString().indexOf("Kappa"), -1);
    }
}

