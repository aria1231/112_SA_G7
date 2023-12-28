package ncu.im3069.demo.app;

import org.json.*;
//import java.security.Timestamp;
//import java.time.LocalDateTime;
//import java.util.*;
//import ncu.im3069.demo.app.MemberHelper;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member
 * Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member {
    
    /** member_id，會員編號 */
    private int member_id;
    
    /** member_email，會員電子郵件信箱 */
    private String member_email;
    
    /** member_first_name，會員名 */
    private String member_first_name;
    
    /** member_last_name，會員姓 */
    private String member_last_name;
   
    // member_phone_number，會員電話號碼
    private String member_phone_number;

    /** member_password，會員密碼 */
    private String member_password;
 
    /** memh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper memh =  MemberHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時，產生一名新的會員
     *
     * @param member_email 會員電子信箱
     * @param member_password 會員密碼
     * @param member_first_name 會員名
     * @param member_last_name 會員姓
     * @param member_phone_number 會員電話號碼
     */
    public Member(String member_email, String member_password, String member_first_name, String member_last_name, String member_phone_number) {
        this.member_email = member_email;
        this.member_password = member_password;
        this.member_first_name = member_first_name;
        this.member_last_name = member_last_name;
        this.member_phone_number = member_phone_number;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時，將每一筆資料新增為一個會員物件
     * 
     * @param member_id 會員編號
     * @param member_email 會員電子信箱
     * @param member_password 會員密碼
     * @param member_first_name 會員名
     * @param member_last_name 會員姓
     * @param member_phone_number 會員電話號碼
     */
    public Member(int member_id, String member_email, String member_password, String member_first_name, String member_last_name, String member_phone_number) {
        this.member_id = member_id;
        this.member_email = member_email;
        this.member_password = member_password;
        this.member_first_name = member_first_name;
        this.member_last_name = member_last_name;
        this.member_phone_number = member_phone_number;
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param member_id 會員編號
     * @param member_email 會員電子信箱
     * @param member_first_name 會員名
     * @param member_last_name 會員姓
     * @param member_phone_number 會員電話號碼
     */
//    public Member(int member_id, String member_email, String member_first_name, String member_last_name, String member_phone_number) {
//        this.member_id = member_id;
//        this.member_email = member_email;
//        this.member_first_name = member_first_name;
//        this.member_last_name = member_last_name;
//        this.member_phone_number = member_phone_number;
//    }
    
    public Member(int member_id) {
    	this.member_id = member_id;
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於會員登入時，將每一筆資料新增為一個會員物件
     * 
     * @param member_email 會員電子信箱
     * @param member_password 會員密碼
     */
    public Member(String member_email, String member_password) {
        this.member_email = member_email;
        this.member_password = member_password;
    }
    
    /**
     * 取得會員之編號
     *
     * @return the member_id 回傳會員編號
     */
    public int getMemberID() {
        return this.member_id;
    }

    /**
     * 取得會員之電子郵件信箱
     *
     * @return the member_email 回傳會員電子郵件信箱
     */
    public String getMemberEMAIL() {
        return this.member_email;
    }
    
    /**
     * 取得會員之名
     *
     * @return the member_first_name 回傳會員名
     */
    public String getMemberFIRSTNAME() {
        return this.member_first_name;
    }
    
    /**
     * 取得會員之姓
     *
     * @return the member_last_name 回傳會員姓
     */
    public String getMemberLASTNAME() {
        return this.member_last_name;
    }

    /**
     * 取得會員之密碼
     *
     * @return the member_ password 回傳會員密碼
     */
    public String getMemberPASSWORD() {
        return this.member_password;
    }
    
    /**
     * 取得會員之電話號碼
     *
     * @return the member_phone_number 回傳會員電話號碼
     */
    public String getMemberPHONENUMBER() {
        return this.member_phone_number;
    }
    
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject updateMember() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間） */
        //this.member_update_time = Timestamp.valueOf(LocalDateTime.now());
        
        
        /** 檢查該名會員是否已經在資料庫 */
        //if(this.member_id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            //memh.updateLoginTimes(this);
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = memh.updateMember(this);
        //}
        
        return data;
    }
    
    /**
     * 取得所有會員所有資料
     *
     * @return the data 取得所有會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getAllData() {
        /** 透過JSONObject將所有會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("member_id", getMemberID());
        jso.put("first_name", getMemberFIRSTNAME());
        jso.put("last_name", getMemberLASTNAME());
        jso.put("email", getMemberEMAIL());
        jso.put("password", getMemberPASSWORD());
        jso.put("phone_number", getMemberPHONENUMBER());
        
        return jso;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getOneData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("member_id", getMemberID());
        jso.put("first_name", getMemberFIRSTNAME());
        jso.put("last_name", getMemberLASTNAME());
        jso.put("email", getMemberEMAIL());
        jso.put("phone_number", getMemberPHONENUMBER());
        jso.put("password", getMemberPASSWORD());
        
        return jso;
    }
    
    public JSONObject getMemID() {
    	JSONObject jso = new JSONObject();
        jso.put("member_id", getMemberID());
        
        return jso;
    }
}