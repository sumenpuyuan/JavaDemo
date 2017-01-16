<?php
	$id=$_POST['id'];
	$passwd=$_POST['password'];
	$host="localhost";
	$name="root";
	$password="Xicheng2016";
	$dbbase="school";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	$sql="select password from student where id='".$id."'";
	$res=mysql_query($sql);
	while($row=mysql_fetch_array($res)){
		$realPasswd=$row[0];
	}
	//echo $realPasswd;
	if($realPasswd == $passwd){
		echo "ok";
	}
	else{
		echo "wrong";
	}

	
?>