package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Movie;
import ncu.im3069.demo.app.MovieHelper;
//import ncu.im3069.demo.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/movie.do")
public class MovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MovieHelper movh =  MovieHelper.getHelper();

    public MovieController() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id_list = jsr.getParameter("id_list");
		String type = jsr.getParameter("movie_type");

        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回電影之資料，否則代表要取回全部資料庫內電影之資料 */
        if (!type.isEmpty()){
          JSONObject query = movh.getByType(type);
          resp.put("status", "200");
          resp.put("message", "查詢類型之電影資料取得成功");
          resp.put("response", query);
		}
		else if(!id_list.isEmpty()) {
          JSONObject query = movh.getByIdList(id_list);
          resp.put("status", "200");
          resp.put("message", "電影資料取得成功");
          resp.put("response", query);
        }
        else {
          JSONObject query = movh.getAll();

          resp.put("status", "200");
          resp.put("message", "所有電影資料取得成功");
          resp.put("response", query);
        }

        jsr.response(resp, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String movie_name = jso.getString("movie_name");
        int movie_time = jso.getInt("movie_time");
        String movie_description = jso.getString("movie_description");
		String movie_image = jso.getString("movie_image");
		int movie_type = jso.getInt("movie_type");
        
        /** 建立一個新的電影物件 */
        Movie m = new Movie(movie_name, movie_type, movie_description, movie_image, movie_type);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(movie_name.isEmpty() || movie_time == 0 || movie_description.isEmpty() || movie_image.isEmpty() || movie_type == 0) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        else {
            /** 透過MovieHelper物件的createMovie()方法新建一個電影至資料庫 */
            JSONObject data = movh.createMovie(m);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 註冊會員資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
	}
	
	/**
     * 處理Http Method請求DELETE方法（刪除）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("movie_id");
        
        /** 透過MovieHelper物件的deleteByID()方法至資料庫刪除該電影，回傳之資料為JSONObject物件 */
        JSONObject query = movh.deleteByID(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "會員移除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
	
	/**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int movie_id = jso.getInt("movie_id");
        String movie_name = jso.getString("movie_name");
        int movie_time = jso.getInt("movie_time");
        String movie_description = jso.getString("movie_description");
		String movie_image = jso.getString("movie_image");
        int movie_type = jso.getInt("movie_type");

        /** 透過傳入之參數，新建一個以這些參數之電影Movie物件 */
        Movie m = new Movie(movie_id, movie_name, movie_time, movie_description, movie_image, movie_type);
        
        /** 透過MovieHelper物件的updateMovie()方法至資料庫更新該電影資料，回傳之資料為JSONObject物件 */
        //JSONObject data = m.updateMovie();
        JSONObject data = movh.updateMovie(m);
		
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

}
