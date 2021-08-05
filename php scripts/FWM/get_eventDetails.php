<?php

@require'connection.php';

  	$eid=$_POST['eid'];
  
 
$sql = "select event_name,datetime,image,dress,address,latitude,longitude from tbl_event where id='$eid'";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array(
    'name'=>$row[0],
'date'=>$row[1],
'image'=>$row[2],
'dress'=>$row[3],
'address'=>$row[4],
'lt'=>$row[5],
'lg'=>$row[6]

));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($conn);

?>
