import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

public class ManagerController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** manh，ManagerHelper之物件與Manager相關之資料庫方法（Sigleton） */
    private ManagerHelper manh =  ManagerHelper.getHelper();

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