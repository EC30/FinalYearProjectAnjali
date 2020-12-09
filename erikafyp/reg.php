<?php 

include ('database_connect.php');

$fullname=$_POST['fullname_post'];
$phone=$_POST['phone_post'];
$passwd=$_POST['passwd_post'];
$gender=$_POST['gender_post'];

//echo $fullname.$phone.$passwd.$gender.$table_name;

$query="INSERT INTO ".$table_name." (fullname, phone, password, gender) VALUES ('$fullname', '$phone', '$passwd', '$gender')";

//echo $query;
$query1=mysqli_query($my_connection_server,$query);

if($query1){
	echo "Inserted Successfully.";
}else{
	echo "Cannot Insert.";
}
 
mysqli_close($my_connection_server);


?>