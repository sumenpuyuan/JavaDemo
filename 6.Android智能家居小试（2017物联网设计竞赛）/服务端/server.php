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
	$port = 10005;
	$host="localhost";
	$name="root";
	$password="";
	$dbbase="";
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
			$f=0;
			$res="";
			while(true)
			{
				$data=socket_read($msgsock, 1024000);  //接收的总数据
				$data=bin2hex($data);
				//echo $data;
			//	echo "\n\n\n";
				
				//开始吧
				$flag=substr($data, 0,4);
				if($flag == "ffd8" || $f == -1){
					if(strpos($data,"ffd9") == null){
						$res.=$data;
						$f=-1;
					}
					else{
						$temp=substr($data, 0,strpos($data, "ffd9")+4);
						$res.=$temp;
						echo "get".$res."\n";
						$sql="update test set image='".$res."' where id=2";
						$res=mysql_query($sql);
						//print_r(mysql_fetch_array($res));
						if($res){
							echo "ok";
						}
						else{
							echo "wrong";
						}
						echo "\n";
						$res="";
						$f=0;
					}
				}
				else if($flag == "0d0a" && $f==0){
					echo $data."\n";
					$sql="update test set gas='".hex2bin($data)."' where id=2";
						$res=mysql_query($sql);
						//print_r(mysql_fetch_array($res));
						if($res){
							echo "ok";
						}
						else{
							echo "wrong";
						}
						echo "\n";
						$res="";
				}else{
					echo "wrong\n";
				}
				

			}
										
		}		
	}
	mysql_close($conn);
	$FLAG=0;			
	socket_close($sock);
?>