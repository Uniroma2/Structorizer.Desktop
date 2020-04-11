<?php
// program ComputeSum (generated by Structorizer 3.30-07) 

// Copyright (C) 2020-03-21 Kay Gürtzig 
// License: GPLv3-link 
// GNU General Public License (V 3) 
// https://www.gnu.org/licenses/gpl.html 
// http://www.gnu.de/documents/gpl.de.html 

// function readNumbers 
// Tries to read as many integer values as possible upto maxNumbers 
// from file fileName into the given array numbers. 
// Returns the number of the actually read numbers. May cause an exception. 
function readNumbers($fileName, $numbers, $maxNumbers)
{

	// TODO Establish sensible web formulars to get the $_GET input working. 

	$nNumbers = 0;
	$fileNo = StructorizerFileAPI::fileOpen($fileName);
	if ($fileNo <= 0)
	{
		throw new Exception("File could not be opened!");
	}
	try {
		while (! StructorizerFileAPI::fileEOF($fileNo) && $nNumbers < $maxNumbers) 
		{
			$number = StructorizerFileAPI::fileReadInt($fileNo);
			$numbers[$nNumbers] = $number;
			$nNumbers = $nNumbers + 1;
		}
	} catch (Exception $eexe2a9676a) {
		$error = $exe2a9676a->getMessage();
		throw new Exception();
	} finally {
		StructorizerFileAPI::fileClose($fileNo);
	}
	return $nNumbers;
}
// program ComputeSum 
// Computes the sum and average of the numbers read from a user-specified 
// text file (which might have been created via generateRandomNumberFile(4)). 
//  
// This program is part of an arrangement used to test group code export (issue 
// #828) with FileAPI dependency. 
// The input check loop has been disabled (replaced by a simple unchecked input 
// instruction) in order to test the effect of indirect FileAPI dependency (only the 
// called subroutine directly requires FileAPI now). 

// TODO Establish sensible web formulars to get the $_GET input working. 

$fileNo = 1000;
// Disable this if you enable the loop below! 
$file_name = $_REQUEST["Name/path of the number file"];	// TODO form a sensible input opportunity;
// If you enable this loop, then the preceding input instruction is to be disabled 
// and the fileClose instruction in the alternative below is to be enabled. 
// do 
// { 
// 	$file_name = $_REQUEST["Name/path of the number file"];	// TODO form a sensible input opportunity; 
// 	$fileNo = StructorizerFileAPI::fileOpen($file_name); 
// } while (!( $fileNo > 0 || $file_name == "" )); 
if ($fileNo > 0)
{
	// This should be enabled if the input check loop above gets enabled. 
// 	StructorizerFileAPI::fileClose($fileNo); 
	$values = array();
	$nValues = 0;
	try {
		$nValues = readNumbers($file_name, $values, 1000);
	} catch (Exception $eex72c31b7f) {
		$failure = $ex72c31b7f->getMessage();
		echo failure;
		exit(-7);
	}
	$sum = 0.0;
	for ($k = 0; $k <= $nValues-1; $k += (1))
	{
		$sum = $sum + $values[$k];
	}
	echo "sum = ", $sum;
	echo "average = ", $sum / $nValues;
}

?>
