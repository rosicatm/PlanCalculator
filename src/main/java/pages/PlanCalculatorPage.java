package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlanCalculatorPage extends BasePage {
    private static final String SERVICE = "(//div[@class='MuiSelect-root MuiSelect-select MuiSelect-selectMenu MuiSelect-outlined MuiInputBase-input MuiOutlinedInput-input'])[1]";
    private static final String ROAD_LENGTH_SLIDER = "(//span[contains(@class, 'MuiSlider-thumb')])[1]";
    private static final String NUMBER_OF_SIGNALIZED_INTERSECTIONS_SLIDER = "(//span[contains(@class, 'MuiSlider-thumb')])[2]";

    private static final String METRIC_UNIT = "//div[text()='METRIC']";
    private static final String IMPERIAL_UNIT = "//div[text()='IMPERIAL']";

    private static final double WEIGHT_LENGTH = 0.01;
    private static final double WEIGHT_JUNCTIONS = 0.02;
    private static final double XWU_REF = 5.625;
    private static final String SAAS_PRICE = "//div[@data-testid='plan-price-line-1']";
    private static final String SUGGESTED_PLAN = "//div[@class='PlanPricing_suggestedPlan-planName__31ZTG']";
    private static final String INVALID_METRIC_PROVIDED_ERROR_MESSAGE = "Invalid metric provided: ";
    private static final String NO_PLAN_DETAILS_FOUND_FOR_THE_FOLLOWING_PLAN_ERROR_MESSAGE = "No plan details found for the following plan: ";
    private static final String NO_SAAS_PRICE_FOUND_IN_THE_FOLLOWING_TEXT_ERROR_MESSAGE = "No SaaS Price found in the following text: ";
    private static final String WRONG_SERVICE_SELECTED_ERROR_MESSAGE = "Wrong service selected.";

    private static WebDriver driver;

    private static WebDriverWait wait;

    public PlanCalculatorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Selecting service
    public void selectService(String service) {
        WebElement serviceDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SERVICE)));
        serviceDropdownElement.click();
        By serviceOption = By.xpath("//li[@data-value='" + service + "']");
        WebElement serviceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(serviceOption));
        serviceElement.click();
    }

    // Selecting the metric
    public void selectMetric(String metric) {
        WebElement metricButton;
        if (metric.equalsIgnoreCase("METRIC")) {
            metricButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(METRIC_UNIT)));
        } else if (metric.equalsIgnoreCase("IMPERIAL")) {
            metricButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(IMPERIAL_UNIT)));
        } else {
            throw new IllegalArgumentException(INVALID_METRIC_PROVIDED_ERROR_MESSAGE + metric);
        }
        metricButton.click();
    }

    // Setting the road length using the slider
    public void setRoadLength(int length, String metric) {
        WebElement roadLengthSlider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ROAD_LENGTH_SLIDER)));
        Actions actions = new Actions(driver);
        actions.clickAndHold(roadLengthSlider).moveByOffset(calculateSliderOffset(length, metric), 0).release().perform();
    }

    // Calculating the position of the thumb based on the unit metric  option
    private int calculateSliderOffset(int value, String metric) {
        int minValue = 1;
        int maxValue;
        if (metric.equals("IMPERIAL")) {
            maxValue = 621;
        } else {
            maxValue = 1000;
        }
        int sliderWidth = 450;
        value = Math.max(minValue, Math.min(value, maxValue));
        double percentage = (double) (value - minValue) / (maxValue - minValue);
        int offset = (int) Math.round(percentage * sliderWidth);
        return offset;
    }

    // Setting the number of signalized intersections using the slider

    public void setNumberOfSignalizedIntersections(int intersections) {
        WebElement intersectionsSlider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NUMBER_OF_SIGNALIZED_INTERSECTIONS_SLIDER)));
        Actions actions = new Actions(driver);
        actions.clickAndHold(intersectionsSlider)
                .moveByOffset(calculateSliderOffsetIntersections(intersections), 0)
                .release()
                .perform();
    }
    // Calculating the position of the thumb

    private int calculateSliderOffsetIntersections(int value) {
        int minValue = 1;
        int maxValue = 1000;
        int sliderWidth = 450;
        value = Math.max(minValue, Math.min(value, maxValue));
        double percentage = (double) (value - minValue) / (maxValue - minValue);
        int offset = (int) Math.round(percentage * sliderWidth);
        return offset;
    }


    // Retrieving the suggested plan from the displayed page/screen
    public String getSuggestedPlan() {
        WebElement suggestedPlanElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUGGESTED_PLAN)));
        return suggestedPlanElement.getText();
    }

    // Calculating XWU based on traffic complexity
    public double calculateXWU(double trafficComplexity) {
        return Math.ceil(XWU_REF * trafficComplexity);
    }

    // Calculating the SaaS price based on XWU and the suggested plan
    public double calculateSaaSPrice(double xwu, String suggestedPlan) {
        PlanDetails planDetails = PlanDetails.PLAN_DETAILS.get(suggestedPlan);
        if (planDetails == null) {
            throw new IllegalArgumentException(NO_PLAN_DETAILS_FOUND_FOR_THE_FOLLOWING_PLAN_ERROR_MESSAGE + suggestedPlan);
        }

        double flatRate = planDetails.getFlatRate();
        double unitRate = planDetails.getUnitRate();
        double unitsIncluded = planDetails.getUnitsIncluded();

        double saasPrice;
        if (xwu <= unitsIncluded) {
            saasPrice = flatRate;
        } else {
            saasPrice = flatRate + (xwu - unitsIncluded) * unitRate;
        }
        return saasPrice;
    }

    // Calculating traffic complexity based on road length and number of signalized intersections
    public double calculateTrafficComplexity(double roadLength, double numSignalizedIntersections) {
        return WEIGHT_LENGTH * roadLength + WEIGHT_JUNCTIONS * numSignalizedIntersections;
    }

    // Calculating the plan based on the xwu and the selected service
    public static String calculatePlan(double xwu, String selectedService) {
        String plan = "";
        switch (selectedService) {
            case X_WAY_PULSE_OPTION:
                plan = calculatePlanForXWayPulse(xwu);
                break;
            case X_WAY_PULSE_TWIN_OPTION:
                plan = calculatePlanForXWayPulseTwin(xwu);
                break;
            case X_WAY_PULSE_TWIN_NEURAL_OPTION:
                plan = calculatePlanForXWayPulseTwinNeural(xwu);
                break;
            default:
                System.out.println(WRONG_SERVICE_SELECTED_ERROR_MESSAGE);
        }
        return plan;
    }

    //Calculating the X Way Pulse plan based on the xwu
    private static String calculatePlanForXWayPulse(double xwu) {
        if (xwu <= 28) {
            return X_WAY_PULSE_STARTER;
        } else if (xwu <= 56) {
            return X_WAY_PULSE_STANDARD;
        } else {
            return X_WAY_PULSE_PRO;
        }
    }

    // Calculating the X Way Pulse + Twin plan based on the xwu
    private static String calculatePlanForXWayPulseTwin(double xwu) {
        if (xwu <= 28) {
            return X_WAY_PULSE_TWIN_STARTER;
        } else if (xwu <= 56) {
            return X_WAY_PULSE_TWIN_STANDARD;
        } else {
            return X_WAY_PULSE_TWIN_PRO;
        }
    }

    // Calculating the X Way Pulse + Twin + Neural plan based on the xwu
    private static String calculatePlanForXWayPulseTwinNeural(double xwu) {
        if (xwu <= 28) {
            return X_WAY_PULSE_TWIN_NEURAL_STARTER;
        } else if (xwu <= 56) {
            return X_WAY_PULSE_TWIN_NEURAL_STANDARD;
        } else {
            return X_WAY_PULSE_TWIN_NEURAL_PRO;
        }
    }

    // Retrieving the SaaS price value from the displayed screen/page
    public double getSaaSPriceValue() {
        WebElement saasPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SAAS_PRICE)));
        String saasPriceText = saasPriceElement.getText();
        Pattern pattern = Pattern.compile("\\$\\s*([\\d,\\s]+)");
        Matcher matcher = pattern.matcher(saasPriceText);
        if (matcher.find()) {
            String priceValue = matcher.group(1).replaceAll("[\\s,]", "");
            return Double.parseDouble(priceValue);
        } else {
            throw new IllegalArgumentException(NO_SAAS_PRICE_FOUND_IN_THE_FOLLOWING_TEXT_ERROR_MESSAGE + saasPriceText);
        }
    }

    // Getting the number of units included for the suggested plan
    public static int getUnitsIncluded(String suggestedPlan) {
        PlanDetails planDetails = PlanDetails.PLAN_DETAILS.get(suggestedPlan);
        if (planDetails == null) {
            throw new IllegalArgumentException(NO_PLAN_DETAILS_FOUND_FOR_THE_FOLLOWING_PLAN_ERROR_MESSAGE + suggestedPlan);
        }
        return planDetails.getUnitsIncluded();
    }
}