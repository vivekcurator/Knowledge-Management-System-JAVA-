<?php

$filepath = "filedata/" . $_FILES["file"]["name"];
 
if(move_uploaded_file($_FILES["file"]["tmp_name"], $filepath)) 
{
	echo "Done";
} 
else 
{
	echo "Error !!";
}
 
?>