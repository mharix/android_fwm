<?php

@require'connection.php';

 	$user=$_POST['uid'];
		$event=$_POST['sid'];
	$food=$_POST['fid'];
 
		 	 
		 	 
	 $regcheck=mysqli_query($conn,"SELECT * FROM `tbl_food_pref` WHERE uid='$user' and sid='$event' and fid='$food'");

            if (mysqli_num_rows($regcheck) > 0) 
			{
            echo 'You Already Regsiter For This Event Food!';

	 
        }
        else{

		$insert=mysqli_query($conn,"INSERT INTO `tbl_food_pref`(`sid`, `fid`, `uid`) VALUES ('$event','$food','$user')");
        if($insert)
        {echo 'Entry Successful!';}
        else
        {echo "Entry  Error!". mysqli_error($conn);}
		 
        

}
?>
