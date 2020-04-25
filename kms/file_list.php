<?php 
	$path    = __DIR__ .'/filedata';
	$files = array_diff(scandir($path), array('.', '..'));
	$out = array_values($files);
	echo json_encode($out);

 ?>