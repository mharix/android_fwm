<?php

@require'connection.php';

 
		$name=$_POST['name'];
	$address=$_POST['address'];
		$dress=$_POST['dress'];
	$date=$_POST['date'];
		$uid=$_POST['user_id'];
		 
	  $img=$_POST['imageupload'];

                   $filename="IMG".rand().".jpg";
	   file_put_contents("event_images/".$filename,base64_decode($img));


 
 

				 $insert=mysqli_query($conn,"INSERT INTO `tbl_event`( event_name,datetime,address,image,dress,user_id) VALUES ('$name','$date','$address','$filename','$dress','$uid')") ;
        if($insert)
        {
            $id = mysqli_insert_id($conn);
            echo $id;
            }
        else
        {echo "Registration  Error!". mysqli_error($conn);}
		 
        


?>
