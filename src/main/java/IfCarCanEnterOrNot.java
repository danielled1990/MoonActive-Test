public class IfCarCanEnterOrNot {
    private String response;
    private Boolean can;


    public IfCarCanEnterOrNot(String response, Boolean can) {
        this.response = response;
        this.can = can;
    }

    public String getResponse() {
        return response;
    }

    public Boolean getCan() {
        return can;
    }

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
