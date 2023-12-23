package ncu.im3069.demo.app;

import org.json.*;

import java.security.Timestamp;

public class Movie {

    /** id 電影編號 */
    private int movie_id;

    /** name 電影名稱 */
    private String movie_name;

    /** time 電影時長 */
    private int movie_time;

    /** image 電影圖片 */
    private String movie_image;

    /** description 電影描述 */
	private String movie_description;

    /** update time 電影更新時間 */
	private Timestamp movie_update_time;

    /** type 電影類型 */
	// 1代表愛情 2代表劇情 3代表動作 4代表驚悚
	private int movie_type;
	
    /**
     * 實例化（Instantiates）一個新的（new）Movie 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增電影時
     *
     * @param name 電影名稱
     * @param time 電影時長
     * @param description 電影描述
     * @param image 電影圖片
     * @param type 電影類型
     */
	public Movie(String movie_name, int movie_time, String movie_description, String movie_image, int movie_type) {
		this.movie_name = movie_name;
		this.movie_time = movie_time;
		this.movie_description= movie_description;
		this.movie_image = movie_image;
		this.movie_type = movie_type;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Movie 物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新電影時
     *
     * @param id 電影編號
     * @param name 電影名稱
     * @param time 電影時長
     * @param description 電影描述
     * @param image 電影圖片
     * @param type 電影類型
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
     * 實例化（Instantiates）一個新的（new）Movie 物件<br>
     * 採用多載（overload）方法進行，此建構子用於查看電影時
     *
     * @param id 電影編號
     * @param name 電影名稱
     * @param time 電影時長
     * @param description 電影描述
     * @param image 電影圖片
     * @param update time 電影更新時間
     * @param type 電影類型
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
     * 取得電影編號
     *
     * @return int 回傳電影編號
     */
	public int getMovieID() {
		return this.movie_id;
	}

    /**
     * 取得電影名稱
     *
     * @return String 回傳電影名稱
     */
	public String getMovieNAME() {
		return this.movie_name;
	}

    /**
     * 取得電影時長
     *
     * @return int 回傳電影時長
     */
	public int getMovieTIME() {
		return this.movie_time;
	}

    /**
     * 取得電影圖片
     *
     * @return String 回傳電影圖片
     */
	public String getMovieIMAGE() {
		return this.movie_image;
	}

    /**
     * 取得電影敘述
     *
     * @return String 回傳電影敘述
     */
	public String getMovieDESCRIPTION() {
		return this.movie_description;
	}
	
	 /**
     * 取得電影更新時間
     *
     * @return String 回傳電影更新時間
     */
	public Timestamp getMovieUPDATETIME() {
		return this.movie_update_time;
	}
	
	/**
     * 取得電影類型
     *
     * @return String 回傳電影類型
     */
	public int getMovieTYPE(){
		return this.movie_type;
	}

    /**
     * 取得電影資訊
     *
     * @return JSONObject 回傳電影資訊
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
}
