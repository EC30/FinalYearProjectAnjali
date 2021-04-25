<?php 

include ('database_connect.php');

$phone=$_POST['phone_login'];
$passwd=md5($_POST['passwd_login']);

//echo $fullname.$phone.$passwd.$gender.$table_name;

//$query="INSERT INTO ".$table_name." (fullname, phone, password, gender) VALUES ('$fullname', '$phone', '$passwd', '$gender')";
$query="SELECT * FROM $table_name WHERE phone = '$phone' and password = '$passwd'";

//echo $query;
$query1=mysqli_query($my_connection_server,$query);
$row = mysqli_fetch_array($query1,MYSQLI_ASSOC);
$count = mysqli_num_rows($query1);


if($count==1){
	//while($row = mysqli_fetch_array($query1)){
		echo "Logged in Successfully.@@@".$row['fullname'];
}else{
	echo "Cannot Login. Username or password incorrect";
}
 
mysqli_close($my_connection_server);


?>