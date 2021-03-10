<?php 

include ('database_connect.php');

$fullname=$_POST['fullname_post'];
$phone=$_POST['phone_post'];
$passwd=$_POST['passwd_post'];
$gender=$_POST['gender_post'];

//echo $fullname.$phone.$passwd.$gender.$table_name;

$query="INSERT INTO ".$table_name." (fullname, phone, password, gender) VALUES ('$fullname', '$phone', '$passwd', '$gender')";
$query2="INSERT INTO ".$ec_table_name." (phone,EC1,EC2,EC3) VALUES ('$phone','NULL-VAL','NULL-VAL','NULL-VAL')";
$query4="INSERT INTO ".$loc_table_name." (user_phone,lat,lon) VALUES ('$phone','unknown','unknown')";

//echo $query;
$query1=mysqli_query($my_connection_server,$query);
$query3=mysqli_query($my_connection_server,$query2);
$query5=mysqli_query($my_connection_server,$query4);

if($query1 and  $query3 and $query5){
	echo "Inserted Successfully.";
}else{
	echo "Cannot Insert.";
}
 
mysqli_close($my_connection_server);

?>