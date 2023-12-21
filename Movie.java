package ncu.im3069.demo.app;

import org.json.*;

import java.security.Timestamp;
import java.util.Calendar;

public class Movie {

    /** id，會員編號 */
    private int movie_id;

    /** id，會員編號 */
    private String movie_name;

    /** id，會員編號 */
    private int movie_time;

    /** id，會員編號 */
    private String movie_image;

    /** id，會員編號 */
	private String movie_description;
	
	private Timestamp movie_update_time;
	
	// 1代表愛情 2代表劇情 3代表動作 4代表驚悚
	private int movie_type;
	
	/** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MovieHelper movh =  MovieHelper.getHelper();
	
    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
	public Movie(String movie_name, int movie_time, String movie_description, String movie_image, int movie_type) {
		this.movie_name = movie_name;
		this.movie_time = movie_time;
		this.movie_description= movie_description;
		this.movie_image = movie_image;
		this.movie_type = movie_type;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新產品時
     *
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     */
	public Movie(int movie_id, String movie_name, int movie_time, String movie_description, String movie_image, int movie_type) {
		this.movie_id = movie_id;
		this.movie_name = movie_name;
		this.movie_time = movie_time;
		this.movie_description = movie_description;
		this.movie_image = movie_image;
		this.movie_type = movie_type;
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
	public Movie(int movie_id, String movie_name, int movie_time, String movie_description, String movie_image, Timestamp movie_update_time, int movie_type) {
		this.movie_id = movie_id;
		this.movie_name = movie_name;
		this.movie_time = movie_time;
		this.movie_description = movie_description;
		this.movie_image = movie_image;
		this.movie_update_time = movie_update_time;
		this.movie_type = movie_type;
	}

    /**
     * 取得產品編號
     *
     * @return int 回傳產品編號
     */
	public int getMovieID() {
		return this.movie_id;
	}

    /**
     * 取得產品名稱
     *
     * @return String 回傳產品名稱
     */
	public String getMovieNAME() {
		return this.movie_name;
	}

    /**
     * 取得產品價格
     *
     * @return double 回傳產品價格
     */
	public int getMovieTIME() {
		return this.movie_time;
	}

    /**
     * 取得產品圖片
     *
     * @return String 回傳產品圖片
     */
	public String getMovieIMAGE() {
		return this.movie_image;
	}

    /**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public String getMovieDESCRIPTION() {
		return this.movie_description;
	}
	
	 /**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public Timestamp getMovieUPDATETIME() {
		return this.movie_update_time;
	}
	
	public int getMovieTYPE(){
		return this.movie_type;
	}

    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getMovieData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("movie_id", getMovieID());
        jso.put("movie_name", getMovieNAME());
        jso.put("movie_time", getMovieTYPE());
        jso.put("movie_image", getMovieIMAGE());
        jso.put("movie_description", getMovieDESCRIPTION());
		jso.put("movie_type", getMovieTYPE());

        return jso;
    }
	
	/*private void getRoHUPDATETIME(){
		JSONObject data = rooh.getRoomUPDATETIME(this);
		this.room_update_time = data.getTimestamp("room_update_time");
	}*/
	
	/*public JSONObject updateMovie(){
		/** 新建一個JSONObject用以儲存更新後之資料 */
        /*JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間）之分鐘數 */
        /*Calendar calendar = Calendar.getInstance();
        this.movie_update_times = calendar.get(Calendar.MINUTE);
        
        /** 檢查該名會員是否已經在資料庫 */
        /*if(this.movie_id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            //rooh.updateRoomUPDATETIME(this);
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
        /*    data = movh.updateMovie(this);
        }
        
        return data;
	}*/
}
