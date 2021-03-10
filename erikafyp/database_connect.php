<?php 

$server_name='localhost';
$server_user_name='root';
$server_password='';
$database_name='wsdb';
$table_name="registration";
$ec_table_name="emergencycontact";
$fr_table_name="friends";
$loc_table_name="my_location";

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
$table2="CREATE TABLE IF NOT EXISTS $ec_table_name (phone VARCHAR(20) NOT NULL, EC1 VARCHAR(20) NOT NULL,EC2 VARCHAR(20) NOT NULL,EC3 VARCHAR(20) NOT NULL, PRIMARY KEY (phone))";

$table3="CREATE TABLE IF NOT EXISTS $fr_table_name (user_phone1 VARCHAR(20) NOT NULL, user_phone2 VARCHAR(20) NOT NULL,status VARCHAR(20) NOT NULL)";

$table4="CREATE TABLE IF NOT EXISTS $loc_table_name (user_phone VARCHAR(20) NOT NULL, lat VARCHAR(20) NOT NULL,lon VARCHAR(20) NOT NULL)";


$table_query=mysqli_query($my_connection_server,$table1);
$table_query2=mysqli_query($my_connection_server,$table2);
$table_query3=mysqli_query($my_connection_server,$table3);
$table_query4=mysqli_query($my_connection_server,$table4);


if($table_query and $table_query2 and $table_query3 and $table_query4){
	//echo "Table Created Succesfully.<br>";
}else{
	//echo "Cannot create Table.<br>";
}



?>