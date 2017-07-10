<?php
	require_once "conn.php";
	$startId=$_POST['startId'];
	$endId=$_POST['endId'];
	$startTime=$_POST['startTime'];
	$endTime=$_POST['endTime'];


	
	//$startTime='2017-4-4';
	//$endTime='2017-4-5';


	$tian=abs((strtotime($endTime)-strtotime($startTime))/86400);
	$id=1;
	$n=0;
	for($ii=$startId;$ii<=$endId;$ii++){
		$id=$ii;
		$sql="select id,sum(haoke) as sumHao,sum(haoke)/".$tian." as avgHao,sum(danNum) as sumDanNum,sum(danZhong) as sumDanZhong,max(danZhong) as maxDanZhong,min(danZhong) as minDanZhong,sum(danZhong)/sum(danNum) as avgDan from chicken where riqi>='".$startTime."' and riqi<='".$endTime."' and id=".$id."";
		//echo $sql;
		$res=mysql_query($sql) or mysql_error();
		
		while($row=mysql_fetch_array($res)){	
			$arr[$n++]=array(
					"id"=>$row["id"],"sumHao"=>$row['sumHao'],
					"avgHao"=>$row['avgHao'],"sumDanNum"=>$row['sumDanNum'],
					"sumDanZhong"=>$row['sumDanZhong'],"maxDanZhong"=>$row['maxDanZhong'],
					"minDanZhong"=>$row['minDanZhong'],"avgDan"=>$row['avgDan']
				);
		}
	}
	
	//var_dump($arr);
	echo json_encode($arr);
	//echo abs((strtotime($endTime)-strtotime($startTime))/86400);


