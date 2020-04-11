program DrawRandomHistogram;
{ Generated by Structorizer 3.30-07 }

{ Copyright (C) 2020-03-21 Kay Gürtzig }
{ License: GPLv3-link }
{
* GNU General Public License (V 3)
* https://www.gnu.org/licenses/gpl.html
* http://www.gnu.de/documents/gpl.de.html
}

{
* Reads a random number file and draws a histogram accotrding to the
* user specifications
}

{===== STRUCTORIZER FILE API START =====}
TYPE
  LongIntFile = FILE OF LongInt;
  DoubleFile = FILE OF Double;
{===== STRUCTORIZER FILE API END =====}


var
  { Interval width }
  width: ???;	{ FIXME! }
  value: ???;	{ FIXME! }
  numberArray: array of ???;	{ FIXME! }
  nObtained: Longint;
  nIntervals: ???;	{ FIXME! }
  min: ???;	{ FIXME! }
  max: ???;	{ FIXME! }
  kMaxCount: Longint;
  k: Longint;
  i: Longint;
  file_name: ???;	{ FIXME! }
  fileNo: ???;	{ FIXME! }
  count: array of Longint;
  failure: Exception;


{===== STRUCTORIZER FILE API START =====}

{ Approaches File API base function fileRead by means of read in a Text file }
function fileRead(var tFile: Text): String;

var
  String word, nextWord;
  Integer len; { String length }

