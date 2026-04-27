package com.apps.quantitymeasurement;

enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factorToKg;

    WeightUnit(double factorToKg) {
        this.factorToKg = factorToKg;
    }

    public double toBase(double value) {
        return value * factorToKg;
    }

    public double fromBase(double baseValue) {
        return baseValue / factorToKg;
    }
}

final class QuantityWeight {
    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");
        this.value = value;
        this.unit = unit;
    }

    private double toBase() {
        return unit.toBase(value);
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        double base = toBase();
        double converted = targetUnit.fromBase(base);
        return new QuantityWeight(converted, targetUnit);
    }

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null) throw new IllegalArgumentException("Other weight cannot be null");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");

        double sumBase = this.toBase() + other.toBase();
        double result = targetUnit.fromBase(sumBase);
        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;
        return Math.abs(this.toBase() - other.toBase()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(Math.round(toBase() / EPSILON));
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

public class QuantityMeasurementApp {

    public static boolean demonstrateWeightEquality(QuantityWeight w1, QuantityWeight w2) {
        return w1.equals(w2);
    }

    public static QuantityWeight demonstrateWeightConversion(double value, WeightUnit from, WeightUnit to) {
        return new QuantityWeight(value, from).convertTo(to);
    }

    public static QuantityWeight demonstrateWeightConversion(QuantityWeight weight, WeightUnit to) {
        return weight.convertTo(to);
    }

    public static QuantityWeight demonstrateWeightAddition(QuantityWeight w1, QuantityWeight w2) {
        return w1.add(w2);
    }

    public static QuantityWeight demonstrateWeightAddition(QuantityWeight w1, QuantityWeight w2, WeightUnit targetUnit) {
        return w1.add(w2, targetUnit);
    }

    public static void main(String[] args) {
        QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);

        System.out.println("Equality: " + demonstrateWeightEquality(w1, w2));

        System.out.println("Conversion: " +
                demonstrateWeightConversion(2, WeightUnit.POUND, WeightUnit.KILOGRAM));

        System.out.println("Addition: " +
                demonstrateWeightAddition(w1, w2));

        System.out.println("Addition with target: " +
                demonstrateWeightAddition(w1, w2, WeightUnit.GRAM));
    }
}