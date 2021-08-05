<?php

@require'connection.php';

 
		$event=$_POST['sid'];
	$food=$_POST['fid'];
 
		 	  $savecheck=mysqli_query($conn,"SELECT * FROM `tbl_foodfor_event` WHERE fid='$food' and sid='$event'");

            if (mysqli_num_rows($savecheck) > 0) 
			{
            echo 'You Already Regsiter some of These Event Food!';

	 
        }
        else{

				 $insert=mysqli_query($conn,"INSERT INTO `tbl_foodfor_event`( sid,fid) VALUES ('$event','$food')") ;
        if($insert)
        {echo 'Entry Successful!';}
        else
        {echo "Entry  Error!". mysqli_error($conn);}
		 
        }


?>
