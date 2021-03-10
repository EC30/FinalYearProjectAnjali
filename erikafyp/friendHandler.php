<?php
include ('database_connect.php');

$action=$_POST['action'];
$phone1=$_POST['phone1'];
$phone2=$_POST['phone2'];
$status=$_POST['status'];

//echo $action.$new_contact.$phone.$contact_to_update;

if($action=="confirm"){
	update_contact($fr_table_name,$my_connection_server,$phone1,$phone2,$status);
}else if($action=="delete"){
	delete_contact($fr_table_name,$my_connection_server,$phone1,$phone2,$status);
}else if($action=="insert"){
	add_friend($fr_table_name,$my_connection_server,$phone1,$phone2,$status);
}else{
}

function update_contact($fr_table_name,$my_connection_server,$phone1,$phone2,$status) {
	$read_query="SELECT * FROM $fr_table_name WHERE user_phone1='$phone1' OR user_phone2='$phone1'";
	$query2=mysqli_query($my_connection_server,$read_query);
	
	if(mysqli_num_rows($query2)>5){
		echo "Cannot accept request. The friend whose request you are trying to accept already has 5 friends.";
	}else{
	
		$update_query="UPDATE $fr_table_name SET status='verified' WHERE user_phone1 = '$phone1' AND user_phone2 = '$phone2' AND status='$status' ";
		$query1=mysqli_query($my_connection_server,$update_query);
		if($query1){
			echo "Friend Request Accepted.";
		}else{
			echo "Cannot Complete Request.";
		}
	}
	mysqli_close($my_connection_server);
}

function delete_contact($fr_table_name,$my_connection_server,$phone1,$phone2,$status){
	$delete_query="DELETE FROM $fr_table_name WHERE user_phone1 = '$phone1' AND user_phone2 = '$phone2' AND status='$status'";
	$query1=mysqli_query($my_connection_server,$delete_query);
	
	if($query1){
		echo "Deleted Successfully.";
	}else{
		echo "Cannot Complete Request.";
	}
	mysqli_close($my_connection_server);
}

function add_friend($fr_table_name,$my_connection_server,$phone1,$phone2,$status){
	$add_query="INSERT INTO $fr_table_name (user_phone1,user_phone2,status) VALUES ('$phone1','$phone2','$status')";
	$query1=mysqli_query($my_connection_server,$add_query);
	
	if($query1){
		echo "Added Successfully.";
	}else{
		echo "Cannot Complete Request.";
	}
	mysqli_close($my_connection_server);
}

?>