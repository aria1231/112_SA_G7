package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;

//import ncu.im3069.demo.util.DBMgr;
//import ncu.im3069.demo.app.Product;

public class MealHelper {
    private MealHelper() {
        
    }
    
    private static MealHelper meah;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static MealHelper getHelper() {
        /** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
        if(meah == null) meah = new MealHelper();
        
        return meah;
    }
    
    public JSONObject getAll() {
        /** 新建一個 Product 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
    	Meal m = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
			
            String sql = "SELECT * FROM `final_pj`.`meal`";
			
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int meal_id = rs.getInt("meal_id");
                String meal_name = rs.getString("meal_name");
                int meal_price = rs.getInt("meal_price");
                String meal_image = rs.getString("meal_image");
                String meal_description = rs.getString("meal_description");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                m = new Meal(meal_id, meal_name, meal_price, meal_image, meal_description);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(m.getMealData());
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
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getByIdList(String data) {
      /** 新建一個 Product 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
      Meal m = null;
      /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
          String[] in_para = DBMgr.stringToArray(data, ",");
          /** SQL指令 */
		  
          String sql = "SELECT * FROM `final_pj`.`meal` WHERE `meal`.`meal_id`";
		  
          for (int i=0 ; i < in_para.length ; i++) {
              sql += (i == 0) ? "in (?" : ", ?";
              sql += (i == in_para.length-1) ? ")" : "";
          }
          
          /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
          pres = conn.prepareStatement(sql);
          for (int i=0 ; i < in_para.length ; i++) {
            pres.setString(i+1, in_para[i]);
          }
          /** 執行查詢之SQL指令並記錄其回傳之資料 */
          rs = pres.executeQuery();

          /** 紀錄真實執行的SQL指令，並印出 **/
          exexcute_sql = pres.toString();
          System.out.println(exexcute_sql);
          
          /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
          while(rs.next()) {
              /** 每執行一次迴圈表示有一筆資料 */
              row += 1;
              
              /** 將 ResultSet 之資料取出 */
              int meal_id = rs.getInt("meal_id");
              String meal_name = rs.getString("meal_name");
              int meal_price = rs.getInt("meal_price");
              String meal_image = rs.getString("meal_image");
              String meal_description = rs.getString("meal_description");
              
              /** 將每一筆商品資料產生一名新Product物件 */
              m = new Meal(meal_id, meal_name, meal_price, meal_image, meal_description);
              /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
              jsa.put(m.getMealData());
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
      
      /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);
      response.put("row", row);
      response.put("time", duration);
      response.put("data", jsa);

      return response;
  }
    
    public Meal getById(String id) {
        /** 新建一個 Product 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
        Meal m = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
			
            String sql = "SELECT * FROM `final_pj`.`meal` WHERE `meal`.`meal_id` = ? LIMIT 1";
			
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
				int meal_id = rs.getInt("meal_id");
				String meal_name = rs.getString("meal_name");
				int meal_price = rs.getInt("meal_price");
				String meal_image = rs.getString("meal_image");
				String meal_description = rs.getString("meal_description");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                m = new Meal(meal_id, meal_name, meal_price, meal_image, meal_description);
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

        return m;
    }
	
	/*public JSONObject getMealUPDATETIME(Meal m){
		/** 用於儲存該名會員所檢索之更新時間分鐘數與會員組別之資料 */
        /*JSONObject jso = new JSONObject();
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        /*ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
        //    conn = DBMgr.getConnection();
            /** SQL指令 */
        //    String sql = "SELECT * FROM `missa`.`meal` WHERE `meal_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
        //    pres = conn.prepareStatement(sql);
        //    pres.setInt(1, m.getMealID());
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
        //    rs = pres.executeQuery();
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該電子郵件之資料，因此其實可以不用使用 while迴圈 */
        //    while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
        //        int update_times = rs.getInt("meal_update_times");
                /** 將其封裝至JSONObject資料 */
        //        jso.put("meal_update_times", update_times);
        /*    }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
        /*    System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
        //    e.printStackTrace();
        /*} finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
        /*    DBMgr.close(rs, pres, conn);
        }

        return jso;
	}*/
	
	//public void updateRoomUPDATETIME(Room r){
		/** 更新時間之分鐘數 */
        //Timestamp new_times = r.getRoomUPDATETIME();
        
        /** 記錄實際執行之SQL指令 */
        //String exexcute_sql = "";
        
        //try {
            /** 取得資料庫之連線 */
            //conn = DBMgr.getConnection();
            /** SQL指令 */
			
			/*改
            String sql = "Update `missa`.`members` SET `room_update_times` = ? WHERE `room_id` = ?";
            */
			
			/** 取得會員編號 */
            //int id = r.getRoomID();
            
            /** 將參數回填至SQL指令當中 */
            //pres = conn.prepareStatement(sql);
            //pres.setInt(1, new_times);
            //pres.setInt(2, id);
            /** 執行更新之SQL指令 */
            //pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            //exexcute_sql = pres.toString();
            //System.out.println(exexcute_sql);

        /*} catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            //System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        /*} catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            //e.printStackTrace();
        //*} finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            //DBMgr.close(pres, conn);
        //}
	//}
	
	public JSONObject updateMeal(Meal m){
		/** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
			
            String sql = "Update `final_pj`.`meal` SET `meal_name` = ? ,`meal_price` = ? , `meal_description` = ? , `meal_image` = ? `meal_update_time` = ? WHERE `meal_id` = ?";
			
			/** 取得所需之參數 */
			int meal_id = m.getMealID();
            String meal_name = m.getMealNAME();
            int meal_price = m.getMealPRICE();
            String meal_description = m.getMealDESCRIPTION();
			String meal_image = m.getMealIMAGE();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, meal_name);
            pres.setInt(2, meal_price);
            pres.setString(3, meal_description);
            pres.setString(4, meal_image);
            pres.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pres.setInt(6, meal_id);
            /** 執行更新之SQL指令並記錄影響之行數 */
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
            DBMgr.close(pres, conn);
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
        response.put("data", jsa);

        return response;
	}
	
	public JSONObject createMeal(Meal m){
		/** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
			
            String sql = "INSERT INTO `final_pj`.`meal`(`meal_name`, `meal_price`, `meal_description`, `meal_image`, `meal_update_time`)"
                    + " VALUES(?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            String meal_name = m.getMealNAME();
            int meal_price = m.getMealPRICE();
			String meal_description = m.getMealDESCRIPTION();
			String meal_image = m.getMealIMAGE();
			
			
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, meal_name);
            pres.setInt(2, meal_price);
            pres.setString(3, meal_description);
            pres.setString(4, meal_image);
            pres.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            
            /** 執行新增之SQL指令並記錄影響之行數 */
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
            DBMgr.close(pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
	}
	
	public JSONObject deleteByID(int id){
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
			
            String sql = "DELETE FROM `final_pj`.`meal` WHERE `meal_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
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
