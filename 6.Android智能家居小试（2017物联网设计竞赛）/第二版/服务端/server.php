<?php

	set_time_limit(0);
	$ip = '0.0.0.0';
	$port = 10004;
	$host="localhost";
	$name="root";
	$password="";
	$dbbase="lot";
	$conn=mysql_connect($host,$name,$password);
	$db_selected=mysql_select_db($dbbase,$conn);
	$FLAG=0;
	if(($sock = socket_create(AF_INET,SOCK_STREAM,SOL_TCP)) < 0) {
	    echo "socket_create() failed: reason:".socket_strerror($sock)."\n";
	}
	//$socket->setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1);
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
				echo "-------".$data."\n";
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
						echo "++++++++++get".$res."\n";
						$sql="update test set image='".$res."' where id=2";
						$res=mysql_query($sql,$conn) or die(mysql_error());
						//print_r(mysql_fetch_array($res));
						if($res){
							echo "image ok";
						}
						else{
							echo "image wrong";
						}
						echo "\n";
						$res="";
						$f=0;
					}
				}
				else if($flag == "0d0a" && $f==0){

					//echo $data."\n";
					$gas=hex2bin($data);

					echo $gas;
					$realData=explode("\n", $gas);
					//echo $realData[8];
				    if(strpos($realData[8], "B") == false){
				    	$sql="update test set gas='".$gas."' where id=2";
				    	$res=mysql_query($sql,$conn) or die(mysql_error());
				    	if($res){
								echo "only 1 ok";
							}
							else{
								echo "only 1 wrong";
							}
							echo "\n";
							$res="";
				    }
				    else{
				    	//还有可能不对
				    	$hang=substr($realData[8], 0,strpos($realData[8], "B")+3);
				    	$hang=substr($hang, strrpos($hang, "T"));
				    	echo "hhhhhang is".$hang."\n";
				    	if(strlen($hang) != 17){
				    		echo "the length is".strlen($hang)."\n";
				    	}else{
				    		echo "the length is".strlen($hang)."\n";
				    		echo "i am coming !!!!\n";
				    		$mine=substr($realData[8], 0,strpos($realData[8], "B")+3);
					        $id=$mine[strrpos($mine,"T")-2];
					        $mine=substr($mine, strrpos($mine, "T"));
					        echo $id;
						    $test="123";
						    $mine=str_replace("*", "\n", $mine);
						     echo $mine;
						    if($id == "2"){
						        $sql="update test set gas='".$gas."' , homeTwo='".$mine."' where id=2";
						        echo $id+"2";
						        echo $sql;
						    }
						    else if($id == "3"){
						        $sql="update test set gas='".$gas."' , homeThree='".$mine."' where id=2";
						        echo $sql;
						       
						    }
	    					$res=mysql_query($sql,$conn) or die(mysql_error());
							//print_r(mysql_fetch_array($res));
							if($res){
								echo "home 2 3 ok";
							}
							else{
								echo "home 2 3 wrong";
							}
							echo "\n";
							$res="";
					    }
				    }
				    
				   
				    
				}
				else{
					echo "wrong\n";
					$res="";
				}
				

			}
										
		}		
	}
	mysql_close($conn);
	$FLAG=0;			
	socket_close($sock);
?>