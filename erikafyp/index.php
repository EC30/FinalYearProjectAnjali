<html>

<body>

<form method="post" action="reg.php">
	Phone: <input type="text" name="phone">
	passwd: <input type="password" name="passwd">
	gender <input type="text" name="gender">
	name: <input type="text" name="fullname">
	<input type="submit" value="aa">
</form>

<div id="main_body">
	<div id="mb_inside">
		<Span id="spanid"> LATEST </span>
		<div id="div_layout1">
			<?php
				$query="SELECT * FROM ttitbl ORDER BY sn DESC LIMIT 7";
				
				$query1=mysqli_query($my_connection_server,$query)or die("ERROR 1");
				
				$n=0;
				$bcolor="black";
				if(mysqli_num_rows($query1)>0){
					while($row=mysqli_fetch_assoc($query1)){
						if($row['type']=="tech"){
							$bcolor="green";
						}else if($row['type']=="science"){
							$bcolor="skyblue";
						}else if($row['type']=="facts"){
							$bcolor="red";
						}else if($row['type']=="countries"){
							$bcolor="brown";
						}else if($row['type']=="blog"){
							$bcolor="yellow";
						}else{
							$bcolor="black";
						}
						if($n==0){
							echo "<div id='larger_box'><a href='article/".make_url($row['title'])."'><div style='height:auto;width:100%;'>";
							echo "<img src='files/".$row['sn'].".jpg' style='width:100%;hieght:100%;'>";
							echo "</div></a><div style='height:auto;overflow:hidden;width:100%;font-size:16px;border-top:10px solid ".$bcolor.";'><div style='float:left;color:".$bcolor.";'>&nbsp;".strtoupper($row['type'])."</div><div style='float:right;'>".$row['date']."&nbsp;</div></div>";
							echo "<div style='height:auto;width:96%;padding:2%;font-weight:bold;' id='larger_font'>";
							echo "<a href='article/".make_url($row['title'])."'><span>".$row['title']."</span></a>";
							echo "</div></div>";
							$n=$n+1;
							$yuj=$row['sn'];
						}else{						
							echo "<div id='smaller_boxes'><a href='article/".make_url($row['title'])."'><div style='height:auto;width:100%;'>";
							echo "<img src='files/".$row['sn'].".jpg' style='width:100%;hieght:100%;'>";
							echo "</div></a><div style='height:auto;overflow:hidden;width:100%;font-size:16px;border-top:10px solid ".$bcolor.";'><div style='float:left;color:".$bcolor.";'>&nbsp;".strtoupper($row['type'])."</div><div style='float:right;'>".$row['date']."&nbsp;</div></div>";
							echo "<div style='height:auto;width:96%;padding:2%;font-weight:bold;font-size:20px;'>";
							echo "<a href='article/".make_url($row['title'])."'><span>".$row['title']."</span></a>";
							echo "</div></div>";
							$n=$n+1;
							$yuj=$row['sn'];
						}
					}
				}
			?>
		</div>
	</div>
</div>

<div id="main_body">
	<div id="mb_inside">
		<div id="div_layout1">
			<div id="larger_box_gad" style="background-color:#F5F0EF;">
				Advertisment
			</div>
			<?php
				$query2="SELECT * FROM ttitbl WHERE sn<$yuj ORDER BY sn DESC LIMIT 8";
				
				$query3=mysqli_query($my_connection_server,$query2)or die("ERROR IN DATABASE CONNECTION");
				$bcolor="black";
				
				if(mysqli_num_rows($query3)>0){
					while($row1=mysqli_fetch_assoc($query3)){
						if($row1['type']=="tech"){
							$bcolor="green";
						}else if($row1['type']=="science"){
							$bcolor="skyblue";
						}else if($row1['type']=="facts"){
							$bcolor="red";
						}else if($row1['type']=="countries"){
							$bcolor="brown";
						}else if($row1['type']=="blog"){
							$bcolor="yellow";
						}else{
							$bcolor="black";
						}
						echo "<div id='smaller_boxes'><a href='article/".make_url($row1['title'])."'><div style='height:auto;width:100%;'>";
						echo "<img src='files/".$row1['sn'].".jpg' style='width:100%;hieght:100%;'>";
						echo "</div></a><div style='height:auto;overflow:hidden;width:100%;font-size:16px;border-top:10px solid ".$bcolor.";'><div style='float:left;color:".$bcolor.";'>&nbsp;".strtoupper($row1['type'])."</div><div style='float:right;'>".$row1['date']."&nbsp;</div></div>";
						echo "<div style='height:auto;width:100%;padding:2%;font-weight:bold;font-size:20px;'>";
						echo "<a href='article/".make_url($row1['title'])."'><span>".$row1['title']."</span></a>";
						echo "</div></div>";
						$yuj=$row1['sn'];
					}
				}			
				mysqli_close($my_connection_server);
			?>			
		</div>
	</div>
</div>


</body>
</html>
