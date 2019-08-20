<?php
	require_once "conn.php";
	$email=$_POST['email'];
	$password=$_POST['password'];
	
	
	
	$sql="select password from user where email='".$email."'";
	$res=mysql_query($sql);
	while($row=mysql_fetch_array($res)){
		$realPasswd=$row[0];
	}
	//echo $realPasswd;
	if($realPasswd == $password){
		echo "ok";
	}
	else{
		echo "wrong";
	}

	
?>