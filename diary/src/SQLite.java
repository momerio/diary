package diary.src;

import java.sql.*;

public class SQLite {

    private Connection conn = null;
    private Statement stm = null;

    private String dbPath;
    private String table_name;

    SQLite(String dbPath) {
        this.dbPath = dbPath;

        try {
            // DBをOPENする
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.dbPath);
            // conn.setAutoCommit(false); // 手動アップデート

            // 操作用インスタンスを取得する
            this.stm = this.conn.createStatement();
            this.stm.close();

            Debugger.out("DBを開きました dbPath:" + this.dbPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create_table() {
        try {
            // テーブルを生成する
            final String create_table_sql = "CREATE TABLE IF NOT EXISTS " + this.table_name
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT, main_text TEXT, flag INTEGER)";
            this.stm.execute(create_table_sql);
            this.stm.close();

            Debugger.out(table_name + " TABLE を生成しました");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String date, String title, String main_text, int flag) {
        try {
            this.conn.setAutoCommit(false);
            final String sql = "INSERT INTO " + this.table_name + " (date, title, main_text, flag) values (?, ?, ?, ?)";
            final PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
            prepareStatement.setString(1, date);
            prepareStatement.setString(2, title);
            prepareStatement.setString(3, main_text);
            prepareStatement.setInt(4, flag);
            prepareStatement.executeUpdate();
            this.conn.commit();
            this.conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * select文 はじめ
     */
    public DiaryData select(int id) {
        // idでselect
        DiaryData diaryData = new DiaryData();
        try {
            final String select_sql = "select * from " + this.table_name + " where id = ? ";
            final PreparedStatement prepareStatement = this.conn.prepareStatement(select_sql);
            prepareStatement.setInt(1, id); // selectを実行
            ResultSet resultSet = prepareStatement.executeQuery();
            diaryData.set_all_data(resultSet.getInt("id"), resultSet.getString("date"), resultSet.getString("title"),
                    resultSet.getString("main_text"), resultSet.getInt("flag"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diaryData;
    }

    public DiaryData select(String date) {
        // 日付でselect
        DiaryData diaryData = new DiaryData();
        try {
            final String select_sql = "select * from " + this.table_name + " where date = ? ";
            final PreparedStatement prepareStatement = this.conn.prepareStatement(select_sql);
            prepareStatement.setString(1, date); // selectを実行
            ResultSet resultSet = prepareStatement.executeQuery();
            diaryData.set_all_data(resultSet.getInt("id"), resultSet.getString("date"), resultSet.getString("title"),
                    resultSet.getString("main_text"), resultSet.getInt("flag"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diaryData;
    }
    /*
     * select文 終わり
     */

    public void drop_table() {
        // テーブル削除
        try {
            final String drop_table = "DROP TABLE IF EXISTS " + this.table_name;
            this.stm.executeUpdate(drop_table);
            this.stm.close();
            Debugger.out(table_name + " TABLE を削除しました");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DB CLOSE処理
     */
    public void dbClose() {

        try {
            if (stm != null && !stm.isClosed()) {
                // 操作用インスタンスをクローズする
                stm.close();
            }

            if (conn != null && !conn.isClosed()) {
                // DBをクローズする
                conn.close();
            }
            Debugger.out("DBを閉じました");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // getter and setter
    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

}
