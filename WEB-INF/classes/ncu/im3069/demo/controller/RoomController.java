package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Room;
import ncu.im3069.demo.app.RoomHelper;
//import ncu.im3069.demo.app.ProductHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/room.do")
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
        String id_list = jsr.getParameter("id_list");

        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回查詢之資料，否則代表要取回全部資料庫內包廂之資料 */
        if (!id_list.isEmpty()) {
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
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String room_name = jso.getString("room_name");
        int room_price = jso.getInt("room_price");
        String room_description = jso.getString("room_description");
		String room_image = jso.getString("room_image");
        
        /** 建立一個新的包廂物件 */
        Room r = new Room(room_name, room_price, room_description, room_image);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(room_name.isEmpty() || room_price == 0 || room_description.isEmpty() || room_image.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        else {
            /** 透過RoomHelper物件的createRoom()方法新建一個包廂至資料庫 */
            JSONObject data = rooh.createRoom(r);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增包廂資料...");
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

        /** 透過傳入之參數，新建一個以這些參數之包廂Room物件 */
        Room r = new Room(room_id, room_name, room_price, room_description, room_image);
        
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
