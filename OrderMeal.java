package ncu.im3069.demo.app;

import org.json.JSONObject;
import ncu.im3069.demo.util.Arith;

public class OrderMeal {

    /** id，餐點細項編號 */
    private int id;

    /** meal，餐點 */
    private Meal meal;

    /** serving，餐點數量 */
    private int omlinking_serving;

    /** price，餐點價格 */
    private int omlinking_price;

    /** subtotal，餐點小計 */
    private int omlinking_subtotal;

    /** meah，MealHelper 之物件與 Meal 相關之資料庫方法（Sigleton） */
    private MealHelper meah = MealHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）OrderMeal 物件<br>
     * 此建構子用於建立訂單細項時
     *
     * @param meal 餐點
     * @param omlinking_serving 餐點數量
     */
    public OrderMeal(Meal meal, int omlinking_serving) {
        this.meal = meal;
        this.omlinking_serving = omlinking_serving;
        this.omlinking_price = this.meal.getPrice();
        this.omlinking_subtotal = Arith.mul(this.omlinking_serving, this.omlinking_price);		
    }

    /**
     * 實例化（Instantiates）一個新的（new）OrderItem 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單細項時
     *
     * @param oml_id 訂單產品編號
     * @param order_id 訂單編號
     * @param meal_id 餐點編號
     * @param omlinking_price 餐點價格
     * @param omlinking_surving 餐點數量
     * @param omlinking_subtotal 小計
     */
	 
    public OrderMeal(int oml_id, int order_id, int meal_id, int omlinking_price, int omlinking_serving, int omlinking_subtotal) {
        this.id = oml_id;
        this.omlinking_serving = omlinking_serving;
        this.omlinking_price = omlinking_price;
        this.omlinking_subtotal = omlinking_subtotal;
        getMealFromDB(meal_id);
    }

    /**
     * 設定訂單的餐點細項編號
     */
    public void setOmlMealId(int id) {
        this.meal_id = id;
    }

    /**
     * 從 DB 中取得餐點
     */
    private void getMealFromDB(int meal_id) {
        String id = String.valueOf(meal_id);
        this.meal = meah.getById(id);
    }

    /**
     * 取得餐點
     *
     * @return Meal 回傳餐點
     */
    public Meal getOmlMeal() {
        return this.meal;
    }

    /**
     * 取得訂單細項的餐點編號
     *
     * @return int 回傳訂單細項的餐點編號
     */
    public int getOmlMealId() {
        return this.meal_id;
    }

    /**
     * 取得餐點價格
     *
     * @return int 回傳餐點價格
     */
    public int getOmlPrice() {
        return this.omlinking_price;
    }

    /**
     * 取得餐點細項小計
     *
     * @return int 回傳餐點細項小計
     */
    public int getOmlSubTotal() {
        return this.omlinking_subtotal;
    }

    /**
     * 取得餐點數量
     *
     * @return int 回傳餐點數量
     */
    public int getOmlServing() {
        return this.omlinking_serving;
    }

    /**
     * 取得餐點細項資料
     *
     * @return JSONObject 回傳餐點細項資料
     */
    public JSONObject getOmlData() {
        JSONObject data = new JSONObject();
        data.put("meal_id", getOmlMealId());
        data.put("meal", getOmlMeal().getData());
        data.put("omlinking_price", getOmlPrice());
        data.put("omlinking_serving", getOmlServing());
        data.put("omlinking_subtotal", getOmlSubTotal());

        return data;
    }
	
}
