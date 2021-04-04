import java.sql.*;

/**
 * this class is the SQL helper that write each decision to the DB.
 */
public class SQLHelper {
    private static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    public static Connection dbConnection = null;
    public static String s = "";

    public void init() {
    }
    ///init the DB
    static{
        try {
            Class.forName(SQL_DRIVER).newInstance();
            dbConnection = DriverManager.getConnection(StringUtils.SQL_DRIVER, USER, PASSWORD);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            Log.WriteToLogFile(StringUtils.CANNOT_ACCESS_DB);
        }
    }

    /**
     * @param lp
     * @param canEnter
     * @throws SomethingWrongExcepction
     * inserting the data to the DB
     */
    public void insertLPintoSQLTABLE(String lp,boolean canEnter) throws SomethingWrongExcepction{
        try {
            dbConnection.setAutoCommit(false);
            PreparedStatement pre;
            int index =0;

            pre = dbConnection.prepareStatement("INSERT INTO lp_ma_schema.lp_table (lp_index, lp_num,lp_edate,lp_ets,lp_can_enter) values(?,?,?,?,?)");
            pre.setInt(1, index);//Auto increment index
            pre.setString(2,lp );//the license plate number
            pre.setDate(3,new Date(Log.getCurrentDate().getTime()));//current date (to make it easier to search by date)
            pre.setTimestamp(4,Log.getTimeStamp());//time stamp
            pre.setBoolean(5,canEnter);// a decision to enter or not. 0 - can't enter. 1 - can enter
            pre.executeUpdate();
            dbConnection.commit();
            Log.WriteToLogFile(Log.getlp(),String.format("%s:%s",StringUtils.NEW_ENTRY_TO_DB,Log.getlp()));

        } catch (SQLException ex) {
            if (dbConnection != null) {
                try {
                    dbConnection.rollback();
                } catch (SQLException throwables) {

                    Log.WriteToLogFile(Log.getlp(),String.format("%s:%s",StringUtils.ERROR_WHEN_ENTERING_SQL,Log.getlp()));
                    throw new SomethingWrongExcepction(StringUtils.SOMETHING_WENT_WRONG);
                }
            }
        }
    }
}
