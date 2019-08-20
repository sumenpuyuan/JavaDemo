<?php
	$host="localhost";
	$name="root";
	$password="";
	$dbbase="yujie";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	