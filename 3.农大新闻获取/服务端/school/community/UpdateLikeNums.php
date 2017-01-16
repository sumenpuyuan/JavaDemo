<?php
	echo "hahha";
	include "../conn.php";
	//$stuId='2014234060301';
	//$commuId=1;
	$stuId=$_POST['stuId'];
	$commuId=$_POST['commuId'];
	//先把给同学值为对社团一喜欢
	$sql="update stulikecommu set ifLike=1 where stuId='".$stuId."' and commuId=".$commuId."";
	//echo $sql;
	mysql_query($sql);
	//更新社团喜欢人数
	$sql="update community set likeNums=likeNums+1 where id=".$commuId."";
	mysql_query($sql);
	//echo $sql;
