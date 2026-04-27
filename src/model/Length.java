package com.apps.quantitymeasurement;

/**
 * QuantityMeasurementApp (UC8)
 * Public API remains SAME (backward compatible)
 */
public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
        return length.convertTo(toUnit);
    }

    // UC6
    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        return l1.add(l2);
    }

    // UC7
    public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit targetUnit) {
        return l1.add(l2, targetUnit);
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(demonstrateLengthAddition(l1, l2)); // 2 FEET
        System.out.println(demonstrateLengthAddition(l1, l2, LengthUnit.INCHES)); // 24 INCHES
        System.out.println(demonstrateLengthAddition(l1, l2, LengthUnit.YARDS)); // ~0.67 YARDS
    }
}