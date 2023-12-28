package ncu.im3069.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Meal;
import ncu.im3069.demo.app.MealHelper;
//import ncu.im3069.demo.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/meal.do")
@MultipartConfig

public class MealController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MealHelper meah =  MealHelper.getHelper();

    public MealController() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id_list = jsr.getParameter("meal_id");

        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回餐點之資料，否則代表要取回全部資料庫內餐點之資料 */
        if (!id_list.isEmpty()) {
          JSONObject query = meah.getByIdList(id_list);
          resp.put("status", "200");
          resp.put("message", "餐點資料取得成功");
          resp.put("response", query);
        }
        else {
          JSONObject query = meah.getAll();

          resp.put("status", "200");
          resp.put("message", "所有餐點資料取得成功");
          resp.put("response", query);
        }

        jsr.response(resp, response);
	}
	
	 private String getSubmittedFileName(Part part) {
	        String contentDisposition = part.getHeader("content-disposition");
	        String[] items = contentDisposition.split(";");
	        for (String item : items) {
	            if (item.trim().startsWith("filename")) {
	                return item.substring(item.indexOf("=") + 2, item.length() - 1);
	            }
	        }
	        return "";
	    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    // 設置回應的內容類型為JSON，並使用UTF-8編碼
	    response.setContentType("application/json;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    
	    /** 取出經解析到之Request參數 */
        String meal_name = request.getParameter("meal_name");
        String mealPriceString = request.getParameter("meal_price");
        int meal_price = Integer.parseInt(mealPriceString);
        String meal_description = request.getParameter("meal_description");
        String successMessage = "添加失敗！";
       
        //從 HTTP 請求中取得名稱為 "meal_image" 的部分（Part），用於處理上傳的檔案。
        Part filePart = request.getPart("meal_image");
        //返回客戶端上傳的檔案的原始文件名稱。這裡將該檔案名稱存儲在 meal_image 字串變數中
        String meal_image = filePart.getSubmittedFileName();
        
        // 處理檔案的相應路徑，這裡假設你希望將檔案存儲在指定目錄下
        String uploadDirectory = "C:\\Users\\yuan\\Desktop\\去你的SA\\v5\\112_SA_G7/statics/img/meal/";
        String fileName = getSubmittedFileName(filePart);
        String savePath = uploadDirectory + fileName;

        // 將檔案保存到伺服器指定的路徑
        filePart.write(savePath);

        
        /** 建立一個新的餐點物件 */
        Meal m = new Meal(meal_name, meal_price, meal_description, meal_image);
        
            /** 透過MealHelper物件的createMeal()方法新建一個餐點至資料庫 */
            JSONObject data = meah.createMeal(m);
            
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
        int id = jso.getInt("meal_id");
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名餐點，回傳之資料為JSONObject物件 */
        JSONObject query = meah.deleteByID(id);
        
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
	    
	    /** 取出經解析到之Request參數 */
	    
	    String mealidString = request.getParameter("mealId");
	    int meal_id = Integer.parseInt(mealidString);
        String meal_name = request.getParameter("mealName");
        String mealPriceString = request.getParameter("mealPrice");
        int meal_price = Integer.parseInt(mealPriceString);
        String meal_description = request.getParameter("mealDescription");
        String successMessage = "更新失敗！";
       
        //從 HTTP 請求中取得名稱為 "meal_image" 的部分（Part），用於處理上傳的檔案。
        Part filePart = request.getPart("mealImage");
        //返回客戶端上傳的檔案的原始文件名稱。這裡將該檔案名稱存儲在 meal_image 字串變數中
        String meal_image = filePart.getSubmittedFileName();
        
        // 處理檔案的相應路徑，這裡假設你希望將檔案存儲在指定目錄下
        String uploadDirectory = "C:\\Users\\yuan\\Desktop\\去你的SA\\v5\\112_SA_G7/statics/img/meal/";
        String fileName = getSubmittedFileName(filePart);
        String savePath = uploadDirectory + fileName;

        // 將檔案保存到伺服器指定的路徑
        filePart.write(savePath);

        /** 透過傳入之參數，新建一個以這些參數之餐點Meal物件 */
        Meal m = new Meal(meal_id, meal_name, meal_price, meal_description, meal_image);
        
        /** 透過MealHelper物件的updateMeal()方法至資料庫更新該餐點資料，回傳之資料為JSONObject物件 */
        //JSONObject data = m.updateMeal();
        JSONObject data = meah.updateMeal(m);
        
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

}
