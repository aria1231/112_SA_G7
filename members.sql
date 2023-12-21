-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2023-12-21 17:24:58
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
-- 資料庫： `missa`
--

-- --------------------------------------------------------

--
-- 資料表結構 `members`
--

CREATE TABLE `members` (
  `member_id` int(11) NOT NULL,
  `member_first_name` varchar(100) NOT NULL,
  `member_last_name` varchar(100) NOT NULL,
  `member_email` varchar(200) NOT NULL,
  `member_update_time` datetime NOT NULL,
  `member_created_time` date NOT NULL,
  `member_phone_number` varchar(10) NOT NULL,
  `member_password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 傾印資料表的資料 `members`
--

INSERT INTO `members` (`member_id`, `member_first_name`, `member_last_name`, `member_email`, `member_update_time`, `member_created_time`, `member_phone_number`, `member_password`) VALUES
(2, '測試', '人員1', 'test1@cc.nuc.edu.tw', '2023-12-21 17:18:54', '2023-12-21', '0912345678', 'Test123456'),
(3, '測試', '人員2', 'test2@cc.ncu.edu.tw', '2023-12-21 17:21:51', '2023-12-21', '0912345666', 'Test123456'),
(4, '測試', '人員3', 'test3@cc.ncu.edu.tw', '2023-12-21 17:21:51', '2023-12-21', '0900123123', 'Test123456'),
(5, '測試', '人員4', 'test4@cc.ncu.edu.tw', '2023-12-21 17:24:06', '2023-12-21', '0900123456', 'Test123456');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`member_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `members`
--
ALTER TABLE `members`
  MODIFY `member_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
