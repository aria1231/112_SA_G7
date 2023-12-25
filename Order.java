package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Order {

    /** order_id，訂單編號 */
    private int order_id;

    /** member_id，會員編號 */
    private int member_id;

    /** movie_id，電影編號 */
    private int movie_id;

    /** room_id，包廂編號 */
    private int room_id;

    /** order_date，預約哪一天 */
    private String order_date;

    /** order_total_price，訂單總金額 */
    private int order_total_price;
	
	/** order_time_of_day，預約的時段 */
    private int order_time_of_day;
	
	/** order_status，訂單狀態 */
    private int order_status;
	
    /** datetime，訂單創建時間 */
    private Timestamp order_datetime = null;
	
    /** list，餐點列表 */
    private ArrayList<OrderMeal> list = new ArrayList<OrderMeal>();


    /** omh，OrderMealHelper 之物件與 Order 相關之資料庫方法（Sigleton） */
    private OrderMealHelper omh = OrderMealHelper.getHelper();


    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
     *
     * @param member_id 會員編號 
     * @param movie_id 電影編號
     * @param room_id 包廂編號
     * @param date 預約哪一天
     * @param time_of_day 預約的時段
     */
	public Order(int member_id,int movie_id,int room_id,String date,int time_of_day) {		
        this.member_id = member_id;
        this.movie_id = movie_id;
        this.room_id = room_id;
        this.order_date = date;
        this.order_time_of_day = time_of_day;
		this.order_datetime = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於取得訂單資料時
     *
	 * @param order_id 訂單編號
     * @param member_id 會員編號
     * @param movie_id 電影編號
     * @param room_id 包廂編號
     * @param order_date 預約哪一天
     * @param order_total_price 訂單總金額
     * @param order_time_of_day 預約哪個時段
     * @param order_datetime 訂單成立時間
	 * @param order_status 訂單狀態
     */
	 
    public Order(int order_id, int member_id, int movie_id, int room_id, String order_date, int order_total_price, int order_time_of_day, Timestamp order_datetime, int order_status) {
        this.order_id = order_id;
        this.member_id = member_id;
        this.movie_id = movie_id;
        this.room_id = room_id;
        this.order_date = order_date;
        this.order_total_price = order_total_price;
        this.order_time_of_day = order_time_of_day;
        this.order_datetime = order_datetime;
		this.order_status = order_status;
        getOrderMealFromDB();
    } 

    /**
     * 新增一個餐點及其數量
     */
    public void addOrderMeal(Meal meal, int serving) {
        this.list.add(new OrderMeal(meal, serving));
    }

    /**
     * 新增一個餐點
     */
    public void addOrderMeal(OrderMeal om) {
        this.list.add(om);
    }


    /**
     * 取得訂單編號
     *
     * @return int 回傳訂單編號
     */
    public int getOrderId() {
        return this.order_id;
    }

    /**
     * 取得訂單會員的編號
     *
     * @return int 回傳訂單會員的編號
     */
    public int getOrderMemberId() {
        return this.member_id;
    }

    /**
     * 取得訂單電影編號
     *
     * @return int 回傳訂單電影編號
     */
    public int getOrderMovieId() {
        return this.movie_id;
    }

    /**
     * 取得訂單包廂編號
     *
     * @return int 回傳訂單包廂編號
     */
    public int getOrderRoomId() {
        return this.room_id;
    }
	
	/**
     * 取得訂單預約哪一天
     *
     * @return Date 回傳訂單預約哪一天
     */
    public String getOrderDate() {
        return this.order_date;
    }
	
	/**
     * 取得訂單總金額
     *
     * @return int 回傳訂單總金額
     */
    public int getOrderTotalPrice() {
        return this.order_total_price;
    }
	
	/**
     * 取得訂單預約哪個時段
     *
     * @return int 回傳訂單預約哪個時段
     */
    public int getOrderTimeOfDay() {
        return this.order_time_of_day;
    }
	
    /**
     * 取得訂單創建時間
     *
     * @return Timestamp 回傳訂單創建時間
     */
    public Timestamp getOrderDatetime() {
        return this.order_datetime;
    }

    

    /**
     * 取得訂單狀態
     *
     * @return int 回傳訂單狀態
     */
    public int getOrderStatus() {
        return this.order_status;
    }


    /**
     * 取得餐點的清單
     *
     * @return ArrayList<OrderMeal> 取得餐點的清單並封裝於JSONObject物件內
     */
    public ArrayList<OrderMeal> getOrderMealList() {
        return this.list;
    }

    /**
     * 從 DB 中取得訂單餐點
     */
    private void getOrderMealFromDB() {
        ArrayList<OrderMeal> data = omh.getOrderMealByOrderId(this.order_id);
        this.list = data;
    }

    /**
     * 取得訂單基本資料
     *
     * @return JSONObject 取得訂單基本資料
     */
    public JSONObject getOrderData() {
        JSONObject jso = new JSONObject();
        jso.put("order_id", getOrderId());
        jso.put("member_id", getOrderMemberId());
        jso.put("movie_id", getOrderMovieId());
        jso.put("room_id", getOrderRoomId());
        jso.put("date", getOrderDate());
        jso.put("total_price", getOrderTotalPrice());
        jso.put("time_of_day", getOrderTimeOfDay());
		jso.put("datetime", getOrderDatetime());
		int s = getOrderStatus();
		if(s == 0){
			jso.put("status", "未完成");
		}else if(s == 1){
			jso.put("status", "已完成");
		}

        return jso;
    }

    /**
     * 取得訂單餐點資料
     *
     * @return JSONArray 取得訂單餐點資料
     */
    public JSONArray getOrderMealData() {
        JSONArray result = new JSONArray();

        for(int i=0 ; i < this.list.size() ; i++) {
            result.put(this.list.get(i).getOmlData());
        }

        return result;
    }

    /**
     * 取得訂單所有資訊
     *
     * @return JSONObject 取得訂單所有資訊
     */
    public JSONObject getOrderAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("order_info", getOrderData());
        jso.put("product_info", getOrderMealData());

        return jso;
    }

    /**
     * 設定訂單編號
     */
    public void setOrderId(int id) {
        this.order_id = id;
    }

    /**
     * 設定訂單餐點編號
     */
    public void setOrderMealId(JSONArray data) {
        for(int i=0 ; i < this.list.size() ; i++) {
            this.list.get(i).setOmlMealId((int) data.getLong(i));
        }
    }
	
	/**
     * 設定訂單總金額
     */
	public void setOrderTotalPrice(int total_price){
		this.order_total_price = total_price;
	}
	
}
