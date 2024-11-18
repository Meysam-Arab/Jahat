<?php
require_once 'include/Config.php';

// json response array
$con=mysql_connect(DB_HOST,DB_USER,DB_PASSWORD);
mysql_query("SET title 'utf8'", $con);

require_once 'getToken.php';

///search channels
if (isset($_POST['getInfoOFchannel']) && isset($_POST['jZTw'])) {
	$link=$_POST['link'];
	
	$infoFetchApi="https://api.pwrtelegram.xyz/bot".getToken()."/getChat?chat_id=@".$link;
	//echo '<a href="'.$infoFetchApi.'">'.$infoFetchApi.'</a>';
	$response2=json_decode(file_get_contents($infoFetchApi),true);
	if(is_null($response2) || !$response2['ok']){ 
			$response["error"] = TRUE;
			//$response["error_msg"] = "لینک معتبر نیست!!!";
			echo json_encode($response);
			return;
	}else{
		$info=$response2['result']['about'];
		$response=array('error' => false
					   ,'info' => $info);
		echo json_encode($response);
	}
}
?>