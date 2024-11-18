<?php
session_start();
if(!isset($_SESSION['user']))
	header('Location: login.php');
?>
<html>
<head></head>
<body>
<table border=2 width="40%">
<?php
$con=mysql_connect("localhost","jahat_db","jahatF7E@xy12");
$query_ext="";
if(isset($_GET['limit'])) $limit=$_GET['limit']; else $limit=50;
if(isset($_GET['offset'])) $offset=$_GET['offset']; else $offset=0;
if(isset($_POST['srchByName'])){
	$filter=$_POST['filter'];
	$query_ext=" AND `name` LIKE '%$filter%'";
}
if(isset($_POST['srchByLink'])){
	$filter=$_POST['filter'];
	$query_ext=" AND `link` LIKE '%$filter%'";
}
if(isset($_GET['conf'])){
	$uud=$_GET['ch_uid'];
	$id=$_GET['ch_id'];
	$rs=mysql_query("UPDATE `jahat_db`.`channels` 
						SET `jahat_db`.`channels`.`isAuthenticated`=1 
						WHERE `jahat_db`.`channels`.`unique_id`='$uud' AND `jahat_db`.`channels`.`id`='$id';");
	header("Location: main.php?offset=".$offset."&limit=".$limit);
}
if(isset($_GET['dlet'])){
	$uud=$_GET['ch_uid'];
	$id=$_GET['ch_id'];
	$rs=mysql_query("UPDATE `jahat_db`.`channels` 
						SET `jahat_db`.`channels`.`deleted_at`=NOW() 
						WHERE `jahat_db`.`channels`.`unique_id`='$uud' AND `jahat_db`.`channels`.`id`='$id';");
if(mysql_affected_rows()){ 
unlink("../avatars/".$uud.".png");
header('Location: main.php');}
}
if(!isset($_GET['isAuted'])){
	$rs=mysql_query("SELECT * FROM `jahat_db`.`channels` WHERE `isAuthenticated`=0 AND `deleted_at` is null ".$query_ext." ORDER BY `id` ASC LIMIT $offset, $limit;");
}
else{
	$rs=mysql_query("SELECT * FROM `jahat_db`.`channels` WHERE `isAuthenticated`=1 AND `deleted_at` is null ".$query_ext." ORDER BY `id` ASC LIMIT $offset, $limit;");
}
$flag=0;
$colorDifu='White';
if($rs)
while($row=mysql_fetch_array($rs,MYSQL_ASSOC)){ 
	$empty=0;
	if($flag==0)
	{
	echo"<tr>";
	foreach ($row as $temp => $value){
		echo"<td bgcolor='gray' style='color:white'><a href='main.php?sort=".$temp."' style='color: #ede0e0'>".$temp."</a></td>";
		$flag=1;
							         }
    echo"<td align=center colspan='2' bgcolor='gray' style='color:white;'>image</td>";	
	echo"<td align=center colspan='2' bgcolor='gray' style='color:white;'>-T-</td>";								 
	echo"</tr>";
	}
	echo"<tr>";
	if($colorDifu=='White')
	    $colorDifu='LightGoldenRodYellow';
	else
		$colorDifu='White';
	foreach ($row as $value)
		echo"<td bgcolor='$colorDifu'>".$value."</td>";
		$uid=$row['unique_id'];
		echo"<td bgcolor='$colorDifu'>";
		$image='../avatars/'.$uid.'.png';
		if(file_exists($image))
			echo "<img style='max-width:150px' src='$image'>";
		echo"</td>";
		if($row['type']==1 || $row['type']==3)
			$link="https://tlgrm.me/".$row['link'];
		else
			$link=$row['link'];
		echo"<td bgcolor='$colorDifu'><a href='$link' target='_blank'>open</a></td>";
		echo"<td align=center bgcolor='AliceBlue'><a href='main.php?conf=1&ch_id=".$row['id']."&ch_uid=".$row['unique_id']."'><img src='b_conf.png'></a></td>";
		echo"<td align=center bgcolor='AliceBlue'><a href='main.php?dlet=1&ch_id=".$row['id']."&ch_uid=".$row['unique_id']."'><img src='b_drop.png'></a></td>";
	echo"</tr>";
}
?>
</table>
<table border=2 width="40%">
<tr>
<td bgcolor='PapayaWhip'>
<form method="post">
<input type="text" name="filter" value=""/>
<input type="submit" name="srchByName" value="search by Name"/>
<input type="submit" name="srchByLink" value="search by Link"/>
</form>
<a href="main.php?offset=0&limit=50">
<input type="button" name="Add" value="unConfermed"/>
</a>
<a href="main.php?isAuted=1&offset=0&limit=50">
<input type="button" name="Add" value="Confermed"/>
</a>
</td>
</table>
<body>
</html>