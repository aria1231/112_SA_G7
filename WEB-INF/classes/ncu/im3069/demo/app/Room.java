package ncu.im3069.demo.app;

import org.json.*;

import java.security.Timestamp;
import java.util.Calendar;

public class Room {

    /** id，會員編號 */
    private int room_id;

    /** id，會員編號 */
    private String room_name;

    /** id，會員編號 */
    private int room_price;

    /** id，會員編號 */
    private String room_image;

    /** id，會員編號 */
	private String room_description;
	
	/** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private RoomHelper rooh =  RoomHelper.getHelper();

	private Timestamp room_update_time;
	
    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
	public Room(String room_name, int room_price, String room_description, String room_image) {
		this.room_name = room_name;
		this.room_price = room_price;
		this.room_description= room_description;
		this.room_image = room_image;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新產品時
     *
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     */
	public Room(int room_id, String room_name, int room_price, String room_description, String room_image) {
		this.room_id = room_id;
		this.room_name = room_name;
		this.room_price = room_price;
		this.room_description = room_description;
		this.room_image = room_image;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於查看產品時
     *
     * @param id 產品編號
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     * @param describe 產品敘述
     */
	public Room(int room_id, String room_name, int room_price, String room_description, String room_image, Timestamp room_update_time) {
		this.room_id = room_id;
		this.room_name = room_name;
		this.room_price = room_price;
		this.room_description = room_description;
		this.room_image = room_image;
		this.room_update_time = room_update_time;
	}

    /**
     * 取得產品編號
     *
     * @return int 回傳產品編號
     */
	public int getRoomID() {
		return this.room_id;
	}

    /**
     * 取得產品名稱
     *
     * @return String 回傳產品名稱
     */
	public String getRoomNAME() {
		return this.room_name;
	}

    /**
     * 取得產品價格
     *
     * @return double 回傳產品價格
     */
	public int getRoomPRICE() {
		return this.room_price;
	}

    /**
     * 取得產品圖片
     *
     * @return String 回傳產品圖片
     */
	public String getRoomIMAGE() {
		return this.room_image;
	}

    /**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public String getRoomDESCRIPTION() {
		return this.room_description;
	}
	
	 /**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public Timestamp getRoomUPDATETIME() {
		return this.room_update_time;
	}

    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getRoomData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("room_id", getRoomID());
        jso.put("room_name", getRoomNAME());
        jso.put("room_price", getRoomPRICE());
        jso.put("room_image", getRoomIMAGE());
        jso.put("room_description", getRoomDESCRIPTION());

        return jso;
    }
	
	/*private void getRoHUPDATETIME(){
		JSONObject data = rooh.getRoomUPDATETIME(this);
		this.room_update_time = data.getTimestamp("room_update_time");
	}*/
	
	public JSONObject updateRoom(){
		/** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間）之分鐘數 */
        //Calendar calendar = Calendar.getInstance();
        //this.room_update_time = calendar.get(Calendar.MINUTE);
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.room_id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            //rooh.updateRoomUPDATETIME(this);
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = rooh.updateRoom(this);
        }
        
        return data;
	}
}
