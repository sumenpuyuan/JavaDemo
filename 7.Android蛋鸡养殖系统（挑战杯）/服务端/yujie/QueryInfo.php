<?php
	require_once "conn.php";
	$id=$_POST['id'];
	$startTime='2017-4-4';
	$endTime='2017-4-5';


	$tian=abs((strtotime($endTime)-strtotime($startTime))/86400);
	
	$n=0;
	
		
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
	
	
	//var_dump($arr);
	echo json_encode($arr);
	//echo abs((strtotime($endTime)-strtotime($startTime))/86400);


