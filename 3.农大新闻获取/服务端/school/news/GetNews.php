<?php
	$host="localhost";
	$name="root";
	$password="Xicheng2016";
	$dbbase="school";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	$sql="select * from news";
	$res=mysql_query($sql);
	$n=0;
	while($row=mysql_fetch_array($res)){
		$arr[$n++]=array("title"=>$row['title'],
					"content_url"=>$row['content_url']
			);
	}
	//print_r($arr);
	echo json_encode($arr);

?>