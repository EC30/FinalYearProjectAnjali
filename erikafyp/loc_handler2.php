<?php
include ('database_connect.php');

$phone=$_POST['phone_logged_in'];
$data=$_POST['friends_data'];
//$data="34a$$$2345$$$8798675";
//echo $data;
echo md5($phone);
mysqli_close($my_connection_server);


?>