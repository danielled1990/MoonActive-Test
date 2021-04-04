/**
 * CheckIfLicensePlateIsValid a call with the method CheckLicenePlate that checks if a lp is valid
 */
public class CheckIfLicensePlateIsValid {


    /**
     * @param lp
     * @return
     */
    public int CheckLicenePlate(String lp) {
        Log.WriteToLogFile(Log.getInstance().getlp(), "checking if license plate is valid");
        String lpNumber = lp.replace("-", "").trim();
        int code;
        if (lpNumber.charAt((lpNumber.length() - 1)) == StringUtils.PUBLIC_TRANSPORTATION_SIX_CHAR
                || lpNumber.charAt((lpNumber.length() - 1)) == StringUtils.PUBLIC_TRANSPORTATION_G_CHAR) {
            code = CodeEnumForEntering.PUBLIC_TRANSPORTATION.ordinal();
        } else if (lpNumber.contains(StringUtils.LAW_ENFORCEMENT_CHAR_L) || lpNumber.contains(StringUtils.LAW_ENFORCEMENT_CHAR_M)) {
            code = CodeEnumForEntering.LAW_ENFORCEMENT.ordinal();
        } else if (lpNumber.matches("[0-9]+")) {
            code = CodeEnumForEntering.NO_LETTERS.ordinal();
        } else {
            code = CodeEnumForEntering.VALID_LP.ordinal();
        }
        Log.WriteToLogFile(Log.getInstance().getlp(), String.format("return code is:%d", code));
        return code;
    }
}
