package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

//import ncu.im3069.demo.app.Manager;
import ncu.im3069.demo.app.ManagerHelper;
import ncu.im3069.demo.app.Manager;
import ncu.im3069.tools.JsonReader;

//TODO: Auto-generated Javadoc
/**
* <p>
* The Class MemberController<br>
* MemberController類別（class）主要用於處理Member相關之Http請求（Request），繼承HttpServlet
* </p>
* 
* @author IPLab
* @version 1.0.0
* @since 1.0.0
*/

@WebServlet("/api/manager.do")
public class ManagerController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** manh，ManagerHelper之物件與Manager相關之資料庫方法（Sigleton） */
    private ManagerHelper manh = ManagerHelper.getHelper();
    
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String manager_email = jso.getString("email");	//登入也會用到
        String manager_password = jso.getString("password");
        
        if ((!manager_email.isEmpty()) && (!manager_email.isEmpty())) {
        	/** 透過MemberHelper物件的verifyLogin()檢查該會員電子郵件信箱是否有效 */
        	if (manh.verifyLogin(new Manager(manager_email, manager_password))) {
        		// 登入驗證成功
        		// 登入成功的處理
        		JSONObject resp = new JSONObject();
        		resp.put("status", "200");
        		resp.put("success", true);
        		resp.put("message", "登入成功");
        		jsr.response(resp, response);
        		// 如果需要回傳會員資訊，也可以在這裡加上
        		//resp.put("member_id", member_id); // 請替換為實際的會員資訊
        	}
        	else if (!manh.verifyLogin(new Manager(manager_email, manager_password))) {
        		// 登入驗證失敗
        		// 登入失敗的處理
        		JSONObject resp = new JSONObject();
        		resp.put("status", "401");
        		resp.put("success", false);
        		resp.put("message", "Email或密碼錯誤");
        		jsr.response(resp, response);
        	}
        }
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        else if(manager_email.isEmpty() || manager_password.isEmpty()) { //登入判斷有無空值
            /** 以字串組出JSON格式之資料 */
            //String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
        	JSONObject resp = new JSONObject();
	        resp.put("status", "400");
		    resp.put("success", false);
		    resp.put("message", "欄位不能有空值");
        	/** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        System.out.println(manager_email);
    }
    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String manager_id = jsr.getParameter("manager_id");
        
        /** 判斷該字串是否存在，若存在代表要取回個別管理員之資料，否則代表要取回全部資料庫內管理員之資料 */
        if (manager_id.isEmpty()) {        	
        	// 判斷email和password是否存在
//        	if (!manager_email.isEmpty() && !manager_password.isEmpty()) {
//        		// 進行登入驗證，例如使用MemberHelper物件的方法進行登入驗證
//        		 boolean loginSuccess = manh.verifyLogin(manager_email, manager_password);
//        		
//        		// 根據登入結果回傳不同的訊息
//        	    JSONObject resp = new JSONObject();
//        	    if (loginSuccess) {
//       		        // 登入成功的處理
//       		        resp.put("status", "200");
//       		        resp.put("success", true);
//       		        resp.put("message", "登入成功");
//       		        // 如果需要回傳會員資訊，也可以在這裡加上
//       		        //resp.put("member_id", member_id); // 請替換為實際的會員資訊
//       		    } else {
//        	        // 登入失敗的處理
//        	        resp.put("status", "401");
//       		        resp.put("success", false);
//       		        resp.put("message", "Email或密碼錯誤");
//       		    }
//
//       		    // 透過JsonReader物件回傳到前端
//       		    jsr.response(resp, response);
//        	}
//        	else {
//           	 // email或password為空的處理
//               JSONObject resp = new JSONObject();
//               resp.put("status", "400");
//               resp.put("success", false);
//               resp.put("message", "請提供有效的Email和密碼");
//               jsr.response(resp, response);
//           }
        }
        else{
            /** 透過ManagerHelper物件的getByID()方法自資料庫取回該名管理員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = manh.getByID(manager_id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "管理員資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
    }
}