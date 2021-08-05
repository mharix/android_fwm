<?php

@require'connection.php';

 
		$sid=$_POST['sid'];
	$uid=$_POST['uid'];
 
	 


	 $regcheck=mysqli_query($conn,"SELECT * FROM `tbl_food_pref` WHERE uid='$uid' and sid='$sid'");

            if (mysqli_num_rows($regcheck) > 0) 
			{
            echo 'Reg Already Exists!';

	 
        }
    
        


?>
