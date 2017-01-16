<?php
/*
	class DbBase{
		$host="localhost";
		$name="root";
		$password="Xicheng2016";
		$dbbase="school";
		private static DbBase instance;
		public $db_selected;
		private DbBase(){
			$conn=mysql_connect($host,$name,$password);
			$db_selected=mysql_select_db($dbbase,$conn);
		}
		public static DbBase getInstance(){
			if(instance == null){
				instance=new DbBase();
			}
			return instance;
		}
		
	}*/
	$host="localhost";
		$name="root";
		$password="";
		$dbbase="school";
		$conn=mysql_connect($host,$name,$password);
			$db_selected=mysql_select_db($dbbase,$conn);
	