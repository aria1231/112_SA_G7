-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2023-12-21 19:13:52
-- 伺服器版本： 10.4.32-MariaDB
-- PHP 版本： 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `final_pj`
--

-- --------------------------------------------------------

--
-- 資料表結構 `room`
--

CREATE TABLE `room` (
  `room_id` int(11) NOT NULL,
  `room_name` varchar(100) NOT NULL,
  `room_price` int(10) NOT NULL DEFAULT 0,
  `room_description` varchar(250) DEFAULT NULL,
  `room_update_time` datetime NOT NULL,
  `room_image` varchar(250) DEFAULT NULL,
  `room_limited` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `room`
--

INSERT INTO `room` (`room_id`, `room_name`, `room_price`, `room_description`, `room_update_time`, `room_image`, `room_limited`) VALUES
(1, '雙人典雅包廂', 600, '配有雙人沙發及小方桌，提供大屏幕觀看，座位舒適可平躺，隔音佳。', '2023-12-20 16:34:29', 'room_1_2.jpg', 2),
(2, '雙人質感包廂', 700, '空間以黑色系為主色，沉浸式影廳色調，配備包含雙人沙發及大屏幕。', '2023-12-21 17:37:49', 'room_2_2.jpg', 2),
(3, '雙人雙螢幕包廂', 800, '提供兩個大屏幕進行觀賞，可使用耳機，配有兩張單人沙發。', '2023-12-21 17:37:49', 'room_3_2.jpg', 2),
(4, '雙人精緻包廂', 900, '包廂內含高級音箱，環繞音效絕佳，配有雙人沙發及一個大屏幕。', '2023-12-21 17:37:49', 'room_4_2.jpg', 2),
(5, '雙人高級包廂', 1000, '空間較寬敞舒適，可自行調控燈光亮度，配有雙人沙發及大屏幕。', '2023-12-21 17:37:49', 'room_5_2.jpg', 2),
(6, '四人家庭包廂', 1200, '空間色調為暖色系，可供家庭一同觀賞電影，配有可容納四人的大型沙發及投影幕。', '2023-12-21 17:49:52', 'room_6_4.jpg', 4),
(7, '四人經典包廂', 1300, '經典包廂配有兩張單人沙發及一張雙人沙發，長桌可供使用，輕鬆享受電影。', '2023-12-21 17:49:52', 'room_7_4.jpg', 4),
(8, '四人復古包廂', 1400, '空間採英式復古風格，配有復古風格的音箱，雙人沙發及大屏幕。', '2023-12-21 17:49:52', 'room_8_4.jpg', 4),
(9, '四人豪華包廂', 1500, '空間寬敞，可調整屏幕遠近度，配有前後各兩張單人躺椅使用。', '2023-12-21 17:49:52', 'room_9_4.jpg', 4),
(10, '五人豪華包廂', 1800, '包廂配有五張單人沙發可平躺，座位與座位間相隔避免互相影響，小型影廳可與親友一起沉浸電影。', '2023-12-21 18:49:57', 'room_10_5.jpg', 5),
(11, '六人經典包廂', 2000, '六人經典包廂採前後座位排列，燈光可自行調整，配有最新音響設備。', '2023-12-21 18:52:38', 'room_11_6.jpg', 6),
(12, '六人派對包廂', 2400, '派對包廂的座位採環繞式，可調整座位，六張單人座椅可平躺，享受一同分享電影之樂趣。', '2023-12-21 18:52:38', 'room_12_6.jpg', 6),
(13, '六人星空包廂', 3000, '星空包廂以宇宙為主題進行設計，觀眾不只可以沉浸電影，更能同時模擬在宇宙的放鬆感受。', '2023-12-21 18:52:38', 'room_13_6.jpg', 6),
(14, '八人典雅包廂', 4300, '典雅包廂採暖色調空間設計，配有六張可調式躺椅及雙人沙發椅，可自行調控環繞音效。', '2023-12-21 18:52:38', 'room_14_8.jpg', 8),
(15, '八人經典包廂', 4000, '經典包廂配有前後兩排各四張單人沙發椅，空間採階梯式，更貼近大螢幕。', '2023-12-21 18:52:38', 'room_15_8.jpg', 8),
(16, '八人豪華包廂', 4800, '豪華包廂仿造一般小型影廳，座位排列為馬蹄形，觀眾可更好看件大螢幕，配有四張大型雙人沙發椅，可平躺。', '2023-12-21 18:52:38', 'room_16_8.jpg', 8);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `room`
--
ALTER TABLE `room`
  MODIFY `room_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
