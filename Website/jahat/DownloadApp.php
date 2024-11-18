<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 2/2/2017
 * Time: 1:09 PM
 */

$filename = "download/jahat_v1.0.apk";

header("Content-Length: " . filesize($filename));
header('Content-Type: application/octet-stream');
header('Content-Disposition: attachment; filename=jahat_v1.0.apk');

readfile($filename);
?>