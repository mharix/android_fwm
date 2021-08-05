<?php

// $servername ="mysql-12642-0.cloudclusters.net";
// $username ="harix";
// $password ="932125";
// $database="fwm_db";
// $db_port = '12672';


// // Create connection
// $conn = new mysqli($servername, $username, $password,$database,$db_port);

$servername = "localhost";
$username = "id14634589_fwmuser";
$password = "*456Mhtye#665u";
$database="id14634589_fwm";

$conn = new mysqli($servername, $username, $password,$database);

// Check connection
if ($conn->connect_error) {
	echo '<script language="javascript">';
echo 'alert("Connection Failed!")';
echo '</script>'; 
}


?>