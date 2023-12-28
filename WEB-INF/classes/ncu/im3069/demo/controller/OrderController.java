package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import ncu.im3069.demo.app.Order;
import ncu.im3069.demo.app.Meal;
import ncu.im3069.demo.app.MealHelper;
import ncu.im3069.demo.app.RoomHelper;
import ncu.im3069.demo.app.MovieHelper;
import ncu.im3069.demo.app.OrderHelper;
import ncu.im3069.demo.app.OrderMeal;
import ncu.im3069.demo.app.OrderMealHelper;
import ncu.im3069.demo.app.Room;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/order.do")
public class OrderController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** meah，MealHelper 之物件與 meal 相關之資料庫方法（Sigleton） */
    private MealHelper meah =  MealHelper.getHelper();
	
	/** rooh，RoomHelper 之物件與 Room 相關之資料庫方法（Sigleton） */
    private RoomHelper rooh =  RoomHelper.getHelper();
	
	/** movh，MovieHelper 之物件與 Movie 相關之資料庫方法（Sigleton） */
    private MovieHelper movh =  MovieHelper.getHelper();

    /** ordh，OrderHelper 之物件與 Order 相關之資料庫方法（Sigleton） */
	private OrderHelper ordh =  OrderHelper.getHelper();
	
	/** omh，OrderMealHelper 之物件與 OrderMeal 相關之資料庫方法（Sigleton） */
	private OrderMealHelper omh =  OrderMealHelper.getHelper();

    public OrderController() {
        super();
    }

    /**
     * 處理 Http Method 請求 GET 方法（取得資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);

        /** 取出經解析到 JsonReader 之 Request 參數 */
        String member_id = jsr.getParameter("member_id");

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();

        
         /** 透過 orderHelper 物件的 getByMemberId() 方法自資料庫取回該會員所有訂單之資料，回傳之資料為 JSONObject 物件 */
         JSONObject query = ordh.getByMemberId(member_id);
         resp.put("status", "200");
         resp.put("message", "會員訂單資料取得成功");
         resp.put("response", query);
        

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}

    /**
     * 處理 Http Method 請求 POST 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        

		/** 取出經解析到 JSONObject 之 Request 參數 */
		int member_id = jso.getInt("member_id");										/**參數名要記得確認 */
											
		int movie_id = jso.getInt("movie_id");  
				
		int room_id = jso.getInt("room_id");
		String roo_id = Integer.toString(room_id);
																						
		String date = jso.getString("order_date");																										
		
		String tod = jso.getString("time_of_day");
		int time_of_day = 0;
		if(tod.equals("9:00 - 12:00")) {
			time_of_day = 900;
		}else if(tod.equals("12:00 - 15:00")) {
			time_of_day = 1200;
		}else if(tod.equals("15:00 - 18:00")) {
			time_of_day = 1500;
		}
		
		
        JSONArray meal = jso.getJSONArray("meal");
        JSONArray meal_serving = jso.getJSONArray("meal_serving");
        
		
        /** 建立一個新的訂單物件 */
        Order od = new Order(member_id,movie_id,room_id,date,time_of_day);

        /** 將每一筆訂單餐點細項取出來 */
        for(int i=0 ; i < meal.length() ; i++) {
            int meal_id = meal.getInt(i);
            String mea_id = Integer.toString(meal_id);
            int amount = meal_serving.getInt(i);

            /** 透過 MealHelper 物件之 getById()，取得餐點的資料並加進訂單物件裡 *//**把各項商品加進訂單的arrayList<orderMeal>，並且這裡有算每樣商品的subtotal*/
            Meal m = meah.getById(mea_id);
            od.addOrderMeal(m, amount);												
        }
		
																					
		ArrayList<OrderMeal> o = od.getOrderMealList();								/**嘗試在這邊算total price*/
		Room r = rooh.getById(roo_id);												/**roo不是我打錯，是getById()裡面的參數型態就是String*/
		int total_price = r.getRoomPRICE();
		
		for(int i=0;i<o.size();i++){
			total_price += o.get(i).getOmlSubTotal();
		}
		od.setOrderTotalPrice(total_price);
		
        /** 透過 orderHelper 物件的 create() 方法新建一筆訂單至資料庫 *//**加進order跟order_meal_linking的table裡*/
        JSONObject result = ordh.create(od);										

        /** 設定回傳回來的訂單編號與訂單餐點細項編號 */
        od.setOrderId((int) result.getLong("order_id"));
        od.setOrderMealId(result.getJSONArray("order_meal_id"));

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "訂單新增成功！");
        resp.put("response", od.getOrderAllInfo());

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
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
        int order_id = jso.getInt("order_id");
        
		/** 透過OrderMealHelper物件的deleteOrderMealByOrderId()方法至資料庫刪除該筆訂單，回傳之資料為JSONObject物件 */
        JSONObject query1 = omh.deleteOrderMealByOrderId(order_id);
		
        /** 透過OrderHelper物件的deleteOrderByOrderId()方法至資料庫刪除該筆訂單，回傳之資料為JSONObject物件 */
        JSONObject query2 = ordh.deleteOrderByOrderId(order_id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "訂單移除成功！");
        resp.put("response1", query1);
		resp.put("response2", query2);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

}
