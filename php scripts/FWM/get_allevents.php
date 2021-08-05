<?php

@require'connection.php';

   
        if($_SERVER['REQUEST_METHOD'] =='POST')
            {
    
     
			$result = array();
			$result['data'] = array();
			$select= "SELECT id,image,event_name from tbl_event where datetime > CURRENT_TIMESTAMP order by datetime asc";
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
