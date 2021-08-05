<?php

@require'connection.php';

  	$type=$_POST['type'];
  
 
$sql = "select * from tbl_food   where type='$type'";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array(
    'id'=>$row[0],
'name'=>$row[1]

));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($conn);

?>
