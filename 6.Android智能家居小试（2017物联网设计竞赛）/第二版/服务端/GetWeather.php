<?php
	header("Content-type: text/html; charset=utf-8"); 
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

	// 心知天气接口调用凭据
	$key = 's1r66znusfhbenup'; // 测试用 key，请更换成您自己的 Key
	$uid = 'U2B49B4B11'; // 测试用 用户ID，请更换成您自己的用户ID
	// 参数
	$api = 'https://api.seniverse.com/v3/weather/now.json'; // 接口地址
	$location = $_GET['city']; // 城市名称。除拼音外，还可以使用 v3 id、汉语等形式
	// 生成签名。文档：https://www.seniverse.com/doc#sign
	$param = [
	    'ts' => time(),
	    'ttl' => 300,
	    'uid' => $uid,
	];
	$sig_data = http_build_query($param); // http_build_query会自动进行url编码
	// 使用 HMAC-SHA1 方式，以 API 密钥（key）对上一步生成的参数字符串（raw）进行加密，然后base64编码
	$sig = base64_encode(hash_hmac('sha1', $sig_data, $key, TRUE));
	// 拼接Url中的get参数。文档：https://www.seniverse.com/doc#daily
	$param['sig'] = $sig; // 签名
	$param['location'] = $location;
	$param['start'] = 0; // 开始日期。0=今天天气
	$param['days'] = 1; // 查询天数，1=只查一天
	// 构造url
	$url = $api . '?' . http_build_query($param);
	
	$str=$m->curl_get($url);
	//echo $str;
	$obj=json_decode($str);
	
	$text=$obj->results[0]->now->text;
	$temperature=$obj->results[0]->now->temperature;
	$code=$obj->results[0]->now->code;
	echo $text.",".$temperature.",".$code;
