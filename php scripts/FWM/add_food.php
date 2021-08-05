<?php

@require'connection.php';

 
		$name=$_POST['name'];
	$type=$_POST['type'];
 
		 	 $foodcheck=mysqli_query($conn,"select name from tbl_food where name ='$name'");

            if (mysqli_num_rows($foodcheck) > 0) 
			{
            echo 'Food Already Exists !';

		 
        }
        else{

				 $insert=mysqli_query($conn,"INSERT INTO `tbl_food`( name,type) VALUES ('$name','$type')") ;
        if($insert)
        {echo 'Entry Successful!';}
        else
        {echo "Entry  Error!". mysqli_error($conn);}
		 
        } 


?>
