<?php
include ('database_connect.php');

$phone=$_POST['phone_logged_in'];
$data=$_POST['friends_data'];
//$data="34a$$$2345$$$8798675";
//echo $data;
$all_friends=explode("@@@",$data);

$read_query="SELECT * FROM $loc_table_name WHERE ";

for($i=0;$i<count($all_friends);$i++){
	if($i<count($all_friends)-1){
		$read_query.="user_phone='$all_friends[$i]' OR ";
	}
	if($i==count($all_friends)-1){
		$read_query.="user_phone='$all_friends[$i]'";
	}
}

$query1=mysqli_query($my_connection_server,$read_query);
$final="Resultoffriendsloc-->";

if(mysqli_num_rows($query1)>0){
	while($row = mysqli_fetch_array($query1)) {
		$final.= $row['user_phone']."::".$row['lat']."::".$row['lon']."::::";
	}
}else{
	$final.="not found";
}
echo $final;
mysqli_close($my_connection_server);


?>