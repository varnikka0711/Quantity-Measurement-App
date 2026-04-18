public class QuantityMeasurementApp {

    // ----------- GENERIC LENGTH CLASS -----------
    static class Length {

        private double value;
        private LengthUnit unit;

        // ENUM for units (base = inches)
        public enum LengthUnit {
            FEET(12.0),
            INCHES(1.0);

            private final double conversionFactor;

            LengthUnit(double conversionFactor) {
                this.conversionFactor = conversionFactor;
            }

            public double getConversionFactor() {
                return conversionFactor;
            }
        }

        // Constructor
        public Length(double value, LengthUnit unit) {
            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Value must be numeric");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert everything to base unit (inches)
        private double toBaseUnit() {
            return this.value * this.unit.getConversionFactor();
        }

        // Compare logic
        public boolean compare(Length other) {
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        // equals override
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;

            Length other = (Length) obj;
            return compare(other);
        }
    }

    // ----------- DEMO METHODS -----------

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static void demonstrateFeetEquality() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        System.out.println("Feet vs Feet: " + demonstrateLengthEquality(l1, l2));
    }

    public static void demonstrateInchesEquality() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.INCHES);

        System.out.println("Inches vs Inches: " + demonstrateLengthEquality(l1, l2));
    }

    public static void demonstrateFeetInchesComparison() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        System.out.println("Feet vs Inches: " + demonstrateLengthEquality(l1, l2));
    }

    // ----------- MAIN METHOD -----------

    public static void main(String[] args) {

        demonstrateFeetEquality();          // true
        demonstrateInchesEquality();        // true
        demonstrateFeetInchesComparison();  // true

        // Extra tests
        System.out.println("1 ft vs 2 ft: " +
                new Length(1.0, Length.LengthUnit.FEET)
                        .equals(new Length(2.0, Length.LengthUnit.FEET))); // false

        System.out.println("12 in vs 1 ft: " +
                new Length(12.0, Length.LengthUnit.INCHES)
                        .equals(new Length(1.0, Length.LengthUnit.FEET))); // true
    }
}