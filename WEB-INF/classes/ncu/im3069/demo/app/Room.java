package ncu.im3069.demo.app;

import org.json.*;

import java.security.Timestamp;

public class Room {

    /** id，包廂編號 */
    private int room_id;

    /** name 包廂名稱 */
    private String room_name;

    /** price 包廂價錢 */
    private int room_price;

    /** image 包廂照片 */
    private String room_image;

    /** description 包廂內容 */
	private String room_description;

    /** update time 更新時間 */
	private Timestamp room_update_time;
	
	/** update time 更新時間 */
	private int room_limited;
	
    /**
     * 實例化（Instantiates）一個新的（new）Room 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增包廂時
     *
     * @param name 包廂名稱
     * @param price 包廂價錢
     * @param description 包廂描述
     * @param image 包廂照片
     */
	public Room(String room_name, int room_price, String room_description, String room_image, int room_limited) {
		this.room_name = room_name;
		this.room_price = room_price;
		this.room_description= room_description;
		this.room_image = room_image;
		this.room_limited = room_limited;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Room 物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新包廂時
     *
     * @param id 包廂編號
     * @param name 包廂名稱
     * @param price 包廂價錢
     * @param description　包廂描述
     * @param image 包廂圖片
     */
	public Room(int room_id, String room_name, int room_price, String room_description, String room_image, int room_limited) {
		this.room_id = room_id;
		this.room_name = room_name;
		this.room_price = room_price;
		this.room_description = room_description;
		this.room_image = room_image;
		this.room_limited = room_limited;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於查看包廂時
     *
     * @param id 包廂編號
     * @param name 包廂名稱
     * @param price 包廂價格
     * @param image 包廂圖片
     * @param describe 產品敘述
     * @param update time 更新時間
     */
	public Room(int room_id, String room_name, int room_price, String room_description, String room_image, Timestamp room_update_time, int room_limited) {
		this.room_id = room_id;
		this.room_name = room_name;
		this.room_price = room_price;
		this.room_description = room_description;
		this.room_image = room_image;
		this.room_update_time = room_update_time;
		this.room_limited = room_limited;
	}

    /**
     * 取得包廂編號
     *
     * @return int 回傳包廂編號
     */
	public int getRoomID() {
		return this.room_id;
	}

    /**
     * 取得包廂名稱
     *
     * @return String 回傳包廂名稱
     */
	public String getRoomNAME() {
		return this.room_name;
	}

    /**
     * 取得包廂價格
     *
     * @return int 回傳包廂價格
     */
	public int getRoomPRICE() {
		return this.room_price;
	}

    /**
     * 取得包廂圖片
     *
     * @return String 回傳包廂圖片
     */
	public String getRoomIMAGE() {
		return this.room_image;
	}

    /**
     * 取得包廂敘述
     *
     * @return String 回傳包廂敘述
     */
	public String getRoomDESCRIPTION() {
		return this.room_description;
	}
	
	 /**
     * 取得包廂敘述
     *
     * @return String 回傳包廂敘述
     */
	public Timestamp getRoomUPDATETIME() {
		return this.room_update_time;
	}
	
	/**
     * 取得包廂敘述
     *
     * @return String 回傳包廂敘述
     */
	public int getRoomLIMITED() {
		return this.room_limited;
	}

    /**
     * 取得包廂資訊
     *
     * @return JSONObject 回傳包廂資訊
     */
	public JSONObject getRoomData() {
        /** 透過JSONObject將該項包廂所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("room_id", getRoomID());
        jso.put("room_name", getRoomNAME());
        jso.put("room_price", getRoomPRICE());
        jso.put("room_image", getRoomIMAGE());
        jso.put("room_description", getRoomDESCRIPTION());
        jso.put("room_limited", getRoomLIMITED());

        return jso;
    }
}
