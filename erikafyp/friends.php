<?php
include ('database_connect.php');

$phone=$_POST['phone_logged_in'];
//read_contact($fr_table_name,$my_connection_server,$phone);
read_contact2($fr_table_name,$my_connection_server,$phone,$table_name);

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

function read_contact2($fr_table_name,$my_connection_server,$myphone,$table_name){
	$read_query="SELECT * FROM $fr_table_name INNER JOIN $table_name ON $fr_table_name.user_phone2=$table_name.phone WHERE user_phone1=$myphone";
	$query1=mysqli_query($my_connection_server,$read_query);
	$final="Resultoffriends-->";
	
	//{		$final.="not found";	}
	
	$read_query1="SELECT * FROM $fr_table_name INNER JOIN $table_name ON $fr_table_name.user_phone1=$table_name.phone WHERE user_phone2=$myphone";
	$query2=mysqli_query($my_connection_server,$read_query1);
	$a=false;
	
	if(mysqli_num_rows($query1)>0){
		while($row = mysqli_fetch_array($query1)) {
			$final.= $row['user_phone1']."::".$row['user_phone2']."::".$row['status']."::".$row['fullname']."::::";
		}
		$a=true;
	}
	
	if(mysqli_num_rows($query2)>0){
		while($row = mysqli_fetch_array($query2)) {
			$final.= $row['user_phone1']."::".$row['user_phone2']."::".$row['status']."::".$row['fullname']."::::";
		}
		$a=true;
	}
	
	if($a==false){
		$final.="not found";
	}
	
	
	//$query1=mysqli_query($my_connection_server,$read_query);
	echo $final;
	mysqli_close($my_connection_server);
}

//query_friends=SELECT * FROM friends WHERE user_phone1="" OR user_phone2="";
?>