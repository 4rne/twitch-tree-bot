package test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.bot.tree.*;

class EstimateTest {

    @Test
    void random() {
        assertTrue(new Estimate("!estimate").toString().indexOf("Kappa") != -1);
    }

    @Test
    void realEstimateOnePersonFourHours() {
        assertTrue(new Estimate("!estimate 1 4").toString().indexOf("Kappa") == -1);
        assertTrue(new Estimate("!estimate 1 4").toString().indexOf("2735") != -1);
        assertTrue(new Estimate("!estimate 1 4").toString().indexOf("5015") != -1);
    }

    @Test
    void realEstimateThreePeopleSixHours() {
        assertTrue(new Estimate("!estimate 3 6").toString().indexOf("Kappa") == -1);
        assertTrue(new Estimate("!estimate 3 6").toString().indexOf("10085") != -1);
        assertTrue(new Estimate("!estimate 3 6").toString().indexOf("12365") != -1);
        assertTrue(new Estimate("!estimate 3 6 5").toString().indexOf("Kappa") == -1);
        assertTrue(new Estimate("!estimate -6 -4").toString().indexOf("Kappa") == -1);
    }

    @Test
    void realEstimateBrokenParams() {
        assertTrue(new Estimate("!estimate 3 1").toString().indexOf("Kappa") != -1);
        assertTrue(new Estimate("!estimate 3").toString().indexOf("Kappa") != -1);
        assertTrue(new Estimate("!estimate 0 6").toString().indexOf("Kappa") != -1);
        assertTrue(new Estimate("!estimate 7 4").toString().indexOf("Kappa") != -1);
        assertTrue(new Estimate("!estimate -7 4").toString().indexOf("Kappa") != -1);
    }
}

