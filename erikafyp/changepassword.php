<?php 

include ('database_connect.php');

$phone=$_POST['phone_login'];
$old_password=md5($_POST['passwd_old']);
$passwd=md5($_POST['passwd_new']);

$query="SELECT * FROM $table_name WHERE phone = '$phone' and password = '$old_password'";

$query2="UPDATE $table_name SET password='$passwd' WHERE phone = '$phone'";

//echo $query2;
$query1=mysqli_query($my_connection_server,$query);

$row = mysqli_fetch_array($query1,MYSQLI_ASSOC);
$count = mysqli_num_rows($query1);


if($count==1){
	$query3=mysqli_query($my_connection_server,$query2);
	
	if($query3){
		echo "Password Updated Successfully.";
	}else{
		echo "Cannot change password.";
	}
}else{
	echo "Old Password Incorrect.";
}
//echo $query;
//$query1=mysqli_query($my_connection_server,$query);
//$row = mysqli_fetch_array($query1,MYSQLI_ASSOC);
//$count = mysqli_num_rows($query1);




 
mysqli_close($my_connection_server);


?>