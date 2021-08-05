




<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body style=" width:500px;margin:0 auto">
<form  method="post" action="signup.php" enctype="multipart/form-data">
  <div class="form-group">
    <label for="exampleInputEmail1">Name</label>
    <input type="text" pattern="[a-zA-Z]{1,20}" required class="form-control" name="uname" id="name" aria-describedby="emailHelp" placeholder="Enter name">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">Email address</label>
    <input type="email" required  class="form-control" name="uemail" id="email1" aria-describedby="emailHelp" placeholder="Enter email">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password</label>
    <input type="password" required pattern="[a-zA-Z0-9*#@$]{3,8}" class="form-control" name="upass" id="password" placeholder="Password">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">phone</label>
    <input type="number" pattern="[0-9]{11}" required class="form-control" name="uphone" id="name" aria-describedby="emailHelp" placeholder="Enter name">
  </div>
   <!--<div class="form-group">
    <label for="exampleInputPassword1">Profile Image</label>
 <input type="file" name="profileImage" id="profileImage"> 
  </div>-->
 
  <button type="submit" name="btnreg" class="btn btn-primary">Submit</button>
</form>

<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Name</th>
      <th scope="col">email</th>
      <th scope="col">pass</th>
	  <th scope="col">phone</th>

    </tr>
  </thead>
  <tbody>
  <?php
  include'connection.php';
$sql = "SELECT id, name, email,pass,phone FROM tbl_users";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
     
	 echo " <tr>";
     echo " <th scope='row' name='tdid'> " . $row["id"]. "</th>";
     echo " <td>" . $row["name"]. "</td>";
     echo " <td>" . $row["email"]. "</td>";
     echo " <td>" . $row["pass"]. "</td>";
	  echo " <td>" . $row["phone"]. "</td>";
	// echo " <td>  <img src='profile_images/".$row['image']."' height='70px' width='70px'> </td>";

	 
  }
}
   
    ?>
  </tbody>
</table>


</body>
</html>
