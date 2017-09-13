<?php
	require_once "conn.php";
	$sql="select * from test";
	$res=mysql_query($sql);
	$n=0;
	while($row=mysql_fetch_array($res)){
		$str=$row[3];
	}
	//print_r($arr);
	//echo json_encode($arr);
	echo $str;


