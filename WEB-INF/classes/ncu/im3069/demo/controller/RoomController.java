package ncu.im3069.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.invoke.VarHandle;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import ncu.im3069.demo.app.MealHelper;
import ncu.im3069.demo.app.Room;
import ncu.im3069.demo.app.RoomHelper;
//import ncu.im3069.demo.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/room.do")
@MultipartConfig
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RoomHelper rooh =  RoomHelper.getHelper();

    public RoomController() {
        super();
        // TODO Auto-generated constructor stub
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id_list = jsr.getParameter("room_id");

        //System.out.print("------request------");
        //System.out.print(request);
        
        String selectedPeople = request.getParameter("people");
        String selectedDate = request.getParameter("date");
        String Time = request.getParameter("time");
        String selectedTime = "0";
        
        System.out.print("\n------ppl, date, time------");
        //System.out.printf("\nppl:", selectedPeople.isEmpty());
        //System.out.printf("\ndate:",selectedDate.isEmpty());
        //System.out.printf("\ntime:",selectedTime.isEmpty());

        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回查詢之資料，否則代表要取回全部資料庫內包廂之資料 */
        if (selectedPeople!= null && selectedDate!= null && selectedTime!= null ) {
        	if(Time.equals("1")) {
            	selectedTime = "900";
            }else if(Time.equals("2")) {
            	selectedTime = "1200";
            }else if(Time.equals("3")) {
            	selectedTime = "1500";
            }
        	JSONObject query = rooh.getAvalibileRoom(selectedPeople,selectedDate,selectedTime);
            resp.put("status", "200");
            resp.put("message", "資料取得成功");
            resp.put("response", query);
          }else if (!id_list.isEmpty()) {
          JSONObject query = rooh.getByIdList(id_list);
          resp.put("status", "200");
          resp.put("message", "資料取得成功");
          resp.put("response", query);
        }
        else {
          JSONObject query = rooh.getAll();

          resp.put("status", "200");
          resp.put("message", "所有包廂資料取得成功");
          resp.put("response", query);
        }

        jsr.response(resp, response);
        System.out.print(resp);
        
        System.out.println("get");
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
       		
        //Enumeration<String> a = request.getParameterNames();
        //System.out.println(a);
        //System.out.println(Collections.list(a).size());
        
        /** 取出經解析到之Request參數 */
        String room_name = request.getParameter("room_name");
        String roomPriceString = request.getParameter("room_price");
        int room_price = Integer.parseInt(roomPriceString);
        String roomLimitString = request.getParameter("room_limited");
        int room_limited = Integer.parseInt(roomLimitString);
        String room_description = request.getParameter("room_description");
        System.out.println(room_name);
        String successMessage = "添加失敗！";
        
        //從 HTTP 請求中取得名稱為 "room_image" 的部分（Part），用於處理上傳的檔案。
        Part filePart = request.getPart("room_image");
        //返回客戶端上傳的檔案的原始文件名稱。這裡將該檔案名稱存儲在 room_image 字串變數中
        String room_image = filePart.getSubmittedFileName();
        
        // 處理檔案的相應路徑，這裡假設你希望將檔案存儲在指定目錄下
        String uploadDirectory = "C:\\Users\\yuan\\Desktop\\去你的SA\\v4\\112_SA_G7/statics/img/room/";
        String fileName = getSubmittedFileName(filePart);
        String savePath = uploadDirectory + fileName;

        // 將檔案保存到伺服器指定的路徑
        filePart.write(savePath);
        
        /** 建立一個新的包廂物件 */
        Room r = new Room(room_name, room_price, room_description, room_image, room_limited);
                
            /** 透過MealHelper物件的createRoom()方法新建一個餐點至資料庫 */
            JSONObject data = rooh.createRoom(r);
            
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
        int id = jso.getInt("room_id");
        
        /** 透過RoomHelper物件的deleteByID()方法至資料庫刪除該包廂，回傳之資料為JSONObject物件 */
        JSONObject query = rooh.deleteByID(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "包廂移除成功！");
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
        int room_id = jso.getInt("room_id");
        String room_name = jso.getString("room_name");
        int room_price = jso.getInt("room_price");
        String room_description = jso.getString("room_description");
		String room_image = jso.getString("room_image");
        int room_limited = jso.getInt("room_limited");

        /** 透過傳入之參數，新建一個以這些參數之包廂Room物件 */
        Room r = new Room(room_id, room_name, room_price, room_description, room_image, room_limited);
        
        /** 透過RoomHelper物件的updateRoom()方法至資料庫更新該包廂資料，回傳之資料為JSONObject物件 */
        JSONObject data = rooh.updateRoom(r);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新包廂資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

}
