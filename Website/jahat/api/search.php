<?php
require_once 'include/Config.php';
error_reporting(0);
// json response array
$con=mysql_connect(DB_HOST,DB_USER,DB_PASSWORD);
mysql_query("SET name 'utf8'", $con);
mysql_query("SET description 'utf8'", $con);
// Create connection
//$con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
//mysqli_set_charset($con, "utf8");


require_once 'getToken.php';

///search channels
if (isset($_POST['filter'])) {

    $lim=intval($_POST['lim']);
    $search=$_POST['filter'];
    $type=$_POST['type'];

    $cat="";
    if($type!=0)
        $cat="AND ".DB_DATABASE.".channels.type='$type'";

    if(startsWithAtsiyn($search)){
        $search=substr($search,1);
        $out = mysql_query("
		SELECT ".DB_DATABASE.".channels.*  
		FROM ".DB_DATABASE.".channels 
		WHERE ".DB_DATABASE.".channels.link LIKE '%$search%'  
		AND ".DB_DATABASE.".channels.deleted_at is null 
		AND ".DB_DATABASE.".channels.isAuthenticated=1 
		".$cat." 
		ORDER BY score DESC 
		LIMIT $lim , 10
		;");
    }else{
        //$out = mysql_query("
        //SELECT *
        //FROM ".DB_DATABASE.".channels
        //WHERE (".DB_DATABASE.".channels.name LIKE '%$search%'
        //or  MATCH (".DB_DATABASE.".channels.name,".DB_DATABASE.".channels.description) AGAINST ('$search' IN NATURAL LANGUAGE MODE))
        //AND ".DB_DATABASE.".channels.deleted_at is null
        //AND ".DB_DATABASE.".channels.isAuthenticated=1
        //".$cat."
        //ORDER BY score DESC
        //LIMIT $lim , 20
        //;");
        //SUM(".DB_DATABASE.".channels.name LIKE '%$search%') AS like_relevance ,
        $out = mysql_query("
		SELECT ".DB_DATABASE.".channels.* , 
				MATCH (".DB_DATABASE.".channels.name) AGAINST ('$search' IN NATURAL LANGUAGE MODE) AS title_relevance ,
				MATCH (".DB_DATABASE.".channels.name,".DB_DATABASE.".channels.description) AGAINST ('$search' IN NATURAL LANGUAGE MODE) AS relevance 
		FROM ".DB_DATABASE.".channels 
		WHERE (".DB_DATABASE.".channels.name LIKE '%$search%' 
		or  MATCH (".DB_DATABASE.".channels.name,".DB_DATABASE.".channels.description) AGAINST ('$search' IN NATURAL LANGUAGE MODE)
		or ".DB_DATABASE.".channels.description LIKE '%$search%') 
		AND ".DB_DATABASE.".channels.deleted_at is null 
		AND ".DB_DATABASE.".channels.isAuthenticated=1 
		".$cat." 
		ORDER BY score DESC,title_relevance+relevance DESC,CASE WHEN name LIKE '$search%' THEN 0.03
																WHEN name LIKE '%$search%' THEN 0.02
																WHEN name LIKE '%$search' THEN 0.01
																ELSE 0.005
															END DESC
														  ,CASE WHEN description LIKE '$search%' THEN 0.003
																WHEN description LIKE '%$search%' THEN 0.002
																WHEN description LIKE '%$search' THEN 0.001
																ELSE 0.0005
															END DESC					
		LIMIT $lim , 10 
		;");
    }


    $flag=false;
    if (mysql_num_rows($out)!=0) {
        $response["error"] = TRUE;
        while($row=mysql_fetch_array($out,MYSQL_ASSOC)){

            //echo "<br/>";
            //echo "::".$row["type"]."<br/>";
            //other detail



            ////members
            if($row["type"]==1){
              $memberFetchApi="https://api.telegram.org/bot".getToken()."/getChatMembersCount?chat_id=@".$row["link"];
              $response2=json_decode(file_get_contents($memberFetchApi),true);
              if(is_null($response2)){ continue;}
            }




            $flag=true;
            //var_dump($response);
            //echo"<br/>".$hello['result'];
            //return;
            //var_dump($temp);
            //$response = json_encode($temp);
            //var_dump($response);
            //echo"<br/>".$response->ok;

            //echo "::".$row["type"]."<br/>";


            $numberMember="unknown";
            if($response2['ok']) {$numberMember=$response2['result'];}

            $response["table"][]=array('id' => $row["id"]
            ,'title_relevance' => $row["title_relevance"]
            ,'relevance' => $row["relevance"]
            ,'unique_id' => $row["unique_id"]
            ,'name' => $row["name"]
            ,'description' => $row["description"]
            ,'link' => $row["link"]
            ,'type' => $row["type"]
            ,'score' => $row["score"]
            ,'avatar' => getAvatar($row["unique_id"])
            ,'numberMember' => $numberMember
            ,'created_at' => $row["created_at"]
            ,'updated_at' => $row["updated_at"]);
        }
        if($flag) $response["error"] = false;
        else $response["error_msg"] = "متاسفانه چیزی پیدا نشد!!!";
        echo json_encode($response);
    } else {
        // channel is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "متاسفانه چیزی پیدا نشد!!!";
        echo json_encode($response);
    }
}
function startsWithAtsiyn($haystack)
{
    return (substr($haystack, 0, 1) === '@');
}
function getAvatar($guid){
    $path="avatars/$guid.png";
    $temp=file_get_contents($path);
    return $temp===false?"":base64_encode($temp);
}
?>