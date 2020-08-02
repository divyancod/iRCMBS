<?php
$con=mysqli_connect("localhost","root","","restproject") or die(mysqli_error($con));

$resid=$_POST['resid'];
$tables=$_POST['numtables[]'];
$table_capacity=$_POST['tablecap[]'];

//$tables=['hello','world'];
//$table_capacity=['12','14'];
$i=0;
$query="INSERT INTO `rest_table_list`(`ResID`, `Table_No`, `Table_capacity`) VALUES (0,0,0)";

foreach($tables as $tableno)
{
    $query=$query.",('".$resid."','".$tableno."','".$table_capacity[$i++]."')";
}
//$res=mysqli_query($con,$query);
echo $query;
?>