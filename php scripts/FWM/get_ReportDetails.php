<?php

@require'connection.php';

  	$eid=$_POST['eid'];
  	
  	$sql ="set @row_number=0";
  mysqli_query($conn,$sql);
 
$sql = "SELECT @row_number:=@row_number+1 AS 'row_number',u.name, GROUP_CONCAT(f.name)FROM tbl_users u,tbl_food f ,tbl_food_pref p where u.id=p.uid and p.fid=f.id and p.sid='$eid'
group by u.name";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array(
    'no'=>$row[0],
    'user'=>$row[1],
'food'=>$row[2]

));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($conn);

?>
