<?php

@require'connection.php';

 
		$lt=$_POST['lt'];
	$lg=$_POST['lg'];
		$eid=$_POST['eid'];
 
 
 

				 $insert=mysqli_query($conn,"UPDATE `tbl_event` SET `latitude`='$lt',`longitude`='$lg' WHERE id='$eid'") ;
        if($insert)
        {
           
            echo ' Event Location Updated!';
            }
        else
        {echo "Updation  Error!". mysqli_error($conn);}
		 
        


?>