begin
  word = '';
  read(tFile, word);
  { FIXME: Detection of array initializers? }
  len = length(word);
  if len > 0 then
  begin
    { test for double or single quote }
    if (word[1] = #34) or (word[1] = #39) then
    begin
      while not eof(tFile) and (word[len] <> word[1]) do
      begin
        read(tFile, nextWord);
        word = word + ' ' + nextWord;
        len = length(word);
      end;
      if word[len] = word[1] then
        word = copy(word, 2, len-2);
    end
  end;
  fileRead = word;
end;

{ Implements File API function fileReadChar by means of read in a Text file }
function fileReadChar(var tFile: Text): String;

var
  String word;

begin
  word = '';
  read(tFile, word);
  fileRead = word;
end;

{ Implements File API function fileReadInt by means of read in a typed file }
function fileReadInt(var iFile: LongIntFile): LongInt;

var
  LongInt value;

begin
  value = 0;
  read(iFile, value);
  fileReadInt = value;
end;

{ Implements File API function fileReadDouble by means of read in a typed file }
function fileReadDouble(var dFile: DoubleFile): Double;

var 
  Double value;

begin
  value = 0.0;
  read(dFile, value);
  fileReadDouble = value;
end;

{ Implements base function fileRead by means of readln in a Text file }
function fileReadLine(var tFile: Text): String

var
  String line;
  
begin
  line = '';
  readln(tFile, line);
  fileReadLine = line;
end;

{===== STRUCTORIZER FILE API END =====}


{
* Draws a bar chart from the array "values" of size nValues.
* Turtleizer must be activated and will scale the chart into a square of
* 500 x 500 pixels
* Note: The function is not robust against empty array or totally equal values.
}
procedure drawBarChart(values: array of Double; nValues: ???);

const
  { Used range of the Turtleizer screen }
  xSize = 500;
  ySize = 500;

var
  yScale: ???;	{ FIXME! }
  yAxis: ???;	{ FIXME! }
  valMin: ???;	{ FIXME! }
  valMax: ???;	{ FIXME! }
  stripeWidth: ???;	{ FIXME! }
  stripeHeight: ???;	{ FIXME! }
  kMin: Longint;
  kMax: Longint;
  k: Longint;

begin
  kMin := 0;
  kMax := 0;
  for k := 1 to nValues-1 do
  begin
    if (values[k] > values[kMax]) then
    begin
      kMax := k;
    end
    else
    begin
      if (values[k] < values[kMin]) then
      begin
        kMin := k;
      end;
    end;
  end;
  valMin := values[kMin];
  valMax := values[kMax];
  yScale := valMax * 1.0 / (ySize - 1);
  yAxis := ySize - 1;
  if (valMin < 0) then
  begin
    if (valMax > 0) then
    begin
      yAxis := valMax * ySize * 1.0 / (valMax - valMin);
      yScale := (valMax - valMin) * 1.0 / (ySize - 1);
    end
    else
    begin
      yAxis := 1;
      yScale := valMin * 1.0 / (ySize - 1);
    end;
  end;
  { draw coordinate axes }
  gotoXY(1, ySize - 1);
  forward(ySize -1); { color = ffffff }
  penUp();
  backward(yAxis); { color = ffffff }
  right(90);
  penDown();
  forward(xSize -1); { color = ffffff }
  penUp();
  backward(xSize-1); { color = ffffff }
  stripeWidth := xSize / nValues;
  for k := 0 to nValues-1 do
  begin
    stripeHeight := values[k] * 1.0 / yScale;
    case (k mod 3) of
      0:
        begin
          setPenColor(255,0,0);
        end;
      1:
        begin
          setPenColor(0, 255,0);
        end;
      2:
        begin
          setPenColor(0, 0, 255);
        end;
    end;
    fd(1); { color = ffffff }
    left(90);
    penDown();
    fd(stripeHeight); { color = ffffff }
    right(90);
    fd(stripeWidth - 1); { color = ffffff }
    right(90);
    forward(stripeHeight); { color = ffffff }
    left(90);
    penUp();
  end;

end;

{
* Tries to read as many integer values as possible upto maxNumbers
* from file fileName into the given array numbers.
* Returns the number of the actually read numbers. May cause an exception.
}
function readNumbers(fileName: string; numbers: array of Longint; maxNumbers: Longint): Longint;

var
  number: Longint;
  nNumbers: Longint;
  fileNo: Longint;

begin
  nNumbers := 0;
  assign(fileNo, fileName); open(fileNo);
  { TODO: Consider replacing this file test using IOResult! }
  if (fileNo <= 0) then
  begin
    raise Exception.Create('File could not be opened!');
  end;
  try
    try
      while (not eof(fileNo) and nNumbers < maxNumbers) do
      begin
        number := fileReadInt(fileNo);
        numbers[nNumbers] := number;
        nNumbers := nNumbers + 1;
      end;
    except
      on Ex : Exception do
      begin
        { FIXME: Ensure a declaration for variable error (String) in the VAR block! }
        error := Ex.Message;
        raise Exception.Create();
      end;
    end;
  finally
    closeFile(fileNo);
  end;
  readNumbers := nNumbers;

end;
begin
  fileNo := -10;
  repeat
    write('Name/path of the number file'); readln(file_name);
    assign(fileNo, file_name); open(fileNo);
    until (fileNo > 0 or file_name = '');
  { TODO: Consider replacing this file test using IOResult! }
  if (fileNo > 0) then
  begin
    closeFile(fileNo);
    write('number of intervals'); readln(nIntervals);
    { Initialize the interval counters }
    for k := 0 to nIntervals-1 do
    begin
      count[k] := 0;
    end;
    { Index of the most populated interval }
    kMaxCount := 0;
    { Hint: Automatically decomposed array initialization }
    nObtained := 0;
    try
      nObtained := readNumbers(file_name, numberArray, 10000);
    except
      on Ex : Exception do
      begin
        { FIXME: Ensure a declaration for variable failure (String) in the VAR block! }
        failure := Ex.Message;
        writeln(failure);
      end;
    end;
    if (nObtained > 0) then
    begin
      min := numberArray[0];
      max := numberArray[0];
      for i := 1 to nObtained-1 do
      begin
        if (numberArray[i] < min) then
        begin
          min := numberArray[i];
        end
        else
        begin
          if (numberArray[i] > max) then
          begin
            max := numberArray[i];
          end;
        end;
      end;
      { Interval width }
      width := (max - min) * 1.0 / nIntervals;
      for i := 0 to nObtained - 1 do
      begin
        value := numberArray[i];
        k := 1;
        while (k < nIntervals and value > min + k * width) do
        begin
          k := k + 1;
        end;
        count[k-1] := count[k-1] + 1;
        if (count[k-1] > count[kMaxCount]) then
        begin
          kMaxCount := k-1;
        end;
      end;
      drawBarChart(count, nIntervals);
      writeln('Interval with max count: ', kMaxCount, ' (', count[kMaxCount], ')');
      for k := 0 to nIntervals-1 do
      begin
        writeln(count[k], ' numbers in interval ', k, ' (', min + k * width, ' ... ', min + (k+1) * width, ')');
      end;
    end
    else
    begin
      writeln('No numbers read.');
    end;
  end;
end.
