package ncu.im3069.demo.app;

import org.json.*;

import java.security.Timestamp;
import java.util.Calendar;

public class Meal {

    /** id，會員編號 */
    private int meal_id;

    /** id，會員編號 */
    private String meal_name;

    /** id，會員編號 */
    private int meal_price;

    /** id，會員編號 */
    private String meal_image;

    /** id，會員編號 */
	private String meal_description;
	
	private Timestamp meal_update_time;
	
	/** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MovieHelper movh = MovieHelper.getHelper();
	
    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
	public Meal(String meal_name, int meal_price, String meal_description, String meal_image) {
		this.meal_name = meal_name;
		this.meal_price = meal_price;
		this.meal_description= meal_description;
		this.meal_image = meal_image;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新產品時
     *
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     */
	public Meal(int meal_id, String meal_name, int meal_price, String meal_description, String meal_image) {
		this.meal_id = meal_id;
		this.meal_name = meal_name;
		this.meal_price = meal_price;
		this.meal_description = meal_description;
		this.meal_image = meal_image;
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
	public Meal(int meal_id, String meal_name, int meal_price, String meal_description, String meal_image, Timestamp meal_update_time) {
		this.meal_id = meal_id;
		this.meal_name = meal_name;
		this.meal_price = meal_price;
		this.meal_description = meal_description;
		this.meal_image = meal_image;
		this.meal_update_time = meal_update_time;
	}

    /**
     * 取得產品編號
     *
     * @return int 回傳產品編號
     */
	public int getMealID() {
		return this.meal_id;
	}

    /**
     * 取得產品名稱
     *
     * @return String 回傳產品名稱
     */
	public String getMealNAME() {
		return this.meal_name;
	}

    /**
     * 取得產品價格
     *
     * @return double 回傳產品價格
     */
	public int getMealPRICE() {
		return this.meal_price;
	}

    /**
     * 取得產品圖片
     *
     * @return String 回傳產品圖片
     */
	public String getMealIMAGE() {
		return this.meal_image;
	}

    /**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public String getMealDESCRIPTION() {
		return this.meal_description;
	}
	
	 /**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public Timestamp getMealUPDATETIME() {
		return this.meal_update_time;
	}

    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getMealData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("meal_id", getMealID());
        jso.put("meal_name", getMealNAME());
        jso.put("meal_price", getMealPRICE());
        jso.put("meal_image", getMealIMAGE());
        jso.put("meal_description", getMealDESCRIPTION());

        return jso;
    }
	
	/*private void getRoHUPDATETIME(){
		JSONObject data = rooh.getRoomUPDATETIME(this);
		this.room_update_time = data.getTimestamp("room_update_time");
	}*/
	
	/*public JSONObject updateMeal(){
		/** 新建一個JSONObject用以儲存更新後之資料 */
        /*JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間）之分鐘數 */
        /*Calendar calendar = Calendar.getInstance();
        this.meal_update_times = calendar.get(Calendar.MINUTE);
        
        /** 檢查該名會員是否已經在資料庫 */
        /*if(this.meal_id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            //meah.updateMealUPDATETIME(this);
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
        /*    data = meah.updateMeal(this);
        }
        
        return data;
	}*/
}
