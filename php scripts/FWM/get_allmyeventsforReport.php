<?php

@require'connection.php';

   
        if($_SERVER['REQUEST_METHOD'] =='POST')
            {
    
     $uid=$_POST['uid'];
			$result = array();
			$result['data'] = array();
			$select= "SELECT id,event_name,image,datetime FROM `tbl_event` where user_id='$uid' and datetime >= CURRENT_TIMESTAMP order by datetime asc";
			$responce = mysqli_query($conn,$select);
	
            
				while($row = mysqli_fetch_array($responce))
			{
		
		    $index['id']      = $row['0'];
		    $index['image']   = $row['2'];
				 $index['event_name']   = $row['1'];
			array_push($result['data'], $index);
			
				}
			
			$result["success"]="1";
		    echo json_encode($result);
			mysqli_close($conn);
	


            }	
   

?>
