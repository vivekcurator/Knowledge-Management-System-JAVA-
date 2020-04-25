<?php 
if (isset($_POST['filename'])) {
	$filename = $_POST['filename'];
	$path    = __DIR__ .'/filedata/'. $filename;
	if (file_exists($path)) {
		unlink($path);
		echo "File deleted";
	}else{
		echo "File not found";
	}
}
 ?>
