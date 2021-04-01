public class CheckIfLicensePlateIsValid {


    public int CheckLicenePlate(String lp) {
        Log.WriteToLogFile(Log.getInstance().getlp(), "checking if license plate is valid");
        String lpNumber = lp.replace("-", "").trim();
        int code;
        if (lpNumber.charAt((lpNumber.length() - 1)) == StringUtils.PUBLIC_TRANSPORTATION_SIX_CHAR
                || lpNumber.charAt((lpNumber.length() - 1)) == StringUtils.PUBLIC_TRANSPORTATION_G_CHAR) {
            code = 0;
        } else if (lpNumber.contains(StringUtils.LAW_ENFORCEMENT_CHAR_L) || lpNumber.contains(StringUtils.LAW_ENFORCEMENT_CHAR_M)) {
            code = 1;
        } else if (lpNumber.matches("[0-9]+")) {
            code = 2;
        } else {//valid
            code = 3;
        }
        Log.WriteToLogFile(Log.getInstance().getlp(), String.format("return code is:%d", code));
        return code;
    }
}
