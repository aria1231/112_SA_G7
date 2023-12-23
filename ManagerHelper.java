import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class ManagerHelper<br>
 * ManagerHelper類別（class）主要管理所有與Managber相關與資料庫之方法（method）
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class ManagerHelper {
    /**
     * 實例化（Instantiates）一個新的（new）ManagerHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private ManagerHelper() {
        
    }
    
    /** 靜態變數，儲存MemberHelper物件 */
    private static MeanagerHelper manh;
    
    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個ManagerHelper物件
     *
     * @return the helper 回傳ManagerHelper物件
     */
    public static ManagerHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if(manh == null) manh = new ManagerHelper();
        
        return manh;
    }
    
    /**
     * 透過管理員編號（ID）取得會員資料
     *
     * @param manager_id 管理員編號
     * @return the JSON object 回傳SQL執行結果與該管理員編號之管理員資料
     */
    public JSONObject getByID(String id) {
        /** 新建一個 Manager 物件之 m 變數，用於紀錄每一位查詢回之管理員資料 */
        Manager m = null;
        /** 用於儲存所有檢索回之管理員，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `final_pj`.`Manager` WHERE `manager_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該管理員編號之資料，因此其實可以不用使用 while 迴圈 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int manager_id = rs.getInt("managerid");
                String manager_first_name = rs.getString("manager_first_name");
                String manager_last_name = rs.getString("manager_last_name");
                String manager_email = rs.getString("manager_email");
                String manager_phone_number = rs.getString("manager_phone_number");
                
                /** 將每一筆會員資料產生一名新Manager物件 */
                m = new Manager(manager_id, manager_email, manager_first_name, manager_last_name, manager_phone_number);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(m.getData());
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有管理員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
}