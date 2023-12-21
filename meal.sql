-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2023-12-21 20:05:10
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
-- 資料表結構 `meal`
--

CREATE TABLE `meal` (
  `meal_id` int(6) NOT NULL,
  `meal_name` varchar(100) NOT NULL,
  `meal_price` int(11) NOT NULL DEFAULT 0,
  `meal_description` varchar(1000) DEFAULT NULL,
  `meal_update_time` datetime NOT NULL,
  `meal_image` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `meal`
--

INSERT INTO `meal` (`meal_id`, `meal_name`, `meal_price`, `meal_description`, `meal_update_time`, `meal_image`) VALUES
(1, '爆米花套餐A', 120, '小桶爆米花+飲料', '2023-12-21 19:57:01', 'meal_1.jpg'),
(2, '爆米花套餐B', 150, '中桶爆米花+飲料', '2023-12-21 19:57:01', 'meal_1.jpg'),
(3, '爆米花套餐C', 180, '大桶爆米花+飲料', '2023-12-21 19:57:01', 'meal_1.jpg'),
(4, '炸雞套餐', 260, '炸雞桶+小桶爆米花+飲料', '2023-12-21 19:57:01', 'meal_2.jpg'),
(5, '吉拿棒套餐', 270, '一盒吉他棒+飲料', '2023-12-21 19:57:01', 'meal_3.jpg'),
(6, '熱狗套餐', 320, '一支吉拿棒+一個熱狗+飲料', '2023-12-21 19:57:01', 'meal_4.jpg'),
(7, '漢堡套餐', 350, '兩隻炸雞+一個漢堡+飲料', '2023-12-21 19:57:01', 'meal_5.jpg');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `meal`
--
ALTER TABLE `meal`
  ADD PRIMARY KEY (`meal_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `meal`
--
ALTER TABLE `meal`
  MODIFY `meal_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
