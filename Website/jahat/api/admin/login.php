<script language="javascript" type="text/javascript">
function POSTfocus()
{
document.POSTElementById('myAnchor').focus()
}
function POSTfocusE()
{
document.POSTElementById('myAnchorE').focus()
}
</script>
<?php
	session_start();
	$error="";
	if(isset($_POST['login']))
	{
		$user=$_POST['username'];
		$pass01=$_POST['password01'];
		$pass02=$_POST['password02'];
		$con=mysql_connect("localhost","jahat_db","jahatF7E@xy12");
		$out=mysql_query("select * from `jahat_db`.`users` where `user`='$user' AND `password01`='$pass01' AND `password02`='$pass02';");
		while($row=mysql_fetch_array($out,MYSQL_ASSOC)){
			$_SESSION['user']=$user;
			header('Location: main.php?offset=0&limit=50');
		}
	}
?>
<html>
<body>
<center>
<form method="POST">
<table BORDER='2'>
	<tr><td bgcolor='LightGoldenRodYellow'>Username :<input type="text" id="myAnchorE" name="username" value=""></td></tr>
    <tr><td bgcolor='LightGoldenRodYellow'>Password_1 :<input type="password" id="myAnchor" name="password01"></td><tr>
	<tr><td bgcolor='LightGoldenRodYellow'>Password_2 :<input type="password" id="myAnchor" name="password02"></td><tr>
    <td bgcolor='LightGoldenRodYellow'><input type="submit" name="login" value="Login"></td></tr>    
</form>
<font color='red'>
<?php  echo($error);?>
</font>
</table>
</center>
</body>
</html>