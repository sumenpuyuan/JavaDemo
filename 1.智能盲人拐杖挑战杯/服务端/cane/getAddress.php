<?php
	$host="localhost";
	$name="??";
	$password="??";
	$dbbase="cane";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	$sql="select * from address where id='18731232826'";
	$res=mysql_query($sql);
	//var_dump($res);
	//echo "<br/>";
	while($row=mysql_fetch_row($res)){
		echo $row[0].",".$row[1];
		
	}
	

	
	

?>