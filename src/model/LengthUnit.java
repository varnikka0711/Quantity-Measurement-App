package model;

/**
 * Standalone LengthUnit Enum (UC8)
 * Responsible for ALL conversion logic.
 * Base unit: FEET
 */
public enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor; // relative to FEET

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    // Convert this unit → base unit (FEET)
    public double convertToBaseUnit(double value) {
        validate(value);
        return round(value * conversionFactor);
    }

    // Convert base unit (FEET) → this unit
    public double convertFromBaseUnit(double baseValue) {
        validate(baseValue);
        return round(baseValue / conversionFactor);
    }

    private void validate(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}