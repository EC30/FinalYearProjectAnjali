<?php 

include ('database_connect.php');

$phone=$_POST['phone_to_check'];

//$passwd=$_POST['passwd_login'];

//echo $fullname.$phone.$passwd.$gender.$table_name;

//$query="INSERT INTO ".$table_name." (fullname, phone, password, gender) VALUES ('$fullname', '$phone', '$passwd', '$gender')";
$query="SELECT * FROM $table_name WHERE phone = '$phone'";

//echo $query;
$query1=mysqli_query($my_connection_server,$query);
//$row = mysqli_fetch_array($query1,MYSQLI_ASSOC);
//$count = mysqli_num_rows($query1);

if(mysqli_num_rows($query1)>0){
	while($row = mysqli_fetch_array($query1)) {
		echo "User Exists.::".$row['fullname'];
	}
//if($count==1){
	//echo "User Exists.";
}else{
	echo "User Not Exists.";
}
 
mysqli_close($my_connection_server);


?>