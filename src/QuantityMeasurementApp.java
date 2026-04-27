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
        return value * unit.getFactor();
    }

    private static double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    public QuantityMeasurementApp convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double inches = toBaseInches();
        double converted = inches / targetUnit.getFactor();
        return new QuantityMeasurementApp(round(converted), targetUnit);
    }

    // UC6: default addition (result in this.unit)
    public QuantityMeasurementApp add(QuantityMeasurementApp other) {
        if (other == null) {
            throw new IllegalArgumentException("Other length cannot be null");
        }
        double sumInInches = this.toBaseInches() + other.toBaseInches();
        double result = sumInInches / this.unit.getFactor();
        return new QuantityMeasurementApp(round(result), this.unit);
    }

    // UC7: addition with explicit target unit
    public QuantityMeasurementApp add(QuantityMeasurementApp other, LengthUnit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Other length cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double sumInInches = this.toBaseInches() + other.toBaseInches();
        double result = sumInInches / targetUnit.getFactor();

        return new QuantityMeasurementApp(round(result), targetUnit);
    }

    private boolean compare(QuantityMeasurementApp other) {
        return Double.compare(round(this.toBaseInches()), round(other.toBaseInches())) == 0;
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
        return Objects.hash(round(toBaseInches()));
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }

    // Static conversion API (UC5)
    public static double convert(double value, LengthUnit source, LengthUnit target) {
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

    public static QuantityMeasurementApp demonstrateLengthConversion(double value,
                                                                     LengthUnit from,
                                                                     LengthUnit to) {
        return new QuantityMeasurementApp(convert(value, from, to), to);
    }

    public static QuantityMeasurementApp demonstrateLengthConversion(QuantityMeasurementApp length,
                                                                     LengthUnit toUnit) {
        if (length == null) {
            throw new IllegalArgumentException("Length cannot be null");
        }
        return length.convertTo(toUnit);
    }

    // UC6 demo
    public static QuantityMeasurementApp demonstrateLengthAddition(QuantityMeasurementApp l1,
                                                                   QuantityMeasurementApp l2) {
        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("Lengths cannot be null");
        }
        return l1.add(l2);
    }

    // UC7 demo
    public static QuantityMeasurementApp demonstrateLengthAddition(QuantityMeasurementApp l1,
                                                                   QuantityMeasurementApp l2,
                                                                   LengthUnit targetUnit) {
        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("Lengths cannot be null");
        }
        return l1.add(l2, targetUnit);
    }

    public static void main(String[] args) {
        QuantityMeasurementApp l1 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        QuantityMeasurementApp l2 = new QuantityMeasurementApp(12.0, LengthUnit.INCHES);

        System.out.println(demonstrateLengthAddition(l1, l2, LengthUnit.FEET));   // 2.00 FEET
        System.out.println(demonstrateLengthAddition(l1, l2, LengthUnit.INCHES)); // 24.00 INCHES
        System.out.println(demonstrateLengthAddition(l1, l2, LengthUnit.YARDS));  // ~0.67 YARDS
    }
}