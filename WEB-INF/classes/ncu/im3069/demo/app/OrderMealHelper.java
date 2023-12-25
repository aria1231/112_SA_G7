package ncu.im3069.demo.app;

import java.sql.*;
import java.util.*;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;

public class OrderMealHelper {
    
    private OrderMealHelper() {
        
    }
    
    private static OrderMealHelper omh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個OrderMealHelper物件
     *
     * @return the helper 回傳OrderMealHelper物件
     */
    public static OrderMealHelper getHelper() {
        /** Singleton檢查是否已經有OrderMealHelper物件，若無則new一個，若有則直接回傳 */
        if(omh == null) omh = new OrderMealHelper();
        
        return omh;
    }
    
    public JSONArray createByList(long order_id, List<OrderMeal> orderMeal) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        for(int i=0 ; i < orderMeal.size() ; i++) {
            OrderMeal om = orderMeal.get(i);
            
            /** 取得所需之參數 */
            int meal_id = om.getOmlMeal().getMealID(); 
            int price = om.getOmlPrice();
            int serving = om.getOmlServing();
            int subtotal = om.getOmlSubTotal();
            
            try {
                /** 取得資料庫之連線 */
                conn = DBMgr.getConnection();
                /** SQL指令 */
                String sql = "INSERT INTO `final_pj`.`Order_Meal_Linking`(`order_id`, `meal_id`, `omlinking_price`, `omlinking_serving`, `omlinking_subtotal`)"
                        + " VALUES(?, ?, ?, ?, ?)";
                
                /** 將參數回填至SQL指令當中 */
                pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pres.setLong(1, order_id);
                pres.setInt(2, meal_id);
                pres.setInt(3, price);
                pres.setInt(4, serving);
                pres.setInt(5, subtotal);
                
                /** 執行新增之SQL指令並記錄影響之行數 */
                pres.executeUpdate();
                
                /** 紀錄真實執行的SQL指令，並印出 **/
                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);
                
                ResultSet rs = pres.getGeneratedKeys();

                if (rs.next()) {
                    long id = rs.getLong(1);
                    jsa.put(id);
                }
            } catch (SQLException e) {
                /** 印出JDBC SQL指令錯誤 **/
                System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                /** 若錯誤則印出錯誤訊息 */
                e.printStackTrace();
            } finally {
                /** 關閉連線並釋放所有資料庫相關之資源 **/
                DBMgr.close(pres, conn);
            }
        }
        
        return jsa;
    }
    
    public ArrayList<OrderMeal> getOrderMealByOrderId(int order_id) {
        ArrayList<OrderMeal> result = new ArrayList<OrderMeal>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        ResultSet rs = null;
        OrderMeal om;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `final_pj`.`Order_Meal_Linking` WHERE `Order_Meal_Linking`.`order_id` = ?";				/**table要改*/
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, order_id);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            rs = pres.executeQuery();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                
                /** 將 ResultSet 之資料取出 */
                int oml_id = rs.getInt("oml_id");
                int meal_id = rs.getInt("meal_id");
                int omlinking_price = rs.getInt("omlinking_price");
                int omlinking_serving = rs.getInt("omlinking_serving");
                int omlinking_subtotal = rs.getInt("omlinking_subtotal");		/************************************************/
                
                /** 將每一筆訂單餐點資料產生一名新OrderMeal物件 */
                om = new OrderMeal(oml_id, order_id, meal_id, omlinking_price, omlinking_serving, omlinking_subtotal);
                /** 取出該筆訂單餐點之資料並封裝至 JSONsonArray 內 */
                result.add(om);
            }
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
        
        return result;
    }
	
	public JSONObject deleteOrderMealByOrderId(int order_id){
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
            String sql = "DELETE FROM `final_pj`.`Order_Meal_Linking` WHERE `order_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, order_id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
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
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
	}
	
}
