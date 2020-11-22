package math;

public class Ventilator {
    // IMPORTANT: BASE UNIT MUST BE INCHES OR METERS (Currently inches)
    // Try to get other units working at your own risk... >:)

    private double volumeChange;
    private double diameter;
    private double height;

    /**
     * Creates a Ventilator from default specs:<br>
     * 
     * <ul>
     *  <li>Default breath size: 500 mL</li>
     *  <li>Default tank diameter: 5 inches</li>
     *  <li>Default tank length: 6 inches</li>
     * </ul>
     * 
     * Mostly used for testing methods.
     */
    public Ventilator() {
        this.volumeChange = 0.5 * Units.LITER;
        this.diameter = 5 * Units.INCH;
        this.height = 6 * Units.INCH;
    }

    /**
     * Creates a Ventilator from specified specs:
     * 
     * @param breathSize Patient breath size
     * @param diameter   Diameter of tank
     * @param length     Length of tank
     */
    public Ventilator(double breathSize, double diameter, double height) {
        this.volumeChange = breathSize;
        this.diameter = diameter;
        this.height = height;
    }

    /**
     * Returns the change in circumference of the tank when deflated by a volume <code>volumeChange</code>
     * (assuming tank is a perfect cylinder throughout deflation.)
     * @return Circumference change
     */
    public double changeCirc() {
        return (Math.PI * diameter) - Math.sqrt(((Math.PI * diameter) * (Math.PI * diameter)) - ((4*Math.PI*volumeChange)/height));
    }
}