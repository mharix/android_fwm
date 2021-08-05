
CREATE TABLE `tbl_event` (
  `id` int NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `datetime` datetime NOT NULL,
  `address` varchar(50) NOT NULL,
  `image` text  NOT NULL,
  `dress` varchar(50) NOT NULL,
  `user_id` int NOT NULL,
  `latitude` varchar(100) DEFAULT '67.0746475',
  `longitude` varchar(100) NOT NULL DEFAULT '24.8630439'
);

CREATE TABLE `tbl_food` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL DEFAULT 'dish'
);

CREATE TABLE `tbl_foodfor_event` (
  `sid` int NOT NULL,
  `fid` int NOT NULL
);
CREATE TABLE `tbl_food_pref` (
  `sid` int NOT NULL,
  `fid` int NOT NULL,
  `uid` int NOT NULL
);
CREATE TABLE `tbl_users` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `pass` varchar(8) NOT NULL,
  `phone` varchar(11) NOT NULL
);
ALTER TABLE `tbl_event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);


ALTER TABLE `tbl_food`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

 
ALTER TABLE `tbl_foodfor_event`
  ADD KEY `sid` (`sid`),
  ADD KEY `fid` (`fid`);

 
ALTER TABLE `tbl_food_pref`
  ADD KEY `sid` (`sid`),
  ADD KEY `fid` (`fid`),
  ADD KEY `uid` (`uid`);

 
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`id`);

 
ALTER TABLE `tbl_event`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;


ALTER TABLE `tbl_food`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;


ALTER TABLE `tbl_users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

 
ALTER TABLE `tbl_event`
  ADD CONSTRAINT `tbl_event_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`);

 
ALTER TABLE `tbl_foodfor_event`
  ADD CONSTRAINT `tbl_foodfor_event_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `tbl_event` (`id`),
  ADD CONSTRAINT `tbl_foodfor_event_ibfk_2` FOREIGN KEY (`fid`) REFERENCES `tbl_food` (`id`);

 
ALTER TABLE `tbl_food_pref`
  ADD CONSTRAINT `tbl_food_pref_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `tbl_event` (`id`),
  ADD CONSTRAINT `tbl_food_pref_ibfk_2` FOREIGN KEY (`fid`) REFERENCES `tbl_food` (`id`),
  ADD CONSTRAINT `tbl_food_pref_ibfk_3` FOREIGN KEY (`uid`) REFERENCES `tbl_users` (`id`);
COMMIT;
 