<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 2/2/2017
 * Time: 11:50 PM
 */
header('Content-Type: text/html; charset=utf-8');

// define variables and set to empty values
$name = $email = $tel = $mobile = $title = $description = "";
$message = "";
$status = "";
$emailErr = $titleErr = $descriptionErr = "";
if ($_SERVER["REQUEST_METHOD"] == "POST") {

        if (empty($_POST["name"])) {
            $name = "";
        } else {
            $name = test_input($_POST["name"]);
        }

        if (empty($_POST["email"])) {
            $emailErr = "وارد کردن پست الکترونیک اجباری می باشد";
        } else {
            $email = test_input($_POST["email"]);
        }

        if (empty($_POST["tel"])) {
            $tel = "";
        } else {
            $tel = test_input($_POST["tel"]);
        }

        if (empty($_POST["mobile"])) {
            $mobile = "";
        } else {
            $mobile = test_input($_POST["mobile"]);
        }

        if (empty($_POST["title"])) {
            $titleErr = "وارد کردن عنوان اجباری می باشد";
        } else {
            $title = test_input($_POST["title"]);
        }

    if (empty($_POST["description"])) {
        $descriptionErr = "وارد کردن توضیحات اجباری می باشد";
    } else {
        $description = test_input($_POST["description"]);
    }

   if ($emailErr != "" || $titleErr != "" || $descriptionErr != "")
   {
       // error token the form data
       $message = 'خطا در اطلاعات ورودی';
       $status = 'Error';
       $arr = array ('status'=>$status,'message'=>$message);
       //            header('Content-Type: application/json');
//            return json_encode($arr);
       echo json_encode($arr);
       return;
   }

    $unique_id = uniqid();
    $created_at = date_create()->format('Y-m-d H:i:s');

    $servername = "localhost";
    $username = "jahat_db";
    $password = "jahatF7E@xy12";

    $dbname = "jahat_db";

// Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    mysqli_set_charset($conn, "utf8");

// Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
      $message = 'عدم برقراری ارتباط با سرور';
      $status = 'Error';
        $arr = array ('status'=>$status,'message'=>$message);
        //            header('Content-Type: application/json');
//            return json_encode($arr);
        echo json_encode($arr);
        return;
    }

    try
    {
        $sql = "INSERT INTO feedback (unique_id, name, title, description, email, tel, mobile, created_at)
                VALUES ('$unique_id', '$name', '$title', '$description', '$email', '$tel', '$mobile', '$created_at')";

        if ($conn->query($sql) === TRUE) {
            $message = 'عملیات با موفقیت انجام شد';
            $status = 'Success';
        } else {
            $message = 'عدم برقراری ارتباط با سرور'.' '. $sql . "<br>" . $conn->error;
            $status = 'Error';
        }
        $arr = array ('status'=>$status,'message'=>$message);
        //            header('Content-Type: application/json');
//            return json_encode($arr);
       // echo json_encode($arr);
       // return;
    }
    catch (Exception $ex)
    {
        $message = 'عدم برقراری ارتباط با سرور';
        $status = 'Error'.' '.$ex->getMessage();
    }
    finally
    {
        $conn->close();
        $arr = array ('status'=>$status,'message'=>$message);
        //            header('Content-Type: application/json');
//            return json_encode($arr);
        echo json_encode($arr);
        return;
    }
    $arr = array ('status'=>$status,'message'=>$message);
//            header('Content-Type: application/json');
//            return json_encode($arr);
    echo json_encode($arr);
    return;
}

function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
?>