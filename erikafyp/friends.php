<?php
include ('database_connect.php');

//$action=$_POST[''];
//$new_contact=$_POST['new_contact'];
$phone=$_POST['phone_logged_in'];
//$contact_to_update=$_POST['contact_to_update'];

//echo $action.$new_contact.$phone.$contact_to_update;

//if($action=="update"){
	//update_contact($ec_table_name,$my_connection_server,$contact_to_update,$new_contact,$phone);
//}else if($action=="read"){
read_contact($fr_table_name,$my_connection_server,$phone);
//echo $phone; 
//}else{
//}

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

function read_contact($fr_table_name,$my_connection_server,$myphone){
	$read_query="SELECT * FROM $fr_table_name WHERE user_phone1='$myphone' OR user_phone2='$myphone'";
	$query1=mysqli_query($my_connection_server,$read_query);
	$final="Resultoffriends-->";
	
	if(mysqli_num_rows($query1)>0){
		while($row = mysqli_fetch_array($query1)) {
			$final.= $row['user_phone1']."::".$row['user_phone2']."::".$row['status']."::::";
		}
	}else{
		$final.="not found";
	}
	echo $final;
	mysqli_close($my_connection_server);
}

function read_contact2($fr_table_name,$my_connection_server,$myphone){
	$read_query="SELECT $fr_table_name. FROM $fr_table_name WHERE user_phone1='$myphone' OR user_phone2='$myphone'";
	$query1=mysqli_query($my_connection_server,$read_query);
	$final="Resultoffriends-->";
	
	if(mysqli_num_rows($query1)>0){
		while($row = mysqli_fetch_array($query1)) {
			$final.= $row['user_phone1']."::".$row['user_phone2']."::".$row['status']."::::";
		}
	}else{
		$final.="not found";
	}
	echo $final;
	mysqli_close($my_connection_server);
}

//query_friends=SELECT * FROM friends WHERE user_phone1="" OR user_phone2="";
?>