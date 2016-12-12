<?php
	class Action
	{
		public function curl_get($url){

			   $testurl = $url;
			   $ch = curl_init();  
			   curl_setopt($ch, CURLOPT_URL, $testurl);  
				//参数为1表示传输数据，为0表示直接输出显示。
			   curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
				//参数为0表示不带头文件，为1表示带头文件
			   curl_setopt($ch, CURLOPT_HEADER,0);
			   $output = curl_exec($ch); 
			   curl_close($ch); 
			   return $output;
		 }
		/*
		 * url:访问路径
		 * array:要传递的数组
		 * */
		public function curl_post($url,$array){

			$curl = curl_init();
			//设置提交的url
			curl_setopt($curl, CURLOPT_URL, $url);
			//设置头文件的信息作为数据流输出
			curl_setopt($curl, CURLOPT_HEADER, 0);
			//设置获取的信息以文件流的形式返回，而不是直接输出。
			curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
			//设置post方式提交
			curl_setopt($curl, CURLOPT_POST, 1);
			//设置post数据
			$post_data = $array;
			curl_setopt($curl, CURLOPT_POSTFIELDS, $post_data);
			//执行命令
			$data = curl_exec($curl);
			//关闭URL请求
			curl_close($curl);
		  //获得数据并返回
			return $data;
		}
	}
	$m=new Action();
	set_time_limit(0);
	$ip = '0.0.0.0';
	$port = 1910;
	$host="localhost";
	$name="??";
	$password="??";
	$dbbase="cane";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	$FLAG=0;
	if(($sock = socket_create(AF_INET,SOCK_STREAM,SOL_TCP)) < 0) {
	    echo "socket_create() failed: reason:".socket_strerror($sock)."\n";
	}
	echo "create success\n";
	if(($ret = socket_bind($sock,$ip,$port)) < 0) {
	    echo "socket_connect() failed./nReason:".socket_strerror($ret)."\n";
	}
	if(($ret = socket_listen($sock,4)) < 0) {
	    echo "socket_listen()failed: reason:".socket_strerror($ret)."\n";
	}
	while(true)
	{
		if(($msgsock = socket_accept($sock)) < 0)
		{		
			echo "socket_accept() failed: reason: " . socket_strerror($msgsock) . "\n";
			socket_close($sock);
			break;
		}
		else
		{
			echo "客户端连接成功\n";
			while(true)
			{

				$data=socket_read($msgsock, 1024);  //接收的总数据
				echo $data;
				$address=explode(",", $data);
				$n=$address[0];
				$e=$address[2];
				echo $n."   ".$e." ";
				$str="http://restapi.amap.com/v3/assistant/coordinate/convert?locations=".$e.",".$n."&coordsys=gps&key=8c0e000ebf297bc1b54931a434556fe9";
				$response=$m->curl_get($str);
				$address=explode(":",$response);
				$address=explode(",",$address[4]);
				//echo $address[0];
				$e=mb_substr($address[0],1,11);
				$n=mb_substr($address[1],0,7);
				echo $n."  ".$e."   ";
				$sql="update address set n='$n',e='$e' where id='18731232826'";
				$res=mysql_query($sql);
				//print_r(mysql_fetch_array($res));
				if($res){
					echo "ok";
				}
				else{
					echo "wrong";
				}
				echo "\n";

			}
										
		}		
	}
	mysql_close($conn);
	$FLAG=0;			
	socket_close($sock);
?>
