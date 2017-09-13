<?php
	$host="localhost";
	$name="root";
	$password="";
	$dbbase="lot";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	