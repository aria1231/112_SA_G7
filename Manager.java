import org.json.*;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member
 * Member類別（class）具有管理員所需要之屬性與方法，並且儲存與管理員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
public class Manager {

    /** manager_id，管理員編號 */
    private int manager_id;
    
    /** manager_email，管理員電子郵件信箱 */
    private String manager_email;
    
    /** manager_name，管理員名 */
    private String manager_first_name;
    
    /** manager_name，管理員姓 */
    private String manager_last_name;
   
    // manager_phone_number，管理員電話號碼
    private String manager_phone_number;

    /** manager_password，管理員密碼 */
    private String manager_password;
 
    /** manh，ManagerHelper之物件與Manager相關之資料庫方法（Sigleton） */
    private ManagerHelper manh =  ManagHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Manager物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢管理員資料時，將每一筆資料新增為一個管理員物件
     *
     * @param manager_id 管理員編號
     * @param manager_email 管理員電子信箱
     * @param manager_first_name 管理員名
     * @param manager_last_name 管理員姓
     * @param manager_phone_number 管理員電話號碼
     */
    public Manager(int manager_id, String manager_email, String manager_first_name, String manager_last_name, String manager_phone_number) {
        this.manager_id = manager_id;
        this.manager_email = manager_email;
        this.manager_first_name = manager_first_name;
        this.manager_last_name = manager_last_name;
        this.manager_phone_number = manager_phone_number;
    }
    
    /**
     * 取得管理員之編號
     *
     * @return the manager_id 回傳管理員編號
     */
    public int getManagerID() {
        return this.manager_id;
    }

    /**
     * 取得管理員之電子郵件信箱
     *
     * @return the manager_email 回傳管理員電子郵件信箱
     */
    public String getManagerEMAIL() {
        return this.manager_email;
    }
    
    /**
     * 取得管理員之名
     *
     * @return the manager_first_name 回傳管理員名
     */
    public String getManagerFIRSTNAME() {
        return this.manager_first_name;
    }
    
    /**
     * 取得管理員之姓
     *
     * @return the manager_last_name 回傳管理員姓
     */
    public String getManagerLASTNAME() {
        return this.manager_last_name;
    }

    /**
     * 取得管理員之密碼
     *
     * @return the manager_ password 回傳管理員密碼
     */
    public String getManagerPASSWORD() {
        return this.manager_password;
    }
    
    /**
     * 取得管理員之電話號碼
     *
     * @return the manager_phone_number 回傳管理員電話號碼
     */
    public String getManagerPHONENUMBER() {
        return this.manager_phone_number;
    }

    /**
     * 取得該名管理員所有資料
     *
     * @return the data 取得該名管理員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名管理員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("manager_id", getManagerID());
        jso.put("manager_first_name", getManagerFIRSTNAME());
        jso.put("manager_last_name", getManagerLASTNAME());
        jso.put("manager_email", getManagerEMAIL());
        jso.put("manager_phone_number", getManagerPHONENUMBER());
        
        return jso;
    }
}