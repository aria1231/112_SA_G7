package ncu.im3069.demo.app;

import org.json.*;

import java.security.Timestamp;

public class Meal {

    /** id, 餐點編號 */
    private int meal_id;

    /** name, 餐點名稱 */
    private String meal_name;

    /** price, 餐點價錢 */
    private int meal_price;

    /** image, 餐點圖片 */
    private String meal_image;

    /** description, 餐點描述 */
	private String meal_description;

    /** update time, 餐點更新時間 */
	private Timestamp meal_update_time;
	
    /**
     * 實例化（Instantiates）一個新的（new）Meal 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增餐點時
     *
     * @param name 餐點名稱
     * @param price 餐點價格
     * @param description 餐點描述
     * @param image 餐點圖片
     */
	public Meal(String meal_name, int meal_price, String meal_description, String meal_image) {
		this.meal_name = meal_name;
		this.meal_price = meal_price;
		this.meal_description= meal_description;
		this.meal_image = meal_image;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Meal 物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新餐點時
     *
     * @param id 餐點編號
     * @param name 餐點名稱
     * @param price 餐點價格
     * @param description 餐點描述
     * @param image 餐點圖片
     */
	public Meal(int meal_id, String meal_name, int meal_price, String meal_description, String meal_image) {
		this.meal_id = meal_id;
		this.meal_name = meal_name;
		this.meal_price = meal_price;
		this.meal_description = meal_description;
		this.meal_image = meal_image;
	}

    /**
     * 實例化（Instantiates）一個新的（new) Meal 物件<br>
     * 採用多載（overload）方法進行，此建構子用於查看餐點時
     *
     * @param id 餐點編號
     * @param name 餐點名稱
     * @param price 餐點價格
     * @param description 餐點描述
     * @param image 餐點圖片
     * @param update time 餐點更新時間
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
     * 取得餐點編號
     *
     * @return int 回傳餐點編號
     */
	public int getMealID() {
		return this.meal_id;
	}

    /**
     * 取得餐點名稱
     *
     * @return String 回傳餐點名稱
     */
	public String getMealNAME() {
		return this.meal_name;
	}

    /**
     * 取得餐點價格
     *
     * @return int 回傳餐點價格
     */
	public int getMealPRICE() {
		return this.meal_price;
	}

    /**
     * 取得餐點圖片
     *
     * @return String 回傳餐點圖片
     */
	public String getMealIMAGE() {
		return this.meal_image;
	}

    /**
     * 取得餐點敘述
     *
     * @return String 回傳餐點敘述
     */
	public String getMealDESCRIPTION() {
		return this.meal_description;
	}
	
	 /**
     * 取得餐點更新時間
     *
     * @return Timestamp 回傳餐點更新時間
     */
	public Timestamp getMealUPDATETIME() {
		return this.meal_update_time;
	}

    /**
     * 取得餐點資訊
     *
     * @return JSONObject 回傳餐點資訊
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
}
