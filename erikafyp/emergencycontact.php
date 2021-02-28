<?php
include ('database_connect.php');

$action=$_POST['action'];
$new_contact=$_POST['new_contact'];
$phone=$_POST['phone_logged_in'];
$contact_to_update=$_POST['contact_to_update'];

//echo $action.$new_contact.$phone.$contact_to_update;

if($action=="update"){
	update_contact($ec_table_name,$my_connection_server,$contact_to_update,$new_contact,$phone);
}else if($action=="read"){
	read_contact($ec_table_name,$my_connection_server,$phone);
}else{
}

function update_contact($ec_table_name,$my_connection_server,$contact_to_update1,$mycontact,$myphone) {
	$update_query="UPDATE $ec_table_name SET $contact_to_update1='$mycontact' WHERE phone = '$myphone'";
	$query1=mysqli_query($my_connection_server,$update_query);
	if($query1){
		echo "Updated EC Successfully.";
	}else{
		echo "Cannot Update EC";
	}
	mysqli_close($my_connection_server);
}

function read_contact($ec_table_name,$my_connection_server,$myphone){
	$read_query="SELECT * FROM $ec_table_name WHERE phone='$myphone'";
	$query1=mysqli_query($my_connection_server,$read_query);
	
	while($row = mysqli_fetch_array($query1)) {
		echo "read_value_wsaa_ec::".$row['EC1']."::".$row['EC2']."::".$row['EC3'];
	}
	mysqli_close($my_connection_server);
}


?>