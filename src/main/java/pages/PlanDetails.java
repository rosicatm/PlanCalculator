package pages;

import java.util.HashMap;
import java.util.Map;

public class PlanDetails extends BasePage {
    private final double flatRate;
    private final double unitRate;
    private final int unitsIncluded;

    public PlanDetails(double flatRate, double unitRate, int unitsIncluded) {
        this.flatRate = flatRate;
        this.unitRate = unitRate;
        this.unitsIncluded = unitsIncluded;
    }

    public double getFlatRate() {
        return flatRate;
    }

    public double getUnitRate() {
        return unitRate;
    }

    public int getUnitsIncluded() {
        return unitsIncluded;
    }

    // Storing plan details by plan name
    public static final Map<String, PlanDetails> PLAN_DETAILS = new HashMap<>();

    static {
        PLAN_DETAILS.put(X_WAY_PULSE_STARTER, new PlanDetails(980.0, 70.0, 14));
        PLAN_DETAILS.put(X_WAY_PULSE_STANDARD, new PlanDetails(1430.0, 65.0, 22));
        PLAN_DETAILS.put(X_WAY_PULSE_PRO, new PlanDetails(3000.0, 60.0, 50));
        PLAN_DETAILS.put(X_WAY_PULSE_TWIN_STARTER, new PlanDetails(1260.0, 90.0, 14));
        PLAN_DETAILS.put(X_WAY_PULSE_TWIN_STANDARD, new PlanDetails(1760.0, 80.0, 22));
        PLAN_DETAILS.put(X_WAY_PULSE_TWIN_PRO, new PlanDetails(3750.0, 75.0, 50));
        PLAN_DETAILS.put(X_WAY_PULSE_TWIN_NEURAL_STARTER, new PlanDetails(1680.0, 120.0, 14));
        PLAN_DETAILS.put(X_WAY_PULSE_TWIN_NEURAL_STANDARD, new PlanDetails(2530.0, 115.0, 22));
        PLAN_DETAILS.put(X_WAY_PULSE_TWIN_NEURAL_PRO, new PlanDetails(5250.0, 105.0, 50));
    }
}
