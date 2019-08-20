<?php
	include_once "../conn.php";
	//$id='123';
	$id=$_POST['id'];
	$sql="select * from student where id ='".$id."'";
	//echo $sql;
	$res=mysql_query($sql);
	$n=0;	
	while($row=mysql_fetch_array($res)){
		$arr = array('id' => $row['id'],'name'=>$row['name'],
				'college'=>$row['college'],'major'=>$row['major'],
				'class'=>$row['class'],'head'=>$row['head']
		 );
	}
	echo json_encode($arr);