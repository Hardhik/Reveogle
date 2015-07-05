<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--

Design by TEMPLATED
http://templated.co
Released for free under the Creative Commons Attribution License

Title      : Landscape
Version    : 1.0
Released   : 20070725
Description: A two-column, fixed-width template.

-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Product Reviews</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="default.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="header">
	<div id="titleName">
	Product Reviews

	</div>
	<div id="logo">
		<h1><a href="#">Search</a></h1>
		<h2><span>Product Reviews</span></h2>
	</div>	
	<div>
		
	</div>
<div id="page">
	<div id="content">
		<div>
			<u><h1 class="title"><center>Reviews displayed here</center></h1></u>

			<!--<p><strong>Landscape</strong>  <em>Enjoy :)</em></p>
			<h2>Praesent Scelerisque</h2>
			<blockquote>
				<p>&ldquo;Integer nisl get.&rdquo;</p>
			</blockquote>-->
		</div>
		</br></br>

<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
 	
//	echo count($_POST);
//	echo $_POST["FindProduct"];
	// foreach ($_POST as $key=>$value) {
	// 	echo "<p>";
	// 	echo $key;
	// 	echo " ";
	// 	echo $value;
	// 	echo " </p>";
	// }
	if (isset($_POST["FindReview"])){
		
		//echo "entered FindReview";
		$searchStr= $_POST["product"];
	 	//echo "<strong>".$searchStr."</strong> ";  
		//echo "java -classpath .:lucene-core-4.0.0.jar:lucene-analyzers-common-4.0.0.jar:lucene-queryparser-4.0.0.jar Searcher ".$searchStr."</br>";
		exec("java -classpath .:lucene-core-4.0.0.jar:lucene-analyzers-common-4.0.0.jar:lucene-queryparser-4.0.0.jar revSearcher ".addcslashes($searchStr, "()"), $output);
		$i=0;
		foreach ($output as &$value) {
			if($i<=3){
				if($i==0){
					//name
					echo "<h2><center>".$value."</center></h2>";
				}
				if($i==1){
					//img
					echo "<center><img src=".$value."></center>";
				}
				if($i==2){
					//prodUrl
					echo "<p>See Product: <a href=".$value.">".$value."</a></p>";
				}
				if($i==3){
					//reviewUrl
					echo "<p>See all Reviews: <a href=".$value.">".$value."</a></p></br></br>";
				}
				/*echo "<div>";
				echo $value;
				echo "</br>";
				echo "</div>";*/
			}
			else{
				if($i%3==1){
					//title
					echo "<hr/>";
					echo "<strong>".$value ."</strong>";
				}
				if($i%3==2){
					//date
					echo "<blockquote><p>".$value."</p></blockquote>";
				}
				if($i%3==0){
					//text
					echo "<p>".$value."</p>";
				}
				//title,date,text,url
			}
			$i=$i+1;
		}
		echo "<hr>";
	}
}
?>
		<br><br><br><br><br><br><br><br>
		<br><br><br><br><br><br><br><br>
	</div>


	<div id="sidebar">
		<div id="search" class="boxed">
			<h2 class="title">Search</h2>
			<div class="content">

				<form id="searchform" method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
					<fieldset>
					<input id="searchinput" type="text" name="searchinput" value="" /> <?php echo "Given query: "; echo $_POST["searchinput"];?>
					<input id="searchsubmit" type="submit" name="FindProduct" value="Search" />
					</fieldset>
				</form>
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {

	if(isset($_POST["FindProduct"])){
//		echo "Entered FindProduct";
	 	$searchStr= $_POST["searchinput"];
	 			
		exec("java -classpath .:lucene-core-4.0.0.jar:lucene-analyzers-common-4.0.0.jar:lucene-queryparser-4.0.0.jar Searcher ".addcslashes($searchStr, "()"), $output);
		echo "<p>Found : ";
		echo count($output);
		echo " results</p>";
		echo "Select any product for viewing it's reviews: ";

		echo "<form method=\"post\" action=\"";
		echo htmlspecialchars($_SERVER["PHP_SELF"]) ;
		echo "\" >";
		echo "<fieldset>";

		foreach ($output as &$value) {
	    		echo "<input id=\"product\" type=\"radio\" name=\"product\" value='".$value."'' /> ";
	    		echo $value;
					
			echo "</br>";
			echo "</br>";
		}
		  echo "<input name= \"FindReview\" type=\"submit\" value=\"Submit\">";
echo "</fieldset>";
		echo "</form>";

	 }
}
		?>


			</div>
		</div>
		
		
		<!--<div id="footer">
			<p id="legal">&copy;2007 Landscape. All Rights Reserved<br />
				Designed by <a href="http://templated.co" rel="nofollow">TEMPLATED</a></p>
			<p id="links"><a href="#">Privacy</a> | <a href="#">Terms</a> | <a href="http://validator.w3.org/check/referer" title="This page validates as XHTML 1.0 Transitional"><abbr title="eXtensible HyperText Markup Language">XHTML</abbr></a> | <a href="http://jigsaw.w3.org/css-validator/check/referer" title="This page validates as CSS"><abbr title="Cascading Style Sheets">CSS</abbr></a></p>
		</div>-->
	</div>
</div>
</div>
</body>
</html>
