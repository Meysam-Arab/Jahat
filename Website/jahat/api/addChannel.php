<?php
require_once 'include/Config.php';
error_reporting(0);
// json response array
$con=mysql_connect(DB_HOST,DB_USER,DB_PASSWORD);
mysql_query("SET title 'utf8'", $con);

require_once 'getToken.php';

///add new channel
if (isset($_POST['addNew']) && isset($_POST['jwTw'])) {
	
	$name=$_POST['name'];
    $link=$_POST['link'];
    
    if(isChannelExist($link)){
        $response["error"] = TRUE;
        $response["error_msg"] = "این لینک قبلا اضافه شده است.";
        echo json_encode($response);
        return;
    }


    if(isset($_POST['description'])){
        $description=$_POST['description'];
    }else{
        $infoFetchApi="https://api.pwrtelegram.xyz/bot".getToken()."/getChat?chat_id=@".$link;
        $response2=json_decode(file_get_contents($infoFetchApi),true);
        if(is_null($response2) || !$response2['ok']){
            $response["error"] = TRUE;
            $response["error_msg"] = "مشکل در دریافت اطلاعات از سرور تلگرام،لطفا قسمت توضیحات را خودتان پر کنید.";
            echo json_encode($response);
            return;
        }else{
            $description=$response2['result']['about'];
        }
    }
    if($description==""){
        $response["error"] = TRUE;
        $response["error_msg"] = "لطفا قسمت توضیحات را خودتان پر کنید.";
        echo json_encode($response);
        return;
    }
	$jwTw=$_POST['jwTw'];
	$type=$_POST['type'];
	$email=$_POST['email'];

	$avatar=null;
    if(isset($_POST['image'])){
        $avatar=$_POST['image'];
    }else{
        $nameFetchApi="https://api.pwrtelegram.xyz/bot".getToken()."/getProfilePhotos?chat_id=@".$link."&offset=0&limit=1";
        $response2=json_decode(file_get_contents($nameFetchApi),true);
        if(is_null($response2) || !$response2['ok']){
            $response["error"] = TRUE;
            $response["error_msg"] = "مشکل در دریافت عکس از سرور تلگرام.لطفا بعد از بررسی صحت آدرس ،دوباره سعی کنید.یا خودتان عکسی را انتخاب کنید.";
            echo json_encode($response);
            return;
        }else{
            $file_id=$response2['result']['photos'][0][0]['file_id'];
            $nameFetchApi="https://api.pwrtelegram.xyz/bot".getToken()."/getFile?file_id=".$file_id;
            $response2=json_decode(file_get_contents($nameFetchApi),true);
            if(is_null($response2) || !$response2['ok']){
                $response["error"] = TRUE;
                $response["error_msg"] = "مشکل در دریافت عکس از سرور تلگرام.لطفا بعد از بررسی صحت آدرس ،دوباره سعی کنید.یا خودتان عکسی را انتخاب کنید.";
                echo json_encode($response);
                return;
            }else{
                $file_path=$response2['result']['file_path'];
                $nameFetchApi="https://storage.pwrtelegram.xyz/".$file_path;
                $avatar=base64_encode(file_get_contents($nameFetchApi));
            }
        }
    }
//    //if couldnt store avatar
//    if($avatar==null){
//        $response["error"] = TRUE;
//        $response["error_msg"] = "لینک معتبر نیست!!!";
//        echo json_encode($response);
//        return;
//    }
	
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
	
	$score=0;
	if($jwTw='jwbk84w')
		$score=$_POST['score'];
	
	if(!isChannelExist($link)){
		$uuid = uniqid('', true);
		$out = mysql_query("INSERT INTO ".DB_DATABASE.".`channels`(unique_id,name,email,description,link,score,created_at,type) VALUES('$uuid','$name','$email','$description','$link','$score',NOW(),'$type');");
		if ($out != false) {
			$out=selectChannelByLink($link);
			if ($out != false) {
				$response["error"] = FALSE;
				$response["unique_id"] = $uuid;
				$response["mess"] = "عملیات موفقیت آمیز بود.رمز شما به ایمیل شما ارسال شد.";
				saveAvatar($uuid,$avatar);
				sendMail($email,$name,$uuid,$link);
			}else{
				$response["error"] = TRUE;
				$response["error_msg"] = "مشکلی وجود دارد!";
			}
			echo json_encode($response);
		}else {
			// cant insert
			$response["error"] = TRUE;
			$response["error_msg"] = "مشکلی وجود دارد!";
			echo json_encode($response);
		}
	}else{
		// cant insert
        $response["error"] = TRUE;
        $response["error_msg"] = "این لینک قبلا اضافه شده است.";
        echo json_encode($response);
	}
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
function sendMail($email,$name,$uud,$link){
	$subject = $name."اضافه شد:";
	$message = "لینک: ".$link."      رمز: ".$uud;
	//$headers ="header is this";
	$headers = 'From: webmaster@example.com' . "\r\n" .
		'Reply-To: webmaster@example.com' . "\r\n" .
		'X-Mailer: PHP/' . phpversion();
	mail($email, $subject, $message, $headers);
}
function saveAvatar($guid,$image){
	$path="avatars/$guid.png";
	file_put_contents($path,base64_decode($image));	
}
?>