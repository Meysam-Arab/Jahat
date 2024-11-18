<?php
session_start();
if (empty($_SESSION['csrf_token'])) {
    if (function_exists('mcrypt_create_iv')) {
        $_SESSION['csrf_token'] = bin2hex(mcrypt_create_iv(32, MCRYPT_DEV_URANDOM));
    } else {
        $_SESSION['csrf_token'] = bin2hex(openssl_random_pseudo_bytes(32));
    }
}
$token = $_SESSION['csrf_token'];
?>


<!DOCTYPE html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>

    <style>
        .error {
            color: #FF0000;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/bootstrap-responsive.css">
    <link rel="stylesheet" href="css/custom-styles.css">
    <link rel="stylesheet" href="css/font-awesome.css">
    <link rel="stylesheet" href="css/component.css">
    <link rel="stylesheet" href="css/font-awesome-ie7.css">
    <style>
        * {
            font-family: Tahoma;
        }
    </style>

    <script src="js/modernizr.custom.js"></script>
    <script src="js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body dir="rtl">
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to
    improve your experience.</p>
<![endif]-->

<!-- This code is taken from http://twitter.github.com/bootstrap/examples/hero.html -->
<div class="header-wrapper">
    <div class="container">
        <div class="row-fluid">

            <div class="site-name">
                <h1 style="text-align:center">&#1580;&#1607;&#1578; (&#1580;&#1587;&#1578; &#1608; &#1580;&#1608;&#1740; &#1607;&#1608;&#1588;&#1605;&#1606;&#1583; &#1578;&#1604;&#1711;&#1585;&#1575;&#1605;)</h1>
            </div>


            <div class="menu-icons">
                <ul>
                    <!--<li><a href="#"><i class="fw-icon-facebook i"></i></a> </li>-->
                    <!--<li><a href="#"><i class="fw-icon-twitter i"></i></a> </li>-->
                    <!--<li><a href="#"><i class="fw-icon-linkedin i"></i></a> </li>-->
                    <!--<li><form id="custom-search-form" class="form-search form-horizontal pull-right"><div class="input-append"><input type="text" class="search-query"><button type="submit" class="btn"><i class="fw-icon-search"></i></button></div></form></li>-->
                </ul>
            </div>
            <div class="cust-form">
                <form id="custom-search-form" class="form-search form-horizontal pull-right">

                    <input type="text" class="search-query">
                    <button type="submit" class="btn"><i class="fw-icon-search"></i></button>

                </form>
            </div>

        </div>
    </div>
</div>
<div class="container">
    <div class="menu">

        <div class="navbar">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <i class="fw-icon-th-list"></i>
            </a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="#">&#1582;&#1575;&#1606;&#1607;</a></li>
                    <li><a href="#aboutus">&#1583;&#1585;&#1576;&#1575;&#1585;&#1607; &#1605;&#1575;</a></li>
                    <li><a href="#contactus">&#1575;&#1585;&#1578;&#1576;&#1575;&#1591; &#1576;&#1575; &#1605;&#1575;</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
        <div class="mini-menu">
            <label>
                <select class="selectnav">
                    <option value="#" selected="">Home</option>
                    <option value="#">About</option>
                    <option value="#">&#8594; Another action</option>
                    <option value="#">&#8594; Something else here</option>
                    <option value="#">&#8594; Another action</option>
                    <option value="#">&#8594; Something else here</option>
                    <option value="#">Services</option>
                    <option value="#">Work</option>
                    <option value="#">Contact</option>
                </select>
            </label>
        </div>

    </div>
</div>
<div class="container bg-white">
    <div class="row-fluid">
        <div class="span12 ">
            <div class="main-caption">
                <h1>&#1580;&#1587;&#1578;&#1580;&#1608;&#1740; &#1607;&#1608;&#1588;&#1605;&#1606;&#1583; &#1705;&#1575;&#1606;&#1575;&#1604; &#1607;&#1575;&#1740; &#1578;&#1604;&#1711;&#1585;&#1575;&#1605; &#1585;&#1575; &#1576;&#1575; &#1605;&#1575; &#1578;&#1580;&#1585;&#1576;&#1607; &#1705;&#1606;&#1740;&#1583;</h1>
                <h5>&#1705;&#1575;&#1606;&#1575;&#1604; &#1582;&#1608;&#1583; &#1585;&#1575; &#1579;&#1576;&#1578; &#1705;&#1606;&#1740;&#1583; &#1608; &#1576;&#1575;&#1586;&#1583;&#1740;&#1583; &#1582;&#1608;&#1583; &#1585;&#1575; &#1575;&#1601;&#1586;&#1575;&#1740;&#1588; &#1583;&#1607;&#1740;&#1583;</h5>
                <h6></h6>
                <a href="DownloadApp.php" class="btn" style="padding: 5px 50px">&#1583;&#1575;&#1606;&#1604;&#1608;&#1583;</a>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <div class="banner">
                <div class="carousel slide" id="myCarousel">
                    <!-- Carousel items -->
                    <div class="carousel-inner">
                        <div class="item">
                            <img src="img/banner1.jpg" alt="">
                        </div>
                        <div class="item active">
                            <img src="img/banner2.jpg" alt="">
                        </div>
                        <div class="item">
                            <img src="img/banner3.jpg" alt="">
                        </div>
                    </div>
                    <a data-slide="prev" href="#myCarousel" class="carousel-control left"><i
                                class="fw-icon-chevron-left"></i></a>
                    <a data-slide="next" href="#myCarousel" class="carousel-control right"><i
                                class="fw-icon-chevron-right"></i></a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container" id="aboutus">
    <div class="featured-content">
        <div class="row-fluid">
            <div class="span8">
                <div class="row-fluid">
                    <div class="span6">
                        <img src="img/company.jpg" class="img-rounded" alt="">
                    </div>
                    <div class="span6">
                        <div class="block">
                            <h4>&#1583;&#1585;&#1576;&#1575;&#1585;&#1607; &#1580;&#1607;&#1578;</h4>
                            <h6>&#1580;&#1587;&#1578; &#1608; &#1580;&#1608;&#1740; &#1607;&#1608;&#1588;&#1605;&#1606;&#1583; &#1576;&#1585;&#1585;&#1608;&#1740; &#1705;&#1575;&#1606;&#1575;&#1604;&#1548;&#1711;&#1585;&#1608;&#1607; &#1608; &#1585;&#1576;&#1575;&#1578; &#1607;&#1575;&#1740; &#1578;&#1604;&#1711;&#1585;&#1575;&#1605;&#1740;</h6>
                            <p>
                                &#1583;&#1585; &#1581;&#1575;&#1604; &#1581;&#1575;&#1590;&#1585; &#1578;&#1606;&#1607;&#1575; &#1585;&#1575;&#1607; &#1580;&#1587;&#1578;&#1580;&#1608; &#1583;&#1585; &#1578;&#1604;&#1711;&#1585;&#1575;&#1605; &#1580;&#1587;&#1578;&#1580;&#1608;&#1740; &#1570;&#1740; &#1583;&#1740; (&#1576;&#1607; &#1591;&#1608;&#1585; &#1583;&#1602;&#1740;&#1602;) &#1605;&#1740; &#1576;&#1575;&#1588;&#1583;. &#1593;&#1604;&#1575;&#1608;&#1607; &#1576;&#1585; &#1575;&#1740;&#1606;&#1548; &#1576;&#1585;&#1575;&#1587;&#1575;&#1587; &#1605;&#1575;&#1607;&#1740;&#1578; &#1606;&#1605;&#1740; &#1578;&#1608;&#1575;&#1606; &#1580;&#1587;&#1578;&#1580;&#1608;&#1740;&#1740; &#1585;&#1575; &#1583;&#1585; &#1578;&#1604;&#1711;&#1585;&#1575;&#1605; &#1575;&#1606;&#1580;&#1575;&#1605; &#1583;&#1575;&#1583;. &#1576;&#1607; &#1591;&#1608;&#1585; &#1605;&#1579;&#1575;&#1604;&#1548; &#1705;&#1575;&#1585;&#1576;&#1585; &#1576;&#1607; &#1583;&#1606;&#1576;&#1575;&#1604; &#1705;&#1575;&#1606;&#1575;&#1604;&#1740; &#1593;&#1604;&#1605;&#1740; &#1605;&#1740; &#1576;&#1575;&#1588;&#1583;. &#1576;&#1585;&#1606;&#1575;&#1605;&#1607; &#1740; &#1580;&#1607;&#1578; &#1576;&#1575; &#1575;&#1587;&#1578;&#1601;&#1575;&#1583;&#1607; &#1575;&#1586; &#1575;&#1604;&#1711;&#1608;&#1585;&#1740;&#1578;&#1605; &#1607;&#1575;&#1740; &#1580;&#1587;&#1578;&#1580;&#1608;&#1740; &#1607;&#1608;&#1588;&#1605;&#1606;&#1583; &#1576;&#1585; &#1575;&#1740;&#1606; &#1605;&#1588;&#1705;&#1604; &#1594;&#1604;&#1576;&#1607; &#1705;&#1585;&#1583;&#1607; &#1608; &#1575;&#1740;&#1606; &#1575;&#1605;&#1705;&#1575;&#1606; &#1585;&#1575; &#1576;&#1607; &#1705;&#1575;&#1585;&#1576;&#1585; &#1605;&#1740; &#1583;&#1607;&#1583; &#1705;&#1607; &#1580;&#1587;&#1578;&#1580;&#1608;&#1740; &#1582;&#1608;&#1583; &#1585;&#1575; &#1576;&#1585; &#1585;&#1608;&#1740; &#1593;&#1606;&#1608;&#1575;&#1606; &#1740;&#1575; &#1588;&#1585;&#1581; &#1740;&#1575; &#1604;&#1740;&#1606;&#1705; &#1575;&#1606;&#1580;&#1575;&#1605; &#1583;&#1607;&#1583; &#1576;&#1607; &#1575;&#1740;&#1606; &#1589;&#1608;&#1585;&#1578; &#1705;&#1607; &#1705;&#1575;&#1585;&#1576;&#1585; &#1605;&#1740; &#1578;&#1608;&#1575;&#1606;&#1583; &#1576;&#1575; &#1580;&#1587;&#1578; &#1608; &#1580;&#1608;&#1740; &#1602;&#1587;&#1605;&#1578;&#1740; &#1575;&#1586; &#1593;&#1606;&#1608;&#1575;&#1606; &#1740;&#1575; &#1604;&#1740;&#1606;&#1705; &#1605;&#1608;&#1585;&#1583; &#1606;&#1592;&#1585;&#1588;&#1548;&#1705;&#1575;&#1606;&#1575;&#1604; &#1607;&#1575;&#1740; &#1605;&#1608;&#1585;&#1583; &#1606;&#1592;&#1585;&#1588; &#1585;&#1575; &#1576;&#1740;&#1575;&#1576;&#1583;.
                            </p>
                        </div>
                    </div>
                    <div class="span6">
                        <div class="block">
                            <h4>&#1583;&#1585;&#1576;&#1575;&#1585;&#1607; &#1605;&#1575;</h4>
                            <h6>&#1588;&#1585;&#1705;&#1578; &#1607;&#1608;&#1588; &#1605;&#1589;&#1606;&#1608;&#1593;&#1740; &#1601;&#1585;&#1583;&#1575;&#1606; &#1607;&#1601;&#1578; &#1575;&#1602;&#1604;&#1740;&#1605;</h6>
                            <p>
                                &#1588;&#1585;&#1705;&#1578; &#1607;&#1608;&#1588; &#1605;&#1589;&#1606;&#1608;&#1593;&#1740; &#1601;&#1585;&#1583;&#1575;&#1606; &#1607;&#1601;&#1578; &#1575;&#1602;&#1604;&#1740;&#1605; &#1576;&#1575; &#1705;&#1575;&#1583;&#1585;&#1740; &#1605;&#1580;&#1585;&#1576; &#1570;&#1605;&#1575;&#1583;&#1607; &#1607;&#1605;&#1705;&#1575;&#1585;&#1740; &#1608; &#1662;&#1575;&#1587;&#1582;&#1711;&#1608;&#1740;&#1740; &#1576;&#1607; &#1606;&#1740;&#1575;&#1586;&#1607;&#1575;&#1740; &#1588;&#1605;&#1575; &#1583;&#1585;
                                &#1578;&#1605;&#1575;&#1605;&#1740; &#1586;&#1605;&#1740;&#1606;&#1607; &#1607;&#1575;&#1740; &#1605;&#1585;&#1578;&#1576;&#1591; &#1605;&#1740;&#1576;&#1575;&#1588;&#1583;.
                                <!--Sed egestas, ante et vulputate volutpat, eros pede semper vitae luctus metus libero eu augue. Morbi purus libero. -->
                            </p>
                            <a href="http://www.fardan7eghlim.ir" class="btn" target="_blank">&#1608;&#1576; &#1587;&#1575;&#1740;&#1578; &#1585;&#1587;&#1605;&#1740; &#1588;&#1585;&#1705;&#1578;<i
                                        class="fw-icon-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="ruler-t"></div>

            </div>

            <div class="span4">

                <div class="contact-form" id="contactus">
                    <div class="block-title">
                        <i class="fw-icon-pencil i" style="margin-left: 10px"></i>
                        <h4>&#1575;&#1585;&#1578;&#1576;&#1591;&#1575; &#1576;&#1575; &#1605;&#1575;</h4>
                        <h6>&#1575;&#1586; &#1591;&#1585;&#1740;&#1602; &#1601;&#1585;&#1605; &#1586;&#1740;&#1585; &#1605;&#1740; &#1578;&#1608;&#1575;&#1606;&#1740;&#1583; &#1576;&#1575; &#1605;&#1575; &#1575;&#1585;&#1578;&#1576;&#1575;&#1591; &#1576;&#1585;&#1602;&#1585;&#1575;&#1585; &#1705;&#1606;&#1740;&#1583;</h6>
                    </div>
                    <div class="block-content">
                        <form action="Feedback.php" name="frm1" id="frm1" method="post">
                            <fieldset>

                                <div id="returnedMessage">

                                </div>
                                <p><span class="error">* </span> &#1601;&#1740;&#1604;&#1583;&#1607;&#1575;&#1740; &#1575;&#1580;&#1576;&#1575;&#1585;&#1740;</p>
                                <input type="text" name="txt_name" id="txt_name" class="input-block-level"
                                       placeholder="&#1606;&#1575;&#1605; &#1588;&#1605;&#1575;..."></input>
                                <span class="error">* </span>
                                <div class="error" id="emailerror" name="emailerror"></div>
                                <input type="text" name="email" id="email" class="input-block-level"
                                       placeholder="&#1575;&#1740;&#1605;&#1740;&#1604; &#1588;&#1605;&#1575;..."></input>
                                <input type="text" name="tel" id="tel" class="input-block-level"
                                       placeholder="&#1578;&#1604;&#1601;&#1606; &#1579;&#1575;&#1576;&#1578; &#1588;&#1605;&#1575;...">
                                <input type="text" name="mobile" id="mobile" class="input-block-level"
                                       placeholder="&#1578;&#1604;&#1601;&#1606; &#1607;&#1605;&#1585;&#1575;&#1607; &#1588;&#1605;&#1575;...">
                                <span class="error">* </span>
                                <div class="error" id="titleerror" name="titleerror"></div>

                                <input type="text" name="title" id="title" class="input-block-level"
                                       placeholder="&#1593;&#1606;&#1608;&#1575;&#1606; &#1662;&#1740;&#1575;&#1605;...">
                                <span class="error">* </span>
                                <div class="error" id="descriptionerror" name="descriptionerror"></div>

                                <textarea name="description" id="description" class=" input-block-level"
                                          placeholder="&#1605;&#1578;&#1606; &#1662;&#1740;&#1575;&#1605;..." cols="30" rows="6"></textarea>



                            </fieldset>
                        </form>
                        <button type="submit" id="feedbacksubmit" name="feedbacksubmit" class="btn">&#1575;&#1585;&#1587;&#1575;&#1604; <i
                                    class="fw-icon-arrow-right"></i></button>
                    </div>
                </div>

            </div>

        </div>
    </div>
</div>

<div class="site-footer">
    <div class="container">

        <div class="row-fluid">
            <div class="span2">
                <div class="block">
                    <h5>&#1604;&#1740;&#1606;&#1705; &#1607;&#1575;&#1740; &#1605;&#1601;&#1740;&#1583;</h5>
                    <ul>
                        <li><a href="http://fardan7eghlim.ir" target="_blank">&#1588;&#1585;&#1705;&#1578; &#1607;&#1608;&#1588; &#1605;&#1589;&#1606;&#1608;&#1593;&#1740; &#1601;&#1585;&#1583;&#1575;&#1606; &#1607;&#1601;&#1578; &#1575;&#1602;&#1604;&#1740;&#1605;</a></li>
                        <li><a href="https://cafebazaar.ir" target="_blank">&#1576;&#1575;&#1586;&#1575;&#1585;</a></li>
                        <li><a href="http://charkhoneh.com/" target="_blank">&#1670;&#1607;&#1575;&#1585;&#1582;&#1608;&#1606;&#1607;</a></li>
                        <li class="border-none"><a href="http://avvalmarket.ir" target="_blank">&#1607;&#1605;&#1585;&#1575;&#1607; &#1605;&#1575;&#1585;&#1705;&#1578;</a></li>
                    </ul>
                </div>
            </div>
        </div>


    </div>
</div>

<div class="wrap bg-black">
    <div class="container ">
        <div class="row-fluid">
            <div class="span12">
                <div class="copy-rights">

                    &#1578;&#1605;&#1575;&#1605;&#1740; &#1581;&#1602;&#1608;&#1602; &#1606;&#1586;&#1583;<a href="http://www.fardan7eghlim.net"> &#1607;&#1608;&#1588; &#1605;&#1589;&#1606;&#1608;&#1593;&#1740; &#1601;&#1585;&#1583;&#1575;&#1606; &#1607;&#1601;&#1578; &#1575;&#1602;&#1604;&#1740;&#1605;</a> &#1605;&#1581;&#1601;&#1608;&#1592; &#1605;&#1740; &#1576;&#1575;&#1588;&#1583;.
                </div>

            </div>
        </div>
    </div>
</div>

<script src="js/jquery-1.9.1.js"></script>

<script>


//    new AnimOnScroll(document.getElementById('grid'), {
//        minDuration: 0.4,
//        maxDuration: 0.7,
//        viewportFactor: 0.2
//    });

    $('#feedbacksubmit').click(function (e) {
        e.preventDefault(); // <------------------ stop default behaviour of button
        var element = this;

        var returnValue = new Object();//dynamically fill model value with different model entity
        returnValue.Title = $("#title").val();
        returnValue.Email = $("#email").val();
        returnValue.Tel = $("#tel").val();
        returnValue.Mobile = $("#mobile").val();
        returnValue.Name = $("#txt_name").val();
        returnValue.Description = $("#description").val();


        $.ajax({
            url: "Feedback.php",
            type: "POST",
            data: {
                csrf_token: gettoken(),
                title: returnValue.Title,
                email: returnValue.Email,
                tel: returnValue.Tel,
                mobile: returnValue.Mobile,
                name: returnValue.Name,
                description: returnValue.Description

            },
//              dataType: "json",
//              traditional: true,
//              contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (data) {
                // Here is the tip
//                  var data = JSON.stringify(data)
                var data = $.parseJSON(data);
                if (data.status == "Success") {
                    alert(data.message);

                    document.getElementById("returnedMessage").innerHTML = data.message;
                    clearFeedBackForm();
                    
                    //$(element).closest("form").submit(); //<------------ submit form
                } else {
                    document.getElementById("returnedMessage").innerHTML = data.message;
                    document.getElementById("emailerror").innerHTML = data.emailErr;
                    document.getElementById("titleerror").innerHTML = data.titleErr;
                    document.getElementById("descriptionerror").innerHTML = data.descriptionErr;
                    clearFeedBackForm();
                    alert(data.status);
                }
            },
            error: function () {
                document.getElementById("returnedMessage").innerHTML = '&#1582;&#1591;&#1575;&#1583;&#1585; &#1575;&#1585;&#1587;&#1575;&#1604; &#1575;&#1591;&#1604;&#1575;&#1593;&#1575;&#1578;';
                clearFeedBackForm();
//                  alert("&#1582;&#1591;&#1575;&#1583;&#1585; &#1575;&#1585;&#1587;&#1575;&#1604; &#1575;&#1591;&#1604;&#1575;&#1593;&#1575;&#1578;");
            }
        });
    });
    function clearFeedBackForm() {


        document.getElementById('title').value = "";
        document.getElementById('email').value = "";
        document.getElementById('tel').value = "";
        document.getElementById('description').value = "";
        document.getElementById('mobile').value = "";
        document.getElementById('txt_name').value = "";

    }

    function gettoken() {
        var token = $("#token").val()
        return token;
    }
</script>
<script src="js/bootstrap.js"></script>
<script src="js/masonry.pkgd.min.js"></script>
<script src="js/imagesloaded.js"></script>
<script src="js/classie.js"></script>
<script src="js/AnimOnScroll.js"></script>
<script>
    $('#myCarousel').carousel({
        interval: 1800
    });
</script>
</body>
</html>
