package seedu.address.model.pet;

public class Height {

    public static final String UNIT = "cm";
    public static final String MESSAGE_USAGE =
            "The height should be in " + UNIT + " and be a non-negative decimal number, such as 22.8";

    private final double value;

    public Height(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
                && value == ((Height) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return value + " " + UNIT;
    }

    public double getValue() {
        return value;
    }

}
