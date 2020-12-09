<?php 

include ('database_connect.php');

$phone=$_POST['phone_logged2'];
$passwd=$_POST['password_new'];

echo $phone."---".$passwd;

$query="UPDATE $table_name SET password='$passwd' WHERE phone = '$phone'";

//echo $query;
$query1=mysqli_query($my_connection_server,$query);
//$row = mysqli_fetch_array($query1,MYSQLI_ASSOC);
//$count = mysqli_num_rows($query1);


if($query1){
	echo "Password Updated Successfully.";
}else{
	echo "Cannot update password.";
}
 
mysqli_close($my_connection_server);


?>