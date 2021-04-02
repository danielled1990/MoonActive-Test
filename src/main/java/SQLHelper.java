import java.sql.*;

public class SQLHelper {
    private static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    public static Connection dbConnection = null;
    public static String s = "";

    public void init() {
    }

    static{
        try {
            Class.forName(SQL_DRIVER).newInstance();
            dbConnection = DriverManager.getConnection(StringUtils.SQL_DRIVER, USER, PASSWORD);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            Log.WriteToLogFile(StringUtils.CANNOT_ACCESS_DB);
        }
    }

    public void insertLPintoSQLTABLE(String lp,boolean canEnter) throws SomethingWrongExcepction{
        try {
            dbConnection.setAutoCommit(false);
            PreparedStatement pre;
            int index =0;

            pre = dbConnection.prepareStatement("INSERT INTO lp_ma_schema.lp_table (lp_index, lp_num,lp_edate,lp_ets,lp_can_enter) values(?,?,?,?,?)");
            pre.setInt(1, index);
            pre.setString(2,lp );
            pre.setDate(3,new Date(Log.getCurrentDate().getTime()));
            pre.setTimestamp(4,Log.getTimeStamp());
            pre.setBoolean(5,canEnter);
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
