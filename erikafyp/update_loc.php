<?php
include ('database_connect.php');

$phone=$_POST['phone_logged_in'];
$lat=$_POST['latitude'];
$lon=$_POST['longitude'];

$update_query="UPDATE $loc_table_name SET lat='$lat',lon='$lon' WHERE user_phone='$phone'";

$query1=mysqli_query($my_connection_server,$update_query);

mysqli_close($my_connection_server);


?>