<?php 

$server_name='localhost';
$server_user_name='root';
$server_password='';
$database_name='wsdb';
$table_name="registration";

$my_connection_server=mysqli_connect($server_name,$server_user_name,$server_password) or die("Cannot Connect To Server");

$my_database=mysqli_select_db($my_connection_server,$database_name);

if($my_database){
	//echo "Database Selected.<br>";
}else{
	//echo "Cannot Find Database. Creating....<br>";
	$query1="CREATE DATABASE ".$database_name;
	$aa=mysqli_query($my_connection_server,$query1);
	if($aa){
		//echo "database created.<br>";
		mysqli_select_db($my_connection_server,$database_name);
	}else{
		//echo "Database Creation Failed.<br>";
	}
}

$table1="CREATE TABLE IF NOT EXISTS $table_name (fullname VARCHAR(255) NOT NULL, phone VARCHAR(15) NOT NULL, password VARCHAR(255) NOT NULL, gender VARCHAR(1) NOT NULL, PRIMARY KEY (phone))";
//$table1="CREATE TABLE IF NOT EXISTS $table_name (fullname VARCHAR(255) NOT NULL, phone VARCHAR(15) NOT NULL, password VARCHAR(255) NOT NULL, gender VARCHAR(1) NOT NULL)";
$table_query=mysqli_query($my_connection_server,$table1);

if($table_query){
	//echo "Table Created Succesfully.<br>";
}else{
	//echo "Cannot create Table.<br>";
}



?>