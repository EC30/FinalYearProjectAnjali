<?php
include ('database_connect.php');
$action=$_POST['action'];	   
$img=$_POST['uploaded_image'];
$phone_logged=$_POST['phone_logged'];

$filename=substr($phone_logged,1).".jpg";

file_put_contents("upload/".$filename,base64_decode($img));

if(!file_exists('upload/'.$filename)){
	$qry="INSERT INTO $image_table_name (userphone,imagename) VALUES ('$phone_logged','$filename')";

	$res=mysqli_query($my_connection_server,$qry);

	if($res==true)
		echo "File Uploaded Successfully";
	else
		echo "Could not upload File";
}else{
	echo "File Uploaded Successfully";
}

mysqli_close($my_connection_server);
?>
