<?php

@require'connection.php';

   
        if($_SERVER['REQUEST_METHOD'] =='POST')
            {
    
     $uid=$_POST['uid'];
			$result = array();
			$result['data'] = array();
			$select= "SELECT DISTINCT e.id,e.image,e.event_name,e.datetime FROM tbl_event e ,tbl_food_pref r WHERE e.id=r.sid and r.uid='$uid' and e.datetime > CURRENT_TIMESTAMP order by e.datetime asc";
			$responce = mysqli_query($conn,$select);
	
            
				while($row = mysqli_fetch_array($responce))
			{
		
		    $index['id']      = $row['0'];
		    $index['image']   = $row['1'];
				 $index['event_name']   = $row['2'];
			array_push($result['data'], $index);
			
				}
			
			$result["success"]="1";
		    echo json_encode($result);
			mysqli_close($conn);
	


            }	
   

?>
