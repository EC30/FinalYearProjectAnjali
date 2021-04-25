<?php
include ('database_connect.php');

$action=$_POST['action'];
$phone_logged=$_POST['phone_logged_in'];
$ecphone=$_POST['ecphone'];
$new_ecphone=$_POST['new_ecphone'];

//echo $action.$new_contact.$phone.$contact_to_update;

if($action=="update"){
	update_contact($ec_table_name,$my_connection_server,$phone_logged,$ecphone,$new_ecphone);
}else if($action=="delete"){
	delete_contact($ec_table_name,$my_connection_server,$phone_logged,$ecphone);
}else if($action=="insert"){
	add_contact($ec_table_name,$my_connection_server,$phone_logged,$new_ecphone);
}else if($action=="read"){
	read_contact($ec_table_name,$my_connection_server,$phone_logged);
}else{
}

function update_contact($ec_table_name,$my_connection_server,$phone_logged,$ecphone,$new_ecphone) {
		
		$update_query="UPDATE $ec_table_name SET EContactsPhone='$new_ecphone' WHERE Phone_logged_user = '$phone_logged' AND EContactsPhone = '$ecphone'";
		$query1=mysqli_query($my_connection_server,$update_query);
		if($query1){
			echo "Updated EC Successfully.";
		}else{
			echo "Cannot Complete Request.";
		}
	
	mysqli_close($my_connection_server);
}

function delete_contact($ec_table_name,$my_connection_server,$phone_logged,$ecphone){
	$delete_query="DELETE FROM $ec_table_name WHERE Phone_logged_user = '$phone_logged' AND EContactsPhone = '$ecphone'";
	$query1=mysqli_query($my_connection_server,$delete_query);
	
	if($query1){
		echo "Deleted Successfully.";
	}else{
		echo "Cannot Complete Request.";
	}
	mysqli_close($my_connection_server);
}

function add_contact($ec_table_name,$my_connection_server,$phone_logged,$new_ecphone){
	$add_query="INSERT INTO $ec_table_name (Phone_logged_user,EContactsPhone) VALUES ('$phone_logged','$new_ecphone')";
	$query1=mysqli_query($my_connection_server,$add_query);
	
	if($query1){
		echo "Added Successfully.";
	}else{
		echo "Cannot Complete Request.";
	}
	mysqli_close($my_connection_server);
}

function read_contact($ec_table_name,$my_connection_server,$phone_logged){
	$read_query="SELECT * FROM $ec_table_name WHERE Phone_logged_user='$phone_logged'"; 
	$query1=mysqli_query($my_connection_server,$read_query);
	$to_go="read_value_wsaa_ec::";
	while($row = mysqli_fetch_array($query1)) {
		//loop through each emergency contact
		// response format
		$to_go.=$row['EContactsPhone']."::";
	}
	echo substr($to_go,0,strlen($to_go)-2);
	//echo $to_go;
	mysqli_close($my_connection_server);
}

?>