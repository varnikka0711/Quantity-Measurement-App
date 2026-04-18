public class QuantityMeasurementApp {

    // FEET CLASS
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // INCHES CLASS
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // DEMO METHODS
    public static void demonstrateFeetEquality() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Feet Equal? " + f1.equals(f2));
    }

    public static void demonstrateInchesEquality() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);

        System.out.println("Inches Equal? " + i1.equals(i2));
    }

    // MAIN METHOD
    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchesEquality();

        // Manual test cases
        System.out.println("Feet 1 vs 2: " + new Feet(1.0).equals(new Feet(2.0)));
        System.out.println("Inch null test: " + new Inches(1.0).equals(null));
    }
}