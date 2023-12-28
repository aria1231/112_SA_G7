package ncu.im3069.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Movie;
import ncu.im3069.demo.app.MovieHelper;
//import ncu.im3069.demo.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/movie.do")
@MultipartConfig
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
        String id_list = jsr.getParameter("movie_id");
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
		response.setCharacterEncoding("UTF-8");
	    // 設置回應的內容類型為JSON，並使用UTF-8編碼
	    response.setContentType("application/json;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    
	    /** 取出經解析到之Request參數 */
        String movie_name = request.getParameter("movie_name");
        String movieTimeString = request.getParameter("movie_time");
        int movie_time = Integer.parseInt(movieTimeString);
        String movie_description = request.getParameter("movie_description");
        String movieTypeString = request.getParameter("movie_type");
        int movie_type = Integer.parseInt(movieTypeString);
        String successMessage = "添加失敗！";
        
      //從 HTTP 請求中取得名稱為 "movie_image" 的部分（Part），用於處理上傳的檔案。
        Part filePart = request.getPart("movie_image");
        //返回客戶端上傳的檔案的原始文件名稱。這裡將該檔案名稱存儲在 meal_image 字串變數中
        String movie_image = filePart.getSubmittedFileName();
        
        // 處理檔案的相應路徑，這裡假設你希望將檔案存儲在指定目錄下
        String uploadDirectory = "C:\\Users\\pooh5\\OneDrive\\桌面\\期末2\\112_SA_G7\\statics\\img\\movie/";
        String fileName = getSubmittedFileName(filePart);
        String savePath = uploadDirectory + fileName;

        // 將檔案保存到伺服器指定的路徑
        filePart.write(savePath);
        

        /** 建立一個新的電影物件 */
        Movie m = new Movie(movie_name, movie_time, movie_description, movie_image, movie_type);
        
            /** 透過MovieHelper物件的createMovie()方法新建一個電影至資料庫 */
            JSONObject data = movh.createMovie(m);
            
            Object affect_row = data.get("row");
            int row=0 ;
            int status=500;
            
            if(affect_row!= null) {
            	row = (int)affect_row;
            	if (row!=0) {
            		successMessage = "添加成功";
            		status=200;
            	}
            }
            
            // 獲取PrintWriter對象，用於將JSON數據寫入OutputStream
            PrintWriter out = response.getWriter();

            // 構建成功的JSON回應
            String jsonResponse = "{\"status\":  \"" + status + "\", \"message\": \"" + successMessage + "\"}";

            // 寫入JSON數據到OutputStream
            out.print(jsonResponse);

            // 關閉PrintWriter
            out.flush();
            out.close();
        }
	
	// 此方法用於從 Part 對象中獲取上傳的檔案名稱
	private String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
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
        
        response.setCharacterEncoding("UTF-8");
	    // 設置回應的內容類型為JSON，並使用UTF-8編碼
	    response.setContentType("application/json;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
        
        String movieidString = request.getParameter("movieId");
        int movie_id = Integer.parseInt(movieidString);
        String movie_name = request.getParameter("movieName");
        String movieTimeString = request.getParameter("duration");
        int movie_time = Integer.parseInt(movieTimeString);
        String movie_description = request.getParameter("description");
        String movieTypeString = request.getParameter("category");
        int movie_type = Integer.parseInt(movieTypeString);
        String successMessage = "更新失敗！";
        
      //從 HTTP 請求中取得名稱為 "movie_image" 的部分（Part），用於處理上傳的檔案。
        Part filePart = request.getPart("image");
        //返回客戶端上傳的檔案的原始文件名稱。這裡將該檔案名稱存儲在 meal_image 字串變數中
        String movie_image = filePart.getSubmittedFileName();
        
        // 處理檔案的相應路徑，這裡假設你希望將檔案存儲在指定目錄下
        String uploadDirectory = "C:\\Users\\pooh5\\OneDrive\\桌面\\期末2\\112_SA_G7\\statics\\img\\movie/";
        String fileName = getSubmittedFileName(filePart);
        String savePath = uploadDirectory + fileName;

        // 將檔案保存到伺服器指定的路徑
        filePart.write(savePath);

        /** 透過傳入之參數，新建一個以這些參數之電影Movie物件 */
        Movie m = new Movie(movie_id, movie_name, movie_time, movie_description, movie_image, movie_type);
        
        /** 透過MovieHelper物件的updateMovie()方法至資料庫更新該電影資料，回傳之資料為JSONObject物件 */
        JSONObject data = movh.updateMovie(m);
        
        Object affect_row = data.get("row");
        int row=0 ;
        int status=500;
        
        if(affect_row!= null) {
        	row = (int)affect_row;
        	if (row!=0) {
        		successMessage = "更新成功";
        		status=200;
        	}
        }
        
        // 獲取PrintWriter對象，用於將JSON數據寫入OutputStream
        PrintWriter out = response.getWriter();

        // 構建成功的JSON回應
        String jsonResponse = "{\"status\":  \"" + status + "\", \"message\": \"" + successMessage + "\"}";

        // 寫入JSON數據到OutputStream
        out.print(jsonResponse);

        // 關閉PrintWriter
        out.flush();
        out.close();
    }

}
