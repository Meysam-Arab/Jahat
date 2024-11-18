<?php
require_once 'include/Config.php';
error_reporting(0);
// json response array
$con=mysql_connect(DB_HOST,DB_USER,DB_PASSWORD);
mysql_query("SET title 'utf8'", $con);
///search channels
if (isset($_POST['select']) && isset($_POST['jwTw'])) {
	
	$uud=$_POST['unique_id'];
	$link=$_POST['link'];
	
	$out = mysql_query("
	SELECT * 
	FROM ".DB_DATABASE.".channels 
	WHERE ".DB_DATABASE.".channels.deleted_at is null 
	AND ".DB_DATABASE.".channels.unique_id='$uud'
	AND ".DB_DATABASE.".channels.link='$link' 
	LIMIT 1 
	;");
    if (mysql_num_rows($out)!=0) {
        $response["error"] = FALSE;
		while($row=mysql_fetch_array($out,MYSQL_ASSOC)){
			$response["table"][]=array('id' => $row["id"]
									  ,'unique_id' => $row["unique_id"]
									  ,'name' => $row["name"]
									  ,'description' => $row["description"]
									  ,'link' => $row["link"]
									  ,'isAuthenticated' => $row["isAuthenticated"]
									  ,'type' => $row["type"]
									  ,'score' => $row["score"]
									  ,'avatar' => getAvatar($row["unique_id"])
									  ,'created_at' => $row["created_at"]
									  ,'updated_at' => $row["updated_at"]
									  ,'deleted_at' => $row["deleted_at"]);
		}
        echo json_encode($response);
    } else {
        // channel is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "متسفانه چیزی پیدا نشد!!!";
        echo json_encode($response);
    }
}
function getAvatar($guid){	
	$path="avatars/$guid.png";
	$temp=file_get_contents($path);
	return $temp===false?"":base64_encode($temp);
}
?>