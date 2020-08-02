<?php
$con=mysqli_connect("localhost","root","","restproject");
if(mysqli_connect_errno())
{
    echo "failed to connect";
    die();
}
$query="select * from rest_menu_list where restid='2693'";
$result= mysqli_query($con,$query);

$lop= mysqli_num_rows($result);


class ne
{
    public $items=array();
    public $price=array();
    public $itemtime=array();
    public $curravail=array();
    public $rating=array();
}


$i=0;
while($lop!=0)
{
    $ob[$i]= new ne();
if(mysqli_num_rows($result)>0)
{
    $row=mysqli_fetch_array($result);
    $ob[$i]->items=$row['Item_Name'];
    $ob[$i]->price=$row['Item_Price'];
    $ob[$i]->itemtime=$row['Item_avail'];
    $ob[$i]->curravail=$row['Item_curr_avail'];
    $ob[$i]->rating=$row['item_rating'];

}
    $lop--;
    $i++;
}
$mjs= json_encode($ob);
echo $mjs;
?>