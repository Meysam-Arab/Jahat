<?php
require_once 'include/Config.php';
//error_reporting(0);
// json response array
$con=mysql_connect(DB_HOST,DB_USER,DB_PASSWORD);
mysql_query("SET title 'utf8'", $con);

require_once 'getToken.php';

///edit channel
if (isset($_POST['edit']) && isset($_POST['unique_id']) && isset($_POST['jwTw'])) {
	
	$id=$_POST['id'];
	$uud=$_POST['unique_id'];
	$name=$_POST['name'];
	$description=$_POST['description'];
	$link=$_POST['link'];
	$type=$_POST['type'];
	
	if($type==1){
		$nameFetchApi="https://api.telegram.org/bot".getToken()."/getChat?chat_id=@".$link;
		$response2=json_decode(file_get_contents($nameFetchApi),true);
		if(is_null($response2) || !$response2['ok']){ 
				$response["error"] = TRUE;
				$response["error_msg"] = "لینک معتبر نیست!!!";
				echo json_encode($response);
				return;
		}else{
			$name=$response2['result']['title'];
		}
	}
	
	
	if(!isChannelExist2($link,$id)){
	$out = mysql_query("UPDATE ".DB_DATABASE.".`channels` 
						SET ".DB_DATABASE.".`channels`.`name`='$name', 
							".DB_DATABASE.".`channels`.`link`='$link', 
							".DB_DATABASE.".`channels`.`description`='$description', 
							".DB_DATABASE.".`channels`.`type`='$type', 
							".DB_DATABASE.".`channels`.`isAuthenticated`=0 
						WHERE ".DB_DATABASE.".`channels`.`unique_id`='$uud' AND ".DB_DATABASE.".`channels`.`id`='$id';");
	if ($out != false) {
		$response["error"] = FALSE;
		$response["mess"] = "عملیات موفقیت آمیز بود.";
		if(isset($_POST['image'])) saveAvatar($uud,$_POST['image']);
		echo json_encode($response);
		}else {
			// cant insert
			$response["error"] = TRUE;
			$response["error_msg"] = "مشکلی در هنگام ویرایش پیش آمد!";
			echo json_encode($response);
	}
	}else{
		// cant insert
		$response["error"] = TRUE;
		$response["error_msg"] = "این لینک مطعلق به کس دیگری است!!!";
		echo json_encode($response);
	}
}
function isChannelExist2($link,$id){
	$out = mysql_query("
	SELECT * 
	FROM ".DB_DATABASE.".channels 
	WHERE ".DB_DATABASE.".channels.link = '$link' AND ".DB_DATABASE.".channels.id != '$id' 
	LIMIT 1
	;");
	return mysql_num_rows($out)==0?false:true;
}
function isChannelExist($link){
	$out = selectChannelByLink($link);
	return mysql_num_rows($out)==0?false:true;
}
function selectChannelByLink($link){
	return mysql_query("
	SELECT * 
	FROM ".DB_DATABASE.".channels 
	WHERE ".DB_DATABASE.".channels.link = '$link' 
	LIMIT 1
	;");
}
function saveAvatar($guid,$image){
	$path="avatars/$guid.png";
	file_put_contents($path,base64_decode($image));	
}
?>