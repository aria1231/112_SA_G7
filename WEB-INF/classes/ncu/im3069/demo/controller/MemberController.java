package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
//記得改import路徑
import ncu.im3069.demo.app.Member;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;

// TODO: Auto-generated Javadoc
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

public class MemberController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper memh = MemberHelper.getHelper();
    
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
        String member_email = jso.getString("member_email");
        String member_password = jso.getString("member_password");
        String member_first_name = jso.getString("member_first_name");
        String member_last_name = jso.getString("member_last_name");
        String member_phone_number = jso.getString("member_phone_number");
        
        /** 建立一個新的會員物件 */
        Member m = new Member(member_email, member_password, member_first_name, member_last_name, member_phone_number);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(member_email.isEmpty() || member_password.isEmpty() || member_first_name.isEmpty() || member_last_name.isEmpty() || member_phone_number.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        else if (!memh.checkDuplicate(m)) {
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = memh.create(m);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 註冊會員資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
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
        String member_id = jsr.getParameter("member_id");
        String member_email = jsr.getParameter("member_mail");
        String member_password = jsr.getParameter("member_password");
        
        /** 判斷該字串是否存在，若存在代表要取回個別會員之資料，否則代表要取回全部資料庫內會員之資料 */
        if (member_id.isEmpty()) {        	
        	// 判斷email和password是否存在
        	if (!member_email.isEmpty() && !member_password.isEmpty()) {
        		// 進行登入驗證，例如使用MemberHelper物件的方法進行登入驗證
        		 boolean loginSuccess = memh.verifyLogin(member_email, member_password);
        		
        		// 根據登入結果回傳不同的訊息
        	    JSONObject resp = new JSONObject();
        	    if (loginSuccess) {
       		        // 登入成功的處理
       		        resp.put("status", "200");
       		        resp.put("success", true);
       		        resp.put("message", "登入成功");
       		        // 如果需要回傳會員資訊，也可以在這裡加上
       		        //resp.put("member_id", member_id); // 請替換為實際的會員資訊
       		    } else {
        	        // 登入失敗的處理
        	        resp.put("status", "401");
       		        resp.put("success", false);
       		        resp.put("message", "Email或密碼錯誤");
       		    }

       		    // 透過JsonReader物件回傳到前端
       		    jsr.response(resp, response);
        	}
        	else {
           	 // email或password為空的處理
               JSONObject resp = new JSONObject();
               resp.put("status", "400");
               resp.put("success", false);
               resp.put("message", "請提供有效的Email和密碼");
               jsr.response(resp, response);
           }
        }
        else {
            /** 透過MemberHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = memh.getByID(member_id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員資料取得成功");
            resp.put("response", query);
    
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
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int member_id = jso.getInt("member_id");
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
        JSONObject query = memh.deleteByID(member_id);
        
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
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int member_id = jso.getInt("member_id");
        String member_email = jso.getString("member_email");
        String member_password = jso.getString("member_password");
        String member_first_name = jso.getString("member_first_name");
        String member_last_name = jso.getString("member_last_name");
        String member_phone_number = jso.getString("member_phone_number");

        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
        Member m = new Member(member_id, member_email, member_password, member_first_name, member_last_name, member_phone_number);
        
        /** 透過Member物件的updateMember()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = m.updateMember();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
}