/**
 * this class return the result wheather a car can enter a not as a IfCarCanEnterOrNot class
 * the class has two elemets: a string that save the error message we show to the client
 * A boolean witch let us know weather we should open the door or not. we save the boolean value on the DB so it will indicate up wathere the car was entered or not.
 * False - the car won't enter. True - the car can enter
 */
public class IfCarCanEnterOrNot {
    private String response;
    private Boolean can;


    /**
     * @param response
     * @param can
     */
    public IfCarCanEnterOrNot(String response, Boolean can) {
        this.response = response;
        this.can = can;
    }

    /**
     * @return
     */
    public String getResponse() {
        return response;
    }

    /**
     * @return
     */
    public Boolean getCan() {
        return can;
    }

    /**
     * @param code
     * @return
     */
    public static IfCarCanEnterOrNot checkIfVihicleCanEnterByGivenCode(int code) {
        final CodeEnumForEntering[] vals = CodeEnumForEntering.values();
        String response = "";
        boolean canEnter = false;
        switch (vals[code]) {
            case PUBLIC_TRANSPORTATION:
                response = StringUtils.PUBLIC_TRANSPORTATION;
                canEnter = false;
                break;
            case LAW_ENFORCEMENT:
                response = StringUtils.LAW_ENFORCEMENT;
                canEnter = false;
                break;
            case NO_LETTERS:
                response = StringUtils.NO_LETTERS;
                canEnter = false;
                break;

            case VALID_LP:
                response = StringUtils.VALID_NUMBER;
                canEnter = true;
                break;

            default:

        }
        if (!canEnter) {
            response = response + StringUtils.CANNOT_ENTER;
        }
        response = String.format("Code:%d, %s", code, response);

        return new IfCarCanEnterOrNot(response, canEnter);
    }
}
