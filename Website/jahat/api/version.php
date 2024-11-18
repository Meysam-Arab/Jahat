<?php
require_once 'include/Config.php';

// json response array
$con=mysql_connect(DB_HOST,DB_USER,DB_PASSWORD);
mysql_query("SET title 'utf8'", $con);
///search channels
//ad should be 300*100
if (isset($_POST['getVersion'])) {
	$response=array('error' => false
					,'version' => "1.0"
					,'link' => "http://www.google.com"
                    ,'jilipili' => "false"
                    ,'Ad1ok' => "false"
                    ,'versionAd1' => "0.11"
                    ,'linkAd1' => "http://stockmedia.cc/business_concepts/thumbs/DSD_1977.jpg"
                    ,'linkOAd1' => "http://fardan7eghlim.ir"
                    ,'Ad2ok' => "false"
                    ,'versionAd2' => "0.11"
                    ,'linkAd2' => "http://stockmedia.cc/business_concepts/thumbs/_DSC0032.jpg"
                    ,'linkOAd2' => "http://fardan7eghlim.ir"
    );
	echo json_encode($response);
}
?>