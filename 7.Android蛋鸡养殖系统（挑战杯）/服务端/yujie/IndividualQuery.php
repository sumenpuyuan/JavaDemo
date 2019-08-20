<?php
	require_once "conn.php";
	
	$startTime=$_POST['startTime'];
	$endTime=$_POST['endTime'];
	$id=$_POST['id'];

	$sql="select * from chicken where riqi>='".$startTime."' and riqi <='".$endTime."' and id=".$id."";
	$res=mysql_query($sql);
	$n=0;
	while($row=mysql_fetch_array($res)){
		$arr[$n++]=array(
				"riqi"=>$row["riqi"],"id"=>$row['id'],
				"haoke"=>$row['haoke'],"danNum"=>$row['danNum'],
				"danTime"=>$row['danTime'],"danZhong"=>$row['danZhong'],
				"bi"=>$row['bi']
			);
	}
	//print_r($arr);
	echo json_encode($arr);


