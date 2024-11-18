<?php
/**
 * Created by PhpStorm.
 * User: Amir
 * Date: 6/17/2017
 * Time: 11:39 PM
 */
function  getToken(){

    $listOfBotToken=[
        "key1",
        "key2"
    ];
    $rndTemp=rand(0,count($listOfBotToken)-1);
    $tokenBot=$listOfBotToken[$rndTemp];

    return $tokenBot;
}

?>
