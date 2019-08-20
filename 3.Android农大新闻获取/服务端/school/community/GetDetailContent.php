<?php
	include "../conn.php";
	//客户端应该是发的是社团的id，我返回社团介绍
	//和该同学是否已经投过票了
	$stuId=$_POST['stuId'];
	$commuId=$_POST['commuId'];
	//$stuId='2014234060301';
	//$commuId=0;
	//先拿到社团介绍
	$sql="select * from community where id=".$commuId."";
	//echo $sql;
	$res=mysql_query($sql);
	$n=0;
	while($row=mysql_fetch_array($res)){
		$arr[$n]=array(
				"name"=>$row["name"],
				"description"=>$row['description'],
				"likeNums"=>$row['likeNums']
			);
	}
	//看看改同学是否喜欢改社团
	$sql="select ifLike from stulikecommu where stuId='".$stuId."'and commuId=".$commuId."";
	//echo $sql;
	$res=mysql_query($sql);
	while($row=mysql_fetch_array($res)){
		$arr[$n]['ifLike']=$row['ifLike'];
	}
	//print_r($arr);
	echo json_encode($arr);



