<?php

@require'connection.php';

  	$eid=$_POST['eid'];
  
 
$sql = "SELECT f.id, f.name from tbl_food f ,tbl_foodfor_event e where f.id=e.fid and e.sid='$eid'";
 
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
