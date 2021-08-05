<?php

@require'connection.php';

  	$email=$_POST['email'];
  	$pass=$_POST['pass'];
  
 
 
 
	 $emailcheck=mysqli_query($conn,"SELECT email,pass FROM `tbl_users` WHERE email='$email' and pass='$pass'");

            if (mysqli_num_rows($emailcheck) > 0) 
			{
			    
			    
			     
$sql = "SELECT * FROM `tbl_users` WHERE email='$email' and pass='$pass'";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array(
    'id'=>$row[0],
'name'=>$row[1]

));
}
 
echo json_encode(array("result"=>$result));
		 }
        else
        {
                
            echo 'login failed!';
        }
 
mysqli_close($conn);

?>
