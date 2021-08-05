<?php

@require'connection.php';

 
		$name=$_POST['uname'];
	$email=$_POST['uemail'];
	$pass=$_POST['upass'];
		$phone=$_POST['uphone'];
	 


	 $emailcheck=mysqli_query($conn,"select email from tbl_users where email ='$email'");

            if (mysqli_num_rows($emailcheck) > 0) 
			{
            echo 'Email Already Exists !';

		//	echo '<script type="text/javascript">';
		//	echo ' alert("Email Already Exists !")';
		//	echo '</script>';
        }
        else
        {
//here we work on image file rename and storage to folder
 
 
  //$time = date("d-m-Y")."-".time() ;
                //  $targetDir = "profile_images/";
                //  $img = basename($_FILES["profileImage"]["name"]);
                //  $file_name = $time."-".$img;
               //   $targetFilePath = $targetDir . $file_name;
              //  move_uploaded_file($_FILES["profileImage"]["tmp_name"], $targetFilePath);

				 $insert=mysqli_query($conn,"INSERT INTO `tbl_users`( name, pass, email,phone) VALUES ('$name','$pass','$email','$phone')");
        if($insert)
        {echo 'Signup Successful!';}
        else
        {echo "Registration  Error!".  $mysqli -> error;}
		 
        

}
?>
