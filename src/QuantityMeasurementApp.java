package com.apps.quantitymeasurement;

import java.util.Objects;

public class QuantityMeasurementApp {

    private final double value;
    private final LengthUnit unit;

    public enum LengthUnit {
        INCHES(1.0),
        FEET(12.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double factorToInches;

        LengthUnit(double factorToInches) {
            this.factorToInches = factorToInches;
        }

        public double getFactor() {
            return factorToInches;
        }
    }

    public QuantityMeasurementApp(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    private double toBaseInches() {
        return round(this.value * this.unit.getFactor());
    }

    public QuantityMeasurementApp convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double inches = toBaseInches();
        double converted = inches / targetUnit.getFactor();
        return new QuantityMeasurementApp(round(converted), targetUnit);
    }

    private boolean compare(QuantityMeasurementApp other) {
        return Double.compare(this.toBaseInches(), other.toBaseInches()) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityMeasurementApp)) return false;
        QuantityMeasurementApp that = (QuantityMeasurementApp) o;
        return compare(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseInches());
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }

    private static double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        double base = value * source.getFactor();
        double result = base / target.getFactor();

        return round(result);
    }

    public static boolean demonstrateLengthEquality(QuantityMeasurementApp l1,
                                                    QuantityMeasurementApp l2) {
        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("Lengths cannot be null");
        }
        return l1.equals(l2);
    }

    public static boolean demonstrateLengthComparison(double v1, LengthUnit u1,
                                                      double v2, LengthUnit u2) {
        QuantityMeasurementApp l1 = new QuantityMeasurementApp(v1, u1);
        QuantityMeasurementApp l2 = new QuantityMeasurementApp(v2, u2);
        return demonstrateLengthEquality(l1, l2);
    }

    public static QuantityMeasurementApp demonstrateLengthConversion(double value,
                                                                     LengthUnit from,
                                                                     LengthUnit to) {
        double converted = convert(value, from, to);
        return new QuantityMeasurementApp(converted, to);
    }

    public static QuantityMeasurementApp demonstrateLengthConversion(QuantityMeasurementApp length,
                                                                     LengthUnit toUnit) {
        if (length == null) {
            throw new IllegalArgumentException("Length cannot be null");
        }
        return length.convertTo(toUnit);
    }

    public static void main(String[] args) {
        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));
        System.out.println(convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));
        System.out.println(convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS));
        System.out.println(convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));
    }
}