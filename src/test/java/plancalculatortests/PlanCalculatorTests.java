package plancalculatortests;

import core.BaseClass;
import org.junit.jupiter.api.Test;
import pages.PlanCalculatorPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanCalculatorTests extends BaseClass {
    private static final String X_WAY_UNITS = " X Way Units";

    //The following tests cover all plans with their extended names.
    // Unit metric is set to "METRIC" as there is an issue with the calculations for "IMPERIAL"

    @Test
    public void testXWayPulseStarterPlan() {

        // Selecting service
        calculatorPage.selectService(X_WAY_PULSE_OPTION);

        // Selecting unit metric
        calculatorPage.selectMetric(METRIC_OPTION);

        // Setting Road Length (not very accurate calculation)
        // First issue is that if I set the road length to 140, the thumb is going to 143
        // Unit metric option needs to be added as a parameter in order to calculate the right value for metric or imperial

        calculatorPage.setRoadLength(140, METRIC_OPTION);

        // Setting Number of signalized intersections
        calculatorPage.setNumberOfSignalizedIntersections(140);

        // Modified the road length and number of signalized intersections that corresponds to the one on the thumb
        // in order for the calculations to be the same
        int roadLength = 143;
        int numSignalizedIntersections = 143;

        // Calculating XWU using the retrieved values
        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        // Calculating the Suggested plan
        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_OPTION);

        // Getting the number of units included for the suggested plan
        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        // Getting the number of additional units beyond the UnitsIncluded
        int additionalUnits = (int) xwu - unitsIncluded;

        //Asserting Calculated suggested plan equals the one displayed on the screen
        assertSuggestedPlan(suggestedPlan, additionalUnits);

        //Asserting Calculated SaaS price equals the one displayed on the screen
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseStandardPlan() {
        calculatorPage.selectService(X_WAY_PULSE_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(191, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(253);

        int roadLength = 193;
        int numSignalizedIntersections = 256;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseProPlan() {
        calculatorPage.selectService(X_WAY_PULSE_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(386, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(657);

        int roadLength = 387;
        int numSignalizedIntersections = 658;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseTwinStarterPlan() {
        calculatorPage.selectService(X_WAY_PULSE_TWIN_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(550, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(846);

        int roadLength = 552;
        int numSignalizedIntersections = 849;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_TWIN_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseTwinStandardPlan() {
        calculatorPage.selectService(X_WAY_PULSE_TWIN_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(573, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(142);

        int roadLength = 576;
        int numSignalizedIntersections = 145;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_TWIN_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseTwinProPlan() {
        calculatorPage.selectService(X_WAY_PULSE_TWIN_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(786, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(393);

        int roadLength = 789;
        int numSignalizedIntersections = 396;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_TWIN_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseTwinNeuralStarterPlan() {
        calculatorPage.selectService(X_WAY_PULSE_TWIN_NEURAL_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(78, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(100);

        int roadLength = 81;
        int numSignalizedIntersections = 103;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_TWIN_NEURAL_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseTwinNeuralStandardPlan() {
        calculatorPage.selectService(X_WAY_PULSE_TWIN_NEURAL_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(415, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(231);

        int roadLength = 416;
        int numSignalizedIntersections = 234;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_TWIN_NEURAL_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    @Test
    public void testXWayPulseTwinNeuralProPlan() {
        calculatorPage.selectService(X_WAY_PULSE_TWIN_NEURAL_OPTION);
        calculatorPage.selectMetric(METRIC_OPTION);
        calculatorPage.setRoadLength(680, METRIC_OPTION);
        calculatorPage.setNumberOfSignalizedIntersections(970);

        int roadLength = 683;
        int numSignalizedIntersections = 971;

        double trafficComplexity = calculatorPage.calculateTrafficComplexity(roadLength, numSignalizedIntersections);
        double xwu = calculatorPage.calculateXWU(trafficComplexity);

        String suggestedPlan = PlanCalculatorPage.calculatePlan(xwu, X_WAY_PULSE_TWIN_NEURAL_OPTION);

        int unitsIncluded = calculatorPage.getUnitsIncluded(suggestedPlan);

        int additionalUnits = (int) xwu - unitsIncluded;

        assertSuggestedPlan(suggestedPlan, additionalUnits);
        assertSaaSPrice(xwu, suggestedPlan);
    }

    private void assertSuggestedPlan(String suggestedPlan, int additionalUnits) {
        String expectedSuggestedPlan = suggestedPlan + " + " + additionalUnits + X_WAY_UNITS;
        String displayedSuggestedPlan = calculatorPage.getSuggestedPlan();
        assertEquals(expectedSuggestedPlan, displayedSuggestedPlan);
    }

    private void assertSaaSPrice(double xwu, String suggestedPlan) {
        double expectedSaaSPrice = calculatorPage.calculateSaaSPrice(xwu, suggestedPlan);
        double displayedSaaSPrice = calculatorPage.getSaaSPriceValue();
        assertEquals(expectedSaaSPrice, displayedSaaSPrice);
    }
}
