<?php
	$n=$_POST['n'];
	$e=$_POST['e'];
	echo $n;
	echo " ".$e;
	$host="localhost";
	$name="??";
	$password="??";
	$dbbase="cane";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	$sql="update address set n='$n',e='$e' where id='+8615931433014'";
	$res=mysql_query($sql);
	//print_r(mysql_fetch_array($res));
	if($res){
		echo "ok";
	}
	else{
		echo "wrong";
	}

	

?>