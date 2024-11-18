<?php
/**
 * Created by PhpStorm.
 * User: Amir
 * Date: 6/17/2017
 * Time: 11:39 PM
 */
function  getToken(){

    $listOfBotToken=[
        "300974265:AAGqKQ04kWjliAyYPEtVX4MorNWmYeIahu0",
        "331504193:AAH48sJOrQvF20Ah-_OvAeCo4lXucuIgIm4",
        "350231374:AAGOx7JYszt9Yb8_KMDOfmS_QWqyraUn-z4",
        "371540588:AAFLCkGQePfL9nc5bqV2N1dZ-wiGqolnn_s",
        "312524868:AAF1dQlZQQETyhR8fXvuoH9Hl4UeL60oxpg",
        "344778152:AAFjPkVtGhWu8OYJXk6iIAOCjHiG-qcNepg",
        "356915612:AAFWo7b5A0wiwYEY3uqbaE0VLHD3DUTPn2M",
        "381720081:AAE4FjLUK_Ba4_Zp4XwMZR-GPcJPprJ4qNc",
        "286802233:AAHBFxXbGQQzust5mKx961lVyFLDGozggp0",
        "363745414:AAE0lZn5aVtOkjYHdIVBelolgcd03yZ955k",
        "391616651:AAGRqXXWiDljCCql37Rf5zDQp0SkmzmSR-4",
        "338036557:AAEhERLtZQITz8p0mw976qmPD3VGSxRH1XI",
        "372664781:AAHIJvNmvfdSmhOlzy2xVPN15nlHptPXEI4",
        "347427001:AAGJMUXJrwGgDg2Zs6MO0y0k9IK3I1cqVSk",
        "317977284:AAGQrFk47ISGcQCyVR7sfWGqxb3pttRWBnY",
        "341695319:AAEfNkha1oZAE_zc9NG7S2ScNnfbGn9ewZ0",
        "354331814:AAHdw5C7P7wBbl63M18dgws9gCDsQ_SU5BI",
        "371558853:AAGKh-1P0Dv25LXoKcopTYVAd1LBze8wTfs",
        "337401920:AAFiwv_7YDT98fOoVVVursvJ-JECXcGnUYs",
        "371558853:AAGKh-1P0Dv25LXoKcopTYVAd1LBze8wTfs",
        "337401920:AAFiwv_7YDT98fOoVVVursvJ-JECXcGnUYs",
        "344763896:AAHVbCAioWlroDbGIx_hUJg0cAXoMXIu3b4",
        "333014810:AAGRvqmubhCyKsqLstoj64Dtf6vqTQKRvbo",
        "264948897:AAF37bxJert5cEze-sO5Glr32AUnpwr3wDE",
        "389900359:AAGoVyStlEocM7egxxGUUB1wjCzP4UsCmWQ",
        "344335159:AAG3zU3U2BL3rxPjL6fMIQG5F7q3XNxBSIA",
        "392454326:AAE80RoFPGEmcmDyqdutcpntPJFaqFvqOo0",
        "393037273:AAFZHQRpO_M0KsPj4GUoWa1d55v6YrGC6HQ",
        "375923639:AAGGxuiYmCkFaDUiYC_YloBfgeKJ2wRVZHs",
        "394911935:AAF5644mNr5UmEFy_6rAvEiSl-qIj4Yuzyc",
        "348070163:AAFVtoscVIZctQtEWjqJjkVQ8iPCwnFI3hU",
        "348314956:AAF5lmqR_zEBXCK4o2ZN8hkCMBLXdRLjWJI",
        "303784952:AAHpQJildQqeaRzQq-oQcicAL5kd9KY04JI",
        "332726177:AAHzBuoyiHKVFWso8IB0JbVx8O0-Btqu6Jk"
    ];
    $rndTemp=rand(0,count($listOfBotToken)-1);
    $tokenBot=$listOfBotToken[$rndTemp];

    return $tokenBot;
}

?>