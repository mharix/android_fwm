<?php

@require'connection.php';

   
  	$eid=$_POST['eid'];
   		    
			     
$sql = "SELECT  `latitude`, `longitude` FROM `tbl_event` WHERE id='$eid";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array(
    'lt'=>$row[0],
'lg'=>$row[1]

));
}
 
echo json_encode(array("result"=>$result));
	 
 
mysqli_close($conn);

?>
