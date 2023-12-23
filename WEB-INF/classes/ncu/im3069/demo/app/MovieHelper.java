package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;

public class MovieHelper {
    private MovieHelper() {
        
    }
    
    private static MovieHelper movh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static MovieHelper getHelper() {
        /** Singleton檢查是否已經有MovieHelper物件，若無則new一個，若有則直接回傳 */
        if(movh == null) movh = new MovieHelper();
        
        return movh;
    }
    
    public JSONObject getAll() {
        /** 新建一個 Movie 物件之 m 變數，用於紀錄每一位查詢回之電影資料 */
    	Movie m = null;
        /** 用於儲存所有檢索回之電影，以JSONArray方式儲存 */
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
			
            String sql = "SELECT * FROM `final_pj`.`movie`";
			
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
              int movie_id = rs.getInt("movie_id");
              String movie_name = rs.getString("movie_name");
              int movie_time = rs.getInt("movie_time");
              String movie_image = rs.getString("movie_image");
              String movie_description = rs.getString("movie_description");
              int movie_type = rs.getInt("movie_type");
                
                /** 將每一筆電影資料產生一名新Movie物件 */
				m = new Movie(movie_id, movie_name, movie_time, movie_image, movie_description, movie_type);
                /** 取出該項電影之資料並封裝至 JSONsonArray 內 */
                jsa.put(m.getMovieData());
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
        
        /** 將SQL指令、花費時間、影響行數與所有電影資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getByIdList(String data) {
      /** 新建一個 Movie 物件之 m 變數，用於紀錄每一位查詢回之電影資料 */
      Movie m = null;
      /** 用於儲存所有檢索回之電影，以JSONArray方式儲存 */
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
		  
          String sql = "SELECT * FROM `final_pj`.`movie` WHERE `movie`.`movie_id`";
		  
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
              int movie_id = rs.getInt("movie_id");
              String movie_name = rs.getString("movie_name");
              int movie_time = rs.getInt("movie_time");
              String movie_image = rs.getString("movie_image");
              String movie_description = rs.getString("movie_description");
              int movie_type = rs.getInt("movie_type");
              
              /** 將每一筆電影資料產生一名新Movie物件 */
              m = new Movie(movie_id, movie_name, movie_time, movie_image, movie_description, movie_type);
              /** 取出該項電影之資料並封裝至 JSONsonArray 內 */
              jsa.put(m.getMovieData());
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
      
      /** 將SQL指令、花費時間、影響行數與所有電影資料之JSONArray，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);
      response.put("row", row);
      response.put("time", duration);
      response.put("data", jsa);

      return response;
  }
    
    public Movie getById(String id) {
        /** 新建一個 Movie 物件之 m 變數，用於紀錄每一位查詢回之電影資料 */
        Movie m = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
			
            String sql = "SELECT * FROM final_pj`.`movie` WHERE `movie`.`movie_id` = ? LIMIT 1";
			
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
				int movie_id = rs.getInt("movie_id");
				String movie_name = rs.getString("movie_name");
				int movie_time = rs.getInt("movie_time");
				String movie_image = rs.getString("movie_image");
				String movie_description = rs.getString("movie_description");
				int movie_type = rs.getInt("movie_type");
                
                /** 將每一筆電影資料產生一名新Product物件 */
                m = new Movie(movie_id, movie_name, movie_time, movie_image, movie_description, movie_type);
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
	
	public JSONObject getByType(String type){
		/** 新建一個 Movie 物件之 m 變數，用於紀錄每一位查詢回之電影資料 */
		  Movie m = null;
		  /** 用於儲存所有檢索回之電影，以JSONArray方式儲存 */
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
			  
			  String sql = "SELECT * FROM `final_pj`.`movie` WHERE `movie`.`movie_type` = ? LIMIT 1";
			  
			  pres = conn.prepareStatement(sql);
			  pres.setInt(1, Integer.parseInt(type));
			  rs = pres.executeQuery();

			  /** 紀錄真實執行的SQL指令，並印出 **/
			  exexcute_sql = pres.toString();
			  System.out.println(exexcute_sql);
			  
			  /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
			  while(rs.next()) {
				  /** 每執行一次迴圈表示有一筆資料 */
				  row += 1;
				  
				  /** 將 ResultSet 之資料取出 */
				  int movie_id = rs.getInt("movie_id");
				  String movie_name = rs.getString("movie_name");
				  int movie_time = rs.getInt("movie_time");
				  String movie_image = rs.getString("movie_image");
				  String movie_description = rs.getString("movie_description");
				  int movie_type = rs.getInt("movie_type");
				  
				  /** 將每一筆電影資料產生一名新Movie物件 */
				  m = new Movie(movie_id, movie_name, movie_time, movie_image, movie_description, movie_type);
				  /** 取出該項電影之資料並封裝至 JSONsonArray 內 */
				  jsa.put(m.getMovieData());
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
		  
		  /** 將SQL指令、花費時間、影響行數與所有電影資料之JSONArray，封裝成JSONObject回傳 */
		  JSONObject response = new JSONObject();
		  response.put("sql", exexcute_sql);
		  response.put("row", row);
		  response.put("time", duration);
		  response.put("data", jsa);

		  return response;
	}
	
	public JSONObject updateMovie(Movie m){
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
			
            String sql = "Update `final_pj`.`movie` SET `movie_name` = ? ,`movie_time` = ? , `movie_description` = ? , `movie_image` = ? `movie_update_time` = ?  `movie_type` = ? WHERE `moive_id` = ?";
			
			/** 取得所需之參數 */
			int movie_id = m.getMovieID();
            String movie_name = m.getMovieNAME();
            int movie_time = m.getMovieTIME();
            String movie_description = m.getMovieDESCRIPTION();
			String movie_image = m.getMovieIMAGE();
			int movie_type = m.getMovieTYPE();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, movie_name);
            pres.setInt(2, movie_time);
            pres.setString(3, movie_description);
            pres.setString(4, movie_image);
            pres.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			pres.setInt(6, movie_type);
			pres.setInt(7, movie_id);
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
	
	public JSONObject createMovie(Movie m){
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
			
            String sql = "INSERT INTO `final_pj`.`movie`(`movie_name`, `movie_time`, `movie_description`, `movie_image`, `movie_type` `movie_update_time`)"
                    + " VALUES(?, ?, ?, ?, ?,?)";
            
            /** 取得所需之參數 */
            String movie_name = m.getMovieNAME();
            int movie_time = m.getMovieTIME();
			String movie_description = m.getMovieDESCRIPTION();
			String movie_image = m.getMovieIMAGE();
            int movie_type = m.getMovieTYPE();
			
			
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, movie_name);
            pres.setInt(2, movie_time);
            pres.setString(3, movie_description);
            pres.setString(4, movie_image);
            pres.setInt(5, movie_type);
            pres.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            
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
			
            String sql = "DELETE FROM `final_pj`.`movie` WHERE `movie_id` = ? LIMIT 1";
            
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
